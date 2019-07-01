package it.sti.landsidemonitor.dto;

import java.awt.Point;

public class SensorDTO {
	
	private int id;
	private int stato;
	private int statoOriginale;
	private String identifier;
	private Point point;
	
	
	
	public SensorDTO(String _id, int x, int y) {
		
		identifier=_id;
		point= new Point(x, y);
		
	}
	public SensorDTO() {
		
	}

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStato() {
		return stato;
	}
	public void setStato(int stato) {
		this.stato = stato;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public Point getPoint() {
		return point;
	}
	public void setPoint(Point point) {
		this.point = point;
	}
	public int getStatoOriginale() {
		return statoOriginale;
	}
	public void setStatoOriginale(int statoOriginale) {
		this.statoOriginale = statoOriginale;
	}


}
