package rest.todo.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rest.todo.model.Cinema;
import rest.todo.model.Movie;
import rest.todo.model.Todo;


public enum TodoDao {
  instance;
  
	// TEEST TESTTT
	
  private Map<String, Todo> contentProvider = new HashMap<String, Todo>();
  
  private Map<String, Cinema> contentCinema = new HashMap<String, Cinema>();
  
  private ArrayList<Movie> contentMovie = new ArrayList<Movie>();
  
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
    
    Movie movie = new Movie("Le Roi Lion", 5, "animation", "leonardo", "Clint Eastwood", 120,
    		"France", "06/12/2018", "Français", "Blablabla" , "https://disney-planet.fr/wp-content/uploads/2015/01/Illustration-Le-Roi-Lion-Faux-Raccords-02.jpg");
    contentMovie.add(movie);
    
  }
  
  public Map<String, Todo> getModel(){
    return contentProvider;
  }
  
  public Map<String, Cinema> getCinemas(){
	return contentCinema;
  }
  
  public ArrayList<Movie> getMovies(){
	return contentMovie;
  }
  
} 