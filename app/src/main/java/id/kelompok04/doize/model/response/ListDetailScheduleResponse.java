package id.kelompok04.doize.model.response;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import id.kelompok04.doize.model.DetailSchedule;

public class ListDetailScheduleResponse{

	@SerializedName("data")
	@Expose
	private List<List<DetailSchedule>> data;

	@SerializedName("message")
	@Expose
	private String message;

	@SerializedName("status")
	@Expose
	private int status;

	public List<List<DetailSchedule>> getData(){
		return data;
	}

	public String getMessage(){
		return message;
	}

	public int getStatus(){
		return status;
	}

	@Override
	public String toString() {
		return "ListDetailScheduleResponse{" +
				"data=" + data +
				", message='" + message + '\'' +
				", status=" + status +
				'}';
	}
}