package ips.data;

import ips.data.entities.Position;
import ips.data.entities.wlan.AccessPoint;
import ips.data.entities.wlan.WLANFingerprint;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

/**
 * 
 * The DataManager takes care of communication with the datastore. In our case
 * it uses the PersistenceManager from Datanucleus to persist, delete and find
 * objects fromt the datastore.
 * 
 * Configuration for the datastore is stored in the datanucleus.properties file
 * and is loaded at creation.
 * 
 * @author Wouter Van Rossem
 * 
 */
public class DataManager {

	/** The DataNucleus PersistenceManager. */
	protected PersistenceManager pm;

	/**
	 * Loads the datanucleus.properties configuration file and initializes the
	 * PersistenceManager.
	 */
	public DataManager() {

		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		InputStream input = classLoader
				.getResourceAsStream("datanucleus.properties");

		PersistenceManagerFactory pmf = JDOHelper
				.getPersistenceManagerFactory(input);
		pm = pmf.getPersistenceManager();
	}

	/**
	 * Delete all persisted object from the database.
	 */
	@SuppressWarnings("unchecked")
	public void clearDatabase() {

		javax.jdo.Query q = pm.newQuery("SELECT FROM "
				+ WLANFingerprint.class.getName());
		List<WLANFingerprint> fps = (List<WLANFingerprint>) q.execute();

		for (WLANFingerprint fp : fps) {
			pm.deletePersistent(fp);
		}
	}

	/**
	 * 
	 * Retrieve all the WLAN samples from the datastore
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<WLANFingerprint> getSampleData() {

		javax.jdo.Query q = pm.newQuery("SELECT FROM "
				+ WLANFingerprint.class.getName());
		List<WLANFingerprint> fps = (List<WLANFingerprint>) q.execute();

		return fps;
	}

	/**
	 * 
	 * Find the fingerprint of a certain location
	 * 
	 * @param p
	 *            The position for which to find the WLAN fingerprint
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public WLANFingerprint findFpWithLocation(Position p) {

		javax.jdo.Query q = pm.newQuery(WLANFingerprint.class);

		q.setFilter("position.x == " + p.getX() + " && position.y == "
				+ p.getY());

		Object qRes = q.execute();

		List<WLANFingerprint> fps = (List<WLANFingerprint>) qRes;

		if (fps.isEmpty()) {

			return null;
		} else {

			WLANFingerprint res = fps.get(0);
			return res;
		}
	}

	/**
	 * 
	 * Persists a WLAN Fingeprint to the datastore.
	 * 
	 * First does a check if there already exists a fingerprint on this
	 * location, and if so combines them.
	 * 
	 * @param fp
	 *            The fingerprint to persist to the datastore.
	 */
	public void persist(WLANFingerprint fp) {

		try {

			WLANFingerprint existingFP = (WLANFingerprint) findFpWithLocation(fp
					.getPosition());

			if (existingFP == null) {
				pm.makePersistent(fp);
			} else {
				// Add the additional power levels to the fingerprint
				existingFP.putApLevels(fp.getMeasurement().getApMeasurements());
				pm.makePersistent(existingFP);
			}
		} catch (JDODataStoreException e) {

		}

	}

	/**
	 * 
	 * Persist an object to the datastore.
	 * 
	 * @param o
	 *            Object to perist.
	 */
	public void persist(Object o) {

		try {

			pm.makePersistent(o);
		} catch (JDODataStoreException e) {
		}
	}

	/**
	 * 
	 * Delete an object from the datastore.
	 * 
	 * @param o
	 *            The object to delete from the datastore.
	 */
	public void delete(Object o) {
		pm.deletePersistent(o);
	}

	/**
	 * 
	 * Checks if a WLAN fingerprint contains an access point with a certain
	 * BSSID.
	 * 
	 * @param fp
	 *            The fingerprint to check
	 * @param bssid
	 *            The bssid of the access point
	 * @return True if an access point with that bssid is in the fingerprint.
	 *         False otherwise.
	 */
	private boolean containsAp(WLANFingerprint fp, String bssid) {

		for (AccessPoint ap : fp.getMeasurement().getApMeasurements().keySet()) {

			if (ap.getBSSID().equals(bssid)) {

				return true;
			}
		}

		return false;
	}

	/**
	 * 
	 * Get all the WLAN fingerprints that have contain any of the access points
	 * with given bssid.
	 * 
	 * @param bssids
	 *            The bssids of the access points to find
	 * @return The WLAN fingerprints that contain any of the access points with
	 *         the bssids.
	 */
	public List<WLANFingerprint> getTrainingDataWithBSSIDs(List<String> bssids) {

		List<WLANFingerprint> unfilteredFps = this.getSampleData();

		List<WLANFingerprint> fps = new ArrayList<WLANFingerprint>();

		/**
		 * Loop over all the fingerprints and check if it contains an access
		 * point with one of the bssids.
		 */
		for (WLANFingerprint fp : unfilteredFps) {

			for (String bssid : bssids) {

				if (containsAp(fp, bssid)) {

					fps.add(fp);
					break;
				}
			}
		}

		return fps;
	}

	/*
	 * @SuppressWarnings({ "unchecked" }) public List<Fingerprint>
	 * getTrainingDataWithBSSIDs(List<String> bssids) {
	 * 
	 * Transaction tx = pm.currentTransaction();
	 * 
	 * List<Fingerprint> fps = null;
	 * 
	 * try { tx.begin();
	 * 
	 * // TEST QUERY
	 * 
	 * // SELECT * FROM FINGERPRINT f INNER JOIN FP_AP_LEVELS l ON f.id =
	 * l.fingerprint_id JOIN ACCESSPOINT a ON a.accesspoint_id =
	 * l.accesspoint_id_kid JOIN ACCESSPOINTPOWERLEVELS al ON
	 * al.accesspointpowerlevels_id = l.accesspointpowerlevels_id_vid WHERE
	 * a.bssid = "00:19:70:49:ba:a7"
	 * 
	 * 
	 * String qry = "SELECT FROM " + Fingerprint.class.getName();
	 * 
	 * qry += " f INNER JOIN FP_AP_LEVELS l ON f.id = l.fingerprint_id"; qry +=
	 * " JOIN " + AccessPoint.class.getName()+
	 * " a ON a.accesspoint_id = l.accesspoint_id_kid"; qry += " JOIN " +
	 * AccessPointPowerLevels.class.getName() +
	 * " al ON al.accesspointpowerlevels_id = l.accesspointpowerlevels_id_vid";
	 * 
	 * Iterator<String> iter = bssids.iterator();
	 * 
	 * qry += " WHERE a.bssid == \"" + iter.next() + "\"";
	 * 
	 * 
	 * while( iter.hasNext() ) {
	 * 
	 * String bssid = iter.next();
	 * 
	 * qry += " OR a.bssid == \"" + bssid + "\"";
	 * 
	 * }
	 * 
	 * javax.jdo.Query q = pm.newQuery(qry);
	 * 
	 * System.out.println(q.toString());
	 * 
	 * fps = (List<Fingerprint>)q.execute();
	 * 
	 * tx.commit(); } finally {
	 * 
	 * if (tx.isActive()) { tx.rollback(); }
	 * 
	 * pm.close();
	 * 
	 * }
	 * 
	 * return fps; }
	 */

}
