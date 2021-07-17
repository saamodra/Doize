package id.kelompok04.doize.api;

import id.kelompok04.doize.service.UserService;

public class ApiUtils {
    public static final String API_URL = "http://192.168.43.145:8000/api/";

    private ApiUtils() {

    }

    public static UserService getUserService() {
        return RetrofitClient.getClient(API_URL).create(UserService.class);
    }
}

