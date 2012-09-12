package ips.data.entities.wlan;

import ips.data.entities.Measurement;
import ips.data.entities.SignalProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.jdo.annotations.Join;
import javax.jdo.annotations.Key;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Value;

import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

/**
 * 
 * A measurement of a WLAN fingerprint. Contains the measured access point and
 * their captured power levels.
 * 
 * @author Wouter Van Rossem
 * 
 */
@PersistenceCapable
@Root
public class WLANMeasurement extends Measurement {

	// Datanucleus
	@Persistent(table = "AP_POWERLEVELS")
	@Join(column = "BASESTATION_ID")
	@Key(types = ips.data.entities.wlan.AccessPoint.class)
	@Value(types = ips.data.entities.wlan.AccessPointPowerLevels.class)
	// Simple XML
	@ElementMap(entry = "wlanMeasurement", key = "accessPoint", value = "powerLevels", attribute = false)
	protected Map<AccessPoint, AccessPointPowerLevels> apMeasurements;

	public WLANMeasurement() {

		super();
	}

	public WLANMeasurement(Map<AccessPoint, AccessPointPowerLevels> levels) {

		super();

		List<SignalProperty> properties = new ArrayList<SignalProperty>();
		properties.add(SignalProperty.RSSI);

		this.apMeasurements = new HashMap<AccessPoint, AccessPointPowerLevels>();

		for (Entry<AccessPoint, AccessPointPowerLevels> entry : levels
				.entrySet()) {

			this.apMeasurements.put(entry.getKey(), entry.getValue());
		}
	}

	public Map<AccessPoint, AccessPointPowerLevels> getApMeasurements() {

		return apMeasurements;
	}

	/**
	 * 
	 * @return Returns a map of the average power levels of each access point in
	 *         the measurement
	 */
	public Map<AccessPoint, Integer> getLevels() {

		Map<AccessPoint, Integer> levels = new HashMap<AccessPoint, Integer>();

		for (Entry<AccessPoint, AccessPointPowerLevels> entry : apMeasurements
				.entrySet()) {

			AccessPoint ap = entry.getKey();
			AccessPointPowerLevels pLvls = entry.getValue();

			levels.put(ap, (int) pLvls.getAverage());
		}

		return levels;
	}

	public void put(AccessPoint ap, AccessPointPowerLevels levels) {

		this.apMeasurements.put(ap, levels);
	}

	public void setApMeasurements(
			Map<AccessPoint, AccessPointPowerLevels> levels) {

		this.apMeasurements = levels;

	}

}
