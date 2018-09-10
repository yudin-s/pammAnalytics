package org.parser.fxtrend.exception;

public class ParseListPageError extends Exception {

	private static final long serialVersionUID = 7784178582991010253L;

	public String detail;

	public ParseListPageError(String detail) {
		this.detail = detail;
	}

}