package de.mhettig.playstoremockup.model;

public enum Permission {
	NETWORK,				//FULL_NETWORK_ACCESS
	WIFI,					//ACCESS_WIFI_STATE
	STORAGE,				//EXTERNAL_STORAGE
	PHONE,					//READ_PHONE_STATUS
	ACCOUNTS,				//FIND_ACCOUNTS
	LOCATION,				//LOCATION
	CONTACTS,				//READ_CONTACTS
	CALL_LOG,				//READ_CALL_LOG
	PICTURES,				//TAKE_PICTURES
	APPS;					

	public String getCaption() {
		switch (this) {
			case NETWORK:   return "Network communication";
			case WIFI:		return "Network communication";
			case STORAGE:   return "Storage";
			case PHONE:  	return "Phone state";
			case ACCOUNTS:  return "Your accounts";
			case LOCATION:  return "Your location";
			case CONTACTS:  return "Your personal information";
			case CALL_LOG:  return "Your personal information";
			case PICTURES:  return "Hardware controls";
			case APPS:		return "System tools";
			
		}
		return "";
	}
	
	public String getDescription() {
		switch (this) {
			case NETWORK:   return "Full Internet access";
			case WIFI:		return "View Wi-Fi state, view network state";
			case STORAGE:   return "Modify/delete SD card contents";
			case PHONE:		return "Read phone state and identity";
			case ACCOUNTS:	return "Find accounts on the device, read Google service configuration";
			case LOCATION:  return "Coarse (network-based) location, fine (GPS) location";
			case CONTACTS:  return "Read contact data, read your own contact card, write contact data";
			case CALL_LOG:  return "Read call log, write call log";
			case PICTURES:  return "Record audio, take pictures";
			case APPS:		return "Retrieve running applications";
		}
		return "";
	}
}
