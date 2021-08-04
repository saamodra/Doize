package id.kelompok04.doize.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pomodoro {

	@SerializedName("short_break")
	@Expose
	private String shortBreak;

	@SerializedName("saved_time")
	@Expose
	private String savedTime;

	@SerializedName("id_pomodoro")
	@Expose
	private int idPomodoro;

	@SerializedName("count")
	@Expose
	private int count;

	@SerializedName("id_user")
	@Expose
	private int idUser;

	@SerializedName("modidate")
	@Expose
	private String modidate;

	@SerializedName("long_break")
	@Expose
	private String longBreak;

	@SerializedName("productivity_time")
	@Expose
	private String productivityTime;

	public void setShortBreak(String shortBreak){
		this.shortBreak = shortBreak;
	}

	public String getShortBreak(){
		return shortBreak;
	}

	public void setSavedTime(String savedTime){
		this.savedTime = savedTime;
	}

	public String getSavedTime(){
		return savedTime;
	}

	public void setIdPomodoro(int idPomodoro){
		this.idPomodoro = idPomodoro;
	}

	public int getIdPomodoro(){
		return idPomodoro;
	}

	public void setCount(int count){
		this.count = count;
	}

	public int getCount(){
		return count;
	}

	public void setIdUser(int idUser){
		this.idUser = idUser;
	}

	public int getIdUser(){
		return idUser;
	}

	public void setModidate(String modidate){
		this.modidate = modidate;
	}

	public String getModidate(){
		return modidate;
	}

	public void setLongBreak(String longBreak){
		this.longBreak = longBreak;
	}

	public String getLongBreak(){
		return longBreak;
	}

	public void setProductivityTime(String productivityTime){
		this.productivityTime = productivityTime;
	}

	public String getProductivityTime(){
		return productivityTime;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"short_break = '" + shortBreak + '\'' + 
			",saved_time = '" + savedTime + '\'' + 
			",id_pomodoro = '" + idPomodoro + '\'' + 
			",count = '" + count + '\'' + 
			",id_user = '" + idUser + '\'' + 
			",modidate = '" + modidate + '\'' + 
			",long_break = '" + longBreak + '\'' + 
			",productivity_time = '" + productivityTime + '\'' + 
			"}";
		}
}