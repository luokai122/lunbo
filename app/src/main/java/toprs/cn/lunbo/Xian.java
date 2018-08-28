package toprs.cn.lunbo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Xian extends Activity {

    public Xian() {

        // TODO 自动生成的构造函数存根
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO 自动生成的方法存根
        super.onCreate(savedInstanceState);

        setContentView(R.layout.web_main);
        WebView web=(WebView) findViewById(R.id.web);
        // 获取意图对象
        Intent intent = getIntent();
        //获取传递的值
        String str = intent.getStringExtra("data");
        web.loadUrl(str);
        web.getSettings().setJavaScriptEnabled(true);  //加上这一行网页为响应式的
        web.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                try{
                    if(url.startsWith("baidumap://")){
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    }
                }catch (Exception e){
                    return false;
                }
                view.loadUrl(url);
                return true;
            }
        });
    }



}
