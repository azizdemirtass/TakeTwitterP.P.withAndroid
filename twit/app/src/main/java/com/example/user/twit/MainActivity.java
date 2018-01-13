package com.example.user.twit;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    String url = "https://twitter.com/";

    ProgressDialog mProgressDialog;
    Button btn;
    EditText user;
    TextView usern;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        user=(EditText)findViewById(R.id.edit_username);

        btn=(Button)findViewById(R.id.twit_buton);
        usern=(TextView)findViewById(R.id.username);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new twitter().execute();
                url="https://twitter.com/";
                url=url+user.getText();

            }
        });
    }

    private class twitter extends AsyncTask<Void, Void, Void> {

        String uname;
        Bitmap bitmap;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("TWİTTER");
            mProgressDialog.setMessage("Lütfen Bekleyiniz...!");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Connection.Response response = Jsoup.connect(url).timeout(30000).execute();
                Document doc = (Document) response.parse();
                Elements ad=doc.select("img[class=\"ProfileAvatar-image\"]");
                uname =ad.attr("alt");
                Elements images = doc.select("img[class=\"ProfileAvatar-image\"]");
                String imgSrc =images.attr("src");
                InputStream input = new java.net.URL(imgSrc).openStream();
                bitmap = BitmapFactory.decodeStream(input);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set title into TextView

            usern.setText(uname);
            ImageView logoimg = (ImageView)findViewById(R.id.logo);
            logoimg.setImageBitmap(bitmap);
            mProgressDialog.dismiss();
        }
    }

}
