package com.motivation.statusforwhatsapp.Database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "fav_quotes")
public class FavQuote implements Serializable {

    @PrimaryKey
    @SerializedName("id")
    private int id;
    @SerializedName("category_id")
    private int category_id;
    @SerializedName("quote")
    private String quote;
    @SerializedName("author")
    private String author;
    @SerializedName("language")
    private String language;
    @SerializedName("favourites")
    private int favourites;
    @SerializedName("shares")
    private int shares;
    @SerializedName("listens")
    private int listens;
    @SerializedName("status_sets")
    private int status_sets;
    @SerializedName("views")
    private int views;
    @SerializedName("image_link")
    private String image_link;
    @SerializedName("category_name")
    private String category_name;
    private String favDateTime;

    public FavQuote(int id, int category_id, String quote, String author, String language, int favourites, int shares, int listens, int status_sets, int views, String image_link, String category_name, String favDateTime) {
        this.id = id;
        this.category_id = category_id;
        this.quote = quote;
        this.author = author;
        this.language = language;
        this.favourites = favourites;
        this.shares = shares;
        this.listens = listens;
        this.status_sets = status_sets;
        this.views = views;
        this.image_link = image_link;
        this.category_name = category_name;
        this.favDateTime = favDateTime;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getFavourites() {
        return favourites;
    }

    public void setFavourites(int favourites) {
        this.favourites = favourites;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }

    public int getListens() {
        return listens;
    }

    public void setListens(int listens) {
        this.listens = listens;
    }

    public int getStatus_sets() {
        return status_sets;
    }

    public void setStatus_sets(int status_sets) {
        this.status_sets = status_sets;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getFavDateTime() {
        return favDateTime;
    }

    public void setFavDateTime(String favDateTime) {
        this.favDateTime = favDateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuote_name() {
        return quote;
    }

    public void setQuote_name(String quote_name) {
        this.quote = quote;
    }

    public String getQuote_author() {
        return author;
    }

    public void setQuote_author(String author) {
        this.author = author;
    }

    public String getQuote_category_name() {
        return category_name;
    }

    public void setQuote_category_name(String category_name) {
        this.category_name = category_name;
    }

    public int getQuote_category_id() {
        return category_id;
    }

    public void setQuote_category_id(int category_id) {
        this.category_id = category_id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }
}
