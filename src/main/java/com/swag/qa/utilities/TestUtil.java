package com.swag.qa.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

import com.swag.qa.base.TestBase;

public class TestUtil extends TestBase{

	public static  long PAGE_LOAD_TIMEOUT = 10 ;
	public static long  IMPLICIT_WAIT = 10;
	
	
	public static void takeScreenshotAtEndOfTest(WebDriver driver) throws Throwable
	{
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileHandler.copy(src, new File(System.getProperty("user.dir")+"\\src\\test\\resources\\Screenshot\\"+System.currentTimeMillis()+".png"));
	}
	
	public static Object[][] getData(String sheetName) throws IOException, FileNotFoundException
	{
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\testdata\\SwagLabsTestData.xlsx");
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sheet = wb.getSheet(sheetName);
		
		int rows = sheet.getPhysicalNumberOfRows();
		int cols = sheet.getRow(0).getPhysicalNumberOfCells();
		
		Object[][] data = new Object[rows-1][cols];
		
		for(int i=1; i< rows; i++) {
			
			for(int j=0; j<cols; j++) {
				
				data[i-1][j] = sheet.getRow(i).getCell(j).toString();
			}
		}
		
		return data;
	}
}
