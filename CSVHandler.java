package swimLesson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.filechooser.FileSystemView;

public class CSVHandler {

	static BufferedReader br;
	static String line = "";
	static String csvSplitBy = ",";
	static String Sloc;
	static PrintWriter pw;
	static StringBuilder sb;

	static ArrayList<String> defaultValues = new ArrayList<String>();

	/*
	 * #Creates a PrintWriter in the settings.csv file in the append mode.
	 * #Creates a StringBuilder
	 */
	public static void startupBuilder() {
		Sloc = FileSystemView.getFileSystemView().getDefaultDirectory().getAbsolutePath()
				+ "\\QCCloud Check-In\\settings.csv";
		startupReader();
		try {
			pw = new PrintWriter(new FileWriter(new File(Sloc), true));
		} catch (IOException e) {
			e.printStackTrace();
		}

		sb = new StringBuilder();

	}

	/*
	 * #Tests for a "settingsTemp.csv" file 
	 * #If it exists it will delete the old "settings.csv" file and replace it with the 
	 * "settingsTemp.csv" file with th name "settings.csv"
	 * #This will also remove the settingsTemp.csv file
	 */
	public static void testForUpdatedSettings() {
		String startDir = FileSystemView.getFileSystemView().getDefaultDirectory().getAbsolutePath();
		File possibleSettings = new File(startDir + "\\QCCloud Check-In\\settingsTemp.csv");
		if (possibleSettings.exists()) {
			File oldSettings = new File(startDir + "\\QCCloud Check-In\\settings.csv");
			oldSettings.delete();
			File oldSettings2 = new File(startDir + "\\QCCloud Check-In\\settings.csv");
			possibleSettings.renameTo(oldSettings2);

		}
	}

	/*
	 * #Starts up a buffered reader in the "settings.csv" file
	 */
	public static void startupReader() {
		File loc = FileSystemView.getFileSystemView().getDefaultDirectory();
		String Sloc = loc.getAbsolutePath() + "\\QCCloud Check-In\\settings.csv";
		try {
			br = new BufferedReader(new FileReader(Sloc));
		} catch (FileNotFoundException e) {
			System.out.println("File with path: " + Sloc + " does not exist");
			e.printStackTrace();
		}

	}

	/*
	 * #Reads through the "settings.csv" file looking for the string that is passed as settingID
	 * #If it is found it will return the value of the setting,
	 * if not it will return the String
	 * "NO SETTING FOUND" plus the original ID.
	 */
	public static String getSetting(String settingID) {
		line = "";
		startupReader();
		try {
			while ((line = br.readLine()) != null) {
				String[] allItems = line.split(csvSplitBy);
				for (int i = 0; i < allItems.length; i++) {
					if (allItems[i].equals(settingID)) {
						return allItems[i + 1];
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "NO SETTING FOUND " + settingID;
	}

	/*
	 * #Fills the "setting.csv" file with the settings and their default values.
	 */
	public static void firstTimeSettings() throws IOException {
		defaultValues.add("SwimClassSheet.txt Location");
		defaultValues.add(",");
		defaultValues.add(TesterClass.real_path_name);
		defaultValues.add(",");
		defaultValues.add("Dark Theme");
		defaultValues.add(",");
		defaultValues.add("false");
		defaultValues.add(",");
		defaultValues.add("Sorting Order");
		defaultValues.add(",");
		defaultValues.add("default");
		defaultValues.add(",");
		defaultValues.add("Safe Mode");
		defaultValues.add(",");
		defaultValues.add("true");
		defaultValues.add(",");
		defaultValues.add("noAccCreateMembership");
		defaultValues.add(",");
		defaultValues.add("false");
		defaultValues.add(",");
		addSettingsToCSV();
	}

	/*
	 * #Creates the "settingsTemp.csv" file
	 * #Creates a new BufferedReader in the "settingsTemp.csv" file
	 * #Creates a new PrintWriter in append mode in the "settingsTemp.csv" file
	 */
	public static void generateTempCSV() throws InterruptedException, IOException {
		String startDir = FileSystemView.getFileSystemView().getDefaultDirectory().getAbsolutePath();
		textReader.generateCsvFile(startDir + "\\QCCloud Check-In\\settingsTemp.csv");
		File tempFile = new File(startDir + "\\QCCloud Check-In\\settingsTemp.csv");
		br = new BufferedReader(new FileReader(tempFile));
		pw = new PrintWriter(new FileWriter(tempFile), true);
	}

	/*
	 * #Starts a BufferedReader in the "settings.csv" file
	 * #Everything is on one line so it just reads the entire file
	 * in one go, the while loop is just a precaution
	 * #it separates every part of the string into the id's and their values
	 * #This doesn't include the commas so they are added back in
	 * #OVERALL FUNCTION
	 * # File > String[] w/out commas > ArrayList<String> w/ commas
	 */
	public static void oldSettingsToEditableArray() throws IOException {
		//Reader in the settings.csv file
		startupReader();
		//TODO Maybe remake this line String
		line = "";
		while ((line = br.readLine()) != null) {
			//Items from file go to String[] because function only goes to String[]
			String[] allItems = line.split(csvSplitBy);
			//Converting String[] items into "defaultValues" ArrayList<String>
			for (int i = 0; i < allItems.length * 2; i++) {
				//adds a comma for every other value
				if (i % 2 == 1) {
					defaultValues.add(defaultValues.size(), ",");
					//adds the value from the allItems String[]
				} else {
					defaultValues.add(defaultValues.size(), allItems[i / 2]);
				}
			}
		}

	}

	/*
	 * #"Compiler Method"
	 * #Generates the "settingsTemp.csv" file
	 * #Prints to console what is being added to the new "settingsTemp.csv" file
	 * #Clears the cache of data inside sb StringBuilder
	 * #Adds the settings of the defaultValues Array to this file ^^^^
	 */
	public static void mostRecentSettings() throws IOException, InterruptedException {

		generateTempCSV();
		System.out.println("Most Recent Settings");
		for (String x : defaultValues)
			System.out.println("-" + x + "-");
		//This is used to clear the cache of previous data in String Builder
		sb = new StringBuilder();
		addSettingsToCSV();
	}

	/*
	 * #StringBuilder gets data from defaultValues and adds it to 
	 * the given PrintWriter
	 */
	public static void addSettingsToCSV() {
		for (String x : defaultValues) {
			sb.append(x);
		}
		/*
		 * Careful with this pw and sb because depending on when it was last called
		 * it is very easy to print to the wrong place or to have left over data
		 * in the StringBuilder
		 */
		
		pw.append(sb.toString());
		pw.close();
	}

	// accepting int's, booleans, and doubles and converting to string for CSV storage
	public static void setSetting(String settingID, int value) throws IOException{
		setSetting(settingID, Integer.toString(value));
	}
	public static void setSetting(String settingID, boolean value) throws IOException{
		System.out.println("Value of setSetting(): " + Boolean.toString(value));
		setSetting(settingID, Boolean.toString(value));
	}
	public static void setSetting(String settingID, double value) throws IOException {
		setSetting(settingID, Double.toString(value));
	}
	
	/*
	 * #Sets the settingID inside defaultValues ArrayList to the passed value
	 * #Final conversion of other similar methods with different values entered
	 */
	public static void setSetting(String settingID, String value) throws IOException {

		boolean settingFound = false;
		/* #If first edit since program opened make take all data from
		* "setting.csv" file and put it into the defaultValue Array
		*/
		if (defaultValues.size() == 0) {
			oldSettingsToEditableArray();
		}

		int count = 0;
		//Searches through defaultValues in an attempt to find the correct settingID
		for (String x : defaultValues) {
			count++;
			if (x.equals(settingID)) {
				System.out.println("Setting Matched: " + x + ", Value: " + value);
				defaultValues.set(count + 1, value);
				settingFound = true;
			}
		}
		//Lets the user know the settingID wasn't found
		if (!settingFound) {
			System.out.println("Setting: " + settingID + " couldn't be found");
		}
		try {
			mostRecentSettings();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
