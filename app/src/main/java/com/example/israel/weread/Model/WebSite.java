package com.example.israel.weread.Model;

import java.util.List;

public class WebSite {
    private String status;
    private List<Source> sources;

    public WebSite(){}

    public WebSite(String status, List<Source> sources){
        this.status = status;
        this.sources = sources;
    }

    public String getStatus() {
        return status;
    }

    public List<Source> getSources() {
        return sources;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }
}
