package id.kelompok04.doize.model.response;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("id_schedule")
	@Expose
	private int idSchedule;

	@SerializedName("creadate")
	@Expose
	private String creadate;

	@SerializedName("name_schedule")
	@Expose
	private String nameSchedule;

	@SerializedName("id_user")
	@Expose
	private int idUser;

	@SerializedName("modidate")
	@Expose
	private String modidate;

	@SerializedName("detail_schedule")
	@Expose
	private List<DetailScheduleItem> detailSchedule;

	@SerializedName("description_schedule")
	@Expose
	private String descriptionSchedule;

	@SerializedName("status")
	@Expose
	private int status;

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

	public void setNameSchedule(String nameSchedule){
		this.nameSchedule = nameSchedule;
	}

	public String getNameSchedule(){
		return nameSchedule;
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

	public void setDetailSchedule(List<DetailScheduleItem> detailSchedule){
		this.detailSchedule = detailSchedule;
	}

	public List<DetailScheduleItem> getDetailSchedule(){
		return detailSchedule;
	}

	public void setDescriptionSchedule(String descriptionSchedule){
		this.descriptionSchedule = descriptionSchedule;
	}

	public String getDescriptionSchedule(){
		return descriptionSchedule;
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
			"id_schedule = '" + idSchedule + '\'' + 
			",creadate = '" + creadate + '\'' + 
			",name_schedule = '" + nameSchedule + '\'' + 
			",id_user = '" + idUser + '\'' + 
			",modidate = '" + modidate + '\'' + 
			",detail_schedule = '" + detailSchedule + '\'' + 
			",description_schedule = '" + descriptionSchedule + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}