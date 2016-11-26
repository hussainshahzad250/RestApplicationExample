package com.truxapiv2.model;

public class S3SignedURL {

	private String bucket;
	private String path;
	public String getBucket() {
		return bucket;
	}
	public void setBucket(String bucket) {
		this.bucket = bucket;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	@Override
	public String toString() {
		return "S3SignedURL [bucket=" + bucket + ", path=" + path + "]";
	}
	
}
