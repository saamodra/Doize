package id.kelompok04.doize.model.response;

import com.google.gson.annotations.SerializedName;

import id.kelompok04.doize.model.DailyActivity;

public class DailyActivityResponse{

	@SerializedName("data")
	private DailyActivity data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setData(DailyActivity data){
		this.data = data;
	}

	public DailyActivity getData(){
		return data;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"DailyActivityResponse{" + 
			"data = '" + data + '\'' + 
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}