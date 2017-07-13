package swimLesson;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import javax.swing.filechooser.FileSystemView;

import java.util.ArrayList;
import java.util.Scanner;

@SuppressWarnings("unused")
public class TesterClass {

	static String real_path_name; 
	
	public static void main(String[] args) throws InterruptedException, IOException
	{
		/*
		checkIn.chromeSetup();
		checkIn.connectToWebsite();
		TimeUnit.SECONDS.sleep(15);
		checkIn.getName();
		*/
		
		
		checkIn.credits();
		if(checkIn.authenticateUser())
		{	
			System.out.println("USER AUTHENTICATED as user: " + checkIn.UU);
		} else {
			System.out.println("USER COULDN'T BE AUTHENTICATED");
			System.exit(1);
			
		}
		
		File home = FileSystemView.getFileSystemView().getHomeDirectory();
		String path_name = home.getAbsolutePath() + "\\SwimClassSheet.txt";
		File testa = new File(path_name);
		boolean firstRun = false;
		System.out.println("Path to desktop: " + home.getAbsolutePath());
		if(!testa.exists()){
			firstRun = true;
			//----Creates a new file-----
			//System.out.println(testa.createNewFile());
			//testa.createNewFile();
		}
		if(firstRun){
			System.out.println("We didn't find a SwimClassSheet.txt file on your desktop, either move your " + 
					"SwimClassSheet.txt file or create a SwimClassSheet.txt file.");
			gui.errorGUI("We didn't find a SwimClassSheet.txt file on your desktop, either move your SwimClassSheet.txt file or create a SwimClassSheet.txt file");

		} else {
			System.out.println("No Errors in finding ref. file");
			//normal run
		}
		System.out.println();
		
		//code from below is here
		
		textReader file;
		String[] aryLines = null;
		
		real_path_name = path_name;
		try{
			file = new textReader(real_path_name);
			aryLines = file.openFile();
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
		CreateClasses.createClasses(aryLines);
		System.out.println("\n" + "End of Swim Classes " + "\n" + "===========================");
		TimeUnit.MILLISECONDS.sleep(500);
		gui.createGUI(CreateClasses.getSwimClass());
		
	}
}
