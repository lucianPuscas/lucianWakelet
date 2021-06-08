package DriverConfiguration;

import com.gargoylesoftware.htmlunit.javascript.host.dom.ShadowRoot;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.boris.pecoff4j.PE;
import org.boris.pecoff4j.ResourceDirectory;
import org.boris.pecoff4j.ResourceEntry;
import org.boris.pecoff4j.constant.ResourceType;
import org.boris.pecoff4j.io.PEParser;
import org.boris.pecoff4j.io.ResourceParser;
import org.boris.pecoff4j.resources.StringFileInfo;
import org.boris.pecoff4j.resources.StringTable;
import org.boris.pecoff4j.resources.VersionInfo;
import org.boris.pecoff4j.util.ResourceHelper;
import org.json.simple.JSONObject;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static com.github.webdriverextensions.Bot.*;
import static com.github.webdriverextensions.WebDriverExtensionsContext.setDriver;
import static java.lang.Integer.parseInt;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;


public class DriverHelper extends EnvironmentConfig {
    public WebDriverWait wait = new WebDriverWait(driver(), 2);
    public String stagingWakelet = getTestUrl("wakeletStaging");

    private Object InternetExplorer;

    public static final String USERNAME = "wakelet_GYv1ES";
    public static final String AUTOMATE_KEY = "pLkxwVxqzpDxUwiXHqqX";
    public static final String remoteUrl = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";
    public static final String environmentSettings = "{  \"project\": \"Wakelet production\",\n" +
            "    \"build\": \"Wakelet Production tests\",\n" +
            "    \"name\": \"Home Page Tests\"}";


    public String getTestUrl(String configKey) {

        JSONObject config = getConfig();

        return (String) config.get(configKey);
    }

    //region Enter Password to decrypt site
    // @FindBy(how = How.NAME, using = "password")
    // private WebElement daedalusPassword;
    //
    // @FindBy(how = How.CSS, using = "button")
    // private WebElement passwordSubmitBtn;
    //endregion

    /**
     * CLose appium web page - app on mobile
     */
    public void closeAppiumInstance(WebDriver driver) {
        driver.quit();
    }


    /**
     * Close used driver instance
     */
    public void closeDriverInstance() {
        driver().quit();
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
    void getWebPage(String webApp) {
        driver().navigate().to(webApp);
    }


    public void getDriver(EdgeOptions webdriver) {
        setDriver(new EdgeDriver(webdriver));
    }


    public void WaitForBrowser() {
        new WebDriverWait(driver(), 30);
    }

    /**
     * Helper method to perform clicks on the page when element is not fully accessible
     * As a last resort the click will be performed using javascript actions
     *
     * @param element Webelement to be clicked
     */
    public void clickElement(WebElement element, boolean... checkVisibility) {
        try {
            if (checkVisibility.length < 1 || checkVisibility[0]) {
                waitForElement(element);
            }
            click(element);
        } catch (Exception ex) {
            ex.getMessage();
            JavascriptExecutor js = (JavascriptExecutor) driver();
            js.executeScript("arguments[0].click()", element);
        }
    }

    /**
     * @param element - wait until webelement is visible
     */
    public void waitForElement(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Perform setup depending on browser chosen to run scripts
     */
    public void setup() {
        String webApp;
        String wakelet = System.getProperty("wakelet.url");
        if (wakelet != null && wakelet != "") {
            webApp = wakelet;
        } else {
            webApp = this.stagingWakelet;
        }
        open(webApp);
        driver().manage().window().maximize();
    }

    public void verifyForBrokenLinks(String linkUrl) {
        try {
            URL url = new URL(linkUrl);

            HttpURLConnection httpURLConnect = (HttpURLConnection) url.openConnection();

            httpURLConnect.setConnectTimeout(3000);

            httpURLConnect.connect();

            if (httpURLConnect.getResponseCode() == 200) {
                System.out.println(linkUrl + " - " + httpURLConnect.getResponseMessage());
            } else if (httpURLConnect.getResponseCode() != 200) {
                System.out.println(linkUrl + " - " + httpURLConnect.getResponseMessage());
                System.out.println(linkUrl + " - " + httpURLConnect.getResponseCode());
            }
            if (httpURLConnect.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND) {
                System.out.println(linkUrl + " - " + httpURLConnect.getResponseMessage() + " - " + HttpURLConnection.HTTP_NOT_FOUND);
            }
            assertTrue(httpURLConnect.getResponseCode() == 200);
        } catch (Exception e) {
            e.getMessage();
            System.out.println(e.getMessage());
        }
    }

    /**
     * Verify if a link works or is broken on the page
     *
     * @param linkUrl - link of the url to be verified
     */
    public static void verifyLinkActive(String linkUrl) {
        try {
            URL url = new URL(linkUrl);

            HttpURLConnection httpURLConnect = (HttpURLConnection) url.openConnection();

            httpURLConnect.setConnectTimeout(3000);

            httpURLConnect.connect();

            if (httpURLConnect.getResponseCode() == 200) {
                System.out.println(linkUrl + " - " + httpURLConnect.getResponseMessage());
            } else if (httpURLConnect.getResponseCode() != 200) {
                System.out.println(linkUrl + " - " + httpURLConnect.getResponseMessage());
                System.out.println(linkUrl + " - " + httpURLConnect.getResponseCode());
            }
            if (httpURLConnect.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND) {
                System.out.println(linkUrl + " - " + httpURLConnect.getResponseMessage() + " - " + HttpURLConnection.HTTP_NOT_FOUND);
            }
        } catch (Exception e) {
            e.getMessage();
            System.out.println(e.getMessage());
        }
    }

    /**
     * Get the link status of a list of links present on the site
     *
     * @param linkList - links list to be verified
     */
    public void getLinksStatus(List<WebElement> linkList) {
        String url = null;
        System.out.println("Number of verified links is: " + linkList.size());
        for (int j = 0; j < linkList.size(); j++) {
            scrollToCenter(linkList.get(j));
            WebElement link = linkList.get(j);

            url = link.getAttribute("href");

            verifyLinkActive(url);
        }
    }

    /**
     * Get the link from a particular web element in order to use it or manipulate it in tests
     *
     * @param linkElement - Link to be get Href from
     * @return - url of the particular web element under test
     */
    public String getLinkUrl(WebElement linkElement) {
        String url;
        url = linkElement.getAttribute("href");
        return url;
    }

    /**
     * Verify that a page is displayed in a new tab
     *
     * @param titlePage -title of the page to be opened in a new tab
     */
    public void checkForNewTab(String titlePage, String url) {
        //get window handlers as list
        waitForPageToLoad();
        if (browserIsFirefox() || browserIsEdge()) {
            waitFor(5);
        } else {
            waitFor(2);
        }
        // Find a list of open windows and get the window handles
        List<String> browserTabs = new ArrayList<String>(driver().getWindowHandles());
        //switch to new tab
        driver().switchTo().window(browserTabs.get(1));
        //check is it correct page opened or not (e.g. check page's title)
        assertEqualsIgnoreCaseAndWhitespace(driver().getTitle(), titlePage);
        //assertTrue(driver().getTitle().equalsIgnoreCase(titlePage));
        assertTrue(driver().getCurrentUrl().equalsIgnoreCase(url));
        //then close tab and get back
        driver().close();
        driver().switchTo().window(browserTabs.get(0));
    }


    /**
     * Verifies text without spaces
     *
     * @param expected - expected text to be verified
     * @param actual   - actual text to verify against
     */
    public static void assertEqualsIgnoreCaseAndWhitespace(String expected, String actual) {
        assertEquals(expected.replaceAll("\n", " ")
                        .replaceAll("\\s+", " ").trim().toLowerCase(),
                actual.replaceAll("\n", " ")
                        .replaceAll("\\s+", " ").trim().toLowerCase());
    }

    /**
     * @param cardTOVerify - speaker card to be verified
     * @param linkToVerify - link on the card to be verified
     */
    public void verifyLinksOnCards(List<WebElement> cardTOVerify, List<WebElement> linkToVerify) {
        int numberOfCards = cardTOVerify.size();
        for (int i = 0; i < numberOfCards; i++) {
            assertTrue(cardTOVerify.get(i).isDisplayed());
            assertTrue(linkToVerify.get(i).isDisplayed());
            assertTrue(linkToVerify.get(i).isEnabled());
        }
        for (int j = 0; j < linkToVerify.size(); j++) {

            WebElement link = linkToVerify.get(j);

            String url = link.getAttribute("href");

            verifyLinkActive(url);
        }
    }

    /**
     * Check and verify that a list of radio buttons are selected correctly
     *
     * @param radioBtns - List of radio buttons to be checked and verified
     */
    public void verifyRadioButtonIsChecked(List<WebElement> radioBtns) {
        for (int i = 0; i < radioBtns.size(); i++) {
            clickElement(radioBtns.get(i), false);
            waitFor(2);
            assertIsSelected(radioBtns.get(i));
        }
    }

    /**
     * @param downloadPath - download path where the file is located
     * @param fileName     - the filename present on the hard drive after download
     * @return - true/false based on the file being present on the hard drive
     */
    public boolean isFileDownloaded(String downloadPath, String fileName) {
        File dir = new File(downloadPath);
        File[] dirContents = dir.listFiles();

        assert dirContents != null;
        for (int i = 0; i < dirContents.length; i++) {
            if (dirContents[i].getName().equals(fileName)) {

                // Verify that the correct file is downloaded and present
                assertTrue(dirContents[i].getName().equals(fileName));
                assertEquals("The file does not exist", dirContents[i].getName(), fileName);

                // File has been found, it can now be deleted:
                waitFor(5);
                dirContents[i].delete();
                return true;
            }

        }
        return false;
    }

    /**
     * Verify that the correct product version is displayed
     * Product version of the downloaded wallet is the same as specified on the website
     */
    public void verifyDownloadVersion(String nameOfFile) throws IOException {
        PE pe = PEParser.parse(nameOfFile);
        ResourceDirectory rd = pe.getImageData().getResourceTable();

        ResourceEntry[] entries = ResourceHelper.findResources(rd, ResourceType.VERSION_INFO);
        for (int i = 0; i < entries.length; i++) {
            byte[] data = entries[i].getData();
            VersionInfo version = ResourceParser.readVersionInfo(data);

            StringFileInfo strings = version.getStringFileInfo();
            StringTable table = strings.getTable(0);
            for (int j = 0; j < table.getCount(); j++) {
                String key = table.getString(j).getKey();
                String value = table.getString(j).getValue();
                System.out.println(key + " = " + value);
            }
        }
    }

    /**
     * Helper method that allows user to accept the prompt to save a file on different browsers
     *
     * @throws AWTException - Robot class cannot perform action on site
     */
    public void saveFileToDisk(String OS) throws AWTException {
        Robot robot = new Robot();
        if (browserIsInternetExplorer()) {
            robot.keyPress(KeyEvent.VK_ALT);
            robot.keyPress(KeyEvent.VK_S);
            robot.keyRelease(KeyEvent.VK_ALT);
        }
        if (browserIsFirefox() && OS.equalsIgnoreCase("Windows")) {
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyPress(KeyEvent.VK_ENTER);

        } else if (browserIsFirefox() && (OS.equalsIgnoreCase("Mac") || OS.equalsIgnoreCase("Linux"))) {
            waitFor(5);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        }

        if (browserIsChrome() && OS.equalsIgnoreCase("Windows")) {
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_J);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_J);
            waitFor(3);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            waitFor(2);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            robot.keyPress(KeyEvent.VK_ENTER);
            waitFor(60);
        }
    }

    /**
     * Helper method that allows user to accept the prompt and save the PGP text file under different browsers
     *
     * @param OS - Os to download PGP signature for
     * @throws AWTException - exception raised by robot class not being able to perform action
     */
    public void savePGPFileToDisk(String OS) throws AWTException {
        Robot robot = new Robot();
        if (browserIsFirefox() && (OS.equalsIgnoreCase("Windows") || OS.equalsIgnoreCase("MAC") || OS.equalsIgnoreCase("Linux"))) {
            robot.keyPress(KeyEvent.VK_ALT);
            robot.keyPress(KeyEvent.VK_S);
            robot.keyRelease(KeyEvent.VK_ALT);
            robot.keyRelease(KeyEvent.VK_S);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        }
    }

    /**
     * Helper method to help clicking/switching to elements in IE 11
     *
     * @param element - element to perform click on
     */
    public void clickElementAndWait(WebElement element) {
        try {
            scrollToCenter(element);
            if (browserIsInternetExplorer() || browserIsChrome() || browserIsSafari()) {
                element.sendKeys(Keys.ENTER);
            } else {
                click(element);
            }
        } catch (Exception ex) {
            ex.getMessage();
            JavascriptExecutor js = (JavascriptExecutor) driver();
            js.executeScript("arguments[0].click()", element);
        }
        waitFor(3);
    }

    /**
     * Get headers for download urls, making sure that the correct information is present
     * Making sure that the link works properly when user clicks it to download
     *
     * @param url - url to verify headers for
     */
    public void getHeaders(String url, String section) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpHead httphead = new HttpHead(url);
        System.out.println("Requesting : " + httphead.getURI());

        try {
            HttpResponse response = httpclient.execute(httphead);
            System.out.println("Protocol : " + response.getProtocolVersion());
            System.out.println("Status code : " + response.getStatusLine().getStatusCode());
            System.out.println("Status line : " + response.getStatusLine());

            //Provide what header content we need value for
            String contentLength = "Content-Length";
            String contentType = "Content-Type";
            String statusCode = "Status Code";

            //Get headers
            //Get particular header value
            Header[] headers = response.getAllHeaders();
            String contentLengthResult = fetchValue(contentLength, headers);
            String contentTypeResult = fetchValue(contentType, headers);
            int statusCodeResult = response.getStatusLine().getStatusCode();

            //Verify that the link is accessible
            assertEquals(200, statusCodeResult);

            if (section.equalsIgnoreCase("mediaKit")) {
                //Assert that the correct information is displayed in the headers Mac download
                if (contentLengthResult != null)
                    assertTrue(parseInt(contentLengthResult) > 1);
                if (contentTypeResult != null)
                    assertTrue(contentTypeResult.equalsIgnoreCase("application/zip"));
            }
            if (section.equalsIgnoreCase("pdf")) {
                if (contentLengthResult != null) {
                    assertTrue(parseInt(contentLengthResult) > 1);
                }
                if (contentTypeResult != null) {
                    if (contentTypeResult.equalsIgnoreCase("application/pdf"))
                        assertTrue(contentTypeResult.equalsIgnoreCase("application/pdf"));
                } else {
                    if (contentTypeResult != null) ;
                    assertTrue(contentTypeResult.equalsIgnoreCase("text/html"));
                }
            }
//            }
//            if (os.equalsIgnoreCase("linux")) {
//                Assert that the correct information is displayed in the headers Linux download
//                String contentLengthLinux = fetchValue(contentLength, headers);
//                String contentTypeLinux = fetchValue(contentType, headers);
//                assertTrue(parseInt(contentLengthLinux) > 1);
//                assertTrue(contentTypeLinux.equalsIgnoreCase("binary/octet-stream"));
//            }
//            if (os.equalsIgnoreCase("windows")) {
//                String contentLengthWindows = fetchValue(contentLength, headers);
//                String contentTypeWindows = fetchValue(contentType, headers);
//                assertTrue(parseInt(contentLengthWindows) > 1);
//                assertTrue(contentTypeWindows.equalsIgnoreCase("binary/octet-stream"));
//            }
            System.out.println("\n\nResponse : ");
            if (response.getEntity() != null) {
                System.out.println(EntityUtils.toString(response.getEntity()));
            } else {
                System.out.println("As expected no response body for HEAD request");
            }

        } catch (
                IOException e) {
            e.printStackTrace();
        } finally {
            httpclient.getConnectionManager().shutdown();
        }

    }

    /**
     * Get header value based on header name
     *
     * @param key     - header name to be fetched
     * @param headers - headers displayed on particular link
     * @return - header value for a particular header name
     */
    private String fetchValue(String key, Header[] headers) {
        for (Header header : headers) {
            if (header.getName().equals(key)) {
                return header.getValue();
            }
        }
        return null;
    }

    /**
     * Verify that the correct url is displayed after navigation action item
     *
     * @param url - expected url to be displayed to the user after a particular action
     */
    public void verifyPageUrl(String url) {
        assertCurrentUrlContains(url);
        driver().navigate().back();
        waitForPageToLoad();


//        assertTrue(driver().getCurrentUrl().contains(url));
    }

    /**
     * Verify that user is redirected to the correct page
     *
     * @param url   - url of the active page
     * @param title - title of active page
     */
    public void verifyPageUrlAndTitle(String url, String title) {
        assertTrue(driver().getCurrentUrl().equalsIgnoreCase(url));
        assertTrue(driver().getTitle().equalsIgnoreCase(title));
    }

    /**
     * Verify that a particular url is displayed in a new tab when clicked
     */
    public void assertNewTabOpen(WebElement link, String url) {
        //get window handlers as list
        waitForPageToLoad(15);
        // assertTrue(link.getAttribute("target").equalsIgnoreCase("_blank"));
        // Find a list of open windows and get the window handles
        waitUntil(webDriver -> {
            ;
            return driver().getWindowHandles().size() == 2;
        }, 15);
        String oldTab = driver().getWindowHandle();
        String newTab = driver().getWindowHandles().stream()
                .filter(s -> !s.equals(oldTab))
                .findFirst()
                .orElse(null);
        driver().switchTo().window(newTab);
        String secure = "https://";
        String unsecured = "http://";

        if (!url.contains(secure)) {
            url = driver().getCurrentUrl().replace(secure, "");
            assertCurrentUrlContains(url);
        } else if (!url.contains(unsecured)) {
            url = driver().getCurrentUrl().replace(unsecured, "");
            assertCurrentUrlContains(url);
        }
        if (!url.contains("www.")) {
            url = driver().getCurrentUrl().replace("www.", "");
            assertTrue(driver().getCurrentUrl().replace("www.", "").contains(url));
        } else {
            assertCurrentUrlContains(url);
        }
        //then close tab and get back
        driver().close();
        driver().switchTo().window(oldTab);
    }

    /**
     * Verify if the images are shown correctly/not broken on the page
     *
     * @param image - image to be verified if it is displayed on a page or not
     * @return - return the result of the picture, shown or not shown
     */
    public boolean areImagesVisible(WebElement image) {
        Boolean result = null;
        if (browserIsInternetExplorer() || browserIsEdge()) {
            result = (Boolean) ((JavascriptExecutor) driver()).executeScript("return arguments[0].complete;", image);
        } else { //other browser types use diff method to check
            result = (Boolean) ((JavascriptExecutor) driver()).executeScript("return (typeof arguments[0].naturalWidth!=\"undefined\" && arguments[0].naturalWidth>0);", image);
        }
        return result;
    }

    /**
     * Fluent wait for page to load
     *
     * @param timeToWait   - time to wait for the element to be displayed
     * @param timeInterval - pooling time interval - at how many seconds to retry to load
     */
    public void fluentWaitForPageElement(Duration timeToWait, Duration timeInterval, WebElement element) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver())
                .withTimeout(timeToWait)
                .pollingEvery(timeInterval)
                .ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.visibilityOf(element));
    }


    //region Wakelet extract selectors


    @FindBy(how = How.TAG_NAME, using = "my-app")
    public WebElement homePage;
    @FindBy(how = How.TAG_NAME, using = "wk-ui-auth-app")
    public WebElement authPage;


    public WebElement extractElement(String elementCss) {
        WebElement shadowRoot1 = expandRootElement(homePage);
        WebElement root2 = shadowRoot1.findElement(By.cssSelector(".iron-lazy-selected"));
        WebElement shadowRoot2 = expandRootElement(root2);
        return shadowRoot2.findElement(By.cssSelector(elementCss));
    }


    public WebElement expandRootElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver();
        return (WebElement) js.executeScript("return arguments[0].shadowRoot", element);
    }

    public List<WebElement> authPageExtractElements(String selectorCss) {
        WebElement shadowRoot1 = expandRootElement(authPage);
        WebElement root2 = shadowRoot1.findElement(By.cssSelector(".page"));
        WebElement shadowRoot2 = expandRootElement(root2);
        WebElement root3 = shadowRoot2.findElement(By.cssSelector(".relative.p--xxl"));
        WebElement shadowRoot3 = expandRootElement(root3);
        return shadowRoot3.findElements(By.cssSelector(selectorCss));

    }


    public WebElement authPageExtractElement(String selectorCss) {
        WebElement shadowRoot1 = expandRootElement(authPage);
        WebElement root2 = shadowRoot1.findElement(By.cssSelector(".page"));
        WebElement shadowRoot2 = expandRootElement(root2);
        WebElement root3 = shadowRoot2.findElement(By.cssSelector(".relative.p--xxl"));
        WebElement shadowRoot3 = expandRootElement(root3);
        return shadowRoot3.findElement(By.cssSelector(selectorCss));

    }

    public WebElement authPageExtractSocialMediaElement(String shadowTagName, String selectorCss) {
        WebElement shadowRoot1 = expandRootElement(authPage);
        WebElement root2 = shadowRoot1.findElement(By.cssSelector(".page"));
        WebElement shadowRoot2 = expandRootElement(root2);
        WebElement root3 = shadowRoot2.findElement(By.cssSelector(".relative.p--xxl"));
        WebElement shadowRoot3 = expandRootElement(root3);
        WebElement root4 = shadowRoot3.findElement(By.cssSelector("wk-ui-social-login"));
        WebElement shadowRoot4 = expandRootElement(root4);
        WebElement root5 = shadowRoot4.findElement(By.cssSelector(shadowTagName));
        WebElement shadowRoot5 = expandRootElement(root5);
        return shadowRoot5.findElement(By.cssSelector(selectorCss));

    }


    //endregion


    public void assertNewTabOpenSocial(String url) {
        //get window handlers as list
        waitForPageToLoad(15);
        waitUntil(webDriver -> {
            return driver().getWindowHandles().size() == 2;
        }, 15);
        String oldTab = driver().getWindowHandle();
        String newTab = driver().getWindowHandles().stream()
                .filter(s -> !s.equals(oldTab))
                .findFirst()
                .orElse(null);
        driver().switchTo().window(newTab);
        assertCurrentUrlContains(url);
        driver().close();
        driver().switchTo().window(oldTab);
    }

}





