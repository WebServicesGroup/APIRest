<!--<%@page import="java.sql.*" %> -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
   String username=request.getParameter("username");
   String password=request.getParameter("pwd");
   if ("admin".equals(username) && "admin".equals(password))
   {
	   session.setAttribute("loginUser",username);
	   response.sendRedirect("http://localhost:8080/rest.todo/createMovie.html");
   }else{
	   //response.sendRedirect("");
	   request.setAttribute("error","Wrong! Try again!");
	   request.getRequestDispatcher("Homepage.jsp").forward(request,response);
   }
%>

