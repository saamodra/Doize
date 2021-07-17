package id.kelompok04.doize.api;

import id.kelompok04.doize.service.LoginService;

public class ApiUtils {
    public static final String API_URL = "http://192.168.43.145:8000/";

    private ApiUtils() {

    }

    public static LoginService getCrimeService() {
        return RetrofitClient.getClient(API_URL).create(LoginService.class);
    }
}

