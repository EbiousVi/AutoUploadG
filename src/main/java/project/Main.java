package project;

import org.fusesource.jansi.AnsiConsole;
import org.openqa.selenium.WebDriver;
import project.autoUpload.AutoUpload;
import project.autoUpload.AutoUploadFactory;
import project.driver.Driver;
import project.filesWalker.Directories;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;

public class Main {
    static {
        System.setProperty("webdriver.chrome.silentOutput", "true");
    }

    public static void main(String[] args) throws InterruptedException, UnsupportedEncodingException {
        PrintStream out = new PrintStream(System.out, true, "Cp1251");
        System.setOut(out);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, "Cp1251"))) {
            String type = getType(reader);
            Map<Directories, Boolean> parts = getParts(reader);
            AutoUpload autoUpload = AutoUploadFactory.getAutoUploadInstance(type, parts);
            prepareFilesToUpload(reader, autoUpload);
            String URL = getUrl(reader);
            String option = getOption(reader);
            WebDriver driver = Driver.getChromeWebDriver(option);
            driver.get(URL);
            TimeUnit.MILLISECONDS.sleep(15000);
            autoUpload.start(driver, parts);
        } catch (IOException e) {
            System.out.println("--> Ошибка ввода " + e.getMessage());
        }
    }

    static Map<Directories, Boolean> getParts(BufferedReader reader) throws IOException {
        Map<Directories, Boolean> parts = new HashMap<>();
        if (askYesOrNo(reader, "Загружать файлы во все части? Ответ [да, нет]")) {
            parts.put(Directories.TECH, true);
            parts.put(Directories.QUAL, true);
            parts.put(Directories.COMM, true);
        } else {
            while (true) {
                parts.put(Directories.TECH, askYesOrNo(reader, "Загружать файлы в Технику? Ответ [да, нет]"));
                parts.put(Directories.QUAL, askYesOrNo(reader, "Загружать файлы в Квалификацию? Ответ [да, нет]"));
                parts.put(Directories.COMM, askYesOrNo(reader, "Загружать файлы в Коммерческую? Ответ [да, нет]"));
                long allFalse = parts.values().stream().filter(b -> b.equals(false)).count();
                if (allFalse == 3) {
                    System.out.println("--> Выберите хотя бы одну часть для загрузки! Везде выбран ответ - нет");
                } else {
                    break;
                }
            }
        }
        return parts;
    }

    private static String getUrl(BufferedReader reader) throws IOException {
        while (true) {
            System.out.println("Введите ссылку на процедуру. После ввода, Браузер должен быть закрыть!");
            String URL = reader.readLine().trim();
            if (URL.startsWith("http")) {
                return URL;
            }
            System.out.println("--> Строка не соответствует формату ссылок. Должна начитаться с http...");
        }
    }

    private static String getOption(BufferedReader reader) throws IOException {
        List<String> options = Arrays.asList("вин10", "вин8", "линукс");
        while (true) {
            System.out.println("Выберите ОС для работы " + options);
            String option = reader.readLine().trim().toLowerCase();
            if (options.contains(option)) {
                return option;
            }
            System.out.println("--> Вы ввели <" + option + "> , Список доступных значений " + options);
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
            System.out.println("--> Вы ввели <" + type + "> , Список доступных значений " + types);
        }
    }

    private static void prepareFilesToUpload(BufferedReader reader, AutoUpload autoUpload) throws IOException {
        while (true) {
            System.out.println("Введите путь к папке");
            String dirPath = reader.readLine().replaceAll("\\\"|'", "").trim();
            if (dirPath.length() == 0) {
                System.out.println("--> Введена пустая строка!");
            }
            boolean successWalked = autoUpload.getWalker().isSuccessWalked(dirPath);
            if (successWalked) {
                break;
            }
        }
    }

    private static boolean askYesOrNo(BufferedReader reader, String message) throws IOException {
        while (true) {
            System.out.println(message);
            String input = reader.readLine().trim().toLowerCase();
            if (input.equals("да")) {
                return true;
            } else if (input.equals("нет")) {
                return false;
            }
            System.out.println("--> Введите да или нет! Вы ввели <" + input + ">");
        }
    }
}
