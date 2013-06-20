package com.demo.web;

import static java.util.Arrays.asList;
import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;
import static org.jbehave.core.reporters.Format.CONSOLE;
import static org.jbehave.web.selenium.WebDriverHtmlOutput.WEB_DRIVER_HTML;

import java.util.List;

import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.embedder.StoryControls;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.failures.PendingStepStrategy;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.SilentStepMonitor;
import org.jbehave.web.selenium.ContextView;
import org.jbehave.web.selenium.LocalFrameContextView;
import org.jbehave.web.selenium.PerStoriesWebDriverSteps;
import org.jbehave.web.selenium.PropertyWebDriverProvider;
import org.jbehave.web.selenium.SeleniumConfiguration;
import org.jbehave.web.selenium.SeleniumContext;
import org.jbehave.web.selenium.SeleniumContextOutput;
import org.jbehave.web.selenium.SeleniumStepMonitor;
import org.jbehave.web.selenium.WebDriverProvider;
import org.jbehave.web.selenium.WebDriverScreenshotOnFailure;
import org.jbehave.web.selenium.WebDriverSteps;

import com.demo.web.pages.Pages;
import com.demo.web.steps.GoogleWebStep;
import com.google.common.util.concurrent.MoreExecutors;

public class GoogleStories extends JUnitStories {

	private WebDriverProvider driverProvider = new PropertyWebDriverProvider();
	private WebDriverSteps lifecycleSteps = new PerStoriesWebDriverSteps(
			driverProvider); // or PerStoryWebDriverSteps(driverProvider)
	private Pages pages = new Pages(driverProvider);
	private SeleniumContext context = new SeleniumContext();
	private ContextView contextView = new LocalFrameContextView().sized(500,
			100);

	Format[] formats = new Format[] { new SeleniumContextOutput(context),
			CONSOLE, WEB_DRIVER_HTML };

	PendingStepStrategy pendingStepStrategy = new FailingUponPendingStep();
	CrossReference crossReference = new CrossReference().withJsonOnly()
			.withPendingStepStrategy(pendingStepStrategy)
			.withOutputAfterEachStory(true)
			.excludingStoriesWithNoExecutedScenarios(true);

	SeleniumStepMonitor stepMonitor = new SeleniumStepMonitor(contextView,
			context, crossReference.getStepMonitor());

	StoryReporterBuilder reporterBuilder = new StoryReporterBuilder()
			.withCodeLocation(codeLocationFromClass(GoogleStories.class))
			.withFailureTrace(true).withFailureTraceCompression(true)
			.withDefaultFormats().withFormats(formats)
			.withCrossReference(crossReference);

	public GoogleStories() {
		// If configuring lifecycle per-stories, you need to ensure that you a
		// same-thread executor
		if (lifecycleSteps instanceof PerStoriesWebDriverSteps) {
			configuredEmbedder().useExecutorService(
					MoreExecutors.sameThreadExecutor());
		}
	}

	@Override
	public Configuration configuration() {
		Class<? extends Embeddable> embeddableClass = this.getClass();
		return new SeleniumConfiguration()
				.useSeleniumContext(context)
				.usePendingStepStrategy(pendingStepStrategy)
				.useStoryControls(
						new StoryControls().doResetStateBeforeScenario(false))
				.useStepMonitor(stepMonitor)
				.useStepMonitor(
						new SeleniumStepMonitor(contextView, context,
								new SilentStepMonitor()))
				.useStoryLoader(new LoadFromClasspath(embeddableClass))
				.useStoryReporterBuilder(reporterBuilder);
	}

	@Override
	public InjectableStepsFactory stepsFactory() {
		Configuration configuration = configuration();
		return new InstanceStepsFactory(configuration,
				new GoogleWebStep(pages), lifecycleSteps,
				new WebDriverScreenshotOnFailure(driverProvider,
						configuration.storyReporterBuilder()));
	}

	@Override
	protected List<String> storyPaths() {
		return new StoryFinder().findPaths(
				codeLocationFromClass(this.getClass()).getFile(), asList("**/"
						+ System.getProperty("storyFilter", "*") + ".story"),
				null);
	}

}