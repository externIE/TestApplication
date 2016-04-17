package com.example.b41_cx.myapplication1;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.DrawableRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import engine.Engine;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import model.BannerModel;
import model.BannerPage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        initWebView();
        initBannarView();
    }

    private TabLayout tabLayout;
    private void initInstances() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
    }
    private void initTabLayout(){

    }

    private WebView m_wv;

    private void initWebView(){
//        m_wv = (WebView)findViewById(R.id.webView);
//        WebSettings settings = m_wv.getSettings();
//        settings.setJavaScriptEnabled(true);
//        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        m_wv.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                // TODO Auto-generated method stub
//                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
//                view.loadUrl(url);
//                return true;
//            }
//        });
//        m_wv.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                // TODO Auto-generated method stub
//                if (newProgress == 100) {
//                    // 网页加载完成
//                    // 结束下拉刷新...
//                    Snackbar.make(view, "加载完成", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                } else {
//                    // 加载中
//                    Snackbar.make(view, "加载中", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                }
//
//            }
//        });
//        m_wv.loadUrl("http://externie.github.io/externieblog/");


    }

    private List<ImageView> mDefaultViews;
    private BGABanner banner;
    private void initBannarView(){
        banner = (BGABanner)findViewById(R.id.banner_main_default);
//        mDefaultViews = getImageViews(3);
//        banner.setViews( getImageViews(4));
//        banner.setViews(mDefaultViews);
        mDefaultViews = getImageViews(4);
        banner.setViews(mDefaultViews);
        Thread t = Thread.currentThread();
        System.out.println("当前线程名字：" + t.getName() + " 当前线程的优先级别为："+ t.getPriority() + " ID:" + t.getId());
        Engine mEngine = new Retrofit.Builder()
                .baseUrl("http://externie.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(Engine.class);
        mEngine.fiveItem().enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Call<BannerModel> call, Response<BannerModel> response) {
                BannerModel bannerModel = response.body();
                List<String> tips = new ArrayList<String>();
//                ScrollingActivity.this.mDefaultViews = getImageViews(bannerModel.pages.size());
//                banner.setViews(mDefaultViews);
                Thread t = Thread.currentThread();
                System.out.println("当前线程名字：" + t.getName() + " 当前线程的优先级别为：" + t.getPriority() + " ID:" + t.getId());
                for (int i = 0; i < bannerModel.pages.size(); i++) {
                    Glide.with(ScrollingActivity.this).load(bannerModel.pages.get(i).img).placeholder(R.drawable.holdimg).error(R.drawable.holdimg).into(mDefaultViews.get(i));
                    tips.add(bannerModel.pages.get(i).profile);
                    // 为每一页添加点击事件
                    final int finalPosition = i;
                    mDefaultViews.get(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            Toast.makeText(App.getInstance(), "点击了第" + (finalPosition + 1) + "页", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
//                setImageViewsToBanner();

                ScrollingActivity.this.banner.setTips(tips);
                System.out.println("成功哦");
            }

            @Override
            public void onFailure(Call<BannerModel> call, Throwable t) {
                System.out.println("失败");
            }
        });

    }

    private List<ImageView> getImageViews(int count) {
        List<ImageView> views = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            views.add((ImageView) getLayoutInflater().inflate(R.layout.view_image, null));
        }
        return views;
    }

    private List<ImageView> mallocNewImageView(List<ImageView> views){
        views.add((ImageView) getLayoutInflater().inflate(R.layout.view_image, null));
        return views;
    }

    private void setImageViewsToBanner(){
        banner.setViews(mDefaultViews);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
