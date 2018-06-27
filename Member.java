package swimLesson;

public class Member {
	
	private String firstName;
	private String lastName;
	private String barcode;
	private static String className = "Member";
	
	public Member(String fN, String lN, String bC) {
		firstName = fN;
		lastName = lN;
		if(bC.length() == 0)
			barcode = "N/A";
		else
			barcode = bC;
	}

	public static String getClassName() {
		return className;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String toString() {
		return className + ": " + getFirstName() + " " + getLastName() + " " + getBarcode();
	}
	
	public boolean equals(Member m)
	{
		if(m.getFirstName().equals(this.getFirstName()) &&
				m.getLastName().equals(this.getLastName()) &&
				m.getBarcode().equals(this.getBarcode()))
		{
			return true;
		} else {
			return false;
		}
	}
	
}
