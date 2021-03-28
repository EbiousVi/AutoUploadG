package Project.autoUpload;

import Project.filesWalker.FilesWalkerBig;
import Project.driver.Driver;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class AutoUploadTriple extends AutoUpload {
    private static final String techXpath = "//*[contains(text(), 'Первая часть заявки')]";
    private static final String qualXpath = "//*[contains(text(), 'Вторая часть заявки')]";
    private static final String commXpath = "//*[contains(text(), 'Ценовое предложение')]";

    @Override
    void validation(WebDriver driver) throws InterruptedException {
        countUploadedCommFiles(driver);
        clickToTransition(driver, qualXpath);
        countUploadedQualFiles(driver);
        clickToTransition(driver, techXpath);
        countUploadedTechFiles(driver);
        printResult();
    }

    public static void main(String[] args) throws InterruptedException {
        AutoUpload autoUpload = new AutoUpload();
        autoUpload.setDirPath(new FilesWalkerBig());
        String url = autoUpload.setUrl();

        WebDriver driver = Driver.getChromeWebDriver();
        driver.get(url);
        TimeUnit.MILLISECONDS.sleep(1000);

        autoUpload.uploadTechPart(driver);
        autoUpload.clickToTransition(driver, qualXpath);
        autoUpload.uploadQualPart(driver);
        autoUpload.clickToTransition(driver, commXpath);
        autoUpload.uploadCommFIle(driver);
        autoUpload.validation(driver);
    }
}
