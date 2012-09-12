package ips.data.entities;

import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * 
 * A fingerprint for doing scene analysis. Represents a measurement on a
 * position on an indoor map.
 * 
 * @author Wouter Van Rossem
 */
@PersistenceCapable
@DatastoreIdentity(strategy = IdGeneratorStrategy.INCREMENT)
@Root
public class Fingerprint {

	@Element
	protected Position position;

	@Element(required = false)
	protected Measurement measurement;

	public Fingerprint() {
		super();
	}

	public Fingerprint(Position position, Measurement measurements) {
		super();
		this.position = position;
		this.measurement = measurements;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Measurement getMeasurement() {
		return measurement;
	}

	public void setMeasurement(Measurement measurement) {
		this.measurement = measurement;
	}

}
