package id.harisgempong.qupaschallenge.main.fragment;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
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
public class MoviesFragment extends Fragment implements MoviesView {

    private List<Result> results;
    private RecyclerView recyclerView;
    private MoviesAdapter adapter;
    private ProgressBar progressBar;
    private TextView empty;
    private MoviesPresenter presenter;
    private String search, type, year;
    private int page;
    private int posType, posYear;

    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initComponents(view);

        if (savedInstanceState == null) {
            presenter.showListMovies(false, false, search, type, year, page);
            Log.d("CHECKLOAD", "onViewCreated: a: " + page);
        } else {
            presenter.showListSaved(savedInstanceState);
            Log.d("CHECKLOAD", "onViewCreated: b: " + page);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        BottomNavigationView navigationView = Objects.requireNonNull(getActivity()).findViewById(R.id.mainNavigation);
        navigationView.getMenu().findItem(R.id.nav_movie).setChecked(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                search = "guardian";
                presenter.showListMovies(true, false, search, type, year, page);
                return true;
            }
        });
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(getResources().getString(R.string.search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.length() > 0) {
                    search = s;
                    presenter.showListMovies(true, false, search, type, year, page);
                } else {
                    search = "guardian";
                    presenter.showListMovies(true, false, search, type, year, page);
                }
                return true;
            }
        });


        MenuItem filterBtn = menu.findItem(R.id.action_filter);
        filterBtn.setVisible(true);
        Drawable icon = DrawableCompat.wrap(getResources().getDrawable(R.drawable.filter));
        DrawableCompat.setTint(icon, Color.WHITE);
        filterBtn.setIcon(icon);

        filterBtn.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                showDialogFilter();
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList("movies", (ArrayList<? extends Parcelable>) results);
        super.onSaveInstanceState(outState);
    }

    private void showDialogFilter() {
        final Dialog dialog = new Dialog(Objects.requireNonNull(getActivity()));
        assert dialog.getWindow() != null;
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.fragment_movies_filter);
        dialog.show();

        final Spinner spinnerType = dialog.findViewById(R.id.spinnerType);
        final Spinner spinnerYear = dialog.findViewById(R.id.spinnerYear);
        final Button btnFilter = dialog.findViewById(R.id.btnFilter);

        final ArrayList<String> years = new ArrayList<>();
        years.add(getResources().getString(R.string.chooseYear));
        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = year; i >= (year - 30); i--) {
            years.add(String.valueOf(i));
        }
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, years);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(yearAdapter);

        spinnerType.setSelection(posType);
        spinnerYear.setSelection(posYear);

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posType = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posYear = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                filter(spinnerType, posType, spinnerYear, posYear);
            }
        });
    }

    private void filter(Spinner spinnerType, int posType, Spinner spinnerYear, int posYear) {
        if (posType == 0) {
            type = "";
        } else {
            type = spinnerType.getSelectedItem().toString();
        }
        if (posYear == 0) {
            year = "";
        } else {
            year = spinnerYear.getSelectedItem().toString();
        }
        page = 1;
        presenter.showListMovies(true, false, search, type, year, page);
    }

    private void initComponents(View view) {
        results = new ArrayList<>();
        recyclerView = view.findViewById(R.id.moviesRecyclerview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);

        page = 1;
        posType = 0;
        posYear = 0;
        search = "guardian";
        type = "";
        year = "";

        adapter = new MoviesAdapter(results, getActivity(), this);
        recyclerView.setAdapter(adapter);
        progressBar = view.findViewById(R.id.moviesProgress);
        empty = view.findViewById(R.id.moviesEmpty);
        presenter = new MoviesPresenter(getActivity(), this);
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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int index = results.size() - 1;
                results.remove(index);
                adapter.notifyItemRemoved(index);
                page++;
                presenter.showListMovies(false, true, search, type, year, page);
            }
        }, 500);
    }

    @Override
    public void getListMovies(boolean isSearching, boolean isPaginate, List<Result> data) {
        if (isSearching) {
            page = 1;
            results.clear();
        }

        int start = results.size();
        results.addAll(data);

        if (data.size() == 10) {
            Result result = new Result(
                    "0",
                    "0",
                    "loading",
                    "0",
                    "0"
            );
            results.add(result);
        }

        if (!isPaginate) {
            adapter.notifyDataSetChanged();
        } else {
            adapter.notifyItemRangeInserted(start, results.size() - 1);
        }
    }

    @Override
    public void getListFavoriteMovies(List<Result> data) {

    }
}
