// import java.util.List;
// import java.util.Map;
// import java.sql.Connection;
// import java.sql.Statement;
// import java.sql.DriverManager;
// import java.sql.ResultSet;
import java.util.*;
import java.sql.*;
import java.io.*;

public interface FetchAndProcess {
    static String DB_NAME = "pokemon.db";
    static String TABLE_NAME = "pokemon";

    /* The map populated by fetch */
    // public Map<String, String> data = new HashMap<String, String>();
    
    // no default implementation
    void fetch(List<String> paths);

    // no default implementation
    Map<String, String> exposeData();
    
    /* Provides a default implementation that does a lot of work:
     * 1. Create the `TABLE_NAME` table if it does not exist (along with a uniqueness constraint).
     * 2. Inserts data into the table, safely. ensuring no duplication.
     * 3. Returns the Connection (useful for the FetchAndProcessNetwork* classes)
     */
    default List<String> process() {
        String sql1 = "CREATE TABLE IF NOT EXISTS pokemon (\n"
                + "    pokemon_name TEXT,\n"
                + "    source_path TEXT,\n"
                + "    PRIMARY KEY(pokemon_name,source_path)\n"
                + ");";
        String sql = "INSERT INTO pokemon(pokemon_name,source_path) VALUES(?,?)";
        try{
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:"+DB_NAME);
            Statement stmt = conn.createStatement();
            stmt.execute(sql1);
            for (Map.Entry<String,String> entry : exposeData().entrySet())
            {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            String pokemon_name = entry.getKey(),source_path = entry.getValue();
            pokemon_name = pokemon_name.split(" ")[0];
            pstmt.setString(1, pokemon_name);
            pstmt.setString(2, source_path);
            try{pstmt.executeUpdate();}
            catch(Exception e){continue;}
            }
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
 
	return null;
    }
}
