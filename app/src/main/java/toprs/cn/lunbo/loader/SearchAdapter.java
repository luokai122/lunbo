package toprs.cn.lunbo.loader;

;
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

import toprs.cn.lunbo.Xian;
import toprs.cn.lunbo.bean.ItemBean;
import toprs.cn.lunbo.search.SelectActivity;


public class SearchAdapter extends  RecyclerView.Adapter<SearchAdapter.SelectViewHolder>  {

    private List<ItemBean> mList;
    private SelectActivity selectActivity;

    static class SelectViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        View myView;
        public SelectViewHolder(View itemView) {
            super(itemView);
            myView = itemView;
            title = (TextView) itemView.findViewById(R.id.select_textview);
            /*content = (TextView) itemView.findViewById(R.id.tv_content);*/

        }
    }

    public SearchAdapter(List<ItemBean> list,SelectActivity selectActivity){
        this.selectActivity = selectActivity;
        this.mList = list;
    }

    @Override
    public SelectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_item,null);
        SearchAdapter.SelectViewHolder holder = new SelectViewHolder(view);
        return holder;
    }

    //将数据绑定到控件上
    @Override
    public void onBindViewHolder(SelectViewHolder holder, final int position) {
        final ItemBean bean = mList.get(position);
        holder.title.setText(bean.itemTitle);
        holder.myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
               /* Context mContext = App.getContext();
                Intent intent = new Intent(mContext, Xian.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("data","http://www.zhihu.com");
                mContext.startActivity(intent);*/
                selectActivity.save(bean.itemTitle);

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
