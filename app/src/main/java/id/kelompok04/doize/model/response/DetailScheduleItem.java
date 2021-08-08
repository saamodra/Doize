package id.kelompok04.doize.model.response;

import com.google.gson.annotations.SerializedName;

public class DetailScheduleItem{

	@SerializedName("start_time")
	private String startTime;

	@SerializedName("id_schedule")
	private int idSchedule;

	@SerializedName("creadate")
	private String creadate;

	@SerializedName("name_detail_schedule")
	private String nameDetailSchedule;

	@SerializedName("day_schedule")
	private String daySchedule;

	@SerializedName("end_time")
	private String endTime;

	@SerializedName("id_detail_schedule")
	private int idDetailSchedule;

	@SerializedName("modidate")
	private String modidate;

	public void setStartTime(String startTime){
		this.startTime = startTime;
	}

	public String getStartTime(){
		return startTime;
	}

	public void setIdSchedule(int idSchedule){
		this.idSchedule = idSchedule;
	}

	public int getIdSchedule(){
		return idSchedule;
	}

	public void setCreadate(String creadate){
		this.creadate = creadate;
	}

	public String getCreadate(){
		return creadate;
	}

	public void setNameDetailSchedule(String nameDetailSchedule){
		this.nameDetailSchedule = nameDetailSchedule;
	}

	public String getNameDetailSchedule(){
		return nameDetailSchedule;
	}

	public void setDaySchedule(String daySchedule){
		this.daySchedule = daySchedule;
	}

	public String getDaySchedule(){
		return daySchedule;
	}

	public void setEndTime(String endTime){
		this.endTime = endTime;
	}

	public String getEndTime(){
		return endTime;
	}

	public void setIdDetailSchedule(int idDetailSchedule){
		this.idDetailSchedule = idDetailSchedule;
	}

	public int getIdDetailSchedule(){
		return idDetailSchedule;
	}

	public void setModidate(String modidate){
		this.modidate = modidate;
	}

	public String getModidate(){
		return modidate;
	}

	@Override
 	public String toString(){
		return 
			"DetailScheduleItem{" + 
			"start_time = '" + startTime + '\'' + 
			",id_schedule = '" + idSchedule + '\'' + 
			",creadate = '" + creadate + '\'' + 
			",name_detail_schedule = '" + nameDetailSchedule + '\'' + 
			",day_schedule = '" + daySchedule + '\'' + 
			",end_time = '" + endTime + '\'' + 
			",id_detail_schedule = '" + idDetailSchedule + '\'' + 
			",modidate = '" + modidate + '\'' + 
			"}";
		}
}