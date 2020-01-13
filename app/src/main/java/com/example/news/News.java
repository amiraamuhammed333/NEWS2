package com.example.news;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.news.API.APIManger;
import com.example.news.API.news.ArticlesItem;
import com.example.news.API.news.NewsResponse;
import com.example.news.API.news.SourceResponse;
import com.example.news.API.news.SourcesItem;
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
        tabLayout = findViewById ( R.id.tablayout );
        recyclerView = findViewById ( R.id.recycleview );
        iniRecyclerView ();
        loadNewsSources(); }


    public void iniRecyclerView(){
        newsAdapter = new NewsAdapter ( null );
        recyclerView.setAdapter ( newsAdapter );
        recyclerView.setLayoutManager (new LinearLayoutManager ( activity ) );
    }

    private void loadNewsSources() {

        showProgreesBar ( R.string.loading );
        APIManger.getApis ()
                .getNewaSources ( Constants.API_KEY, Constants.LANGuAGE )
                .enqueue ( new Callback<SourceResponse> () {
                    @Override
                    public void onResponse(Call<SourceResponse> call, Response<SourceResponse> response) {
                        hideProgressDialog ();
                        setTabLayoutWithNewsSources(response.body ().getSources ()); }
                    @Override
                    public void onFailure(Call<SourceResponse> call, Throwable t) {
                        hideProgressDialog ();
                        showMessage ( getString ( R.string.error )
                                ,t.getLocalizedMessage (),getString(R.string.oak) );
                    }} ); }


    private void loadNewsSourcesById(String sourceId){
        showProgreesBar ( R.string.loading );
        APIManger.getApis ().getNews ( Constants.API_KEY, Constants.LANGuAGE,sourceId+"" )
                .enqueue ( new Callback<NewsResponse> () {
                    @Override
                    public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                        hideProgressDialog ();
                        articles=response.body ().getArticles ();
                         newsAdapter.changeData ( articles ); }
                    @Override
                    public void onFailure(Call<NewsResponse> call, Throwable t) { }} ); }


    private void setTabLayoutWithNewsSources(final List<SourcesItem> sources) {
        for (int i=0;i<sources.size ();i++){
            TabLayout.Tab  tab=tabLayout.newTab ();
            tab.setText ( sources.get ( i ).getName () );
            tab.setTag ( sources.get (i) );
            tabLayout.addTab ( tab ); }
        tabLayout.addOnTabSelectedListener ( new TabLayout.OnTabSelectedListener () {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                SourcesItem item =((SourcesItem) tab.getTag ());
                loadNewsSourcesById (item.getId ()); }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }} ); }





}
