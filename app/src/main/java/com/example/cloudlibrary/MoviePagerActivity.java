package com.example.cloudlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cloudlibrary.Adapter.CloudPagerAdapter;
import com.example.cloudlibrary.Data.AllData;
import com.example.cloudlibrary.Data.ListData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;
import org.xutils.common.Callback;
import org.xutils.db.table.TableEntity;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MoviePagerActivity extends AppCompatActivity implements View.OnClickListener{
    TextView movie_title;
    TextView movie_star;
    TextView movie_director;
    TextView movie_type;
    TextView movie_area;
    TextView movie_date;
    TextView movie_summary;
    TextView movie_score;
    TextView movie_language;
    ImageView movie_img;
    TextView movie_scorenum;
    TextView movie_timelen;
    TextView Ten_score;
    TextView Ten_vip;
    TextView Ai_score;
    TextView Ai_vip;
    TextView So_score;
    TextView So_vip;
    TextView score_1905;
    TextView vip_1905;
    Button movie_want;
    Button movie_on;
    Button movie_have;

    ListData listData;

    private AllData allData=new AllData();
    private String userphone;
    private String username;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_pager);
        movie_title=(TextView)findViewById(R.id.movie_title);
        movie_star=(TextView)findViewById(R.id.movie_star);
        movie_director=(TextView)findViewById(R.id.movie_director);
        movie_type=(TextView)findViewById(R.id.movie_type);
        movie_area=(TextView)findViewById(R.id.movie_area);
        movie_date=(TextView)findViewById(R.id.movie_date);
        movie_summary=(TextView)findViewById(R.id.movie_summary);
        movie_score=(TextView)findViewById(R.id.movie_score);
        movie_language=(TextView)findViewById(R.id.movie_language);
        movie_img=(ImageView)findViewById(R.id.movie_img);
        movie_scorenum=(TextView)findViewById(R.id.movie_scorenum);
        movie_timelen=(TextView)findViewById(R.id.movie_timelen);
        Ten_score=(TextView)findViewById(R.id.Ten_score);
        Ten_vip=(TextView)findViewById(R.id.Ten_vip);
        Ai_score=(TextView)findViewById(R.id.Ai_score);
        Ai_vip=(TextView)findViewById(R.id.Ai_vip);
        So_score=(TextView)findViewById(R.id.So_score);
        So_vip=(TextView)findViewById(R.id.So_vip);
        score_1905=(TextView)findViewById(R.id.score_1905);
        vip_1905=(TextView)findViewById(R.id.vip_1905);
        movie_want=(Button)findViewById(R.id.movie_want);
        movie_on=(Button)findViewById(R.id.movie_on);
        movie_have=(Button)findViewById(R.id.movie_have);
        movie_want.setOnClickListener(this);
        movie_on.setOnClickListener(this);
        movie_have.setOnClickListener(this);
        Intent intent=getIntent();
        String movie_title_str=intent.getStringExtra("title");
        String movie_scorenum_str=intent.getStringExtra("scorenum");
        set_movie(movie_title_str,movie_scorenum_str);
        SharedPreferences sp=getSharedPreferences("userdata",MODE_PRIVATE);
        userphone=(sp.getString("user_phone",""));
        username=(sp.getString("user_name",""));
    }
    public void set_movie(String title,String scorenum){
        RequestParams params = new RequestParams("http://"+allData.getUrl()+":5000/android_query");
        //params.setMultipart(true);
        params.addBodyParameter("title",title);
        params.addBodyParameter("scorenum",scorenum);
        //params.addBodyParameter("File",new File(Environment.getExternalStorageDirectory()+"/test_download/YLY.jpg"),null,"YLY.jpg");
        x.http().get(params, new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                return false;
            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object=new JSONObject(result);
                    JSONArray array=object.optJSONArray("data");
                    movie_title.setText(array.getString(0));
                    movie_star.setText("?????????"+array.getString(1));
                    movie_director.setText("?????????"+array.getString(2));
                    movie_type.setText("?????????"+array.getString(3));
                    movie_area.setText("?????????"+array.getString(4));
                    movie_date.setText("???????????????"+array.getString(5));
                    movie_summary.setText("?????????"+array.getString(6));
                    movie_score.setText(array.getString(7)+"???");
                    movie_language.setText("?????????"+array.getString(8));
                    movie_scorenum.setText("???????????????"+array.getString(10));
                    movie_timelen.setText("?????????"+array.getString(11));
                    //????????????
                    if(!array.getString(12).equals("0")){
                        Ten_score.setText(array.getString(12)+"???");
                        Ten_vip.setText(array.getString(13));
                    }else{
                        Ten_score.setText("????????????");
                        Ten_vip.setText("");
                    }
                    //?????????
                    if(!array.getString(15).equals("0")){
                        Ai_score.setText(array.getString(15)+"???");
                        Ai_vip.setText(array.getString(16 ));
                    }else{
                        Ai_score.setText("????????????");
                        Ai_vip.setText("");
                    }
                    //????????????
                    if(!array.getString(18).equals("0")){
                        So_score.setText(array.getString(18)+"???");
                        So_vip.setText(array.getString(19 ));
                    }else{
                        So_score.setText("????????????");
                        So_vip.setText("");
                    }
                    //1905?????????
                    if(!array.getString(18).equals("0")){
                        score_1905.setText(array.getString(18)+"???");
                        vip_1905.setText(array.getString(19 ));
                    }else{
                        score_1905.setText("????????????");
                        vip_1905.setText("");
                    }
                    url=array.getString(9);
                    Bitmap bitmap = getHttpBitmap(url);
                    movie_img.setImageBitmap(bitmap);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TAG","onError=="+ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("TAG","onCancelled=="+cex.toString());
            }

            @Override
            public void onFinished() {
                Log.e("TAG","onFinished");
            }
        });
    }
    public static Bitmap getHttpBitmap(String url){
        URL myFileURL;
        Bitmap bitmap=null;
        try{
            myFileURL = new URL(url);
            //????????????
            HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();
            Log.e("TAG",conn.toString());
            //?????????????????????6000?????????conn.setConnectionTiem(0);????????????????????????
            conn.setConnectTimeout(6000);
            //???????????????????????????
            conn.setDoInput(true);
            //??????????????????
            conn.setRequestMethod("GET");
            //???????????????
            conn.setUseCaches(false);
            //??????????????????
            int code = conn.getResponseCode();
            //?????????????????????????????????
            //conn.connect();
            //???????????????
            InputStream is = conn.getInputStream();
            //??????????????????
            bitmap = BitmapFactory.decodeStream(is);
            //???????????????
            is.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.movie_want:
                http_like(userphone,movie_title.getText().toString(),"??????",movie_scorenum.getText().toString().substring(5)
                        ,url,movie_score.getText().toString().substring(0,3));
                break;
            case R.id.movie_on:
                http_like(userphone,movie_title.getText().toString(),"??????",movie_scorenum.getText().toString().substring(5)
                        ,url,movie_score.getText().toString().substring(0,3));
                break;
            case R.id.movie_have:
                http_like(userphone,movie_title.getText().toString(),"??????",movie_scorenum.getText().toString().substring(5)
                        ,url,movie_score.getText().toString().substring(0,3));
                break;
        }
    }
    public void http_like(String userphone,String usermovie,String usertype,String scorenum,String img,String score){
        RequestParams params = new RequestParams("http://"+allData.getUrl()+":5000/android_like");
        //params.setMultipart(true);
        params.addBodyParameter("userphone",userphone);
        params.addBodyParameter("usermovie",usermovie);
        params.addBodyParameter("usertype",usertype);
        params.addBodyParameter("scorenum",scorenum);
        params.addBodyParameter("url",url);
        params.addBodyParameter("score",score);
        //params.addBodyParameter("File",new File(Environment.getExternalStorageDirectory()+"/test_download/YLY.jpg"),null,"YLY.jpg");
        x.http().get(params, new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                return false;
            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object= null;
                    object = new JSONObject(result);
                    int Flag=object.optInt("data");
                    if(Flag==1){
                        Toast.makeText(MoviePagerActivity.this,"???????????????"+usertype,Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(MoviePagerActivity.this,"????????????",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TAG","onError=="+ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("TAG","onCancelled=="+cex.toString());
            }

            @Override
            public void onFinished() {
                Log.e("TAG","onFinished");
            }
        });
    }
}