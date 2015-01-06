package com.alexecollins.docker.mojo;

import java.util.HashMap;
import java.util.regex.Pattern;

import com.alexecollins.docker.orchestration.DockerOrchestrator;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * Validate {@code Dockerfile} files.
 * Based on
 * @see https://github.com/Runnable/validate-dockerfile
 * and
 * @see https://docs.docker.com/articles/dockerfile_best-practices/
 */
@Mojo(name = "validate", defaultPhase = LifecyclePhase.VALIDATE)
public class ValidateMojo extends AbstractDockerMojo {

	private final static String[] instructions = new String[] {"CMD", "FROM","MAINTAINER","RUN","EXPOSE","ENV","ADD","ENTRYPOINT","VOLUME","USER","WORKDIR","ONBUILD","COPY"};

	// Some regexes sourced from:
	// http://stackoverflow.com/a/2821201/1216976
	// http://stackoverflow.com/a/3809435/1216976
	// http://stackoverflow.com/a/6949914/1216976
	private final static HashMap<String, Pattern> instructionsParams = new HashMap<String, Pattern>();

	static {
		instructionsParams.put("FROM", Pattern.compile("/^[a-z0-9.\\/_-]+(:[a-z0-9._-]+)?$/"));
		instructionsParams.put("MAINTAINER", Pattern.compile("/.+/"));
		instructionsParams.put("EXPOSE", Pattern.compile("/^[0-9]+([0-9\\s]+)?$/"));
		instructionsParams.put("ENV", Pattern.compile("/^[a-zA-Z_]+[a-zA-Z0-9_]* .+$/"));
		instructionsParams.put("USER", Pattern.compile("/^[a-z_][a-z0-9_]{0,30}$/"));
		instructionsParams.put("RUN", Pattern.compile("/.+/"));
		instructionsParams.put("CMD", Pattern.compile("/.+/"));
		instructionsParams.put("ONBUILD", Pattern.compile("/.+/"));
		instructionsParams.put("ENTRYPOINT", Pattern.compile("/.+/"));
		instructionsParams.put("ADD", Pattern.compile("/^(~?[A-z0-9\\/_.-]+|https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&\\/\\/=]*))\\s~?[A-z0-9\\/_.-]+$/"));
		instructionsParams.put("COPY", Pattern.compile("/^(~?[A-z0-9\\/_.-]+|https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&\\/\\/=]*))\\s~?[A-z0-9\\/_.-]+$/"));
		instructionsParams.put("VOLUME", Pattern.compile("/^~?([A-z0-9\\/_.-]+|\\[(\\s*)?(\"[A-z0-9\\/_. -]+\"(,\\s*)?)+(\\s*)?\\])$/"));
		instructionsParams.put("WORKDIR", Pattern.compile("/^~?[A-z0-9\\/_.-]+$/"));
	}
	
	

    @Override
    protected void doExecute(DockerOrchestrator orchestrator) {
	    // Read file 
	    // validate line

    }
}
