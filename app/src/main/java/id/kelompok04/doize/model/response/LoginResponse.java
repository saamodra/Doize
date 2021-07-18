package id.kelompok04.doize.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import id.kelompok04.doize.model.User;

public class LoginResponse{

	@SerializedName("data")
	@Expose
	private User mUser;

	@SerializedName("message")
	@Expose
	private String message;

	@SerializedName("status")
	@Expose
	private int status;

	public User getUser() {
		return mUser;
	}

	public void setUser(User user) {
		mUser = user;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "LoginResponse{" +
				"mUser=" + mUser +
				", message='" + message + '\'' +
				", status=" + status +
				'}';
	}
}