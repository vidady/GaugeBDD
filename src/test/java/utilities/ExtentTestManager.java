package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.util.Date;

public class ExtentTestManager {

    public static ThreadLocal<ExtentTest> testReport = new ThreadLocal<>();
    ExtentReports extent = ExtentManager.getInstance();
    public static ThreadLocal<ExtentTest> testScenario = new ThreadLocal<>();

    //ExtentTest testScenario;
    public static ThreadLocal<ExtentTest> testSpec = new ThreadLocal<>();
    static Date date = new Date();

    public static synchronized ExtentTest getTest() {
        return testReport.get();
    }

    public synchronized void testStart(String testName) {

        testScenario.set(testSpec.get().createNode(testName));
        //testScenario = testSpec.get().createNode(testName);

        testReport.set(testScenario.get());
    }

    public synchronized void specStart(String specName) {
        testSpec.set(extent.createTest(specName));

    }

    public synchronized void flushReports() {
        extent.flush();
    }

    public void logInfo(String message) {
        getTest().info(message);
    }

    public void logPass(String message) {
        getTest().pass("<font color=" + "green>" + message + "</font>");
    }

    public void logFail(String message) {
        getTest().fail("<font color=" + "red>" + message + "</font>");
    }

    public void scenarioPass() {
        String passLog = "SCENARIO PASSED";
        Markup markup = MarkupHelper.createLabel(passLog, ExtentColor.GREEN);
        getTest().log(Status.PASS, markup);
    }

    public void scenarioFail() {
        String passLog = "SCENARIO FAILED";
        Markup markup = MarkupHelper.createLabel(passLog, ExtentColor.RED);
        getTest().log(Status.FAIL, markup);

    }

    public void scenarioException() {
        String passLog = "EXCEPTION OCCURRED: " + Thread.currentThread().getStackTrace();
        Markup markup = MarkupHelper.createLabel(passLog, ExtentColor.GREY);
        getTest().log(Status.WARNING, markup);

    }

    public synchronized void captureScreenshot() {
        File scrFile = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.FILE);
        String screenshotName = date.toString().replace(":", "_").replace(" ", "_") + ".jpg";
        try {
            FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "/target/extent-report/" + screenshotName));
            injectExtentScreenshot(screenshotName);
        } catch (Exception e) {
            scenarioException();
        }
    }

    public synchronized void injectExtentScreenshot(String screenshotName) {
        try {
            getTest().fail("<font color=" + "orange>" + "Screenshot of failure" + "</font>" + "<br>",
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotName).build());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
