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
	static String UB = "";
	static String UU = "";
	static String UP = "";
	static boolean found;
	static String mID;
	static String fN;
	static String lN;
	static boolean hasID;
	static String pxpath;
	static final String QCCLOUD_WEBSITE = "https://qccloud.net/account/login";

	public static void spinnerChecker() {
		while (isElementPresent(By.className("hd-spinner"))) {
		}
	}

	/*
	 * #Outputs the latest credits according to the github
	 * #https://github.com/LastBubbleMaster/qccloudcheckin
	 */
	public static void credits() {
		System.out.println("[][][][][][][][][][][][][][][]");
		System.out.println(" QuikCheckCloud Check-In Tool ");
		System.out.println(" Developed By: Nick Karlovich ");
		System.out.println("        Version 1.1.0         ");
		System.out.println("        July, 13, 2017        ");
		System.out.println("[][][][][][][][][][][][][][][]");

	}

	/*
	 * #Starts the Chrome Webdriver
	 */
	public static void chromeSetup() {
		String exePath = "H:/Desktop/chrome Driver/chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", exePath);
		driver = new ChromeDriver();
		System.out.println("-absdfsdfsdf-");
		wait = new FluentWait<WebDriver>(driver).withTimeout(6, TimeUnit.SECONDS).pollingEvery(20,
				TimeUnit.MILLISECONDS);
	}

	/*
	 * Connects to a website passed parameter
	 */
	public static void connectToWebsite(String website) {
		System.out.println("Attempting to connect to: " + website);
		driver.get(website);
		System.out.println("Successfully connected to: " + website);

	}

	//Unused?
	public static void getName() {
		connectToWebsite("https://qccloud.net/checkin/search");
		System.out.println("--");
		System.out.println(driver.findElement(By.id("tr_nav1")).getText());
		System.out.println("--");
		System.out.println(driver.findElement(By.xpath("//tr[@id='tr_nav1']//td[1]")));
		System.out.println("--");

	}

	/*
	 * #Searches through the webpage table and compares it to passed value
	 * #If the data of the Swimmer Object is equal to the data found within
	 * the iteration it will return the xpath of the swimmer in the list.
	 * #Example xPath: "tr_nav
	 */
	public static String xpathGetter(Swimmer test1) {
		String xpath = "";
		int counter = 0;
		found = false;
		System.out.println("                  | memID | Last Name        | First Name    |");
		do {
			counter++;
			xpath = "tr_nav";
			xpath = xpath + Integer.toString(counter);
			//DEBUG System.out.println(xpath);
			//System.out.println("Counter" + Integer.toString(counter));

			/* 
			* Checks if the row in the table with the xpath "tr_nav" + whatever row its on
			* If it returns true that means there is data, if false that means there are no
			* more entries on the page
			*/
			if (isElementPresent(By.id(xpath))) {
				System.out.println("ELEMENT IS PRESENT|-------|------------------|---------------|");
				//gottenSwim is all the data in the row in the table
				//Example: "2376  Martin  Aaron    555-555-5555  Mbr-Add-on"
				String gottenSwim = driver.findElement(By.id(xpath)).getText();
				//DEBUG System.out.println(gottenSwim);
				//gottenSwim = gottenSwim.toLowerCase();
				//TODO make file all lowercase so case isn't a problem

				//Remove the first value from gottenSwim, the ID
				mID = gottenSwim.substring(0, gottenSwim.indexOf("  "));
				gottenSwim = gottenSwim.substring(gottenSwim.indexOf("  ") + 2);
				//DEBUG System.out.println(gottenSwim);
				lN = gottenSwim.substring(0, gottenSwim.indexOf("  "));
				gottenSwim = gottenSwim.substring(gottenSwim.indexOf("  ") + 2);
				//DEBUG System.out.println(gottenSwim);
				fN = gottenSwim.substring(0, gottenSwim.indexOf("  "));
				//System.out.println("mID|" + mID + "|lN|" + lN + "|fN|" + fN + "|");

				System.out.printf("%17s %1s %5s %1s %-16s %1s %-13s %1s\n", "Data found in row", "|", mID, "|", lN, "|",
						fN, "|");
				//System.out.println("|" + test1.getBarcode() + "|" + test1.getFirstName() + "|" + test1.getLastName() + "|");
				System.out.printf("   %12s   %1s %5s %1s %-16s %1s %-13s %1s\n", "Looking for?", "|",
						test1.getBarcode(), "|", test1.getLastName(), "|", test1.getFirstName(), "|");
				if (hasID) {
					if (test1.getBarcode().equalsIgnoreCase(mID) && test1.getFirstName().equalsIgnoreCase(fN)
							&& test1.getLastName().equalsIgnoreCase(lN)) {
						found = true;
					}
				} else if (test1.getFirstName().equalsIgnoreCase(fN) && test1.getLastName().equalsIgnoreCase(lN)) {
					found = true;
				}
			}
			//This while loop loops while the swimmer hasn't been found and there is still rows available

		} while (!found && isElementPresent(By.id(xpath)));

		//Either found user or ran out of elements
		if (found)
			//end of this program
			return xpath;
		else
			//triggers program to go to the next page and run this program until success or no values remain
			return "NEXTPAGE";
	}

	/*
	 * #Triggered when xpathGetter(Swimmer) returns Next Page
	 * #If there are no more pages it will return a value that will exit this
	 */
	public static String goToNextPage() {
		if (isElementPresent(By.className("next"))) {
			driver.findElement(By.className("next")).click();
			double startTime = System.currentTimeMillis();
			try {
				counter1 = 0;
				spinnerChecker();
				double endTime = System.currentTimeMillis() - startTime;
				System.out.println("Next Page Spinner Time: " + endTime / 1000 + " seconds");
			} catch (StaleElementReferenceException e2) {
				e2.printStackTrace();
			}
			return "NEXT PAGE";
		} else {
			return "NO NEXT PAGE";
		}
	}

	//Looping function for checking webpage table for swimmer
	public static String getxpath(Swimmer test) {

		if (test.getBarcode().equals("N/A"))
			hasID = false;
		else
			hasID = true;

		//DEBUG System.out.println("HAS A BARCODE: " + hasID);
		String xpath = xpathGetter(test);
		while (xpath.equals("NEXTPAGE")) {
			System.out.println("NEXT PAGE RAN");
			if (goToNextPage().equals("NO NEXT PAGE")) {
				gui.unable.add(test);
				System.out.println("NO NEXT PAGE");
				return "NO PATH, USER NOT FOUND";
			} else {
				//Keep Searching pages until fail or finds Swimmer
				xpath = xpathGetter(test);
			}
		}
		return xpath;
	}

	//#Closes the webdriver
	public static void shutdown() {
		driver.close();
	}

	/*
	 * #Authenticates the user of program has credentials to access QCCloud
	 * #NOT SECURE
	 */
	public static boolean authenticateUser() throws InterruptedException, StaleElementReferenceException {
		chromeSetup();
		//Makes the window look better, no real purpose
		System.out.println("Java");
		driver.get("https://www.google.com");
		driver.manage().window().setSize(new Dimension(500, 700));
		connectToWebsite(QCCLOUD_WEBSITE);
		//Once is to end the program after the user fails the authentication to slow down cracking attempts
		boolean once = true;
		/*
		 * Constantly checking website and taking data entered to be used later for checking in
		 * It determines if you have the credientials based on what the page shows after you hit enter
		 * It will show  danger text if you entered the wrong credentials
		 */
		while (driver.getCurrentUrl().equals("https://qccloud.net/account/login") && once) {

			try {
				try {
					UB = driver.findElement(By.xpath("//input[@id='bname']")).getAttribute("value");
					UU = driver.findElement(By.xpath("//input[@id='login']")).getAttribute("value");
					UP = driver.findElement(By.xpath("//input[@id='password']")).getAttribute("value");
				} catch (StaleElementReferenceException e1) {
					e1.printStackTrace();
				} catch (NoSuchElementException e1) {
					e1.printStackTrace();
				}
				if (driver.findElement(By.className("text-danger")).getText()
						.equals("Invalid Business Name, Username or Password. Please try again.") && once) {
					gui.errorGUI("Couldn't verify the credentials of the user");
					once = false;
					driver.close();
					return false;
				}
			} catch (StaleElementReferenceException e) {
				e.printStackTrace();
			} catch (NoSuchElementException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//If browser shows this url it will pass and allow access
		wait.until(ExpectedConditions.urlToBe("https://qccloud.net/"));
		driver.close();
		return true;
	}

	//Testing if an element is present for spinner loading
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

	/*
	 * #Function to login automated browser while checking in swimmers
	 * #Uses login data from the authentication page
	 * #It also immediately afterwards connects to the checkin page
	 */
	public static void login() {
		//Debug
		if (UB == null || UU == null || UP == null) {
			System.out.println("ENABLE AUTHENTICATION");
		}

		driver.findElement(By.id("bname")).sendKeys(UB);
		driver.findElement(By.id("login")).sendKeys(UU);
		driver.findElement(By.id("password")).sendKeys(UP);
		System.out.println("Logging in as " + UU);
		driver.findElement(By.name("commit")).click();
		System.out.println("Logged in as " + UU);
		System.out.println("Accessing Check In");
		connectToWebsite("https://qccloud.net/checkin/search");
	}

	/*
	 * #The program that actually does the clicking on the webiste
	 */
	public static void loginSwimmers(ArrayList<Swimmer> swimmer) throws InterruptedException {
		//Timer to test how long it takes to login all the swimmers
		double allSwimStart = System.currentTimeMillis();

		for (Swimmer x : swimmer) {
			double swimStart = System.currentTimeMillis();
			System.out.println("\n" + "====================");
			System.out.print("User: ");
			System.out.println(x);
			System.out.println("====================");
			boolean nonExistent = false;

			//Finds seach box and searches by Lastname
			WebElement searchBox = driver.findElement(By.id("query"));
			WebElement mySelectElement = driver.findElement(By.id("field"));
			Select dropdown = new Select(mySelectElement);
			dropdown.selectByIndex(2);
			//SERACH BY LAST NAME                         ***********
			driver.findElement(By.id("query")).sendKeys(x.getLastName());
			//boolean found = true;
			//individual user timer
			double startTime = System.currentTimeMillis();

			try {
				/*
				 * When you type something into the text field above a
				 * "Spinner" gif appears on page while the data loads
				 * so this is to check that the page is done loading
				 * before moving on.  If this doesn't work then when the 
				 * program searches for an element it wont appear and 
				 * this is faster than just guessing on a time to have the 
				 * program sleep for.  This type of while loop is used
				 * often throughout this portion of code.
				 */

				//TODO REPLACE THESE WITH THE FUNCTION ABOVE
				/*while (isElementPresent(By.className("hd-spinner"))) {
					if (!isElementPresent(By.className("hd-spinner")))
						found = false;
				}*/
				spinnerChecker();
				//Time for all page elements to load
				double endTime = System.currentTimeMillis() - startTime;
				System.out.println("Query Spinner Time: " + endTime / 1000 + " seconds");

			} catch (StaleElementReferenceException e2) {
				e2.printStackTrace();
			}

			/*
			 * "Second" enter press, the "First" one occurs when you type the name into the box
			 * This makes the list of objects on the page smaller
			 * Ex:If you serach the last name "Jon" but don't press enter then people
			 * with the last name "Jon" will show up but also people with the last name "Jonathan"
			 * This second enter press should remove those entries with data that only matches the
			 * first part of the search.
			 */
			wait.until(ExpectedConditions.elementToBeClickable(searchBox));
			searchBox.sendKeys(Keys.ENTER);

			//Second search time for enter press search
			startTime = System.currentTimeMillis();
			try {
				spinnerChecker();
				//Ending of user "enter" press search time
				double endTime = System.currentTimeMillis() - startTime;
				System.out.println("User Search Spinner Time: " + endTime / 1000 + " seconds");
			} catch (StaleElementReferenceException e2) {

			}
			//TODO similar to one above, what is this used for
			if (!isElementPresent(By.className("hd-spinner")))
				found = false;

			/*
			 * Checks if a table exists meaning there are users that match criteria
			 * If IF statement returns true it means there is no table and the program
			 * has "failed" to find the user, it then sets nonExistent to true
			 */
			if (!(isElementPresent(By.xpath("//table")))) {
				gui.unable.add(x);
				nonExistent = true;
				System.out.println("User with this last name doesn't exist");
			}

			/*
			 * If there is atleast a user in the table, maybe not the one we are lookign for
			 * Ex: if the user is on the next page, it will continue despite not seeing the 
			 * actual user that we want because it doesn't know that yet.
			 */
			if (!nonExistent) {
				/*
				 * Timer to determine the time it takes to go through each user and determine
				 * if its the right person and if it is, check them in
				 */
				startTime = System.currentTimeMillis();
				try {
					spinnerChecker();
					double endTime = System.currentTimeMillis() - startTime;
					System.out.println("Loading Spinner: " + endTime / 1000 + " seconds");
				} catch (StaleElementReferenceException e2) {

				}

				// START LOOKING FOR CORRECT ROW WITH USER
				WebElement swimmerLine = null;
				String xpath = getxpath(x);
				pxpath = "//tr[@id='" + xpath + "']//td[1]";
				//DEBUG System.out.println("xpath run with xpath as " + "//tr[@id='" + xpath + "']//td[1]");
				//"NO PATH USER NOT FOUND" is returned once the looping function that goes through
				//all available pages doesn't find anything
				if (!(xpath.equals("NO PATH USER NOT FOUND"))) {
					//swimmerLine = driver.findElement(By.xpath("//tr[@id='" + xpath + "']//td[1]"));
					swimmerLine = driver.findElement(By.xpath(pxpath));
					spinnerChecker();
					HoverAndClick(driver, swimmerLine, swimmerLine);

					//DEBUG System.out.println("TRIED HOVER AND CLICK");
					//Resizes window so that the Swimlesson button is visible on the screen
					Dimension dimension = new Dimension(1400, 800);
					driver.manage().window().setSize(dimension);
					//Activity 4 is the swimlesson activity
					swimLessonElement = driver.findElement(By.id("activity_4"));
					wait.until(ExpectedConditions.elementToBeClickable(swimLessonElement));
					swimLessonElement.click();

					checkInButton = driver.findElement(By.id("chkbtn"));
					forceInButton = driver.findElement(By.id("forcebtn"));
					boolean clicked = false;
					spinnerChecker();
					startTime = System.currentTimeMillis();
					while ((checkInButton.isDisplayed() || forceInButton.isDisplayed()) && clicked == false) {
						counter1 = 0;
						double endTime = System.currentTimeMillis() - startTime;
						System.out.println("Force/Safe Login Spinner: " + endTime / 1000 + " seconds");

						if (checkInButton.isDisplayed()) {
							System.out.println("User is signed up for this class");
							gui.good.add(x);
							clicked = true;
							if (gui.SafeMode) {
								System.out.println("runs");
								//checkInButton.click();
							}
						} else if (forceInButton.isDisplayed()) {
							System.out.println("User Isn't Signed up for this class");
							gui.bad.add(x);
							clicked = true;
							if (gui.SafeMode) {
								System.out.println("runs2");
								//forceInButton.click();
							}
						}

					}
				} else {
					System.out.println("User Couldn't be found in system");
				}
			}
			//DEBUG System.out.println("SWIMMER IN LOOP " + x.toString());
			//DEBUG System.out.println(
			//       "WHAT WEBPATGE FINDS" + driver.findElement(By.xpath("//div[@class='page-header']//h4")).getText());
			connectToWebsite("https://qccloud.net/checkin/search");
			//Total time of swimmer check in process
			double endTime = System.currentTimeMillis() - swimStart;
			System.out.println("Total swimmer check-in time: " + endTime / 1000 + " seconds");
		}
		//Total time of all swimmers being checked in
		double endTime = System.currentTimeMillis() - allSwimStart;
		System.out.println("Entire Process " + endTime / 1000 + " seconds");
		gui.finalCheckPanel = new JPanel();
		driver.close();
		gui.resultGUI();

	}

	//Hovers and clicks on certain object in the program
	public static void HoverAndClick(WebDriver driver, WebElement elementToHover, WebElement elementToClick) {
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(By.xpath(pxpath))).doubleClick().build().perform();
	}

}