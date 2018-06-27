package swimLesson;

import java.util.ArrayList;
import java.util.Collections;

public class Team_SwimLessons extends Team{
	
	public Team_SwimLessons(String[] dataFromTextFile) {
		super(dataFromTextFile);
		getClassLevels();
	}

	private static ArrayList<String> allLevels = new ArrayList<String>();
	
	public static ArrayList<String> getClassLevels() {
		for(Class someclass: getAllClasses()) {
			if(someclass instanceof SwimClass) {
				String test = ((SwimClass) someclass).getLevel();
				if(allLevels.contains(test)) {
					System.out.println("That level is already in the list");
				} else {
					allLevels.add(test);
				}
			}
			
		}
		Collections.sort(allLevels);
		return allLevels;
	}

	public static ArrayList<String> getAllLevels() {
		return allLevels;
	}

	
	
}
