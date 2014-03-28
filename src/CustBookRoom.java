import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;



public class CustBookRoom extends HttpServlet {
 private static final long serialVersionUID = 1L;

 
 public CustBookRoom() {
  super();
 }

 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
  try {
	    PrintWriter pw = response.getWriter();
		InitialContext ctx = new InitialContext();
		DataSource ds = (DataSource) ctx.lookup("OracleDS");
		Connection connection = ds.getConnection();
		connection.commit();
		

   String roomid=request.getParameter("roomid");
   String hotelid=request.getParameter("hotelid");
   String arrival=request.getParameter("arrival");
   String departure=request.getParameter("departure");
   System.out.println(arrival);
   System.out.println("HEREjjhjhEEEEE");
    /*SimpleDateFormat format1 = new SimpleDateFormat("dd-MMM-yy");
	SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yyyy");
	java.util.Date date = format1.parse(arrival);
	
	arrival=format2.format(date);
	date = format1.parse(departure);
	departure=format2.format(date);*/
	
	System.out.println(arrival);
   
   Statement stmt1 = connection.createStatement();
   Statement stmt2=connection.createStatement();
	
	//ArrayList listOfProd=null;
	HttpSession ses=request.getSession(false);
	//pulling up the session
	if (ses == null) {
	    // Not created yet. Now do so yourself.
		System.out.println("Session not created");
		//stmt2.executeQuery("insert into customer values(6,'gender6','firstname6','surname6','address6','8888888888','email8')");
		//System.out.println("insert into reservation values(8,6,"+roomid+","+hotelid+",to_date('"+arrival+"','dd-Mmm-yy'),to_date('"+departure+"','dd-Mmm-yy'),'B')");
		stmt1.executeQuery("insert into reservation values(8,6,"+roomid+","+hotelid+",'"+arrival+"','"+departure+"','B')");
		
		//stmt1.executeQuery("insert into reservation values(8,6,"+roomid+","+hotelid+",to_date('"+arrival+"','dd-Mmm-yy'),to_date('"+departure+"','dd-Mmm-yy'),'B')");
		System.out.println("query fired");
		
		ses=request.getSession();
		ses.setAttribute("resnos", "8");	
		ses.setAttribute("custnos", "5");	
	    
	} else {
	    // Already created.
		System.out.println("Session already there");
		
		String resnos=(String) ses.getAttribute("resnos");
		String custnos=(String) ses.getAttribute("custnos");
		int resno=Integer.parseInt(resnos);
		int custno=Integer.parseInt(custnos);
		resno=resno +1;
		custno=custno +1;
		stmt1.executeQuery("insert into customer values("+custno+",'gender"+custno+"','firstname"+custno+"','surname"+custno+"','address"+custno+"','"+custno+""+custno+""+custno+""+custno+""+custno+""+custno+""+custno+""+custno+""+custno+""+custno+"','email"+custno+"')");
		stmt1.executeQuery("insert into reservation values('"+resno+"','"+custno+"',"+roomid+","+hotelid+",'"+arrival+"','"+departure+"','B')");
		ses.setAttribute("resnos", resno);	
		ses.setAttribute("custnos", custno);
		
	}
	response.sendRedirect("/CaseStudy/CustLogin");	
	stmt1.close();
	connection.close();
   
  }
  catch(Exception e){

  }
 }


}