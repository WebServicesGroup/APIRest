package rest.todo.model;
import java.util.ArrayList;

import rest.todo.model.Movie;

public class ListMovies extends ArrayList<Movie>{

	
	@Override
	public String toString() {
		String str = "";
		for(Movie movie : this ) {
			if (movie != null) {
			str+="<a href=\"http://localhost:8080/rest.todo/rest/movies/";
			str+=movie.getId();
			str+="\">";
			str+=movie;
			str+="</a><br>";
			}
		}
		return str;
	}
}
