import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CustLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CustLogin() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			PrintWriter pw = response.getWriter();

			pw.println("<html><title>Reservation Login</title>" + "<head>" + ""
					+ "<style>" + "ul" + "{" + "list-style-type:none;"
					+ "margin:0;" + "padding:0;" + "overflow:hidden;" + "}"
					+ "li" + "{" + "float:right;" + "}" + "a" + "{"
					+ "display:block;" + "width:60px;"
					+ "background-color:#dddddd;" + "}" + "</style>" + "<ul>"
					+ "<li><a href=\"/CaseStudy/HomePage.html\">Home</a></li>"
					+ "<li><a href=\"/CaseStudy/CustLogin\">Status</a></li>"
					+ "<li><a href=\"/CaseStudy/CalenderServlet\">Search</a></li>"
					+ "</ul>" + "" + "</head>" + "<body>");
			pw.print("<form action=\"/CaseStudy/CustCheckMyStatus\">");
			pw.print("</br>Enter Customer ID to edit/review your past submission!</br></br>Customer ID:<input type=\"text\"  name='custno'>");
			pw.print("</br><input type=\"submit\"  value=Submit></form>");
			pw.println("</body></html>");
		} catch (Exception e) {

		}
	}

}
