package id.kelompok04.doize.model.response;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import id.kelompok04.doize.model.User;

public class ListUserResponse{

	@SerializedName("data")
	@Expose
	private List<User> data;

	@SerializedName("message")
	@Expose
	private String message;

	@SerializedName("status")
	@Expose
	private int status;

	public void setData(List<User> data){
		this.data = data;
	}

	public List<User> getData(){
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
			"ListUserResponse{" + 
			"data = '" + data + '\'' + 
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}