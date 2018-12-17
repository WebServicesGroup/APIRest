package rest.todo.resources;

import java.sql.Connection;   //导入所需要的包
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import rest.todo.dao.TodoDao;
import rest.todo.model.Cinema;
import rest.todo.model.Todo;

import com.sun.jersey.api.view.Viewable;

//Will map the resource to the URL todos
@Path("/cinemas")
public class CinemasResources {
	// Allows to insert contextual objects into the class, 
	  // e.g. ServletContext, Request, Response, UriInfo
	  @Context
	  UriInfo uriInfo;
	  @Context
	  Request request;
	  
	// Return the list of todos to the user in the browser
	  
	  /*
	  @GET
	  @Produces(MediaType.TEXT_XML)
	  public List<Cinema> getCinemasXml() {
	    List<Cinema> cinemas = new ArrayList<Cinema>(){
			@Override
			public
			String toString() {
				String str = "";
				for(Cinema cinema : this) {
					str+=cinema;
				}
				return str;
			}
		};
	    cinemas.addAll(TodoDao.instance.getCinemas().values());
	    return cinemas; 
	  }
	  */
	  
	  
	  @GET
	  @Produces(MediaType.TEXT_HTML)
	  public String getCinemasHTML() {
	    List<Cinema> cinemas = new ArrayList<Cinema>(){
			@Override
			public
			String toString() {
				String str = "";
				for(Cinema cinema : this) {
					str+="<a href=\"http://localhost:8080/rest.todo/rest/cinemas/";
					str+=cinema.getId();
					str+="\">";
					str+=cinema;
					str+="</a><br>";
				}
				return str;
			}
		};
	    cinemas.addAll(TodoDao.instance.getCinemas().values());
	    return "" + cinemas; 
	  }
	  
	  @Path("/dropdown")
	  @GET
	  @Produces(MediaType.TEXT_HTML)
	  public String getCinemasDropdown() {
	    List<Cinema> cinemas = new ArrayList<Cinema>(){
			@Override
			public
			String toString() {
				String str = "<select name=\"cinema_id\">";
				for(Cinema cinema : this) {
					str+="<option value=\"" + cinema.getId() + "\">";
					str+=cinema.getName();
					str+="</option>";
				}
				str+="</select>";
								
				return str;
			}
		};
	    cinemas.addAll(TodoDao.instance.getCinemas().values());
	    return "" + cinemas; 
	  }
	  
	  
	  
	// Defines that the next path parameter after todos is
	  // treated as a parameter and passed to the TodoResources
	  // Allows to type http://localhost:8080/rest.todo/rest/todos/1
	  // 1 will be treaded as parameter todo and passed to TodoResource
	  @GET
	  @Path("/query")
	  @Produces(MediaType.TEXT_HTML)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  public String getCinemasFrom(@QueryParam("city") String city) {
		  ArrayList<Cinema> list = new ArrayList<Cinema>(){
				@Override
				public
				String toString() {
					String str = "";
					for(Cinema cinema : this) {
						str+="<a href=\"http://localhost:8080/rest.todo/rest/cinemas/";
						str+=cinema.getId();
						str+="\">";
						str+=cinema;
						str+="</a><br>";
					}
					return str;
				}
			};
		  	//debug:
//			//System.out.println("city:"+city);
			for(Cinema cinema : TodoDao.instance.getCinemas().values()) {
				if(cinema==null)
					throw new RuntimeException("Get: Cinema with " + city +  " not found");
				if(cinema.getCity().contains(city) ) {
					list.add(cinema);
				}
			}
			
			// if we cannot find a cinema with the city name entered
			if(list.isEmpty())
				return "<html>There is no cinema in the city " + city + "</html>";
			else
				return "<html>" + list + "</html>";
	  }
	  
	  // retuns the number of todos
	  // Use http://localhost:8080/rest.todo/rest/todos/count
	  // to get the total number of records
	  @GET
	  @Path("count")
	  @Produces(MediaType.TEXT_PLAIN)
	  public String getCount() {
	    int count = TodoDao.instance.getCinemas().size();
	    return String.valueOf(count);
	  }
	  
	  
	  @POST
	  @Path("/createCinema")
	  @Produces(MediaType.TEXT_HTML)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  public void newCinema(@FormParam("city") String city,
			  @FormParam("name") String name,
	      @Context HttpServletResponse servletResponse,
	      @Context HttpServletRequest request) throws IOException, ServletException {
		  System.out.println("city : " + city);
		  System.out.println("name : " + name);
	    Cinema cinema = new Cinema(name, city);
	    TodoDao.instance.getCinemas().put(cinema.getId(), cinema);
	    
	    // TODO when changes.
	    servletResponse.sendRedirect("../../create_cinema.html");
	    request.getRequestDispatcher("/WEB-INF/administration.html").forward(request, servletResponse);
	    //servletResponse.sendRedirect("../../login.html");
	  }
	  
	  
	  @GET
	  @Path("/producerCinema")
	  @Produces(MediaType.TEXT_HTML)
	  public String producerCinemasHTML() {
		  
		   //collect data from Database
		    Connection con;
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/hellocine?autoReconnect=true&useSSL=false";
			String user ="root";
			String password = "";
			List<Cinema> cinemaList = new ArrayList<Cinema>(){
				@Override
				public
				String toString() {
					String str = "";
					for(Cinema cinema : this) {
						str+=cinema;
					}
					return str;
				}
			};
			
			//use the data from database
			try {
				 //Load driver
				 Class.forName(driver);
				 //Connect to the MySQL database! !
				 con = DriverManager.getConnection(url,user,password);
		         
				 //create statement object to execute SQL script
				 Statement statement = con.createStatement();
				 String sql = "select * from cinema";
				 ResultSet rs = statement.executeQuery(sql); //ResultSet Class used to store the data you get from database
				 
				  while (rs.next()) {
					  Cinema cn = new Cinema();
					  cn.setId(rs.getInt("id"));
					  cn.setName(rs.getString("name"));
					  cn.setCity(rs.getString("address"));
					  cinemaList.add(cn);
				  }
				  rs.close();
				  con.close();
			
		    //driver Exception & connection Exception
			}catch(ClassNotFoundException e) {
				System.out.println("Sorry,can`t find the Driver!"); 
				e.printStackTrace(); 
			}catch(SQLException e) {
				 e.printStackTrace();  
			}catch (Exception e) {
				 e.printStackTrace();
			}finally{
				//System.out.println("Success access to Database！！");
			}
	   
	    return "<html>" + "This is all cinemas in database!!<br>" + cinemaList + "If you want to create new cinema, click " 
	    + "<form method=\"get\" action=\"../../create_cinema.html\">\n" + 
	    "    <button type=\"submit\">AddCinema</button>\n" + 
	    " </form>" + "</html>"; 
	  }
	  
}
