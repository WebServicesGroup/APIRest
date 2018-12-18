package rest.todo.model;

import java.util.ArrayList;
import rest.todo.model.Movie;

public class ListMovies extends ArrayList<Movie> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		String str = "";
		for (Movie movie : this) {
			if (movie != null) {
				str += movie;
				str += "<br>";
			}
		}
		return str;
	}
}
