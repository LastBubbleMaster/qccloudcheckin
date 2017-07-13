package swimLesson;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

import com.google.common.base.Function;

import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;

public class checkIn {

	static WebDriver driver;
	static WebElement swimLessonElement;
	static WebElement checkInButton;
	static WebElement forceInButton;
	static WebElement loadingIcon;
	static Wait<WebDriver> wait;
	static int counter1;
	static String UB;
	static String UU;
	static String UP;

	public static void credits(){
		System.out.println("[][][][][][][][][][][][][][][]");
		System.out.println("QuikCheckCloud Check-In Tool");
		System.out.println("Developed By: Nick Karlovich");
		System.out.println("        Version 1.0.0");
		System.out.println("        July, 9, 2017");
		System.out.println("[][][][][][][][][][][][][][][]");
		
		
	}
	
	
	
	public static void chromeSetup() {
		String exePath = "H:/Desktop/chrome Driver/chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", exePath);
		driver = new ChromeDriver();
		System.out.println("--");
		wait = new FluentWait<WebDriver>(driver).withTimeout(6, TimeUnit.SECONDS).pollingEvery(20,
				TimeUnit.MILLISECONDS);
	}

	public static void connectToWebsite() {
		System.out.println("Attempting to connect to qccloud.net");
		driver.get("https://qccloud.net/account/login");
		System.out.println("Connected");

	}
	
	public static void getName() {
		driver.get("https://qccloud.net/checkin/search");
		System.out.println("--");
		System.out.println(driver.findElement(By.id("tr_nav1")).getText());
		System.out.println("--");
		System.out.println(driver.findElement(By.xpath("//tr[@id='tr_nav1']//td[1]")));
		System.out.println("--");
		
	}
	
	

	public static void shutdown() {
		driver.close();
	}

	public static boolean authenticateUser() throws InterruptedException {
		chromeSetup();
		driver.manage().window().setSize(new Dimension(500, 700));
		connectToWebsite();

		boolean once = true;
		int ccc = 0;
		while (driver.getCurrentUrl().equals("https://qccloud.net/account/login") && once) {
			try{
			UB = driver.findElement(By.xpath("//input[@id='bname']")).getAttribute("value");
			UU = driver.findElement(By.xpath("//input[@id='login']")).getAttribute("value");
			UP = driver.findElement(By.xpath("//input[@id='password']")).getAttribute("value");
			} catch (StaleElementReferenceException eq) {
				
			} catch (NoSuchElementException eq) {
				
			}
			/*if(ccc % 20 == 0)
			{
				System.out.println("Business Name: " + UB);
				System.out.println("Username: " + UU);
				System.out.println("Password: " + UP);
			}	*/
			
			
			try {
				if (driver.findElement(By.className("text-danger")).getText()
						.equals("Invalid Business Name, Username or Password. Please try again.") && once) {
					gui.errorGUI("Couldn't verify the credentials of the user");
					once = false;
					driver.close();
					return false;
				}
			} catch (StaleElementReferenceException e) {
			} catch (NoSuchElementException e) {
			}
			ccc++;
		}
		try {
			wait.until(ExpectedConditions.urlToBe("https://qccloud.net/"));
			driver.close();
			return true;
		} catch (Exception e) {
			driver.close();
			return false;
		}
	}

	protected static boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		} catch (StaleElementReferenceException e) {
			return false;
		} catch (Exception e) {
			return false;
		} catch (Throwable e) {
			return false;
		}
	}

	public static void login() {
		driver.findElement(By.id("bname")).sendKeys(UB);
		driver.findElement(By.id("login")).sendKeys(UU);
		driver.findElement(By.id("password")).sendKeys(UP);
		System.out.println("Logging in as " + UU);
		driver.findElement(By.name("commit")).click();
		System.out.println("Logged in as " + UU);
		System.out.println("Accessing Check In");
		driver.get("https://qccloud.net/checkin/search");

	}

	public static void loginSwimmers(ArrayList<String> barcodes) throws InterruptedException {
		double allSwimStart = System.currentTimeMillis();
		
		for (String x : barcodes) {
			double swimStart = System.currentTimeMillis();
			System.out.println("\n" + "====================");
			System.out.print("User: ");
			System.out.print(SwimClass.getUserByBarcode(x));
			System.out.println("====================");
			// System.out.println();

			boolean nonExistent = false;
			WebElement searchBox = driver.findElement(By.id("query"));
			WebElement mySelectElement = driver.findElement(By.id("field"));
			Select dropdown = new Select(mySelectElement);
			dropdown.selectByIndex(3);
			driver.findElement(By.id("query")).sendKeys(x);

			boolean found = true;
			double startTime = System.currentTimeMillis();

			try {
				counter1 = 0;
				while (isElementPresent(By.className("hd-spinner"))) {

					if (!isElementPresent(By.className("hd-spinner")))
						found = false;
					/*
					 * if(counter1 % 35 == 0)
					 * System.out.println("Query Spinner: " + found + " ");
					 * counter1++;
					 */
				}
				double endTime = System.currentTimeMillis() - startTime;
				System.out.println("Query Spinner Time: " + endTime / 1000 + "seconds");
			} catch (StaleElementReferenceException e2) {

			}
			if (!isElementPresent(By.className("hd-spinner")))
				found = false;

			wait.until(ExpectedConditions.elementToBeClickable(searchBox));
			searchBox.sendKeys(Keys.ENTER);

			found = false;
			startTime = System.currentTimeMillis();
			try {
				counter1 = 0;
				while (isElementPresent(By.className("hd-spinner"))) {
					if (!isElementPresent(By.className("hd-spinner")))
						found = false;
					/*
					 * if(counter1 % 35 == 0)
					 * System.out.println("User Search Spinner: " + found +
					 * " "); counter1++;
					 */
				}
				double endTime = System.currentTimeMillis() - startTime;
				System.out.println("User Search Spinner Time: " + endTime / 1000 + "seconds");
			} catch (StaleElementReferenceException e2) {

			}
			if (!isElementPresent(By.className("hd-spinner")))
				found = false;

			if (!(isElementPresent(By.xpath("//table")))) {
				gui.unable.add(x);
				nonExistent = true;
				System.out.println("User with this Membership ID doesn't exist");
			}
			/*
			 * long stopTime = System.currentTimeMillis(); long elapsedTime =
			 * stopTime - startTime; double finalTime = (double)elapsedTime /
			 * 1000; System.out.println("Time Taken: " + finalTime +
			 * " seconds");
			 */

			if (!nonExistent) {
				found = true;
				startTime = System.currentTimeMillis();
				try {
					counter1 = 0;
					while (isElementPresent(By.className("hd-spinner"))) {
						if (!isElementPresent(By.className("hd-spinner")))
							found = false;
						if (counter1 % 35 == 0)
							System.out.println("LOADING: " + found);
						counter1++;
					}
					double endTime = System.currentTimeMillis() - startTime;
					System.out.println("Loading Spinner: " + endTime / 1000 + "seconds");
				} catch (StaleElementReferenceException e2) {

				}
				if (!isElementPresent(By.className("hd-spinner")))
					found = false;
				WebElement baseTable = null;
				baseTable = driver.findElement(By.xpath("//table"));
				WebElement tableRow = baseTable.findElement(By.id("tr_nav1"));
				WebElement cellINeed = null;
				cellINeed = tableRow.findElement(By.xpath("//td[1]"));
				wait.until(ExpectedConditions.elementToBeClickable(cellINeed));
				HoverAndClick(driver, cellINeed, cellINeed);

				System.out.println("TRIED HOVER AND CLICK");
				Dimension dimension = new Dimension(1400, 800);
				driver.manage().window().setSize(dimension);
				swimLessonElement = driver.findElement(By.id("activity_4"));
				wait.until(ExpectedConditions.elementToBeClickable(swimLessonElement));
				swimLessonElement.click();

				checkInButton = driver.findElement(By.id("chkbtn"));
				forceInButton = driver.findElement(By.id("forcebtn"));
				found = false;
				boolean clicked = false;
				startTime = System.currentTimeMillis();
				while ((checkInButton.isDisplayed() || forceInButton.isDisplayed()) && clicked == false) {
					counter1 = 0;
					while (isElementPresent(By.className("hd-spinner"))) {
						/*
						if (counter1 % 35 == 0)
							System.out.println("Force/Safe Login: " + found);
						counter1++;*/
					}
					double endTime = System.currentTimeMillis() - startTime;
					System.out.println("Force/Safe Login Spinner: " + endTime / 1000 + "seconds");
					found = true;

					if (checkInButton.isDisplayed()) {
						System.out.println("User is signed up for this class");
						gui.good.add(x);
						clicked = true;
						/*checkInButton.click();
						TimeUnit.SECONDS.sleep(3);*/
					} else if (forceInButton.isDisplayed()) {
						System.out.println("User Isn't Signed up for this class");
						gui.bad.add(x);
						clicked = true;
						/*forceInButton.click();
						TimeUnit.SECONDS.sleep(3);*/
					}

				}
			}
			driver.get("https://qccloud.net/checkin/search");
			double endTime = System.currentTimeMillis() - swimStart;
			System.out.println("Swimmer: " + endTime / 1000 + "seconds");
		}
		double endTime = System.currentTimeMillis() - allSwimStart;
		System.out.println("Entire Process " + endTime / 1000 + "seconds");
		gui.panel7 = new JPanel();
		driver.close();
		gui.resultGUI();

	}

	public static void HoverAndClick(WebDriver driver, WebElement elementToHover, WebElement elementToClick) {
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(By.xpath("//tbody//tr[@id='tr_nav1']//td[1]"))).doubleClick().build()
				.perform();
	}

}
/*
 * package swimLesson;
 * 
 * import java.util.ArrayList;
 * 
 * import org.openqa.selenium.By; import org.openqa.selenium.Dimension; import
 * org.openqa.selenium.Keys; import org.openqa.selenium.NoSuchElementException;
 * import org.openqa.selenium.StaleElementReferenceException; import
 * org.openqa.selenium.WebDriver; import org.openqa.selenium.WebElement; import
 * org.openqa.selenium.chrome.ChromeDriver; import
 * org.openqa.selenium.interactions.Actions; import
 * org.openqa.selenium.support.ui.ExpectedConditions; import
 * org.openqa.selenium.support.ui.FluentWait; import
 * org.openqa.selenium.support.ui.Select; import
 * org.openqa.selenium.support.ui.Wait;
 * 
 * import com.google.common.base.Function;
 * 
 * import java.util.concurrent.TimeUnit;
 * 
 * 
 * public class checkIn {
 * 
 * static WebDriver driver; static WebElement swimLessonElement; static
 * WebElement checkInButton; static WebElement forceInButton; static WebElement
 * loadingIcon; static Wait<WebDriver> wait;
 * 
 * 
 * public static void chromeSetup(){ String exePath =
 * "H:/Desktop/chrome Driver/chromedriver.exe";
 * System.setProperty("webdriver.chrome.driver",exePath); driver = new
 * ChromeDriver(); wait = new FluentWait<WebDriver>(driver).withTimeout(6,
 * TimeUnit.SECONDS).pollingEvery(20, TimeUnit.MILLISECONDS); }
 * 
 * public static void connectToWebsite(){
 * System.out.println("Attempting to connect to qccloud.net");
 * driver.get("https://qccloud.net/account/login");
 * System.out.println("Connected");
 * 
 * }
 * 
 * public static void shutdown() { driver.close(); }
 * 
 * 
 * 
 * public static boolean authenticateUser() throws InterruptedException {
 * chromeSetup(); driver.manage().window().setSize(new Dimension(500,700));
 * connectToWebsite();
 * 
 * boolean once = true;
 * 
 * while(driver.getCurrentUrl().equals("https://qccloud.net/account/login") &&
 * once) { // System.out.println("WHAT"); try {
 * if(driver.findElement(By.className("text-danger")).getText().
 * equals("Invalid Business Name, Username or Password. Please try again.") &&
 * once) { gui.errorGUI("Couldn't verify the credentials of the user"); once =
 * false; //System.out.println("HELLO"); //driver.close(); //return false; } }
 * catch(StaleElementReferenceException e) { // System.out.println("WHAT"); }
 * catch(NoSuchElementException e) { // System.out.println("WHAT 222"); }
 * 
 * } // System.out.println("TEST 1"); try { // System.out.println("TEST 2"); //
 * System.out.println(driver.getCurrentUrl());
 * wait.until(ExpectedConditions.urlToBe("https://qccloud.net/"));
 * //System.out.println("TEST 5"); driver.close(); return true; }
 * catch(Exception e) { // System.out.println("TEST 3"); //
 * System.out.println("FALSERONI"); driver.close(); return false; }
 * 
 * 
 * 
 * /* try { //==============================================================
 * TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { try {
 * //==============================================================
 * TimeUnit.SECONDS.sleep(2);
 * if(driver.getCurrentUrl().equals("https://qccloud.net/")) { driver.close();
 * return true; } } catch (InterruptedException e1) { // TODO Auto-generated
 * catch block e1.printStackTrace(); }
 * if(driver.getCurrentUrl().equals("https://qccloud.net/")) { driver.close();
 * return true; }
 * 
 * } if(driver.getCurrentUrl().equals("https://qccloud.net/")) { driver.close();
 * return true; } System.out.println("FALSERONI"); driver.close(); return false;
 * }
 * 
 * 
 * public static boolean authenticateUser(String b, String u, String p){
 * chromeSetup(); connectToWebsite();
 * driver.findElement(By.id("bname")).sendKeys(b);
 * driver.findElement(By.id("login")).sendKeys(u);
 * driver.findElement(By.id("password")).sendKeys(p);
 * System.out.println("Logging In");
 * driver.findElement(By.name("commit")).click();
 * System.out.println("Logged In");
 * //System.out.println(driver.findElement(By.className("text-danger")).getText(
 * )); if(driver.getCurrentUrl().equals("https://qccloud.net/")) {
 * driver.close(); return true;
 * 
 * } else { driver.close(); return false;
 * 
 * } }
 * 
 * protected static boolean isElementPresent(By by) { try {
 * driver.findElement(by); return true; } catch(NoSuchElementException e) {
 * return false; } catch(StaleElementReferenceException e) { return false; }
 * catch(Exception e) { return false; } catch(Throwable e) { return false; } }
 * 
 * public static void login() {
 * driver.findElement(By.id("bname")).sendKeys("cambridge");
 * driver.findElement(By.id("login")).sendKeys("nicklg");
 * driver.findElement(By.id("password")).sendKeys("karlovichlg");
 * System.out.println("Logging In");
 * driver.findElement(By.name("commit")).click();
 * System.out.println("Logged In"); System.out.println("Accessing Check In");
 * driver.get("https://qccloud.net/checkin/search");
 * 
 * 
 * }
 * 
 * public static void loginSwimmers(ArrayList<String> barcodes) throws
 * InterruptedException { for(String x : barcodes) { boolean nonExistent =
 * false; WebElement searchBox = driver.findElement(By.id("query")); WebElement
 * mySelectElement = driver.findElement(By.id("field")); Select dropdown = new
 * Select(mySelectElement); dropdown.selectByIndex(3);
 * driver.findElement(By.id("query")).sendKeys(x);
 * 
 * 
 * boolean found = true; long startTime = System.currentTimeMillis();
 * 
 * try { while(isElementPresent(By.className("hd-spinner"))) //while(found ==
 * false || loadingIcon.isDisplayed() == true) {
 * if(!isElementPresent(By.className("hd-spinner"))) found = false;
 * System.out.println("222LOADING: FOUND: " + found + " "); }
 * System.out.print("-----=-=-=-=-="); } catch (StaleElementReferenceException
 * e2) {
 * 
 * } if(!isElementPresent(By.className("hd-spinner"))) found = false;
 * System.out.println("LOADINGasdfasdfadsf: FOUND: " + found + " ");
 * System.out.println("WAIT UNTIL");
 * 
 * wait.until(ExpectedConditions.elementToBeClickable(searchBox));
 * searchBox.sendKeys(Keys.ENTER);
 * 
 * found = false; try { while(isElementPresent(By.className("hd-spinner")))
 * //while(found == false || loadingIcon.isDisplayed() == true) {
 * if(!isElementPresent(By.className("hd-spinner"))) found = false;
 * System.out.println("333LOADING: FOUND: " + found + " "); }
 * System.out.print("-----=-=-=-=-="); } catch (StaleElementReferenceException
 * e2) {
 * 
 * } if(!isElementPresent(By.className("hd-spinner"))) found = false;
 * 
 * 
 * if(!(isElementPresent(By.xpath("//table")))){ gui.unable.add(x); nonExistent
 * = true; System.out.println("User with this Membership ID doesn't exist"); }
 * 
 * 
 * 
 * /*try { wait.until(ExpectedConditions.elementToBeClickable(tableRow2)); }
 * catch(Exception e) {
 * 
 * 
 * 
 * 
 * }
 * 
 * System.out.println("TEST");
 * 
 * long stopTime = System.currentTimeMillis(); long elapsedTime = stopTime -
 * startTime; System.out.println(elapsedTime);
 * 
 * 
 * 
 * 
 * 
 * //============================================================== /*
 * TimeUnit.SECONDS.sleep(1); System.out.println("1 Second");
 * TimeUnit.SECONDS.sleep(1); System.out.println("2 Second");
 * TimeUnit.SECONDS.sleep(1); System.out.println("3 Second");
 * TimeUnit.SECONDS.sleep(3);
 * //wait.until(ExpectedConditions.urlToBe("https://qccloud.net/"));
 * driver.findElement(By.id("query")).sendKeys(Keys.ENTER); WebElement baseTable
 * = null; try { baseTable = driver.findElement(By.xpath("//table")); } catch
 * (NoSuchElementException i) {
 * //==============================================================
 * TimeUnit.SECONDS.sleep(3); try { baseTable =
 * driver.findElement(By.xpath("//table")); } catch (NoSuchElementException e) {
 * gui.unable.add(x); nonExistent = true;
 * System.out.println("User with this Membership ID doesn't exist"); } }
 * 
 * if(!nonExistent) { found = true; startTime = System.currentTimeMillis();
 * //loadingIcon = driver.findElement(By.className("hd-spinner"));
 * 
 * //try {
 * 
 * System.out.println("FINDING ELEMENT"); /*TimeUnit.SECONDS.sleep(1);
 * System.out.println("1 Second"); TimeUnit.SECONDS.sleep(1);
 * System.out.println("2 Second"); TimeUnit.SECONDS.sleep(1);
 * System.out.println("3 Second");
 * 
 * try { while(isElementPresent(By.className("hd-spinner"))) //while(found ==
 * false || loadingIcon.isDisplayed() == true) {
 * if(!isElementPresent(By.className("hd-spinner"))) found = false;
 * System.out.println("LOADING: FOUND: " + found + " "); }
 * System.out.print("-----=-=-=-=-="); } catch (StaleElementReferenceException
 * e2) {
 * 
 * } if(!isElementPresent(By.className("hd-spinner"))) found = false;
 * System.out.println("LOADINGasdfasdfadsf: FOUND: " + found + " ");
 * System.out.println("WAIT UNTIL"); WebElement baseTable = null; baseTable =
 * driver.findElement(By.xpath("//table")); WebElement tableRow =
 * baseTable.findElement(By.id("tr_nav1")); WebElement cellINeed = null;
 * cellINeed = tableRow.findElement(By.xpath("//td[1]"));
 * wait.until(ExpectedConditions.elementToBeClickable(cellINeed));
 * System.out.println("TEST"); stopTime = System.currentTimeMillis();
 * elapsedTime = stopTime - startTime; System.out.println(elapsedTime);
 * 
 * 
 * 
 * try { //TimeUnit.MILLISECONDS.sleep(100); System.out.println("FIRST CLICK");
 * HoverAndClick(driver,cellINeed,cellINeed); } catch
 * (StaleElementReferenceException e) {
 * //==============================================================
 * TimeUnit.SECONDS.sleep(3); try{ System.out.println("SECOND CLICK");
 * HoverAndClick(driver,cellINeed,cellINeed); } catch
 * (StaleElementReferenceException f) {
 * 
 * } } System.out.println("TRIED HOVER AND CLICK"); Dimension dimension = new
 * Dimension(1400,800); driver.manage().window().setSize(dimension);
 * swimLessonElement = driver.findElement(By.id("activity_4"));
 * wait.until(ExpectedConditions.elementToBeClickable(swimLessonElement));
 * swimLessonElement.click();
 * 
 * checkInButton = driver.findElement(By.id("chkbtn")); forceInButton =
 * driver.findElement(By.id("forcebtn")); found = false; boolean clicked =
 * false; while((checkInButton.isDisplayed() || forceInButton.isDisplayed()) &&
 * clicked == false) { System.out.println("Found: " + found );
 * while(!isElementPresent(By.className("hd-spinner"))) {
 * 
 * System.out.println("LOADING"); if(loadingIcon.isDisplayed() == false) found =
 * false; } System.out.println("TEST");
 * 
 * 
 * if(checkInButton.isDisplayed()) {
 * System.out.println("User is signed up for this class"); gui.good.add(x);
 * clicked = true; } else if(forceInButton.isDisplayed()) {
 * System.out.println("User Isn't Signed up for this class"); gui.bad.add(x);
 * clicked = true; }
 * 
 * } /*TimeUnit.MILLISECONDS.sleep(4000); try {
 * driver.findElement(By.id("chkbtn"));
 * if(driver.findElement(By.id("chkbtn")).isDisplayed()){
 * System.out.println("User is signed up for this class"); gui.good.add(x);
 * //driver.findElement(By.id("chkbtn")).click(); }
 * 
 * } catch (NoSuchElementException u) { System.out.println(u); }
 */
/*
 * try { driver.findElement(By.id("forcebtn"));
 * if(driver.findElement(By.id("forcebtn")).isDisplayed()){
 * System.out.println("User Isn't Signed up for this class"); gui.bad.add(x);
 * //driver.findElement(By.id("forcebtn")).click(); } } catch
 * (NoSuchElementException u) { System.out.println(u); } } if(!nonExistent) {
 * WebElement tableRow = baseTable.findElement(By.id("tr_nav1")); WebElement
 * cellINeed = tableRow.findElement(By.xpath("//td[1]")); try {
 * //TimeUnit.SECONDS.sleep(2); System.out.println("FIRST CLICK");
 * HoverAndClick(driver,cellINeed,cellINeed); } catch
 * (StaleElementReferenceException e) { TimeUnit.SECONDS.sleep(3); try{
 * System.out.println("SECOND CLICK");
 * HoverAndClick(driver,cellINeed,cellINeed); } catch
 * (StaleElementReferenceException f) {
 * 
 * } } System.out.println("TRIED HOVER AND CLICK"); Dimension dimension = new
 * Dimension(1400,800); driver.manage().window().setSize(dimension);
 * TimeUnit.SECONDS.sleep(2); try {
 * driver.findElement(By.id("activity_4")).click(); } catch
 * (NoSuchElementException u) { TimeUnit.SECONDS.sleep(3);
 * driver.findElement(By.id("activity_4")).click(); }
 * 
 * TimeUnit.MILLISECONDS.sleep(4000); try { driver.findElement(By.id("chkbtn"));
 * if(driver.findElement(By.id("chkbtn")).isDisplayed()){
 * System.out.println("User is signed up for this class"); gui.good.add(x);
 * //driver.findElement(By.id("chkbtn")).click(); }
 * 
 * } catch (NoSuchElementException u) { System.out.println(u); } try {
 * driver.findElement(By.id("forcebtn"));
 * if(driver.findElement(By.id("forcebtn")).isDisplayed()){
 * System.out.println("User Isn't Signed up for this class"); gui.bad.add(x);
 * //driver.findElement(By.id("forcebtn")).click(); } } catch
 * (NoSuchElementException u) { System.out.println(u); } }
 * driver.get("https://qccloud.net/checkin/search"); } driver.close();
 * gui.resultGUI();
 * 
 * 
 * }
 * 
 * public static void HoverAndClick(WebDriver driver, WebElement elementToHover,
 * WebElement elementToClick) { Actions action = new Actions(driver);
 * action.moveToElement(driver.findElement(By.xpath(
 * "//tbody//tr[@id='tr_nav1']//td[1]"))).doubleClick().build().perform(); }
 * 
 * 
 * 
 * }
 */
