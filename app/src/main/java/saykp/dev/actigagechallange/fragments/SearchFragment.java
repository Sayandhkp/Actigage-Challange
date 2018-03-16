package saykp.dev.actigagechallange.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import saykp.dev.actigagechallange.R;
import saykp.dev.actigagechallange.adapter.HomeAdapter;
import saykp.dev.actigagechallange.adapter.RecyclerItemClickListener;
import saykp.dev.actigagechallange.model.PhotModel;
import saykp.dev.actigagechallange.other.Constants;
import saykp.dev.actigagechallange.other.Utilities;
import saykp.dev.actigagechallange.services.DbAdapter;
import saykp.dev.actigagechallange.services.ServiceHandler;

/**
 * Created by SAYANDH KP (sayandhkp59@gmail.com) on 16-03-2018.
 */

public class SearchFragment extends Fragment implements View.OnClickListener {

    private int positionClick;
    protected TextView textViewGrid;
    protected TextView textViewList;
    ArrayList<String> urlArray = new ArrayList<>();
    protected View rootView;
    protected EditText keyEditText;
    protected Button buttonDone;
    protected ProgressBar progerssBar;
    protected RecyclerView recyclerView;
    protected LinearLayout layoutsecond;

    private String url = null;
    private HomeAdapter adapter;
    DbAdapter dbAdapter;

    public SearchFragment() {

    }

    public static SearchFragment getInstance() {
        SearchFragment searchFragment = new SearchFragment();

        return searchFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTitle("Search");
        dbAdapter = new DbAdapter(getContext());
        dbAdapter.open();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);


        initView(view);

        adapter = new HomeAdapter(getContext(), urlArray, Constants.LAYOUT_GRID);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);


        recyclerView.addOnItemTouchListener(

                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnDoubleClickListener() {

                    @Override
                    public void onDoubleClick(View view, int position) {
                        if (position != positionClick) {
                            String url = urlArray.get(position);
                            Toast.makeText(getContext(), "This Image Added to Favourites", Toast.LENGTH_SHORT).show();
                            dbAdapter.insertMsgData(url, "1");


                        }
                    }
                })
        );

        return view;
    }

    private void initView(View rootView) {
        keyEditText = (EditText) rootView.findViewById(R.id.keyEditText);
        buttonDone = (Button) rootView.findViewById(R.id.buttonDone);
        buttonDone.setOnClickListener(SearchFragment.this);
        progerssBar = (ProgressBar) rootView.findViewById(R.id.progerssBar);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        layoutsecond = (LinearLayout) rootView.findViewById(R.id.layoutsecond);
        textViewGrid = (TextView) rootView.findViewById(R.id.textViewGrid);
        textViewGrid.setOnClickListener(SearchFragment.this);
        textViewList = (TextView) rootView.findViewById(R.id.textViewList);
        textViewList.setOnClickListener(SearchFragment.this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonDone) {
            String keyWord = keyEditText.getEditableText().toString().trim();

            if (keyWord.isEmpty()) {
                keyEditText.setHint("Please enter keyword");
            } else {
                if (Utilities.isOnline(getContext())) {
                    url = Constants.URL_PREFIX + keyWord + Constants.URL_SUFIX;
                    new getImagestask().execute();
                } else {
                    Log.v("No NETWORK", "NO");
                    Toast.makeText(getContext(), "No Connection, please enable your internet", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (view.getId() == R.id.textViewGrid) {
            textViewGrid.setBackgroundColor(getResources().getColor(R.color.blue));
            textViewGrid.setTextColor(getResources().getColor(R.color.white));
            textViewList.setBackgroundColor(getResources().getColor(R.color.white));
            textViewList.setTextColor(getResources().getColor(R.color.blue));
            Constants.LAYOUT = Constants.LAYOUT_GRID;
            adapter = new HomeAdapter(getContext(), urlArray, Constants.LAYOUT);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(adapter);

        } else if (view.getId() == R.id.textViewList) {
            textViewList.setBackgroundColor(getResources().getColor(R.color.blue));
            textViewList.setTextColor(getResources().getColor(R.color.white));
            textViewGrid.setBackgroundColor(getResources().getColor(R.color.white));
            textViewGrid.setTextColor(getResources().getColor(R.color.blue));

            Constants.LAYOUT = Constants.LAYOUT_LIST;

            adapter = new HomeAdapter(getContext(), urlArray, Constants.LAYOUT);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(adapter);
        }
    }

    private class getImagestask extends AsyncTask<Void, Void, Void> {
        JSONObject responceObj = null;

        @Override
        protected void onPreExecute() {
            progerssBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (Utilities.isOnline(getContext())) {
                try {
                    responceObj = ServiceHandler.getInstance().contactServerWithGET(url);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progerssBar.setVisibility(View.GONE);
            try {
                if (responceObj != null) {
                    if (responceObj.getString("stat").equalsIgnoreCase("ok")) {
                        JSONArray phtoArray = responceObj.getJSONObject("photos").getJSONArray("photo");

                        urlArray = new ArrayList<>();
                        if (phtoArray.length() != 0) {
                            for (int x = 0; x < phtoArray.length(); x++) {
                                PhotModel photModel = new PhotModel();

                                JSONObject photoObj = phtoArray.getJSONObject(x);
                                String imageId = (photoObj.getString("id"));
                                String farmid = (photoObj.getString("farm"));
                                String title = (photoObj.getString("title"));
                                String secret = (photoObj.getString("secret"));
                                String serverId = (photoObj.getString("server"));

                                String imageUrl = "https://farm" + farmid + ".staticflickr.com/" + serverId + "/" + imageId + "_" + secret + ".jpg";

                                urlArray.add(imageUrl);
                            }

                            adapter = new HomeAdapter(getContext(), urlArray, Constants.LAYOUT);
                            recyclerView.setAdapter(adapter);

                            recyclerView.setVisibility(View.VISIBLE);

                        }

                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
