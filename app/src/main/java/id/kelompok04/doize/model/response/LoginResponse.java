package id.kelompok04.doize.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse{

	@SerializedName("data")
	@Expose
	private TokenResponse mTokenResponse;

	@SerializedName("message")
	@Expose
	private String message;

	@SerializedName("status")
	@Expose
	private int status;

	public void setData(TokenResponse tokenResponse){
		this.mTokenResponse = tokenResponse;
	}

	public TokenResponse getData(){
		return mTokenResponse;
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
			"LoginResponse{" + 
			"data = '" + mTokenResponse + '\'' +
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}