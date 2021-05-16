package utilities;

import com.thoughtworks.gauge.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Driver {

    // Holds the WebDriver instance
    public static ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();
    WebDriver driver;
    EventFiringWebDriver eventFiringWebDriver;
    WebEventListener webEventListener;
    ExtentTestManager extentTestManager = new ExtentTestManager();


    // Initialize a webDriver instance of required browser
    @BeforeSpec
    public void initializeDriver(ExecutionContext context) {
        extentTestManager.specStart(context.getCurrentSpecification().getName());

    }

    @BeforeScenario
    public void beforeScenarioActions(ExecutionContext context) {
        if (webDriver.get() == null) {
            driver = DriverFactory.getDriverInstance();
            eventFiringWebDriver = new EventFiringWebDriver(driver);
            webEventListener = new WebEventListener();
            eventFiringWebDriver.register(webEventListener);
            webDriver.set(eventFiringWebDriver);
            webDriver.get().manage().timeouts().implicitlyWait(Integer.parseInt(System.getenv("IMPLICIT_WAIT")), TimeUnit.SECONDS);
            webDriver.get().manage().timeouts().pageLoadTimeout(Integer.parseInt(System.getenv("PAGE_LOAD_TIMEOUT")), TimeUnit.SECONDS);
            webDriver.get().manage().deleteAllCookies();
            webDriver.get().manage().window().maximize();
        }
        extentTestManager.testStart(context.getCurrentScenario().getName());


    }

    @AfterStep
    public void afterStepActions(ExecutionContext context) {
        boolean stepStatus = context.getCurrentStep().getIsFailing();
        if (!stepStatus) {
            extentTestManager.logPass(context.getCurrentStep().getText());
        } else {
            extentTestManager.logFail(context.getCurrentStep().getText());
            extentTestManager.captureScreenshot();
            extentTestManager.logInfo(CommonUtils.getStepErrorMessage(context));
            extentTestManager.logInfo(CommonUtils.getStackTrace());

        }
    }


    @AfterScenario
    public void afterScenarioActions(ExecutionContext context) {
        boolean scenarioStatus = context.getCurrentScenario().getIsFailing();
        if (!scenarioStatus)
            extentTestManager.scenarioPass();
        else
            extentTestManager.scenarioFail();
    }


    // Close the webDriver instance
    @AfterSpec
    public void closeDriver() {
        if (System.getenv("inParallel").equals("true"))
            getDriver().close();
    }

    @AfterSuite
    public synchronized void quitDriver() {
        if (getDriver() != null)
            getDriver().quit();
        extentTestManager.flushReports();
    }

    public static WebDriver getDriver() {
        return webDriver.get();

    }

}
