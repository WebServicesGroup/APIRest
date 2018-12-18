
package rest.todo.resources;

import rest.todo.dao.TodoDao;
import rest.todo.model.Cinema;
import rest.todo.model.Movie;
import rest.todo.model.Session;
import rest.todo.utils.Constants;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;


//Will map the resource to the URL sessions
@Path("/sessions")
public class SessionsResources {
	  // Allows to insert contextual objects into the class, 
	  // e.g. ServletContext, Request, Response, UriInfo
	  @Context
	  UriInfo uriInfo;
	  @Context
	  Request request;
	  
	  /*
	   * Use http://localhost:8080/rest.todo/rest/sessions/{id}
	   * Return the informations of session into <html>
	   */
	  @GET
	  @Path("{id}")
	  @Produces(MediaType.TEXT_HTML)
	  public String getSession(@PathParam("id") int id) {
		  Session session = TodoDao.instance.getSessions().get(id);
		  if(session != null)
			  return "<html>" + session + "</html>";
		  else
			  return "<html>The session with this id" + session.getId() +" doesn\'t exist</html>";
	  }
	  

	  /*
	   * Use http://localhost:8080/rest.todo/rest/sessions/dropdown
	   * Return all the sessions existing into a dropdown box
	   */
	  @GET
	  @Path("/dropdown")
	  @Produces(MediaType.TEXT_HTML)
	  public String getSessionsDropdown() {
	    List<Session> sessions = new ArrayList<Session>(){
			@Override
			public String toString() {
				String str = "<select name=\"session_id\">";
				for(Session sessions : this) {
					Cinema cinema = TodoDao.instance.getCinemas().get(sessions.getCinema_id());
					Movie movie = TodoDao.instance.getMovies().get(sessions.getMovie_id());
					str+="<option value=\"" + sessions.getId() + "\">";
					str+=cinema.getName() + " ";
					str+=movie.getName() + " ";
					str+=sessions.getHour() + " ";
					str+=sessions.getDate() + " ";
					str+="</option>";
				}
				str+="</select>";				
				return str;
			}
		};
		// added all the sessions into the List<Sessions> sessions
		sessions.addAll(TodoDao.instance.getSessions().values());
	    return "" + sessions; 
	  }
	  
	  
	  /*
	   * Use http://localhost:8080/rest.todo/rest/sessions
	   * Return all the sessions in HTML Text
	   */
	  @GET
	  @Produces(MediaType.TEXT_HTML)
	  public String getSessionHTML() {
	    List<Session> sessions = new ArrayList<Session>(){
			@Override
			public String toString() {
				String str = "";
				for(Session session : this) {
					str+=session;
				}
				return str;
			}
		};
		sessions.addAll(TodoDao.instance.getSessions().values());
		return "<html>" + sessions + "</html>";
	}

	/*
	 * Use http://localhost:8080/rest.todo/rest/sessions/count return the total
	 * number of sessions
	 */
	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount() {
		int count = TodoDao.instance.getSessions().size();
		return String.valueOf(count);
	}

	/*
	 * Use http://localhost:8080/rest.todo/rest/sessions/createSession 
	 * Allows us the creation of a session by getting information from a form
	 */
	@POST
	@Path("/createSession")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void newSession(@FormParam("movie_id") int movieId, @FormParam("cinema_id") int cinemaId,
			@FormParam("hour") Time hour, @FormParam("date") Date date, @FormParam("version") String version,
			@Context HttpServletResponse servletResponse, @Context HttpServletRequest request)
			throws IOException, ServletException {
		System.out.println("movie_id : " + movieId);
		System.out.println("cinema_id : " + cinemaId);
		System.out.println("hour : " + hour);
		System.out.println("date : " + date);
		System.out.println("version : " + version);
		Session session = new Session(movieId, cinemaId, hour, date, version);
		TodoDao.instance.getSessions().put(session.getId(), session);

		// TODO when changes.
		request.getRequestDispatcher("/WEB-INF/administration.html").forward(request, servletResponse);
		// servletResponse.sendRedirect("../../create_session.html");
	}

	/*
	 * Use http://localhost:8080/rest.todo/rest/sessions/deleteSession 
	 * Delete the session selected by the user into a form
	 */
	@POST
	@Path("/deleteSession")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void removeSession(@FormParam("session_id") int sessionId, @Context HttpServletResponse servletResponse,
			@Context HttpServletRequest request) throws IOException, ServletException {
		System.out.println("sessionId : " + sessionId);

		// collect data from Database
		Connection con;
		String driver = Constants.driver;
		String url = Constants.url;
		String user = Constants.user;
		String password = Constants.password;

		// begin to delete
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			PreparedStatement ps = con.prepareStatement("delete from session where id = ?");
			ps.setInt(1, sessionId);
			ps.executeUpdate();
			ps.close();
			con.close();

			// driver Exception & connection Exception
		} catch (ClassNotFoundException e) {
			System.out.println("Sorry,can`t find the Driver!");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// System.out.println("Success access to Database！！");
		}

		request.getRequestDispatcher("/WEB-INF/administration.html").forward(request, servletResponse);
		// servletResponse.sendRedirect("../../create_session.html");
	}
	    sessions.addAll(TodoDao.instance.getSessions().values());
	    return "<html>" + sessions + "</html>"; 
	  }
	  
	  
	  /* 
	   * Use http://localhost:8080/rest.todo/rest/sessions/count
	   * return the total number of sessions
	   */
	  @GET
	  @Path("count")
	  @Produces(MediaType.TEXT_PLAIN)
	  public String getCount() {
	    int count = TodoDao.instance.getSessions().size();
	    return String.valueOf(count);
	  }
	  
	  
	  /* 
	   * Use http://localhost:8080/rest.todo/rest/sessions/createSession
	   * Allows us the creation of a session by getting information from a form
	   */
	  @POST
	  @Path("/createSession")
	  @Produces(MediaType.TEXT_HTML)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  public void newSession(  @FormParam("movie_id") int movieId,
							  @FormParam("cinema_id") int cinemaId,
							  @FormParam("hour") Time hour,
							  @FormParam("date") Date date,
							  @FormParam("version") String version,
						      @Context HttpServletResponse servletResponse,
						      @Context HttpServletRequest request) throws IOException, ServletException {
		  System.out.println("movie_id : " + movieId);
		  System.out.println("cinema_id : " + cinemaId);
		  System.out.println("hour : " + hour);
		  System.out.println("date : " + date);
		  System.out.println("version : " + version);
		  
		  
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
		         
				 PreparedStatement ps=con.prepareStatement("insert into session(movie_id,cinema_id,hour,date,version) values(?,?,?,?,?)");//创建一个Statement对象
				  //ps.setInt(1,4);
				  ps.setInt(1,movieId);
				  ps.setInt(2,cinemaId);
				  ps.setTime(3, hour);
				  ps.setDate(4, date);
				  ps.setString(5, version);
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
	   * Use http://localhost:8080/rest.todo/rest/sessions/deleteSession
	   * return the total number of sessions
	   */
	  @POST
	  @Path("/deleteSession")
	  @Produces(MediaType.TEXT_HTML)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  public void removeSession(  @FormParam("session_id") int sessionId,
							      @Context HttpServletResponse servletResponse,
							      @Context HttpServletRequest request) throws IOException, ServletException {
		    System.out.println("sessionId : " + sessionId);
	   
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
				 PreparedStatement ps=con.prepareStatement("delete from session where id = ?");
				  ps.setInt(1,sessionId);
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
