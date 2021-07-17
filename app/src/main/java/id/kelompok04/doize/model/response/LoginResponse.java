package id.kelompok04.doize.model.response;

import com.google.gson.annotations.SerializedName;

public class LoginResponse{

	@SerializedName("data")
	private TokenResponse mTokenResponse;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
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