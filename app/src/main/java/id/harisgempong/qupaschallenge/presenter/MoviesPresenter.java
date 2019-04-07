package id.harisgempong.qupaschallenge.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.util.List;

import id.harisgempong.qupaschallenge.BuildConfig;
import id.harisgempong.qupaschallenge.api.BaseApiService;
import id.harisgempong.qupaschallenge.api.UtilsApi;
import id.harisgempong.qupaschallenge.database.methodhelper.FavoriteHelper;
import id.harisgempong.qupaschallenge.model.Result;
import id.harisgempong.qupaschallenge.model.Results;
import id.harisgempong.qupaschallenge.view.MoviesView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesPresenter {
    private final Context context;
    private final BaseApiService baseApiService;
    private final MoviesView view;
    private final FavoriteHelper favoriteHelper;

    public MoviesPresenter(Context context, MoviesView view) {
        this.context = context;
        this.view = view;
        this.baseApiService = UtilsApi.getApiService();
        this.favoriteHelper = new FavoriteHelper(context);
    }

    public void showListMovies(final boolean isSearching, final boolean isPaginate, String search, String type, String year, final int page) {
        if (!isPaginate) {
            view.showLoading();
        }
        baseApiService.listMovie(BuildConfig.API_KEY, search, type, year, page).enqueue(new Callback<Results>() {
            @Override
            public void onResponse(@NonNull Call<Results> call, @NonNull Response<Results> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    String res = response.body().getResponse();
                    if (res.equals("True")) {
                        view.getListMovies(isSearching, isPaginate, response.body().getResults());
                        view.hideLoading();
                    } else if (res.equals("False") && page == 1) {
                        view.showEmpty(response.body().getError());
                    } else {
                        view.hideLoading();
                    }
                } else {
                    view.hideLoading();
                    Toast.makeText(context, "Failed to load data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Results> call, @NonNull Throwable t) {
                view.hideLoading();
            }
        });
    }

    public void showListSaved(Bundle savedInstanceState) {
        List<Result> results = savedInstanceState.getParcelableArrayList("movies");
        view.getListMovies(false, false, results);
    }

    public void showListFavoriteMovies() {
        view.showLoading();
        int count = favoriteHelper.list().size();
        if (count > 0) {
            view.getListFavoriteMovies(favoriteHelper.list());
            view.hideLoading();
        } else {
            view.showEmpty("Movies not found!");
        }
    }
}
