package com.automation.objectRepository;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SwagLabLocators {

	@FindBy(xpath = "//input[@id='user-name']")
	public WebElement username;

	@FindBy(xpath = "//input[@id='password']")
	public WebElement password;

	@FindBy(xpath = "//input[@id='login-button']")
	public WebElement btnLogin;

	@FindBy(xpath = "//button[@id='add-to-cart-sauce-labs-bike-light']")
	public WebElement btnAddItemToCart; 
	
	
	@FindBy(xpath = "//div[@class='shopping_cart_container']") 
	public WebElement btnCart; 

	@FindBy(xpath = "//button[@id='checkout']") 
	public WebElement btnCheckOut; 

	@FindBy(xpath = "//input[@id='first-name']") 
	public WebElement firstName; 

	@FindBy(xpath = "//input[@id='last-name']") 
	public WebElement lastName; 

	@FindBy(xpath = "//input[@id='postal-code']") 
	public WebElement postalCode; 

	@FindBy(xpath = "//input[@id='continue']") 
	public WebElement btnContinue; 

	@FindBy(xpath = "//button[@id='finish']") 
	public WebElement btnFinish;

	@FindBy(xpath = "//button[@id='back-to-products']") 
	public WebElement btnHome;
	
	@FindBy(xpath = "//button[@ID='react-burger-menu-btn']")
	public WebElement btnMenu;
	
	@FindBy(xpath = "//a[@id='logout_sidebar_link']")
	public WebElement btnLogout;
}