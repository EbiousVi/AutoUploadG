package project;

import project.autoUpload.AutoUpload;
import project.autoUpload.AutoUploadFactory;
import project.driver.Driver;
import org.openqa.selenium.WebDriver;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, "Windows-1251"))) {
            String type = getType(reader);
            AutoUpload autoUpload = AutoUploadFactory.getAutoUploadInstance(type);
            inputDirPath(reader, autoUpload);
            String URL = getUrl(reader);
            String option = getOption(reader);
            WebDriver driver = Driver.getChromeWebDriver(option);
            driver.get(URL);
            TimeUnit.MILLISECONDS.sleep(10000);
            autoUpload.execute(driver);
        } catch (IOException e) {
            System.out.println("ОШИБКА ВВОДА " + e.getMessage());
        }
    }

    private static String getUrl(BufferedReader reader) throws IOException {
        System.out.println("Введите ссылку на процедуру");
        return reader.readLine().trim();
    }

    private static String getOption(BufferedReader reader) throws IOException {
        List<String> options = Arrays.asList("win10", "win8", "linux");
        while (true) {
            System.out.println("Выберите пк для работы " + options);
            String option = reader.readLine().trim().toLowerCase();
            if (options.contains(option)) {
                return option;
            }
            System.out.println("Список доступных значений " + options);
        }
    }

    private static String getType(BufferedReader reader) throws IOException {
        List<String> types = Arrays.asList("бол", "мал", "мал3");
        while (true) {
            System.out.println("Выберите версию " + types);
            String type = reader.readLine().trim().toLowerCase();
            if (types.contains(type)) {
                return type;
            }
            System.out.println("Список доступных значений " + types);
        }
    }

    private static void inputDirPath(BufferedReader reader, AutoUpload autoUpload) throws IOException {
        while (true) {
            System.out.println("Введите путь к папке");
            String dirPath = reader.readLine().trim();
            boolean successWalked = autoUpload.getWalker().isSuccessWalked(dirPath);
            if (successWalked) {
                break;
            }
        }
    }
}
