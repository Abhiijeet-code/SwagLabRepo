package com.swag.qa.pages;

import org.openqa.selenium.By;

import com.swag.qa.base.TestBase;

public class CartPage extends TestBase{
	
	public void cartList()
	{
		driver.findElement(By.xpath("//div[@class='cart_list']")).isDisplayed();
	}

}
