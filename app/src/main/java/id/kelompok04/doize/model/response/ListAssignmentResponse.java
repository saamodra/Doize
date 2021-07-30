package id.kelompok04.doize.model.response;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import id.kelompok04.doize.model.Assignment;

public class ListAssignmentResponse{

	@SerializedName("data")
	private List<Assignment> data;

	@SerializedName("status")
	@Expose
	private int status;

	@SerializedName("message")
	private String message;

	public void setData(List<Assignment> data){
		this.data = data;
	}

	public List<Assignment> getData(){
		return data;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"ListAssignmentResponse{" + 
			"data = '" + data + '\'' + 
			",status = '" + status + '\'' +
			",message = '" + message + '\'' + 
			"}";
		}
}