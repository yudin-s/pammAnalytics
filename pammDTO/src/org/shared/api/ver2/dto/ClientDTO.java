package org.shared.api.ver2.dto;

import java.io.Serializable;

public class ClientDTO implements Serializable {

	private static final long serialVersionUID = -512108120679519619L;
	private int apiVer;

	public int getApiVer() {
		return apiVer;
	}

	public void setApiVer(int apiVer) {
		this.apiVer = apiVer;
	}

}
