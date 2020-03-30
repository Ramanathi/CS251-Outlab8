// import java.io.InputStream;
// import java.net.URL;
// import java.net.URLConnection;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Map;
// import java.util.HashMap;
 import java.sql.*;
 import java.util.*;
 import java.net.*;
 import java.io.*;
/* add some more imports */

class MultithreadingDemo extends Thread 
{ 
    public void run(String path,Map<String, String> data) 
    { 
        try{    
            URL url = new URL(path);
            URLConnection con = url.openConnection();
            InputStream is =con.getInputStream();
            Scanner in = new Scanner(is);
            while(in.hasNext())
            {
                String s1 = in.nextLine(); s1 = s1.trim();
                if(!s1.equals("")) data.put(s1+" "+path,new String(path));
            } 
        //      Set< Map.Entry< String,String> > st = data.entrySet();  
        //      for (Map.Entry< String,String> me:st) 
        // { 
        //     System.out.print(me.getKey()+":"); 
        //     System.out.println(me.getValue()); 
        // }    
            }catch(Exception e){System.out.println(e);}   
    } 
} 

public class FetchAndProcessFromNetworkFast implements FetchAndProcess {
    private Map<String, String> data;

    @Override
    public Map<String, String> exposeData() {
	return data;
    }

    @Override
    public void fetch(List<String> paths) {	
          int len = paths.size();
        data = new HashMap< String,String>(); 
        for(int k=0;k<len;k++)
        {
            MultithreadingDemo object = new MultithreadingDemo(); 
            object.run(paths.get(k),data); 
        }
        
	// Implement here, just do it parallely!
        
    }

    @Override
    public List<String> process() {
        FetchAndProcess.super.process();
        String query = "SELECT pokemon_name, COUNT(*) c FROM pokemon GROUP BY pokemon_name HAVING c > 1;";
        try{
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:"+DB_NAME);
            PreparedStatement pstmt = conn.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery();
                  while (rs.next()) {
                System.out.println(rs.getString(1));
            }
        }
        catch (Exception e){System.out.println(e);}
	// Implement here
	// Can you make use of the default implementation here?
	// See https://dzone.com/articles/interface-default-methods-java

	return null;
    }
}
