package id.kelompok04.doize.model;

public class DailyActivity {
	private int idDailyActivity;
	private String creadate;
	private String reminderAt;
	private String descriptionDailyActivity;
	private String nameDailyActivity;
	private int workingStatus;
	private int idUser;
	private String modidate;
	private int priority;
	private String duedateDailyActivity;
	private int status;

	public void setIdDailyActivity(int idDailyActivity){
		this.idDailyActivity = idDailyActivity;
	}

	public int getIdDailyActivity(){
		return idDailyActivity;
	}

	public void setCreadate(String creadate){
		this.creadate = creadate;
	}

	public String getCreadate(){
		return creadate;
	}

	public void setReminderAt(String reminderAt){
		this.reminderAt = reminderAt;
	}

	public String getReminderAt(){
		return reminderAt;
	}

	public void setDescriptionDailyActivity(String descriptionDailyActivity){
		this.descriptionDailyActivity = descriptionDailyActivity;
	}

	public String getDescriptionDailyActivity(){
		return descriptionDailyActivity;
	}

	public void setNameDailyActivity(String nameDailyActivity){
		this.nameDailyActivity = nameDailyActivity;
	}

	public String getNameDailyActivity(){
		return nameDailyActivity;
	}

	public void setWorkingStatus(int workingStatus){
		this.workingStatus = workingStatus;
	}

	public int getWorkingStatus(){
		return workingStatus;
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

	public void setPriority(int priority){
		this.priority = priority;
	}

	public int getPriority(){
		return priority;
	}

	public void setDuedateDailyActivity(String duedateDailyActivity){
		this.duedateDailyActivity = duedateDailyActivity;
	}

	public String getDuedateDailyActivity(){
		return duedateDailyActivity;
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
			"DataItem{" + 
			"id_daily_activity = '" + idDailyActivity + '\'' + 
			",creadate = '" + creadate + '\'' + 
			",reminder_at = '" + reminderAt + '\'' + 
			",description_daily_activity = '" + descriptionDailyActivity + '\'' + 
			",name_daily_activity = '" + nameDailyActivity + '\'' + 
			",working_status = '" + workingStatus + '\'' + 
			",id_user = '" + idUser + '\'' + 
			",modidate = '" + modidate + '\'' + 
			",priority = '" + priority + '\'' + 
			",duedate_daily_activity = '" + duedateDailyActivity + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}
