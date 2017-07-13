package swimLesson;

import java.io.IOException;
import java.io.InputStream;
import java.io.FileReader;
import java.io.BufferedReader;

@SuppressWarnings("unused")
public class textReader {

	private String path;
	
	public String[] textData;
	
	public textReader(String file_path) 
	{
		path = file_path;
	}
	
	public String[] openFile() throws IOException, InterruptedException
	{
		FileReader fr = new FileReader(path);
		BufferedReader textReader = new BufferedReader(fr);
		
		int numberOfLines = readLines();
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
	
	public String[] getAllText()
	{
		return textData;
	}
	
	public int readLines() throws IOException
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

}
