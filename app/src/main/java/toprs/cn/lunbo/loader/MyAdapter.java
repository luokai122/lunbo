package toprs.cn.lunbo.loader;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import toprs.cn.lunbo.Final.App;
import toprs.cn.lunbo.MyImageView;
import toprs.cn.lunbo.R;
import toprs.cn.lunbo.bean.ItemBean;
import toprs.cn.lunbo.Xian;

/**
 * Created by hx on 2017/11/7.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<ItemBean> mList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View myView;
        MyImageView myimageView;
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            myView = itemView;
            myimageView = (MyImageView) itemView.findViewById(R.id.img);
            title = (TextView) itemView.findViewById(R.id.text);
            /*content = (TextView) itemView.findViewById(R.id.tv_content);*/
        }
    }

    public MyAdapter(List<ItemBean> list){
        this.mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_item,null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }



    //将数据绑定到控件上
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ItemBean bean = mList.get(position);
        holder.myimageView.setImageURL(bean.itemImage);
        holder.title.setText(bean.itemTitle);
        final String url = bean.itemUrl;
        /*holder.content.setText(bean.itemContent);*/
        holder.myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Context mContext = App.getContext();
                Intent intent = new Intent(mContext, Xian.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("data",url);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }



    //下面两个方法提供给页面刷新和加载时调用
    public void add(List<ItemBean> addMessageList) {
        //增加数据
        int position = mList.size();
        mList.addAll(position, addMessageList);
        notifyItemInserted(position);
    }

    public void refresh(List<ItemBean> newList) {
        //刷新数据
        mList.removeAll(mList);
        mList.addAll(newList);
        notifyDataSetChanged();
    }



}
