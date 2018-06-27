package swimLesson;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.swing.filechooser.FileSystemView;

//@SuppressWarnings("unused")
public class TesterClass {

	static String real_path_name = "";

	public static void main(String[] args) throws InterruptedException, IOException {
		System.out.println(new File(".").getAbsolutePath());

		boolean debug = true;
		checkIn.credits();
		
		//Authentication
		if (debug || checkIn.authenticateUser()) {
			System.out.println("USER AUTHENTICATED as user: " + checkIn.UU);
		} else {
			System.out.println("USER COULDN'T BE AUTHENTICATED");
			System.exit(1);
		}
		

		File test = FileSystemView.getFileSystemView().getDefaultDirectory();
		String startDir = test.getAbsolutePath();
		File dirThere = new File(startDir + "\\QCCloud Check-In");
		boolean firstRun = false;
		if (!dirThere.exists() && !dirThere.isDirectory()) {
			firstRun = true;
			System.out.println("FIRST TIME SETUP");

		}
		if (firstRun) {
			new File(startDir + "\\QCCloud Check-In").mkdir();
			gui.firstTimeGUI();
			textReader.generateCsvFile(startDir + "\\QCCloud Check-In\\settings.csv");
			//gui.fileChooser();

			while (real_path_name.length() == 0) {
				TimeUnit.SECONDS.sleep(2);
				System.out.println("WAITING FOR FILE CHOOSER");
			}

			CSVHandler.startupBuilder();
			CSVHandler.firstTimeSettings();
			real_path_name = CSVHandler.getSetting("SwimClassSheet.txt Location");
		} else {
			CSVHandler.testForUpdatedSettings();
			CSVHandler.startupBuilder();
			real_path_name = CSVHandler.getSetting("SwimClassSheet.txt Location");
			System.out.println("No Errors in finding ref. file");
		}
		System.out.println();
		textReader file;
		String[] aryLines = null;

		try {
			file = new textReader(real_path_name);
			aryLines = file.openFile();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		//System.out.println("TESTING" + aryLines.length);
		Team cambridge_pool = new Team(aryLines);
		System.out.println("End of Classes " + "\n" + "===========================");
		TimeUnit.MILLISECONDS.sleep(500);
		gui.createGUI(Team.getAllClasses());

	}
}
