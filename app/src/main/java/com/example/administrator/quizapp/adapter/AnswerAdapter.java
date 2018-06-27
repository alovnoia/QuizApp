package com.example.administrator.quizapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.quizapp.GameActivity;
import com.example.administrator.quizapp.LevelActivity;
import com.example.administrator.quizapp.R;
import com.example.administrator.quizapp.lib.AppHelper;
import com.example.administrator.quizapp.model.Answer;
import com.example.administrator.quizapp.model.Topic;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.RecyclerViewHolder>{

    private Context mContext;
    private List<Answer> data = new ArrayList<>();
    private AnswerAdapter.RecyclerViewHolder rightAnswer;
    public boolean isClickable = true;

    public AnswerAdapter(Context mContext, List<Answer> data) {
        this.mContext = mContext;
        this.data = data;
        isClickable = true;
    }

    @Override
    public AnswerAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.answer_item, parent, false);
        return new AnswerAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnswerAdapter.RecyclerViewHolder holder, final int position) {
        final Answer answer = data.get(position);
        if (answer.isCorrect()) {
            rightAnswer = holder;
        }
        holder.tvAnswerContent.setText(answer.getContent());

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isClickable) {
                    if (answer.isCorrect()) {
                        holder.item.setBackgroundResource(R.drawable.btn_answer_right);
                        onItemClickedListener.onItemClick(position, answer, true);
                    } else {
                        holder.item.setBackgroundResource(R.drawable.btn_answer_false);
                        onItemClickedListener.onItemClick(position, answer, false);
                    }
                    rightAnswer.item.setBackgroundResource(R.drawable.btn_answer_right);
                    isClickable = false;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnItemClickedListener {
        void onItemClick(int position, Answer answer, boolean correct);
    }

    private AnswerAdapter.OnItemClickedListener onItemClickedListener;

    public void setOnItemClickedListener(AnswerAdapter.OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tvAnswerContent;
        LinearLayout item;
        public RecyclerViewHolder(final View itemView) {
            super(itemView);
            tvAnswerContent = itemView.findViewById(R.id.tvAnwerContent);
            item = itemView.findViewById(R.id.itemAnswer);
        }
    }

}
