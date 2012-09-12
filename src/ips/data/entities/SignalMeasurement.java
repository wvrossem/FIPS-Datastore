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
 * A measurement of certain signal properties with the captured measurement
 * values. For each property there can be a number of captured values since this
 * allows us to for example take the average of the values or get other relevant
 * information (SignalMeasurementValues).
 * 
 * @author Wouter Van Rossem
 * 
 */
@PersistenceCapable
@Root
public class SignalMeasurement {

	// Datanucleus
	@Persistent(table = "SIGNAL_MEASUREMENTS")
	@Join(column = "SIGNALMEASUREMENTVALUES_ID")
	@Key(types = ips.data.entities.SignalProperty.class)
	@Value(types = ips.data.entities.SignalMeasurementValues.class)
	// Simple XML
	@ElementMap(entry = "signalMeasurement", key = "singalProperty", value = "measurements", attribute = false)
	protected Map<SignalProperty, SignalMeasurementValues> signalMeasurements;

	public SignalMeasurement() {

		super();
	}

	public SignalMeasurement(SignalProperty signalProperty,
			Map<SignalProperty, SignalMeasurementValues> signalMeasurement) {

		super();
		this.signalMeasurements = signalMeasurement;
	}

	public Map<SignalProperty, SignalMeasurementValues> getSignalMeasurement() {

		return signalMeasurements;
	}

	public void setSignalMeasurement(
			Map<SignalProperty, SignalMeasurementValues> signalMeasurement) {

		this.signalMeasurements = signalMeasurement;
	}
}
