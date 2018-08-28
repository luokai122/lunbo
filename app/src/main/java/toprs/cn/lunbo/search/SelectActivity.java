package toprs.cn.lunbo.search;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import toprs.cn.lunbo.R;
import toprs.cn.lunbo.bean.ItemBean;
import toprs.cn.lunbo.loader.SearchAdapter;


public class SelectActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mSearchTagEdit;
    private RecyclerView mRecyclerView;
    private Handler handler = new Handler();
    private List<ItemBean> searchTagList;
    private SearchAdapter mAdapter;

    private Runnable delayRun = new Runnable() {
        @Override
        public void run() {
            searchTags(mSearchTagEdit.getText().toString().trim());
        }
    };

    private FlowLayout mFlowLayout;
    private LayoutInflater mInflater;

    /************
     * 以上为流式标签相关
     ************/
    public static final String EXTRA_KEY_TYPE = "extra_key_type";
    public static final String EXTRA_KEY_KEYWORD = "extra_key_keyword";
    public static final String KEY_SEARCH_HISTORY_KEYWORD = "key_search_history_keyword";
    public SharedPreferences mPref;//使用SharedPreferences记录搜索历史
    private String mType;
    private EditText input;
    private Button btn_search;
    private List<String> mHistoryKeywords;//记录文本
    private ArrayAdapter<String> mArrAdapter;//搜索历史适配器
    private LinearLayout mSearchHistoryLl;
    String history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        searchTagList = new ArrayList<ItemBean>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select);
        input = (EditText) findViewById(R.id.editText);
        initView();
        setEvent();
        initFlowView();
        mPref = getSharedPreferences("input", Activity.MODE_PRIVATE);
        history = mPref.getString("HISTORY",null);
        //SharedPreferences.Editor editor = mPref.edit() ;
        //editor.clear();
        //editor.commit();
        if(history!=null) {
            Gson gson = new Gson();
            List<String> stringList = gson.fromJson(history, new TypeToken<List<String>>() {
            }.getType());
            initData(stringList);
        }

    }

    private void initFlowView(){
        mInflater = LayoutInflater.from(this);
        mFlowLayout = (FlowLayout) findViewById(R.id.flowlayout);
    }



    /**
     * 将数据放入流式布局
     */
    public void initData(List<String> mVals) {
        for (int i = 0; i < mVals.size(); i++) {
            TextView tv = (TextView) mInflater.inflate(
                    R.layout.search_label_tv, mFlowLayout, false);
            tv.setText(mVals.get(i));
            final String str = tv.getText().toString();
            //点击事件
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //加入搜索历史纪录记录
                    /*Toast.makeText(SelectActivity.this, str, Toast.LENGTH_LONG).show();*/
                    input.setText(str);
                }
            });
            mFlowLayout.addView(tv);
        }
    }

    public void deleteData() {

        TextView tv = (TextView) mInflater.inflate(
                R.layout.search_label_tv, mFlowLayout, false);
        mFlowLayout.removeViews(0,1);

    }



    /**
     * 储存搜索历史
     */
    public void save(String text) {
        history = mPref.getString("HISTORY",null);
        List<String> stringList = new ArrayList<>();
        Gson gson = new Gson();
        List<String> add = new ArrayList<>();
        if(history!=null) {
            stringList = gson.fromJson(history, new TypeToken<List<String>>() {}.getType());
            if(!stringList.contains(text)){
                if(stringList.size()>6){
                    stringList.remove(0);
                    deleteData();
                }
                add.add(text);
                initData(add);
                stringList.add(text);
                SharedPreferences.Editor editor = mPref.edit() ;
                editor.putString("HISTORY",gson.toJson(stringList));
                editor.commit() ;
            }
        }else{
            add.add(text);
            initData(add);
            stringList.add(text);
            SharedPreferences.Editor editor = mPref.edit() ;
            editor.putString("HISTORY",gson.toJson(stringList));
            editor.commit() ;
        }



    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                String keywords = input.getText().toString();
                if (!TextUtils.isEmpty(keywords)) {
                    Toast.makeText(SelectActivity.this, keywords + "save成功", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SelectActivity.this, "请输入搜索内容", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }

    }



    private void initView() {
        mAdapter = new SearchAdapter(searchTagList,this);
        mRecyclerView = (RecyclerView) findViewById(R.id.search_tag_recycler);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);//纵向线性布局
        mRecyclerView.setLayoutManager(layoutManager);
        //解决数据加载不完的问题
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);
        //解决数据加载完成后, 没有停留在顶部的问题
        mRecyclerView.setFocusable(false);
        mRecyclerView.setAdapter(mAdapter);
    }


    private void setEvent() {
        mSearchTagEdit = (EditText) findViewById(R.id.editText);
        mSearchTagEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //no-op
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //输入完成后严重8毫秒在请求
                if (delayRun != null) {
                    handler.removeCallbacks(delayRun);
                }
                handler.postDelayed(delayRun, 800);
            }
        });

    }

    /**
     * 请求数据
     * @param searchTagName
     */
    private void searchTags(String searchTagName) {
        List<ItemBean> searchTags=new ArrayList<>();
        searchTags.add(new ItemBean("测试数据1"));
        searchTags.add(new ItemBean("测试数据2"));
        searchTags.add(new ItemBean("测试数据3"));
        searchTags.add(new ItemBean("测试数据4"));
        searchTags.add(new ItemBean("测试数据5"));
        searchTags.add(new ItemBean("测试数据6"));
        searchTags.add(new ItemBean("测试数据7"));
        searchTags.add(new ItemBean("测试数据8"));
        searchTags.add(new ItemBean("测试数据9"));
        searchTags.add(new ItemBean("测试数据10"));
        searchTags.add(new ItemBean("测试数据1"));
        searchTags.add(new ItemBean("测试数据2"));
        searchTags.add(new ItemBean("测试数据3"));
        searchTags.add(new ItemBean("测试数据4"));
        searchTags.add(new ItemBean("测试数据5"));
        searchTags.add(new ItemBean("测试数据6"));
        searchTags.add(new ItemBean("测试数据7"));
        searchTags.add(new ItemBean("测试数据8"));
        searchTags.add(new ItemBean("测试数据9"));
        searchTags.add(new ItemBean("测试数据10"));
        mAdapter.refresh(searchTags);
    }

}
