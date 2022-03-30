
package com.cgtsi.application;
import java.io.Serializable;

/**
 * This class holds the primary security details
*/

public class PrimarySecurityDetails implements Serializable{
	
	
	private String landParticulars;
	private double landValue;

	private String bldgParticulars;
	private double bldgValue;
	
	private String machineParticulars;
	private double machineValue;

	private String assetsParticulars;
	private double assetsValue;
	
	private String currentAssetsParticulars;
	private double currentAssetsValue;
		
	private String othersParticulars;
	private double othersValue;
	
	/**
	 * @return
	 */
	public String getAssetsParticulars() {
		return assetsParticulars;
	}

	/**
	 * @return
	 */
	public double getAssetsValue() {
		return assetsValue;
	}

	/**
	 * @return
	 */
	public String getBldgParticulars() {
		return bldgParticulars;
	}

	/**
	 * @return
	 */
	public double getBldgValue() {
		return bldgValue;
	}

	/**
	 * @return
	 */
	public String getCurrentAssetsParticulars() {
		return currentAssetsParticulars;
	}

	/**
	 * @return
	 */
	public double getCurrentAssetsValue() {
		return currentAssetsValue;
	}

	/**
	 * @return
	 */
	public double getLandValue() {
		return landValue;
	}

	/**
	 * @return
	 */
	public String getMachineParticulars() {
		return machineParticulars;
	}

	/**
	 * @return
	 */
	public double getMachineValue() {
		return machineValue;
	}

	/**
	 * @return
	 */
	public String getOthersParticulars() {
		return othersParticulars;
	}

	/**
	 * @return
	 */
	public double getOthersValue() {
		return othersValue;
	}

	/**
	 * @param string
	 */
	public void setAssetsParticulars(String aAssetsParticulars) {
		assetsParticulars = aAssetsParticulars;
	}

	/**
	 * @param d
	 */
	public void setAssetsValue(double aAssetsValue) {
		assetsValue = aAssetsValue;
	}

	/**
	 * @param string
	 */
	public void setBldgParticulars(String aBldgParticulars) {
		bldgParticulars = aBldgParticulars;
	}

	/**
	 * @param d
	 */
	public void setBldgValue(double aBldgValue) {
		bldgValue = aBldgValue;
	}

	/**
	 * @param string
	 */
	public void setCurrentAssetsParticulars(String aCurrentAssetsParticulars) {
		currentAssetsParticulars = aCurrentAssetsParticulars;
	}

	/**
	 * @param d
	 */
	public void setCurrentAssetsValue(double aCurrentAssetsValue) {
		currentAssetsValue = aCurrentAssetsValue;
	}

	/**
	 * @param d
	 */
	public void setLandValue(double aLandValue) {
		landValue = aLandValue;
	}

	/**
	 * @param string
	 */
	public void setMachineParticulars(String aMachineParticulars) {
		machineParticulars = aMachineParticulars;
	}

	/**
	 * @param d
	 */
	public void setMachineValue(double aMachineValue) {
		machineValue = aMachineValue;
	}

	/**
	 * @param string
	 */
	public void setOthersParticulars(String aOthersParticulars) {
		othersParticulars = aOthersParticulars;
	}

	/**
	 * @param d
	 */
	public void setOthersValue(double aOthersValue) {
		othersValue = aOthersValue;
	}

	/**
	 * @return
	 */
	public String getLandParticulars() {
		return landParticulars;
	}

	/**
	 * @param string
	 */
	public void setLandParticulars(String aLandParticulars) {
		landParticulars = aLandParticulars;
	}

}
