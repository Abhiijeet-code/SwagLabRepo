package com.swag.qa.pages;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.DataProvider;

import com.swag.qa.base.TestBase;
import com.swag.qa.utilities.TestUtil;

public class HomePage extends TestBase{

	@FindBy(id = "react-burger-menu-btn")
	WebElement menuBtn;
	
	
	@FindBy(id = "shopping_cart_container")
	WebElement cart;
	
	
	
	List<WebElement> inventoryList = driver.findElements(By.xpath("//div[@class='inventory_item']"));
	
	
	public void homepage()
	{
		PageFactory.initElements(driver, this);
	}
	
	public String homeTitle()
	{
		return driver.getTitle();
	}
	
	public MenuPage menuButton()
	{
		menuBtn.click();
		
		return new MenuPage();
	}
	
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
	
	public CartPage cart()
	{
		cart.click();
		return new CartPage();
	}
	
	public int inventoryList()
	{
		return inventoryList.size();
	}
	
	public String beforeSorting()
	{
		return driver.findElement(By.xpath("//div[contains(@class,'inventory_item_name')]")).getText();
	}
	
	public String afterSorting()
	{
		return driver.findElement(By.xpath("//div[contains(@class,'inventory_item_name')]")).getText();
	}
	
	public void addToCart(String itemname)
	{
		driver.findElement(By.xpath("//div[contains(text(),'"+itemname+"')]//ancestor::div[@class='inventory_item_description']//button[contains(@name,'add-to-cart')]")).click();
	}
	
	public void remove(String itemname)
	{
		driver.findElement(By.xpath("//div[contains(text(),'"+itemname+"')]//ancestor::div[@class='inventory_item_description']//button[contains(@name,'remove')]")).click();
	}
	
	@DataProvider(name = "CartData")
	public Object[][] getCart() throws FileNotFoundException, IOException
	{
		return TestUtil.getData("Home");
	}
}
