package com.automation.supportLibraries;
import static org.testng.Assert.assertTrue;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.automation.baseClass.BaseClass;
public class Util extends BaseClass {
	public static String scrFileName = null;
	public static String screenshotPath = System.getProperty("user.dir") + "/screenshots/";
	public static void hardWait(long miliSeconds) {
		try {
			Thread.sleep(miliSeconds);
		} catch (Exception e) {
			Report.failTest("Not able to wait");
		}
	}

	public WebDriverWait driverWait() {
		WebDriverWait explicitWait = new WebDriverWait(driver, 120);
		return explicitWait;
	}

	public boolean type(WebElement locator, String message, String strData) throws Exception {
		boolean blnVal = false;
		try {
			driverWait().until(ExpectedConditions.elementToBeClickable(locator));
			hardWait(Long.valueOf(configProp.getProperty("Sleep_VeryLow")));
			if (locator.isDisplayed()) {
				if (locator.getText() != null || !locator.getText().equalsIgnoreCase("")) {
					locator.clear();
				}
				locator.sendKeys(strData);
				log.info("Text entered in the textbox is: " + strData);
				blnVal = true;
			}
		} catch (RuntimeException localRuntimeException) {
			log.error("Unable to Enter the value in the Textbox :" + locator);
			Report.failTestSnapshot(driver, "Element not found " + locator, "Failed");
			throw (localRuntimeException);
		}
		return blnVal;
	}

	public boolean type(WebElement locator, String message, String strData, boolean masked) throws Exception {
		boolean blnVal = false;
		try {
			driverWait().until(ExpectedConditions.elementToBeClickable(locator));
			hardWait(Long.valueOf(configProp.getProperty("Sleep_VeryLow")));
			if (locator.isDisplayed()) {
				if (locator.getText() != null || !locator.getText().equalsIgnoreCase("")) {
					locator.clear();
				}
				locator.sendKeys(strData);
				if (masked)
					strData = getMasked(strData);
				log.info("Text entered in the textbox is: " + strData);
				blnVal = true;
			}
		} catch (RuntimeException localRuntimeException) {
			log.error("Unable to Enter the value in the Textbox :" + locator);
			Report.failTestSnapshot(driver, "Element not found " + locator, "Failed");
			throw (localRuntimeException);
		}
		return blnVal;
	}

	public static String getMasked(String data) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < data.length(); i++)
			sb.append('*');
		return sb.toString();
	}

	public boolean JSClick(WebElement locator) throws Exception {
		boolean flag = false;
		try {
			driverWait().until(ExpectedConditions.elementToBeClickable(locator));
			if (locator.isDisplayed()) {
				JavascriptExecutor executor = (JavascriptExecutor)driver;
				if (executor.executeScript("return document.readyState").toString().equals("complete")) {
					hardWait(Long.valueOf(configProp.getProperty("Sleep_VeryLow")));
					locator.click();
					log.info("Clicked on the element using Javascript: " + locator);
					flag = true;
					hardWait(Long.valueOf(configProp.getProperty("Sleep_VeryLow")));
				}
			}
		} catch (Exception e) {
			log.error("Error in clicking on the element using Javascript: " + locator);
			Report.failTestSnapshot(driver, "Element not found " + locator, "Failed");
			e.printStackTrace();
			assertTrue(flag);
			throw (e);
		}
		return flag;
	}

	public boolean waitForElement(WebElement locator) throws Exception {
		boolean blnVal = false;
		try {
			driverWait().until(ExpectedConditions.elementToBeClickable(locator));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			if (locator.isDisplayed()) {
				if (js.executeScript("return document.readyState").toString().equals("complete")) {
					log.info("Element found: " + locator);
					blnVal = true;
				}
			}
		} catch (RuntimeException localRuntimeException) {
			log.error("Element not found : " + locator);
			Report.failTestSnapshot(driver, "Element not found " + locator, "Failed");
		}
		return blnVal;
	}

	public static String getHostNameOfMachine()
	{
		String hostname = "Unknown";
		try
		{
			InetAddress addr;
			addr = InetAddress.getLocalHost();
			hostname = addr.getHostName();
		}
		catch (UnknownHostException ex)
		{
			Report.failTest("Hostname can not be resolved");
		}
		return hostname;
	}

	public static String randomNum() {
		String randomNumber = "";
		try {
			randomNumber = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()).toString();
			return randomNumber;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return randomNumber;
	}

	public static boolean CreateFolder(String FolderPath) {
		boolean result = false;
		try {
			File directory = new File(FolderPath);
			if (!directory.exists()) {
				result = directory.mkdirs();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String DateString() {
		Util.hardWait(1000);
		int MyDay = LocalDateTime.now().getDayOfMonth();
		int MyYear = LocalDateTime.now().getYear();
		int MyMonth = LocalDateTime.now().getMonthValue();
		final String CureentDtTm = (String.valueOf(MyMonth) + String.valueOf(MyDay) + String.valueOf(MyYear));
		return CureentDtTm;
	}
}