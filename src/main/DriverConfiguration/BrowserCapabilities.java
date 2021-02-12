package DriverConfiguration;

import org.openqa.selenium.remote.DesiredCapabilities;

public class BrowserCapabilities extends DesiredCapabilities {
    private static final long serialVersionUID = 1L;

    public BrowserCapabilities() {
        setCapability("browserstack.local", System.getProperty("browserstack.is_local"));
        setCapability("browserstack.localIdentifier", System.getProperty("browserstack.local_identifier"));
    }
}
