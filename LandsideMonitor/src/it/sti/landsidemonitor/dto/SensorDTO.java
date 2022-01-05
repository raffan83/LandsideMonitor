package it.sti.landsidemonitor.dto;

import java.awt.Point;

public class SensorDTO {
	
	private int id;
	private int stato;
	private int statoOriginale;
	private String identifier;
	private String type;
	private Point point;
	private boolean active;
	
	private String bering="N/D";
	private String pitch="N/D";
	private String roll="N/D";
	private String battLevel="N/D";
	private String signal="N/D";
	
	private double tempo_periodo_5_sec=0;
	private double tempo_periodo_3_sec=0;
	private double tempo_periodo_2_sec=0;
	
	
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
	public void setStatoOriginale(int statoOriginale) 
	{
		this.statoOriginale = statoOriginale;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getTempo_periodo_5_sec() {
		return tempo_periodo_5_sec;
	}
	public void setTempo_periodo_5_sec(double tempo_periodo_5_sec) {
		this.tempo_periodo_5_sec = tempo_periodo_5_sec;
	}
	public double getTempo_periodo_3_sec() {
		return tempo_periodo_3_sec;
	}
	public void setTempo_periodo_3_sec(double tempo_periodo_3_sec) {
		this.tempo_periodo_3_sec = tempo_periodo_3_sec;
	}
	public double getTempo_periodo_2_sec() {
		return tempo_periodo_2_sec;
	}
	public void setTempo_periodo_2_sec(double tempo_periodo_2_sec) {
		this.tempo_periodo_2_sec = tempo_periodo_2_sec;
	}
	public String getBering() {
		return bering;
	}
	public void setBering(String bering) {
		this.bering = bering;
	}
	public String getPitch() {
		return pitch;
	}
	public void setPitch(String pitch) {
		this.pitch = pitch;
	}
	public String getRoll() {
		return roll;
	}
	public void setRoll(String roll) {
		this.roll = roll;
	}
	public String getBattLevel() {
		return battLevel;
	}
	public void setBattLevel(String battLevel) {
		this.battLevel = battLevel;
	}
	public String getSignal() {
		return signal;
	}
	public void setSignal(String signal) {
		this.signal = signal;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}

	
}
