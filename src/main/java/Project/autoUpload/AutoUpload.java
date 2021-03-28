package Project.autoUpload;

import Project.filesWalker.FilesWalker;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AutoUpload {
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private int downloadCount;
    private int recountUploadedFiles;
    private Map<String, String> techMap;
    private Map<String, String> qualMap;
    private Map<String, String> commMap;

    private void setTechMap(Map<String, String> techMap) {
        this.techMap = techMap;
    }

    private void setQualMap(Map<String, String> qualMap) {
        this.qualMap = qualMap;
    }

    private void setCommMap(Map<String, String> commMap) {
        this.commMap = commMap;
    }

    void validation(WebDriver driver) throws InterruptedException {
        countUploadedCommFiles(driver);
        countUploadedQualFiles(driver);
        countUploadedTechFiles(driver);
        printResult();
    }

     void countUploadedTechFiles(WebDriver driver) {
        List<WebElement> uploadedTechFiles = driver.findElements(By.xpath("//*[text() = 'Техническое предложение и иные документы']/ancestor::fieldset//a[contains(@href,'file')]"));
        recountUploadedFiles += uploadedTechFiles.size();
    }

     void countUploadedQualFiles(WebDriver driver) {
        List<WebElement> uploadedQualFiles = driver.findElements(By.xpath("//*[text() = 'Иные документы']/ancestor::fieldset//a[contains(@href,'file')]"));
        recountUploadedFiles += uploadedQualFiles.size();
    }

     void countUploadedCommFiles(WebDriver driver) {
        List<WebElement> uploadedCommFiles = driver.findElements(By.xpath("//fieldset[@id='price_offer_docs_wrapper_id']//a[contains(@href,'file')]"));
        recountUploadedFiles += uploadedCommFiles.size();
    }

     void printResult() {
        System.out.println("Maps.size - " + (techMap.size() + qualMap.size() + commMap.size()));
        System.out.println("Вовремя загрузки = " + downloadCount);
        System.out.println("После загрузки = " + recountUploadedFiles);
    }

    void clickToTransition(WebDriver driver, String xPath) throws InterruptedException {
        WebElement commPart = driver.findElement(By.xpath(xPath));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", commPart);
        TimeUnit.MILLISECONDS.sleep(3000);
    }

    void uploadCommFIle(WebDriver driver) throws InterruptedException {
        for (Map.Entry<String, String> pair : commMap.entrySet()) {
            WebElement commFileName = driver.findElement(By.xpath("//fieldset[@id='price_offer_docs_wrapper_id']//input[@type='text']"));
            WebElement commFile = driver.findElement(By.xpath("//fieldset[@id='price_offer_docs_wrapper_id']//input[@type='file'][@class='x-form-file']"));
            commFileName.sendKeys(pair.getValue());
            TimeUnit.MILLISECONDS.sleep(1000);
            commFile.sendKeys(pair.getKey());
            waiting(driver);
        }
    }

    void uploadQualPart(WebDriver driver) throws InterruptedException {
        for (Map.Entry<String, String> pair : qualMap.entrySet()) {
            WebElement qualFileName = driver.findElement(By.xpath("//*[text() = 'Иные документы']/ancestor::fieldset//input[@type='text']"));
            WebElement qualFile = driver.findElement(By.xpath("//*[text() = 'Иные документы']/ancestor::fieldset//input[@type='file']"));
            qualFileName.sendKeys(pair.getValue());
            TimeUnit.MILLISECONDS.sleep(1000);
            qualFile.sendKeys(pair.getKey());
            waiting(driver);
        }
    }

    void uploadTechPart(WebDriver driver) throws InterruptedException {
        for (Map.Entry<String, String> pair : techMap.entrySet()) {
            WebElement techFileName = driver.findElement(By.xpath("//*[text() = 'Техническое предложение и иные документы']/ancestor::fieldset//input[@type='text']"));
            WebElement techFile = driver.findElement(By.xpath("//*[text() = 'Техническое предложение и иные документы']/ancestor::fieldset//input[@type='file'][@class='x-form-file']"));
            techFileName.sendKeys(pair.getValue());
            TimeUnit.MILLISECONDS.sleep(1000);
            techFile.sendKeys(pair.getKey());
            waiting(driver);
        }
    }

    private void waiting(WebDriver driver) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 75, 2000);
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//*[text() = 'Загрузка файла']"), "Загрузка файла"));
        downloadCount++;
        TimeUnit.MILLISECONDS.sleep(1000);
    }

    void setDirPath(FilesWalker walker) {
        try {
            System.out.println("Путь к директории");
            String dirPath = reader.readLine();
            walker.walk(dirPath, walker);
            setTechMap(FilesWalker.techMap);
            setQualMap(FilesWalker.qualMap);
            setCommMap(FilesWalker.commMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String setUrl() {
        try {
            System.out.println("Ссылка на процедуру");
            String url = reader.readLine();
            reader.close();
            return url;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("CAN'T RETURN URL");
        }
    }
}
