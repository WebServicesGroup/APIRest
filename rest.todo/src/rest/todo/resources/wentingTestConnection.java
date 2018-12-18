package rest.todo.resources;  
 
 
import java.sql.Connection;   //SQL package
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.Statement;

 
public class wentingTestConnection {
	
	public static void main(String[] args) {
		Connection con;
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/hellocine?autoReconnect=true&useSSL=false";
		String user ="root";
		String password = "";
		
		try {
			 Class.forName(driver);
			 con = DriverManager.getConnection(url,user,password);
             //Test whether it connected
			 if(!con.isClosed())
				System.out.println("Succeeded connecting to the Database!");
			 
			 
		     
			 Statement statement = con.createStatement();
			 String sql = "select * from cinema";
			 ResultSet rs = statement.executeQuery(sql);
			 System.out.println("-------------------");
			 System.out.println("This is all cinema:");
			 System.out.println("-------------------");
			 System.out.println(" ID " + "\t" + " NAME " + "\t"  +"ADDRESS");
			 
			  int id;
			  String name;
			  String address;
			  while (rs.next()) {
				  id = rs.getInt("id");
				  name = rs.getString("name");
				  address = rs.getString("address");
				  
				  System.out.println(id + "\t" + name + "\t" +address);
			  }
			  

			  PreparedStatement ps=con.prepareStatement("delete from cinema where id = ?");
			  ps.setInt(1,5);
			  ps.executeUpdate();
			  ps.close();
			  
			  
			  rs.close();
			  con.close();
			  
		}catch(ClassNotFoundException e) {
			System.out.println("Sorry,can`t find the Driver!"); 
			e.printStackTrace(); 
		}catch(SQLException e) {
			 e.printStackTrace();  
		}catch (Exception e) {
			 e.printStackTrace();
		}finally{
			//System.out.println("Success acess to Database£¡£¡");
			}
	}
	
}
