package id.kelompok04.doize.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PomodoroActivity {

	@SerializedName("activity_name")
	@Expose
	private String activityName;

	@SerializedName("id_pomodoro")
	@Expose
	private int idPomodoro;

	@SerializedName("working_status")
	@Expose
	private int workingStatus;

	@SerializedName("id_pomodoro_activity")
	@Expose
	private int idPomodoroActivity;

	@SerializedName("status")
	@Expose
	private int status;

	public PomodoroActivity() {
	}

	public PomodoroActivity(String activityName, int idPomodoro, int workingStatus) {
		this.activityName = activityName;
		this.idPomodoro = idPomodoro;
		this.workingStatus = workingStatus;
	}

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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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