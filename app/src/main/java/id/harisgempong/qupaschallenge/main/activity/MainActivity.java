package id.harisgempong.qupaschallenge.main.activity;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import id.harisgempong.qupaschallenge.R;
import id.harisgempong.qupaschallenge.main.fragment.FavoriteFragment;
import id.harisgempong.qupaschallenge.main.fragment.MoviesFragment;

public class MainActivity extends AppCompatActivity {

    private MoviesFragment moviesFragment;
    private FavoriteFragment favoriteFragment;
    private int count;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_movie:
                    count++;
                    setFragment(moviesFragment, false);
                    return true;
                case R.id.nav_favorite:
                    count++;
                    setFragment(favoriteFragment, false);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moviesFragment = new MoviesFragment();
        favoriteFragment = new FavoriteFragment();
        BottomNavigationView navigationView = findViewById(R.id.mainNavigation);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        count = 0;
        if (savedInstanceState == null) {
            setFragment(moviesFragment, true);
        }
    }

    @Override
    public void onBackPressed() {
        if (count == 0) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), "Are you sure you want to close this app ?", Snackbar.LENGTH_LONG)
                    .setAction("CLOSE", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                            System.exit(0);
                        }
                    });
            snackbar.show();
        } else {
            count--;
            super.onBackPressed();
        }
    }

    private void setFragment(final Fragment fragment, final boolean isFirst) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.mainContainer, fragment);
                if (!isFirst) {
                    fragmentTransaction.addToBackStack(null);
                }
                fragmentTransaction.commit();
            }
        });
    }
}
