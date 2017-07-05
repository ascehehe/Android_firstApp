package Network;

import android.text.format.DateFormat;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import java.io.IOException;
import java.io.PipedOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import Utils.MD5Util;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by meng on 2017/7/3.
 */

public class APIClient {


    public  String post(String url)  throws IOException{
        OkHttpClient client = new OkHttpClient();
        String str= geneSignUrl(APIConstants.SERCER_HOST_URL+ url);
        Request request=new Request.Builder()
                .url(str)
                .build();
        Log.i("post_url", "url= "+str);
        Response response = client.newCall(request).execute();
        String resultStr = response.body().string();
        return resultStr;

    }



    private String geneSignUrl(String orginUrl)
    {
        String preString="http://www.quhua.com/index.php?";
        String valueString=orginUrl.substring(preString.length());
        String[]valuesArr= valueString.split("&");
        String paramsString="";
        for(String tmp:valuesArr)
        {
            String[] tempArr=tmp.split("=");

            String key= tempArr[0];
            if (key.equals("m")||key.equals("c")||key.equals("a"))
            {
                paramsString=paramsString+tempArr[1];
            }
        }

        String timeStamp=getTimeStamp();
        String signStr=paramsString+timeStamp+"#$@%!*";
        String md5String= MD5Util.md5(signStr);
        String urlStr=orginUrl+"&token="+md5String.toLowerCase()+"&time="+timeStamp;
        return urlStr;
    }

    private String getTimeStamp()
    {
        Date date=new Date();
        SimpleDateFormat format=new SimpleDateFormat("YYYY-MM-dd-HH");
        String time= format.format(date);
        return time;
    }
}
