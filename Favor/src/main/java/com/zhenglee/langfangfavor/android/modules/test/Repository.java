package com.zhenglee.langfangfavor.android.modules.test;

import java.io.Serializable;

import com.google.gson.GsonBuilder;

public class Repository implements Serializable {

    private static final long serialVersionUID = -6429611402427611008L;

    private long id;

    private String name;

    private User owner;

    private boolean description;

    private boolean fork;

    private String url;

    private long size;

    private String language;

    private long forks;

    private long watchers;

    public Repository() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public boolean isDescription() {
        return description;
    }

    public void setDescription(boolean description) {
        this.description = description;
    }

    public boolean isFork() {
        return fork;
    }

    public void setFork(boolean fork) {
        this.fork = fork;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public long getForks() {
        return forks;
    }

    public void setForks(long forks) {
        this.forks = forks;
    }

    public long getWatchers() {
        return watchers;
    }

    public void setWatchers(long watchers) {
        this.watchers = watchers;
    }

    @Override
    public String toString() {
        return new GsonBuilder().create().toJson(this);
    }

}
