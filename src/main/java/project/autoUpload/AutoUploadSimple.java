package project.autoUpload;

import project.filesWalker.FilesWalker;
import org.openqa.selenium.WebDriver;

public class AutoUploadSimple extends AutoUpload {

    public AutoUploadSimple(FilesWalker filesWalker) {
        super(filesWalker);
    }

    @Override
    public void execute(WebDriver driver) {
        try {
            uploadTechPart(driver);
            uploadQualPart(driver);
            uploadCommFIle(driver);
            validation(driver);
        } catch (InterruptedException e) {
            System.out.println("Ошибка звони создателю и не забудь скрин ошибки приложить!");
            System.out.println(e.getMessage());
        }
    }

    @Override
    void validation(WebDriver driver) {
        countUploadedCommFiles(driver);
        countUploadedQualFiles(driver);
        countUploadedTechFiles(driver);
        printResult();
    }
}