package id.harisgempong.qupaschallenge.detail.activity;

import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import id.harisgempong.qupaschallenge.R;
import id.harisgempong.qupaschallenge.database.methodhelper.FavoriteHelper;
import id.harisgempong.qupaschallenge.model.Result;
import id.harisgempong.qupaschallenge.model.ResultDetail;
import id.harisgempong.qupaschallenge.presenter.DetailPresenter;
import id.harisgempong.qupaschallenge.view.DetailView;

public class DetailActivity extends AppCompatActivity implements DetailView {

    private ImageView detailImage;
    private TextView detailTitle, detailYear, detailGenre, detailRating, detailCountry,
                    detailDirector, detailWriter, detailActor, detailPlot, detailLanguage,
                    detailProduction, detailRelease;
    private DetailPresenter presenter;
    private String id;
    private FavoriteHelper favoriteHelper;
    private boolean isAvailable;
    private MenuItem favoriteItem;
    private Result result = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initComponents();

        presenter.showDetail(id);
    }

    private void initComponents() {
        detailImage = findViewById(R.id.detailImage);
        detailTitle = findViewById(R.id.detailTitle);
        detailYear = findViewById(R.id.detailYear);
        detailGenre = findViewById(R.id.detailGenre);
        detailRating = findViewById(R.id.detailRating);
        detailCountry = findViewById(R.id.detailCountry);
        detailDirector = findViewById(R.id.detailDirectior);
        detailWriter = findViewById(R.id.detailWriter);
        detailActor = findViewById(R.id.detailActor);
        detailPlot = findViewById(R.id.detailPlot);
        detailLanguage = findViewById(R.id.detailLanguage);
        detailProduction = findViewById(R.id.detailProduction);
        detailRelease = findViewById(R.id.detailRelease);
        presenter = new DetailPresenter(this, this);
        favoriteHelper = new FavoriteHelper(this);
        id = getIntent().getStringExtra("id");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        this.favoriteItem = menu.findItem(R.id.btnFavorite);
        setIconColor();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnFavorite:
                if (!isAvailable) {
                    if (result != null) {
                        favoriteHelper.insert(result);
                    } else {
                        Toast.makeText(this, "Please wait for the process to load data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    favoriteHelper.delete(id);
                }
                setIconColor();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getDataDetail(ResultDetail data) {
        Glide.with(this).load(data.getImage()).placeholder(getResources().getDrawable(R.drawable.noposter)).into(detailImage);
        detailTitle.setText(data.getTitle());
        String yearAndDuration = data.getYear() + " | " + data.getDuration();
        detailYear.setText(yearAndDuration);
        String[] splitGenre = data.getGenre().split(", ");
        if (splitGenre.length > 0) {
            StringBuilder genreBuilder = new StringBuilder();
            for (int i = 0; i < splitGenre.length; i++) {
                genreBuilder.append(splitGenre[i]);
                if (i == splitGenre.length - 1) {
                    break;
                } else {
                    genreBuilder.append(" | ");
                }
            }
            detailGenre.setText(genreBuilder.toString());
        } else {
            detailGenre.setText(data.getGenre());
        }
        String ratingType = "  -  Rating " + data.getRating() + " | " + data.getType();
        detailRating.setText(ratingType);
        detailCountry.setText(data.getCountry());
        detailDirector.setText(data.getDirector());
        detailWriter.setText(data.getWriter());
        detailActor.setText(data.getActor());
        detailPlot.setText(data.getPlot());
        detailLanguage.setText(data.getLanguage());
        String production = "-";
        if (data.getProduction() != null) {
            production = data.getProduction();
        }
        detailProduction.setText(production);
        detailRelease.setText(data.getRelease());

        result = new Result(
                data.getId(),
                data.getYear(),
                data.getTitle(),
                data.getType(),
                data.getImage()
        );
    }

    private void setIconColor() {
        Drawable icon = DrawableCompat.wrap(getResources().getDrawable(R.drawable.ic_favorite));
        isAvailable = favoriteHelper.isAvailable(id);
        if (isAvailable) {
            DrawableCompat.setTint(icon, getResources().getColor(R.color.colorPink));
            favoriteItem.setIcon(icon);
        } else {
            DrawableCompat.setTint(icon, getResources().getColor(R.color.colorWhite));
            favoriteItem.setIcon(icon);
        }
    }
}
