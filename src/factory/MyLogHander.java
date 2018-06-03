package factory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Ϊ���õõ�����־���Ӿ߱��ɶ���
 * @author 11051
 *
 */
public class MyLogHander extends Formatter { 
    @Override 
    public String format(LogRecord record) { 
    	Date day=new Date();
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss"); 
    	String st = df.format(day);
        return st+" "+record.getLevel() + ":" + record.getMessage()+" "+record.getSourceClassName()+"."+record.getSourceMethodName()+"\n";
    } 
}
