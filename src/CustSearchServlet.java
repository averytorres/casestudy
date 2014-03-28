import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

public class CustSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CustSearchServlet() {
		super();

	}
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		pw.println("<html>");
		pw.println("<head><title>Hotel Choices</title>"
				+ "<meta charset=\"utf-8\">"
				+ "<title>jQuery UI Datepicker - Default functionality</title>"
				+ "<link rel=\"stylesheet\" href=\"//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css\">"
				+ "<script src=\"//code.jquery.com/jquery-1.9.1.js\"></script>"
				+ "<script src=\"//code.jquery.com/ui/1.10.4/jquery-ui.js\"></script>"
				+ "<link rel=\"stylesheet\" href=\"/resources/demos/style.css\">"
				+ "<script>"
				+ "$(function() {"
				+ "$( \".arrival\" ).datepicker({"
				+ "numberOfMonths: 1,"
				+ "onSelect: function(selected) {"
				+ "$(\".departure\").datepicker(\"option\",\"minDate\", selected)"
				+ "}"
				+ "});"
				+ "});"
				+ "</script>"
				+ "<script>"
				+ "$(function() {"
				+ "$( \".departure\" ).datepicker({"
				+ "numberOfMonths: 1,"
				+ "onSelect: function(selected) {"
				+ "$(\".arrival\").datepicker(\"option\",\"maxDate\", selected)"
				+ "}" + "}); " + "});" + "</script>" + "</head>");
		
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
				"</ul>");
		pw.println("<body>");
		try {
			
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("OracleDS");
			Connection connection = ds.getConnection();
			System.out.println("Connected");
			


			String arrival=request.getParameter("arrival");
			String departure=request.getParameter("departure");
			String roomtype=request.getParameter("room");
		
			SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yy");
			java.util.Date date = format1.parse(arrival);
			arrival=format2.format(date);
			date = format1.parse(departure);
			departure=format2.format(date);
			
			System.out.println("in search "+arrival);
			Statement stmt = connection.createStatement();
			Statement stmt2 = connection.createStatement();
			String sql;
			if (roomtype.equals("all")){
			
			sql="Select distinct room.hotelid,room.quantity,room.service,room.price,room.roomid from reservation,room where (reservation.arrival not between \'"+arrival+"\' and \'"+departure+"\' and reservation.departure not between \'"+arrival+"\' and \'"+departure+"\') and (room.quantity>0)"; 
			}
			else 
				{sql="Select distinct room.hotelid,room.quantity,room.service,room.price,room.roomid from reservation,room where (reservation.arrival not between \'"+arrival+"\' and \'"+departure+"\' and reservation.departure not between \'"+arrival+"\' and \'"+departure+"\') and (room.type ='"+roomtype+"')  and (room.quantity>0)";} 
			
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int clmn = rsmd.getColumnCount();
			
			pw.println("<body><table border=1 >");
			
			pw.println("<tr>");
			for (int i = 1; i < clmn; i++) {
				if (i==1){
					pw.print("<th> Hotel Name </th>");
					continue;
				}
				if (i==2){
					pw.print("<th> Available </th>");
					continue;
				}
				pw.print("<th>" + rsmd.getColumnName(i) + "</th>");
				
			}
			pw.print("<th> Quantity </th>");
			pw.print("<th> Book it! </th>");
			pw.println("</tr>");
			
			while (rs.next()) {
				
		ResultSet rs2 = stmt2.executeQuery("select name from hotel where hotel.hotelid = "
						+ rs.getInt(1));
		while (rs2.next()) {
			pw.println("<tr>");
			pw.println("<td>" + rs2.getString(1) + "</td>" + "<td align='center'>"
							+ rs.getInt(2) + "</td>" + "<td>"
							+ rs.getString(3) + "</td>" + "<td> $"
							+ rs.getInt(4) + ".00 </td><td align='center'>" +
			"<input type='text' name='qtyr' value='0'/></td><td>");
			
				
				pw.println("<form action='/CaseStudy/CustBookRoom'>");
				pw.println("<input type='hidden' name='hotelid' value='"+rs.getInt(1)+"'/>");
				pw.println("<input type='hidden' name='roomid' value='"+rs.getInt(5)+"'/>");
				pw.println("<input type='hidden' name='arrival' value='"+arrival+"'/>");
				pw.println("<input type='hidden' name='departure' value='"+departure+"'/>");
				pw.println("<input type='submit' value='Book it now!'/>");
				pw.println("</form>");

			pw.println("</td></tr>");
			
		}
		rs2.close();
		
			}
			pw.println("</table></body></html>");
			connection.close();

		} catch (Exception e) {

		}
	}

}