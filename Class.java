package swimLesson;

import java.util.ArrayList;

public class Class {
	
	private String instructor;
	private String day;
	private String startTime;
	private ArrayList<Member> members;
	
	//Default Constructor
	public Class(String t, String d, String sT, ArrayList<Member> m) {
		instructor = t;
		day = d;
		startTime = sT;
		members = m;
	}
	
	//Getter Methods
	public String getInstructor() {
		return instructor;
	}

	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public ArrayList<Member> getMembers() {
		return members;
	}

	public void setMembers(ArrayList<Member> members) {
		this.members = members;
	}
	
	public int getClassSize() {
		return members.size();
	}
	
	//This Method should be overrid in subclasses to reflect what kind of class it is.
	public String getClassType() {
		return "Class";
	}
	
	public String toString() {
		String memberList = "";
		for(int x = 0; x < getClassSize(); x++) {
			memberList += members.get(x) + "\n";
		}
		return "Class\n" + getInstructor() + "\n" + getDay() + 
				"\n" + getStartTime() + memberList;
	}
	
	//THIS MAY NOT WORK, DEPENDS ON REFERENCE
		public static Class buildClass(Q2 data, String instructorName) {
			
			ArrayList<Member> members = new ArrayList<>();
			Class test;
			
			String instructor = instructorName;
			String day = (String) data.remove();
			String time = (String) data.remove();
			if(!data.remove().equals("==")) {
				System.out.println("ERROR BAD FILE_CLASS");
				return null;
			} else {
				String somedata = (String) data.remove();
				while(!somedata.equals("--")) {
					int i = somedata.indexOf(",");
					String memberFName = somedata.substring(0,i);
					somedata = somedata.substring(i + 1);
					i = somedata.indexOf(",");
					String memberLName = somedata.substring(0,i);
					String memberBarcode = somedata.substring(i + 1);
					Swimmer c = new Swimmer(memberFName,memberLName,memberBarcode);
					members.add(c);
					somedata = (String) data.remove();
				}
				test = new Class(instructor,day,time,members);
			}
			return test;
			
			
		}
		
		public String getButtonText(){
			return "<html>" + getInstructor() + "<br />" + getDay() + "<br />" + getStartTime() + 
					"<br />";
		}

}
