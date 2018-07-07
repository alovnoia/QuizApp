package com.example.administrator.quizapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.quizapp.LevelActivity;
import com.example.administrator.quizapp.R;
import com.example.administrator.quizapp.StatActivity;
import com.example.administrator.quizapp.lib.AppHelper;
import com.example.administrator.quizapp.model.Game;
import com.example.administrator.quizapp.model.Topic;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.RecyclerViewHolder> {

    private Context mContext;
    private List<Game> data = new ArrayList<>();

    public HistoryAdapter(Context mContext, List<Game> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public HistoryAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.game_item, parent, false);
        return new HistoryAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HistoryAdapter.RecyclerViewHolder holder, final int position) {
        final Game game = data.get(position);
        try {
            String user = AppHelper.userEmail.equals(game.getIdUser1()) ? game.getIdUser1() : game.getIdUser2();
            if (game.getResult().getString("winner").equalsIgnoreCase("")) {
                holder.tvResult.setText("D");
                holder.tvResult.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorDraw));
            } else {
                if (game.getResult().getString("winner").equalsIgnoreCase(user)) {
                    holder.tvResult.setText("W");
                    holder.tvResult.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorWin));
                } else {
                    holder.tvResult.setText("L");
                    holder.tvResult.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorLose));
                }
            }
            holder.tvTopic.setText(game.getPack().getJSONObject("topic").getString("name"));
            holder.tvLevel.setText(game.getPack().getString("level"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppHelper.gameData = game.gameToJsonObject();

                Intent i = new Intent(mContext, StatActivity.class);
                i.putExtra("fromHistory", true);
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tvResult, tvTopic, tvLevel;
        ConstraintLayout item;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            tvResult = itemView.findViewById(R.id.tvResult);
            tvTopic = itemView.findViewById(R.id.tvTopic);
            tvLevel = itemView.findViewById(R.id.tvLevel);
            item = itemView.findViewById(R.id.itemGame);
        }
    }

    public interface OnItemClickedListener {
        void onItemClick(Topic topic);
    }

    private HistoryAdapter.OnItemClickedListener onItemClickedListener;

    public void setOnItemClickedListener(HistoryAdapter.OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }


}
