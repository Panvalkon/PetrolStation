import java.util.HashMap;
import java.util.Map;
import prPetrolStation.*;

public class Main1 {

	public static void main(String[] args) {
		try {
			//A map with the prices is created
			Map<String,Double> prices = new HashMap<>();
			prices.put(PetrolStation.FUEL95, 1.28);
			prices.put(PetrolStation.FUEL98, 1.38);
			prices.put(PetrolStation.DIESEL, 1.25);
			prices.put(PetrolStation.DIESELPLUS, 1.30);
			
			//Petrol Station 1: natural ordering without promotion
			System.out.println ("Natural ordering, without promotion");
			System.out.println("------------------------------");
			PetrolStation station = 
				new PetrolStation("Teatinos",prices,"dispensers.txt");
			
			System.out.println("Initial state: " + station);
			station.refuel("1111AAA",PetrolStation.FUEL95,1,20);
			station.refuel("1111AAA",PetrolStation.FUEL95,2,60);
			station.refuel("1111AAA",PetrolStation.FUEL95,2,60);
			station.refuel("2222BBB",PetrolStation.DIESEL,1,45);
			station.refuel("2222BBB",PetrolStation.DIESELPLUS,1,45);
			station.bill("1111AAA");
			station.bill("2222BBB");
			System.out.println("After refueling: " + station);
			
			System.out.println("\nAlternative ordering, without promotion");
			System.out.println("------------------------------");
			//Petrol Station 2: alternative ordering without promotion
			PetrolStation station2 = 
					new PetrolStation("Ampliacion",prices,"dispensers.txt",
							new TicketAlternativeOrder());
			station2.refuel("1111AAA",PetrolStation.FUEL95,1,20);
			station2.refuel("1111AAA",PetrolStation.FUEL95,2,60);
			station2.refuel("1111AAA",PetrolStation.FUEL95,2,60);
			station2.refuel("2222BBB",PetrolStation.DIESEL,1,45);
			station2.refuel("2222BBB",PetrolStation.DIESELPLUS,1,45);
			station2.bill("1111AAA");
			station2.bill("2222BBB");
			System.out.println("After refueling: " + station2);
		}catch(Exception e) {
			System.err.println("ERROR: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
