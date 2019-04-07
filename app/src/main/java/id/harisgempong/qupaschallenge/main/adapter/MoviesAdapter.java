package id.harisgempong.qupaschallenge.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import id.harisgempong.qupaschallenge.R;
import id.harisgempong.qupaschallenge.detail.activity.DetailActivity;
import id.harisgempong.qupaschallenge.model.Result;
import id.harisgempong.qupaschallenge.view.MoviesView;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private List<Result> results;
    private final Context context;
    private final MoviesView view;
    private List<Result> copy_list = new ArrayList<>();

    public MoviesAdapter(List<Result> results, Context context, MoviesView view) {
        this.results = results;
        this.context = context;
        this.view = view;
    }

    public void addToCopy(List<Result> data) {
        copy_list.clear();
        copy_list.addAll(data);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_movies_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Result result = results.get(position);
        if (!result.getImdbID().equals("0")) {
            holder.constraintLayout.setVisibility(View.VISIBLE);
            holder.progressBar.setVisibility(View.GONE);
            holder.title.setText(result.getTitle());
            holder.year.setText(result.getYear());
            holder.type.setText(result.getType());
            Glide.with(context).load(result.getPoster()).placeholder(context.getResources().getDrawable(R.drawable.noposter)).into(holder.image);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, DetailActivity.class)
                            .putExtra("id", result.getImdbID()));
                }
            });
        } else {
            holder.constraintLayout.setVisibility(View.GONE);
            holder.progressBar.setVisibility(View.VISIBLE);
        }

        if (result.getImdbID().equals("0") && position == results.size() - 1) {
            view.loadingPagination();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return (results == null) ? 0 : results.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView image;
        private final TextView title, year, type;
        private final ConstraintLayout constraintLayout;
        private final ProgressBar progressBar;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.image = itemView.findViewById(R.id.moviesRowImage);
            this.title = itemView.findViewById(R.id.moviesRowTitle);
            this.year = itemView.findViewById(R.id.moviesRowYear);
            this.type = itemView.findViewById(R.id.moviesRowType);
            this.constraintLayout = itemView.findViewById(R.id.moviesRowConstraint);
            this.progressBar = itemView.findViewById(R.id.moviesRowProgress);
        }
    }

    public void filter(String queryText) {
        results.clear();
        if (queryText.isEmpty()) {
            results.addAll(copy_list);
        } else {
            for (Result result : copy_list) {
                if (result.getTitle().toLowerCase().contains(queryText.toLowerCase())) {
                    results.add(result);
                }
            }
        }

        notifyDataSetChanged();
    }
}
