package ips.data.entities;

import javax.jdo.annotations.PersistenceCapable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * 
 * An indoor map with an ID to identify it.
 * 
 * @author Wouter Van Rossem
 * 
 */
@PersistenceCapable
@Root
public class Map {

	@Element
	protected String mapId;

	@Element(required = false)
	protected String mapFileName;

	public Map() {
		super();
	}

	public Map(String mapId, String mapFileName) {
		super();
		this.mapId = mapId;
		this.mapFileName = mapFileName;
	}

	public String getMapId() {
		return mapId;
	}

	public void setMapId(String mapId) {
		this.mapId = mapId;
	}

	public String getMapFileName() {
		return mapFileName;
	}

	public void setMapFileName(String mapFileName) {
		this.mapFileName = mapFileName;
	}

}
