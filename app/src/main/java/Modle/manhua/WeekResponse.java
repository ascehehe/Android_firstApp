package Modle.manhua;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by meng on 2017/7/4.
 */

public class WeekResponse {
    private String success;

    @SerializedName("data")
    private List<Manhua> manhuaList;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<Manhua> getManhuaList() {
        return manhuaList;
    }

    public void setManhuaList(List<Manhua> manhuaList) {
        this.manhuaList = manhuaList;
    }
}
