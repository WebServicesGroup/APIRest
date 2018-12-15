package rest.todo.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;



import rest.todo.dao.TodoDao;
import rest.todo.model.Cinema;
import rest.todo.model.Todo;


public class CinemaResource {
  @Context
  UriInfo uriInfo;
  @Context
  Request request;
  String city;
  public CinemaResource(UriInfo uriInfo, Request request, String city) {
    this.uriInfo = uriInfo;
    this.request = request;
    this.city = city;
  }
  
//For the browser
  
 @GET
 @Produces(MediaType.TEXT_HTML)
 public Cinema getCinemaHTML() {
   Cinema cinema = TodoDao.instance.getCinemas().get(city);
   if(cinema==null)
     throw new RuntimeException("Get: Cinema with " + city +  " not found");
   return cinema;
 }
 

  

} 