package utilities;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

    public static ExtentReports extent;

    public static ExtentReports getInstance(){

        if (extent==null){
            String reportPath=System.getProperty("user.dir")+"/target/extent-report/index.html";
            ExtentSparkReporter htmlReporter=new ExtentSparkReporter(reportPath);
            htmlReporter.config().setTheme(Theme.DARK);
            htmlReporter.config().setDocumentTitle("Extent Report");
            extent=new ExtentReports();
            extent.attachReporter(htmlReporter);
            extent.setAnalysisStrategy(AnalysisStrategy.SUITE);
            extent.setSystemInfo("Browser: ",System.getenv("Browser"));
            extent.setSystemInfo("Thread Nos: ",String.valueOf(Thread.currentThread().getThreadGroup().activeGroupCount()));

        }

        return extent;
    }
}

