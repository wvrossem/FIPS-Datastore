package ips.data.entities;

import java.util.List;

import javax.jdo.annotations.PersistenceCapable;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * 
 * A list of measured values.
 * 
 * @author Wouter Van Rossem
 * 
 */
@Root
@PersistenceCapable()
public class SignalMeasurementValues {

	@ElementList
	protected List<Double> values;

	public SignalMeasurementValues() {
		super();
	}

	public SignalMeasurementValues(List<Double> values) {
		super();
		this.values = values;
	}

	public List<Double> getValues() {
		return values;
	}

	public void setValues(List<Double> values) {
		this.values = values;
	}
}
