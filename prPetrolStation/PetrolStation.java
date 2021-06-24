package prPetrolStation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.StringJoiner;
import java.util.TreeMap;
import java.util.TreeSet;

public class PetrolStation {
	protected String name;
	protected int nextTicketNumber;
	private Map<String, List<Double>> dispensers;
	private Map<String, SortedSet<Ticket>> refuelings;
	private TicketAlternativeOrder order;
	public static final String FUEL95 = "fuel95";
	public static final String FUEL98 = "fuel98";
	public static final String DIESEL = "diesel";
	public static final String DIESELPLUS = "dieselPlus";
	private final Map<String, Double> prices;
	private static final int NUM_DISP = 4;

	public PetrolStation(String name, Map<String, Double> prices, String filename, TicketAlternativeOrder order) {
		this.name = name;
		this.prices = prices;
		this.order = order;
		this.nextTicketNumber = 1;
		this.refuelings = new TreeMap<String, SortedSet<Ticket>>();
		this.dispensers = new TreeMap<String, List<Double>>();
		putDispencers("fuel95");
		putDispencers("fuel98");
		putDispencers("diesel");
		putDispencers("dieselPlus");

		try (Scanner sc = new Scanner(new File(filename))) {
			while (sc.hasNextLine()) {
				try (Scanner line = new Scanner(sc.nextLine())) {
					line.useLocale(Locale.ENGLISH);
					line.useDelimiter("\\s*");
					try {
						int dispNum = line.nextInt();
						String fuelType = line.next();
						double amount = line.nextDouble();
						addFuel(fuelType, dispNum, amount);
					} catch (NoSuchElementException | IndexOutOfBoundsException e) {
						// to ignore errors;
					}

				}
			}
		} catch (FileNotFoundException e) {
			throw new PetrolStationException("No such file");
		}
	}

	private void addFuel(String fuelType, int dispNum, double amount) {
		dispensers.get(fuelType).set(dispNum, dispensers.get(fuelType).get(dispNum) + amount);
	}

	private void putDispencers(String string) {
		dispensers.put(string, new ArrayList<Double>());
		for (int i = 0; i < NUM_DISP; i++) {
			dispensers.get(string).add(0.0);
		}

	}

	public PetrolStation(String name, Map<String, Double> prices, String filename) {
		this(name, prices, filename, null);
	}

	public void refuel(String licensePlate, String fuelType, int dispNum, double amount) {
		if (dispNum > NUM_DISP - 1) {
			throw new PetrolStationException("Dispenser with such number not existing");
		}
		if (amount <= 0) {
			throw new PetrolStationException("Trying to refuel negative amount");
		}
		if (!dispensers.containsKey(fuelType)) {
			throw new PetrolStationException("Station does not have such fuel type");
		}
		if (dispensers.get(fuelType).get(dispNum) != 0) {

			if (dispensers.get(fuelType).get(dispNum) < amount) {
				amount = dispensers.get(fuelType).get(dispNum);
				dispensers.get(fuelType).set(dispNum, 0.0);
			} else {
				dispensers.get(fuelType).set(dispNum, dispensers.get(fuelType).get(dispNum) - amount);
			}
			double price = this.prices.get(fuelType);
			if (this.order == null) {
				refuelings.putIfAbsent(licensePlate, new TreeSet<Ticket>());
			} else {
				refuelings.putIfAbsent(licensePlate, new TreeSet<Ticket>(order));
			}
			Ticket ticket = newTicket(licensePlate, amount, price);
			if (refuelings.get(licensePlate).contains(ticket)) {
				throw new PetrolStationException("Such ticket already exist.");
			}
			refuelings.get(licensePlate).add(ticket);
		}
	}

	protected Ticket newTicket(String licensePlate, double amount, double price) {
		return new Ticket(nextTicketNumber++, name, licensePlate, amount, price);
	}

	public void bill(String licensePlate) {
		try (PrintWriter pw = new PrintWriter(licensePlate)) {
			double total = 0;
			for (Ticket ticket : refuelings.get(licensePlate)) {
				if (!ticket.getBilled()) {
					pw.append(ticket.toString() + "\n");
					total += ticket.totalPrice();
					ticket.setBilled(true);
				}
			}
			pw.append("TOTAL = " + String.valueOf(total));
		} catch (FileNotFoundException e) {
			// just in case;
		}
	}
	public double getaAlreadyBilled(String licensePlate) {
		double totalPaid = 0;
		for (Ticket ticket : refuelings.get(licensePlate)) {
			if (ticket.getBilled()) {
				totalPaid += ticket.totalPrice();
			}
		}
		return totalPaid;
	}
	
	@Override
	public String toString() {
		String s = new String(this.name + " = \n");
		
		for (Entry<String, List<Double>> fuelType :  dispensers.entrySet()) {
			StringJoiner sj = new StringJoiner(", ", "[", "]");
			for(double d : fuelType.getValue()) {
				sj.add(String.valueOf(d));
			}
			s += fuelType.getKey() + ": " + sj.toString() + "\nRefuelings :";
		}
		StringJoiner sj = new StringJoiner(", ", "[", "]");
		for (SortedSet<Ticket> ticket : refuelings.values()) {
			
			sj.add(ticket.toString());
			
		}
		return s += sj.toString();
	}
}
