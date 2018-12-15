package rest.todo.model;

public class Movie {
	private int id;
	private String name;
	private int rate;
	private String type;
	private String actors;
	private String producers;
	private int length;
	private String country;
	private String onScreenDate;
	private String language;
	private String shortDescription;
	private String image;
	
	
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getActors() {
		return actors;
	}
	public void setActors(String actors) {
		this.actors = actors;
	}
	
	
	
	
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getProducers() {
		return producers;
	}
	public void setProducers(String producers) {
		this.producers = producers;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getOnScreenDate() {
		return onScreenDate;
	}
	public void setOnScreenDate(String onScreenDate) {
		this.onScreenDate = onScreenDate;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	
	
	public Movie(int id, String name, int rate, String type, String actors, String producers, int length,
			String country, String onScreenDate, String language, String shortDescription, String image) {
		super();
		this.id = id;
		this.name = name;
		this.rate = rate;
		this.type = type;
		this.actors = actors;
		this.producers = producers;
		this.length = length;
		this.country = country;
		this.onScreenDate = onScreenDate;
		this.language = language;
		this.shortDescription = shortDescription;
		this.image = image;
	}
	public Movie() {}
	@Override
	public String toString() {
		return "Movie [id=" + id + ", name=" + name + ", rate=" + rate + ", type=" + type + ", actors=" + actors
				+ ", producers=" + producers + ", length=" + length + ", country=" + country + ", onScreenDate="
				+ onScreenDate + ", language=" + language + ", shortDescription=" + shortDescription + ", image="
				+ image + "]";
	}
	
	

}
