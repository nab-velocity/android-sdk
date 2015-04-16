package com.velocity.model.transactions.query.response;

import com.velocity.enums.ApplicationLocation;
import com.velocity.enums.EncryptionType;
import com.velocity.enums.HardwareType;
import com.velocity.enums.PINCapability;
import com.velocity.enums.ReadCapability;
import com.velocity.gson.annotations.SerializedName;



/**
 * @author ranjitk
 *
 */
public class ApplicationData {

	@SerializedName("ApplicationAttended")
	private boolean applicationAttended;
	@SerializedName("ApplicationName")
	private String applicationName;
	@SerializedName("DeveloperId")
	private String developerId;
	@SerializedName("DeviceSerialNumber")
	private String deviceSerialNumber;
	@SerializedName("PTLSSocketId")
	private String pTLSSocketId;
	@SerializedName("SerialNumber")
	private String serialNumber;
	@SerializedName("SoftwareVersion")
	private String softwareVersion;
	@SerializedName("SoftwareVersionDate")
	private String softwareVersionDate;
	@SerializedName("VendorId")
	private String  vendorId;
	@SerializedName("ReadCapability")
	private ReadCapability readCapability;
	@SerializedName("PINCapability")
	private PINCapability pINCapability;
	@SerializedName("HardwareType")
	private HardwareType hardwareType;
	@SerializedName("EncryptionType")
	private EncryptionType encryptionType;
	@SerializedName("ApplicationLocation")
	private ApplicationLocation applicationLocation;
	public boolean isApplicationAttended() {
		return applicationAttended;
	}
	public void setApplicationAttended(boolean applicationAttended) {
		this.applicationAttended = applicationAttended;
	}
	public String getApplicationName() {
		return applicationName;
	}
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	public String getDeveloperId() {
		return developerId;
	}
	public void setDeveloperId(String developerId) {
		this.developerId = developerId;
	}
	public String getDeviceSerialNumber() {
		return deviceSerialNumber;
	}
	public void setDeviceSerialNumber(String deviceSerialNumber) {
		this.deviceSerialNumber = deviceSerialNumber;
	}
	public String getpTLSSocketId() {
		return pTLSSocketId;
	}
	public void setpTLSSocketId(String pTLSSocketId) {
		this.pTLSSocketId = pTLSSocketId;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getSoftwareVersion() {
		return softwareVersion;
	}
	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}
	public String getSoftwareVersionDate() {
		return softwareVersionDate;
	}
	public void setSoftwareVersionDate(String softwareVersionDate) {
		this.softwareVersionDate = softwareVersionDate;
	}
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public ReadCapability getReadCapability() {
		return readCapability;
	}
	public void setReadCapability(ReadCapability readCapability) {
		this.readCapability = readCapability;
	}
	public PINCapability getpINCapability() {
		return pINCapability;
	}
	public void setpINCapability(PINCapability pINCapability) {
		this.pINCapability = pINCapability;
	}
	public HardwareType getHardwareType() {
		return hardwareType;
	}
	public void setHardwareType(HardwareType hardwareType) {
		this.hardwareType = hardwareType;
	}
	public EncryptionType getEncryptionType() {
		return encryptionType;
	}
	public void setEncryptionType(EncryptionType encryptionType) {
		this.encryptionType = encryptionType;
	}
	public ApplicationLocation getApplicationLocation() {
		return applicationLocation;
	}
	public void setApplicationLocation(ApplicationLocation applicationLocation) {
		this.applicationLocation = applicationLocation;
	} 
	
	
}
