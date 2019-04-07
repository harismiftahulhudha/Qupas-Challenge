package id.harisgempong.qupaschallenge.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Result implements Parcelable {
    @SerializedName("imdbID")
    private final String imdbID;
    @SerializedName("Type")
    private final String type;
    @SerializedName("Title")
    private final String title;
    @SerializedName("Year")
    private final String year;
    @SerializedName("Poster")
    private final String poster;

    public Result(String imdbID, String type, String title, String year, String poster) {
        this.imdbID = imdbID;
        this.type = type;
        this.title = title;
        this.year = year;
        this.poster = poster;
    }

    private Result(Parcel in) {
        imdbID = in.readString();
        type = in.readString();
        title = in.readString();
        year = in.readString();
        poster = in.readString();
    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };

    public String getImdbID() {
        return imdbID;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getPoster() {
        return poster;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imdbID);
        dest.writeString(type);
        dest.writeString(title);
        dest.writeString(year);
        dest.writeString(poster);
    }
}
