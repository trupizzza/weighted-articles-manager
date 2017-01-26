package com.onegolabs.wamanager.model;

/**
 * For testing purposes
 *
 * @author dmzhg
 */
public class TempData {

	private int code;
	private String name;
	private String description;

	public TempData(int code, String name, String description) {
		this.code = code;
		this.name = name;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "Name : " + this.name + "\r\nCode: " + this.code + "\r\nDescription: " + this.description;
	}
}
