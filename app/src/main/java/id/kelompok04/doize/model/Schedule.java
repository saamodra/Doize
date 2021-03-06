package id.kelompok04.doize.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Schedule {

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
	private List<DetailSchedule> detailSchedule;

	@SerializedName("description_schedule")
	@Expose
	private String descriptionSchedule;

	@SerializedName("status")
	@Expose
	private int status;


	public Schedule() {

	}

	public Schedule(String nameSchedule, String descriptionSchedule, int idUser) {
		this.nameSchedule = nameSchedule;
		this.descriptionSchedule = descriptionSchedule;
		this.idUser = idUser;
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

	public void setDetailSchedule(List<DetailSchedule> detailSchedule){
		this.detailSchedule = detailSchedule;
	}

	public List<DetailSchedule> getDetailSchedule(){
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