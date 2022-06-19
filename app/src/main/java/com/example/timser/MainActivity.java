package com.example.timser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.style.UpdateLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timser.adapter.MainAdapter;
import com.example.timser.db.MyDbManager;

public class MainActivity extends AppCompatActivity {

    private MyDbManager myDbManager;
    private EditText edTitle, edDisc;
    private RecyclerView rcView;
    TextView tvTitle;
    private MainAdapter mainAdapter;
//    Window window = activity.getWindows();

    ConstraintLayout Backer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init(0);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.id_search);
        SearchView sv = (SearchView) item.getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mainAdapter.updateAdapter(myDbManager.getFromDb(newText));

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent i = new Intent(MainActivity.this, Parametrs.class);
        int cl = 0;
//        setContentView(R.layout.item_list_layout);
        int id = item.getItemId();
        switch (id){
            case R.id.blackMenu:
                init(2);
                Backer.setBackgroundResource(R.color.black);

                startActivity(i);
                break;

            case R.id.WhiteMenu:
                init(3);
                Backer.setBackgroundResource(R.drawable.backer);
                startActivity(i);

//                rcView.setBackgroundResource(R.color.white);
                break;
            case R.id.StokeMenu:
                init(1);
                Backer.setBackgroundResource(R.color.greey);
                startActivity(i);
                break;

            default:
                break;
        }





        return super.onOptionsItemSelected(item);
    }

    private void init(int cl){

        tvTitle = findViewById(R.id.tvTitle);
        Backer = findViewById(R.id.Backer);
        myDbManager = new MyDbManager(this);
        rcView = findViewById(R.id.rcView);
        mainAdapter = new MainAdapter(this, cl);
        rcView.setLayoutManager(new LinearLayoutManager(this));
        getItemTouchHelper().attachToRecyclerView(rcView);
        rcView.setAdapter(mainAdapter);
    }
    @Override
    protected void onResume() {
        super.onResume();

        myDbManager.openDb();
        mainAdapter.updateAdapter(myDbManager.getFromDb(""));


    }


    public void onClickAdd(View view) {

        Intent i = new Intent(MainActivity.this, EditActivity.class);

        startActivity(i);

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        myDbManager.closeDb();
    }


    private ItemTouchHelper getItemTouchHelper(){
        return new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                AlertDialog.Builder builderDel = new AlertDialog.Builder(MainActivity.this);
                builderDel.setMessage("Удалить данную задачу")
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mainAdapter.removeItem(viewHolder.getAdapterPosition(), myDbManager);
                                    }
                                })
                        .setNegativeButton("нет", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                Intent i1 = new Intent(MainActivity.this, Parametrs.class);
                                startActivity(i1);

                            }
                        });
                AlertDialog d = builderDel.create();
                d.show();


//               mainAdapter.removeItem(viewHolder.getAdapterPosition(), myDbManager);
            }
        });
    }
}
