package Project.driver;

import Project.driver.options.OptionsTemplate;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Driver {
    public static WebDriver getChromeWebDriver(OptionsTemplate optionsTemplate) {
        return new ChromeDriver(optionsTemplate.getOptions());
    }
}
