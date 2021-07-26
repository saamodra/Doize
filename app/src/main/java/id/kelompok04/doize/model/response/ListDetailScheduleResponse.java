package id.kelompok04.doize.model.response;

import com.google.gson.annotations.SerializedName;

public class ListDetailScheduleResponse {

	@SerializedName("data")
	private ListDayScheduleResponse data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setData(ListDayScheduleResponse data){
		this.data = data;
	}

	public ListDayScheduleResponse getData(){
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
			"DetailScheduleResponse{" + 
			"data = '" + data + '\'' + 
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}