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
import rest.todo.model.Todo;

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
					str+=cinema;
				}
				return str;
			}
		};
	    cinemas.addAll(TodoDao.instance.getCinemas().values());
	    return "<html>" + cinemas + "</html>"; 
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
						str+=cinema;
					}
					return str;
				}
			};
		  	//debug:
//			//System.out.println("city:"+city);
			for(Cinema cinema : TodoDao.instance.getCinemas().values()) {
				if(cinema==null)
					throw new RuntimeException("Get: Cinema with " + city +  " not found");
				if(cinema.getCity().equals(city) ) {
					list.add(cinema);
				}
			}
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
	      @Context HttpServletResponse servletResponse) throws IOException {
		  System.out.println("city : " + city);
		  System.out.println("name : " + name);
	    Cinema cinema = new Cinema(name, city);
	    TodoDao.instance.getCinemas().put(city, cinema);
	    
	    // TODO when changes.
	    servletResponse.sendRedirect("../../create_cinema.html");
	  }
	  
	  
	  
	  
}
