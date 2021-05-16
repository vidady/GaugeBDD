package web.steps;

import com.thoughtworks.gauge.Gauge;
import com.thoughtworks.gauge.Step;
import utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import web.pages.Homepage;

import static org.assertj.core.api.Assertions.assertThat;

public class HomepageSteps {
    Homepage hp=new Homepage();
    @Step("Go to Gauge Get Started Page")
    public void gotoGetStartedPage() {
        WebElement getStartedButton = Driver.getDriver().findElement(By.xpath("//a[@href='https://docs.gauge.org/getting_started/installing-gauge.html']"));
        hp.getGauge.click();

        Gauge.writeMessage("Page title is %s", Driver.getDriver().getTitle());
    }

    @Step("Ensure installation instructions are available")
    public void ensureInstallationInstructionsAreAvailable() {
        assertThat(hp.installationInstructions).isNotNull();
    }

    @Step("Open the Gauge homepage")
    public void implementation1() {
        String app_url = System.getenv("APP_URL");
        Driver.getDriver().get(app_url + "/");
        assertThat(Driver.getDriver().getTitle()).contains("Gauge");
    }

    @Step("Step Fail")
    public void stepFail(){
        assertThat(true).isEqualTo(false);
    }
}
