<%@page import="org.testwar.Customer"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Customer Details</title>
</head>
<body>
<%
    Customer customer=(Customer)request.getAttribute("cust");
%>
<table align="center" bgcolor="#FFFFCC" border="1" width="70%">
    <tr>
        <td colspan="2" align="center"><%="Welcome "+customer.getName()+" !!!!. Your details Processed." %></td>
    </tr>
    <tr>
        <td>Name </td>
        <td><%=customer.getName()%></td>
    </tr>
    <tr>
        <td>Address </td>
        <td><%=customer.getAddress() %></td>
    </tr>
    <tr>
        <td>Mobile </td>
        <td><%=String.valueOf(customer.getMobile()) %></td>
    </tr>
    <tr>
        <td>EmailId </td>
        <td><%=customer.getEmailid() %></td>
    </tr> 
</table>
</body>
</html>