package ips.data.entities.wlan;

import ips.data.entities.SignalMeasurement;
import ips.data.entities.SignalMeasurementValues;
import ips.data.entities.SignalProperty;

import java.util.HashMap;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * 
 * A type of Signal measurement with measurements from only the signal property
 * RSSI, that is the different power levels of nearby access points that are
 * captured from WLAN scans.
 * 
 * An average of the different captured values is always kept and updated when
 * new values are added.
 * 
 * @author Wouter Van Rossem
 * 
 */
@PersistenceCapable
@Root
public class AccessPointPowerLevels extends SignalMeasurement {

	@Element
	protected double average;

	public AccessPointPowerLevels() {

		super();
	}

	public AccessPointPowerLevels(List<Double> levels) {
		super();

		this.signalMeasurements = new HashMap<SignalProperty, SignalMeasurementValues>();
		this.signalMeasurements.put(SignalProperty.RSSI,
				new SignalMeasurementValues(levels));

		calcAverage();
	}

	/**
	 * 
	 * Calculate the average power level.
	 * 
	 */
	private void calcAverage() {

		double sum = 0;

		SignalMeasurementValues levels = this.signalMeasurements
				.get(SignalProperty.RSSI);

		for (double level : levels.getValues()) {
			sum += level;
		}

		average = sum / levels.getValues().size();

	}

	/**
	 * 
	 * Add a single extra power level and update the average power level.
	 * 
	 * @param level
	 */
	public void addPowerLevel(double level) {

		SignalMeasurementValues levels = this.signalMeasurements
				.get(SignalProperty.RSSI);

		levels.getValues().add(level);

		calcAverage();

	}

	public List<Double> getLevels() {

		SignalMeasurementValues levels = this.signalMeasurements
				.get(SignalProperty.RSSI);

		return levels.getValues();
	}

	public double getAverage() {

		return average;
	}

	public void setAverage(int average) {

		this.average = average;
	}

	/**
	 * 
	 * Add additional power level values. Also updates the average power level.
	 * 
	 * @param apLvl
	 */
	public void addLevels(AccessPointPowerLevels apLvl) {

		SignalMeasurementValues values = this.signalMeasurements
				.get(SignalProperty.RSSI);
		List<Double> levels = values.getValues();

		for (double lvl : apLvl.getLevels()) {

			levels.add(lvl);
		}

		calcAverage();
	}
}
