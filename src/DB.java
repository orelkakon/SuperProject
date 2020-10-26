import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DB { // to build the Tables And DB
	Connection c= null;
	Statement st = null;

	DB(){
		try{
			Class.forName("org.sqlite.JDBC");
			String path="jdbc:sqlite:DB1.db";
            c = DriverManager.getConnection(path);
            System.out.println("Connected to Data Base!!!");
		}
		catch(Exception e ){
			
		}
	}
	
	public void CloseConnection(){
		try{
			c.close();			
		} 
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
}
