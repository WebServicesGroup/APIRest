package rest.todo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
  
  private Map<String, Cinema> contentCinema = new HashMap<String, Cinema>();
  
  private ArrayList<Session> contentSession = new ArrayList<Session>();
  
  
  
  // TODO CHANGER LES HASHMAP EN ARRAYLIST
  private ArrayList<Movie> contentMovie = new ArrayList<Movie>();
  
  
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
    
    contentCinema.put("1", cinema);
    contentCinema.put("2", cinema2);
    contentCinema.put("3", cinema3);
    
 
    
    contentSession.add(session);
    
    User user = new User("admin","admin");
    userList.add(user);    
  }
  
  public Map<String, Todo> getModel(){
    return contentProvider;
  }
  
  public Map<String, Cinema> getCinemas(){
	return contentCinema;
  }
  
  public ArrayList<Session> getSessions(){
	  return contentSession;
  }
  
  public ArrayList<Movie> getMovies(){
	  return contentMovie;
  }
  
  public ArrayList<User> getUsers(){
	  return userList;
  }
	  
} 