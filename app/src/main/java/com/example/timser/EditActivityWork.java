package com.example.timser;
import com.example.timser.db.MyDbManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.timser.db.MyDbManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditActivityWork extends AppCompatActivity {
    private FloatingActionButton AddWork;
    private EditText edTitle, edDisc;
    private MyDbManager myDbManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_work);
        initWork();
    }

    @Override
    protected void onResume() {
        super.onResume();
        myDbManager.openDb();
    }

    private void initWork(){

        myDbManager = new MyDbManager(this);
        edTitle = findViewById(R.id.tvTitle);
        edDisc = findViewById(R.id.tvDisc);

    }
    public void getMyIntents(){
        Intent i  = getIntent();
    }

}
