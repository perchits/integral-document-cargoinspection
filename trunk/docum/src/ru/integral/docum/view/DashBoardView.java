package ru.integral.docum.view;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import ru.integral.docum.domain.ShipArrival;

@ManagedBean(name = "ship")
@SessionScoped
public class DashBoardView implements Serializable {
	private static final long serialVersionUID = 8925725427524960747L;

	public DashBoardView() {

		shipArrivals = new ArrayList<ShipArrival>();
		populateShip(shipArrivals, 22);
		shipDocks = new ArrayList<ShipArrival>();
		populateShip(shipDocks, 15);
		sortByOrderNo();

	}

	private void populateShip(List<ShipArrival> ship, int maxCount) {
		DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			for (int i = 1; i < maxCount; i++) {
				Date iDate = dFormat.parse("2011-12-" + Integer.toString(i));
				ship.add(new ShipArrival("Судно № " + Integer.toString(i),
						iDate, 100));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private List<ShipArrival> shipArrivals;
	private List<ShipArrival> shipDocks;

	public List<ShipArrival> getShipArrivals() {
		return shipArrivals;
	}

	public List<ShipArrival> getShipDocks() {
		return shipDocks;
	}

	public void sortByOrderNo() {

		// ascending order
		Collections.sort(shipDocks, new Comparator<ShipArrival>() {

			@Override
			public int compare(ShipArrival o1, ShipArrival o2) {

				return o1.getShipName().compareTo(o2.getShipName());

			}

		});

	}

}