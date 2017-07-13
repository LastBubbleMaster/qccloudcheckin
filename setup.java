package swimLesson;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class setup implements java.io.Serializable {
	
	static String classFileLoc; 
	static boolean x = false;
	static final String PREFS_NAME = "MyPrefsFile";
	/*
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		SharedPreferences settings = getSharedPreferences(PREFS_NAME,0);
		boolean firstRun = settings.getBoolean("firstRun",true);
		
		if(firstRun) {
			
			SharedPreferences.Editor editor = settings.edit();
			editor.putBoolean("firstRun", false);
			editor.commit();
		} 
		
		
		
	}*/
	
	public void firstTimeStartup()
	{
		
		//GUI to select file location
	}
	
	public void normalStart()
	{
		
	}
	
	public static void main(String[] args)
	{
		if(!x){
			//y = false;
		} else {
		

		try {
			FileInputStream fileIn = new FileInputStream("/tmp/startup.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			x = in.readBoolean();
			in.close();
			fileIn.close();
		} catch(IOException i) {
			i.printStackTrace();
			return;
		}/* catch(ClassNotFoundException c) {
			System.out.println("Boolean state not found");
			c.printStackTrace();
			return;
			
		}*/
		}
		System.out.println("Has program been started before");
		System.out.println(x);
		
	}
	
	
	
}
