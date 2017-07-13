package swimLesson;

public class Swimmer {
	
	private String firstName;
	private String lastName;
	private String barcode;
	
	public Swimmer(String fN, String lN, String b)
	{
		firstName = fN;
		lastName = lN;
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
		System.out.println(getFirstName() + " " + getLastName() + " " + getBarcode());
		return "";
	}
	
}
