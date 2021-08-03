package project.driver;

import project.driver.options.LINUXOptions;
import project.driver.options.Options;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import project.driver.options.WIN10Options;
import project.driver.options.WIN8Options;

import java.util.HashMap;
import java.util.Map;

public class Driver {
    private static final String WIN10 = "вин10";
    private static final String WIN8 = "вин8";
    private static final String LINUX = "линукс";
    private static final Map<String, Options> options = new HashMap<>();

    public static WebDriver getChromeWebDriver(String option) {
        if (options.isEmpty()) {
            options.put(WIN10, new WIN10Options());
            options.put(WIN8, new WIN8Options());
            options.put(LINUX, new LINUXOptions());
        }

        if (options.containsKey(option)) {
            return new ChromeDriver(options.get(option).getChromeOption());
        }
        throw new RuntimeException("--> Нет подходящего файла конфигурации для ОС");
    }
}
