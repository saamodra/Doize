package id.kelompok04.doize.api;

import id.kelompok04.doize.service.DetailScheduleService;
import id.kelompok04.doize.service.ScheduleService;
import id.kelompok04.doize.service.DailyActivityService;
import id.kelompok04.doize.service.UserService;

public class ApiUtils {
//    public static final String API_URL = "http://192.168.43.145:8000/api/";
    public static final String API_URL = "http://10.0.2.2:8000/api/";
//    public static final String API_URL = "http://192.168.43.100:8000/api/";
//    public static final String API_URL = "https://doize.herokuapp.com/api/";

    private ApiUtils() {

    }

    public static UserService getUserService() {
        return RetrofitClient.getClient(API_URL).create(UserService.class);
    }

    public static ScheduleService getScheduleService() {
        return RetrofitClient.getClient(API_URL).create(ScheduleService.class);
    }
    
    public static DailyActivityService getDailyActivityService() {
        return RetrofitClient.getClient(API_URL).create(DailyActivityService.class);
    }

    public static DetailScheduleService getDetailScheduleService() {
        return RetrofitClient.getClient(API_URL).create(DetailScheduleService.class);
    }
}

