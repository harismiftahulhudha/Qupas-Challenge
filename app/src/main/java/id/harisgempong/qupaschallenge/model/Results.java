package id.harisgempong.qupaschallenge.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Results {
    @SerializedName("Search")
    private final List<Result> results;
    @SerializedName("totalResults")
    private final String total;
    @SerializedName("Response")
    private final String response;
    @SerializedName("Error")
    private final String error;

    public Results(List<Result> results, String total, String response, String error) {
        this.results = results;
        this.total = total;
        this.response = response;
        this.error = error;
    }

    public List<Result> getResults() {
        return results;
    }

    public String getTotal() {
        return total;
    }

    public String getResponse() {
        return response;
    }

    public String getError() {
        return error;
    }
}
