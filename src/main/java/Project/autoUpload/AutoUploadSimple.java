package Project.autoUpload;

import Project.filesWalker.FilesWalkerSmall;
import Project.driver.Driver;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class AutoUploadSimple extends AutoUpload {

    public static void main(String[] args) throws InterruptedException {
        AutoUpload autoUpload = new AutoUploadSimple();
        autoUpload.setDirPath(new FilesWalkerSmall());

        String url = autoUpload.setUrl();

        WebDriver driver = Driver.getChromeWebDriver();
        driver.get(url);
        TimeUnit.MILLISECONDS.sleep(10000);

        autoUpload.uploadTechPart(driver);
        autoUpload.uploadQualPart(driver);
        autoUpload.uploadCommFIle(driver);

        autoUpload.validation(driver);
    }
}