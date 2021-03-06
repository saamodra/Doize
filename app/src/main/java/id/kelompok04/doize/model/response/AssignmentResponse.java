package id.kelompok04.doize.model.response;

import com.google.gson.annotations.SerializedName;

import id.kelompok04.doize.model.Assignment;

public class AssignmentResponse{

	@SerializedName("data")
	private Assignment data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setAssignment(Assignment data){
		this.data = data;
	}

	public Assignment getAssignment(){
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
			"AssignmentResponse{" + 
			"data = '" + data + '\'' + 
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}