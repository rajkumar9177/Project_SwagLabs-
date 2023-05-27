package com.automation.dao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import com.automation.baseClass.BaseClass;
import com.automation.objectRepository.SwagLabLocators;
import com.automation.supportLibraries.Report;
import com.automation.supportLibraries.Util;

public class SwagLabDao extends BaseClass {
	private static Logger logger=LogManager.getLogger(SwagLabDao.class);
	private Util utils;
	private SwagLabLocators slLocatorsPage;

	public SwagLabDao(String scenario) throws Exception {
		this.slLocatorsPage = PageFactory.initElements(driver, SwagLabLocators.class);
		this.utils = new Util();
	}

	public void logIn() throws Exception {
			utils.waitForElement(slLocatorsPage.username);
			utils.type(slLocatorsPage.username, "Entered Username: ", configProp.getProperty("userName"));
			utils.type(slLocatorsPage.password, "Entered Password: ",configProp.getProperty("password") , true);
			Report.passTestScreenshotEmbedded(driver, "Swag Labs", "Login");
			utils.JSClick(slLocatorsPage.btnLogin);
	}

	public void addProductToCart() throws Exception{
		utils.waitForElement(slLocatorsPage.btnAddItemToCart);
		Report.passTestScreenshotEmbedded(driver, "Swag Labs", "All Products");
		utils.JSClick(slLocatorsPage.btnAddItemToCart);
		Report.passTestScreenshotEmbedded(driver, "Swag Labs", "Adding product into cart");
		utils.JSClick(slLocatorsPage.btnCart);
		Report.passTestScreenshotEmbedded(driver, "Swag Labs", "User Cart");
		utils.JSClick(slLocatorsPage.btnCheckOut);
	}

	public void enterUserInfo() throws Exception {
		utils.waitForElement(slLocatorsPage.firstName);
		utils.type(slLocatorsPage.firstName, "Entered First Name: ",configProp.getProperty("firstName"));
		utils.type(slLocatorsPage.lastName, "Entered Last Name: ",configProp.getProperty("lastName"));
		utils.type(slLocatorsPage.postalCode, "Entered Postalcode: ",configProp.getProperty("postalCode"));
		Report.passTestScreenshotEmbedded(driver, "Swag Labs", "Checkout:User Information");
		utils.waitForElement(slLocatorsPage.btnContinue);
	}

	public void productCheckout() throws Exception {
		utils.JSClick(slLocatorsPage.btnContinue);
		Report.passTestScreenshotEmbedded(driver, "Swag Labs", "Overview of the Product");
		utils.JSClick(slLocatorsPage.btnFinish);
		Report.passTestScreenshotEmbedded(driver, "Swag Labs", "Order complete");
		utils.JSClick(slLocatorsPage.btnHome);
	}

	public void logout() throws Exception{
		utils.JSClick(slLocatorsPage.btnMenu);
		Report.passTestScreenshotEmbedded(driver, "Swag Labs", "Home");
		utils.JSClick(slLocatorsPage.btnLogout);
		Report.passTestScreenshotEmbedded(driver, "Swag Labs", "Logout");

	}
}
