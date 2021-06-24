package prPetrolStation;

import java.util.Comparator;

public class TicketAlternativeOrder implements Comparator<Ticket> {

	public TicketAlternativeOrder() {

	}

	@Override
	public int compare(Ticket o1, Ticket o2) {
		int result = Integer.compare(o1.getNumTicket(), o2.getNumTicket()) * -1;
		if (result == 0) {
			result = o1.getStation().compareToIgnoreCase(o2.getStation());
		}
		return result;
	}

}
