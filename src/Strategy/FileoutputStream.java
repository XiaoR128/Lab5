package Strategy;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class FileoutputStream implements OutmethodStrategy{

	@Override
	public void output(String filename, List<String> li) {
        FileOutputStream fos;
		try {
			fos = new FileOutputStream(filename);
        	for(int i=0;i<li.size();i++) {
            	String str = li.get(i);
            	fos.write(str.getBytes());  
        	}
	        fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}

}
