package com.josmejia2401.utils;

public enum Status {
	CONFIRMADA("confirmada", 1L), CANCELADA("cancelada", 2L);

	private String name;
	private Long id;

	private Status(String name, Long id) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public Long getId() {
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
