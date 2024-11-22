package com.fitnessbet.product.model.dto;

public class Product {
	
	private int id;
	private String name;
	private int price;
	private String img;
	private int delYn;
	private int point;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public int getDelYn() {
		return delYn;
	}
	public void setDelYn(int delYn) {
		this.delYn = delYn;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price + ", img=" + img + ", delYn=" + delYn
				+ ", point=" + point + "]";
	}
	
	

}
