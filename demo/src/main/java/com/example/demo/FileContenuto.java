package com.example.demo;

public class FileContenuto {

	private final long id;
	private final String fileName;
	private final String contenuto;

	public FileContenuto(long id, String fileName, String contenuto) {
		this.id = id;
		this.fileName = fileName;
		this.contenuto = contenuto;

	}

	public long getId() {
		return id;
	}

	public String getFileName() {
		return fileName;
	}

	public String getContenuto() {
		return contenuto;
	}
}
