package prPetrolStation;

import java.util.StringJoiner;

public class Ticket implements Comparable<Ticket> {
	private int ticketNumber;
	private String name;
	private String carLicencePlate;
	private double numberOfLiters;
	private double pricePerLiter;
	private boolean isBilled;

	public Ticket(int ticketNumber, String name, String carLicencePlate, double numberOfLiters, double pricePerLiter) {
		if (pricePerLiter <= 0 || numberOfLiters <= 0) {
			throw new PetrolStationException("The ticket cannot be created");
		}
		if (name == null || carLicencePlate == null || name == "" || carLicencePlate == "") {
			throw new PetrolStationException("Invalid name or Licence plate number");
		}
		this.ticketNumber = ticketNumber;
		this.name = name;
		this.carLicencePlate = carLicencePlate;
		this.numberOfLiters = numberOfLiters;
		this.pricePerLiter = pricePerLiter;
		this.isBilled = false;
	}

	public boolean getBilled() {
		return isBilled;
	}

	public void setBilled(boolean isBilled) {
		this.isBilled = isBilled;
	}

	public int getNumTicket() {
		return ticketNumber;
	}

	public String getStation() {
		return name;
	}

	public double getNumLiters() {
		return numberOfLiters;
	}

	public double totalPrice() {
		return numberOfLiters * pricePerLiter;
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof Ticket && ((Ticket) o).getStation().equalsIgnoreCase(this.name)
				&& ((Ticket) o).getNumTicket() == this.ticketNumber;
	}

	@Override
	public int hashCode() {
		return this.name.hashCode() + this.ticketNumber * 7;
	}

	@Override
	public int compareTo(Ticket o) {
		int result = this.getStation().compareToIgnoreCase(o.getStation());
		if (result == 0) {
			result = Integer.compare(this.ticketNumber, o.getNumTicket());
		}
		return result;
	}

	@Override
	public String toString() {
		StringJoiner sj = new StringJoiner(", ", "(", ")");
		sj.add("station: " + this.name).add("licence: " + this.carLicencePlate).add("liters: " + this.numberOfLiters)
				.add("PRICE: " + this.totalPrice());
		return "Ticket: " + this.getNumTicket() + " " + sj.toString();
	}
}
