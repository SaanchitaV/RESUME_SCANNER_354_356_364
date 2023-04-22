//package pdf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
//import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.HashSet;   
import java.util.LinkedList;   
import java.util.List;
import java.util.Set;

public class PostgreSQLJDBC {
	//method for insert skill into testdb
	public void insertSkill(String email, String skill) {
		Connection c = null;
        Statement stmt = null;
        try {
           Class.forName("org.postgresql.Driver");
           c = DriverManager
              .getConnection("jdbc:postgresql://localhost:5432/testdb",
              "postgres", "Skanda&2008"); 
           stmt = c.createStatement();
           String sql = "insert into skill values ('" + email + "','" + skill + "');";
           stmt.executeUpdate(sql);
           stmt.close();
           c.close();
        } catch (Exception e) {
           e.printStackTrace();
           System.err.println(e.getClass().getName()+": "+e.getMessage());
           System.exit(0);
        }
    }
	//method for insert name into testdb
	//input email, firstName, lastName as strings
	//execute insert command to testdb database
	public void insertName(String email, String firstName, String lastName) {
		Connection c = null;
        Statement stmt = null;
        try {
           Class.forName("org.postgresql.Driver");
           c = DriverManager
              .getConnection("jdbc:postgresql://localhost:5432/testdb",
              "postgres", "Skanda&2008"); 
           stmt = c.createStatement();
           String sql = "insert into name values ('" + email + "','" + firstName + "','" + lastName + "');";
           stmt.executeUpdate(sql);
           stmt.close();
           c.close();
        } catch (Exception e) {
           e.printStackTrace();
           System.err.println(e.getClass().getName()+": "+e.getMessage());
           System.exit(0);
        }
    }
	
	//This intersect function is for multiple skill
	public static String[] intersect(String[] arr1, String[] arr2){
        List<String> l = new LinkedList<String>();
        Set<String> common = new HashSet<String>();                  
        for(String str:arr1){
            if(!l.contains(str)){
                l.add(str);
            }
        }
        for(String str:arr2){
            if(l.contains(str)){
                common.add(str);
            }
        }
        String[] result={};
        return common.toArray(result);
    }
	
	public String[] multipleSkillQuery(String multiSkill) {
		String[] QuerySkillList = new String[20];
		QuerySkillList = multiSkill.split(";");
		String[] result_insect = skillQuery(QuerySkillList[0]);
		for (int i = 0; i < QuerySkillList.length; i++) {
			result_insect = intersect(result_insect, skillQuery(QuerySkillList[i]));
		}
		return result_insect;
	}
	
	//method for skill query
	//input skill name as string, output email | skill table
	public String[] skillQuery(String skill) {
	      Connection c = null;
	      Statement stmt = null;
	      try {
	    	 Class.forName("org.postgresql.Driver");
	         c = DriverManager
	              .getConnection("jdbc:postgresql://localhost:5432/testdb",
	              "postgres", "Skanda&2008"); 
	         c.setAutoCommit(false);
	         //command as Statement class
	         //intermediate result as ResultSet class, use getString to get email as string
	         stmt = c.createStatement();
	         ResultSet rs = stmt.executeQuery( "select * from skill where (skill ='"+ skill +"');" );
	         //count the length of the email list;
	         int counter = 0;
	         while ( rs.next() ) {
	        	 counter++;
	         }
	         
	         //do search again to recover rs.next()
	         rs = stmt.executeQuery( "select * from skill where (skill ='"+ skill +"');" );
	         //generate email list;
	         String[]emailList = new String[counter];
	         int posi = 0;
	         while ( rs.next() ) {
	        	 emailList[posi] = rs.getString("email");
	        	 posi++;
	         }
	         rs.close();
	         stmt.close();
	         c.close();
	         return emailList;
	      } catch ( Exception e ) {
	         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	         System.exit(0);
	         return null;
	      }
	   }
}

