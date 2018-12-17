<<<<<<< HEAD
package rest.todo.resources;

import java.sql.Connection;   //导入所需要的包
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import rest.todo.dao.TodoDao;
import rest.todo.model.Cinema;
import rest.todo.model.Movie;
import rest.todo.model.Session;
import rest.todo.model.Todo;
import rest.todo.utils.Constants;

import com.sun.jersey.api.view.Viewable;
import rest.todo.model.*;
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
	  @Path("{id}")
	  public String getCinema(@PathParam("id") int id) {
		  Cinema cinema = TodoDao.instance.getCinemas().get(id);
		  
		// if the there is no cinema with the id entered
		  if(cinema == null)
			  return "<html>The cinema with this id does not exist</html>";
		  
		  ListMovies movieList= new ListMovies();
		
		  ArrayList<Session> sessionList = new ArrayList<Session>();
		  
		// search for the session with the cinema id
		  for(Session session : TodoDao.instance.getSessions().values() ) {
			  if(session.getCinema_id() == cinema.getId()) {
				  sessionList.add(session);
			  }
		  }
		  
		  
		  // get the UNIQUE movies
		  HashSet<Movie> hashSet = new HashSet<Movie>();
		  
		  for(Session session : sessionList)
			  hashSet.add(TodoDao.instance.getMovies().get(session.getMovie_id()));
		  
		  // get the unique movies in the movie list
		  movieList.addAll(hashSet);
		  
		  if(cinema != null)
			  return "<html><h1>" + cinema + "</h1><h2>" + "All the movies in this cinema</h2>" +  movieList + "</html>";
		  else
			  return "<html>The cinema with this id " +  cinema.getId() + " doesn\'t exist</html>";
	  }
	  
	  
	  @GET
	  @Produces(MediaType.TEXT_HTML)
	  public String getCinemasHTML() {
	    ListCinemas cinemas = new ListCinemas();
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
		  ListCinemas list = new ListCinemas();
			
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
	    
	  //collect data from Database
	    Connection con;
		String driver = Constants.driver;
		String url = Constants.url;
		String user = Constants.user;
		String password = Constants.password;
		
		//begin adding
		try {
			 //Load driver
			 Class.forName(driver);
			 //Connect to the MySQL database! !
			 con = DriverManager.getConnection(url,user,password);
	         
			 PreparedStatement ps=con.prepareStatement("insert into cinema(name,address) values(?,?)");//创建一个Statement对象
			  //ps.setInt(1,4);
			  ps.setString(1,name);
			  ps.setString(2,city);
			  ps.executeUpdate();
			 
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
	    
	    // TODO when changes.

	    //servletResponse.sendRedirect("../../create_cinema.html");
	    
	    request.getRequestDispatcher("/WEB-INF/administration.html").forward(request, servletResponse);
	    //servletResponse.sendRedirect("../../login.html");
}
	  
	  
	  
	  
}
=======
package rest.todo.resources;

import java.sql.Connection;   //导入所需要的包
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import rest.todo.dao.TodoDao;
import rest.todo.model.Cinema;
import rest.todo.model.Movie;
import rest.todo.model.Session;
import rest.todo.model.Todo;

import com.sun.jersey.api.view.Viewable;
import rest.todo.model.*;
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
	  @Path("{id}")
	  public String getCinema(@PathParam("id") int id) {
		  Cinema cinema = TodoDao.instance.getCinemas().get(id);
		  
		// if the there is no cinema with the id entered
		  if(cinema == null)
			  return "<html>The cinema with this id does not exist</html>";
		  
		  ListMovies movieList= new ListMovies();
		
		  ArrayList<Session> sessionList = new ArrayList<Session>();
		  
		// search for the session with the cinema id
		  for(Session session : TodoDao.instance.getSessions().values() ) {
			  if(session.getCinema_id() == cinema.getId()) {
				  sessionList.add(session);
			  }
		  }
		  
		  
		  // get the UNIQUE movies
		  HashSet<Movie> hashSet = new HashSet<Movie>();
		  
		  for(Session session : sessionList)
			  hashSet.add(TodoDao.instance.getMovies().get(session.getMovie_id()));
		  
		  // get the unique movies in the movie list
		  movieList.addAll(hashSet);
		  
		  if(cinema != null)
			  return "<html><h1>" + cinema + "</h1><h2>" + "All the movies in this cinema</h2>" +  movieList + "</html>";
		  else
			  return "<html>The cinema with this id " +  cinema.getId() + " doesn\'t exist</html>";
	  }
	  
	  
	  @GET
	  @Produces(MediaType.TEXT_HTML)
	  public String getCinemasHTML() {
	    ListCinemas cinemas = new ListCinemas();
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
	    //System.out.println(cinemas);
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
		  ListCinemas list = new ListCinemas();
			
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
	    
	  //collect data from Database
	    Connection con;
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/hellocine?autoReconnect=true&useSSL=false";
		String user ="root";
		String password = "";
		
		//begin adding
		try {
			 //Load driver
			 Class.forName(driver);
			 //Connect to the MySQL database! !
			 con = DriverManager.getConnection(url,user,password);
	         
			 PreparedStatement ps=con.prepareStatement("insert into cinema(name,address) values(?,?)");//创建一个Statement对象
			  //ps.setInt(1,4);
			  ps.setString(1,name);
			  ps.setString(2,city);
			  ps.executeUpdate();
			 
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
	    
	    // TODO when changes.

	    //servletResponse.sendRedirect("../../create_cinema.html");
	    
	    request.getRequestDispatcher("/WEB-INF/administration.html").forward(request, servletResponse);
	    //servletResponse.sendRedirect("../../login.html");
}
	  
	  @POST
	  @Path("/deleteCinema")
	  @Produces(MediaType.TEXT_HTML)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  public void newCinema(@FormParam("cinema_id") int cinemaId,
	      @Context HttpServletResponse servletResponse,
	      @Context HttpServletRequest request) throws IOException, ServletException {
		  System.out.println("cinema_id : " + cinemaId);
	   
		//collect data from Database
		    Connection con;
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/hellocine?autoReconnect=true&useSSL=false";
			String user ="root";
			String password = "";
			
			//begin to delete
			try {
				 Class.forName(driver);
				 con = DriverManager.getConnection(url,user,password);
				 PreparedStatement ps=con.prepareStatement("delete from cinema where id = ?");
				  ps.setInt(1,cinemaId);
				  ps.executeUpdate();
				  ps=con.prepareStatement("delete from session where cinema_id = ?");
				  ps.setInt(1,cinemaId);
				  ps.executeUpdate();
				  ps.close();
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
		  
	    /*
		for(Session session : TodoDao.instance.getSessions().values()) {
			if(session==null)
				throw new RuntimeException("Get: session with " + movieId +  " not found");
			if(session.getMovie_id() == (movieId) ) {
				TodoDao.instance.getMovies().remove(session);
			}
		}
		 TodoDao.instance.getMovies().remove(movieId);
	    // TODO when changes.
		 */
	    request.getRequestDispatcher("/WEB-INF/administration.html").forward(request, servletResponse); 
	    
	    //servletResponse.sendRedirect("../../create_session.html");
	  	//debug:
     	//System.out.println("city:"+city);
		
	  }
	  
	  
	  
}
>>>>>>> branch 'master' of https://github.com/WebServicesGroup/APIRest
