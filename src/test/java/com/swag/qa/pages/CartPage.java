package com.swag.qa.pages;

import org.openqa.selenium.By;

import com.swag.qa.base.TestBase;

public class CartPage extends TestBase{
	
	public boolean cartList()
	{
		return getDriver().findElement(By.xpath("//div[@class='cart_list']")).isDisplayed();
	}

}
