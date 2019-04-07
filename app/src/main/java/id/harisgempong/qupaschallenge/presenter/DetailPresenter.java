package id.harisgempong.qupaschallenge.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import id.harisgempong.qupaschallenge.BuildConfig;
import id.harisgempong.qupaschallenge.api.BaseApiService;
import id.harisgempong.qupaschallenge.api.UtilsApi;
import id.harisgempong.qupaschallenge.model.ResultDetail;
import id.harisgempong.qupaschallenge.view.DetailView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPresenter {
    private final Context context;
    private final DetailView view;
    private final BaseApiService baseApiService;

    public DetailPresenter(Context context, DetailView view) {
        this.context = context;
        this.view = view;
        this.baseApiService = UtilsApi.getApiService();
    }

    public void showDetail(String id) {
        baseApiService.detailMovie(BuildConfig.API_KEY, id).enqueue(new Callback<ResultDetail>() {
            @Override
            public void onResponse(@NonNull Call<ResultDetail> call, @NonNull Response<ResultDetail> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    String res = response.body().getResponse();
                    if (res.equals("True")) {
                        view.getDataDetail(response.body());
                    } else {
                        Toast.makeText(context, "Failed to load data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Failed to load data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResultDetail> call, @NonNull Throwable t) {
                Toast.makeText(context, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
