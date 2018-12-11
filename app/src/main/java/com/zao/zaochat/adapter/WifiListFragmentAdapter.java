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
import android.widget.TextView;

import com.zao.zaochat.R;
import com.zao.zaochat.WifiTool.WifiTools;
import com.zao.zaochat.global.ConstantC;
import com.zao.zaochat.object.ScanResultWithLock;

import java.util.List;

public class WifiListFragmentAdapter extends RecyclerView.Adapter<WifiListFragmentAdapter.MyViewHolder> {

    private List<ScanResultWithLock> scanResultWithLocks;
    private Context mContext;
    private LayoutInflater inflater;

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public WifiListFragmentAdapter(Context context, List<ScanResultWithLock> scanResultWithLocks) {
        this.mContext = context;
        this.scanResultWithLocks = scanResultWithLocks;
        inflater = LayoutInflater.from(mContext);
    }

    public void setData(List<ScanResultWithLock> scanResultWithLocks) {
        this.scanResultWithLocks = scanResultWithLocks;

    }

    @Override
    public int getItemCount() {

        return scanResultWithLocks.size();
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        String textName = scanResultWithLocks.get(position).getScanResult().SSID + "\n" + scanResultWithLocks.get(position).getScanResult().BSSID;

        holder.tvName.setText(textName);
        if(textName.startsWith(ConstantC.WIFI_AP_PRE)){
            holder.tvConnect.setTextColor(mContext.getResources().getColor(R.color.colorGreen));
        }
        int level = WifiTools.calculateSignalLevel(scanResultWithLocks.get(position).getScanResult().level, 4);
//        System.out.println(level);
        switch (level) {
            case 1:
                holder.ivIcon.setImageResource(R.drawable.wifi_yellow1);
                break;
            case 2:
                holder.ivIcon.setImageResource(R.drawable.wifi_yellow2);
                break;
            case 3:
                holder.ivIcon.setImageResource(R.drawable.wifi_yellow3);
                break;
            case 4:
                holder.ivIcon.setImageResource(R.drawable.wifi_yellow4);
                break;
            default:
                break;
        }

        if (scanResultWithLocks.get(position).isLocked()) {
            holder.ivLock.setImageResource(R.drawable.lock);
        } else {
            holder.ivLock.setImageResource(R.drawable.lock_empty);
        }

        if (mOnItemClickListener != null) {
            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onNameClick(position);
                }
            });
            holder.ivIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onImageClick(position);
                }
            });
            holder.tvConnect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onConnectClick(position);
                }
            });
        }
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.wifi_list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    class MyViewHolder extends ViewHolder {

        TextView tvName;
        ImageView ivIcon;
        TextView tvConnect;
        ImageView ivLock;

        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tv_wifi_name);
            tvConnect = (TextView) view.findViewById(R.id.btn_wifi_name);
            ivIcon = (ImageView) view.findViewById(R.id.iv_wifi_icon);
            ivLock = (ImageView) view.findViewById(R.id.iv_wifi_item_lock);
        }

    }

    /**
     * wifi列表项目被点击的事件
     */
    public interface OnRecyclerViewItemClickListener {
        void onNameClick(int position);

        void onImageClick(int position);

        void onConnectClick(int position);
    }

    public void setOnRItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
