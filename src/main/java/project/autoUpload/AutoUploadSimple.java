package project.autoUpload;

import project.filesWalker.Directories;
import project.filesWalker.FilesWalker;
import org.openqa.selenium.WebDriver;

import java.util.Map;

public class AutoUploadSimple extends AutoUpload {

    public AutoUploadSimple(FilesWalker filesWalker) {
        super(filesWalker);
    }

    @Override
    public void start(WebDriver driver, Map<Directories, Boolean> parts) {
        try {
            if (parts.get(Directories.TECH)) executeTechPart(driver);
            if (parts.get(Directories.QUAL)) executeQualPart(driver);
            if (parts.get(Directories.COMM)) executeCommPart(driver);
            printResult(parts);
        } catch (InterruptedException e) {
            System.out.println("--> ВАЖНО! Сделайте скриншот ошибки, чтобы можно было помочь");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    @Override
    public void executeTechPart(WebDriver driver) throws InterruptedException {
        uploadTechPart(driver);
        countUploadedTechFiles(driver);
    }

    @Override
    public void executeQualPart(WebDriver driver) throws InterruptedException {
        uploadQualPart(driver);
        countUploadedQualFiles(driver);
    }

    @Override
    public void executeCommPart(WebDriver driver) throws InterruptedException {
        uploadCommFile(driver);
        countUploadedCommFiles(driver);

    }
}