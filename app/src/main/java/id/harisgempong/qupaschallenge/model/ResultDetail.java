package id.harisgempong.qupaschallenge.model;

import com.google.gson.annotations.SerializedName;

public class ResultDetail {
    @SerializedName("Title")
    private final String title;
    @SerializedName("Year")
    private final String year;
    @SerializedName("Runtime")
    private final String duration;
    @SerializedName("Genre")
    private final String genre;
    @SerializedName("imdbRating")
    private final String rating;
    @SerializedName("Type")
    private final String type;
    @SerializedName("Country")
    private final String country;
    @SerializedName("Poster")
    private final String image;
    @SerializedName("Released")
    private final String release;
    @SerializedName("Director")
    private final String director;
    @SerializedName("Writer")
    private final String writer;
    @SerializedName("Actors")
    private final String actor;
    @SerializedName("Plot")
    private final String plot;
    @SerializedName("Language")
    private final String language;
    @SerializedName("Production")
    private final String production;
    @SerializedName("imdbID")
    private final String id;
    @SerializedName("Response")
    private final String response;

    public ResultDetail(String title, String year, String duration, String genre, String rating, String type, String country, String image, String release, String director, String writer, String actor, String plot, String language, String production, String id, String response) {
        this.title = title;
        this.year = year;
        this.duration = duration;
        this.genre = genre;
        this.rating = rating;
        this.type = type;
        this.country = country;
        this.image = image;
        this.release = release;
        this.director = director;
        this.writer = writer;
        this.actor = actor;
        this.plot = plot;
        this.language = language;
        this.production = production;
        this.id = id;
        this.response = response;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getDuration() {
        return duration;
    }

    public String getGenre() {
        return genre;
    }

    public String getRating() {
        return rating;
    }

    public String getType() {
        return type;
    }

    public String getCountry() {
        return country;
    }

    public String getImage() {
        return image;
    }

    public String getRelease() {
        return release;
    }

    public String getDirector() {
        return director;
    }

    public String getWriter() {
        return writer;
    }

    public String getActor() {
        return actor;
    }

    public String getPlot() {
        return plot;
    }

    public String getLanguage() {
        return language;
    }

    public String getProduction() {
        return production;
    }

    public String getId() {
        return id;
    }

    public String getResponse() {
        return response;
    }
}
