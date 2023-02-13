
import java.io.IOException;
import java.text.ParseException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;


@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	
    private final static String query = "insert into emp(name,address,salary,dob,gender) values(?,?,?,?,?)";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //get PrintWriter
        PrintWriter pw = res.getWriter();
        //set content type
        
        res.setContentType("text/html");
        //link the bootstrap
        pw.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");
        //get the values
        String name = req.getParameter("empName");
        String address = req.getParameter("address");
        double salary =Double.parseDouble(req.getParameter("salary"));
        
        String dob = req.getParameter("dob");
        
        
        
		
        byte gender = Byte.parseByte(req.getParameter("gender"));
        //load the JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(Exception e) {
            e.printStackTrace();
        }
        
        //generate the connection
        try(Connection con = DriverManager.getConnection("jdbc:mysql:///empmgmt","root","ritesh");
                PreparedStatement ps = con.prepareStatement(query);){
            //set the values
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setDouble(3, salary);
            ps.setDate(4,java.sql.Date.valueOf(dob));
          
           
            ps.setByte(5, gender);
            
            //execute the query
            int count = ps.executeUpdate();
            pw.println("<div class='card' style='margin:auto;width:300px;margin-top:100px'>");
            if(count==1) {
                pw.println("<h2 class='bg-danger text-light text-center'>Record Registered Successfully</h2>");
            }else {
                pw.println("<h2 class='bg-danger text-light text-center'>Record Not Registered</h2>");
            }
        }catch(SQLException se) {
            pw.println("<h2 class='bg-danger text-light text-center'>"+se.getMessage()+"</h2>");
            se.printStackTrace();
        }catch(Exception e) {
            e.printStackTrace();
        }
        pw.println("<a href='http://localhost:8080/employmanagement/'><button class='btn btn-outline-success'>Home</button></a>");
        pw.println("<a href='showdata'><button class='btn btn-outline-success'>ShowEmp</button></a>");
        pw.println("</div>");
      
        pw.close();
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req,res);
    }
}
