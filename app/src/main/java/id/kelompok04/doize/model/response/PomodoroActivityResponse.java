package id.kelompok04.doize.model.response;

import com.google.gson.annotations.SerializedName;

import id.kelompok04.doize.model.PomodoroActivity;

public class PomodoroActivityResponse{

	@SerializedName("data")
	private PomodoroActivity data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setData(PomodoroActivity data){
		this.data = data;
	}

	public PomodoroActivity getData(){
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
			"PomodoroActivityResponse{" + 
			"data = '" + data + '\'' + 
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}