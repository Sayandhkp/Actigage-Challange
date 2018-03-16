package saykp.dev.actigagechallange.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import saykp.dev.actigagechallange.R;
import saykp.dev.actigagechallange.adapter.HomeAdapter;
import saykp.dev.actigagechallange.adapter.RecyclerItemClickListener;
import saykp.dev.actigagechallange.other.Constants;
import saykp.dev.actigagechallange.services.DbAdapter;

/**
 * Created by SAYANDH KP (sayandhkp59@gmail.com) on 16-03-2018.
 */

public class FavouriteFragment extends Fragment {
    protected View rootView;
    protected RecyclerView recyclerViewFav;
    ArrayList<String> urlArray=new ArrayList<>();
    DbAdapter dbAdapter;
    HomeAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbAdapter=new DbAdapter(getContext());
        dbAdapter.open();
        getActivity().setTitle("Favourites");

        Constants.LAYOUT=Constants.LAYOUT_FAV;
    }

    public FavouriteFragment getInstance(){
        FavouriteFragment favouriteFragment=new FavouriteFragment();

        return favouriteFragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);

        urlArray=dbAdapter.getData();

        initView(view);

        adapter = new HomeAdapter(getContext(), urlArray, Constants.LAYOUT);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerViewFav.setLayoutManager(gridLayoutManager);
        recyclerViewFav.setAdapter(adapter);

        recyclerViewFav.addOnItemTouchListener(
        new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemLongClickListenetr() {
            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getContext(), "Long Press =="+position, Toast.LENGTH_SHORT).show();
            }
        })
        );

        return view;

    }

    private void initView(View rootView) {
        recyclerViewFav = (RecyclerView) rootView.findViewById(R.id.recyclerViewFav);
    }
}
