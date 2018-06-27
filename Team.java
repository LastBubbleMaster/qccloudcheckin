package swimLesson;

import java.util.ArrayList;
import java.util.Collections;

public class Team {
	
	private static ArrayList<String> allTypesOfTeams = new ArrayList<String>();
	private static ArrayList<Class> allClasses = new ArrayList<Class>();
	private static ArrayList<String> allInstructors = new ArrayList<String>();
	private static ArrayList<String> allDays = new ArrayList<String>();
	private static ArrayList<String> allStartTimes = new ArrayList<String>();
	private static String[] dataFromSwimSheetFile;
	private static Q2 textFromDataFile = new Q2();
	
	public Team(String[] dataFromTextFile){
		dataFromSwimSheetFile = dataFromTextFile;
		buildClasses(dataFromTextFile);
		getInstructorNames();
		getClassDays();
		getClassTimes();
		collectDataFromExtraClasses();
//		for(Class x : allClasses){
//			System.out.println(x.getClassType());
//		}
		
	}
	
	public void collectDataFromExtraClasses(){
		Team_SwimLessons.getClassLevels();
	}
	
	public ArrayList<String> getInstructorNames() {
		for(Class someclass : allClasses) {
			String test = someclass.getInstructor();
			if(allInstructors.contains(test)) {
				System.out.println("Instructor's name is in list");
			} else {
				allInstructors.add(test);
			}
		}
		Collections.sort(allInstructors);
		return allInstructors;
	}
	
	public ArrayList<String> getClassDays() {
		for(Class someclass : allClasses) {
			String test = someclass.getDay();
			if(allDays.contains(test)) {
				System.out.println("That class day is already in list");
			} else {
				allDays.add(test);
			}
		}
		Collections.sort(allDays);
		return allDays;
	}

	public ArrayList<String> getClassTimes() {
		for(Class someclass : allClasses) {
			String test = someclass.getStartTime();
			if(allStartTimes.contains(test)) {
				System.out.println("That start time is already in list");
			} else {
				allStartTimes.add(test);
			}
		}
		Collections.sort(allStartTimes);
		return allStartTimes;
	}
	
	public static ArrayList<Class> getAllClasses() {
		return allClasses;
	}

	public static int getNumberOfClasses(){
		return allClasses.size();
	}
	
	public static ArrayList<String> getAllInstructors() {
		return allInstructors;
	}

	public static ArrayList<String> getAllDays() {
		return allDays;
	}

	public static ArrayList<String> getAllStartTimes() {
		return allStartTimes;
	}
	
	public static void convertStringArrayToQueue() {
		for(String x : dataFromSwimSheetFile) {
			textFromDataFile.add(x);
		}
	}
	
	public static ArrayList<Class> buildClasses(String[] dataFromTextFile)
	{
		convertStringArrayToQueue();
		int counter = 0;
		int errorLineCounter = 0;
		String classType = "";
		String instructorName = "";
		String classDay = "";
		String classTime = "";
		String[] data = dataFromTextFile;
		while(!textFromDataFile.isEmpty()) {
			errorLineCounter++;
			String x = (String) textFromDataFile.remove();
//				SwimClass b = new SwimClass(teach,day,time,level,finalSwim);  ##DONE##
//				swimClasses.add(b); ##DONE##
//				System.out.println(b); ##DONE##
//				buildSwimmers = false; ##NO LONGER NECESSARY##
//				swimmers = new ArrayList<String>(); ##NOT NECESSARY
//				finalSwim = new ArrayList<Swimmer>();
			switch(counter) {
				case 0:
					if(!(x.equals("**"))) {
						System.out.println("ERROR IN READING FROM ClassStart LINE: " +  errorLineCounter + x);
						break;
					} else {
						counter++;
					}
					break;
				case 1:
					if(!(x.substring(0,12).equals("ClassTypeID:"))) {
						System.out.println("ERROR IN READING FROM ClassTypeID LINE: " +  errorLineCounter);
						break;
					} else {
						classType = x.substring(12);
						counter++;
					}
					break;
				case 2:
					Class a;
					if(classType.equals("swim_lesson")) {
						System.out.println("Building Swim Class");
						a = SwimClass.buildClass(textFromDataFile,x);
					} else {
						System.out.println("Building Default Class");
						a = Class.buildClass(textFromDataFile,x);
					}
					//System.out.println("#############\n" + a + "#############");
					allClasses.add(a);
					counter = 0;
					break;
			}
		}
		return allClasses;
	}
}
