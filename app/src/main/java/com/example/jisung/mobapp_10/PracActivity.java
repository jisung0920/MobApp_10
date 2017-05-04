package com.example.jisung.mobapp_10;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class PracActivity extends AppCompatActivity {

    EditText e1;
    LinearLayout linear;
    WebView web1;
    ProgressDialog dialog;
    Animation animTop;
    LinearLayout l1;
    ListView listview;
    ArrayList<urlData> urllist= new ArrayList<urlData>();
    urlAdapter adapter;
    Handler myhandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prac);

        init();

        adapter = new urlAdapter(this,urllist);
        urlData data = new urlData("네이버","http://blog.naver.com");
        urllist.add(data);
        for(int i =0;i<urllist.size();i++)
            Log.d("compare","urlList :"+urllist.get(i).name);
       // urllist.add(new urlData("다음","http://www.daum.net"));
        listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        dialog = new ProgressDialog(this);



        web1.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if(newProgress>=100)
                    dialog.dismiss();
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });
        web1.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                dialog.setMessage("Loading");
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.show();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        WebSettings webSettings = web1.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        web1.loadUrl("http://blog.naver.com/jisung0920");


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listview.setVisibility(View.INVISIBLE);
                linear.setVisibility(View.VISIBLE);
                web1.loadUrl(urllist.get(position).url);
                e1.setText(urllist.get(position).url);
            }
        });
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final int i = position;
                        AlertDialog.Builder dlg = new AlertDialog.Builder(PracActivity.this);
                        dlg.setTitle("삭제").setMessage("삭제 하시겠습니까?")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        urllist.remove(i);
                                        adapter.notifyDataSetChanged();
                                    }
                                }).setNegativeButton("cancel", null).show();

                return true;
            }
        });


    }

    void init() {
        e1 = (EditText)findViewById(R.id.e1);
        linear = (LinearLayout)findViewById(R.id.linear);
        listview = (ListView)findViewById(R.id.list1);
        l1 = (LinearLayout)findViewById(R.id.l1);
        web1 = (WebView) findViewById(R.id.web1);
        web1.addJavascriptInterface(new PracActivity.JavaScriptMethodsCustom(),"myApp");
        animTop = AnimationUtils.loadAnimation(this, R.anim.trans);
        animTop.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                l1.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,1,0,"즐겨찾기 추가");
        menu.add(0,2,0,"즐겨찾기 목록");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==1) {
            l1.setAnimation(animTop);
            animTop.start();
            linear.setVisibility(View.VISIBLE);
            listview.setVisibility(View.INVISIBLE);
            web1.loadUrl("file:///android_asset/www/urladd.html");

        }
        else if(item.getItemId()==2){
            listview.setVisibility(View.VISIBLE);
            linear.setVisibility(View.INVISIBLE);

        }
        return super.onOptionsItemSelected(item);
    }


    class JavaScriptMethodsCustom {
        JavaScriptMethodsCustom(){}


        @JavascriptInterface
        public void nowURL(){
            Log.d("compare","urlClick");

            myhandler.post(new Runnable() {
                               @Override
                               public void run() {

                                   l1.setVisibility(View.VISIBLE);

                               }
                           }
            );
            Log.d("compare","urlClickCOmplete");
        }


        @JavascriptInterface
        public void receiveURL(String name,String value){
            if(sameCheck(value)){
                Log.d("compare"," casfsdfall--- ");
                myhandler.post(new Runnable() {
                                   @Override
                                   public void run() {

                                       web1.loadUrl("javascript:displayMsg()");

                                   }
                               }
                );

                Log.d("compare"," call--- ");
            }
            else {
                Toast.makeText(getApplicationContext(), value + "가 추가되었습니다.", Toast.LENGTH_SHORT).show();
                urllist.add(new urlData(name,value));
                for(int i =0;i<urllist.size();i++)
                    Log.d("compare","urlList :"+urllist.get(i).name);
                adapter.notifyDataSetChanged();
                //러너블에 있는걸 집어 넣겠다.

            }
        }

        @JavascriptInterface
        public Boolean sameCheck(String url){
            for(int i=0;i<urllist.size();i++) {
                Log.d("compare",url+" --- "+urllist.get(i).url);
                if (urllist.get(i).url.equals(url))
                    return true;
            }
            return false;
        }
    }

    void onClick(View v){
        if(v.getId()==R.id.b1){
            web1.loadUrl(e1.getText().toString());
        }
    }

}
