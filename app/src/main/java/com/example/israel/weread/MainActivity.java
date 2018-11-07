package com.example.israel.weread;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.app.AlertDialog;

import com.example.israel.weread.Adapter.ListSourceAdapter;
import com.example.israel.weread.Common.Common;
import com.example.israel.weread.Interface.NewsService;
import com.example.israel.weread.Model.WebSite;
import com.google.gson.Gson;

import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView listWebsite;
    RecyclerView.LayoutManager layoutManager;
    NewsService mService;
    ListSourceAdapter adapter;
    AlertDialog dialog;
    SwipeRefreshLayout swipeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Cache
        Paper.init(this);

        //Services
        mService = Common.getNewsService();

        //Views
        swipeLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefresh);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadWebSiteSource(true);
            }
        });

        listWebsite = (RecyclerView)findViewById(R.id.list_source);
        listWebsite.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        listWebsite.setLayoutManager(layoutManager);

        dialog = new SpotsDialog(this);

        loadWebSiteSource(false);

    }

    private void loadWebSiteSource(boolean isRefreshed) {
        if(!isRefreshed){

            String cache = Paper.book().read("cache");
            if(cache != null && !cache.isEmpty() && !cache.equals("null")) // if have cache
            {
                WebSite webSite = new Gson().fromJson(cache, WebSite.class);// Convert cache from Json to Object
                adapter = new ListSourceAdapter(getBaseContext(), webSite);
                adapter.notifyDataSetChanged();
                listWebsite.setAdapter(adapter);
            }
            else // Case not have cache
            {
                dialog.show();
                //Fetch new data
                mService.getSources().enqueue(new Callback<WebSite>() {
                    @Override
                    public void onResponse(Call<WebSite> call, Response<WebSite> response) {
                        adapter = new ListSourceAdapter(getBaseContext(), response.body());
                        adapter.notifyDataSetChanged();
                        listWebsite.setAdapter(adapter);

                        //Save to cache
                        Paper.book().write("cache", new Gson().toJson(response.body()));
                        dialog.dismiss();

                    }

                    @Override
                    public void onFailure(Call<WebSite> call, Throwable t) {

                    }
                });
            }
        }
        else
        {
            swipeLayout.setRefreshing(true);
            //Fetch new data
            mService.getSources().enqueue(new Callback<WebSite>() {
                @Override
                public void onResponse(Call<WebSite> call, Response<WebSite> response) {
                    adapter = new ListSourceAdapter(getBaseContext(), response.body());
                    adapter.notifyDataSetChanged();
                    listWebsite.setAdapter(adapter);

                    //Save to cache
                    Paper.book().write("cache", new Gson().toJson(response.body()));

                    //Dismiss refresh progressring
                    swipeLayout.setRefreshing(false);
                }

                @Override
                public void onFailure(Call<WebSite> call, Throwable t) {

                }
            });
        }
    }
}
