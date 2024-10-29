package com.josmejia2401.utils;

public enum Status {
	INICIADA("INICIADA", 1), EN_PROCESP("EN_PROCESO", 2), TERMINADA("TERMINADA", 4);

	private String name;
	private int id;

	private Status(String name, int id) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public static Status findById(int id) {
		for (Status v : values()) {
			if (v.getId() == id) {
				return v;
			}
		}
		return null;
	}

}
