
package test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class CitriiLoginTest {
	ExtentHtmlReporter htmlReporter;
	ExtentReports extent;
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");


	@BeforeTest
	public void setUp() {

		htmlReporter = new ExtentHtmlReporter("CitriiLogin.html");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
	}

	@Parameters("browserName")
	@BeforeTest
	public void SetUp(String browserName) {

		System.out.println("Browser Name is :"+browserName);
		System.out.println("Thread id :"+Thread.currentThread().getId());

		if (browserName.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", projectPath+"\\drivers\\chromedriver\\chromedriver.exe");
			driver = new ChromeDriver();

		}
		else if (browserName.equalsIgnoreCase("firefox")) {
//			System.setProperty("webdriver.gecko.marionette", projectPath+"t\\drivers\\gickodriver\\geckodriver.exe");
			System.setProperty("webdriver.gecko.driver", projectPath+"\\Drivers\\gickodriver\\geckodriver.exe");
			
			driver = new FirefoxDriver();

		}

	}


	@Test
	public void test1() throws Exception {
		ExtentTest test = extent.createTest("Citrii Login using Chrome Browser", "This is an assessment test");
		
		driver.get("https://poc9.citrii-software.co.za/login");
		test.pass("Launch Citrii Website");

		test.log(Status.INFO, "Starting Test Case");


		// Enter username
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//input[@id='uxUsername']")).sendKeys("TestUsername");
		test.pass("Enter username in Username texbox");

		//Enter Password
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//input[@id='uxPassword']")).sendKeys("TestPassword");
		test.pass("Enter password in Password texbox");

		//Click on LogIn button
		driver.findElement(By.xpath("//span[contains(text(),'Login')]")).click();
		test.pass("Click on LogIn button");

		// log with snapshot
		test.pass("details", MediaEntityBuilder.createScreenCaptureFromPath("Verification.png").build());

		// test with snapshot
		test.addScreenCaptureFromPath( projectPath+"//Screenshorts//Verification.png");
		
		
		
		//Attempting to Login verification Message
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		String actual_msg = driver.findElement(By.xpath("/html[1]/body[1]/app-root[1]/app-login[1]/div[1]/p-toast[1]/div[1]/p-toastitem[1]/div[1]/div[1]/div[1]/div[2]")).getText();
		test.pass("verification Message");
		
		// Store message in variable
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		String expect = "Attempting login...";
		                
		// Here Assert is a class and assertEquals is a method which will compare two values if// both matches it will run fine but in case if does not match then if will throw an 
		//exception and fail testcases
		 
		// Verify error message
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		Assert.assertEquals(expect, actual_msg);
	

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	


	@AfterTest
	public void tearDownTest() {


		//Wait 30 minutes before closing browser
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Close browser

		//driver.close();
		driver.quit();
		System.out.println("Test Completed Successfully");

	}


	@AfterSuite
	public void tearDown() {

		extent.flush();

	}


}
