

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;


import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


public class CalenderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public CalenderServlet() {
        super();
              
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			PrintWriter pw = response.getWriter();
			

		pw.println("<html>");
		pw.println("<head><title>Choose Your Date Range</title>"
				+ "<meta charset=\"utf-8\">"
				+ "<title>jQuery UI Datepicker - Default functionality</title>"
				+ "<link rel=\"stylesheet\" href=\"//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css\">"
				+ "<script src=\"//code.jquery.com/jquery-1.9.1.js\"></script>"
				+ "<script src=\"//code.jquery.com/ui/1.10.4/jquery-ui.js\"></script>"
				+ "<link rel=\"stylesheet\" href=\"/resources/demos/style.css\">"
				+ "<script>"
				+ "$(function() {"
				+ "$( \".arrival\" ).datepicker({"
				+"alignment:'top',numberOfMonths: 1,"
				+ "minDate: 0,"
				+ "onSelect: function(selected) {"
				+ "$(\".departure\").datepicker(\"option\",\"minDate\", selected)"
				+ "}"
				+ "});"
				+ "});"
				+ "</script>"
				+ "<script>"
				+ "$(function() {"
				+ "$( \".departure\" ).datepicker({"
				+"alignment:'top',numberOfMonths: 1,"
				+ "onSelect: function(selected) {"
				+ "$(\".arrival\").datepicker(\"option\",\"maxDate\", selected)"
				+ "}" + "}); " + "});" + "</script>");
		
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
				"</ul></head>");
		pw.println("<body>");
		pw.println("<h3>Reservations</h3></br><img src=\"reservation.gif\" alt=\"some_text\">");
		pw.print("<form action=\"/CaseStudy/CustSearchServlet\">");
		
		pw.print("Arrival Date: <input type=\"text\" class=arrival id=\""
				 + "arrival\" name=\""  + "arrival\" ></br>");
		pw.print("Departure Date: <input type=\"text\" class=departure id=\""
				+  "departure\" name=\""  + "departure\" ></br>");
		pw.println("Room type <select name='room'>"+
				"<option value='all'>select type</option>"+
				"<option value='Heritage Single'>Heritage Single</option>"+
				"<option value='Heritage Double'>Heritage Double</option>"+
				"<option value='Heritage Triple'>Heritage Triple</option>"+
				"<option value='Delux Double'>Delux Double</option>"+
				"<option value='Delux Suite'>Delux Suite</option></select></br>");

		
		pw.print("<input type=\"submit\"value=Search></form></td></br>");
		
		
		}
		catch (Exception e){
			
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
