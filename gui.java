package swimLesson;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

import org.openqa.selenium.NoSuchSessionException;

public class gui{

	final static int STARTING_SWIM_GUI = 1;
	final static int CREATED_CLASSES = 2;
	final static int LOGGING_IN = 3;
	final static int LOGGED_IN = 4;
	final static int ACCESSING_CHECK_IN = 5;
	static boolean close = false;
	
	static JFrame frame3 = new JFrame("Status Console");
	static JFrame frame6 = new JFrame("Check-In Overview");
	static JPanel panel3 = new JPanel();
	static JPanel panel7 = new JPanel();
	
	static boolean work = false;
	static boolean authenticating = false;
	static ArrayList<JLabel> statusQueue = new ArrayList<JLabel>();
	static JLabel line1 = new JLabel("");
	static JLabel line2 = new JLabel("");
	static JLabel line3 = new JLabel("");
	
	static ArrayList<Swimmer> good = new ArrayList<Swimmer>();
	static ArrayList<Swimmer> bad = new ArrayList<Swimmer>();
	static ArrayList<Swimmer> unable = new ArrayList<Swimmer>();
	static ArrayList<String> checkedSwimText;
	static ArrayList<String> checkedSwimCodes = new ArrayList<String>();
	static ArrayList<Swimmer> checkedSwimSwimmers = new ArrayList<Swimmer>();
	
	//NOT CURRENTLY USED
	public static void statusGUI()
	{
		
		panel3.setLayout(new GridLayout(3,0));
		panel3.setBackground(Color.BLACK);
		panel3.add(line1);
		panel3.add(line2);
		panel3.add(line3);
		frame3.add(panel3);
		frame3.setSize(400, 160);
		frame3.setLocationRelativeTo(null);
		frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame3.setVisible(true);
		statusQueue.add(0,line1);
		statusQueue.add(0,line2);
		statusQueue.add(0,line3);
	}
	
	public static void printGoodBadUnable()
	{
		System.out.println("[");
		for(Swimmer x : good)
			System.out.print(x + ",");
		System.out.println("][");
		for(Swimmer x : bad)
			System.out.print(x + ",");
		System.out.println("][");
		for(Swimmer x : unable)
			System.out.print(x + ",");
		System.out.println("]");
		
		System.out.println();
	}
	
	public static void resultGUI()

	{
		
		JFrame frame = new JFrame("Check In Result");
		JPanel panel = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel4 = new JPanel();
		panel.setBackground(Color.GREEN);
		panel2.setBackground(Color.RED);
		panel4.setBackground(Color.CYAN);
		frame.setLayout(new GridLayout(3,0));
		if(good.size() == 0)
			panel.setLayout(new GridLayout(good.size() + 2,0));
		else 
			panel.setLayout(new GridLayout(good.size() + 1,0));
		if(bad.size() == 0)
			panel2.setLayout(new GridLayout(bad.size() + 2,0));
		else 
			panel2.setLayout(new GridLayout(bad.size() + 1,0));
		if(unable.size() == 0)
			panel4.setLayout(new GridLayout(unable.size() + 2,0));
		else
			panel4.setLayout(new GridLayout(unable.size() + 1,0));
		
		frame.add(panel);
		frame.add(panel2);
		frame.add(panel4);
		JLabel goodLabel = new JLabel("Users Entered In Successfully");
		JLabel badLabel = new JLabel("Users Entered In Forcefully");
		JLabel unableLabel = new JLabel("Users were not entered in due to errors");
		
		goodLabel.setBackground(Color.GREEN);
		badLabel.setBackground(Color.YELLOW);
		unableLabel.setBackground(Color.CYAN);
		
		System.out.print("GOOD BAD UNABLE SIZES: ");
		printGoodBadUnable();
		
		panel.add(goodLabel);
		if(good.size() == 0){
			panel.add(new JLabel("NONE"));
		} else {
			for(Swimmer y: good)
			{
				panel.add(new JLabel(y.getFirstName() + " " + y.getLastName() + " " + y.getBarcode()));
			}
		}
		
		panel2.add(badLabel);
		if(bad.size() == 0) {
			panel2.add(new JLabel("NONE"));	
		} else {
			for(Swimmer y: bad){
				panel2.add(new JLabel(y.getFirstName() + " " + y.getLastName() + " " + y.getBarcode()));
			}
				
		}
		
		panel4.add(unableLabel);
		if(unable.size() == 0){
			panel4.add(new JLabel("NONE"));
		} else {
			for(Swimmer y: unable)
			{
				panel4.add(new JLabel(y.getFirstName() + " " + y.getLastName() + " " + y.getBarcode()));
			}
		}
		
		good = new ArrayList<Swimmer>();
		bad = new ArrayList<Swimmer>();
		unable = new ArrayList<Swimmer>();
		checkedSwimText = new ArrayList<String>();
		checkedSwimCodes = new ArrayList<String>();
		
		frame.setSize(300, 800);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		frame6.dispose();
		
		//checkedSwimCodes = new ArrayList<String>();
		
	}
	
	//NOT CURRENTLY USED
	public static void updateStatus(int code)
	{
		JLabel x;
		switch (code) {
			case 1: x = new JLabel("Starting Swim Gui");
			x.setForeground(Color.PINK);
			x.setBounds(0, 0, 400, 50);
			statusQueue.add(0,x);
			updateDisplay();
			break;
			case 2: x = new JLabel("Created Classes");
			x.setForeground(Color.RED);
			x.setBounds(0, 0, 400, 50);
			statusQueue.add(0,x);
			updateDisplay();
			break;
			case 3: x = new JLabel("Logging In");
			x.setForeground(Color.ORANGE);
			x.setBounds(0, 0, 400, 50);
			statusQueue.add(0,x);
			updateDisplay();
			break;
			case 4: x = new JLabel("Logged In");
			x.setForeground(Color.YELLOW);
			x.setBounds(0, 0, 400, 50);
			statusQueue.add(0,x);
			updateDisplay();
			break;
			case 5: x = new JLabel("Accessing Check In");
			x.setForeground(Color.GREEN);
			x.setBounds(0, 0, 400, 50);
			statusQueue.add(0,x);
			updateDisplay();
			break;
		
		}
	}
	
	//NOT CURRENTLY USED
	public static void updateDisplay()
	{
		panel3.remove(line1);
		panel3.remove(line2);
		panel3.remove(line3);
		panel3.add(statusQueue.get(0));
		panel3.add(statusQueue.get(1));
		panel3.add(statusQueue.get(2));
		frame3.setVisible(true);
		
	}

	//10 User Display with checkboxes
	public static void createLoginGUI(ArrayList<Swimmer> abc) throws InterruptedException
	{
		JFrame frame2 = new JFrame("Swimmer Checking");
		JPanel panel2 = new JPanel();
		ArrayList<Swimmer> swimmers = abc;
		int swimSize = abc.size();
		if(swimSize > 9)
			errorGUI("Program detected that the class size was greater than 9.  Either fix the SwimClassSheet.txt file or make the class into seperate classes.");
		panel2.setLayout(new GridLayout(10,0));
		for(Swimmer x: swimmers)
		{
			JCheckBox test = new JCheckBox();
			test.setText(x.getLastName() + "," + x.getFirstName() + ":" + x.getBarcode());
			panel2.add(test);
			test.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent d) {
					
				}
			});
		}
		JButton saveButton = new JButton();
		saveButton.setText("Save");
		saveButton.setBackground(Color.GRAY);
		saveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				
				
				
				
				
				
				checkedSwimText = manageCheckedCheckboxes(panel2);
				
				
				
				if(checkedSwimText.size() > 1)
				{
					System.out.println("------------");
					System.out.println("Swimmers Added: ");
					for(String swimmer : checkedSwimText)
					{
						System.out.println(swimmer);
						String lN = swimmer.substring(0, swimmer.indexOf(","));
						swimmer = swimmer.substring(swimmer.indexOf(",") + 1);
						String fN = swimmer.substring(0, swimmer.indexOf(":"));
						swimmer = swimmer.substring(swimmer.indexOf(":") + 1);
						String mID = swimmer.substring(0);
						checkedSwimSwimmers.add(new Swimmer(fN,lN,mID));
					}
					/*for(String x: checkedSwimText)
					{
						
						int i = x.indexOf(":");
						checkedSwimCodes.add(x.substring(i + 1));
					}*/
					for(Swimmer u : checkedSwimSwimmers)
						System.out.println(u);
					
					
					/*for(String u : checkedSwimCodes)
						System.out.println(u);*/
					System.out.println("-==-==-");
					frame2.dispose();
					
				} else if(checkedSwimText.size() ==1)
				{
					System.out.println("------------");
					System.out.println("Swimmer Added: ");
					
					for(String swimmer : checkedSwimText)
					{
						System.out.println(swimmer);
						String lN = swimmer.substring(0, swimmer.indexOf(","));
						swimmer = swimmer.substring(swimmer.indexOf(",") + 1);
						String fN = swimmer.substring(0, swimmer.indexOf(":"));
						swimmer = swimmer.substring(swimmer.indexOf(":") + 1);
						String mID = swimmer.substring(0);
						checkedSwimSwimmers.add(new Swimmer(fN,lN,mID));
					}
					/*
					for(String x: checkedSwimText)
					{
						
						int i = x.indexOf(":");
						checkedSwimCodes.add(x.substring(i + 1));
					}
					for(String u : checkedSwimCodes)
						System.out.println(u);*/
					
					for(Swimmer u : checkedSwimSwimmers)
						System.out.println(u);
					System.out.println("-==-==-");
					frame2.dispose();
					
				} else {
					//no swimmers added nothing happens
				}
				
				
				
				
			}
			
		});
		panel2.add(saveButton);
		frame2.add(panel2);
		frame2.setSize(200, 500);
		frame2.setLocationRelativeTo(null);
		frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame2.setVisible(true);
	}
	
	public static ArrayList<String> manageCheckedCheckboxes(final Container c) {
		Component[] comps = c.getComponents();
		ArrayList<String> checkedSwimmers = new ArrayList<String>();
		for(Component comp : comps)
		{
			if(comp instanceof JCheckBox) {
				JCheckBox box = (JCheckBox) comp;
				if(box.isSelected()) {
					String barcode = box.getText();
					checkedSwimmers.add(barcode);
				}
			}
		}
		return checkedSwimmers;
	}
	
	//checkIn button UI, shows all users to be checked in with checkin button and back button
	public static void confirmSignInGUI()
	{
		
		panel7 = new JPanel();
		panel7.setLayout(new GridLayout(checkedSwimSwimmers.size() + 2,0));
		System.out.println(checkedSwimSwimmers.size() + " : SIZE");
		for(Swimmer y : checkedSwimSwimmers)
		{
			//Swimmer y = SwimClass.getUserByBarcode(x);
			panel7.add(new JLabel(y.getFirstName() + " " + y.getLastName() + " " + y.getBarcode()));
		}
		JButton finalCheck = new JButton("Check-In");
		JButton back = new JButton("Back");
		finalCheck.setBackground(Color.GREEN);
		back.setBackground(Color.RED);
		
		finalCheck.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				checkIn.chromeSetup();
				checkIn.connectToWebsite();
				checkIn.login();
				try {
					checkIn.loginSwimmers(checkedSwimSwimmers);
					frame6.dispose();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				//checkedSwimCodes = new ArrayList<String>();
				
			}
			
		});
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame6.remove(panel7);
				frame6.dispose();
				//frame6 = new JFrame("Check-In Overview");
				//checkedSwimCodes = new ArrayList<String>();
				//checkedSwimText = new ArrayList<String>();
				
			}
			
		});
		panel7.add(finalCheck);
		panel7.add(back);
		frame6.add(panel7);
		frame6.setSize(300, 800);
		frame6.setLocationRelativeTo(null);
		frame6.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame6.setVisible(true);
		
		
	}
	
	//NOT CURRENTLY USED
	public static void passwordWarning()
	{
		JFrame frame = new JFrame("Warning");
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,0));
		JLabel warning = new JLabel("Warning:");
		JLabel warn2 = new JLabel("You have 1 login left");
		
		panel.add(warning);
		panel.add(warn2);
		frame.add(panel);
		frame.setSize(300, 300);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		System.out.println("Warning Given");
		
		
	}
	
	//NOT CURRENTLY USED
	public static void authenticationGUI()
	{
		/*
		authenticating = true;
		JFrame frame = new JFrame("Login");
		JPanel panel = new JPanel();
		JTextField business = new JTextField();
		JLabel btext = new JLabel("Business");
		JTextField username = new JTextField();
		JLabel utext = new JLabel("Username");
		JTextField password = new JTextField();
		JLabel ptext = new JLabel("Password");
		JButton login = new JButton("Login");
		login.addActionListener(new ActionListener() {
			
			
			
			@Override
			public void actionPerformed(ActionEvent e) {
				work = checkIn.authenticateUser(business.getText(),username.getText(),password.getText());
				authenticating = false;
				
			}
			
		}
		);
	
		
		panel.setLayout(new GridLayout(3,0));
		
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel4 = new JPanel();
		panel1.setLayout(new GridLayout(3,0));
		panel2.setLayout(new GridLayout(3,0));
		
		
		
		panel1.add(btext);
		panel1.add(business);
		panel1.add(utext);
		panel2.add(username);
		panel2.add(ptext);
		panel2.add(password);
		
		panel4.add(login);
		panel.add(panel1);
		panel.add(panel2);
		panel.add(panel4);
		
		frame.add(panel);
		frame.setSize(300,210);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);*/
	}
	
	//NOT CURRENTLY USED
	public static void firstTimeGUI()
	{
		JButton save = new JButton("Set as file path");
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
			
		});
		
		
		JFrame frame = new JFrame("First Time Setup");
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		JLabel x1 = new JLabel("We didn't find a SwimClassSheet.txt file in the C:/ Documents folder, either move your Swim");
		JLabel x2 = new JLabel("ClassSheet.txt folder and leave the path as C:/Documents/SwimClassSheet.txt ");
		JLabel x3 = new JLabel("or put in the path of your SwimClassSheet.txt file");
				
		x1.setHorizontalAlignment(JLabel.CENTER);
		x1.setVerticalAlignment(JLabel.TOP);
		x2.setHorizontalAlignment(JLabel.CENTER);
		x2.setVerticalAlignment(JLabel.TOP);
		x3.setHorizontalAlignment(JLabel.CENTER);
		x3.setVerticalAlignment(JLabel.TOP);
		panel.add(x1,BorderLayout.CENTER);
		panel.add(x2,BorderLayout.CENTER);
		panel.add(x3,BorderLayout.CENTER);
		
		JTextField path_file = new JTextField();
		Font pathFont = new Font("SansSerif",Font.PLAIN, 14);
		path_file.setFont(pathFont);
		path_file.setMaximumSize(new Dimension(256,20));
		
		path_file.setHorizontalAlignment(JLabel.CENTER);
		save.setHorizontalAlignment(JLabel.CENTER);
		save.setVerticalAlignment(JLabel.BOTTOM);
		
		
		panel.add(path_file,BorderLayout.CENTER);
		panel.add(save,BorderLayout.CENTER);
		panel.add(save);
		frame.add(panel);
		frame.setSize(550,150);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
	//Main Swim Lesson Chooser GUI
	public static void createGUI(ArrayList<SwimClass> swim)
	{
		JFrame frame = new JFrame("Swim Lesson Chooser");
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(11,4));
		for(SwimClass x : swim)
		{
			String buttonText = "<html>";
			buttonText = buttonText + x.getTeacher() + "<br />" + x.getDay() + "<br />" + x.getStartTime() + "<br />" + x.getLevel() + "</html>";
			JButton test = new JButton();
			test.setText(buttonText);
			panel.add(test);
			test.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						createLoginGUI(x.getStudents());
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					frame6.dispose();
				}
			});
		}
		
		JPanel decision = new JPanel();
		
		JButton exitButton = new JButton();
		exitButton.setText("Exit");
		exitButton.setBackground(Color.RED);
		exitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				try {
				checkIn.driver.close();
				} catch(NoSuchSessionException e3) {
					
				}
				System.exit(1);
			}
		});
		
		JButton checkInButton = new JButton();
		checkInButton.setText("Check In");
		checkInButton.setBackground(Color.GREEN);
		checkInButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				confirmSignInGUI();
			}
		});
		
		JButton clearButton = new JButton();
		clearButton.setText("Clear all selected users");
		clearButton.setBackground(Color.WHITE);
		clearButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				checkedSwimCodes = new ArrayList<String>();
				checkedSwimText = new ArrayList<String>();
				frame6 = new JFrame("Check-In Overview");
			}
		});
		
		decision.add(checkInButton);
		decision.add(exitButton);
		decision.add(clearButton);
		panel.add(decision);
		frame.add(panel);
		frame.setSize(700, 900);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	//Error Codes GUI
	public static void errorGUI(String errorCode) throws InterruptedException
	{
		JFrame frame = new JFrame("ERROR");
		JPanel panel = new JPanel();
		JLabel errorLabel = new JLabel("<html>An error occured in the program..." + "<br>" + "==========" + "</br></html>");
		JLabel eC = new JLabel();
		panel.setLayout(new BorderLayout());
		panel.add(errorLabel, BorderLayout.NORTH);
		eC.setText("<html>" + errorCode + "</html>");
		JButton exit = new JButton("Close");
		
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				close = true;
			}
			
		});
		
		exit.setBackground(Color.GRAY);
		panel.add(exit, BorderLayout.SOUTH);
		panel.add(eC,BorderLayout.CENTER);
		
		
		
		frame.add(panel);
		frame.setSize(400, 300);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		
		while(!close)
			TimeUnit.MILLISECONDS.sleep(100);
		checkIn.shutdown();
		System.exit(1);
	}
	
}


/*package swimLesson;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class gui{

	final static int STARTING_SWIM_GUI = 1;
	final static int CREATED_CLASSES = 2;
	final static int LOGGING_IN = 3;
	final static int LOGGED_IN = 4;
	final static int ACCESSING_CHECK_IN = 5;
	
	static JFrame frame3 = new JFrame("Status Console");
	static JPanel panel3 = new JPanel();
	
	static ArrayList<JLabel> statusQueue = new ArrayList<JLabel>();
	static JLabel line1 = new JLabel("");
	static JLabel line2 = new JLabel("");
	static JLabel line3 = new JLabel("");
	
	static ArrayList<String> good = new ArrayList<String>();
	static ArrayList<String> bad = new ArrayList<String>();
	static ArrayList<String> unable = new ArrayList<String>();
	
	
	public static void statusGUI()
	{
		
		panel3.setLayout(new GridLayout(3,0));
		panel3.setBackground(Color.BLACK);
		panel3.add(line1);
		panel3.add(line2);
		panel3.add(line3);
		frame3.add(panel3);
		frame3.setSize(400, 160);
		frame3.setLocationRelativeTo(null);
		frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame3.setVisible(true);
		statusQueue.add(0,line1);
		statusQueue.add(0,line2);
		statusQueue.add(0,line3);
	}
	
	public static void resultGUI()
	{
		
		JFrame frame = new JFrame("Check In Result");
		JPanel panel = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel4 = new JPanel();
		panel.setBackground(Color.GREEN);
		panel2.setBackground(Color.RED);
		panel4.setBackground(Color.CYAN);
		frame.setLayout(new GridLayout(3,0));
		if(good.size() == 0)
			panel.setLayout(new GridLayout(good.size() + 2,0));
		else 
			panel.setLayout(new GridLayout(good.size() + 1,0));
		if(bad.size() == 0)
			panel2.setLayout(new GridLayout(bad.size() + 2,0));
		else 
			panel2.setLayout(new GridLayout(bad.size() + 1,0));
		if(unable.size() == 0)
			panel4.setLayout(new GridLayout(unable.size() + 2,0));
		else
			panel4.setLayout(new GridLayout(unable.size() + 1,0));
		
		frame.add(panel);
		frame.add(panel2);
		frame.add(panel4);
		JLabel goodLabel = new JLabel("Users Entered In Successfully");
		JLabel badLabel = new JLabel("Users Entered In Forcefully");
		JLabel unableLabel = new JLabel("Users were not entered in due to errors");
		
		goodLabel.setBackground(Color.GREEN);
		badLabel.setBackground(Color.YELLOW);
		unableLabel.setBackground(Color.CYAN);
		
		System.out.println("GOOD BAD UNABLE SIZES: " + good + " ; " + bad + " ; " + unable);
		
		panel.add(goodLabel);
		if(good.size() == 0){
			panel.add(new JLabel("NONE"));
		} else {
			for(String x: good)
			{
				Swimmer y = SwimClass.getUserByBarcode(x);
				panel.add(new JLabel(y.getFirstName() + " " + y.getLastName() + " " + y.getBarcode()));
			}
		}
		
		panel2.add(badLabel);
		if(bad.size() == 0) {
			panel2.add(new JLabel("NONE"));	
		} else {
			for(String x: bad){
				Swimmer y = SwimClass.getUserByBarcode(x);
				panel2.add(new JLabel(y.getFirstName() + " " + y.getLastName() + " " + y.getBarcode()));
			}
				
		}
		
		panel4.add(unableLabel);
		if(unable.size() == 0){
			panel4.add(new JLabel("NONE"));
		} else {
			for(String x: unable)
			{
				Swimmer y = SwimClass.getUserByBarcode(x);
				panel4.add(new JLabel(y.getFirstName() + " " + y.getLastName() + " " + y.getBarcode()));
			}
		}
		
		frame.setSize(300, 800);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
	
	
	
	public static void updateStatus(int code)
	{
		JLabel x;
		switch (code) {
			case 1: x = new JLabel("Starting Swim Gui");
			x.setForeground(Color.PINK);
			x.setBounds(0, 0, 400, 50);
			statusQueue.add(0,x);
			updateDisplay();
			break;
			case 2: x = new JLabel("Created Classes");
			x.setForeground(Color.RED);
			x.setBounds(0, 0, 400, 50);
			statusQueue.add(0,x);
			updateDisplay();
			break;
			case 3: x = new JLabel("Logging In");
			x.setForeground(Color.ORANGE);
			x.setBounds(0, 0, 400, 50);
			statusQueue.add(0,x);
			updateDisplay();
			break;
			case 4: x = new JLabel("Logged In");
			x.setForeground(Color.YELLOW);
			x.setBounds(0, 0, 400, 50);
			statusQueue.add(0,x);
			updateDisplay();
			break;
			case 5: x = new JLabel("Accessing Check In");
			x.setForeground(Color.GREEN);
			x.setBounds(0, 0, 400, 50);
			statusQueue.add(0,x);
			updateDisplay();
			break;
		
		}
	}
	
	public static void updateDisplay()
	{
		panel3.remove(line1);
		panel3.remove(line2);
		panel3.remove(line3);
		panel3.add(statusQueue.get(0));
		panel3.add(statusQueue.get(1));
		panel3.add(statusQueue.get(2));
		frame3.setVisible(true);
		
	}
	
	
	
	public static void createLoginGUI(ArrayList<Swimmer> abc)
	{
		JFrame frame2 = new JFrame("Swimmer Checking");
		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(10,0));
		ArrayList<Swimmer> swimmers = abc;
		for(Swimmer x: swimmers)
		{
			JCheckBox test = new JCheckBox();
			test.setText(x.getLastName() + "," + x.getFirstName() + ":" + x.getBarcode());
			panel2.add(test);
			test.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent d) {
				}
			});
		}
		JButton checkInButton = new JButton();
		checkInButton.setText("Check In");
		checkInButton.setBackground(Color.GREEN);
		checkInButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				ArrayList<String> checkedSwimText = manageCheckedCheckboxes(panel2);
				ArrayList<String> checkedSwimCodes = new ArrayList<String>();
				System.out.println("------------");
				for(String x: checkedSwimText)
				{
					System.out.println("Before: " + x);
					int i = x.indexOf(":");
					checkedSwimCodes.add(x.substring(i + 1));
				}
				
				checkIn.chromeSetup();
				checkIn.connectToWebsite();
				checkIn.login();
				try {
					checkIn.loginSwimmers(checkedSwimCodes);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				frame2.dispose();
			}
		});
		panel2.add(checkInButton);
		frame2.add(panel2);
		frame2.setSize(200, 500);
		frame2.setLocationRelativeTo(null);
		frame2.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame2.setVisible(true);
	}
	
	public static ArrayList<String> manageCheckedCheckboxes(final Container c) {
		Component[] comps = c.getComponents();
		ArrayList<String> checkedSwimmers = new ArrayList<String>();
		for(Component comp : comps)
		{
			if(comp instanceof JCheckBox) {
				JCheckBox box = (JCheckBox) comp;
				if(box.isSelected()) {
					String barcode = box.getText();
					checkedSwimmers.add(barcode);
				}
			}
		}
		return checkedSwimmers;
	}
	
	
	public static void createGUI(ArrayList<SwimClass> swim)
	{
		JFrame frame = new JFrame("Swim Lesson Chooser");
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(11,4));
		System.out.println(swim.size());
		for(SwimClass x : swim)
		{
			String buttonText = "<html>";
			buttonText = buttonText + x.getTeacher() + "<br />" + x.getDay() + "<br />" + x.getStartTime() + "<br />" + x.getLevel() + "</html>";
			JButton test = new JButton();
			test.setText(buttonText);
			panel.add(test);
			test.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					createLoginGUI(x.getStudents());
				}
			});
		}
		frame.add(panel);
		frame.setSize(700, 900);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	
}
*/