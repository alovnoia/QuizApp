package com.example.administrator.quizapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.quizapp.R;
import com.example.administrator.quizapp.lib.AppHelper;
import com.example.administrator.quizapp.model.Answer;
import com.example.administrator.quizapp.model.Question;
import com.example.administrator.quizapp.model.Topic;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.RecyclerViewHolder> {

    private Context mContext;
    private List<Question> data = new ArrayList<>();
    private ArrayList<Answer> arrAnswer;
    private AnswerAdapter answerAdapter;
    private String userChoose;

    public QuestionAdapter(Context mContext, List<Question> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public QuestionAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.question_item, parent, false);
        return new QuestionAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final QuestionAdapter.RecyclerViewHolder holder, final int position) {
        final Question question = data.get(position);
        holder.tvQuestion.setText(question.getContent());
        try {
            if (AppHelper.isChallenge()) {
                userChoose = question.getPlayer1().getString("choose");
            } else {
                userChoose = question.getPlayer2().getString("choose");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (question.getImage().equalsIgnoreCase("")) {
            holder.ivImage.setVisibility(View.GONE);
        } else {
            byte[] decodedString = Base64.decode(question.getBase64Image(), Base64.NO_WRAP);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.ivImage.setImageBitmap(decodedByte);
        }

        try {
            arrAnswer = new ArrayList<>();
            for (int i = 0; i < question.getAnswer().length(); i++) {
                JSONObject obj = (JSONObject) question.getAnswer().get(i);

                arrAnswer.add(new Answer(
                        obj.getString("content"),
                        obj.getBoolean("correct")
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        answerAdapter = new AnswerAdapter(mContext, arrAnswer, true, userChoose);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.rvAnswer.setLayoutManager(layoutManager);

        holder.rvAnswer.setAdapter(answerAdapter);
        answerAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestion;
        ImageView ivImage;
        RecyclerView rvAnswer;
        ConstraintLayout item;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            tvQuestion = itemView.findViewById(R.id.tvQuestion);
            ivImage = itemView.findViewById(R.id.ivImage);
            rvAnswer = itemView.findViewById(R.id.rvAnswer);
            item = itemView.findViewById(R.id.topicItem);
        }
    }

    public interface OnItemClickedListener {
        void onItemClick(Topic topic);
    }

    private QuestionAdapter.OnItemClickedListener onItemClickedListener;

    public void setOnItemClickedListener(QuestionAdapter.OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

}
