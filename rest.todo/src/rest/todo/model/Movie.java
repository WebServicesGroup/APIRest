package rest.todo.model;

import java.sql.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Movie {
	private static int count = 0;
	private int id;
	private String name;
	private int rate;
	private String type;
	private String actors;
	private String producers;
	private int length;
	private String country;
	private Date onScreenDate;
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

	public Date getOnScreenDate() {
		return onScreenDate;
	}

	public void setOnScreenDate(Date onScreenDate) {
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

	public Movie(String name, int rate, String type, String actors, String producers, int length, String country,
			Date onScreenDate, String language, String shortDescription, String image) {
		super();
		this.id = count;
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
		count++;
	}

	public Movie() {
	}

	@Override
	public String toString() {
		//TODO 
		/*
		str = "<div id=\"bandeau\"><a href=\\\"http://localhost:8080/rest.todo/rest/movies/"+ movie.getId() +"\">" + movie + "</a></div>";
		str += "<div id=\"menu\">testtest</div>";
		str += "<div id=\"contenu\">Ceci est le contenu</div>";
		*/
		return "Movie " + name + ", rate=" + rate + ", type=" + type;
	}

	public String allTheInfos() {
		
		return "<h1>Movie</h1>" + name + "<br>Rate " + rate + "<br>Type " + type + "<br>Actors " + actors
				+ "<br>Producers " + producers + "<br>Length " + length + "<br> Country " + country
				+ "<br>On screen date " + onScreenDate + "<br>Language " + language + "<br>Short description:<br>"
				+ shortDescription + "<br>Image " + image;
	}

}
