package com.example.jisung.mobapp_10;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText e1;
    WebView web1;
    ProgressDialog dialog;
    Animation animTop;
    LinearLayout l1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        dialog = new ProgressDialog(this);
        WebSettings webSettings = web1.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

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
                e1.setText(url);
            }
        });

        web1.loadUrl("http://blog.naver.com/jisung0920");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,1,0,"즐겨찾기");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==1) {
            web1.loadUrl("file:///android_asset/www/index.html");
            l1.setAnimation(animTop);
            animTop.start();
        }
        return super.onOptionsItemSelected(item);
    }

    void init(){
        l1 = (LinearLayout)findViewById(R.id.l1);
        e1 = (EditText)findViewById(R.id.e1);
        web1 = (WebView)findViewById(R.id.web1);
        animTop = AnimationUtils.loadAnimation(this,R.anim.trans);
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
        web1.addJavascriptInterface(new JavaScriptMethodsCustom(),"myApp"); // java interface 를 설정한 후
        //web뷰가 js를 사용할 수 있도록 하는거 2번째 매개변수는 얘를 사용할 때 이름을 지정한다.(여러개 사용할 수 있다.)

    }


    class JavaScriptMethodsCustom {
        JavaScriptMethodsCustom(){}


        @JavascriptInterface
        public void display(){
            Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
            myhandler.post(new Runnable() {
                               @Override
                               public void run() {
                                   AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                                   dlg.setTitle("그림변경").setMessage("그림을 변경하시겠습니까>")
                                           .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                               @Override
                                               public void onClick(DialogInterface dialog, int which) {
                                                   web1.loadUrl("javascript:changeImage()");
                                               }
                                           }).setNegativeButton("cancel", null).show();
                               }
                           }
                );

            //러너블에 있는걸 집어 넣겠다.

        }
    }
    public void onClick(View v){
        if(v.getId()==R.id.b2){
            web1.loadUrl("javascript:changeImage()");
        }
    }

    Handler myhandler = new Handler();


}
