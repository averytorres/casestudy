import java.io.Serializable;


public class Cust implements Serializable{
	
	int hotelid;int roomid;int pr;int qty;
	
	public Cust (int ehotelid,int eroomid, int epr,int equantity){
	hotelid=ehotelid;
	roomid=eroomid;
	pr=epr;
	qty=equantity;}
}