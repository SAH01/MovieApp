package com.example.cloudlibrary.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cloudlibrary.Data.ListData;
import com.example.cloudlibrary.R;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CloudPagerAdapter extends BaseAdapter {
    private List<ListData> list_data=new ArrayList<>();
    private Context context;
    public CloudPagerAdapter(Context context, List<ListData> list_data){
        this.context=context;
        this.list_data=list_data;
    }
    @Override
    public int getCount() {
        return list_data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(context).inflate(R.layout.first_pager_list,null);
        }
        ImageView first_img=(ImageView)convertView.findViewById(R.id.first_img);
        TextView first_name=(TextView)convertView.findViewById(R.id.first_name);
        TextView first_score=(TextView)convertView.findViewById(R.id.first_score);
        TextView first_star=(TextView)convertView.findViewById(R.id.first_satr);
        TextView first_scorenum=(TextView)convertView.findViewById(R.id.first_scorenum);
        TextView first_director=(TextView)convertView.findViewById(R.id.first_director);
        ListData listData=list_data.get(position);
        first_name.setText(listData.getTitle());
        first_score.setText(listData.getScore()+"???");
        first_director.setText("?????????"+and_so_on(listData.getDirector()));
        first_scorenum.setText(listData.getScorenum()+"?????????");
        first_star.setText("?????????"+and_so_on(listData.getStar()));
        String url=listData.getImg();
        Bitmap bitmap = getHttpBitmap(url);
        first_img.setImageBitmap(bitmap);
        return convertView;
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
    public String and_so_on(String str){
        if(str.length()>=12){
            str=str.substring(0,11)+"...";
        }
        return str;
    }
}
