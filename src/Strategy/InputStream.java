package Strategy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class InputStream implements InmethodStrategy{

	@Override
	public String input(String filePath) {
		File file = new File(filePath);
		Long filelength = file.length();
		byte[] filecontent = new byte[filelength.intValue()];
		String p = new String();
		try {
			FileInputStream in = new FileInputStream(file);
			p=new String(filecontent,0,in.read(filecontent));
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p;
	}
	
}
