package Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by meng on 2017/7/7.
 */

public class Common {

    /**
     * 通过Gosn反馈回来的日期转成对应星期，如果不存在则返回 null
     * @return weekDate;
     */
    public static String getDate(String WeatherDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");// 定义日期格式
        Date date = null;
        try {
            date = format.parse(WeatherDate);// 将字符串转换为日期
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String weekDate = getWeek(date);
        return weekDate;
    }

    public static String getWeek(Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        String week = sdf.format(date);
        return week;

    }
    /**
     * 截取小时天气时间 eg: 2017-2-16 18:00
     * 保留18:00
     */
    public static String getHour(String Hour){
        String str = Hour;
        String weatherHour = str.substring(10);
        return weatherHour;
    }


}
