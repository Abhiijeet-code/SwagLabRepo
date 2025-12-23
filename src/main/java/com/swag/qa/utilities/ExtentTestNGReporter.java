package com.swag.qa.utilities;
import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;
import org.testng.IResultMap;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentTestNGReporter implements IReporter {

    private ExtentReports extent;

    @Override
    public void generateReport(List<XmlSuite> xmlSuites,
                               List<ISuite> suites,
                               String outputDirectory) {

        // Report location
        String reportPath = outputDirectory + File.separator + "ExtentReport.html";

        // Spark Reporter
        ExtentSparkReporter sparkReporter =
                new ExtentSparkReporter(reportPath);
        sparkReporter.config().setReportName("Automation Test Report");
        sparkReporter.config().setDocumentTitle("Extent Report");

        // Extent Reports
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // Iterate suites
        for (ISuite suite : suites) {
            Map<String, ISuiteResult> suiteResults = suite.getResults();

            for (ISuiteResult sr : suiteResults.values()) {
                ITestContext context = sr.getTestContext();

                buildTestNodes(context.getPassedTests(), Status.PASS);
                buildTestNodes(context.getFailedTests(), Status.FAIL);
                buildTestNodes(context.getSkippedTests(), Status.SKIP);
            }
        }

        extent.flush();
    }

    private void buildTestNodes(IResultMap tests, Status status) {

        if (tests.size() == 0) {
            return;
        }

        for (ITestResult result : tests.getAllResults()) {

            ExtentTest test = extent.createTest(
                    result.getMethod().getMethodName()
            );

            // Assign groups as categories
            for (String group : result.getMethod().getGroups()) {
                test.assignCategory(group);
            }

            // Set timestamps
            test.getModel().setStartTime(getTime(result.getStartMillis()));
            test.getModel().setEndTime(getTime(result.getEndMillis()));

            // Log status
            if (result.getThrowable() != null) {
                test.log(status, result.getThrowable());
            } else {
                test.log(status,
                        "Test " + status.toString().toLowerCase() + "ed");
            }
        }
    }

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }
}
