package model;

import java.io.Serializable;

/**
 * Author : Andrej Ščasný
 * Date : 03.05.2017
 */
public class Movie implements Serializable {
    private String title;
    private int year;
    private String director;
    private int length;
    private String genre;
    private int imdbRating;

    public Movie(String title, int year, String director, int length, String genre, int imdbRating) {
        this.title = title;
        this.year = year;
        this.director = director;
        this.length = length;
        this.genre = genre;
        this.imdbRating = imdbRating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(int imdbRating) {
        this.imdbRating = imdbRating;
    }
}
