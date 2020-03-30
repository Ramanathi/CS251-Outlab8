 // import java.io.FileInputStream;
 // import java.io.IOException;
 // import java.util.ArrayList;
 // import java.util.List;
 // import java.util.Map;
 // import java.util.HashMap;
 // import java.util.Scanner;
 import java.io.*;
 import java.util.*;

public class FetchAndProcessFromDisk implements FetchAndProcess{
    private Map<String, String> data;

    @Override
    public Map<String, String> exposeData() {
	return data;
    }

    @Override
    public void fetch(List<String> paths){
	// Implement here
    	int len = paths.size();
    	data = new HashMap< String,String>(); 
    	for(int k=0;k<len;k++)
    	{
    		try{    
            FileInputStream fis =new FileInputStream(paths.get(k));    
            Scanner in = new Scanner(fis);
            File f = new File(paths.get(k));
            String s = f.getName();
            
            int pos = s.lastIndexOf(".");
            if (pos > 0) {
                s = s.substring(0, pos);
                    }

            while(in.hasNext())
            {
            	String s1 = in.nextLine();s1 = s1.trim();
            	if(!s1.equals("")) data.put(s1+" "+s,new String(s));
            } 
            fis.close(); 
        //     Set< Map.Entry< String,String> > st = data.entrySet();  
        //      for (Map.Entry< String,String> me:st) 
        // { 
        //    System.out.print(me.getKey()+":"); 
        //     System.out.println(me.getValue()); 
        // }    
          }catch(Exception e){System.out.println(e);}    
    	}
    }
}
