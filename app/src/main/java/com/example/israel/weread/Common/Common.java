package com.example.israel.weread.Common;

import com.example.israel.weread.Interface.IconBetterIdeaService;
import com.example.israel.weread.Interface.NewsService;
import com.example.israel.weread.Model.IconBetterIdea;
import com.example.israel.weread.Remote.IconBetterIdeaClient;
import com.example.israel.weread.Remote.RetrofitClient;

public class Common {
    private static final String BASE_URL ="https://newsapi.org/";

    public static final String API_KEY="fb82033080bf45398b651183c7f9a44b";

    public static NewsService getNewsService()
    {
        return RetrofitClient.getClient(BASE_URL).create(NewsService.class);
    }

    public static IconBetterIdeaService getIconService()
    {
        return IconBetterIdeaClient.getClient().create(IconBetterIdeaService.class);
    }

    public static String getApiUrl (String source, String sortBy, String apiKey)
    {
        StringBuilder apiUrl= new StringBuilder("https://newsapi.org/v2/top-headlines?sources=");
        return apiUrl.append(source)
                .append("&apiKey=")
                .append(apiKey)
                .toString();
    }
}
