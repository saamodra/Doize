package id.kelompok04.doize.model;

import com.google.gson.annotations.SerializedName;

public class PomodoroActivity {

	@SerializedName("activity_name")
	private String activityName;

	@SerializedName("id_pomodoro")
	private int idPomodoro;

	@SerializedName("working_status")
	private int workingStatus;

	@SerializedName("id_pomodoro_activity")
	private int idPomodoroActivity;

	public void setActivityName(String activityName){
		this.activityName = activityName;
	}

	public String getActivityName(){
		return activityName;
	}

	public void setIdPomodoro(int idPomodoro){
		this.idPomodoro = idPomodoro;
	}

	public int getIdPomodoro(){
		return idPomodoro;
	}

	public void setWorkingStatus(int workingStatus){
		this.workingStatus = workingStatus;
	}

	public int getWorkingStatus(){
		return workingStatus;
	}

	public void setIdPomodoroActivity(int idPomodoroActivity){
		this.idPomodoroActivity = idPomodoroActivity;
	}

	public int getIdPomodoroActivity(){
		return idPomodoroActivity;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"activity_name = '" + activityName + '\'' + 
			",id_pomodoro = '" + idPomodoro + '\'' + 
			",working_status = '" + workingStatus + '\'' + 
			",id_pomodoro_activity = '" + idPomodoroActivity + '\'' + 
			"}";
		}
}