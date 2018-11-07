package com.example.israel.weread.Interface;

import com.example.israel.weread.Common.Common;
import com.example.israel.weread.Model.News;
import com.example.israel.weread.Model.WebSite;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface NewsService {
    @GET("v2/sources?language=en&apiKey="+ Common.API_KEY)
    Call<WebSite> getSources();

    @GET
    Call<News> getNewestArticles(@Url String url);
}
