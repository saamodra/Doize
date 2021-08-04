package id.kelompok04.doize.model.response;

import com.google.gson.annotations.SerializedName;

import id.kelompok04.doize.model.Pomodoro;

public class PomodoroResponse{

	@SerializedName("data")
	private Pomodoro data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setData(Pomodoro data){
		this.data = data;
	}

	public Pomodoro getData(){
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
			"PomodoroResponse{" + 
			"data = '" + data + '\'' + 
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}