package org.parser.utilites;

public class Pair<F, L> {

	private F first;
	private L last;

	public Pair(F first, L last) {
		setFirst(first);
		setLast(last);
	}

	public Pair() {

	}

	public F getFirst() {
		return first;
	}

	public void setFirst(F first) {
		this.first = first;
	}

	public L getLast() {
		return last;
	}

	public void setLast(L last) {
		this.last = last;
	}
}
