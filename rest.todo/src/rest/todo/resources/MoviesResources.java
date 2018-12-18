package rest.todo.resources;

import rest.todo.dao.TodoDao;
import rest.todo.model.ListMovies;
import rest.todo.model.Movie;
import rest.todo.model.Session;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.text.SimpleDateFormat;
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
import rest.todo.model.Movie;

//Will map the resource to the URL movies
@Path("/movies")
public class MoviesResources {
	// Allows to insert contextual objects into the class,
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	
	/*
	 * Use http://localhost:8080/rest.todo/rest/movies 
	 * Return all the movies in HTML Text
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getMoviesHTML() {
		ListMovies movies = new ListMovies();
		movies.addAll(TodoDao.instance.getMovies().values());
		return "" + movies;
	}

	
    /*
     * Use http://localhost:8080/rest.todo/rest/movies/{id}
     * Return the informations of a movie into <html>
     */
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("{id}")
	public String getMovie(@PathParam("id") int id) {
		Movie movie = TodoDao.instance.getMovies().get(id);

		// if the there is no movie with the id entered
		if (movie == null)
			return "<html>The movie with this id does not exist</html>";

		// the way to display the sessions
		ArrayList<Session> sessionList = new ArrayList<Session>() {
			@Override
			// TODO
			public String toString() {
				int count = 1;
				String str = "";
				for (Session session : this) {
					str += "<h3>Session " + count + "</h3>";
					str += TodoDao.instance.getCinemas().get(session.getCinema_id());
					str += "<br>Date: ";
					str += session.getDate();
					str += "<br>Hour:";
					str += session.getHour();
					str += "<br>Version:";
					str += session.getVersion();
					count++;
				}
				return str;
			}
		};

		// search for the session with the movie id
		for (Session session : TodoDao.instance.getSessions().values()) {
			if (session.getMovie_id() == movie.getId()) {
				sessionList.add(session);
			}
		}

		if (movie != null)
			return "<html>" + movie.allTheInfos() + "<h2>" + "All the sessions for this movie</h2>" + sessionList
					+ "</html>";
		else
			return "<html>The movie with this id" + movie.getId() + " doesn\'t exist</html>";
	}

	
	/*
	 * Use http://localhost:8080/rest.todo/rest/movies/dropdown
	 * Return all the movies existing into a dropdown box
	 */
	@Path("/dropdown")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getMoviesDropdown() {
		List<Movie> movies = new ArrayList<Movie>() {
			@Override
			public String toString() {
				String str = "<select name=\"movie_id\">";
				for (Movie movie : this) {
					str += "<option value=\"" + movie.getId() + "\">";
					str += movie.getName();
					str += "</option>";
				}
				str += "</select>";
				return str;
			}
		};
		movies.addAll(TodoDao.instance.getMovies().values());
		return "" + movies;
	}

	
	/*
	 * For the searching part Use http://localhost:8080/rest.todo/rest/movies/query
	 * return the list of movies containing the query "name" entered as a Param
	 */
	@GET
	@Path("/query")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String getMoviesFrom(@QueryParam("name") String name) {
		ListMovies list = new ListMovies();
		for (Movie movie : TodoDao.instance.getMovies().values()) {
			if (movie == null)
				throw new RuntimeException("Get: Movie with " + name + " not found");
			if (movie.getName().contains(name)) {
				list.add(movie);
			}
		}
		return "<html>" + list + "</html>";
	}

	
	/*
	 * Use http://localhost:8080/rest.todo/rest/movies/count 
	 * return the total number of movies
	 */
	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount() {
		int count = TodoDao.instance.getMovies().size();
		return String.valueOf(count);
	}

	
	/*
	 * Use http://localhost:8080/rest.todo/rest/movies/createMovie 
	 * Allows us the creation of a movie by getting informations from a form
	 */
	@POST
	@Path("/createMovie")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void newMovie(@FormParam("name") String name, @FormParam("rate") String rate,
			@FormParam("type") String type, @FormParam("actor") String actor, @FormParam("producer") String producer,
			@FormParam("length") String length, @FormParam("country") String country,
			@FormParam("on_screen_date") Date on_screen_date, @FormParam("language") String language,
			@FormParam("description") String description, @Context HttpServletResponse servletResponse,
			@Context HttpServletRequest request) throws IOException, ServletException {
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

		Movie movie = new Movie(name, Integer.parseInt(rate), type, actor, producer, Integer.parseInt(length), country,
				on_screen_date, language, description, "test");
		TodoDao.instance.getMovies().put(movie.getId(), movie);

		request.getRequestDispatcher("/WEB-INF/administration.html").forward(request, servletResponse);
	}

	
	/*
	 * Use http://localhost:8080/rest.todo/rest/movies/deleteMovie
	 * Delete the movie selected by the user into a form
	 */
	@POST
	@Path("/deleteMovie")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void newCinema(@FormParam("movie_id") int movieId, @Context HttpServletResponse servletResponse,
			@Context HttpServletRequest request) throws IOException, ServletException {
		System.out.println("movie_id : " + movieId);

		for (Session session : TodoDao.instance.getSessions().values()) {
			if (session == null)
				throw new RuntimeException("Get: session with " + movieId + " not found");
			if (session.getMovie_id() == (movieId)) {
			
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
			  @FormParam("on_screen_date") Date on_screen_date,
			  @FormParam("language") String language,
			  @FormParam("description") String description,
	      @Context HttpServletResponse servletResponse,
	      @Context HttpServletRequest request) throws IOException, ServletException {
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
		         
				 PreparedStatement ps=con.prepareStatement("insert into movie(name,rate,type,actor,producer,length,country,on_screen_date,language,description) values(?,?,?,?,?,?,?,?,?,?)");
				  //ps.setInt(1,4);
				  ps.setString(1,name);
				  ps.setInt(2, Integer.parseInt(rate));
				  ps.setString(3, type);
				  ps.setString(4, actor);
				  ps.setString(5, producer);
				  ps.setInt(6, Integer.parseInt(length));
				  ps.setString(7, country);
				  //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				  ps.setDate(8,on_screen_date);
				  ps.setString(9,language);
				  ps.setString(10, description);
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
				//System.out.println("Success access to Database£¡£¡");
			}
		  
	    //Movie movie = new Movie(name, Integer.parseInt(rate), type,  actor, producer, Integer.parseInt(length), country, on_screen_date, language, description, "test");
	    //TodoDao.instance.getMovies().put(movie.getId(),movie);
	    
	    // TODO when changes.
	    request.getRequestDispatcher("/WEB-INF/administration.html").forward(request, servletResponse); 
	    //servletResponse.sendRedirect("../../login.html");
	  }	  
	  
	  
	  @POST
	  @Path("/deleteMovie")
	  @Produces(MediaType.TEXT_HTML)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  public void newCinema(@FormParam("movie_id") int movieId,
	      @Context HttpServletResponse servletResponse,
	      @Context HttpServletRequest request) throws IOException, ServletException {
		  System.out.println("movie_id : " + movieId);
	   
	    
		for(Session session : TodoDao.instance.getSessions().values()) {
			if(session==null)
				throw new RuntimeException("Get: session with " + movieId +  " not found");
			if(session.getMovie_id() == (movieId) ) {
				TodoDao.instance.getMovies().remove(session);
			}
		}
		  
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
				 PreparedStatement ps=con.prepareStatement("delete from movie where id = ?");
				  ps.setInt(1,movieId);
				  ps.executeUpdate();
				  ps=con.prepareStatement("delete from session where movie_id = ?");
				  ps.setInt(1,movieId);
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
				//System.out.println("Success access to Database£¡£¡");
			}

		TodoDao.instance.getMovies().remove(movieId);

		request.getRequestDispatcher("/WEB-INF/administration.html").forward(request, servletResponse);
	}
		
		 //TodoDao.instance.getMovies().remove(movieId);
	    // TODO when changes.
	    request.getRequestDispatcher("/WEB-INF/administration.html").forward(request, servletResponse); 
	    //servletResponse.sendRedirect("../../create_session.html");
	    
	    
	  	//debug:
//			//System.out.println("city:"+city);
		
	  }
}
