package com.ELSE.model;

import java.io.Serializable;
import java.time.Year;

public class BookMetadata implements Serializable {
	private static final long serialVersionUID = 1L;
	private Year anno;
	private String autore;
	private String checksum;
	// Warning: The field is transient but isn't set by deserialization
	// private transient String percorso; // Do I really need it here?
	private int npagine;
	private String titolo;

	public Year getAnno() {
		return anno;
	}

	public String getAutore() {
		return autore;
	}

	public String getChecksum() {
		return checksum;
	}

	public int getNpagine() {
		return npagine;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setAnno(Year anno) {
		this.anno = anno;
	}

	public void setAutore(String autore) {
		this.autore = autore;
	}

	public void setChecksum(String string) {
		this.checksum = string;
	}

	public void setNpagine(int npagine) {
		this.npagine = npagine;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	@Override
	public String toString() {
		return checksum + ":" + titolo + ":" + autore + ":" + anno + ":" + npagine;
	}
}
