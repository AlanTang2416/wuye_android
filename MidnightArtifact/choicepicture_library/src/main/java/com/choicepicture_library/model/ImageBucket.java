package com.choicepicture_library.model;

import java.util.List;

public class ImageBucket {
	public int count = 0;
	public String bucketName;
	public List<ImageItem> imageList;

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<ImageItem> getImageList() {
		return imageList;
	}

	public void setImageList(List<ImageItem> imageList) {
		this.imageList = imageList;
	}
}
