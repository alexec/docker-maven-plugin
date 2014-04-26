package com.alexecollins.docker.orchestration;


import com.alexecollins.docker.orchestration.model.Id;
import com.kpelykh.docker.client.DockerClient;
import com.kpelykh.docker.client.DockerException;
import com.kpelykh.docker.client.model.*;
import com.sun.jersey.api.client.ClientResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.apache.commons.io.FileUtils.*;
import static org.apache.commons.io.IOUtils.closeQuietly;
import static org.apache.commons.io.IOUtils.copyLarge;

/**
 * Orchestrates multiple Docker containers based on
 */
public class DockerOrchestrator {
	private static final Logger LOGGER = LoggerFactory.getLogger(DockerOrchestrator.class);
	private final DockerClient docker;
	private final Repo repo;
	private final File workDir;

	public DockerOrchestrator(DockerClient docker, File src, File workDir, String prefix) {
		if (docker == null) {
			throw new IllegalArgumentException("docker is null");
		}
		if (src == null) {
			throw new IllegalArgumentException("src is null");
		}
		if (workDir == null) {
			throw new IllegalArgumentException("workDir is null");
		}
		if (prefix == null) {
			throw new IllegalArgumentException("prefix is null");
		}
		this.docker = docker;
		try {
			this.repo = new Repo(docker, prefix, src);
		} catch (IOException e) {
			throw new OrchestrationException(e);
		}
		this.workDir = workDir;
	}

	public void clean() {
		for (Id id : repo.ids(true)) {
			stop(id);
			clean(id);
		}
	}

	void clean(final Id id) {
		if (id == null) {
			throw new IllegalArgumentException("id is null");
		}
		stop(id);
		LOGGER.info("clean " + id);
		for (Container container : repo.findContainers(id, true)) {
			LOGGER.info("rm " + Arrays.toString(container.getNames()));
			try {
				docker.removeContainer(container.getId());
			} catch (DockerException e) {
				throw new OrchestrationException(e);
			}
		}
		final Image image;
		try {
			image = repo.findImage(id);
		} catch (DockerException e) {
			throw new OrchestrationException(e);

		}
		if (image != null) {
			LOGGER.info("rmi " + image.getId());
			try {
				docker.removeImage(image.getId());
			} catch (DockerException e) {
				LOGGER.warn(" - " + e.getMessage());
			}
		}
	}

	void build(final Id id) {
		if (id == null) {
			throw new IllegalArgumentException("id is null");
		}
		LOGGER.info("package " + id);
		try {
			build(prepare(id), id);
		} catch (IOException e) {
			throw new OrchestrationException(e);
		}
	}

	private File prepare(Id id) throws IOException {
		if (id == null) {
			throw new IllegalArgumentException("id is null");
		}
		final File dockerFolder = repo.src(id);
		final File destDir = new File(workDir, dockerFolder.getName());
		// copy template
		copyDirectory(dockerFolder, destDir);
		// copy files
		for (String file : repo.conf(id).packaging.add) {
			File fileEntry = new File(file);
			copyFileEntry(destDir, fileEntry);
		}

		return destDir;
	}

	private void copyFileEntry(final File destDir, File fileEntry) throws IOException {
		if (fileEntry.isDirectory()) {
			LOGGER.info(" - add (dir) " + fileEntry.getAbsolutePath());
			copyDirectoryToDirectory(fileEntry, destDir);
		} else {
			LOGGER.info(" - add (file) " + fileEntry.getAbsolutePath());
			copyFileToDirectory(fileEntry, destDir);
		}
	}


	@SuppressWarnings(("DM_DEFAULT_ENCODING"))
	private void build(File dockerFolder, Id id) {

		final ClientResponse response;
		try {
			response = docker.build(dockerFolder, repo.imageName(id));
		} catch (DockerException e) {
			throw new OrchestrationException(e);
		}

		final StringWriter out = new StringWriter();
		try {
			copyLarge(new InputStreamReader(response.getEntityInputStream(), Charset.defaultCharset()), out);
		} catch (IOException e) {
			throw new OrchestrationException(e);
		} finally {
			closeQuietly(response.getEntityInputStream());
		}

		String log = out.toString();
		if (!log.contains("Successfully built")) {
			throw new IllegalStateException("failed to build, log missing lines in" + log);
		}

		// imageId
		// return substringBetween(log, "Successfully built ", "\\n\"}").trim();
	}

	void start(final Id id) {
		if (id == null) {
			throw new IllegalArgumentException("id is null");
		}

		// only start
		try {
			if (repo.findContainer(id) == null) {
				LOGGER.info("creating " + id);
				final ContainerConfig config = new ContainerConfig();
				config.setImage(repo.findImage(id).getId());
	        /*
            config.setVolumesFrom(volumesFrom(id).toString().replaceAll("[ \\[\\]]", ""));

            LOGGER.info(" - volumes from " + volumesFrom(id));
             */

				docker.createContainer(config, repo.containerName(id));
			}

			LOGGER.info("starting " + id);
			docker.startContainer(repo.findContainer(id).getId(), newHostConfig(id));
		} catch (DockerException e) {
			throw new OrchestrationException(e);
		}
	}

	private List<Id> volumesFrom(Id id) {
		final List<Id> ids = new ArrayList<Id>();
		for (Id from : repo.conf(id).volumesFrom) {
			ids.add(new Id(repo.findContainer(from).getId()));
		}

		return ids;
	}

	private HostConfig newHostConfig(Id id) {
		final HostConfig config = new HostConfig();

		config.setPublishAllPorts(true);
		config.setLinks(links(id));

		LOGGER.info(" - links " + repo.conf(id).links);

		final Ports portBindings = new Ports();
		for (String e : repo.conf(id).ports) {

			final String[] split = e.split(" ");

			assert split.length == 1 || split.length == 2;

			final int a = Integer.parseInt(split[0]);
			final int b = split.length == 2 ? Integer.parseInt(split[1]) : a;

			LOGGER.info(" - port " + e);
			portBindings.addPort(new Ports.Port("tcp", String.valueOf(a), null, String.valueOf(b)));
		}

		config.setPortBindings(portBindings);

		return config;
	}

	private String[] links(Id id) {

		final List<Id> links = repo.conf(id).links;
		final String[] out = new String[links.size()];
		for (int i = 0; i < links.size(); i++) {
			final String name = repo.findContainer(links.get(i)).getNames()[0];
			out[i] = name + ":" + name;
		}

		return out;
	}

	void stop(final Id id) {
		if (id == null) {
			throw new IllegalArgumentException("id is null");
		}
		for (Container container : repo.findContainers(id, false)) {
			LOGGER.info("stopping " + Arrays.toString(container.getNames()));
			try {
				docker.stopContainer(container.getId(), 1);
			} catch (DockerException e) {
				throw new OrchestrationException(e);
			}
		}
	}

	public void build() {
		for (Id id : repo.ids(false)) {
			build(id);
		}
	}

	public void start() {
		for (Id id : repo.ids(false)) {
			try {
				if (repo.findImage(id) == null) {
					build(id);
				}
			} catch (DockerException e) {
				throw new OrchestrationException(e);
			}
			start(id);
		}
	}

	public void stop() {
		for (Id id : repo.ids(true)) {
			stop(id);
		}
	}
}
