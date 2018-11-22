package com.zao.zaochat.adapter;

import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zao.zaochat.R;
import com.zao.zaochat.global.ConstantC;
import com.zao.zaochat.model.ChatModel;
import com.zao.zaochat.model.ItemModel;

import java.util.ArrayList;

/**
 * Created by yummy on 2016/11/28.
 */
public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.BaseAdapter>{

    private ArrayList<ItemModel> dataList = new ArrayList<>();
    private OnChatModelItemClickListener listener;
    private final static String TAG = "ChatRoomAdapter";

    public void replaceAll(ArrayList<ItemModel> list) {
        dataList.clear();
        if (list != null && list.size() > 0) {
            dataList.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<ItemModel> list) {
        if (dataList != null && list != null) {
            dataList.addAll(list);
            notifyItemRangeChanged(dataList.size(), list.size());
        }

    }

    public void clearData(){
        dataList.clear();
        notifyDataSetChanged();
    }

    @Override
    public ChatRoomAdapter.BaseAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ItemModel.CHAT_A:
                return new ChatAViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_fragment_a, parent, false));
            case ItemModel.CHAT_B:
                return new ChatBViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_fragment_b, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ChatRoomAdapter.BaseAdapter holder, int position) {
        holder.setData(dataList.get(position).object);
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).type;
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    public class BaseAdapter extends RecyclerView.ViewHolder {

        public BaseAdapter(View itemView) {
            super(itemView);
        }

        void setData(Object object) {

        }
    }

    private class ChatAViewHolder extends BaseAdapter {
        private ImageView civIcon;
        private TextView tvContent;
        private TextView tvName;
        private ImageView ivSex;
        private TextView tvTime;
        private ImageView ivContent;

        public ChatAViewHolder(View view) {
            super(view);
            civIcon = (ImageView) itemView.findViewById(R.id.civ_bubble_user_icon);
            tvContent = (TextView) itemView.findViewById(R.id.tv_bubble_content);
            tvName = (TextView) itemView.findViewById(R.id.tv_bubble_user_name);
            ivSex = (ImageView) itemView.findViewById(R.id.iv_bubble_user_sex);
            tvTime = (TextView) itemView.findViewById(R.id.tv_bubble_user_time);
            ivContent = (ImageView) itemView.findViewById(R.id.iv_bubble_content);
        }

        @Override
        void setData(Object object) {
            super.setData(object);
            final ChatModel model = (ChatModel) object;
//            Picasso.with(itemView.getContext()).load(model.getIcon()).placeholder(R.mipmap.ic_launcher).into(ic_user);
            switch (model.getIcon()) {
                case "1":
                    civIcon.setImageResource(R.drawable.header);
                    break;
                case "2":
                    civIcon.setImageResource(R.drawable.header2);
                    break;
                case "3":
                    civIcon.setImageResource(R.drawable.header3);
                    break;
                case "4":
                    civIcon.setImageResource(R.drawable.header4);
                    break;
                default:
                    civIcon.setImageResource(R.drawable.header4);
                    break;
            }

            civIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onIconClick(model);
                }
            });

            tvContent.setText(model.getContent());
            tvContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onContentClick(model);
                }
            });

            tvContent.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onContentLongClick(view,model);
                    return true;
                }
            });


            tvName.setText(model.getNick() + "(" + model.getSign() + ")");
            switch (model.getSex()) {
                case "man":
                    ivSex.setImageResource(R.drawable.man);
                    break;
                case "woman":
                    ivSex.setImageResource(R.drawable.woman);
                    break;
                default:
                    break;
            }

            tvTime.setText(model.getTime());

            String file_path = model.getFile_path();
            if(!TextUtils.isEmpty(file_path)){
                ivContent.setImageBitmap(BitmapFactory.decodeFile(file_path));
                ivContent.setVisibility(View.VISIBLE);
                tvContent.setVisibility(View.GONE);
            }
        }
    }

    private class ChatBViewHolder extends BaseAdapter {
        private ImageView civIcon;
        private TextView tvContent;
        private TextView tvName;
        private ImageView ivSex;
        private TextView tvTime;
        private ImageView ivContent;

        public ChatBViewHolder(View view) {
            super(view);
            civIcon = (ImageView) itemView.findViewById(R.id.civ_bubble_user_icon);
            tvContent = (TextView) itemView.findViewById(R.id.tv_bubble_content);
            tvName = (TextView) itemView.findViewById(R.id.tv_bubble_user_name);
            ivSex = (ImageView) itemView.findViewById(R.id.iv_bubble_user_sex);
            tvTime = (TextView) itemView.findViewById(R.id.tv_bubble_user_time);
            ivContent = (ImageView) itemView.findViewById(R.id.iv_bubble_content);
        }

        @Override
        void setData(Object object) {
            super.setData(object);
            final ChatModel model = (ChatModel) object;
//            Picasso.with(itemView.getContext()).load(model.getIcon()).placeholder(R.mipmap.ic_launcher).into(ic_user);
            switch (model.getIcon()) {
                case "1":
                    civIcon.setImageResource(R.drawable.header);
                    break;
                case "2":
                    civIcon.setImageResource(R.drawable.header2);
                    break;
                case "3":
                    civIcon.setImageResource(R.drawable.header3);
                    break;
                case "4":
                    civIcon.setImageResource(R.drawable.header4);
                    break;
                default:
                    break;
            }

            civIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onIconClick(model);
                }
            });

            tvContent.setText(model.getContent());
            tvContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onContentClick(model);
                }
            });

            tvContent.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onContentLongClick(view,model);
                    return true;
                }
            });


            tvName.setText(model.getNick()  + "(" + model.getSign() + ")");
            switch (model.getSex()) {
                case "man":
                    ivSex.setImageResource(R.drawable.man);
                    break;
                case "woman":
                    ivSex.setImageResource(R.drawable.woman);
                    break;
                default:
                    break;
            }
            tvTime.setText(model.getTime());

            String file_path = model.getFile_path();
            if(!TextUtils.isEmpty(file_path)){
                ivContent.setImageBitmap(BitmapFactory.decodeFile(file_path));
                ivContent.setVisibility(View.VISIBLE);
                tvContent.setVisibility(View.GONE);
            }
        }
    }

    public void setOnChatModelItemClickListener(OnChatModelItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnChatModelItemClickListener {
        void onIconClick(ChatModel model);
        void onContentClick(ChatModel model);
        void onContentLongClick(View view,ChatModel model);
    }

}
