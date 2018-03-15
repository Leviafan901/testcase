package com.google.testtask;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GMailPage {

	private static final String WEBDRIVER_NAME = "webdriver.chrome.driver";
	private static final int WAITING_TIME = 15;
	private static final String DRIVER_PATH = "./src/main/resources/chromedriver.exe";
	private static final String URL = "https://www.gmail.com";
	private static final String USER_PASSWORD = "223223223";
	private static final String LOGIN = "sosenkovTest";
	private static final String GMAIL_ADDRESS = "sosenkovtest@gmail.com";

	private WebDriver driver;

	public GMailPage() {
		System.setProperty(WEBDRIVER_NAME, DRIVER_PATH);
		driver = new ChromeDriver();
	}

	public WebElement getUnreadedLetterText() throws InterruptedException {
		WebElement letterText = null;
		driver.get(URL);
		driver.findElement(By.cssSelector("input[type='email']")).sendKeys(LOGIN);
		driver.findElement(By.id("identifierNext")).click();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
		driver.getCurrentUrl();
		driver.findElement(By.name("password")).sendKeys(USER_PASSWORD);
		driver.findElement(By.id("passwordNext")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.T-I.J-J5-Ji.T-I-KE.L3")));
		driver.getCurrentUrl();
		driver.findElement(By.cssSelector("div.T-I.J-J5-Ji.T-I-KE.L3")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("to")));
		driver.getCurrentUrl();
		driver.findElement(By.name("to")).sendKeys(GMAIL_ADDRESS);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[aria-label='Тело письма']")));
		driver.getCurrentUrl();
		driver.findElement(By.cssSelector("div[aria-label='Тело письма']")).sendKeys("Hello, world!");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Отправить')]")));
		driver.getCurrentUrl();
		driver.findElement(By.xpath("//div[contains(text(),'Отправить')]")).click();
		driver.manage().timeouts().implicitlyWait(WAITING_TIME, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//a[contains(text(),'Входящие')]")).click();
		driver.manage().timeouts().implicitlyWait(WAITING_TIME, TimeUnit.SECONDS);
		
		driver.getCurrentUrl();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='yW']/span")));
		
		WebElement mailerData = driver.findElement(By.xpath("//div[@class='yW']/span"));
		String mailer = mailerData.getAttribute("email");
		boolean isRightMailer = GMAIL_ADDRESS.equals(mailer);
		if (isRightMailer) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("tr[class='zA zE']")));
			driver.findElement(By.cssSelector("tr[class='zA zE']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text() = 'Hello, world!']")));
			letterText = driver.findElement(By.xpath("//div[text() = 'Hello, world!']"));
		}
		
		return letterText;
	}

	public void quit() {
		driver.quit();
	}
}
