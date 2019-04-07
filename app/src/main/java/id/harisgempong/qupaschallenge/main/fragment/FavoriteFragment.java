package id.harisgempong.qupaschallenge.main.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import id.harisgempong.qupaschallenge.R;
import id.harisgempong.qupaschallenge.main.adapter.MoviesAdapter;
import id.harisgempong.qupaschallenge.model.Result;
import id.harisgempong.qupaschallenge.presenter.MoviesPresenter;
import id.harisgempong.qupaschallenge.view.MoviesView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment implements MoviesView {

    private List<Result> results;
    private RecyclerView recyclerView;
    private MoviesAdapter adapter;
    private ProgressBar progressBar;
    private TextView empty;
    private MoviesPresenter presenter;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initComponents(view);

        presenter.showListFavoriteMovies();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        BottomNavigationView navigationView = Objects.requireNonNull(getActivity()).findViewById(R.id.mainNavigation);
        navigationView.getMenu().findItem(R.id.nav_favorite).setChecked(true);
    }

    private void initComponents(View view) {
        results = new ArrayList<>();
        recyclerView = view.findViewById(R.id.favoriteRecyclerview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);
        adapter = new MoviesAdapter(results, getActivity(), this);
        recyclerView.setAdapter(adapter);
        progressBar = view.findViewById(R.id.favoriteProgress);
        empty = view.findViewById(R.id.favoriteEmpty);

        presenter = new MoviesPresenter(getActivity(), this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            presenter.showListFavoriteMovies();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(getResources().getString(R.string.search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.filter(s);
                return true;
            }
        });


        MenuItem filterBtn = menu.findItem(R.id.action_filter);
        filterBtn.setVisible(false);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        empty.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        empty.setVisibility(View.GONE);
    }

    @Override
    public void showEmpty(String error) {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        empty.setVisibility(View.VISIBLE);
        empty.setText(error);
    }

    @Override
    public void loadingPagination() {

    }

    @Override
    public void getListMovies(boolean isSearching, boolean isPaginate, List<Result> data) {

    }

    @Override
    public void getListFavoriteMovies(List<Result> data) {
        results.clear();
        results.addAll(data);
        adapter.addToCopy(data);
        adapter.notifyDataSetChanged();
    }
}
