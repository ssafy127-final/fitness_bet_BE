package com.fitnessbet.mission.model.dto;

public class Mission {
	private int id; // 미션 번호
	private String content; // 운동 종목
	private int maleMin; // 남자 최소 개수
	private int maleMax; // 남자 최대 개수
	private int femaleMin; // 여자 최소 개수
	private int femaleMax; // 여자 최대 개수

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getMaleMin() {
		return maleMin;
	}

	public void setMaleMin(int maleMin) {
		this.maleMin = maleMin;
	}

	public int getMaleMax() {
		return maleMax;
	}

	public void setMaleMax(int maleMax) {
		this.maleMax = maleMax;
	}

	public int getFemaleMin() {
		return femaleMin;
	}

	public void setFemaleMin(int femaleMin) {
		this.femaleMin = femaleMin;
	}

	public int getFemaleMax() {
		return femaleMax;
	}

	public void setFemaleMax(int femaleMax) {
		this.femaleMax = femaleMax;
	}

	@Override
	public String toString() {
		return "Mission [id=" + id + ", content=" + content + ", maleMin=" + maleMin + ", maleMax=" + maleMax
				+ ", femaleMin=" + femaleMin + ", femaleMax=" + femaleMax + "]";
	}

}
