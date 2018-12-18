package rest.todo.model;

import java.util.ArrayList;

public class ListCinemas extends ArrayList<Cinema> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		String str = "";
		for (Cinema cinema : this) {
			str += "<a href=\"http://localhost:8080/rest.todo/rest/cinemas/";
			str += cinema.getId();
			str += "\">";
			str += cinema;
			str += "</a><br>";
		}
		return str;
	}
}
