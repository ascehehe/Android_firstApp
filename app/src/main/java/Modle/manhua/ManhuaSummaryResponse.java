package Modle.manhua;

import java.io.Serializable;
import java.util.List;

/**
 * Created by meng on 2017/7/5.
 */

public class ManhuaSummaryResponse {
     public String  success;
    public  List<ManhuaSummary>data;

    public static  class  ManhuaSummary implements Serializable
    {
        public  String id;
        public  String catid;
        public  String title;
        public  String thumb;
        public  String views;
        public  String inputtime;
    }

}
