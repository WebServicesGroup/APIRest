package rest.todo.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
	  @GET
	  @Produces(MediaType.TEXT_XML)
	  public List<Cinema> getCinemasBrowser() {
	    List<Cinema> cinemas = new ArrayList<Cinema>();
	    cinemas.addAll(TodoDao.instance.getCinemas().values());
	    return cinemas; 
	  }
	  
	// Defines that the next path parameter after todos is
	  // treated as a parameter and passed to the TodoResources
	  // Allows to type http://localhost:8080/rest.todo/rest/todos/1
	  // 1 will be treaded as parameter todo and passed to TodoResource
	  @GET
	  @Produces(MediaType.TEXT_XML)
	  @Path("{city}/")
	  public List<Cinema> getCinemasFrom(@PathParam("city") String city) {
		  ArrayList<Cinema> list = new ArrayList<Cinema>();
			
			for(Cinema cinema : TodoDao.instance.getCinemas().values()) {
				if(cinema==null)
					throw new RuntimeException("Get: Cinema with " + city +  " not found");
				if(cinema.getCity().equals(city) ) {
					list.add(cinema);
				}
			}
		  
	    return list;
	  }
}
