package Project.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Driver {
    public static WebDriver getChromeWebDriver() {
      /*  //Windows x64
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Google\\Chrome\\Application\\chromedriver.exe");
        System.setProperty("webdriver.chrome.binary", "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-data-dir=C:\\Users\\v\\AppData\\Local\\Google\\Chrome\\User Data");
        options.addArguments("profile-directory=Profile 1");*/

/*        //Windows x86
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
        System.setProperty("webdriver.chrome.binary", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-data-dir=C:\\Users\\Петров\\AppData\\Local\\Google\\Chrome\\User Data");
        options.addArguments("profile-directory=Profile 8");*/

        //Linux
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        System.setProperty("webdriver.chrome.binary", "/opt/google/chrome/google-chrome");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-data-dir=/home/v/.config/google-chrome");
        options.addArguments("profile-directory=Profile 1");
        return new ChromeDriver(options);
    }
}
