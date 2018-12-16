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
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import rest.todo.dao.TodoDao;
import rest.todo.model.Cinema;
import rest.todo.model.Movie;

@Path("/movies")
public class MoviesResources {
	// Allows to insert contextual objects into the class, 
	  // e.g. ServletContext, Request, Response, UriInfo
	  @Context
	  UriInfo uriInfo;
	  @Context
	  Request request;
	  
	  @GET
	  @Produces(MediaType.TEXT_HTML)
	  public String getMoviesHTML() {
	    ArrayList<Movie> movies = new ArrayList<Movie>(){
			@Override
			public
			String toString() {
				String str = "";
				for(Movie movie : this) {
					str+=movie;
				}
				return str;
			}
		};
	    movies.addAll(TodoDao.instance.getMovies());
	    return "<html>" + movies + "</html>"; 
	  }
	  
	  @Path("/dropdown")
	  @GET
	  @Produces(MediaType.TEXT_HTML)
	  public String getCinemasDropdown() {
	    List<Movie> movies = new ArrayList<Movie>(){
			@Override
			public
			String toString() {
				String str = "<select name=\"movie_id\">";
				for(Movie movie : this) {
					str+="<option value=\"" + movie.getId() + "\">";
					str+=movie.getName();
					str+="</option>";
				}
				str+="</select>";
								
				return str;
			}
		};
	    movies.addAll(TodoDao.instance.getMovies());
	    return "" + movies; 
	  }
	  
	  
	  // Defines that the next path parameter after todos is
	  // treated as a parameter and passed to the TodoResources
	  // Allows to type http://localhost:8080/rest.todo/rest/todos/1
	  // 1 will be treaded as parameter todo and passed to TodoResource
	  @GET
	  @Path("/query")
	  @Produces(MediaType.TEXT_HTML)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  public String getCinemasFrom(@QueryParam("name") String name) {
		  ArrayList<Movie> list = new ArrayList<Movie>(){
				@Override
				public
				String toString() {
					String str = "";
					for(Movie movie : this) {
						str+=movie;
					}
					return str;
				}
			};
		  	//debug:
//				//System.out.println("city:"+city);
			
			for(Movie movie : TodoDao.instance.getMovies()) {
				if(movie==null)
					throw new RuntimeException("Get: Movie with " + name +  " not found");
				if(movie.getName().contains(name) ) {
					list.add(movie);
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
	    int count = TodoDao.instance.getMovies().size();
	    return String.valueOf(count);
	  }
	  
	  
	  @POST
	  @Path("/createMovie")
	  @Produces(MediaType.TEXT_HTML)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  public void newCinema(@FormParam("name") String name,
			  @FormParam("rate") String rate,
			  @FormParam("type") String type,
			  @FormParam("actor") String actor,
			  @FormParam("producer") String producer,
			  @FormParam("length") String length,
			  @FormParam("country") String country,
			  @FormParam("on_screen_date") String on_screen_date,
			  @FormParam("language") String language,
			  @FormParam("description") String description,
	      @Context HttpServletResponse servletResponse) throws IOException {
		  System.out.println("name : " + name);
		  System.out.println("rate : " + rate);
		  System.out.println("type : " + type);
		  System.out.println("actor : " + actor);
		  System.out.println("producer : " + producer);
		  System.out.println("length : " + length);
		  System.out.println("country : " + country);
		  System.out.println("on_screen_date" + on_screen_date);
		  System.out.println("language : " + language);
		  System.out.println("description : " + description);
		  
	    Movie movie = new Movie(name, Integer.parseInt(rate), type,  actor, producer, Integer.parseInt(length), country, on_screen_date, language, description, "test");
	    TodoDao.instance.getMovies().add(movie);
	    
	    // TODO when changes.
	    servletResponse.sendRedirect("../../create_movie.html");
	  }	  
}
