package saykp.dev.actigagechallange;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import saykp.dev.actigagechallange.fragments.FavouriteFragment;
import saykp.dev.actigagechallange.fragments.SearchFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    protected TextView textViewSearch;
    protected TextView textViewFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        initView();


        textViewSearch.setTextColor(getResources().getColor(R.color.blue));
        textViewFav.setTextColor(getResources().getColor(R.color.black));


        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        SearchFragment homeFragment = new SearchFragment();
        ft.replace(R.id.layoutFragment, homeFragment);
        ft.commit();



    }


    @Override
    public void onClick(View view) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (view.getId() == R.id.textViewSearch) {
            textViewSearch.setTextColor(getResources().getColor(R.color.blue));
            textViewFav.setTextColor(getResources().getColor(R.color.black));

            SearchFragment homeFragment = new SearchFragment();
            ft.replace(R.id.layoutFragment, homeFragment);
            ft.commit();

        } else if (view.getId() == R.id.textViewFav) {
            textViewSearch.setTextColor(getResources().getColor(R.color.black));
            textViewFav.setTextColor(getResources().getColor(R.color.blue));


            FavouriteFragment favouriteFragment = new FavouriteFragment();
            ft.replace(R.id.layoutFragment, favouriteFragment);
            ft.commit();


        }
    }


    private void initView() {
        textViewSearch = (TextView) findViewById(R.id.textViewSearch);
        textViewSearch.setOnClickListener(MainActivity.this);
        textViewFav = (TextView) findViewById(R.id.textViewFav);
        textViewFav.setOnClickListener(MainActivity.this);
    }
}
