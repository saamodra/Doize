package id.kelompok04.doize.model.response;

import com.google.gson.annotations.SerializedName;

import id.kelompok04.doize.model.DetailSchedule;

public class DetailScheduleResponse{

	@SerializedName("data")
	private DetailSchedule detailSchedule;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public DetailSchedule getDetailSchedule() {
		return detailSchedule;
	}

	public void setDetailSchedule(DetailSchedule detailSchedule) {
		this.detailSchedule = detailSchedule;
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
			"data = '" + detailSchedule + '\'' +
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}