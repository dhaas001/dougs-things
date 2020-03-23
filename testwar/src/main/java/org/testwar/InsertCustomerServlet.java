package org.testwar;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
public class InsertCustomerServlet extends HttpServlet{
 
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        System.out.println("----- InsertCustomerServlet -----");
        try {
        // Get the customer value submitted from Customer.jsp page through HttpServletRequest object
            String name=request.getParameter("name");
            String address=request.getParameter("address");
            String mobile=request.getParameter("mobile");
            String emailid=request.getParameter("emailid");
             
            //Set the Customer values into Customer Bean or POJO(Plain Old Java Object) class
            Customer customer=new Customer();
            customer.setName(name);
            customer.setAddress(address);
            customer.setMobile(Long.valueOf(mobile));
            customer.setEmailid(emailid);
             
            RequestDispatcher dispatcher=request.getRequestDispatcher("/jsp/Welcome.jsp");
            //Set the customer instance into request.Then only the customer object 
            //will be available in the Welcome.jsp page
            request.setAttribute("cust",customer);
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
         
    }
 
}
