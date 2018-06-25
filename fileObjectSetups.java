package swimLesson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.filechooser.FileSystemView;

import org.apache.commons.io.FileUtils;

public class fileObjectSetups {

	
	//Not Used
	public static void setup() {

		String sourceLoc = new File("").getAbsolutePath() + "\\abc.jar";
		String docLoc = FileSystemView.getFileSystemView().getDefaultDirectory().getAbsolutePath()
				+ "\\QCCloud Check-In";
		File sourceF = new File(sourceLoc);
		File docF = new File(docLoc);
		System.out.println("TEST99");
		try {
			System.out.println("H");
			FileUtils.copyFileToDirectory(sourceF, docF);
			System.out.println("HH");
		} catch (IOException e) {
			System.out.println("HHH");
			e.printStackTrace();
		}
		System.out.println("HHHH");

	}
}
