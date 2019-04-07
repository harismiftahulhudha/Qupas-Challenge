package id.harisgempong.qupaschallenge.api;

import id.harisgempong.qupaschallenge.model.ResultDetail;
import id.harisgempong.qupaschallenge.model.Results;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BaseApiService {
    @GET("/")
    Call<Results> listMovie(@Query("apikey") String apiKey, @Query("s") String search, @Query("type") String type, @Query("y") String year, @Query("page") int page);

    @GET("/")
    Call<ResultDetail> detailMovie(@Query("apikey") String apiKey, @Query("i") String id);
}
