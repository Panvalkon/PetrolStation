import java.util.HashMap;

import java.util.Map;

import prPetrolStation.*;

public class Main2 {

	public static void main(String[] args) {
		try {
			Ticket t1 = new Ticket(1,"Teatinos","1111AAA",50.0,2.0);
			System.out.println("Ticket without promotion: \n   " + t1);
			TicketPromotion t2 = new TicketPromotion(1,"Teatinos","1111AAA",50.0,2.0,0.10);
			System.out.println("Ticket with 10% discount: \n   " + t2);
			
			System.out.println("----------------------------------");
			//A map with the prices is created
			Map<String,Double> prices = new HashMap<>();
			prices.put(PetrolStation.FUEL95, 1.28);
			prices.put(PetrolStation.FUEL98, 1.38);
			prices.put(PetrolStation.DIESEL, 1.25);
			prices.put(PetrolStation.DIESELPLUS, 1.30);
			
			PetrolStationPromotion station3 = 
					new PetrolStationPromotion("TeatinosPROMO",prices,"dispensers.txt");
			//Station 3: natural ordering with promotion
			station3.refuel("1111AAA",PetrolStation.FUEL95,1,80);
			station3.refuel("1111AAA",PetrolStation.FUEL95,2,80);
			station3.refuel("1111AAA",PetrolStation.FUEL95,2,80);
			station3.refuel("2222BBB",PetrolStation.DIESEL,1,45);
			station3.refuel("2222BBB",PetrolStation.DIESELPLUS,1,45);
			station3.bill("1111AAA");
			station3.bill("2222BBB");
			System.out.println(station3);
			station3.refuel("1111AAA",PetrolStation.FUEL95,1,20);
			station3.refuel("1111AAA",PetrolStation.FUEL95,2,60);
			station3.refuel("1111AAA",PetrolStation.FUEL95,2,60);
			station3.refuel("2222BBB",PetrolStation.DIESEL,1,45);
			station3.refuel("2222BBB",PetrolStation.DIESELPLUS,1,45);
			station3.bill("1111AAA");
			station3.bill("2222BBB");
			System.out.println(station3);

		}catch(Exception e) {
			System.err.println("ERROR: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
