package id.kelompok04.doize.model.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

import id.kelompok04.doize.model.DetailSchedule;

public class ListDayScheduleResponse {

	@SerializedName("sunday")
	private List<DetailSchedule> sunday;

	@SerializedName("saturday")
	private List<DetailSchedule> saturday;

	@SerializedName("tuesday")
	private List<DetailSchedule> tuesday;

	@SerializedName("wednesday")
	private List<DetailSchedule> wednesday;

	@SerializedName("thursday")
	private List<DetailSchedule> thursday;

	@SerializedName("friday")
	private List<DetailSchedule> friday;

	@SerializedName("monday")
	private List<DetailSchedule> monday;

	public void setSunday(List<DetailSchedule> sunday){
		this.sunday = sunday;
	}

	public List<DetailSchedule> getSunday(){
		return sunday;
	}

	public void setSaturday(List<DetailSchedule> saturday){
		this.saturday = saturday;
	}

	public List<DetailSchedule> getSaturday(){
		return saturday;
	}

	public void setTuesday(List<DetailSchedule> tuesday){
		this.tuesday = tuesday;
	}

	public List<DetailSchedule> getTuesday(){
		return tuesday;
	}

	public void setWednesday(List<DetailSchedule> wednesday){
		this.wednesday = wednesday;
	}

	public List<DetailSchedule> getWednesday(){
		return wednesday;
	}

	public void setThursday(List<DetailSchedule> thursday){
		this.thursday = thursday;
	}

	public List<DetailSchedule> getThursday(){
		return thursday;
	}

	public void setFriday(List<DetailSchedule> friday){
		this.friday = friday;
	}

	public List<DetailSchedule> getFriday(){
		return friday;
	}

	public void setMonday(List<DetailSchedule> monday){
		this.monday = monday;
	}

	public List<DetailSchedule> getMonday(){
		return monday;
	}

	@Override
 	public String toString(){
		return 
			"ListDaySchedule{" +
			"sunday = '" + sunday + '\'' + 
			",saturday = '" + saturday + '\'' + 
			",tuesday = '" + tuesday + '\'' + 
			",wednesday = '" + wednesday + '\'' + 
			",thursday = '" + thursday + '\'' + 
			",friday = '" + friday + '\'' + 
			",monday = '" + monday + '\'' + 
			"}";
		}
}