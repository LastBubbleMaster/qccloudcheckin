package swimLesson;

import java.util.ArrayList;

public class SwimClass {
	
	private String teacher;
	private String day;
	private String startTime;
	private String level;
	private ArrayList<Swimmer> students;
	public ArrayList<String> teachersNames;
	
	//Default Constructor
	public SwimClass(String t, String d, String sT, String l,ArrayList<Swimmer> s){
		teacher = t;
		day = d;
		startTime = sT;
		level = l;
		students = s;
	}
	
	//Getter Methods
	public  String getTeacher()
	{
		return teacher;
	}
	public String getDay()
	{
		return day;
	}
	public String getStartTime()
	{
		return startTime;
	}
	public String getLevel()
	{
		return level;
	}
	public ArrayList<Swimmer> getStudents()
	{
		return students;
	}
	
	
	public String toString()
	{
		System.out.println(getTeacher());
		System.out.println(getDay());
		System.out.println(getStartTime());
		System.out.println(getLevel());
		int i = students.size();
		System.out.println(i);
		for(int x = 0; x < i; x++)
			System.out.println(students.get(x));
		
		System.out.println();
		return "";
	}
	
	
	
	public static Swimmer getUserByBarcode(String searchCode)
	{
		for(SwimClass x : CreateClasses.getSwimClass())
		{
			for(Swimmer y : x.getStudents())
			{
				if(y.getBarcode().equals(searchCode))
				{
					return y;
				}
			}
		}
		return null;
	}
	
	
}
