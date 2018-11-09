package com.zao.zaochat.widget;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zao.zaochat.R;
import com.zao.zaochat.model.ChatModel;

/**
 * 项目名称：HotChat2
 * 类描述：
 * 创建人：qq985
 * 创建时间：2016/12/22 0:28
 * 修改人：qq985
 * 修改时间：2016/12/22 0:28
 * 修改备注：
 */
public class UserInfoDialog extends AlertDialog {
    LayoutInflater layoutInflater;
    TextView tvId;
    TextView tvName;
    TextView tvSex;
    TextView tvIllustration;
    ImageView ivIcon;

    View view;
    int layoutId;
    ChatModel chatModel;

    public UserInfoDialog(Context context, LayoutInflater layoutInflater, int layoutId, ChatModel chatModel) {
        super(context);
        this.layoutInflater = layoutInflater;
        this.layoutId = layoutId;
        view = layoutInflater.inflate(layoutId, (ViewGroup) findViewById(layoutId));
        tvId = (TextView) view.findViewById(R.id.tv_dialog_user_info_id);
        tvName = (TextView) view.findViewById(R.id.tv_dialog_user_info_name);
        tvSex = (TextView) view.findViewById(R.id.tv_dialog_user_info_sex);
        tvIllustration = (TextView) view.findViewById(R.id.tv_dialog_user_info_illustration);
        ivIcon= (ImageView) view.findViewById(R.id.iv_dialog_user_info_header);
        this.chatModel=chatModel;
    }


    public void initParameterAndShow() {

        tvId.setText(chatModel.getSign());
        tvName.setText(chatModel.getNick());
        switch (chatModel.getSex()){
            case "man":
                tvSex.setText("男");
                break;
            case "woman":
                tvSex.setText("女");
                break;
            default:
                tvSex.setText("未知");
                break;
        }
        tvIllustration.setText(chatModel.getIllustration());
        switch (chatModel.getIcon()){
            case "1":
                ivIcon.setImageResource(R.drawable.header);
                break;
            case "2":
                ivIcon.setImageResource(R.drawable.header2);
                break;
            case "3":
                ivIcon.setImageResource(R.drawable.header3);
                break;
            case "4":
                ivIcon.setImageResource(R.drawable.header4);
                break;
            default:
                ivIcon.setImageResource(R.drawable.header);
                break;
        }

        setView(view);
        show();
    }

}
