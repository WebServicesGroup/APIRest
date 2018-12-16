package rest.todo.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import rest.todo.model.Session;
import rest.todo.model.Todo;

//Will map the resource to the URL todos
@Path("/sessions")
public class SessionsResources {
	// Allows to insert contextual objects into the class, 
	  // e.g. ServletContext, Request, Response, UriInfo
	  @Context
	  UriInfo uriInfo;
	  @Context
	  Request request;
	  
	  
	  @GET
	  @Produces(MediaType.TEXT_HTML)
	  public String getCinemasHTML() {
	    List<Session> sessions = new ArrayList<Session>(){
			@Override
			public
			String toString() {
				String str = "";
				for(Session session : this) {
					str+=session;
				}
				return str;
			}
		};
	    sessions.addAll(TodoDao.instance.getSessions());
	    return "<html>" + sessions + "</html>"; 
	  }
	  
	  
	  
	  
	  // retuns the number of todos
	  // Use http://localhost:8080/rest.todo/rest/todos/count
	  // to get the total number of records
	  @GET
	  @Path("count")
	  @Produces(MediaType.TEXT_PLAIN)
	  public String getCount() {
	    int count = TodoDao.instance.getSessions().size();
	    return String.valueOf(count);
	  }
	  
	  //(int id, int movie_id, int cinema_id, String hour, String date, String version) 
	  @POST
	  @Path("/createSession")
	  @Produces(MediaType.TEXT_HTML)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  public void newCinema(@FormParam("movie_id") int movieId,
			  @FormParam("cinema_id") int cinemaId,
			  @FormParam("hour") String hour,
			  @FormParam("date") String date,
			  @FormParam("version") String version,
	      @Context HttpServletResponse servletResponse) throws IOException {

		  System.out.println("movie_id : " + movieId);
		  System.out.println("cinema_id : " + cinemaId);
		  System.out.println("hour : " + hour);
		  System.out.println("date : " + date);
		  System.out.println("version : " + version);
	    Session session = new Session(movieId,cinemaId,hour,date,version);
	    TodoDao.instance.getSessions().add(session);
	    
	    // TODO when changes.
	    servletResponse.sendRedirect("../../create_sessions.html");
	  }
	  
	  
	  
	  
	  
}