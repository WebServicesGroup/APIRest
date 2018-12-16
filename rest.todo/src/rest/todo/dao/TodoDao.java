package rest.todo.dao;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rest.todo.model.Cinema;
import rest.todo.model.Session;
import rest.todo.model.Todo;


public enum TodoDao {
  instance;
  
	// TEEST TESTTT
	
	
  private Map<String, Todo> contentProvider = new HashMap<String, Todo>();
  
	//TODO: change the MAP to List
  private Map<String, Cinema> contentCinema = new HashMap<String, Cinema>();
  
  private ArrayList<Session> contentSession = new ArrayList<Session>();
  
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
    
    contentCinema.put("0", cinema);
    contentCinema.put("1", cinema2);
    contentCinema.put("2", cinema3);
    
    Session session = new Session(1,1,"10:20", "jeudi", "vf");
    
    contentSession.add(session);
    
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
	  
} 