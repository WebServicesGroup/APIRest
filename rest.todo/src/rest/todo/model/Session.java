package rest.todo.model;

public class Session {
	private static int count = 0;
	private int id;
	private int movie_id;
	private int cinema_id;
	private String hour;
	private String date;
	private String version;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMovie_id() {
		return movie_id;
	}
	public void setMovie_id(int movie_id) {
		this.movie_id = movie_id;
	}
	public int getCinema_id() {
		return cinema_id;
	}
	public void setCinema_id(int cinema_id) {
		this.cinema_id = cinema_id;
	}
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Session( int movie_id, int cinema_id, String hour, String date, String version) {
		super();
		this.id = count;
		this.movie_id = movie_id;
		this.cinema_id = cinema_id;
		this.hour = hour;
		this.date = date;
		this.version = version;
		
		count++;
	}
	
	public Session() {}
	
	@Override
	public String toString() {
		return "<p>Session, hour=" + hour
				+ ", date=" + date + ", version=" + version + "</p>";
	}
	
	
	
	
	
	
	
	
}
