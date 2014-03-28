import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class VOCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public VOCheckServlet() {
		super();

	}

	public void book(int i) {

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("OracleDS");
			Connection con = ds.getConnection();
			con.commit();
			

			// reading parameters from the website
			String name = request.getParameter("uName");
			String pwd = request.getParameter("pwd");
			ctx = new InitialContext();

			PrintWriter pw = response.getWriter();

			boolean p = false;
			if (name.equals("vo") && pwd.equals("vo")) {
				p = true;

				Statement stmt1 = con.createStatement();
				Statement stmt2 = con.createStatement();
				Statement stmt3 = con.createStatement();

				String sql1 = "Select * from reservation";
				ResultSet rs1 = stmt1.executeQuery(sql1);
				ResultSetMetaData rsmd = rs1.getMetaData();
				int clmn = rsmd.getColumnCount();
				

				pw.println("<html><head>");
				pw.println("<head>");
				pw.println("<script>");
				pw.append("function flow() {var x=document.getElementById('resno').value;" +
						 "if (document.getElementById('reject').checked) " +
						"{ alert('Reservation rejected!'); " +
						"window.location.replace('/CaseStudy/VORejectServlet?resno='+x);   } " +
						
						"else if (document.getElementById('approve').checked) " +
						"{ alert('Reservation approved'); " +
						"window.location.replace('/CaseStudy/VOApproveServlet?resno='+x);}" +
						
						" else { alert('Nothing happening!');} }");
				pw.append("</script>");
				pw.println("</head>");		
				pw.println("<body><table border=1 >");
				
				pw.println("<tr>");
				for (int i = 1; i < clmn; i++) {
					if (i == 2) {
						pw.print("<th>" + rsmd.getColumnName(i));
						pw.append("  (Custno) </th>");
						continue;
					}
					pw.print("<th>" + rsmd.getColumnName(i) + "</th>");
				}
				pw.print("<th> Status </th>");
				pw.println("</tr>");
				int j = 0;
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
								+ "  (" + rs1.getInt(2) + ")</td>" + "<td>"
								+ rs1.getInt(3) + "</td>" + "<td>"
								+ rs3.getString(1) + "</td>" + "<td>"
								+ rs1.getDate(5) + "</td>" + "<td>"
								+ rs1.getDate(6) + "</td>");
						if (rs1.getString(7).equalsIgnoreCase("P")) {
							
							pw.println("<form name='decision'>");
			
							pw.println("<input type='hidden' id='resno' name='resno' value='" + rs1.getInt(1) + "'/>");
							
							pw.println("<td> Approve <input type='radio' name='vodec' id='approve'  onClick='flow()'>");
							pw.println("	  Reject <input type='radio' name='vodec' id='reject'   onClick='flow()'></td>");
							pw.println("</form>");

						} else if (rs1.getString(7).equalsIgnoreCase("b"))
							pw.println("<td> Booked! </td>");

						else if (rs1.getString(7).equalsIgnoreCase("a"))
							pw.println("<td> Approved! </td>");

						else if (rs1.getString(7).equalsIgnoreCase("c"))
							pw.println("<td> Canceled! </td>");

						else
							pw.println("<td> Denied! </td>");

						pw.println("</tr>");
						j++;

					}
					rs2.close();
					rs3.close();
				}
				rs1.close();
				pw.println("</html></body></table>");
				stmt1.close();
				stmt2.close();
				stmt3.close();
				con.close();
			}
			if (!p) {
				response.sendRedirect("/CaseStudy/VOLogin.html");
				pw.println("Wrong details, please login again");
				
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
