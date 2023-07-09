package utils;

import dataobject.RunnerData;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class Runner {
    public WebDriver driver;
    public WebDriverWait wait;

    public WebDriver getDriverInstance(String browserName) {
        switch (browserName) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            case "internet explorer":
                WebDriverManager.iedriver().setup();
                driver = new InternetExplorerDriver();
                break;
            default:
                break;
        }

        return driver;
    }

    @BeforeMethod
    public void setUp() {
        driver = new Runner().getDriverInstance(RunnerData.browserName);

//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(RunnerData.implicitWaitDuration));
        wait = new WebDriverWait(driver, Duration.ofSeconds(RunnerData.explicitWaitDuration));
        driver.get(RunnerData.url);
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown() {
        driver.close();
        driver.quit();
    }
}
