package com.alexecollins.docker.mojo;

import com.alexecollins.docker.orchestration.DockerOrchestrator;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.VersionCmd;
import com.github.dockerjava.api.model.Version;
import org.apache.maven.model.Build;
import org.apache.maven.project.MavenProject;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;

import java.util.Properties;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MojoTestSupport {

    protected static final String PROJECT_GROUP_ID = "id.group";

    protected static final String PROJECT_ARTIFACT_ID = "artifact-id";

    protected static final String PROJECT_VERSION = "1.2.3-SNAPSHOT";

    protected static final String PROJECT_NAME = "project-name";

    protected static final String PROJECT_DESCRIPTION = "Project Description";

    protected static final String BUILD_DIRECTORY = "/build/directory";

    protected static final String BUILD_FINAL_NAME = "buildFinalName";


    protected void prepareMojo(
            AbstractDockerMojo dockerMojo,
            DockerClient mockDockerClient,
            DockerOrchestrator mockDockerOrchestrator) throws Exception {

        MavenProject mavenProject = createMavenProject();
        Whitebox.setInternalState(dockerMojo, "project", mavenProject);

        // prepare docker client
        if (mockDockerClient != null) {
            VersionCmd mockVersionCmd = mock(VersionCmd.class);
            Version mockVersion = mock(Version.class);

            PowerMockito.doReturn(mockDockerClient).when(dockerMojo, "dockerClient");

            when(mockDockerClient.versionCmd()).thenReturn(mockVersionCmd);
            when(mockVersionCmd.exec()).thenReturn(mockVersion);
            when(mockVersion.getVersion()).thenReturn("1.0");
        }

        // prepare docker orchestrator
        if (mockDockerOrchestrator != null) {
            PowerMockito.doReturn(mockDockerOrchestrator)
                    .when(dockerMojo, "dockerOrchestrator",
                            Mockito.any(Properties.class),
                            Mockito.any(DockerClient.class)
                    );
        }
    }

    protected MavenProject createMavenProject() {

        MavenProject mavenProject = new MavenProject();
        mavenProject.setGroupId(PROJECT_GROUP_ID);
        mavenProject.setArtifactId(PROJECT_ARTIFACT_ID);
        mavenProject.setVersion(PROJECT_VERSION);
        mavenProject.setName(PROJECT_NAME);
        mavenProject.setDescription(PROJECT_DESCRIPTION);
        mavenProject.setBuild(createBuild());

        return mavenProject;
    }

    protected Build createBuild() {

        Build build = new Build();

        build.setDirectory(BUILD_DIRECTORY);
        build.setFinalName(BUILD_FINAL_NAME);

        return build;
    }

}
