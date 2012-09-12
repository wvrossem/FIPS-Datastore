package ips.data.serialization;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;

import org.simpleframework.xml.core.Persister;

/**
 * Serializes specific object to XML using the Simple XML
 * framework.
 * 
 * Uses the serializer from the framework to do the actual
 * serialization.
 * 
 * @author Wouter Van Rossem
 *
 */
public class Serializer {

	/**
	 * The actual serializer from the framework
	 */
	private org.simpleframework.xml.Serializer xmlSerializer;

	/**
	 * Private constructor prevents instantiation from other classes
	 */
	private Serializer() {

		xmlSerializer = new Persister();
	}

	/**
	 * SingletonHolder is loaded on the first execution of
	 * Singleton.getInstance() or the first access to SingletonHolder.INSTANCE,
	 * not before.
	 * 
	 */
	private static class SerializerHolder {
		
		public static final Serializer instance = new Serializer();
	}

	/**
	 * Gets the instance from the SerializerHolder
	 * 
	 * @return A Serializer instance
	 */
	public static Serializer getInstance() {
		
		return SerializerHolder.instance;
	}

	/**
	 * Serializes an object in XML format to a given file.
	 * 
	 * @param o The object to serialize
	 * @param f The file to serialize to
	 * @throws Exception
	 */
	public void writeToFile(Object o, File f) throws Exception {

		xmlSerializer.write(o, f);
	}

	/**
	 * Serializes an object to a given output stream 
	 * in XML format.
	 * 
	 * @param o The object to serialize
	 * @param out The output stream to serialize to
	 * @throws Exception
	 */
	public void serializeToXML(Object o, OutputStream out) throws Exception {

		xmlSerializer.write(o, out);
	}

	/**
	 * Serializes an object to XML and returns it as a string.
	 * 
	 * @param o The object to serialize
	 * @return A string containing the XML of the serialized object
	 * @throws Exception
	 */
	public String serializeToXML(Object o) throws Exception {

		// New output stream to write the serialization to
		OutputStream out = new ByteArrayOutputStream();

		// Serialize the object to the output stream
		this.serializeToXML(o, out);

		// Return it as a string
		return out.toString();
	}
	
	/**
	 * Serializes an object to a given buffered writer 
	 * 
	 * @param o The object to serialize
	 * @param out The buffered writer to serialize to
	 * @throws Exception
	 */
	public void serializeToXML(Object o, BufferedWriter out) throws Exception {
		
		xmlSerializer.write(o, out);
	}

	/**
	 * Serializes an object in XML format to a given file.
	 * 
	 * @param o The object to serialize
	 * @param f The file to serialize to
	 * @throws Exception
	 */
	public void serializeToXML(Object o, File file) throws Exception {
		
		xmlSerializer.write(o, file);
	}

	/**
	 * Deserializes an object from a given file.
	 * 
	 * @param type The Class of the object in the file
	 * @param source The file that contains the serialized object
	 * @return The deserialized object
	 * @throws Exception
	 */
	public Object deserialize(Class<?> type, File source) throws Exception {

		return xmlSerializer.read(type, source);
	}

	/**
	 * Deserializes an object from a string.
	 * 
	 * @param type The Class of the object in the string
	 * @param data The string that contains the serialized object
	 * @return The deserialized object
	 * @throws Exception
	 */
	public Object deserialize(Class<?> type, String data) throws Exception {

		return xmlSerializer.read(type, data);
	}
}
