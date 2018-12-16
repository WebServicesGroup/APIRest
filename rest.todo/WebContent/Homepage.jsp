<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home page</title>
</head>
<body>
  
  Welcome to XXXXXXXX!!!<br>
  <form method="get" action="../rest.todo/searchCity.html">
    <button type="submit">Search by City</button>
 </form>
 or
 <form method="get" action="">
    <button type="submit">Search by Movie Name</button>
 </form>
  <pre>
  Here is the latest movies,Click it to see the details!
  <!-- Show 4 latest movies -->
  
  
  
  
  
  </pre>
  Login as producer?
  <form action="verifyProducer.jsp" method="post">
  Username <input name="username"/>
  Password <input type="password" name="pwd">
  <input type="submit" value="login"/>
 </form>
  
</body>
</html>