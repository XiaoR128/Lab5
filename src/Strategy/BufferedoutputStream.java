package Strategy;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class BufferedoutputStream implements OutmethodStrategy{

	@Override
	public void output(String filename, List<String> li) {
        BufferedOutputStream buff;  
        try {
        	buff = new BufferedOutputStream(new FileOutputStream(filename));
        	for(int i=0;i<li.size();i++) {
            	String str = li.get(i);
            	buff.write(str.getBytes());  
        	}
			buff.flush();
			buff.close(); 
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}  
	}

}
