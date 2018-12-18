package rest.todo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rest.todo.utils.*;
import rest.todo.model.Cinema;
import rest.todo.model.Session;
import rest.todo.model.Movie;
import rest.todo.model.User;

public enum TodoDao {
	instance;

	private Map<Integer, Cinema> contentCinema = new HashMap<Integer, Cinema>();

	private Map<Integer, Session> contentSession = new HashMap<Integer, Session>();

	private Map<Integer, Movie> contentMovie = new HashMap<Integer, Movie>();

	private ArrayList<User> userList = new ArrayList<User>();

	private TodoDao() {

		/*
		 * Cinema cinema = new Cinema("test", "Paris"); Cinema cinema2 = new
		 * Cinema("test2", "Paris"); Cinema cinema3 = new Cinema("test3", "Lyon");
		 * 
		 * 
		 * contentCinema.put(cinema.getId(), cinema); contentCinema.put(cinema2.getId(),
		 * cinema2); contentCinema.put(cinema3.getId(), cinema3);
		 */

		/*
		 * Session session = new Session(0,0,"10:00", "2018-12-20", "vf");
		 * contentSession.put(session.getId(),session);
		 */

		/*
		 * Movie movie = new Movie("Le Roi Lion", 5, "animation", "leonardo",
		 * "Clint Eastwood", 120, "France", "2018-12-06", "Français", "Blablabla" ,
		 * "https://disney-planet.fr/wp-content/uploads/2015/01/Illustration-Le-Roi-Lion-Faux-Raccords-02.jpg"
		 * ); contentMovie.put(movie.getId(),movie);
		 */

		User user = new User("admin", "admin");
		userList.add(user);
	}

	public Map<Integer, Cinema> getCinemas() {

		// collect data from Database
		Connection con;
		String driver = Constants.driver;
		String url = Constants.url;
		String user = Constants.user;
		String password = Constants.password;

		// use the data from database
		try {
			// Load driver
			Class.forName(driver);
			// Connect to the MySQL database! !
			con = DriverManager.getConnection(url, user, password);

			// create statement object to execute SQL script
			Statement statement = con.createStatement();
			String sql = "select * from cinema";
			ResultSet rs = statement.executeQuery(sql); // ResultSet Class used to store the data you get from database
			contentCinema.clear();
			while (rs.next()) {
				Cinema cn = new Cinema();
				cn.setId(rs.getInt("id"));
				cn.setName(rs.getString("name"));
				cn.setCity(rs.getString("address"));
				contentCinema.put(cn.getId(), cn);
			}
			rs.close();
			con.close();

			// driver Exception & connection Exception
		} catch (ClassNotFoundException e) {
			System.out.println("Sorry,can`t find the Driver!");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// System.out.println("Success access to Database£¡£¡");
		}
		return contentCinema;
	}

	public Map<Integer, Session> getSessions() {
		// collect data from Database
		Connection con;
		String driver = Constants.driver;
		String url = Constants.url;
		String user = Constants.user;
		String password = Constants.password;

		// use the data from database
		try {
			// Load driver
			Class.forName(driver);
			// Connect to the MySQL database! !
			con = DriverManager.getConnection(url, user, password);

			// create statement object to execute SQL script
			Statement statement = con.createStatement();
			String sql = "select * from session";
			ResultSet rs = statement.executeQuery(sql); // ResultSet Class used to store the data you get from database
			contentSession.clear();
			while (rs.next()) {
				Session ss = new Session();
				ss.setId(rs.getInt("id"));
				ss.setMovie_id(rs.getInt("movie_id"));
				ss.setCinema_id(rs.getInt("cinema_id"));
				ss.setHour(rs.getTime("hour"));
				ss.setDate(rs.getDate("date"));
				ss.setVersion(rs.getString("version"));
				// cinemaList.add(cn);
				contentSession.put(ss.getId(), ss);
			}
			rs.close();
			con.close();

			// driver Exception & connection Exception
		} catch (ClassNotFoundException e) {
			System.out.println("Sorry,can`t find the Driver!");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// System.out.println("Success access to Database£¡£¡");
		}

		return contentSession;
	}

	public Map<Integer, Movie> getMovies() {

		// collect data from Database
		Connection con;
		String driver = Constants.driver;
		String url = Constants.url;
		String user = Constants.user;
		String password = Constants.password;

		// use the data from database
		try {
			// Load driver
			Class.forName(driver);
			// Connect to the MySQL database! !
			con = DriverManager.getConnection(url, user, password);

			// create statement object to execute SQL script
			Statement statement = con.createStatement();
			String sql = "select * from movie";
			ResultSet rs = statement.executeQuery(sql); // ResultSet Class used to store the data you get from database
			contentCinema.clear();
			while (rs.next()) {
				Movie mv = new Movie();
				mv.setId(rs.getInt("id"));
				mv.setName(rs.getString("name"));
				mv.setRate(rs.getInt("rate"));
				mv.setType(rs.getString("type"));
				mv.setActors(rs.getString("actor"));
				mv.setProducers(rs.getString("producer"));
				mv.setLength(rs.getInt("length"));
				mv.setCountry(rs.getString("country"));
				mv.setOnScreenDate(rs.getDate("on_screen_date"));
				mv.setLanguage(rs.getString("language"));
				mv.setShortDescription(rs.getString("description"));
				mv.setImage(rs.getString("image"));
				contentMovie.put(mv.getId(), mv);
			}
			rs.close();
			con.close();

			// driver Exception & connection Exception
		} catch (ClassNotFoundException e) {
			System.out.println("Sorry,can`t find the Driver!");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// System.out.println("Success access to Database£¡£¡");
		}
		return contentMovie;
	}

	public ArrayList<User> getUsers() {
		return userList;
	}

}