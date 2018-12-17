
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
//Will map the resource to the URL cinemas
@Path("/cinemas")
public class CinemasResources {
	// Allows to insert contextual objects into the class, 
	  // e.g. ServletContext, Request, Response, UriInfo
	  @Context
	  UriInfo uriInfo;
	  @Context
	  Request request;
	  
	  /*
	   * Use http://localhost:8080/rest.todo/rest/cinemas/{id}
	   * Return the informations of a cinema into <html> + all the sessions in this cinema
	   */
	  @GET
	  @Produces(MediaType.TEXT_HTML)
	  @Path("{id}")
	  public String getCinema(@PathParam("id") int id) {
		  Cinema cinema = TodoDao.instance.getCinemas().get(id);
		  
		  // if the there is no cinema with the id entered
		  if(cinema == null)
			  return "<html>The cinema with this id " +  cinema.getId() + " doesn\'t exist</html>";
		  
		  else {
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
			  
			  return "<html><h1>" + cinema + "</h1><h2>" + "All the movies in this cinema</h2>" +  movieList + "</html>";
		  }
	  }
	  
	  /*
	   * Use http://localhost:8080/rest.todo/rest/cinemas
	   * Return the informations of all cinemas
	   */
	  @GET
	  @Produces(MediaType.TEXT_HTML)
	  public String getCinemasHTML() {
	    ListCinemas cinemas = new ListCinemas();
	    cinemas.addAll(TodoDao.instance.getCinemas().values());
	    return "" + cinemas; 
	  }
	  
	  /*
	   * Use http://localhost:8080/rest.todo/rest/cinemas/dropdown
	   * Return all the cinemas existing into a dropdown box
	   */
	  @GET
	  @Path("/dropdown")
	  @Produces(MediaType.TEXT_HTML)
	  public String getCinemasDropdown() {
	    List<Cinema> cinemas = new ArrayList<Cinema>(){
			@Override
			public String toString() {
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
	  

	  /*
	   * For the searching part
	   * Use http://localhost:8080/rest.todo/rest/cinemas/query
	   * return the list of cinemas containing the query "city" entered as a Param
	   */
	  @GET
	  @Path("/query")
	  @Produces(MediaType.TEXT_HTML)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  public String getCinemasFrom(@QueryParam("city") String city) {
		  
		ListCinemas list = new ListCinemas();
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
	  
	  /* 
	   * Use http://localhost:8080/rest.todo/rest/cinemas/count
	   * return the total number of cinemas
	   */
	  @GET
	  @Path("count")
	  @Produces(MediaType.TEXT_PLAIN)
	  public String getCount() {
	    int count = TodoDao.instance.getCinemas().size();
	    return String.valueOf(count);
	  }
	  

	  
	  /* 
	   * Use http://localhost:8080/rest.todo/rest/cinemas/createCinema
	   * Allows us the creation of a cinema by getting information from a form
	   */
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
	         
			 PreparedStatement ps=con.prepareStatement("insert into cinema(name,address) values(?,?)");
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
	  
	  
	  /* 
	   * Use http://localhost:8080/rest.todo/rest/cinemas/deleteCinema
	   * delete the cinema selected by the user inside a form section
	   */ 
	  @POST
	  @Path("/deleteCinema")
	  @Produces(MediaType.TEXT_HTML)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  public void removeCinema(@FormParam("cinema_id") int cinemaId,
						       @Context HttpServletResponse servletResponse,
						       @Context HttpServletRequest request) throws IOException, ServletException {
		System.out.println("cinema_id : " + cinemaId);
	   
		//collect data from Database
		    Connection con;
			String driver = Constants.driver;
			String url = Constants.url;
			String user = Constants.user;
			String password = Constants.password;
			
		//begin to delete
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,password);
			// we deleting the cinema from the DB
			PreparedStatement ps=con.prepareStatement("delete from cinema where id = ?");
			ps.setInt(1,cinemaId);
			ps.executeUpdate();
			// we also need the delete the session at this Cinema
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
	    request.getRequestDispatcher("/WEB-INF/administration.html").forward(request, servletResponse); 
	    
	    //servletResponse.sendRedirect("../../create_session.html");
	  }

}
