package com.example.mealRecommend.util;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.nio.file.Path;
import java.nio.file.Paths;

public class WebDriverUtil {

    private static final Path BACKEND_DIR = Paths.get(System.getProperty("user.dir"));
    private static volatile boolean configured = false;

    public static WebDriver createChromeDriver() {
        return createChromeDriver(true);
    }

    public static WebDriver createChromeDriver(boolean headless) {
        configureDriver();

        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless=new", "--disable-gpu", "--no-sandbox", "--disable-dev-shm-usage");
        }
        options.addArguments("--remote-allow-origins=*");
        return new ChromeDriver(options);
    }

    private static synchronized void configureDriver() {
        if (configured) {
            return;
        }

        // Auto-download a ChromeDriver that matches the installed Chrome version.
        // Cached under BackEnd/drivers/ (ignore the old chromedriver.exe in BackEnd root if mismatched).
        Path driverCache = BACKEND_DIR.resolve("drivers");
        WebDriverManager.chromedriver()
                .cachePath(driverCache.toString())
                .setup();

        System.out.println("Using ChromeDriver: " + System.getProperty("webdriver.chrome.driver"));
        configured = true;
    }
}
