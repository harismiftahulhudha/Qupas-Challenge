package id.harisgempong.qupaschallenge.view;

import java.util.List;

import id.harisgempong.qupaschallenge.model.Result;

public interface MoviesView {
    void showLoading();
    void hideLoading();
    void showEmpty(String error);
    void loadingPagination();
    void getListMovies(boolean isSearching, boolean isPaginate, List<Result> data);
    void getListFavoriteMovies(List<Result> data);
}
