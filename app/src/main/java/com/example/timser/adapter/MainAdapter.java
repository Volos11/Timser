package com.example.timser.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
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
    private int cp1 = 1;

    public MainAdapter(Context context, int cp) {
        this.context = context;
        MainArray = new ArrayList<>();
        this.cp1 = cp;

    }

    @NonNull
    @Override
    public MyViewHolper onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_layout, parent, false);
//        View view1 = LayoutInflater.from(context).inflate(R.layout.item_list_work, parent, false);


        return new MyViewHolper(view, context, MainArray, this.cp1);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolper holder, int position) {
        holder.setData(MainArray.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return MainArray.size();
    }




   public static class MyViewHolper extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvTitle;
        private Context context;
        private List<ListItem> MainArray;
        private int cp;
        View deleted;

        public MyViewHolper(@NonNull View itemView, Context context, List<ListItem>  MainArray, int cp) {
            super(itemView);

            this.context = context;
            this.MainArray = MainArray;
            this.cp = cp;

            tvTitle = itemView.findViewById(R.id.tvTitle);
            deleted = itemView.findViewById(R.id.deleted);



            switch (cp){
                //Черный
                case 2:
                    tvTitle.setTextColor(Color.WHITE);
                    tvTitle.setBackgroundResource(R.drawable.neon);
                    itemView.setOnClickListener(this);
                    break;
                //Белый
                case 3:
                    tvTitle.setTextColor(Color.BLACK);
                    tvTitle.setBackgroundResource(R.drawable.whiter);
                    itemView.setOnClickListener(this);
                    break;
                default:
                    break;
            }
           itemView.setOnClickListener(this);
        }

        public void setData(String title){
            tvTitle.setText(title);
        }

        @Override
        public void onClick(View v) {
            int loc = 1;
            int status = 0;
//            Log.d("MyLog","Pressed : " + getAdapterPosition());
            Intent i = new Intent(context, EditActivity.class);
            i.putExtra(myConstants.LIST_ITEM_INTENT, MainArray.get(getAdapterPosition()));
            i.putExtra(myConstants.EDIT_STATE, false);
            i.putExtra("loc", loc);
            i.putExtra("color", cp);
            i.putExtra("statuser", status);
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

