package com.alexecollins.docker;

import com.kpelykh.docker.client.DockerClient;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.File;

import static org.apache.commons.io.FileUtils.touch;


@Mojo(name = "build-test", defaultPhase = LifecyclePhase.GENERATE_TEST_RESOURCES)
@SuppressWarnings("unused")
public class BuildTestMojo extends AbstractDockerMojo {

    @Override
    void doExecute(DockerClient dockerClient, File workDir) throws Exception {

        final File dir = new File("src/test/docker/dockerfiles");

        if (!dir.isDirectory()) {
            throw new IllegalStateException(dir.getAbsolutePath() + " does not exist, or is not dir");
        }

        for (File dockerFolder : dir.listFiles()) {
            final String name = dockerFolder.getName();
            dockerClient.build(dockerFolder);
            getLog().info("build " + name);
        }
        touch(new File(workDir, "build-test"));
    }

}
