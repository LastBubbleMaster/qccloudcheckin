package swimLesson;

import java.util.ArrayList;

public class CreateClasses {

	private static String teach;
    private static String day;
    private static String time;
    private static String level;
    public static int teachLevel = 0;
    public static boolean buildSwimmers = false;
    private static ArrayList<String> swimmers = new ArrayList<String>();
    private static ArrayList<Swimmer> finalSwim = new ArrayList<Swimmer>();
    private static ArrayList<SwimClass> swimClasses = new ArrayList<SwimClass>();
    static int i;
	private static String swimFName;
	private static String swimLName;
	private static String swimBarcode;
	
	public static ArrayList<SwimClass> getSwimClass()
	{
		return swimClasses;
	}
	
	public static ArrayList<SwimClass> createClasses(String[] dataFromTextFile)
	{
		String[] data = dataFromTextFile;
		for(String x : data)
		{
			if(buildSwimmers)
			{
				if(!(x.equals("--"))){
					swimmers.add(x);
				} else {
					for(String swimmer : swimmers)
					{
						i = swimmer.indexOf(",");
						swimFName = swimmer.substring(0,i);
						swimmer = swimmer.substring(i + 1);
						i = swimmer.indexOf(",");
						swimLName = swimmer.substring(0,i);
						swimBarcode = swimmer.substring(i + 1);
						
						Swimmer c = new Swimmer(swimFName,swimLName,swimBarcode);
						finalSwim.add(c);
					}
					SwimClass b = new SwimClass(teach,day,time,level,finalSwim);
					swimClasses.add(b);
					System.out.println(b);
					buildSwimmers = false;
					swimmers = new ArrayList<String>();
					finalSwim = new ArrayList<Swimmer>();
					
				}
			} else {
				if(teachLevel == 1)
				{
					teach = x;
					teachLevel++;
				} else if(teachLevel == 2) 
				{	
					day = x;
					teachLevel++;
				} else if(teachLevel == 3)
				{
					time = x;
					teachLevel++;
				} else if(teachLevel == 4)
				{
					level = x;
					teachLevel++;
				} else if(teachLevel == 5)
				{
					if(x.equals("=="))
					{
						buildSwimmers = true;
						teachLevel = 0;
					}
				}
			}

			if(x.equals("**") && teachLevel == 0 )//start class build
			{
				teachLevel = 1;
			}
			
		}
		
		return swimClasses;
		
		
		
	}
	
	
}
