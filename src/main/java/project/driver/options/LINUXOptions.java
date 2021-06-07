package project.driver.options;

import org.openqa.selenium.chrome.ChromeOptions;

public class LINUXOptions implements Options {
    @Override
    public ChromeOptions getChromeOption() {
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        System.setProperty("webdriver.chrome.binary", "/opt/google/chrome/google-chrome");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-data-dir=/home/v/.config/google-chrome");
        options.addArguments("profile-directory=Profile 1");
        return options;
    }
}
