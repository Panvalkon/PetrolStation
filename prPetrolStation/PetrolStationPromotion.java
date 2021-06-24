package prPetrolStation;

import java.util.Map;

public class PetrolStationPromotion extends PetrolStation {
	public static final double MINIMUM_BILLED1 = 100;
	public static final double MINIMUM_BILLED2 = 300;
	public static final double DISCOUNT1 = 0.1;
	public static final double DISCOUNT2 = 0.3;

	public PetrolStationPromotion(String name, Map<String, Double> prices, String filename,
			TicketAlternativeOrder order) {
		super(name, prices, filename, order);
		// TODO Auto-generated constructor stub
	}

	public PetrolStationPromotion(String name, Map<String, Double> prices, String filename) {
		super(name, prices, filename);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Ticket newTicket(String licensePlate, double amount, double price) {
		double billed = super.getaAlreadyBilled(licensePlate);
		Ticket ticket;
		if (billed >= MINIMUM_BILLED2) {
			ticket = new TicketPromotion(nextTicketNumber++, name, licensePlate, amount, price, DISCOUNT2);
		} else if (billed >= MINIMUM_BILLED1) {
			ticket = new TicketPromotion(nextTicketNumber++, name, licensePlate, amount, price, DISCOUNT1);
		} else {
			ticket = new Ticket(nextTicketNumber++, name, licensePlate, amount, price);
		}
		return ticket;
	}

}
