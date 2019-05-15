package it.polito.tdp.borders.model;

public class Border {

	private Country State1;
	private Country State2;
	private int conttype;
	
	public Border(Country state1, Country state2, int conttype) {
		super();
		State1 = state1;
		State2 = state2;
		this.conttype = conttype;
	}

	public Country getState1() {
		return State1;
	}

	public void setState1(Country state1) {
		State1 = state1;
	}

	public Country getState2() {
		return State2;
	}

	public void setState2(Country state2) {
		State2 = state2;
	}

	public int getConttype() {
		return conttype;
	}

	public void setConttype(int conttype) {
		this.conttype = conttype;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((State1 == null) ? 0 : State1.hashCode());
		result = prime * result + ((State2 == null) ? 0 : State2.hashCode());
		result = prime * result + conttype;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Border other = (Border) obj;
		if (State1 == null) {
			if (other.State1 != null)
				return false;
		} else if (!State1.equals(other.State1))
			return false;
		if (State2 == null) {
			if (other.State2 != null)
				return false;
		} else if (!State2.equals(other.State2))
			return false;
		if (conttype != other.conttype)
			return false;
		return true;
	}
	
	
}
