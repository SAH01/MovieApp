package com.example.cloudlibrary;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cloudlibrary.Data.AllData;
import com.example.cloudlibrary.Data.ListData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MovieLikeActivity extends AppCompatActivity implements View.OnClickListener{
    TextView movie_title_like;
    TextView movie_star_like;
    TextView movie_director_like;
    TextView movie_type_like;
    TextView movie_area_like;
    TextView movie_date_like;
    TextView movie_summary_like;
    TextView movie_score_like;
    TextView movie_language_like;
    ImageView movie_img_like;
    TextView movie_scorenum_like;
    TextView movie_timelen_like;
    TextView Ten_score_like;
    TextView Ten_vip_like;
    TextView Ai_score_like;
    TextView Ai_vip_like;
    TextView So_score_like;
    TextView So_vip_like;
    TextView score_1905_like;
    TextView vip_1905_like;
    Button movie_want_like;
    Button movie_on_like;
    Button movie_have_like;
    Button movie_remove;

    ListData listData;

    private AllData allData=new AllData();
    private String userphone;
    private String username;
    private String url;
    private String usertype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_like);
        Intent intent=getIntent();
        usertype=intent.getStringExtra("usertype");
        movie_want_like=(Button)findViewById(R.id.movie_want_like);
        movie_on_like=(Button)findViewById(R.id.movie_on_like);
        movie_have_like=(Button)findViewById(R.id.movie_have_like);
        if(usertype.equals("??????")){
            movie_want_like.setBackgroundColor(Color.parseColor("#F8DDFF"));
            movie_on_like.setBackgroundColor(Color.parseColor("#ffffff"));
            movie_have_like.setBackgroundColor(Color.parseColor("#ffffff"));
            movie_want_like.setTextColor(Color.parseColor("#ffffff"));
            movie_on_like.setTextColor(Color.parseColor("#000000"));
            movie_have_like.setTextColor(Color.parseColor("#000000"));
        }
        if(usertype.equals("??????")){
            movie_want_like.setBackgroundColor(Color.parseColor("#ffffff"));
            movie_on_like.setBackgroundColor(Color.parseColor("#F8DDFF"));
            movie_have_like.setBackgroundColor(Color.parseColor("#ffffff"));
            movie_want_like.setTextColor(Color.parseColor("#000000"));
            movie_on_like.setTextColor(Color.parseColor("#ffffff"));
            movie_have_like.setTextColor(Color.parseColor("#000000"));
        }
        if(usertype.equals("??????")){
            movie_want_like.setBackgroundColor(Color.parseColor("#ffffff"));
            movie_on_like.setBackgroundColor(Color.parseColor("#ffffff"));
            movie_have_like.setBackgroundColor(Color.parseColor("#F8DDFF"));
            movie_want_like.setTextColor(Color.parseColor("#000000"));
            movie_on_like.setTextColor(Color.parseColor("#000000"));
            movie_have_like.setTextColor(Color.parseColor("#ffffff"));
        }
        movie_remove=(Button)findViewById(R.id.movie_remove);
        movie_title_like=(TextView)findViewById(R.id.movie_title_like);
        movie_star_like=(TextView)findViewById(R.id.movie_star_like);
        movie_director_like=(TextView)findViewById(R.id.movie_director_like);
        movie_type_like=(TextView)findViewById(R.id.movie_type_like);
        movie_area_like=(TextView)findViewById(R.id.movie_area_like);
        movie_date_like=(TextView)findViewById(R.id.movie_date_like);
        movie_summary_like=(TextView)findViewById(R.id.movie_summary_like);
        movie_score_like=(TextView)findViewById(R.id.movie_score_like);
        movie_language_like=(TextView)findViewById(R.id.movie_language_like);
        movie_img_like=(ImageView)findViewById(R.id.movie_img_like);
        movie_scorenum_like=(TextView)findViewById(R.id.movie_scorenum_like);
        movie_timelen_like=(TextView)findViewById(R.id.movie_timelen_like);
        Ten_score_like=(TextView)findViewById(R.id.Ten_score_like);
        Ten_vip_like=(TextView)findViewById(R.id.Ten_vip_like);
        Ai_score_like=(TextView)findViewById(R.id.Ai_score_like);
        Ai_vip_like=(TextView)findViewById(R.id.Ai_vip_like);
        So_score_like=(TextView)findViewById(R.id.So_score_like);
        So_vip_like=(TextView)findViewById(R.id.So_vip_like);
        score_1905_like=(TextView)findViewById(R.id.score_1905_like);
        vip_1905_like=(TextView)findViewById(R.id.vip_1905_like);
        movie_remove.setOnClickListener(this);
        movie_want_like.setOnClickListener(this);
        movie_on_like.setOnClickListener(this);
        movie_have_like.setOnClickListener(this);
        movie_remove.setOnClickListener(this);
        movie_want_like.setOnClickListener(this);
        movie_on_like.setOnClickListener(this);
        movie_have_like.setOnClickListener(this);
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
                    movie_title_like.setText(array.getString(0));
                    movie_star_like.setText("?????????"+array.getString(1));
                    movie_director_like.setText("?????????"+array.getString(2));
                    movie_type_like.setText("?????????"+array.getString(3));
                    movie_area_like.setText("?????????"+array.getString(4));
                    movie_date_like.setText("???????????????"+array.getString(5));
                    movie_summary_like.setText("?????????"+array.getString(6));
                    movie_score_like.setText(array.getString(7)+"???");
                    movie_language_like.setText("?????????"+array.getString(8));
                    movie_scorenum_like.setText("???????????????"+array.getString(10));
                    movie_timelen_like.setText("?????????"+array.getString(11));
                    if(!array.getString(12).equals("0")){
                        Ten_score_like.setText(array.getString(12)+"???");
                        Ten_vip_like.setText(array.getString(13));
                        Log.e("TAG",array.getString(12));
                    }else{
                        Ten_score_like.setText("????????????");
                        Ten_vip_like.setText("");
                    }
                    if(!array.getString(15).equals("0")){
                        Ai_score_like.setText(array.getString(15)+"???");
                        Ai_vip_like.setText(array.getString(16 ));
                    }else{
                        Ai_score_like.setText("????????????");
                        Ai_vip_like.setText("");
                    }
                    if(!array.getString(18).equals("0")){
                        So_score_like.setText(array.getString(18)+"???");
                        So_vip_like.setText(array.getString(19 ));
                    }else{
                        So_score_like.setText("????????????");
                        So_vip_like.setText("");
                    }
                    //1905?????????
                    if(!array.getString(18).equals("0")){
                        score_1905_like.setText(array.getString(18)+"???");
                        vip_1905_like.setText(array.getString(19 ));
                    }else{
                        score_1905_like.setText("????????????");
                        vip_1905_like.setText("");
                    }
                    url=array.getString(9);
                    Bitmap bitmap = getHttpBitmap(url);
                    movie_img_like.setImageBitmap(bitmap);
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
            case R.id.movie_want_like:
                movie_want_like.setBackgroundColor(Color.parseColor("#F8DDFF"));
                movie_on_like.setBackgroundColor(Color.parseColor("#ffffff"));
                movie_have_like.setBackgroundColor(Color.parseColor("#ffffff"));
                movie_want_like.setTextColor(Color.parseColor("#ffffff"));
                movie_on_like.setTextColor(Color.parseColor("#000000"));
                movie_have_like.setTextColor(Color.parseColor("#000000"));
                http_like_trans(userphone,movie_title_like.getText().toString(),usertype,movie_scorenum_like.getText().toString().substring(5),"??????");
                break;
            case R.id.movie_on_like:
                movie_want_like.setBackgroundColor(Color.parseColor("#ffffff"));
                movie_on_like.setBackgroundColor(Color.parseColor("#F8DDFF"));
                movie_have_like.setBackgroundColor(Color.parseColor("#ffffff"));
                movie_want_like.setTextColor(Color.parseColor("#000000"));
                movie_on_like.setTextColor(Color.parseColor("#ffffff"));
                movie_have_like.setTextColor(Color.parseColor("#000000"));
                http_like_trans(userphone,movie_title_like.getText().toString(),usertype,movie_scorenum_like.getText().toString().substring(5),"??????");
                break;
            case R.id.movie_have_like:
                movie_want_like.setBackgroundColor(Color.parseColor("#ffffff"));
                movie_on_like.setBackgroundColor(Color.parseColor("#ffffff"));
                movie_have_like.setBackgroundColor(Color.parseColor("#F8DDFF"));
                movie_want_like.setTextColor(Color.parseColor("#000000"));
                movie_on_like.setTextColor(Color.parseColor("#000000"));
                movie_have_like.setTextColor(Color.parseColor("#ffffff"));
                http_like_trans(userphone,movie_title_like.getText().toString(),usertype,movie_scorenum_like.getText().toString().substring(5),"??????");
                break;
            case R.id.movie_remove:
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("??????").setIcon(R.mipmap.ic_launcher).setMessage("????????????????????????");
                builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MovieRemove(userphone,usertype,movie_title_like.getText().toString(),movie_scorenum_like.getText().toString().substring(5));
                    }
                }).setNegativeButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog ad=builder.create();
                ad.show();
                break;
        }
    }

    public void http_like_trans(String userphone,String usermovie,String usertype,String scorenum,String usertype_new){
        RequestParams params = new RequestParams("http://"+allData.getUrl()+":5000/android_user_like_trans");
        //params.setMultipart(true);
        params.addBodyParameter("userphone",userphone);
        params.addBodyParameter("usermovie",usermovie);
        params.addBodyParameter("usertype",usertype);
        params.addBodyParameter("scorenum",scorenum);
        params.addBodyParameter("usertype_new",usertype_new);
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
                        Toast.makeText(MovieLikeActivity.this,"????????????:"+usertype_new,Toast.LENGTH_LONG).show();
                    }
                    if(Flag==0){
                        Toast.makeText(MovieLikeActivity.this,"????????????:"+usertype_new,Toast.LENGTH_LONG).show();
                    }
                    if(Flag==-1){
                        Toast.makeText(MovieLikeActivity.this,"?????????:"+usertype,Toast.LENGTH_LONG).show();
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
    public void MovieRemove(String userphone,String usertype,String usermovie,String scorenum){
        RequestParams params = new RequestParams("http://"+allData.getUrl()+":5000/android_delete");
        //params.setMultipart(true);
        params.addBodyParameter("userphone",userphone);
        params.addBodyParameter("usermovie",usermovie);
        params.addBodyParameter("usertype",usertype);
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
                    JSONObject object= null;
                    object = new JSONObject(result);
                    int Flag=object.optInt("data");
                    if(Flag==1){
                        Toast.makeText(MovieLikeActivity.this,"????????????",Toast.LENGTH_LONG).show();
                    }
                    if(Flag==0){
                        Toast.makeText(MovieLikeActivity.this,"????????????",Toast.LENGTH_LONG).show();
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