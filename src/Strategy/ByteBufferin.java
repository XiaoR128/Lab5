package Strategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.io.RandomAccessFile;   


public class ByteBufferin implements InmethodStrategy{
	@Override
	public String input(String filePath) {
		String p = new String();
		File file = new File(filePath);
		CharBuffer charBuffer = null;
		try {
			ByteBuffer mbb = new RandomAccessFile(file, "r").getChannel()
					.map(FileChannel.MapMode.READ_ONLY, 0,
					file.length());
			Charset charset = Charset.forName("UTF-8");
			CharsetDecoder decoder = charset.newDecoder();
			charBuffer = decoder.decode(mbb);
			mbb.flip();
			p = charBuffer.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p;
	}
	
}