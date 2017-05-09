package model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Author : Andrej Ščasný
 * Date : 03.05.2017
 */
public class Movie implements Serializable {
    private String imdbid;
    private String title;
    private String year;
    private String director;
    private String length;
    private String genre;
    private String imdbRating;
    private Timestamp added_at;
    private int added_by;

    public Movie(String imdbid, String title, String year, String director, String length, String genre, String imdbRating, Timestamp added_at, int added_by) {
        this.imdbid = imdbid;
        this.title = title;
        this.year = year;
        this.director = director;
        this.length = length;
        this.genre = genre;
        this.imdbRating = imdbRating;
        this.added_at = added_at;
        this.added_by = added_by;
    }

    public String getImdbid() {
        return imdbid;
    }

    public void setImdbid(String imdbid) {
        this.imdbid = imdbid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public Timestamp getAdded_at() {
        return added_at;
    }

    public void setAdded_at(Timestamp added_at) {
        this.added_at = added_at;
    }

    public int getAdded_by() {
        return added_by;
    }

    public void setAdded_by(int added_by) {
        this.added_by = added_by;
    }
}
