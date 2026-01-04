package com.swag.qa.pages;



import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import com.swag.qa.base.TestBase;

import io.qameta.allure.Step;


public class HomePage extends TestBase{

	@FindBy(id = "react-burger-menu-btn")
	WebElement menuBtn;
	
	
	@FindBy(id = "shopping_cart_container")
	WebElement cart;
	
	
	public HomePage()
	{
		PageFactory.initElements(driver, this);
	}
	
	public List<WebElement> inventorylist = driver.findElements(By.xpath("//div[@class='inventory_item']"));
	
	@Step("Getting the title of page")
	public String homeTitle()
	{
		return driver.getTitle();
	}
	
	@Step("Clicking on menu button")
	public MenuPage menuButton()
	{
		menuBtn.click();
		
		return new MenuPage();
	}
	
	@Step("Selecting the value fron sorting dropdown")
	public void sort(String value)
	{
		WebElement sortDD = driver.findElement(By.xpath("//select[contains(@class, 'product_sort_container')]"));
		
	/*
	 *  List<WebElement> sortDD = driver.findElememts(By.xpath("//select[contains(@class, 'product_sort_container')]//option"))
	 * 		
	 * 	for(int i =0; i<sortDD.size(); i++)
	 * {
	 * 		if(sortdd.get(i).getText().Equals("+value+"))
	 * 			{
	 * 				sortdd.get(i).click();
	 * 			}
	 * }
	 * 
	 */
		
//		WebElement DDValue = sortDD.findElement(By.xpath(".//option[@value='"+value+"']")); ==>.// (dot slash) → means search inside the parent element
		
		Select select = new Select(sortDD);
		select.selectByValue(value);
	}
	
	@Step("Clickin on cart button")
	public CartPage cart()
	{
		cart.click();
		return new CartPage();
	}
	
	@Step("Getting the inventory size")
	public int inventoryList()
	{
		return inventorylist.size();
	}
	
	@Step("Geeting the sorted items on the page")
	public String sortedItem()
	{
		return driver.findElement(By.xpath("//div[contains(@class,'inventory_item_name')]")).getText();
	}
	
	
	@Step("Adding items to cart : {0}")
	public void addToCart(String itemname)
	{
		driver.findElement(By.xpath("//div[contains(text(),'"+itemname+"')]//ancestor::div[@class='inventory_item_description']//button[contains(@name,'add-to-cart')]")).click();
	}
	
	@Step("Click on remove button : {0}")
	public void remove(String itemname)
	{
		driver.findElement(By.xpath("//div[contains(text(),'"+itemname+"')]//ancestor::div[@class='inventory_item_description']//button[contains(@name,'remove')]")).click();
	}
	
	
}
