package com.automation.supportLibraries;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.AssertJUnit;
import org.testng.asserts.SoftAssert;
import com.automation.baseClass.BaseClass;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
public class Report extends BaseClass {
	public static String reportName;
	public static SoftAssert s_assert = new SoftAssert();
	public static String relativePathIndividualTest;

	public static ExtentHtmlReporter StartHtmlFinalReport(ExtentHtmlReporter htmlReporter, String reportName, String executionType,
			String ResultFolderPath) {
		htmlReporter = new ExtentHtmlReporter(
				(ResultFolderPath + "TestReport" + "_" +reportName+"_"+ Util.randomNum() + ".html"));
		htmlReporter.config().setDocumentTitle("Automation Execution Report");
		htmlReporter.config().setReportName("Test Execution Statistics");
		htmlReporter.config().setTheme(Theme.STANDARD);
		return htmlReporter;
	}

	public static ExtentReports StartExtentReport(ExtentHtmlReporter htmlReporter, ExtentReports extent) {
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("OS", "Windows");
		extent.setSystemInfo("Host Name", Util.getHostNameOfMachine()); // MPINDO02056
		return extent;
	}

	//@Test
	public static ExtentTest testCreate(ExtentReports extent, String testCaseName) {
		LogClass.startTestCase(testCaseName);
		test = extent.createTest(testCaseName, testCaseName);
		return test;
	}

	public static ExtentTest nodeCreate(String nodeName) {
		ExtentTest node=test.createNode(nodeName);
		return node;
	}

	public static void passTest(String stepDetails) {
		try {
			test.pass(MarkupHelper.createLabel(stepDetails, ExtentColor.GREEN));
			AssertJUnit.assertTrue(true);
			LogClass.info(stepDetails);
		} catch (Exception e) {
			Report.infoTest("Error while reporting" + e.getMessage());
			LogClass.info("Error while reporting" + e.getMessage());
		}
	}

	public static void infoTest(String stepDetails) {

		try {
			test.info(MarkupHelper.createLabel(stepDetails, ExtentColor.BLUE));
			LogClass.info(stepDetails);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void failTest(String stepDetails) {
		try {
			test.fail(MarkupHelper.createLabel(stepDetails, ExtentColor.RED));
			AssertJUnit.assertTrue(false);
		} catch (Exception e) {		
			e.printStackTrace();
		}
	}

	public static void failTestSnapshot(WebDriver driver, String stepDetails, String screenshotName) {
		try {
			AssertJUnit.assertTrue(false);
			if (!screenshotName.equals("")) {
				List<String> base64StringImage = generatePageScreenshot(driver);
				AssertJUnit.fail("<span class='label white-text red'>" + stepDetails + "</span><br><a href='data:image/gif;base64,"+base64StringImage.get(0)+"' data-featherlight='image'><img src='data:image/gif;base64,"+base64StringImage.get(1)+"' height='50' width='50' alt='Failed Screenshot'></a>");
			}
		} catch (Exception e) {
		}
	}

	public static void passTestScreenshotEmbedded(WebDriver driver, String stepDetails, String screenshotName) {
		try {
			if (!screenshotName.equals("")) {
				List<String> base64StringImage = generatePageScreenshot(driver);
				test.pass("<span class='label white-text green'>" + screenshotName + "</span><br><a href='data:image/gif;base64,"+base64StringImage.get(0)+"' data-featherlight='image'><img src='data:image/gif;base64,"+base64StringImage.get(1)+"' height='50' width='50' alt='Failed Screenshot'></a>");
			}
		} catch (Exception e) {
			Report.infoTest(" message " + e.getMessage());
			Report.failTestSnapshot(driver, "File IO Exception ", "Exception");
		}
	}

	public static void failTestSnapshotEmbedded(WebDriver driver, String stepDetails, String screenshotName) {
		try {
			if (!screenshotName.equals("")) {
				List<String> base64StringImage = generatePageScreenshot(driver);
				AssertJUnit.fail("<span class='label white-text green'>" + screenshotName + "</span><br><a href='data:image/gif;base64,"+base64StringImage.get(0)+"' data-featherlight='image'><img src='data:image/gif;base64,"+base64StringImage.get(1)+"' height='50' width='50' alt='Failed Screenshot'></a>");
			}
		} catch (Exception e) {
			Report.infoTest(" message " + e.getMessage());
			Report.failTestSnapshot(driver, "File IO Exception ", "Exception");
		}
	}

	public static void reportLink(ExtentTest test) {
		test.info(MarkupHelper.createLabel("  <a href='" + "Testcases/" + relativePathIndividualTest
				+ "' target=\"_blank\" style=\"color: rgb(255, 255, 255)\"> Test Case link : " + reportName + "</a>",
				ExtentColor.BLUE));
	}

	public static void endReport(ExtentReports extent) {
		extent.flush();
	}

	public static List<String> generatePageScreenshot(WebDriver driver) throws IOException {
		List<String> stringImages = new ArrayList<String>();
		String strImage = "";
		String thumbnailImage = "";
		try {
			File src = null;
			if (driver.getClass().getName().contains("ChromeDriver")) {
				TakesScreenshot ts = (TakesScreenshot) driver;
				src = ts.getScreenshotAs(OutputType.FILE);
			} 
			BufferedImage img = ImageIO.read(src);			
			strImage = imgToBase64String(img, img.getWidth(), img.getHeight());
			thumbnailImage = thumbnailImageToBase64String(img,100,100);
			stringImages.add(strImage);
			stringImages.add(thumbnailImage);
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch(WebDriverException se)
		{
			try {
				File src = null;
				if (driver.getClass().getName().contains("ChromeDriver")) {
					TakesScreenshot ts = (TakesScreenshot) driver;
					src = ts.getScreenshotAs(OutputType.FILE);
				} 
				BufferedImage img = ImageIO.read(src);			
				strImage = imgToBase64String(img, img.getWidth(), img.getHeight());
				thumbnailImage = thumbnailImageToBase64String(img,100,100);
				stringImages.add(strImage);
				stringImages.add(thumbnailImage);
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
			catch(Exception e)
			{
			}
		}		
		catch(Exception e)
		{
		}
		return stringImages;
	}

	private static String thumbnailImageToBase64String(final RenderedImage img, int width, int height) {

		final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		try {
			Image tmp = ((Image) img).getScaledInstance(width, height, Image.SCALE_DEFAULT);
			BufferedImage thumbnailImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = thumbnailImage.createGraphics();
			g2d.drawImage(tmp, 0, 0, null);
			g2d.dispose();
			ImageIO.write(thumbnailImage, "png", java.util.Base64.getEncoder().wrap(byteStream));
			return byteStream.toString(StandardCharsets.ISO_8859_1.name());
		} catch (final IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}

	public static String imgToBase64String(final RenderedImage img, int width, int height) {
		final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		try {
			Image tmp = ((Image) img).getScaledInstance(width, height, Image.SCALE_DEFAULT);
			BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = resizedImage.createGraphics();
			g2d.drawImage(tmp, 0, 0, width, height, null);
			g2d.setComposite(AlphaComposite.Src);
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
					RenderingHints.VALUE_RENDER_QUALITY);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.dispose();
			ImageIO.write(resizedImage, "gif", java.util.Base64.getEncoder().wrap(byteStream));
			return byteStream.toString(StandardCharsets.ISO_8859_1.name());
		} catch (final IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}
}
