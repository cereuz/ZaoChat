package com.zao.zaochat.adapter;

/**
 * 项目名称：HotChat2
 * 类描述：
 * 创建人：qq985
 * 创建时间：2016/12/2 21:41
 * 修改人：qq985
 * 修改时间：2016/12/2 21:41
 * 修改备注：
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zao.zaochat.R;
import com.zao.zaochat.utils.ZaoUtils;
import com.zao.zaochat.znote.NotesInfo;

import java.util.List;

public class NotesFragmentAdapter extends RecyclerView.Adapter<NotesFragmentAdapter.MyViewHolder> {

    private List<NotesInfo> notesInfos;
    private Context mContext;
    private LayoutInflater inflater;

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public NotesFragmentAdapter(Context context, List<NotesInfo> notesInfos) {
        this.mContext = context;
        this.notesInfos = notesInfos;
        inflater = LayoutInflater.from(mContext);
    }

    public void setData(List<NotesInfo> notesInfos) {
        this.notesInfos = notesInfos;

    }

    @Override
    public int getItemCount() {

        return notesInfos.size();
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.tvNote.setText(notesInfos.get(position).getNote());
        holder.tvName.setText(notesInfos.get(position).getName());
        if(position % 2 == 0){
            holder.tvTime.setText(ZaoUtils.getSystemTimeMore(11, notesInfos.get(position).getTime()));
        }  else {
            holder.tvTime.setText(ZaoUtils.getSystemTimeMore(12, notesInfos.get(position).getTime()));
        }

/*        Glide.with(context)
                .load(url)
                .placeholder(R.mipmap.ic_tab_group_active)//加载成功前显示的图片
                .error(R.mipmap.ic_tab_home_active)//异常时候显示的图片
                .fallback(R.mipmap.ic_tab_profile_active)//url为空的时候，显示的图片
                .diskCacheStrategy(DiskCacheStrategy.NONE)  //不使用缓存
                .into(holder.iv_dailynotes_pic);
        Glide.with(context).load(url).into(holder.iv_dailynotes_pic);*/
        String mode = notesInfos.get(position).getMode();
//        System.out.println(level);
        switch (mode) {
            case "0":
                Glide.with(mContext).load("http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg").into(holder.ivIcon);
                break;
            case "1":
                Glide.with(mContext).load("http://p1.pstatp.com/large/166200019850062839d3").into(holder.ivIcon);
                break;
            case "2":
                Glide.with(mContext).load("https://upfile.asqql.com/2009pasdfasdfic2009s305985-ts/2018-8/201881110474228420.gif").into(holder.ivIcon);
                break;
            case "3":
                Glide.with(mContext).load("https://img3.doubanio.com/view/photo/l/public/p2518471180.webp").into(holder.ivIcon);
                break;
            default:
                break;
        }

/*        String mode = notesInfos.get(position).getMode();
//        System.out.println(level);
        switch (mode) {
            case "0":
                holder.ivIcon.setImageResource(R.drawable.wifi_yellow1);
                break;
            case "1":
                holder.ivIcon.setImageResource(R.drawable.wifi_yellow2);
                break;
            case "2":
                holder.ivIcon.setImageResource(R.drawable.wifi_yellow3);
                break;
            case "3":
                holder.ivIcon.setImageResource(R.drawable.wifi_yellow4);
                break;
            default:
                break;
        }*/

        if (mOnItemClickListener != null) {
/*            holder.tvNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onNoteClick(position);
                }
            });
            holder.ivIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onImageClick(position);
                }
            });
            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onNameClick(position);
                }
            });*/
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(view,position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickListener.onItemLongClick(view,position);
                    return true;
                }
            });
        }
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.notes_list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    class MyViewHolder extends ViewHolder {

        TextView tvNote;
        RelativeLayout itemView;
        TextView tvName;
        TextView tvTime;
        ImageView ivIcon;
        ImageView ivLock;

        public MyViewHolder(View view) {
            super(view);
            itemView = (RelativeLayout)view.findViewById(R.id.rl_item_view);
            tvNote = (TextView) view.findViewById(R.id.tv_note_note);
            tvName = (TextView) view.findViewById(R.id.tv_note_name);
            tvTime = (TextView) view.findViewById(R.id.tv_note_item_time);
            ivIcon = (ImageView) view.findViewById(R.id.iv_note_icon);
            ivLock = (ImageView) view.findViewById(R.id.iv_note_item_lock);
        }

    }

    /**
     * wifi列表项目被点击的事件
     */
    public interface OnRecyclerViewItemClickListener {
/*        void onNoteClick(int position);

        void onImageClick(int position);

        void onNameClick(int position);*/

        void onItemClick(View view,int position);

        void onItemLongClick(View view,int position);

    }

    public void setOnRItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
