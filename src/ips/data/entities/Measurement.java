package ips.data.entities;

import java.util.Map;

import javax.jdo.annotations.Join;
import javax.jdo.annotations.Key;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Value;

import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

/**
 * 
 * Contains the different Signal measurements of certain Base stations.
 * 
 * @author Wouter Van Rossem
 * 
 */
@PersistenceCapable
@Root
public class Measurement {

	// Datanucleus
	@Persistent(table = "BS_MEASUREMENTS")
	@Join(column = "BASESTATION_ID")
	@Key(types = ips.data.entities.BaseStation.class)
	@Value(types = ips.data.entities.SignalMeasurement.class)
	// Simple XML
	@ElementMap(entry = "measurement", attribute = false, required = false)
	protected Map<BaseStation, SignalMeasurement> measurements;

	public Measurement() {

		super();
	}

	public Measurement(Map<BaseStation, SignalMeasurement> measurements) {
		super();
		this.measurements = measurements;
	}

	public Map<BaseStation, SignalMeasurement> getMeasurements() {
		return measurements;
	}

	public void setMeasurements(Map<BaseStation, SignalMeasurement> measurements) {
		this.measurements = measurements;
	}
}
