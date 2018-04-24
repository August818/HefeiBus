package com.hefeibus.www.hefeibus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestActivity extends AppCompatActivity {

    private Runnable network;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Button button = (Button) findViewById(R.id.button3);

        network = new Runnable() {
            @Override
            public void run() {
                try {
                    String url1 = "http://www.hfbus.cn/map/AjaxHandler/BusLineHandler.ashx?type=GetStationInfoList&StationName=省旅游学校";
                    URL url = new URL(url1);
                    //得到connection对象。
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //设置请求方式
                    connection.setRequestMethod("GET");
                    //连接
                    connection.connect();
                    //得到响应码
                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        //得到响应流
                        InputStream inputStream = connection.getInputStream();
                        //将响应流转换成字符串
                        String result = inputStream2String(inputStream);//将流转换为字符串。
                        Log.d("kwwl", "result=============" + result);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(network).start();
            }
        });
    }

    public String inputStream2String(InputStream in) throws IOException {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1; ) {
            out.append(new String(b, 0, n));
        }
        return out.toString();
    }


}


