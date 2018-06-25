package swimLesson;

import java.io.IOException;
import java.io.InputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;

@SuppressWarnings("unused")
public class textReader {

	private String path;
	public static FileWriter writer = null;
	public String[] textData;
	
	
	//Default Constructor
	public textReader(String file_path) 
	{
		path = file_path;
	}
	
	/*
	 * Creates a String[] where each item is a line of text in the file
	 */
	public String[] openFile() throws IOException, InterruptedException
	{
		FileReader fr = new FileReader(path);
		BufferedReader textReader = new BufferedReader(fr);
		
		int numberOfLines = numOfLines();
		if(numberOfLines == 0)
			gui.errorGUI("It looks like there is no data in your SwimClassSheet.txt file.  Make sure you have the right file chosen");
		textData = new String[numberOfLines];
		
		int i;
		
		for(i=0; i < numberOfLines; i++)
		{
			textData[i] = textReader.readLine();
		}
		
		textReader.close();
		return textData;
	}
	
	/*
	 * Getter Method for String[] from file
	 */
	public String[] getAllText()
	{
		return textData;
	}
	
	/*
	 * Prints out the number of lines in the file.
	 */
	public int numOfLines() throws IOException
	{
		FileReader file_to_read = new FileReader(path);
		BufferedReader bf = new BufferedReader(file_to_read);
		
		String aLine;
		int numberOfLines = 0;
		
		while((aLine = bf.readLine()) != null)
			numberOfLines++;
		
		bf.close();
		
		return numberOfLines;
	
	
	}
	
	/*
	 * Create a CSV file
	 */
	public static void generateCsvFile(String fileName)
	{
		
		try {
			writer = new FileWriter(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}

}
