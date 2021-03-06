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
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.cloudlibrary.Data.ListData;
import com.example.cloudlibrary.R;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyPagerListAdapter extends BaseAdapter {
    private List<ListData> list_data=new ArrayList<>();
    private Context context;
    public MyPagerListAdapter(Context context, List<ListData> list_data){
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
            convertView= LayoutInflater.from(context).inflate(R.layout.mypager_like_list,null);
        }
        ImageView picture_list=(ImageView)convertView.findViewById(R.id.picture_list);
        TextView name_list=(TextView)convertView.findViewById(R.id.name_list);
        TextView score_list=(TextView)convertView.findViewById(R.id.score_list);
        ListData listData=list_data.get(position);
        name_list.setText(listData.getTitle());
        score_list.setText(listData.getScore());
        String url=listData.getImg();
        Bitmap bitmap = getHttpBitmap(url);
        picture_list.setImageBitmap(bitmap);
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
}
