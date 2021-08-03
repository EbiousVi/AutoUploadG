package project.autoUpload;

import project.filesWalker.Directories;
import project.filesWalker.FilesWalker;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public abstract class AutoUpload {
    private final FilesWalker walker;
    private int countFilesBeforeUpload;
    private int countFilesDuringUpload;
    private int countFilesAfterUpload;

    public AutoUpload(FilesWalker walker) {
        this.walker = walker;
    }

    public FilesWalker getWalker() {
        return walker;
    }

    public abstract void start(WebDriver driver, Map<Directories, Boolean> parts) throws InterruptedException;

    public abstract void executeTechPart(WebDriver driver) throws InterruptedException;

    public abstract void executeQualPart(WebDriver driver) throws InterruptedException;

    public abstract void executeCommPart(WebDriver driver) throws InterruptedException;

    void countUploadedTechFiles(WebDriver driver) {
        List<WebElement> uploadedTechFiles = driver.findElements(By.xpath("//*[text() = 'Техническое предложение и иные документы']/ancestor::fieldset//a[contains(@href,'file')]"));
        countFilesAfterUpload += uploadedTechFiles.size();
    }

    void countUploadedQualFiles(WebDriver driver) {
        List<WebElement> uploadedQualFiles = driver.findElements(By.xpath("//*[text() = 'Иные документы']/ancestor::fieldset//a[contains(@href,'file')]"));
        countFilesAfterUpload += uploadedQualFiles.size();
    }

    void countUploadedCommFiles(WebDriver driver) {
        List<WebElement> uploadedCommFiles = driver.findElements(By.xpath("//fieldset[@id='price_offer_docs_wrapper_id']//a[contains(@href,'file')]"));
        countFilesAfterUpload += uploadedCommFiles.size();
    }

    void printResult(Map<Directories, Boolean> parts) {
        if (parts.get(Directories.TECH)) countFilesBeforeUpload += walker.techMap.size();
        if (parts.get(Directories.QUAL)) countFilesBeforeUpload += walker.qualMap.size();
        if (parts.get(Directories.COMM)) countFilesBeforeUpload += walker.commMap.size();
        System.out.println();
        System.out.println("Перед загрузкой = " + countFilesBeforeUpload);
        System.out.println("Загружено = " + countFilesDuringUpload);
        System.out.println("После загрузки = " + countFilesAfterUpload);
        System.out.println();
    }

    void clickToTransition(WebDriver driver, String xPath) throws InterruptedException {
        WebElement clickableElem = driver.findElement(By.xpath(xPath));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", clickableElem);
        TimeUnit.MILLISECONDS.sleep(3000);
    }

    void uploadCommFile(WebDriver driver) throws InterruptedException {
        for (Map.Entry<String, String> pair : walker.commMap.entrySet()) {
            WebElement commFileName = driver.findElement(By.xpath("//fieldset[@id='price_offer_docs_wrapper_id']//input[@type='text']"));
            WebElement commFile = driver.findElement(By.xpath("//fieldset[@id='price_offer_docs_wrapper_id']//input[@type='file'][@class='x-form-file']"));
            commFileName.sendKeys(pair.getValue());
            TimeUnit.MILLISECONDS.sleep(1000);
            commFile.sendKeys(pair.getKey());
            waiting(driver);
        }
    }

    void uploadQualPart(WebDriver driver) throws InterruptedException {
        for (Map.Entry<String, String> pair : walker.qualMap.entrySet()) {
            WebElement qualFileName = driver.findElement(By.xpath("//*[text() = 'Иные документы']/ancestor::fieldset//input[@type='text']"));
            WebElement qualFile = driver.findElement(By.xpath("//*[text() = 'Иные документы']/ancestor::fieldset//input[@type='file']"));
            qualFileName.sendKeys(pair.getValue());
            TimeUnit.MILLISECONDS.sleep(1000);
            qualFile.sendKeys(pair.getKey());
            waiting(driver);
        }
    }

    void uploadTechPart(WebDriver driver) throws InterruptedException {
        for (Map.Entry<String, String> pair : walker.techMap.entrySet()) {
            WebElement techFileName = driver.findElement(By.xpath("//*[text() = 'Техническое предложение и иные документы']/ancestor::fieldset//input[@type='text']"));
            WebElement techFile = driver.findElement(By.xpath("//*[text() = 'Техническое предложение и иные документы']/ancestor::fieldset//input[@type='file'][@class='x-form-file']"));
            techFileName.sendKeys(pair.getValue());
            TimeUnit.MILLISECONDS.sleep(1000);
            techFile.sendKeys(pair.getKey());
            waiting(driver);
        }
    }

    private void waiting(WebDriver driver) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 120, 2000);
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//*[text() = 'Загрузка файла']"), "Загрузка файла"));
        countFilesDuringUpload++;
        TimeUnit.MILLISECONDS.sleep(1000);
    }
}
