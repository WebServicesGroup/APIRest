package rest.todo.dao;

import java.awt.List;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rest.todo.model.Cinema;
import rest.todo.model.Session;
import rest.todo.model.Movie;
import rest.todo.model.Todo;
import rest.todo.model.User;


public enum TodoDao {
  instance;
  
	// TEEST TESTTT
	
  private Map<String, Todo> contentProvider = new HashMap<String, Todo>();
  
  private Map<Integer, Cinema> contentCinema = new HashMap<Integer, Cinema>();
  
  private Map<Integer,Session> contentSession = new HashMap<Integer,Session>();
  
  
  
  // TODO CHANGER LES HASHMAP EN ARRAYLIST
  private Map<Integer, Movie> contentMovie = new HashMap<Integer,Movie>();
  
  
  private ArrayList<User> userList = new ArrayList<User>();
  
  // TODO CHANGER LES HASHMAP EN ARRAYLIST
  private TodoDao() {
    
    Todo todo = new Todo("1", "Learn REST");
    todo.setDescription("Read http://rest.elkstein.org/");
    contentProvider.put("1", todo);
    todo = new Todo("2", "Do something");
    todo.setDescription("Read complete http://www.efrei.fr");
    contentProvider.put("2", todo);
    
    Cinema cinema = new Cinema("test", "Paris");
    Cinema cinema2 = new Cinema("test2", "Paris");
    Cinema cinema3 = new Cinema("test3", "Lyon");
    
    contentCinema.put(cinema.getId(), cinema);
    contentCinema.put(cinema2.getId(), cinema2);
    contentCinema.put(cinema3.getId(), cinema3);
    
    Session session = new Session(0,0,"10:20", "jeudi", "vf");
    contentSession.put(session.getId(),session);
    
    Movie movie = new Movie("Le Roi Lion", 5, "animation", "leonardo", "Clint Eastwood", 120,
    		"France", "06/12/2018", "Français", "Blablabla" , "https://disney-planet.fr/wp-content/uploads/2015/01/Illustration-Le-Roi-Lion-Faux-Raccords-02.jpg");
    contentMovie.put(movie.getId(),movie);
    
    
    
    User user = new User("admin","admin");
    userList.add(user);    
  }
  
  public Map<String, Todo> getModel(){
    return contentProvider;
  }
  
  public Map<Integer, Cinema> getCinemas(){
	return contentCinema;
  }
  
  public Map<Integer,Session> getSessions(){
	  return contentSession;
  }
  
  public Map<Integer,Movie> getMovies(){
	  return contentMovie;
  }
  
  public ArrayList<User> getUsers(){
	  return userList;
  }
	  
} 