package id.kelompok04.doize.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Assignment {

	@SerializedName("description_assignment")
	@Expose
	private String descriptionAssignment;

	@SerializedName("duedate_assignment")
	@Expose
	private String duedateAssignment;

	@SerializedName("creadate")
	@Expose
	private String creadate;

	@SerializedName("reminder_at")
	@Expose
	private String reminderAt;

	@SerializedName("working_status")
	@Expose
	private int workingStatus;

	@SerializedName("course")
	@Expose
	private String course;

	@SerializedName("name_assignment")
	@Expose
	private String nameAssignment;

	@SerializedName("id_user")
	@Expose
	private int idUser;

	@SerializedName("modidate")
	@Expose
	private String modidate;

	@SerializedName("priority")
	@Expose
	private int priority;

	@SerializedName("id_assignment")
	@Expose
	private int idAssignment;

	@SerializedName("status")
	@Expose
	private int status;

	public void setDescriptionAssignment(String descriptionAssignment){
		this.descriptionAssignment = descriptionAssignment;
	}

	public String getDescriptionAssignment(){
		return descriptionAssignment;
	}

	public void setDuedateAssignment(String duedateAssignment){
		this.duedateAssignment = duedateAssignment;
	}

	public String getDuedateAssignment(){
		return duedateAssignment;
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

	public void setWorkingStatus(int workingStatus){
		this.workingStatus = workingStatus;
	}

	public int getWorkingStatus(){
		return workingStatus;
	}

	public void setCourse(String course){
		this.course = course;
	}

	public String getCourse(){
		return course;
	}

	public void setNameAssignment(String nameAssignment){
		this.nameAssignment = nameAssignment;
	}

	public String getNameAssignment(){
		return nameAssignment;
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

	public void setIdAssignment(int idAssignment){
		this.idAssignment = idAssignment;
	}

	public int getIdAssignment(){
		return idAssignment;
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
			"description_assignment = '" + descriptionAssignment + '\'' + 
			",duedate_assignment = '" + duedateAssignment + '\'' + 
			",creadate = '" + creadate + '\'' + 
			",reminder_at = '" + reminderAt + '\'' + 
			",working_status = '" + workingStatus + '\'' + 
			",course = '" + course + '\'' + 
			",name_assignment = '" + nameAssignment + '\'' + 
			",id_user = '" + idUser + '\'' + 
			",modidate = '" + modidate + '\'' + 
			",priority = '" + priority + '\'' + 
			",id_assignment = '" + idAssignment + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}