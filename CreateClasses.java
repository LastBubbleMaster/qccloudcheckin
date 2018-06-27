//package swimLesson;
//
//import java.util.ArrayList;
//import java.util.Collections;
//
//public class CreateClasses {
//
//	private static String teach;
//    private static String day;
//    private static String time;
//    private static String level;
//    public static int teachLevel = 0;
//    public static boolean buildSwimmers = false;
//    private static ArrayList<String> swimmers = new ArrayList<String>();
//    private static ArrayList<Swimmer> finalSwim = new ArrayList<Swimmer>();
//    private static ArrayList<SwimClass> swimClasses = new ArrayList<SwimClass>();
//    static int i;
//	private static String swimFName;
//	private static String swimLName;
//	private static String swimBarcode;
//	public static ArrayList<String> teachersNames = new ArrayList<String>();
//	public static ArrayList<String> classDays = new ArrayList<String>();
//	public static ArrayList<String> startTimes = new ArrayList<String>();
//	public static ArrayList<String> classLevels = new ArrayList<String>();
//	
//	public static int getSwimClassesSize()
//	{
//		return swimClasses.size();
//	}
//	
//	public static ArrayList<String> getTeachersNames(){
//		for(SwimClass swimclass : swimClasses){
//			String test = swimclass.getTeacher();
//			if(teachersNames.contains(test)){
//				System.out.println("Teacher's name already in list");
//			} else {
//				teachersNames.add(test);
//			}
//			/*for(String x : teachersNames){
//				if(!(x.equals(swimclass.getTeacher()))){
//					System.out.println(x);
//					teachersNames.add(x);
//				}
//			}*/
//		}
//		//Sorts alphabetically
//		Collections.sort(teachersNames);
//		return teachersNames;
//	}
//	
//	public static ArrayList<String> getClassDays(){
//		for(SwimClass swimclass : swimClasses){
//			String test = swimclass.getDay();
//			if(classDays.contains(test)){
//				System.out.println("That class day is already in list");
//			} else {
//				classDays.add(test);
//			}
//		}
//		//Sorts alphabetically
//		Collections.sort(classDays);
//		return classDays;
//	}
//	
//	public static ArrayList<String> getClassTimes(){
//		for(SwimClass swimclass : swimClasses){
//			String test = swimclass.getStartTime();
//			if(startTimes.contains(test)){
//				System.out.println("That start time is already in list");
//			} else {
//				startTimes.add(test);
//			}
//		}
//		//Sorts alphabetically
//		Collections.sort(startTimes);
//		return startTimes;
//	}
//	
//	public static ArrayList<String> getClassLevels(){
//		for(SwimClass swimclass : swimClasses){
//			String test = swimclass.getLevel();
//			if(classLevels.contains(test)){
//				System.out.println("That level is already in list");
//			} else {
//				classLevels.add(test);
//			}
//		}
//		//Sorts alphabetically
//		Collections.sort(classLevels);
//		return classLevels;
//	}
//	
//	
//	/*
//	 * Returns built class, normally after classes are built
//	 */
//	public static ArrayList<SwimClass> getSwimClass()
//	{
//		return swimClasses;
//	}
//	
//	/*
//	 * Converts data from SwimClassSheet.txt file into SwimClass objects
//	 * made up of Swimmer Objects
//	 * Process:
//	 * Starts off with buildSwimmers false, meaning no data has been pulled from file
//	 * It checks that first line in file is "**"
//	 * teachLevel = 0;
//	 * if so then it moves to the next line
//	 * teachLevel = 1;
//	 * The next line should be the teacher so then it saves the teachers value to the String teach;
//	 * teachLevel = 2;
//	 * Goes to next line which contains the day of the lesson
//	 * teachLevel = 3;
//	 * Goes to next line which contain the time of the lesson
//	 * teachLevel = 4;
//	 * Goes to final line which contains the level of the lesson
//	 * teachLevel = 5;
//	 * checks for "==* to signify the end of the class id data
//	 * returns teach level to 0 for next line but makes buildSwimmers = true;
//	 * this means it skips the class id data assignment part and pulls data for the swimmers
//	 * Gets the first and last name and barcode separated by commas.
//	 * It then creates a Swimmer object based on that data
//	 * Then adds that Swimmer to that SwimClass object
//	 * After every swimmer it checks for "--" which signifies the end of the list of swimmers for a given class
//	 * Once all swimmers for a given class have been created the data from the class id data and the array of Swimmers
//	 * that were just built are combined together to create a SwimClass
//	 * That swimclass is added to a master ArrayList that contains every SwimClass.
//	 * It then sets buildSwimmers = false; so that the program can start looking for class id data
//	 * It also resets the swimmer and class id data to null so it can be reassigned
//	 * Finally it returns the master ArrayList<SwimClass>
//	 * 
//	 */
//	public static ArrayList<SwimClass> createClasses(String[] dataFromTextFile)
//	{
//		String[] data = dataFromTextFile;
//		for(String x : data)
//		{
//			if(buildSwimmers)
//			{
//				if(!(x.equals("--"))){
//					swimmers.add(x);
//				} else {
//					for(String swimmer : swimmers)
//					{
//						//swimmer = swimmer.toLowerCase();
//						i = swimmer.indexOf(",");
//						swimFName = swimmer.substring(0,i);
//						swimmer = swimmer.substring(i + 1);
//						i = swimmer.indexOf(",");
//						swimLName = swimmer.substring(0,i);
//						swimBarcode = swimmer.substring(i + 1);
//						
//						Swimmer c = new Swimmer(swimFName,swimLName,swimBarcode);
//						finalSwim.add(c);
//					}
//					SwimClass b = new SwimClass(teach,day,time,level,finalSwim);
//					swimClasses.add(b);
//					System.out.println(b);
//					buildSwimmers = false;
//					swimmers = new ArrayList<String>();
//					finalSwim = new ArrayList<Swimmer>();
//					
//				}
//			} else {
//				if(teachLevel == 1)
//				{
//					teach = x;
//					teachLevel++;
//				} else if(teachLevel == 2) 
//				{	
//					day = x;
//					teachLevel++;
//				} else if(teachLevel == 3)
//				{
//					time = x;
//					teachLevel++;
//				} else if(teachLevel == 4)
//				{
//					level = x;
//					teachLevel++;
//				} else if(teachLevel == 5)
//				{
//					if(x.equals("=="))
//					{
//						buildSwimmers = true;
//						teachLevel = 0;
//					}
//				}
//			}
//			//ran 1st
//			if(x.equals("**") && teachLevel == 0 )//start class build
//			{
//				teachLevel = 1;
//			}
//			
//		}
//		
//		return swimClasses;
//		
//		
//		
//	}
//	
//	
//}
