package prPetrolStation;

public class TicketPromotion extends Ticket {
	private double discount;
	public TicketPromotion (int ticketNum, String name, String licensePlate, double fuelAmount, double price, double discount){
		super(ticketNum, name, licensePlate, fuelAmount, price);
		this.discount = discount;
	}
	
	@Override
	public double totalPrice() {
		double totalPrice = super.totalPrice();
		return totalPrice - (totalPrice * discount);
	}
	
	@Override
	public String toString() {
		return "PROMOTION " + this.discount * 100 +"%: " + super.toString();
	}
}
