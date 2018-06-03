package Strategy;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Filewriter implements OutmethodStrategy{
	@Override
	public void output(String filename,List<String> li) {
		FileWriter fw;
		try {
			fw = new FileWriter(filename);
        	for(int i=0;i<li.size();i++) {
            	String str = li.get(i);
            	fw.write(str,0,str.length());  
        	}
//	        fw.write(str, 0, str.length()); 
	        fw.close(); 
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}

}
