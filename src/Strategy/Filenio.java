package Strategy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Filenio implements InmethodStrategy{

	
	@Override
	public String input(String filePath) {
		String p=new String();
		try {
			p = new String(Files.readAllBytes(Paths.get(filePath)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p;
	}

}