package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {

    // Get a new WebDriver Instance.
    // There are various implementations for this depending on browser. The required browser can be set as an environment variable.
    // Refer http://getgauge.io/documentation/user/current/managing_environments/README.html
    public static WebDriver getDriverInstance() {

        String browser = System.getenv("BROWSER");
        browser = (browser == null) ? "CHROME" : browser;
        ChromeOptions options;
        DesiredCapabilities cap;

        switch (browser) {
            case "IE":
                WebDriverManager.iedriver().setup();
                return new InternetExplorerDriver();
            case "FIREFOX":
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver();
            case "EDGE":
                System.setProperty("webdriver.edge.driver", System.getProperty("user.dir") + "/libs/edgedriver/msedgedriver.exe");
                return new EdgeDriver();
            case "CHROME":
                WebDriverManager.chromedriver().setup();
                options = new ChromeOptions();
                if ("Y".equalsIgnoreCase(System.getenv("HEADLESS"))) {
                    options.addArguments("--headless");
                    options.addArguments("--disable-gpu");
                }
                return new ChromeDriver(options);

            case "REMOTE":
                cap=DesiredCapabilities.safari();

                URL url = null;
                try {
                    url = new URL("http://localhost:4444/wd/hub");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                new RemoteWebDriver(url, cap);


            default:
                WebDriverManager.chromedriver().setup();
                options = new ChromeOptions();
                if ("Y".equalsIgnoreCase(System.getenv("HEADLESS"))) {
                    options.addArguments("--headless");
                    options.addArguments("--disable-gpu");
                }

                return new ChromeDriver(options);
        }
    }
}
