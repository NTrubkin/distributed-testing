package ru.nntu.distributedtesting.prototypemavenplugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import ru.nntu.distributedtesting.common.Utils;
import ru.nntu.distributedtesting.common.model.Resources;

import static org.apache.maven.plugins.annotations.LifecyclePhase.TEST;

@Mojo(name = "run", defaultPhase = TEST)
@SuppressWarnings("unused")
public class DistributedTestingMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    @Override
    public void execute() {
        getLog().info("Start distributed testing");

        var resources = new Resources();

        String mainResPath = project.getBuild().getOutputDirectory();
        byte[] mainResZip = Utils.zipFiles(mainResPath);
        resources.setMainResources(mainResZip);

        String testResPath = project.getBuild().getTestOutputDirectory();
        byte[] testResZip = Utils.zipFiles(testResPath);
        resources.setTestResources(testResZip);

        new DistributedTestingClient(resources).start();

        getLog().info("Distributed testing success");
    }
}

