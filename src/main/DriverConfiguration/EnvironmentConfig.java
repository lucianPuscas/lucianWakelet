package DriverConfiguration;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.io.FileReader;

public class EnvironmentConfig extends HtmlUnitDriver {

    public JSONObject getConfig() {
        // pass the path to the file as a parameter
        JSONObject values = null;
        try {
            JSONParser parser = new JSONParser();
            Object configValues = parser.parse(new FileReader(System.getProperty("user.dir") + "/config.json"));
            values = (JSONObject) configValues;
        } catch (Exception e) {
            e.getMessage();
        }
        return (JSONObject) values.get("environment");
    }
}
