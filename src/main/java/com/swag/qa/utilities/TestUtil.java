package com.swag.qa.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.swag.qa.base.TestBase;

public class TestUtil extends TestBase {

	public static ExtentTest extentTest;
	public static ExtentReports extent;

	public static long PAGE_LOAD_TIMEOUT = 20;
	public static long IMPLICIT_WAIT = 20;

	public static void takeScreenshotAtEndOfTest(WebDriver driver) throws Throwable {
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileHandler.copy(src, new File(System.getProperty("user.dir") + "\\src\\test\\resources\\Screenshot\\"
				+ System.currentTimeMillis() + ".png"));
	}

	public static Object[][] getData(String sheetName) throws IOException, FileNotFoundException {
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\test\\resources\\testdata\\SwagLabsTestData.xlsx");
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sheet = wb.getSheet(sheetName);

		int rows = sheet.getPhysicalNumberOfRows();
		int cols = sheet.getRow(0).getPhysicalNumberOfCells();

		Object[][] data = new Object[rows - 1][cols];

		for (int i = 1; i < rows; i++) {

			for (int j = 0; j < cols; j++) {

				data[i - 1][j] = sheet.getRow(i).getCell(j).toString();
			}
		}

		return data;
	}

	public static String takeScreenshot(WebDriver driver, String testName) {
		String path = System.getProperty("user.dir") + "/test-output/Screenshots/" + testName + "_"
				+ System.currentTimeMillis() + ".png";

		try {
			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File dest = new File(path);
			FileUtils.copyFile(src, dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}

	public static void handleTestFailure(WebDriver driver, ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {

			String path = takeScreenshot(driver, result.getName());

			extentTest.log(Status.FAIL, "Test Case Failed : " + result.getName());
			extentTest.log(Status.FAIL, "Test Case Failed : " + result.getThrowable());

			extentTest.addScreenCaptureFromPath(path);
		}
		if (result.getStatus() == ITestResult.SUCCESS) {

			String path = takeScreenshot(driver, result.getName());

			extentTest.log(Status.PASS, "Test Case Passed : " + result.getName());
			extentTest.addScreenCaptureFromPath(path);
		}
	}
}
