package id.kelompok04.doize.model.response;

import com.google.gson.annotations.SerializedName;

import id.kelompok04.doize.model.User;

public class UserResponse{

	@SerializedName("data")
	private User user;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setData(User user){
		this.user = user;
	}

	public User getData(){
		return user;
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
			"UserResponse{" + 
			"data = '" + user + '\'' +
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}