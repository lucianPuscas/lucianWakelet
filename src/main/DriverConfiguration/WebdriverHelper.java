package DriverConfiguration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.support.PageFactory;

public class WebdriverHelper {

    public WebDriver driver;


    public WebdriverHelper(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public WebdriverHelper() {
    }


    /**
     * Create configurable webdriver instance to be used when defining browser to test on
     */
    public WebDriver createDriverInstance(String browserType) {
        if (BrowserType.CHROME.equals(browserType)) {
            System.setProperty("webdriver.chrome.driver", "WebDrivers\\chromedriver\\chromedriver.exe");
            driver = new ChromeDriver();

        } else if (BrowserType.FIREFOX.equals(browserType)) {
            System.setProperty("webdriver.gecko.driver", "WebDrivers\\geckodriver\\geckodriver.exe");
            driver = new FirefoxDriver();

        }
        driver.manage().window().maximize();
        return driver;

    }


    /**
     * Close used driver instance
     */
    public void closeDriverInstance() {
        driver.close();
    }

    /**
     * @param element - click one element form the DOM
     */
    public void clickElementFromPage(WebElement element) {
        element.click();
    }

    /**
     * Get test app
     *
     * @param webApp
     */
    public void getWebPage(String webApp) {
        driver.get(webApp);
    }


    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }
}