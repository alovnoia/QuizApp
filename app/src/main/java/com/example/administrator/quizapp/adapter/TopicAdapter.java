package com.example.administrator.quizapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.quizapp.LevelActivity;
import com.example.administrator.quizapp.R;
import com.example.administrator.quizapp.lib.AppHelper;
import com.example.administrator.quizapp.model.Topic;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.RecyclerViewHolder> {

    private Context mContext;
    private List<Topic> data = new ArrayList<>();

    public TopicAdapter(Context mContext, List<Topic> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public TopicAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.topic_item, parent, false);
        return new TopicAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TopicAdapter.RecyclerViewHolder holder, final int position) {
        final Topic topic = data.get(position);
        holder.tvName.setText(topic.getName());
        holder.tvDesc.setText(topic.getDesc());

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppHelper.choosenTopic = new JSONObject();
                try {
                    AppHelper.choosenTopic.put("_id", topic.getId());
                    AppHelper.choosenTopic.put("name", topic.getName());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(mContext, LevelActivity.class);
                mContext.startActivity(i);
                //Log.d("ggg", AppHelper.choosenTopic.toString() + "s");
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDesc;
        ConstraintLayout item;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvTopicName);
            tvDesc = itemView.findViewById(R.id.tvTopicDesc);
            item = itemView.findViewById(R.id.topicItem);
        }
    }

    public interface OnItemClickedListener {
        void onItemClick(Topic topic);
    }

    private TopicAdapter.OnItemClickedListener onItemClickedListener;

    public void setOnItemClickedListener(TopicAdapter.OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

}
