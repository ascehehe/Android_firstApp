package Modle;

import java.util.List;

/**
 * Created by meng on 2017/7/5.
 */

public class ManhuaDetailResponse
{
    public ManhuaDetail data;
    public static  class ManhuaDetail
    {
        public   String id;
        public  String title;
        public  String comment_sum;
        public List<String> content;

    }

}
