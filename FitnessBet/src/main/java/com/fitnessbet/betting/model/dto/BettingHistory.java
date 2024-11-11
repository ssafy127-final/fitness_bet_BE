package com.fitnessbet.betting.model.dto;

public class BettingHistory {
	
	private int id;
	private String player;
	private int bettingId;
	private int point;
	private int choice;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPlayer() {
		return player;
	}
	public void setPlayer(String player) {
		this.player = player;
	}
	public int getBettingId() {
		return bettingId;
	}
	public void setBettingId(int bettingId) {
		this.bettingId = bettingId;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public int getChoice() {
		return choice;
	}
	public void setChoice(int choice) {
		this.choice = choice;
	}
	
	

}
