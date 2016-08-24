package com.choicepicture_library.model;

public class SelectImageItem {

	private String drr_or;
	private String drr_sle;
	private String image_id;

	public SelectImageItem (){
	}

	public SelectImageItem (String drr_or, String drr_sle, String image_id){
		this.drr_or = drr_or;
		this.drr_sle = drr_sle;
		this.image_id = image_id;
	}

	public String getImage_id() {
		return image_id;
	}

	public void setImage_id(String image_id) {
		this.image_id = image_id;
	}

	public String getDrr_or() {
		return drr_or;
	}

	public void setDrr_or(String drr_or) {
		this.drr_or = drr_or;
	}

	public String getDrr_sle() {
		return drr_sle;
	}

	public void setDrr_sle(String drr_sle) {
		this.drr_sle = drr_sle;
	}
	
}
