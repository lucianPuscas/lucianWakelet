package WakeletPages;

import DriverConfiguration.DriverHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.lang.*;

import java.util.List;

import static com.github.webdriverextensions.Bot.*;
import static java.time.Duration.*;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.PageFactory.initElements;

public class WakeletPage extends DriverHelper {


    /**
     * Instantiate objects from Blog page
     */
    public WakeletPage() {
        initElements(driver(), this);
    }


    public final String urlApple = "https://appleid.apple.com/auth/authorize?client_id=com.wakelet.accounts&response_type";
    public final String urlGoogle = "https://accounts.google.com/o/oauth2/v2/auth/identifier?client_id";
    public final String urlMicrosoft = "https://login.microsoftonline.com/common/oauth2/v2.0/authorize?client_id";
    public final String urlFacebook = "https://www.facebook.com/login.php?skip_api_login=1&api_key";
    //public final String urlFacebook = "https://login.microsoftonline.com/common/oauth2/v2.0";
    public String login = "[data-testid='website-header-fixed-menu-login-button']";
    public String invalidEmail = "Invalid email address";
    public String invalidPassword = "Password must be at least 8 characters";


    /**
     * Test Login translations
     */
    public void testLogin() {
        waitForPageToLoad();
        clickElementAndWait(extractElement(login));
        waitForPageToLoad();
        clickElementAndWait(authPageExtractElement(".btn--primary.gt--s"));
        String invalidMail = authPageExtractElements(".input--error").get(1).getText();
        assertEquals(invalidEmail, invalidMail);
        String invalidPass = authPageExtractElements(".input--error").get(5).getText();
        assertEquals(invalidPassword, invalidPass);
        authPageExtractElement("#email").sendKeys("guesttestuser1@waketest.com");
        authPageExtractElement("#password").sendKeys("Test@123");
        clickElementAndWait(authPageExtractElement(".btn--primary.gt--s"));
    }


    /**
     * Test Social Media Login pages
     */
    public void testLoginSocialMedia() {
        List<String> url = asList(urlApple, urlGoogle, urlMicrosoft, urlFacebook);
        List<String> shadowRoot = asList("wk-ui-apple-login", "wk-ui-google-login", "wk-ui-microsoft-login", "wk-ui-facebook-login");
        List<String> buttons = asList(".btn--apple", "#google-login", ".btn--microsoft", "#fb-login");
        waitForPageToLoad();
        clickElementAndWait(extractElement(login));
        waitForPageToLoad();
        for (int i = 0; i < shadowRoot.size(); i++) {
            clickElementAndWait(authPageExtractSocialMediaElement(shadowRoot.get(i), buttons.get(i)));
            waitFor(2);
            switch (i) {
                case 2:
                    verifyPageUrl(url.get(i));
                    driver().navigate().back();
                    break;
                case 3:
                    System.out.println(url.get(i));
                    assertNewTabOpenSocial(url.get(i));

                    break;
                default:
                    verifyPageUrl(url.get(i));
                    break;
            }
        }
    }
}
