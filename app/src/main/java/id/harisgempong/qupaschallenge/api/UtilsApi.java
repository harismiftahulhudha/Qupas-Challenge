package id.harisgempong.qupaschallenge.api;

public class UtilsApi {
    public static BaseApiService getApiService() {
        RetrofitClient retrofitClient = new RetrofitClient();
        return retrofitClient.getClient().create(BaseApiService.class);
    }
}
