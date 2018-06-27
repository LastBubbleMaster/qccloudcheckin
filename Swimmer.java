package swimLesson;

public class Swimmer extends Member{

	private static String className = "Swimmer";

	//Default Constructor
	public Swimmer(String fN, String lN, String b) {
		super(fN,lN,b);
	}
	//Getter Method

	public static String getClassName() {
		return className;
	}
	
	public boolean equals(Swimmer swimmer) {
		if(swimmer.getFirstName().equals(this.getFirstName()) &&
				swimmer.getLastName().equals(this.getLastName()) &&
				swimmer.getBarcode().equals(this.getBarcode()))
		{
			return true;
		} else {
			return false;
		}
	}

	public String toString() {
		return className + ": " + getFirstName() + " " + getLastName() + " " + getBarcode();
	}
	
}
