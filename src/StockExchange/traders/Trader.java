package StockExchange.traders;

public class Trader {
	private Brokerage myBrokerage;
	private String myName;
	private String myPswd;
	
	public Trader(Brokerage brokerage, String name, String pswd){
		myBrokerage = brokerage;
		myName = name.toUpperCase();
		myPswd = pswd;
	}
	
	public int compareTo(Trader other){
		return (other.getName()).compareTo(myName);
	}
	
	public String getName(){
		return myName;
	}
	
	public boolean equals(Object other){
		try{
			if(this.compareTo((Trader) other)==0){
				return true;
			}else{
				return false;
			}
		}catch(ClassCastException e){
			System.out.println("You're a poopie");
			return false;
		}
	}
	
	public String getPassword(){
		return myPswd;
	}
	
	public void getQuote(String symbol){
		
	}
	
	public boolean hasMessages(){
		if ()
	}
	

}
