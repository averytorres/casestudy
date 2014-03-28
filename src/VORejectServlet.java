

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


public class VORejectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public VORejectServlet() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("OracleDS");
			Connection con = ds.getConnection();
			con.commit();
			
		int resno = Integer.parseInt(request.getParameter("resno"));
		
		Statement stmt1 = con.createStatement();
		
		ResultSet rs1=stmt1.executeQuery("UPDATE reservation SET status='C' WHERE reservation.resno="+resno);
		
		
		response.sendRedirect("/CaseStudy/VOCheckServlet?uName=vo&pwd=vo");	
		stmt1.close();
		rs1.close();
		con.close();

	} catch (Exception e) {

		e.printStackTrace();
	}
}

}

