package id.harisgempong.qupaschallenge.main.activity;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import id.harisgempong.qupaschallenge.R;
import id.harisgempong.qupaschallenge.main.fragment.FavoriteFragment;
import id.harisgempong.qupaschallenge.main.fragment.MoviesFragment;

public class MainActivity extends AppCompatActivity {

    private MoviesFragment moviesFragment;
    private FavoriteFragment favoriteFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_movie:
                    setFragment(moviesFragment);
                    return true;
                case R.id.nav_favorite:
                    setFragment(favoriteFragment);
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

        if (savedInstanceState == null) {
            setFragment(moviesFragment);
        }
    }

    private void setFragment(final Fragment fragment) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.mainContainer, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }
}
