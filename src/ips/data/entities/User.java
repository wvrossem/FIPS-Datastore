package ips.data.entities;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * 
 * A user that can be identified by an ID.
 * 
 * @author Wouter Van Rossem
 * 
 */
@PersistenceCapable
@Root
public class User {

	@PrimaryKey
	@Attribute
	protected String id;

	public User() {

		super();
	}

	public User(String id) {

		super();
		this.id = id;
	}

	public String getId() {

		return id;
	}

	public void setId(String id) {

		this.id = id;
	}

}
