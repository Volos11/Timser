package com.example.timser.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timser.EditActivity;
import com.example.timser.MainActivity;
import com.example.timser.R;
import com.example.timser.db.MyDbManager;
import com.example.timser.db.myConstants;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolper> {
    private Context context;
    private List<ListItem> MainArray;

    public MainAdapter(Context context) {
        this.context = context;
        MainArray = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolper onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_layout, parent, false);
        return new MyViewHolper(view, context, MainArray);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolper holder, int position) {
        holder.setData(MainArray.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return MainArray.size();
    }

    static class MyViewHolper extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTitle;
        private Context context;
        private List<ListItem> MainArray;


        public MyViewHolper(@NonNull View itemView, Context context, List<ListItem>  MainArray) {
            super(itemView);
            this.context = context;
            this.MainArray = MainArray;
            tvTitle = itemView.findViewById(R.id.tvTitle);

            itemView.setOnClickListener(this);
//            delete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }

        }
        public void setData(String title){
            tvTitle.setText(title);
        }

        @Override
        public void onClick(View v) {
//            Log.d("MyLog","Pressed : " + getAdapterPosition());
            Intent i = new Intent(context, EditActivity.class);
            i.putExtra(myConstants.LIST_ITEM_INTENT, MainArray.get(getAdapterPosition()));
            i.putExtra(myConstants.EDIT_STATE, false);

            context.startActivity(i);

        }
    }

    public void updateAdapter(List<ListItem> newList){
        MainArray.clear();
        MainArray.addAll(newList);
        notifyDataSetChanged();
    }
    public void removeItem(int pos, MyDbManager dbManager){
        dbManager.delete(MainArray.get(pos).getId());
        MainArray.remove(pos);
        notifyItemRangeChanged(0, MainArray.size());
        notifyItemRemoved(pos);
    }
}
