package id.kelompok04.doize.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import id.kelompok04.doize.model.User;

public class TokenResponse {

	@SerializedName("token")
	@Expose
	private String token;

	@SerializedName("user")
	@Expose
	private User user;

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "TokenResponse{" +
				"token='" + token + '\'' +
				", user=" + user +
				'}';
	}
}