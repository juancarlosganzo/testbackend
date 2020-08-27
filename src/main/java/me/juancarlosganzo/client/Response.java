package me.juancarlosganzo.client;

public class Response {
	
	private int responseCode;
	private String description;
	private String elapsedTime;
	private ResponseResult result;
	
	
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getElapsedTime() {
		return elapsedTime;
	}
	public void setElapsedTime(String elapsedTime) {
		this.elapsedTime = elapsedTime;
	}
	public ResponseResult getResult() {
		return result;
	}
	public void setResult(ResponseResult result) {
		this.result = result;
	}
	

}
