package rest.todo.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Cinema {
	private static int count = 0;
	private int id;
	private String name;
	private String city;
	
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
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public Cinema(String name, String city) {
		super();
		this.id = count;
		this.name = name;
		this.city = city;
		count++;
	}
	
	public Cinema() {}
	@Override
	public String toString() {
		return "<p>" + name + " in" + city + "</p>";
	}
	
	
	
	
}
