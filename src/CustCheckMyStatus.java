import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


public class CustCheckMyStatus extends HttpServlet {
 private static final long serialVersionUID = 1L;


 public CustCheckMyStatus() {
  super();
  
 }


 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  // TODO Auto-generated method stub
  try {
   PrintWriter pw = response.getWriter();
   pw.print("<style>"+
     "ul"+
     "{"+
     "list-style-type:none;"+
     "margin:0;"+
     "padding:0;"+
     "overflow:hidden;"+
     "}"+
     "li"+
     "{"+
     "float:right;"+
     "}"+
     "a"+
     "{"+
     "display:block;"+
     "width:60px;"+
     "background-color:#dddddd;"+
     "}"+
     "</style>"+
     "<ul>"+
     "<li><a href=\"/CaseStudy/HomePage.html\">Home</a></li>"+
     "<li><a href=\"/CaseStudy/CustLogin\">Status</a></li>"+
     "<li><a href=\"/CaseStudy/CalenderServlet\">Search</a></li>"+
     "</ul>" +
     "</br>");
   InitialContext ctx = new InitialContext();
   DataSource ds = (DataSource) ctx.lookup("OracleDS");
   Connection con = ds.getConnection();
   // System.out.println("data source");

   // reading parameters from the website
   ctx = new InitialContext();

    Statement stmt1 = con.createStatement();
    Statement stmt2 = con.createStatement();
    Statement stmt3 = con.createStatement();
    
    int custno = Integer.parseInt(request.getParameter("custno"));

    String sql1 = "Select *  from reservation where custno = " + custno;
    ResultSet rs1 = stmt1.executeQuery(sql1);
    ResultSetMetaData rsmd = rs1.getMetaData();
    int clmn = rsmd.getColumnCount();
    // System.out.println(getString );

    pw.println("<html><body><table border=1>");
    pw.println("<tr>");
    for (int i = 1; i <= clmn - 1; i++) {
     pw.print("<th>" + rsmd.getColumnName(i) + "</th>");
    }
    pw.print("<th> Status </th>");
    pw.print("<th> Client Confirmation </th>");
    pw.println("</tr>");
    
    int count = 1;
    
    while (rs1.next()) {

     ResultSet rs2 = stmt2
       .executeQuery("select firstname,surname from customer where customer.custno = "
         + rs1.getInt(2));
     ResultSet rs3 = stmt3
       .executeQuery("select name from hotel where hotel.hotelid = "
         + rs1.getInt(4));
     while (rs2.next() && rs3.next()) {
      pw.println("<tr>");
      pw.println("<td>" + rs1.getInt(1) + "</td>" + "<td>"
        + rs2.getString(1) + " " + rs2.getString(2)
        + "</td>" + "<td>" + rs1.getInt(3) + "</td>"
        + "<td>" + rs3.getString(1) + "</td>" + "<td>"
        + rs1.getDate(5) + "</td>"
        + "<td>" + rs1.getDate(6)
        + "</td>"); 
        if(rs1.getString(7).equalsIgnoreCase("a"))
        		{
        			pw.println("<td> Approved! </td>");
        			pw.println("<form action='/CaseStudy/CustCommitServlet'>");
        	    	pw.println("<input type='hidden' name='resno' value='" + rs1.getInt(1) + "'/>");
        	    	pw.println("<input type='hidden' name='custno' value='" + custno + "'/>");
        	    	pw.println("<input type='hidden' name='count' value='" + count + "'/>");
        	    	pw.println("<td>" + "<input type='submit' value='book'>");
        	    	
        	    	pw.append("</form>");
        	    	pw.println("<form action='/CaseStudy/CustCancelServlet'>");
        	    	pw.println("<input type='hidden' name='resno' value='" + rs1.getInt(1) + "'/>");
        	    	pw.println("<input type='hidden' name='custno' value='" + custno + "'/>");
        	    	pw.println("<input type='submit' value='cancel'>" + "</td>");
        	    	pw.append("</form>");
        	    	
        		}
        else if(rs1.getString(7).equalsIgnoreCase("p"))
        {
        	pw.println("<td> Pending! </td>");
        }
        else if(rs1.getString(7).equalsIgnoreCase("b"))
        {
        	
        	Date arrival =rs1.getDate(5);
        	arrival.setDate(arrival.getDate()-1);
        	Calendar cal=new GregorianCalendar();
        	cal.add(Calendar.DATE, 1);
        	Date d=(Date) cal.getTime();
        	if (d.after(arrival)){
        		pw.println("<td> Can no longer Cancel </td>");
        	}
        	else{pw.println("<td> Booked! </td>");
        	pw.println("<form action='/CaseStudy/CustCancelServlet'>");
        	pw.println("<input type='hidden' name='resno' value='" + rs1.getInt(1) + "'/>");
	    	pw.println("<input type='hidden' name='custno' value='" + custno + "'/>");
	    	pw.println("<td>" + "<input type='submit' value='Cancel'></td>");
	    	pw.append("</form>");}
        }
        else if(rs1.getString(7).equalsIgnoreCase("c"))
        {
        	pw.println("<td> Canceled! </td>");
        }
        else
        {
        	pw.println("<td> Denied! </td>");
        }

      pw.println("</tr>");
      	count++;
     }
     rs2.close();
     rs3.close();
    }
    pw.println("</table></body></html>");
    rs1.close();
    stmt1.close();
    stmt2.close();
    stmt3.close();
    con.close();
   }
  
  catch(Exception e)
  {

  }
 }


}