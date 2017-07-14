package MA;
import java.util.Scanner;

public class BusinessLogic {
	
	static Scanner in = new Scanner(System.in);
	
	
	
	
	static public void doBooking( ) {
		
		Booking newBooking;
		
		//MicroAirlines.flights.add(arg0)
		
		System.out.println("Name of passenger?");
		String name = in.nextLine();
		
		printFlightList();
		
		
		int flightIdx=-1;
		
		do {
			try {
				System.out.println("What flight do you want to book?");
				flightIdx = Integer.parseInt(in.nextLine());
				break;
			} catch (Exception e) {
				System.out.println("Invalid number");
			}
		} while (true);
		
		String temp;
		do {
			System.out.println("Select F for Firstclass or E for Economyclass");
			temp = in.nextLine().toUpperCase();
		} while ( (!temp.equals("E"))  && !temp.equals("F")) ;
		
		TicketClassesEnum ticket;
		if (temp.equals("E"))
			ticket = TicketClassesEnum.ECONOMY;
		else
			ticket = TicketClassesEnum.FIRSTCLASS;
		
		
		do {
			System.out.println("Do you want to order food?");
			temp = in.nextLine().substring(0,1).toUpperCase();
		} while ( (!temp.equals("Y"))  && !temp.equals("N")) ;
		
		boolean wantToEat=false;
		Meal selectedMeal=null;
		
		if (temp.equals("Y")) { 
			wantToEat=true;		
			printFoodMenu(ticket);

			int mealIdx=-1;
			
			do {
				try {
					System.out.println("What food do you want to order?");
					mealIdx = Integer.parseInt(in.nextLine());
					break;
				} catch (Exception e) {
					System.out.println("Invalid number");
				}
			} while (true);
			
			if (ticket==TicketClassesEnum.ECONOMY) 
				selectedMeal=CheapMeal.cheap.get(mealIdx);
			else
				selectedMeal=NiceMeal.nice.get(mealIdx);
			
		};
				
		
		newBooking = new Booking(name, MicroAirlines.flights.get(flightIdx), ticket, wantToEat, selectedMeal);
		
		MicroAirlines.flights.get(flightIdx).book(newBooking);
		
		
		
		//System.out.println("\n\nBooking reference="+newBooking.getBookingNr());
		
		printTicket(newBooking);
		
			
		
	}
	
	private static void printTicket(Booking newBooking) {
		// TODO Auto-generated method stub ///////////////////////////////////////////////////////////////
		
		
		
	}


	private static void printFoodMenu(TicketClassesEnum ticket) {
		switch(ticket) {
		case ECONOMY:
			for (int i=0; i<CheapMeal.cheap.size(); i++)
				System.out.println(i+" "+ CheapMeal.cheap.get(i).toString());
			break;
		case FIRSTCLASS:
			for (int i=0; i<NiceMeal.nice.size(); i++)
				System.out.println(i+" "+ NiceMeal.nice.get(i).toString());		
			break;
		}
		
	}

	public static void doListFlights() {
		
		BusinessLogic.printFlightList();
		
		int flightIdx=-1;
		
		do {
			try {
				System.out.println("What flight do you want to print?");
				flightIdx = Integer.parseInt(in.nextLine());
				break;
			} catch (Exception e) {
				System.out.println("Invalid number");
			}
		} while (true);
			
		MicroAirlines.flights.get(flightIdx).print();
		
	}
	
	
	static public void printFlightList() {
		for (int i=0; i< MicroAirlines.flights.size(); i++) {
			System.out.println(i+" "+MicroAirlines.flights.get(i));
		}
	}
		
	static public void printSeatList(Flight f, boolean singleNumber) {
		int seat=1;//start with seat 1
		
		System.out.println("Seats First class");

		for (int i=0; i< f.firstclassBookings.length; i++) {
			System.out.print(fStr("Seat "+seat+" ", 10));
			if ( f.firstclassBookings[i] == null) System.out.println(fStr("<empty>",25)); 
			else {
				System.out.print(fStr(f.firstclassBookings[i].getPassengerName(),25));		

				if (f.firstclassBookings[i].isWantToEat()) {
					System.out.println(" < "+f.firstclassBookings[i].getSelectedMeal()+" >");
				} else System.out.println(" <no food ordered >");		
			}
			seat++;
		}
		if (singleNumber==false) // if not single number then reset seat between firstclass/economy
			seat=1; 
		
		System.out.println("\nSeats Economy class");
		

		for (int i=0; i< f.economyBookings.length; i++) {
			System.out.print(fStr("Seat "+seat+" ", 10));
			if ( f.economyBookings[i] == null) System.out.println(fStr("<empty>", 25)); 
			else {
				System.out.print(fStr(f.economyBookings[i].getPassengerName(),25));
				
				if (f.economyBookings[i].isWantToEat()) {
					System.out.println(" < "+f.economyBookings[i].getSelectedMeal()+" >");
				} else System.out.println(" < no food ordered >");
				
			}
			seat++;
		}

		
	}
		
		
		
	public static String fStr(String in, int len) {
		return (in+"                                                            ").substring(0, len);
	}

	public static void doUnbooking() {
		// TODO Auto-generated method stub
		
	}

	public static void doListBooking() {
		// TODO Auto-generated method stub
		
	}

	public static void doUnbookPassenger() {
		BusinessLogic.printFlightList();
		
		int flightIdx=-1;
		
		do {
			try {
				System.out.println("What flight do you want to unbook from?");
				flightIdx = Integer.parseInt(in.nextLine());
				break;
			} catch (Exception e) {
				System.out.println("Invalid number");
			}
		} while (true);
			
		printSeatList(MicroAirlines.flights.get(flightIdx), true);
		
		int seatNumber=-1;
		
		do {
			try {
				System.out.println("What seat do you want to unbook?");
				seatNumber = Integer.parseInt(in.nextLine());
				break;
			} catch (Exception e) {
				System.out.println("Invalid number");
			}
		} while (true);		
		
		unbookSeat(MicroAirlines.flights.get(flightIdx), seatNumber);
				
	}

	private static void unbookSeat(Flight flight, int seatNumber) {
		if (seatNumber>=flight.getFirstclassBookings().length) {
			// ECONOMY Class
			seatNumber--;
			seatNumber-= flight.getFirstclassBookings().length;
			flight.getEconomyBookings()[seatNumber]=null;	
		} else {
			// FIRST CLASS
			seatNumber--;
			flight.getFirstclassBookings()[seatNumber]=null;	
		}
	}

	public static void doCheckProfit() {
		BusinessLogic.printFlightList();
		
		int flightIdx=-1;
		
		do {
			try {
				System.out.println("What flight do you want to get economic info about?");
				flightIdx = Integer.parseInt(in.nextLine());
				break;
			} catch (Exception e) {
				System.out.println("Invalid number");
			}
		} while (true);
		
		Flight f=MicroAirlines.flights.get(flightIdx);
			
		int totalFood=0;
		int totalTicket=0;
		int profit=0;
		
		for (int i=0; i< f.firstclassBookings.length; i++) {
			if ( f.firstclassBookings[i]!=null) {
				totalFood += f.firstclassBookings[i].getMealPrice();
				totalTicket += f.firstclassBookings[i].getFlightPrice();
			}
		}
		
		for (int i=0; i< f.economyBookings.length; i++) {
			if ( f.economyBookings[i]!=null) {
				totalFood += f.economyBookings[i].getMealPrice();
				totalTicket += f.economyBookings[i].getFlightPrice();
			}				
		}
		
		profit = totalFood+totalTicket;
		profit *= 0.3;
		
		System.out.println("totalFood="+totalFood);
		System.out.println("totalTicket="+totalTicket);
		System.out.println("Profit ="+profit);
				
	}

	
	

}
