package swimLesson;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileSystemView;

import org.openqa.selenium.NoSuchSessionException;

public class gui {

	static boolean close = false;
	static JFrame finalCheckFrame = new JFrame("Check-In Overview");
	static JPanel finalCheckPanel = new JPanel();

	static int numOfLessonObjects = 18;
	static int swimClassSize;
	static int row = 0;
	static int col = 0;
	static int bigcounter = 0;
	static int counterPow = 1;
	static boolean DarkTheme;
	static boolean SafeMode;
	static int counter = 0;
	static String[] daysOfTheWeek = { "sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday" };

	//Keeps track of the success/force/failure of checking swimmers in
	static ArrayList<Swimmer> good = new ArrayList<Swimmer>();
	static ArrayList<Swimmer> bad = new ArrayList<Swimmer>();
	static ArrayList<Swimmer> unable = new ArrayList<Swimmer>();

	//Gets swimmers from checkboxes and put into this array
	static ArrayList<String> checkedSwimText;
	//Master list of all swimmers shown in GUI
	static ArrayList<Swimmer> checkedSwimSwimmers = new ArrayList<Swimmer>();

	/*
	 * Prints to the console the Swimmers and what
	 * "group" they were placed in when they were checked in.  
	 */
	public static void printGoodBadUnable() {
		
		System.out.println();
		System.out.print("GOOD:   [");
		for (Swimmer x : good)
			System.out.print(x + ",");
		System.out.println("]");
		System.out.print("BAD:    [");
		for (Swimmer x : bad)
			System.out.print(x + ",");
		System.out.println("]");
		System.out.print("UNABLE: [");
		for (Swimmer x : unable)
			System.out.print(x + ",");
		System.out.println("]");
		System.out.println();
	}

	/*
	 * #Create a GUI that shows the results of the Check-In
	 */
	public static void resultGUI() {
		int numberOfPanels = 3;
		JFrame outputFrame = new JFrame("Check In Result");

		//Panels that will hold the JLabels in
		JPanel goodPanel = new JPanel();
		JPanel badPanel = new JPanel();
		JPanel unablePanel = new JPanel();
		
		//This little bit may not be used every time, its a setting
		JPanel additionalOptionsPanel = new JPanel();
		additionalOptionsPanel.setBackground(Color.GRAY);
		numberOfPanels++;
		JLabel additionalOptionsLabel = new JLabel("Additional Options");
		JPanel additionalOptionsButtonPanel = new JPanel();
		
		goodPanel.setBackground(Color.GREEN);
		badPanel.setBackground(Color.RED);
		unablePanel.setBackground(Color.CYAN);
		outputFrame.setLayout(new GridLayout(numberOfPanels, 0));

		int goodSize = good.size();
		int badSize = bad.size();
		int unableSize = unable.size();

		/*
		 * This bit makes it so that if the array is empty then only 2 labels are created in the area
		 * One to say "Users entered in (successfully/forcefully/unable)
		 * The other to say NONE
		 * 
		 * If the size is greater than one then the first line is still output but then instead of "NONE"
		 * every user is put in instead and the amount of "JLables" added is equivalent to the number
		 *  of users in a group + 1 for the title
		 */
		if (goodSize == 0) {
			goodPanel.setLayout(new GridLayout(2, 0));
		} else {
			goodPanel.setLayout(new GridLayout(goodSize + 1, 0));
		}
		if (badSize == 0) {
			badPanel.setLayout(new GridLayout(2, 0));
		} else {
			badPanel.setLayout(new GridLayout(badSize + 1, 0));
		}
		if (unableSize == 0) {
			unablePanel.setLayout(new GridLayout(2, 0));
		} else {
			unablePanel.setLayout(new GridLayout(unableSize + 1, 0));
		}

		JLabel goodLabel = new JLabel("Users Entered In Successfully");
		JLabel badLabel = new JLabel("Users Entered In Forcefully");
		JLabel unableLabel = new JLabel("Users were not entered in due to errors");

		goodPanel.add(goodLabel);
		badPanel.add(badLabel);
		unablePanel.add(unableLabel);

		//Console output version of GUI
		printGoodBadUnable();

		if (goodSize == 0) {
			goodPanel.add(new JLabel("NONE"));
		} else {
			for (Swimmer y : good) {
				goodPanel.add(new JLabel(y.getLastName() + ", " + y.getFirstName() + " " + y.getBarcode()));
			}
		}
		if (badSize == 0) {
			badPanel.add(new JLabel("NONE"));
		} else {
			for (Swimmer y : bad) {
				badPanel.add(new JLabel(y.getLastName() + ", " + y.getFirstName() + " " + y.getBarcode()));
			}
		}
		if (unableSize == 0) {
			unablePanel.add(new JLabel("NONE"));
		} else {
			for (Swimmer y : unable) {
				unablePanel.add(new JLabel(y.getLastName() + ", " + y.getFirstName() + " " + y.getBarcode()));
			}
		}

		//Removes all users from 
		good = new ArrayList<Swimmer>();
		bad = new ArrayList<Swimmer>();
		unable = new ArrayList<Swimmer>();

		checkedSwimText = new ArrayList<String>();

		outputFrame.add(goodPanel);
		outputFrame.add(badPanel);
		outputFrame.add(unablePanel);
		if(CSVHandler.getSetting("noAccCreateMembership").equals("true")) {
			additionalOptionsButtonPanel.setLayout(new GridLayout(0,2));
			additionalOptionsButtonPanel.add(createNoAcctCreateMembershipButton());
			additionalOptionsPanel.add(additionalOptionsButtonPanel);
			outputFrame.add(additionalOptionsPanel);
		}
		outputFrame.setSize(300, 800);
		outputFrame.setLocationRelativeTo(null);
		outputFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		outputFrame.setVisible(true);
	}

	/*
	 * #Display of up to 9 checkboxes per class that can be selected to be added to a list for automated check-in
	 */
	public static void createSwimmerCheckboxSelectionGUI(ArrayList<Swimmer> allSwimmerList)
			throws InterruptedException {
		JFrame swimmerCheckboxFrame = new JFrame("Swimmer Checking");
		JPanel swimmerCheckboxPanel = new JPanel();
		//TODO is this unnecessary
		ArrayList<Swimmer> swimmers = allSwimmerList;
		int swimSize = allSwimmerList.size();
		//TODO what is the point of this/ test
		if (swimSize > 9)
			errorGUI(
					"Program detected that the class size was greater than 9.  Either fix the SwimClassSheet.txt file or make the class into seperate classes.");
		swimmerCheckboxPanel.setLayout(new GridLayout(10, 0));
		for (Swimmer x : swimmers) {
			JCheckBox swimmerCheckbox = new JCheckBox();
			swimmerCheckbox.setText(x.getLastName() + ", " + x.getFirstName() + ":" + x.getBarcode());
			swimmerCheckboxPanel.add(swimmerCheckbox);
		}

		JButton backButton = new JButton("Back without saving");
		backButton.setBackground(Color.RED);
		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				swimmerCheckboxFrame.remove(swimmerCheckboxPanel);
				swimmerCheckboxFrame.dispose();
			}
		});

		JButton saveButton = new JButton();
		saveButton.setText("Save");
		saveButton.setBackground(Color.GRAY);
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				checkedSwimText = manageCheckedCheckboxes(swimmerCheckboxPanel);
				boolean noSwimmer = false;
				boolean duplicate = false;
				if (checkedSwimText.size() == 0) {
					//Nothing Happens no swimmers
					noSwimmer = true;

					//making sure to output with correct grammar
				} else if (checkedSwimText.size() > 1) {
					System.out.println("Swimmers Added: ");
				} else {
					System.out.println("Swimmer Added: ");
				}

				//If there are swimmers
				if (!noSwimmer) {
					System.out.println("------------");
					for (String swimmer : checkedSwimText) {
						//reset for each swimmer, for checking if a given swimmer is a duplicate/ already added to the check-in list
						duplicate = false;
						//DEBUG System.out.println(swimmer);
						String lN = swimmer.substring(0, swimmer.indexOf(","));
						swimmer = swimmer.substring(swimmer.indexOf(",") + 2);
						String fN = swimmer.substring(0, swimmer.indexOf(":"));
						swimmer = swimmer.substring(swimmer.indexOf(":") + 1);
						String mID = swimmer.substring(0);
						Swimmer newSwimmer = new Swimmer(fN, lN, mID);
						//TODO setting to be able to add the same person/ check in the same person twice/ add to list
						for (Swimmer swimmerFromList : checkedSwimSwimmers) {
							if (newSwimmer.equals(swimmerFromList)) {
								duplicate = true;
								System.out.println("This swimmer is a duplicate so it isn't being added");
							}
						}
						//If it's not a duplicate
						if (!duplicate) {
							checkedSwimSwimmers.add(newSwimmer);
						}
					}

					for (Swimmer u : checkedSwimSwimmers) {
						System.out.println(u);
					}

					System.out.println("-==-==-");
					swimmerCheckboxFrame.dispose();
				}

			}

		});
		swimmerCheckboxPanel.add(saveButton);
		swimmerCheckboxPanel.add(backButton);
		swimmerCheckboxFrame.add(swimmerCheckboxPanel);
		swimmerCheckboxFrame.setSize(200, 500);
		swimmerCheckboxFrame.setLocationRelativeTo(null);
		swimmerCheckboxFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		swimmerCheckboxFrame.setVisible(true);
	}

	/*
	 * #Determines what check boxes are selected in the class selection screen and adds them to a list
	 */
	public static ArrayList<String> manageCheckedCheckboxes(final Container c) {
		Component[] comps = c.getComponents();
		ArrayList<String> checkedSwimmers = new ArrayList<String>();
		for (Component comp : comps) {
			if (comp instanceof JCheckBox) {
				JCheckBox box = (JCheckBox) comp;
				if (box.isSelected()) {
					String barcode = box.getText();
					checkedSwimmers.add(barcode);
				}
			}
		}
		return checkedSwimmers;
	}

	/*
	 * #CheckIn button UI, shows all users to be checked in with check-in button
	 * and back button.  This is the final step before the webdriver starts
	 */
	public static void confirmSignInGUI() {

		finalCheckPanel = new JPanel();
		finalCheckPanel.setLayout(new GridLayout(checkedSwimSwimmers.size() + 2, 0));
		//DEBUG System.out.println(checkedSwimSwimmers.size() + " : SIZE");
		for (Swimmer y : checkedSwimSwimmers) {
			//DEBUG Swimmer y = SwimClass.getUserByBarcode(x);
			finalCheckPanel.add(new JLabel(y.getFirstName() + ", " + y.getLastName() + " " + y.getBarcode()));
		}
		JButton finalCheck = new JButton("Check-In");
		JButton back = new JButton("Back");
		finalCheck.setBackground(Color.GREEN);
		back.setBackground(Color.RED);

		finalCheck.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				checkIn.chromeSetup();
				checkIn.connectToWebsite(checkIn.QCCLOUD_WEBSITE);
				checkIn.login();
				try {
					checkIn.loginSwimmers(checkedSwimSwimmers);
					finalCheckFrame.dispose();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

			}

		});
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				finalCheckFrame.remove(finalCheckPanel);
				finalCheckFrame.dispose();
			}

		});
		finalCheckPanel.add(finalCheck);
		finalCheckPanel.add(back);
		finalCheckFrame.add(finalCheckPanel);
		finalCheckFrame.setSize(300, 800);
		finalCheckFrame.setLocationRelativeTo(null);
		finalCheckFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		finalCheckFrame.setVisible(true);

	}

	/*
	 * #Runs a small GUI that opens a file chooser for the user to find their "SwimClassSheet.txt" file
	 */
	public static void firstTimeGUI() {
		JFrame firstTimeFrame = new JFrame("First Time Setup");
		JButton save = new JButton("Set as file path");
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TesterClass.real_path_name = fileChooser();
				firstTimeFrame.dispose();
			}

		});

		JPanel firstTimePanel = new JPanel();
		firstTimePanel.setLayout(new BoxLayout(firstTimePanel, BoxLayout.PAGE_AXIS));

		JLabel x1 = new JLabel("                Select       ");
		JLabel x2 = new JLabel("  'SwimClassSheet.txt'");
		JLabel x3 = new JLabel("                   file        ");

		x1.setHorizontalAlignment(JLabel.CENTER);
		x1.setVerticalAlignment(JLabel.TOP);
		firstTimePanel.add(x1, BorderLayout.CENTER);
		x2.setHorizontalAlignment(JLabel.CENTER);
		x2.setVerticalAlignment(JLabel.TOP);
		firstTimePanel.add(x2, BorderLayout.CENTER);
		x3.setHorizontalAlignment(JLabel.CENTER);
		x3.setVerticalAlignment(JLabel.TOP);
		firstTimePanel.add(x3, BorderLayout.CENTER);

		save.setHorizontalAlignment(JLabel.CENTER);
		save.setVerticalAlignment(JLabel.BOTTOM);
		firstTimePanel.add(save, BorderLayout.CENTER);
		firstTimePanel.add(save);
		firstTimeFrame.add(firstTimePanel);
		firstTimeFrame.setSize(150, 150);
		firstTimeFrame.setLocationRelativeTo(null);
		firstTimeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		firstTimeFrame.setVisible(true);

	}

	/*
	 * #Makes a small GUI that shows the credits
	 */
	public static void creditsGUI() {
		JFrame frame = new JFrame("Credits");
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 0));
		JLabel l1 = new JLabel("                      QuikChekCloud Check-In");
		JLabel l2 = new JLabel("                                    Created By      ");
		JLabel l3 = new JLabel("                                 Nick Karlovich    ");
		JLabel l4 = new JLabel("                                      Contact:       ");
		JLabel l5 = new JLabel("                          nick4pie@gmail.com  ");

		panel.add(l1);
		panel.add(l2);
		panel.add(l3);
		panel.add(l4);
		panel.add(l5);
		frame.add(panel);
		frame.setSize(300, 300);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);

	}

	/*
	 * #Returns the status of the radio button
	 * #Currently only used to get Radiobuttonstatus of Dark Theme 8/17/17
	 */
	public static boolean getRadioButtonStatus(JRadioButton jrb) {
		if (jrb.isSelected())
			return true;
		else
			return false;
	};

	public static JRadioButton noAccountCreateMembershipButtonCreate() {
		JRadioButton noAccCreateMembership = new JRadioButton("No Account Create Membership");
		
		noAccCreateMembership.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(getRadioButtonStatus(noAccCreateMembership)) {
					try {
						CSVHandler.setSetting("noAccCreateMembership", true);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					System.out.println("Status of Program: True");
				} else {
					try {
						CSVHandler.setSetting("noAccCreateMembership", false);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					System.out.println("Status of Program: False");
				}
				System.out.println("Status of File: " + CSVHandler.getSetting("noAccCreateMembership"));
			}
		});
		return noAccCreateMembership;
		
	}
	
	public static JRadioButton darkModeButtonCreate() {
		JRadioButton darkmode = new JRadioButton("Dark Theme");
		darkmode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (getRadioButtonStatus(darkmode)) {
					try {
						CSVHandler.setSetting("Dark Theme", true);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					System.out.println("Status of Program: True");
				} else {
					try {
						CSVHandler.setSetting("Dark Theme", false);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					System.out.println("Status of Program: False");
				}
				System.out.println("Status of File: " + CSVHandler.getSetting("Dark Theme"));
			}
		});
		return darkmode;
	}
	
	
	public static JRadioButton safeModeButtonCreate() {
		JRadioButton safemode = new JRadioButton("Safe Mode");

		safemode.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (getRadioButtonStatus(safemode)) {
					try {
						CSVHandler.setSetting("Safe Mode", true);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					System.out.println("Status of Program: True");
				} else {
					try {
						CSVHandler.setSetting("Safe Mode", false);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					System.out.println("Status of Program: False");
				}
				System.out.println("Status of File: " + CSVHandler.getSetting("Safe Mode"));
			}
		});
		return safemode;
	}

	public static JMenuItem creditsButtonCreate() {
		JMenuItem credits = new JMenuItem("Credits");

		credits.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				creditsGUI();
			}
		});
		return credits;
	}

	public static JLabel safemodeStatusCreate() {
		String status = "";
		if(SafeMode) {
			status = "enabled";
		} else if(!SafeMode){
			status = "disabled";
		} else {
			System.out.println("ERROR");
		}
		JLabel safemodeLabel = new JLabel("Safe Mode is " + status);
		return safemodeLabel;
	}
	
	//Clear values in checked-boxes array button
	public static JButton createClearAllButton() {
		JButton clearButton = new JButton();
		clearButton.setText("Clear all selected users");
		clearButton.setBackground(Color.WHITE);
		clearButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("TEST");
				checkedSwimText = new ArrayList<String>();
				checkedSwimSwimmers = new ArrayList<Swimmer>();
				finalCheckFrame.remove(finalCheckPanel);
				finalCheckFrame.dispose();
				finalCheckFrame = new JFrame("Check-In Overview");
				finalCheckPanel = new JPanel();
			}
		});
		return clearButton;
	}

	public static JButton createNoAcctCreateMembershipButton() {
		//check in button
		JButton noAcctButton = new JButton();
		noAcctButton.setText("<html>Create Memberships/nfor users who/n don't have memberships</html>");
		noAcctButton.setBackground(Color.BLUE);
		noAcctButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO
				//METHOD TO BE RUN
			}
		});
		return noAcctButton;
	}
	
	public static JButton createCheckInButton() {
		//check in button
		JButton checkInButton = new JButton();
		checkInButton.setText("Check In");
		checkInButton.setBackground(Color.GREEN);
		checkInButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				confirmSignInGUI();
			}
		});
		return checkInButton;
	}

	public static JButton createExitButton(JFrame frameToExit) {
		//Button to exit program
		JButton exitButton = new JButton();
		exitButton.setText("Exit");
		exitButton.setBackground(Color.RED);
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(CSVHandler.getSetting("Sorting Order"));
				frameToExit.dispose();
				try {
					checkIn.driver.close();
				} catch (NoSuchSessionException e1) {
					e1.printStackTrace();
				} catch (NullPointerException e1) {
					e1.printStackTrace();
				}
				System.exit(1);
			}
		});
		return exitButton;
	}

	public static JComboBox createComboBox(String[] boxOptions) {
		//Sort drop down menu
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox sortBox = new JComboBox(boxOptions);
		sortBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Can you see if this action is performed?");
				@SuppressWarnings("rawtypes")
				JComboBox cb = (JComboBox) e.getSource();
				String sortOption = (String) cb.getSelectedItem();
				try {
					CSVHandler.setSetting("Sorting Order", sortOption);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		return sortBox;
	}

	/*
	 * #Main Swim Lesson Chooser GUI
	 */
	public static void createGUI(ArrayList<SwimClass> swim) {

		int sortOption = 0;
		swimClassSize = CreateClasses.getSwimClassesSize();

		if (CSVHandler.getSetting("Sorting Order").equals("default")) {
			sortOption = 0;
		} else if (CSVHandler.getSetting("Sorting Order").equals("teacher")) {
			sortOption = 1;
		} else if (CSVHandler.getSetting("Sorting Order").equals("day")) {
			sortOption = 2;
		} else if (CSVHandler.getSetting("Sorting Order").equals("time")) {
			sortOption = 3;
		} else if (CSVHandler.getSetting("Sorting Order").equals("level")) {
			sortOption = 4;
		} else {
			System.out.println("ERROR not a valid option");
		}
		
		if(CSVHandler.getSetting("Safe Mode").equals("true")) {
			SafeMode = true;
		} else {
			SafeMode = false;
		}
		

		JFrame mainFrame = new JFrame("Swim Lesson Chooser " + new File(".").getAbsolutePath());
		JMenuBar menuBar = new JMenuBar();

		//Menus
		JMenu fileMenu = new JMenu("File");
		JMenu settingsMenu = new JMenu("Settings");
		JMenu about = new JMenu("About");

		JRadioButton darkmode = darkModeButtonCreate();
		JRadioButton noAccCreateMembership = noAccountCreateMembershipButtonCreate();
		JRadioButton safemode = safeModeButtonCreate();
		JMenuItem credits = creditsButtonCreate();

		settingsMenu.add(darkmode);
		settingsMenu.add(noAccCreateMembership);
		settingsMenu.add(safemode);
		about.add(credits);
		menuBar.add(fileMenu);
		menuBar.add(settingsMenu);
		menuBar.add(about);
		
		JPanel decision = new JPanel();
		JLabel safemodeLabel = safemodeStatusCreate();
		JButton exitButton = createExitButton(mainFrame);
		JButton checkInButton = createCheckInButton();
		JButton clearButton = createClearAllButton();

		JPanel mainPanel = new JPanel();
		JPanel subPanel;

		
		if (CSVHandler.getSetting("Dark Theme").equals("true")) {
			DarkTheme = true;
			mainPanel.setBackground(Color.GRAY);
			decision.setBackground(Color.GRAY);
			menuBar.setBackground(Color.LIGHT_GRAY);
			darkmode.setSelected(true);
		} else {
			DarkTheme = false;
			darkmode.setSelected(false);
			//DEBUG System.out.println("Dark Theme-" + CSVHandler.getSetting("Dark Theme") + "-");
		}
		
		if(CSVHandler.getSetting("Safe Mode").equals("true")) {
			safemode.setSelected(true);
			safemodeLabel.setForeground(Color.GREEN);
		} else {
			safemode.setSelected(false);
			safemodeLabel.setForeground(Color.RED);
		}
		
		if(CSVHandler.getSetting("noAccCreateMembership").equals("true")) {
			noAccCreateMembership.setSelected(true);
		}
		
		if (sortOption == 0) {
			for (int i = 0; i < CreateClasses.getSwimClassesSize() / 18 + 1; i++) {
				System.out.println("RUN");
				subPanel = new JPanel();
				subPanel.setLayout(new GridLayout(1, numOfLessonObjects));
				TitledBorder title;
				title = BorderFactory.createTitledBorder("All Classes");
				subPanel.setBorder(title);
				addSwimmersToPanel(subPanel, "default", "", swim);
				if(DarkTheme){
					subPanel.setBackground(Color.GRAY);
				}
				mainPanel.add(subPanel);
			}

		} else if (sortOption == 1) {//By Teacher
			ArrayList<String> teachersNames = CreateClasses.getTeachersNames();
			for (String x : teachersNames) {
				subPanel = new JPanel();
				subPanel.setLayout(new GridLayout(1, numOfLessonObjects));
				TitledBorder title;
				title = BorderFactory.createTitledBorder(x);
				subPanel.setBorder(title);
				addSwimmersToPanel(subPanel, "teacher", x, swim);
				if(DarkTheme){
					subPanel.setBackground(Color.GRAY);
				}
				mainPanel.add(subPanel);
			}
		} else if (sortOption == 2) {//By Day	
			ArrayList<String> classDays = CreateClasses.getClassDays();
			for (String x : classDays) {
				subPanel = new JPanel();
				subPanel.setLayout(new GridLayout(1, numOfLessonObjects));
				TitledBorder title;
				title = BorderFactory.createTitledBorder(x);
				subPanel.setBorder(title);
				addSwimmersToPanel(subPanel, "day", x, swim);
				if(DarkTheme){
					subPanel.setBackground(Color.GRAY);
				}
				mainPanel.add(subPanel);
			}
		} else if (sortOption == 3) {//By Time
			ArrayList<String> classTimes = CreateClasses.getClassTimes();
			for (String x : classTimes) {
				subPanel = new JPanel();
				subPanel.setLayout(new GridLayout(1, numOfLessonObjects));
				TitledBorder title;
				title = BorderFactory.createTitledBorder(x);
				subPanel.setBorder(title);
				addSwimmersToPanel(subPanel, "time", x, swim);
				if(DarkTheme){
					subPanel.setBackground(Color.GRAY);
				}
				mainPanel.add(subPanel);
			}
		} else if (sortOption == 4) {//By Level
			ArrayList<String> classLevels = CreateClasses.getClassLevels();
			for (String x : classLevels) {
				subPanel = new JPanel();
				subPanel.setLayout(new GridLayout(1, numOfLessonObjects));
				TitledBorder title;
				title = BorderFactory.createTitledBorder(x);
				subPanel.setBorder(title);
				addSwimmersToPanel(subPanel, "level", x, swim);
				if(DarkTheme){
					subPanel.setBackground(Color.GRAY);
				}
				mainPanel.add(subPanel);
			}
		} else {
			System.out.println("ERROR: Sort Option not included");
		}

		JPanel sortTitle = new JPanel();
		String[] sortOptions = { "default", "teacher", "day", "time", "level" };
		JComboBox sortBox = createComboBox(sortOptions);
		sortTitle.add(new JLabel("Sort By: "));
		sortTitle.add(sortBox);

		mainPanel.setLayout(new GridLayout(10, 1));



		
		
		sortBox.setSelectedIndex(sortOption);
		decision.add(safemodeLabel);
		decision.add(checkInButton);
		decision.add(exitButton);
		decision.add(clearButton);
		decision.add(sortTitle);
		mainPanel.add(decision);
		mainFrame.add(mainPanel);
		mainFrame.setJMenuBar(menuBar);
		mainFrame.setSize(1886, 1038);
		//mainFrame.setResizable(false);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);

	}

	//public static void yesAdd();

	public static void addSwimmersToPanel(JPanel destPanel, String sortBy, String modifier, ArrayList<SwimClass> swim) {
		counter = 0;
		System.out.println("swimClassSize:" + swimClassSize);
		if (sortBy.equals("default")) {
			for (int i = 0; i < swimClassSize; i++) {

				if ((i < counterPow * 18) && (i + 1 > (counterPow - 1) * 18)) {
					counter++;
					addSwimmersToPanel(destPanel, swim.get(i));
				}

			}
		} else {
			for (SwimClass x : swim) {
				if (sortBy.equals("teacher")) {
					if (modifier.equalsIgnoreCase(x.getTeacher())) {
						counter++;
						addSwimmersToPanel(destPanel, x);
					}
				} else if (sortBy.equals("day")) {
					System.out.println(modifier + " " + x.getDay());
					if (modifier.equalsIgnoreCase(x.getDay())) {
						counter++;
						addSwimmersToPanel(destPanel, x);
					}
				} else if (sortBy.equals("time")) {
					System.out.println(modifier + " " + x.getStartTime());
					if (modifier.equalsIgnoreCase(x.getStartTime())) {
						counter++;
						addSwimmersToPanel(destPanel, x);
					}
				} else if (sortBy.equals("level")) {
					System.out.println(modifier + " " + x.getLevel());
					if (modifier.equalsIgnoreCase(x.getLevel())) {
						counter++;
						addSwimmersToPanel(destPanel, x);
					}
				}
			}
		}

		int counter2 = numOfLessonObjects - counter;
		for (int i = 0; i < counter2; i++) {
			destPanel.add(new JLabel(" "));//Throw away panel
			//	}
		}
		if (counter % 18 == 0) {
			counterPow++;
		}
	}

	public static void addSwimmersToPanel(JPanel destPanel, SwimClass x) {
		String buttonText = "<html>";
		buttonText = buttonText + x.getTeacher() + "<br />" + x.getDay() + "<br />" + x.getStartTime() + "<br />"
				+ x.getLevel() + "</html>";
		JButton test = new JButton();
		if (DarkTheme) {
			test.setBackground(Color.GRAY);
			test.setForeground(Color.LIGHT_GRAY);
		}
		test.setText(buttonText);
		destPanel.add(test);
		test.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					createSwimmerCheckboxSelectionGUI(x.getStudents());
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				finalCheckFrame.dispose();
			}
		});
	}

	/*
	 *  Error Codes GUI
	 */
	public static void errorGUI(String errorCode) throws InterruptedException {
		JFrame frame = new JFrame("ERROR");
		JPanel panel = new JPanel();
		JLabel errorLabel = new JLabel(
				"<html>An error occured in the program..." + "<br>" + "==========" + "</br></html>");
		JLabel eC = new JLabel();
		panel.setLayout(new BorderLayout());
		panel.add(errorLabel, BorderLayout.NORTH);
		eC.setText("<html>" + errorCode + "</html>");
		JButton exit = new JButton("Close");

		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				close = true;
				checkIn.shutdown();
				System.exit(1);
			}

		});

		exit.setBackground(Color.GRAY);
		panel.add(exit, BorderLayout.SOUTH);
		panel.add(eC, BorderLayout.CENTER);

		frame.add(panel);
		frame.setSize(400, 300);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);

		/*
		while (!close)
			TimeUnit.MILLISECONDS.sleep(100);
		checkIn.shutdown();
		System.exit(1);
		*/
	}

	/*
	 * The actual method that runs the fileChooser GUI popup
	 */
	public static String fileChooser() {
		JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		int returnValue = fc.showOpenDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fc.getSelectedFile();
			System.out.println(selectedFile.getAbsolutePath());

			return selectedFile.getAbsolutePath();
		} else {
			return "FILE FIND ERROR";
		}

	}

}
