package com.alexecollins.docker.mojo;

import com.alexecollins.docker.orchestration.DockerOrchestrator;
import com.alexecollins.docker.orchestration.model.Id;
import com.github.dockerjava.api.DockerClient;
import org.apache.commons.io.FilenameUtils;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectHelper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SaveMojo.class, FilenameUtils.class})
public class SaveMojoTest extends MojoTestSupport {

    private static final String SAVE_DIR = "/save/dir";

    private static final String EXTENSION = "ext";

    private static Map<Id, File> SAVED_FILES;

    private SaveMojo saveMojo;

    private DockerOrchestrator mockDockerOrchestrator;

    private File saveDir;

    private MavenProjectHelper mavenProjectHelper;

    @BeforeClass
    public static void init() {
        SAVED_FILES = new HashMap<>();

        SAVED_FILES.put(new Id("id1"), new File("/saved/file1.ext"));
        SAVED_FILES.put(new Id("id2"), new File("/saved/file2.ext"));
        SAVED_FILES.put(new Id("id3"), new File("/saved/file3.ext"));

        SAVED_FILES = Collections.unmodifiableMap(SAVED_FILES);
    }

    @Before
    public void setUp() throws Exception {

        saveMojo = PowerMockito.spy(new SaveMojo());

        saveDir = new File(SAVE_DIR);

        DockerClient mockDockerClient = mock(DockerClient.class);

        mockDockerOrchestrator = mock(DockerOrchestrator.class);
        when(mockDockerOrchestrator.save(saveDir, true)).thenReturn(SAVED_FILES);

        prepareMojo(saveMojo, mockDockerClient, mockDockerOrchestrator);

        Whitebox.setInternalState(saveMojo, "saveDir", saveDir);
        Whitebox.setInternalState(saveMojo, "gzipSave", true);

        mavenProjectHelper = mock(MavenProjectHelper.class);
        Whitebox.setInternalState(saveMojo, "mavenProjectHelper", mavenProjectHelper);
    }

    @Test
    public void testSaveWithAttach() throws Exception {
        // given
        Whitebox.setInternalState(saveMojo, "attach", true);
        PowerMockito.mockStatic(FilenameUtils.class);
        PowerMockito.when(FilenameUtils.getExtension(anyString())).thenReturn(EXTENSION);

        // when
        saveMojo.execute();

        // then
        verify(saveMojo).doExecute(mockDockerOrchestrator);
        verify(mockDockerOrchestrator).save(saveDir, true);

        for (Id id : SAVED_FILES.keySet()) {
            verify(mavenProjectHelper).attachArtifact(
                    saveMojo.getProject(), EXTENSION, id.toString(), SAVED_FILES.get(id)
            );
        }
    }

    @Test
    public void testSaveWithoutAttach() throws Exception {
        // when
        saveMojo.execute();

        // then
        verify(saveMojo).doExecute(mockDockerOrchestrator);
        verify(mockDockerOrchestrator).save(saveDir, true);
        verify(mavenProjectHelper, never()).attachArtifact(
                any(MavenProject.class), anyString(), anyString(), any(File.class)
        );
    }

}
