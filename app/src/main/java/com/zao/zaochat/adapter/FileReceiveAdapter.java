package com.zao.zaochat.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zao.zaochat.R;
import com.zao.zaochat.listener.OnItemClickListener;
import com.zao.zaochat.model.FileBean;
import com.zao.zaochat.utils.ZaoUtils;

import java.util.ArrayList;
import java.util.Date;


public class FileReceiveAdapter extends RecyclerView.Adapter<FileReceiveAdapter.ViewHolder> {

    private Context context;
    public ArrayList<FileBean> list;
    private ItemImageViewInterface imageViewInterface;

    public FileReceiveAdapter(Context context, ArrayList<FileBean> list){
        this.context=context;
        this.list=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_receive_file,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.iv_item_record_pic.setImageBitmap(list.get(position).getBitmap());
        holder.tv_item_record_name.setText(list.get(position).getFilename());
        holder.tv_item_record_time.setText(ZaoUtils.tranTime(2,new Date(list.get(position).getDate())));
        holder.tv_item_record_size.setText(list.get(position).getSize());

        //setTag为当前view添加状态，之后直接点击调用getTag便可获取其position
//        holder.itemView.setTag(position);
        //当然也可以将数据传给setTag
        holder.itemView.setTag(list.get(position));

        View itemView = ((ConstraintLayout) holder.itemView).getChildAt(0);
        setClickListener(holder,position);

/*        //可以直接在onBindViewHolder中实现点击事件。 也可以在调用此Adapter的方法中实现点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
    }


    //设置点击事件
    private void setClickListener(final ViewHolder holder, final int position) {
        if(mOnItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    int pos = holder.getLayoutPosition()-1;//默认是第一个开始
                    int pos = holder.getLayoutPosition();//默认是第一个开始
                    mOnItemClickListener.onItemClick(holder.itemView, pos);
                }
            });
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v)
            {
//                int pos = holder.getLayoutPosition()-1;//默认是第一个开始
                int pos = holder.getLayoutPosition();//默认是第一个开始
                mOnItemClickListener.onItemLongClick(holder.itemView, pos);
                return true;//返回true可以屏蔽点击监听的响应
            }
        });

        //条目item中的ImageView 的点击事件
        holder.iv_item_record_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageViewInterface !=null) {
//                  接口实例化后的而对象，调用重写后的方法
                    imageViewInterface.onclick(v,position);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    private OnItemClickListener mOnItemClickListener;//声明接口

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    /**
     *图片点击事件需要的方法
     */
    public void setOnItemImageViewClickListener(ItemImageViewInterface itemImageViewInterface){
        this.imageViewInterface = itemImageViewInterface;
    }

    /**
     * 按钮点击事件对应的接口
     */
    public interface ItemImageViewInterface {
        public void onclick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_item_record_pic;
        public TextView tv_item_record_name;
        public TextView tv_item_record_time;
        public TextView tv_item_record_size;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_item_record_pic=(ImageView)itemView.findViewById(R.id.iv_item_record_pic);
            tv_item_record_name=(TextView)itemView.findViewById(R.id.tv_item_record_name) ;
            tv_item_record_time=(TextView)itemView.findViewById(R.id.tv_item_record_time) ;
            tv_item_record_size=(TextView)itemView.findViewById(R.id.tv_item_record_size) ;

            //设置图片的宽高
            ViewGroup.LayoutParams params = iv_item_record_pic.getLayoutParams();
            params.height= 100;
            params.width = 100;
            iv_item_record_pic.setLayoutParams(params);
        }
    }
}