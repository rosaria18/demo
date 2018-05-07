package com.example.demo;

public class FileContent {

	private final long id;
	private final String fileName;

	public FileContent(long id, String fileName) {
		this.id = id;
		this.fileName = fileName;
	}

	public long getId() {
		return id;
	}

	public String getFileName() {
		return fileName;
	}
}
