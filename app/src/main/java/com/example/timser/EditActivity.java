package com.example.timser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.mtp.MtpConstants;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.timser.adapter.ListItem;
import com.example.timser.db.MyDbManager;
import com.example.timser.db.myConstants;

public class EditActivity extends AppCompatActivity {

    private MyDbManager myDbManager;
    private EditText edTitle, edDisc;
    private boolean isEditState = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        init();
        getMyIntents();
    }
    @Override
    protected void onResume() {
        super.onResume();
        myDbManager.openDb();
    }
    private void init(){
        myDbManager = new MyDbManager(this);
        edTitle = findViewById(R.id.edTitle);
        edDisc = findViewById(R.id.edDisc);
    }
    public void getMyIntents(){
        Intent i = getIntent();
        if (i != null){
            ListItem item = (ListItem)i.getSerializableExtra(myConstants.LIST_ITEM_INTENT);
            isEditState = i.getBooleanExtra(myConstants.EDIT_STATE, true);

            if (!isEditState){
                edTitle.setText(item.getTitle());
                edDisc.setText(item.getDesc());
            }
        }
    }
    public void Save(View view){

        String title = edTitle.getText().toString();
        String desc = edDisc.getText().toString();

        if (title.equals("")
                || desc.equals("")){
            Toast.makeText(this, R.string.errortitle, Toast.LENGTH_SHORT).show();
        } else {
            myDbManager.insertToDb(title, desc);
            Toast.makeText(this, R.string.save, Toast.LENGTH_SHORT).show();
            finish();
            myDbManager.closeDb();
        }

    }
}