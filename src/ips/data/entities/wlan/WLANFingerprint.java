package ips.data.entities.wlan;

import ips.data.entities.Fingerprint;
import ips.data.entities.Position;

import java.util.Map;
import java.util.Map.Entry;

import javax.jdo.annotations.PersistenceCapable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * 
 * A fingerprint used in WLAN fingerprinting.
 * 
 * @author Wouter Van Rossem
 *
 */
@PersistenceCapable
@Root(strict = false)
public class WLANFingerprint extends Fingerprint {

	@Element
	WLANMeasurement wlanMeasurement;

	public WLANFingerprint() {
		super();
	}

	public WLANFingerprint(Position p, WLANMeasurement m) {

		this.position = p;
		this.wlanMeasurement = m;
	}

	public WLANMeasurement getMeasurement() {
		return wlanMeasurement;
	}

	public AccessPointPowerLevels getApLevels(String bssid) {

		for (Entry<AccessPoint, AccessPointPowerLevels> entry : wlanMeasurement
				.getApMeasurements().entrySet()) {

			if (entry.getKey().BSSID.equals(bssid)) {
				return entry.getValue();
			}
		}

		return null;

	}

	/**
	 * Checks if the access point is in this fingerprint.
	 * 
	 * @param ap
	 * @return True if the access point is in this fingerprint.
	 */
	public boolean containsAP(AccessPoint ap) {

		for (AccessPoint entry : wlanMeasurement.getApMeasurements().keySet()) {
			if (entry.getBSSID().equals(ap.getBSSID())) {
				System.out.println("Yes it contains this AP");
				return true;
			}
		}

		return false;
	}

	/**
	 * Add extra levels to the list of power levels of an access point in the
	 * WLAN fingerprint/
	 * 
	 * @param ap
	 * @param apLvl
	 */
	public void addLevels(AccessPoint ap, AccessPointPowerLevels apLvl) {

		for (Entry<AccessPoint, AccessPointPowerLevels> entry : wlanMeasurement
				.getApMeasurements().entrySet()) {
			if (entry.getKey().getBSSID().equals(ap.getBSSID())) {

				entry.getValue().addLevels(apLvl);
			}
		}
	}

	/**
	 * 
	 * Adds new power levels to this WLAN Fingerprint. If the access point was
	 * already in the fingerprint, the values are added. Otherwise the new
	 * access point with its values are added.
	 * 
	 * @param lvls
	 */
	public void putApLevels(Map<AccessPoint, AccessPointPowerLevels> lvls) {

		for (Entry<AccessPoint, AccessPointPowerLevels> entry : lvls.entrySet()) {

			AccessPoint ap = entry.getKey();
			AccessPointPowerLevels apLvl = entry.getValue();

			if (containsAP(ap)) {

				addLevels(ap, apLvl);

			} else {
				wlanMeasurement.put(ap, apLvl);
			}

		}

	}

	public Map<AccessPoint, AccessPointPowerLevels> getLevels() {

		return wlanMeasurement.getApMeasurements();
	}

}
