package variousConcepts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest {

	WebDriver driver;
	String browser;
	String url;

	// Element List
	By userNameField = By.xpath("//input[@id='username']");
	By passWordField = By.xpath("//input[@id='password']");
	By buttonElementField = By.xpath("//button[@name='login']");
	By dashboardHeaderField = By.xpath("//h2[contains(text(), 'Dashboard' )]");
	By customerMenuField = By.xpath("//*[@id=\"side-menu\"]/li[3]/a/span[1]");
	By headercustomerField = By.xpath("//*[@id=\"page-wrapper\"]/div[3]/div[1]/div/div/div/div[1]/h5");
	By addcustomerMenuField = By.xpath("//*[@id=\"side-menu\"]/li[3]/ul/li[1]/a");
	By fullNameField = By.xpath("//input[@id='account']");
	By companyDropdownField = By.xpath("//select[@id='cid']");

	@BeforeClass
	public void readConfig() {
		// InputStream //BufferedReader //FileReader //Scanner

		try {
			InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
			Properties prop = new Properties();
			prop.load(input);
			prop.getProperty("browser");
			browser = prop.getProperty("browser");
			url = prop.getProperty("url");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@BeforeMethod
	public void init() {

		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "driver\\chromedriver.exe");
			driver = new ChromeDriver();

		}

		else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "driver\\geckodriver.exe");
			driver = new FirefoxDriver();

		}

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	 @Test(priority=1)
	public void loginTest() {

		driver.findElement(userNameField).sendKeys("demo@techfios.com");
		driver.findElement(passWordField).sendKeys("abc123");
		driver.findElement(buttonElementField).click();

		Assert.assertEquals(driver.findElement(dashboardHeaderField).getText(), "Dashboard", "Page Not Found!!");
	}

	@Test(priority=2)
	public void addCustomer() throws InterruptedException {

		loginTest();
		driver.findElement(customerMenuField).click();
		driver.findElement(addcustomerMenuField).click();

		Thread.sleep(3000); // explicit Wait
		Assert.assertEquals(driver.findElement(headercustomerField).getText(), "Add Contact", "Page Not Found!!");

		driver.findElement(fullNameField).sendKeys("Selenium" + generateRandomNum(999));

		// selectFromDropdown(driver.findElement(companyDropdownField), "Techfios");

		selectFromDropdown(companyDropdownField, "Techfios");

	}

	private int generateRandomNum(int boundaryNum) {

		Random rnd = new Random();
		int genNum = rnd.nextInt(boundaryNum);
		return genNum;

	}

	// By type
	private void selectFromDropdown(By Locator, String visibleText) {
		// TODO Auto-generated method stub
		Select sel = new Select(driver.findElement(Locator));
		sel.selectByVisibleText(visibleText);

	}
	// list of webelements
	// private void selectFromDropdown(WebElement element, String visibleText) {
	// TODO Auto-generated method stub
	// Select sel = new Select(element);
	// sel.selectByVisibleText(visibleText);

}
