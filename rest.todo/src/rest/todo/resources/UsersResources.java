package rest.todo.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;



import rest.todo.dao.TodoDao;
import rest.todo.model.Cinema;
import rest.todo.model.Todo;
import rest.todo.model.User;

//Will map the resource to the URL todos
@Path("/users")
public class UsersResources {
  @Context
  UriInfo uriInfo;
  @Context
  Request request;

  @Path("createCinema")
  @POST
  @Produces(MediaType.TEXT_HTML)
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public void redirectToCreateCinema(@FormParam("connected") String connected,
      @Context HttpServletResponse servletResponse,
      @Context HttpServletRequest request) throws IOException, ServletException {
	  System.out.println("connected : " + connected);
    
    if(connected.equals("true")) {
	    // TODO when changes.
    	request.getRequestDispatcher("/WEB-INF/create_cinema.html").forward(request, servletResponse); 
	    //servletResponse.sendRedirect("../WEB-INF/administration.html");
    }
    else {
    	servletResponse.sendRedirect("../login.html");
    }
  }
  
  @Path("createMovie")
  @POST
  @Produces(MediaType.TEXT_HTML)
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public void redirectToCreateMovie(@FormParam("connected") String connected,
      @Context HttpServletResponse servletResponse,
      @Context HttpServletRequest request) throws IOException, ServletException {
	  System.out.println("connected : " + connected);
    
    if(connected.equals("true")) {
	    // TODO when changes.
    	request.getRequestDispatcher("/WEB-INF/create_movie.html").forward(request, servletResponse); 
	    //servletResponse.sendRedirect("../WEB-INF/administration.html");
    }
    else {
    	servletResponse.sendRedirect("../login.html");
    }
  }
  
  @Path("deleteMovie")
  @POST
  @Produces(MediaType.TEXT_HTML)
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public void redirectToDeleteMovie(@FormParam("connected") String connected,
      @Context HttpServletResponse servletResponse,
      @Context HttpServletRequest request) throws IOException, ServletException {
	  System.out.println("connected : " + connected);
    
    if(connected.equals("true")) {
	    // TODO when changes.
    	request.getRequestDispatcher("/WEB-INF/delete_movie.html").forward(request, servletResponse); 
	    //servletResponse.sendRedirect("../WEB-INF/administration.html");
    }
    else {
    	servletResponse.sendRedirect("../login.html");
    }
  }
  
  @Path("createSession")
  @POST
  @Produces(MediaType.TEXT_HTML)
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public void redirectToCreateSession(@FormParam("connected") String connected,
      @Context HttpServletResponse servletResponse,
      @Context HttpServletRequest request) throws IOException, ServletException {
	  System.out.println("connected : " + connected);
    
    if(connected.equals("true")) {
	    // TODO when changes.
    	request.getRequestDispatcher("/WEB-INF/create_session.html").forward(request, servletResponse); 
	    //servletResponse.sendRedirect("../WEB-INF/administration.html");
    }
    else {
    	servletResponse.sendRedirect("../login.html");
    }
  }


  @POST
  @Produces(MediaType.TEXT_HTML)
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public void login(@FormParam("login") String login,
		  @FormParam("password") String password,
      @Context HttpServletResponse servletResponse,
      @Context HttpServletRequest request) throws IOException, ServletException {
	  System.out.println("login : " + login);
	  System.out.println("password : " + password);
    
	boolean connected = false;
    for(User user : TodoDao.instance.getUsers()) {
    	if(user.getLogin().equals(login) && user.getPassword().equals(password))
    		connected = true;
    }
    
    if(connected) {
	    // TODO when changes.
    	request.getRequestDispatcher("/WEB-INF/administration.html").forward(request, servletResponse); 
	    //servletResponse.sendRedirect("../WEB-INF/administration.html");
    }
    else {
    	servletResponse.sendRedirect("../login.html");
    }
  }
 

  

} 