package id.kelompok04.doize.model.response;

import java.util.List;

import id.kelompok04.doize.model.DailyActivity;

public class ListDailyActivityResponse{
	private List<DailyActivity> data;
	private String message;
	private int status;

	public void setData(List<DailyActivity> data){
		this.data = data;
	}

	public List<DailyActivity> getData(){
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
			"ListDailyActivityResponse{" + 
			"data = '" + data + '\'' + 
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}