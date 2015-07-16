package com.example.myapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    @SuppressLint("NewApi")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void buttonOnClick01(View view) {


        HttpResponse httpResponse	= null;
        HttpEntity httpEntity		= null;
        TextView textview = (TextView)findViewById(R.id.helloText   );


        // 生成一个请求对象，参数就是地址
        HttpGet httpGet = new HttpGet("http://192.168.2.116:8088/AndroidService/hello?username=htp&password=123");
        // 生成Http客户端
        HttpClient httpClient = new DefaultHttpClient();
        InputStream inputStream = null;
        // 使用HTTP客户端发送请求对象
        try
        {
            // 发送请求的响应
            httpResponse = httpClient.execute(httpGet);
            // 代表接收的http消息，服务器返回的消息都在httpEntity
            httpEntity = httpResponse.getEntity();
            if(httpResponse.getStatusLine().getStatusCode() == 200){
                inputStream = httpEntity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String result = "";
                String line = "";
                while ((line = reader.readLine()) != null)
                {
                    result = result + line;
                    System.out.println(result);
                }

                textview.setText(result);
            }
        }
        catch (ClientProtocolException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                inputStream.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

    }
}
