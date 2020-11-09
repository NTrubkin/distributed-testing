package ru.nntu.prototypetestrunner;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.platform.engine.discovery.ClassSelector;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

public class TestRunnerApp {

    public static void main(String[] args) {
        new TestRunnerApp().run(args);
    }

    private void run(String[] tests) {
        System.out.println("Run tests: " + Arrays.toString(tests));

        List<ClassSelector> testSelectors = Arrays.stream(tests)
                                                  .map(DiscoverySelectors::selectClass)
                                                  .collect(Collectors.toList());

        var request = LauncherDiscoveryRequestBuilder.request()
                                                     .selectors(testSelectors)
                                                     .build();

        var launcher = LauncherFactory.create();
        var listener = new SummaryGeneratingListener();
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);

        var summary = listener.getSummary();
        summary.printTo(new PrintWriter(System.out));
        if (!summary.getFailures().isEmpty()) {
            System.exit(1);
        }
    }
}
