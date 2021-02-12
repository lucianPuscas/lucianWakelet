import DriverConfiguration.DriverHelper;
import WakeletPages.WakeletPage;
import com.github.webdriverextensions.junitrunner.WebDriverRunner;
import com.github.webdriverextensions.junitrunner.annotations.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.openqa.selenium.Platform;

/*
//region BrowserConfiguration
@RunWith(WebDriverRunner.class)
@RemoteAddress(DriverHelper.remoteUrl)
@DisplayName("Iohk.io Blog Page tests")
@Browsers(
        chrome = {
                @Chrome(version = "83", platform = Platform.MAC, desiredCapabilities = WakeletPageTest.environmentSettings),
                @Chrome(version = "83", platform = Platform.WINDOWS, desiredCapabilities = WakeletPageTest.environmentSettings),
        },
        firefox = {
                @Firefox(version = "77", platform = Platform.MAC, desiredCapabilities = WakeletPageTest.environmentSettings),
                @Firefox(version = "77", platform = Platform.WINDOWS, desiredCapabilities = WakeletPageTest.environmentSettings)
        },
        safari = @Safari(version = "13", platform = Platform.MAC, desiredCapabilities = WakeletPageTest.environmentSettings),
        edge = @Edge(version = "83", platform = Platform.WINDOWS, desiredCapabilities = WakeletPageTest.environmentSettings))
//endregion
*/
//region BrowserRunnerSetup
@RunWith(WebDriverRunner.class)
//@Chrome(platform = Platform.ANY)
@Firefox(platform = Platform.ANY)
//@Edge(platform = Platform.WINDOWS)
//@Safari(platform = Platform.MAC)
//endregion

public class WakeletPageTest extends DriverHelper {

    public static final String environmentSettings = "{  \"project\": \"Wakelet\",\n" +
            "    \"build\": \"Wakelet production tests\",\n" +
            "    \"name\": \"Tests\",\n" +
            "    \"resolution\": \"1920x1080\",\n" +
            "    \"browserstack.use_w3c\" : \"true\" }";

    public WakeletPage blogPage;

    public WakeletPageTest() {
        blogPage = new WakeletPage();
    }

    /**
     * Setup application under test to work on different browsers
     */
    @Before
    public void setupTest() {
        setup();
    }

    /**
     * Test to run
     */
    @Test
    public void test() {
        blogPage.testclick();
    }

    /**
     * Close driver instance and browser after each test
     */
    @After
    public void tearDown() {
        closeDriverInstance();
    }
}
