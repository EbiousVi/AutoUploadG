package project.autoUpload;

import project.filesWalker.FilesWalker;
import org.openqa.selenium.WebDriver;

public class AutoUploadTriple extends AutoUpload {
    private static final String techXpath = "//*[contains(text(), 'Первая часть заявки')]";
    private static final String qualXpath = "//*[contains(text(), 'Вторая часть заявки')]";
    private static final String commXpath = "//*[contains(text(), 'Ценовое предложение')]";

    public AutoUploadTriple(FilesWalker filesWalker) {
        super(filesWalker);
    }

    @Override
    public void execute(WebDriver driver) {
        try {
            uploadTechPart(driver);
            clickToTransition(driver, qualXpath);
            uploadQualPart(driver);
            clickToTransition(driver, commXpath);
            uploadCommFIle(driver);
            validation(driver);
        } catch (InterruptedException e) {
            System.out.println("Ошибка звони создателю и не забудь скрин ошибки приложить!");
            System.err.println(e.getMessage());
        }
    }

    @Override
    void validation(WebDriver driver) {
        try {
            countUploadedCommFiles(driver);
            clickToTransition(driver, qualXpath);
            countUploadedQualFiles(driver);
            clickToTransition(driver, techXpath);
            countUploadedTechFiles(driver);
            printResult();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
