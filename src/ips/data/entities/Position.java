package ips.data.entities;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * 
 * A simple 2-D position on a map where a Measurement can take place or that is
 * returned as a position estimate by a positioning algorithm. It is defined by
 * its X and Y coordinate on a map of an indoor location.
 * 
 * 
 * @author Wouter Van Rossem
 * 
 */
@PersistenceCapable
@Root
public class Position {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT)
	@Attribute(required = false)
	protected long id;

	@Element(required = false)
	protected Map map;

	@Element
	protected int x;
	@Element
	protected int y;

	public Position() {
		super();
	}

	public Position(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public Position(Map map, int x, int y) {
		super();
		this.map = map;
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

}
