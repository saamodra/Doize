package id.kelompok04.doize.model.response;

import com.google.gson.annotations.SerializedName;

import id.kelompok04.doize.model.Schedule;

public class ScheduleResponse{

	@SerializedName("data")
	private Schedule data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setData(Schedule data){
		this.data = data;
	}

	public Schedule getData(){
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
			"ScheduleResponse {" +
			"data = '" + data + '\'' + 
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}