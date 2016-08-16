package com.android.contractorshare.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.contractorshare.R;
import com.android.contractorshare.models.Comment;
import com.android.contractorshare.utils.DateHandler;

import java.util.List;

public class CommentAdapter extends BaseAdapter {

    private final List<Comment> chatComments;
    private final int loggedUserId;
    private Activity context;

    public CommentAdapter(Activity context, List<Comment> chatComments, int loggedUserId) {
        this.context = context;
        this.chatComments = chatComments;
        this.loggedUserId = loggedUserId;
    }

    @Override
    public int getCount() {
        if (chatComments != null) {
            return chatComments.size();
        } else {
            return 0;
        }
    }

    @Override
    public Comment getItem(int position) {
        if (chatComments != null) {
            return chatComments.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Comment chatComment = getItem(position);

        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = vi.inflate(R.layout.message_list_item, null);
            holder = createViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        boolean myMsg = chatComment.getCreatedByUserId().equals(loggedUserId);
        //to simulate whether it me or other sender
        setAlignment(holder, myMsg);
        holder.txtMessage.setText(chatComment.getMessage());
        String image = chatComment.getImage();
        if (image != null && !image.isEmpty()) {
            if (image.equals("1")) holder.txtImage.setImageResource(R.drawable.ic_accept);
            if (image.equals("2")) holder.txtImage.setImageResource(R.drawable.ic_accept);
            if (image.equals("3")) holder.txtImage.setImageResource(R.drawable.ic_accept);
            else if (image.equals("custom")) {
                holder.txtImage.setImageBitmap(chatComment.getBitMap());
            }
        }
        holder.txtInfo.setText(DateHandler.fromWCFToAndroidDateConverter(chatComment.getCreated()));

        return convertView;
    }

    public void add(Comment comment) {
        chatComments.add(comment);
    }

    public void add(List<Comment> comments) {
        chatComments.addAll(comments);
    }

    private void setAlignment(ViewHolder holder, boolean isMe) {
        if (!isMe) {
            holder.contentWithBG.setBackgroundResource(R.drawable.in_message_bg);

            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams) holder.contentWithBG.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            holder.contentWithBG.setLayoutParams(layoutParams);

            RelativeLayout.LayoutParams lp =
                    (RelativeLayout.LayoutParams) holder.content.getLayoutParams();
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            holder.content.setLayoutParams(lp);
            layoutParams = (LinearLayout.LayoutParams) holder.txtMessage.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            holder.txtMessage.setLayoutParams(layoutParams);

            layoutParams = (LinearLayout.LayoutParams) holder.txtInfo.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            holder.txtInfo.setLayoutParams(layoutParams);
        } else {

            holder.contentWithBG.setBackgroundResource(R.drawable.out_message_bg);

            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams) holder.contentWithBG.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            holder.contentWithBG.setLayoutParams(layoutParams);

            RelativeLayout.LayoutParams lp =
                    (RelativeLayout.LayoutParams) holder.content.getLayoutParams();
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            holder.content.setLayoutParams(lp);
            layoutParams = (LinearLayout.LayoutParams) holder.txtMessage.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            holder.txtMessage.setLayoutParams(layoutParams);

            layoutParams = (LinearLayout.LayoutParams) holder.txtInfo.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            holder.txtInfo.setLayoutParams(layoutParams);
        }
    }

    private ViewHolder createViewHolder(View v) {
        ViewHolder holder = new ViewHolder();
        holder.txtMessage = (TextView) v.findViewById(R.id.txtMessage);
        holder.content = (LinearLayout) v.findViewById(R.id.content);
        holder.contentWithBG = (LinearLayout) v.findViewById(R.id.contentWithBackground);
        holder.txtInfo = (TextView) v.findViewById(R.id.txtInfo);
        holder.txtImage = (ImageView) v.findViewById(R.id.txtImage);
        return holder;
    }

    private static class ViewHolder {
        public TextView txtMessage;
        public TextView txtInfo;
        public ImageView txtImage;
        public LinearLayout content;
        public LinearLayout contentWithBG;
    }
}
