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
import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.PageFactory.initElements;

public class WakeletPage extends DriverHelper {


    /**
     * Instantiate objects from Blog page
     */
    public WakeletPage() {
        initElements(driver(), this);
    }


    //endregion
    @FindBy(how = How.TAG_NAME, using = "my-app")
    public WebElement first;
    @FindBy(how = How.CSS, using = "iron-lazy-selected")
    public WebElement second;
    @FindBy(how = How.CSS, using = ".iron-lazy-selected")
    public WebElement third;
    @FindBy(how = How.CSS, using = ".hero__btn.btn--primary")
    public WebElement button;




    public WebElement expandRootElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver();
        return (WebElement) js.executeScript("arguments[0].shadowRoot", element);
    }




    public void testclick(){
        waitForPageToLoad();

        WebElement root1 = driver().findElement(By.tagName("my-app"));
        waitFor(2);
        WebElement shadowRoot1 = expandRootElement(root1);
        waitFor(1);
        WebElement root2 = shadowRoot1.findElement(By.tagName("page-new-home"));
        waitFor(1);
        WebElement shadowRoot2 = expandRootElement(root2);

        WebElement root3 =shadowRoot2.findElement(By.cssSelector(".educators-btn"));
        waitFor(1);
        clickElementAndWait(root3);
    }

}
