package ips.data.entities;

import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * A device which can be uniquely identified and sends out some signal that can
 * be captured and from which certain properties can be observed.
 * 
 * @author Wouter Van Rossem
 * 
 */
@PersistenceCapable
@Root
public class BaseStation {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT)
	long dbid;

	@Attribute
	protected String id;

	@ElementList(required = false)
	protected List<SignalProperty> signalProperties;

	public BaseStation() {
		super();
	}

	public BaseStation(String id, List<SignalProperty> signalProperties) {
		super();
		this.id = id;
		this.signalProperties = signalProperties;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<SignalProperty> getSignalProperties() {
		return signalProperties;
	}

	public void setSignalProperties(List<SignalProperty> signalProperties) {
		this.signalProperties = signalProperties;
	}
}
