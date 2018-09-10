package org.parser.fxtrend.exception;

import org.parser.fxtrend.beans.FxtAccountsData;

public class AccountsDataNotAddedToDatabase extends Exception {
	private static final long serialVersionUID = 4827264618014954637L;
	private FxtAccountsData notAddedData;

	public AccountsDataNotAddedToDatabase(FxtAccountsData data) {
		notAddedData = data;
	}

	public FxtAccountsData getNotAddedData() {
		return notAddedData;
	}

	public void setNotAddedData(FxtAccountsData notAddedData) {
		this.notAddedData = notAddedData;
	}
}