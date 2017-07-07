package Modle.manhua;

import java.io.Serializable;

/**
 * Created by meng on 2017/7/4.
 */

public class Manhua implements Serializable{
    private String catid;
    private String catname;
    private String parent_catname;
    private String image;
    private String sum;
    private String title;
    private String updatetime;


    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    public String getCatname() {
        return catname;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }

    public String getParent_catname() {
        return parent_catname;
    }

    public void setParent_catname(String parent_catname) {
        this.parent_catname = parent_catname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
}
