package Project;

import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class AutoUploadSimple extends AutoUpload {

    public static void main(String[] args) throws InterruptedException {
        AutoUpload autoUpload = new AutoUpload();
        autoUpload.setDirPath(new FilesWalkerSmall());

        String url = autoUpload.setUrl();

        WebDriver driver = Driver.getChromeWebDriver();
        driver.get(url);
        TimeUnit.MILLISECONDS.sleep(1000);

        autoUpload.uploadTechPart(driver);
        autoUpload.uploadQualPart(driver);
        autoUpload.uploadCommFIle(driver);

        autoUpload.validation(driver);
    }
}