package it.polito.tdp.borders.model;

public class Country {

	private int code;
	private String abbName;
	private String name;
	private int grado;
	
	public Country(int code, String abbName, String name) {
		super();
		this.code = code;
		this.abbName = abbName;
		this.name = name;
		grado=0;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getAbbName() {
		return abbName;
	}

	public void setAbbName(String abbName) {
		this.abbName = abbName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getGrado() {
		return this.grado;
	}
	
	public void setGrado(int grado) {
		this.grado = grado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + code;
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
		Country other = (Country) obj;
		if (code != other.code)
			return false;
		return true;
	}
	
	
}
