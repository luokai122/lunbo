package toprs.cn.lunbo;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.zxy.recovery.core.Recovery;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import toprs.cn.lunbo.Final.App;
import toprs.cn.lunbo.bean.ItemBean;
import toprs.cn.lunbo.bean.TownArticle;
import toprs.cn.lunbo.loader.GlideImageLoader;
import toprs.cn.lunbo.loader.MyAdapter;


public class MainActivity extends AppCompatActivity implements  OnBannerListener {
    public static List<?> images=new ArrayList<>();
    public static List<String> titles=new ArrayList<>();
    public static int H,W;
    Banner banner;

    public int pageNumber = 0;
    RequestQueue requestQueue = Volley.newRequestQueue(App.getContext());
    private List<ItemBean> blist;
    private MyAdapter myAdapter;
    private RecyclerView recyclerView;
    RefreshLayout refreshLayout;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getimg();
        setContentView(R.layout.activity_main);



        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        banner = (Banner) findViewById(R.id.banner);
        banner.getLayoutParams().height=H/4;
        banner.getLayoutParams().width=W;

        //简单使用
        banner.setImages(images)
                .setBannerTitles(titles)
                .setImageLoader(new GlideImageLoader())
                .setIndicatorGravity(BannerConfig.CENTER)

                .start();
        final String [] url = {"https://www.douyu.com"};


        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Context mContext = App.getContext();
                Intent intent = new Intent(mContext, Xian.class);
                switch (position) {
                    case 0:
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("data",url[0]);
                        mContext.startActivity(intent);
                        break;
                    case 1:

                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("data",url[0]);
                        mContext.startActivity(intent);
                        break;
                    case 2:
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("data",url[0]);
                        mContext.startActivity(intent);
                        break;
                    case 3:
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("data",url[0]);
                        mContext.startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });// 让主活动实现OnBannerListener接口




        refreshLayout = (RefreshLayout)findViewById(R.id.refreshLayout);
        initDate();
        setPullRefresher();

    }


    private void initDate(){
        blist = new ArrayList<ItemBean>();

        blist.add(new ItemBean(
                "https://pic.cnblogs.com/avatar/1142647/20170416093225.png",
                "initTitle"+1,
                "https://www.zhihu.com/"
        ));

        blist.add(new ItemBean(
                "https://pic.cnblogs.com/avatar/1142647/20170416093225.png",
                "initTitle"+2,
                "https://www.douyu.com/"
        ));
        pageNumber+=1;
        myAdapter = new MyAdapter(blist);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);//纵向线性布局

        recyclerView.setLayoutManager(layoutManager);
        //解决数据加载不完的问题
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        //解决数据加载完成后, 没有停留在顶部的问题
        recyclerView.setFocusable(false);
        recyclerView.setAdapter(myAdapter);

    }


    private void setPullRefresher(){
        //设置 Header 为 MaterialHeader
        refreshLayout.setRefreshHeader(new toprs.cn.lunbo.ClassicsHeader(this));
        //设置 Footer 为 经典样式
        refreshLayout.setRefreshFooter(new toprs.cn.lunbo.ClassicsFooter(this));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //在这里执行上拉刷新时的具体操作(网络请求、更新UI等)

                //模拟网络请求到的数据
                ArrayList<ItemBean> newList = new ArrayList<ItemBean>();
                for (int i=0;i<20;i++){
                    newList.add(new ItemBean(
                            "https://pic.cnblogs.com/avatar/1142647/20170416093225.png",
                            "initTitle"+i,
                            "https://www.zhihu.com/"
                    ));
                }
                myAdapter.refresh(newList);
                refreshlayout.finishRefresh(2000/*,false*/);
                //不传时间则立即停止刷新    传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {

                //模拟网络请求到的数据
                ArrayList<ItemBean> newList = new ArrayList<ItemBean>();
                pageNumber+=1;
                System.out.print(pageNumber);
                newList.add(new ItemBean(
                        "https://pic.cnblogs.com/avatar/1142647/20170416093225.png",
                        "initTitle"+1,
                        "https://www.zhihu.com/"
                ));
                newList.add(new ItemBean(
                        "https://pic.cnblogs.com/avatar/1142647/20170416093225.png",
                        "initTitle"+2,
                        "https://www.douyu.com/"
                ));
                myAdapter.add(newList);
                //在这里执行下拉加载时的具体操作(网络请求、更新UI等)
                refreshlayout.finishLoadMore(2000/*,false*/);//不传时间则立即停止刷新    传入false表示加载失败
                //refreshlayout.finishLoadMoreWithNoMoreData(); 没有更多了
            }
        });
    }
    @Override
    public void OnBannerClick(int position) {
        Toast.makeText(getApplicationContext(),"你点击了："+position,Toast.LENGTH_SHORT).show();
    }


    //如果你需要考虑更好的体验，可以这么操作
    @Override
    protected void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }

    public void getimg(){
        getScreen(this);
        Fresco.initialize(this);
        Recovery.getInstance()
                .debug(true)
                .recoverInBackground(false)
                .recoverStack(true)
                .mainPage(MainActivity.class)
                .init(this);
        String[] urls = getResources().getStringArray(R.array.url);
        String[] tips = getResources().getStringArray(R.array.title);
        images = Arrays.asList(urls);
        titles= Arrays.asList(tips);
    }

    public void getScreen(Context aty) {
        DisplayMetrics dm = aty.getResources().getDisplayMetrics();
        H=dm.heightPixels;
        W=dm.widthPixels-50;
    }


}
