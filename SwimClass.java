package swimLesson;

import java.util.ArrayList;

public class SwimClass extends Class{
	
	private String level;
	
	//Default Constructor
	public SwimClass(String t, String d, String sT, String l,ArrayList<Member> s){
		super(t,d,sT,s);
		level = l;
	}
	
	//Getter Methods
	public String getLevel()
	{
		return level;
	}

	public String getClassType(){
		return "Swim Class";
	}
	
	public static Member getUserByBarcode(String searchCode)
	{
		for(Class x : Team.getAllClasses())
		{
			for(Member y : x.getMembers())
			{
				if(y.getBarcode().equals(searchCode))
				{
					return y;
				}
			}
		}
		return null;
	}
	
	
	//THIS MAY NOT WORK, DEPENDS ON REFERENCE-Q2 refrence works, it goes by mem location not copy
	public static SwimClass buildClass(Q2 data, String instructorName) {
		
		ArrayList<Member> swimmers = new ArrayList<>();
		SwimClass test;
		
		String instructor = instructorName;
		String day = (String) data.remove();
		String time = (String) data.remove();
		String level = (String) data.remove();
		if(!data.remove().equals("==")) {
			System.out.println("ERROR BAD FILE_SWIM");
			return null;
		} else {
			String somedata = (String) data.remove();
			while(!somedata.equals("--")) {
				int i = somedata.indexOf(",");
				String swimFName = somedata.substring(0,i);
				somedata = somedata.substring(i + 1);
				i = somedata.indexOf(",");
				String swimLName = somedata.substring(0,i);
				String swimBarcode = somedata.substring(i + 1);
				Swimmer c = new Swimmer(swimFName,swimLName,swimBarcode);
				swimmers.add((Swimmer) c);
				somedata = (String) data.remove();
			}
			test = new SwimClass(instructor,day,time,level,swimmers);
		}
		return test;	
	}
	
	public String getButtonText(){
		return "<html>" + getInstructor() + "<br />" + getDay() + "<br />" + getStartTime() + 
				"<br />" + getLevel() + "<br />";
	}
	
	
}
