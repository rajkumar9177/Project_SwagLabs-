package com.automation.driver;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import com.automation.baseClass.BaseClass;

public class BrowserOpen extends BaseClass{
	static String systemDir = System.getProperty("user.dir");

	public static WebDriver startBrowser() {
		try {
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\src\\test\\java\\com\\automation\\driver\\chromedriver.exe");
			driver = new ChromeDriver();
		} catch (Exception e) {
			e.printStackTrace();
		}
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		
		driver.get(configProp.getProperty("applicationUrl"));
		
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		return driver;
	}
}