package ips.data.entities.wlan;

import javax.jdo.annotations.PersistenceCapable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import ips.data.entities.Map;

/**
 * 
 * A type of map used in WLAN Fingerprinting where the map is divided in grids
 * of a certain size.
 * 
 * @author Wouter Van Rossem
 * 
 */
@PersistenceCapable
@Root
public class GridMap extends Map {

	@Element
	protected int gridSize;

	public GridMap() {
		super();
	}

	public GridMap(String mapId, String mapFileName, int gridSize) {
		super(mapId, mapFileName);
		this.gridSize = gridSize;
	}

	public int getGridSize() {
		return gridSize;
	}

	public void setGridSize(int gridSize) {
		this.gridSize = gridSize;
	}

}
