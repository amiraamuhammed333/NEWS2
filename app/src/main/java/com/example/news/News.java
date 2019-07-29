package com.example.news;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.news.API.APIManger;
import com.example.news.API.ArticlesItem;
import com.example.news.API.NewsResponse;
import com.example.news.API.SourceResponse;
import com.example.news.API.SourcesItem;
import com.example.news.Base.BaseActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class News extends BaseActivity {

    TabLayout tabLayout;
    RecyclerView recyclerView;
    List<ArticlesItem>articles;
    NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_news );
        Toolbar toolbar = findViewById ( R.id.toolbar );
        setSupportActionBar ( toolbar );


        tabLayout = findViewById ( R.id.tablayout );
        recyclerView = findViewById ( R.id.recycleview );
        iniRecyclerView ();

        loadNewsSources();
    }


    public void iniRecyclerView(){
        newsAdapter = new NewsAdapter ( null );
        recyclerView.setAdapter ( newsAdapter );
        recyclerView.setLayoutManager (new LinearLayoutManager ( activity ) );
    }

    private void loadNewsSources() {

        showProgreesBar ( R.string.loading );
        APIManger.getApis ()
                .getNewaSources ( Constanrs.API_KEY,Constanrs.LANGuAGE )
                .enqueue ( new Callback<SourceResponse> () {
                    @Override
                    public void onResponse(Call<SourceResponse> call, Response<SourceResponse> response) {
                        hideProgressDialog ();
                        setTabLayoutWithNewsSources(response.body ().getSources ());
                    }

                    @Override
                    public void onFailure(Call<SourceResponse> call, Throwable t) {
                        hideProgressDialog ();
                        showMessage ( getString ( R.string.error )
                                ,t.getLocalizedMessage (),getString(R.string.oak) );

                    }
                } );
    }


    private void loadNewsSourcesById(String sourceId){


        showProgreesBar ( R.string.loading );
        APIManger.getApis ().getNews ( Constanrs.API_KEY,Constanrs.LANGuAGE,sourceId+"" )
                .enqueue ( new Callback<NewsResponse> () {
                    @Override
                    public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                        hideProgressDialog ();
                        articles=response.body ().getArticles ();
                         newsAdapter.changeData ( articles );
                    }

                    @Override
                    public void onFailure(Call<NewsResponse> call, Throwable t) {

                        hideProgressDialog ();
                        showMessage ( getString ( R.string.error )
                                ,t.getLocalizedMessage (),getString(R.string.oak) );

                    }
                } );


    }

    private void setTabLayoutWithNewsSources(final List<SourcesItem> sources) {
        for (int i=0;i<sources.size ();i++){

            TabLayout.Tab  tab=tabLayout.newTab ();
            tab.setText ( sources.get ( i ).getName () );
            tab.setTag ( sources.get (i) );

            tabLayout.addTab ( tab );
        }

        tabLayout.addOnTabSelectedListener ( new TabLayout.OnTabSelectedListener () {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                //int tabPos=tab.getPosition ();
                // loadNewsSourcesById (sources.get ( tabPos ).getId () );
                SourcesItem item =((SourcesItem) tab.getTag ());
                loadNewsSourcesById (item.getId ());
            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        } );
    }





}
