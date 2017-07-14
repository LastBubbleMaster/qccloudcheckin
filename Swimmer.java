package swimLesson;

public class Swimmer {
	
	private String firstName;
	private String lastName;
	private String barcode;
	
	public Swimmer(String fN, String lN, String b)
	{
		firstName = fN;
		lastName = lN;
		if(b.length() == 0)
			barcode = "NOT AVAILABLE";
		else
			barcode = b;
		
	}
	
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
	
}
