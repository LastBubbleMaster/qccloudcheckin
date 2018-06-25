package swimLesson;

public class Swimmer {
	
	private String firstName;
	private String lastName;
	private String barcode;
	
	//Default Constructor
	public Swimmer(String fN, String lN, String b)
	{
		firstName = fN;
		lastName = lN;
		if(b.length() == 0)
			barcode = "N/A";
		else
			barcode = b;
		
	}
	//Getter Method
	public String getFirstName()
	{
		return firstName;
	}
	public String getLastName()
	{
		return lastName;
	}
	public String getBarcode()
	{
		return barcode;
	}
	
	
	public String toString()
	{
		System.out.print(getFirstName() + " " + getLastName() + " " + getBarcode());
		return "";
	}
	
	public boolean equals(Swimmer swimmer)
	{
		if(swimmer.getFirstName().equals(this.getFirstName()) &&
				swimmer.getLastName().equals(this.getLastName()) &&
				swimmer.getBarcode().equals(this.getBarcode()))
		{
			return true;
		} else {
			return false;
		}
	}
	
}
