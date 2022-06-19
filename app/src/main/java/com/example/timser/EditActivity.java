package com.example.timser;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.mtp.MtpConstants;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timser.adapter.ListItem;
import com.example.timser.db.MyDbManager;
import com.example.timser.db.myConstants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditActivity extends AppCompatActivity {

    private final int PICK_IMAGE_CODE = 123;
    private ImageView imNewImage;
    private ConstraintLayout imageContainer;
    private FloatingActionButton fbAddImage;
    private MyDbManager myDbManager;
    private EditText edTitle, edDisc;
    private String tempURI = "empty";
    private boolean isEditState = true;
    public int Buffer;
    public int Buffer1;
    ConstraintLayout Backer;
    private TextView dater;

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



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE_CODE && data!=null){
            imNewImage.setImageURI(data.getData());
            tempURI = data.getData().toString();
//            Toast.makeText(this, "ресулт", Toast.LENGTH_SHORT).show();
        }

    }

    private void init(){
        myDbManager = new MyDbManager(this);
        Backer = findViewById(R.id.Back);
        edTitle = findViewById(R.id.edTitle);
        edDisc = findViewById(R.id.edDisc);
        imageContainer = findViewById(R.id.imContainer);
        fbAddImage = findViewById(R.id.fbAddIm);
        imNewImage = findViewById(R.id.imageNew);
        dater = findViewById(R.id.Date);

    }
    public void getMyIntents(){
        Buffer = getIntent().getIntExtra("color", 0);
        int cp = Buffer;
        switch (cp){
            //Черный
            case 2:
                Backer.setBackgroundResource(R.drawable.edblak);
                edDisc.setBackgroundResource(R.color.black);
                edDisc.setTextColor(Color.WHITE);
                edTitle.setBackgroundResource(R.color.black);
                edTitle.setTextColor(Color.WHITE);
                dater.setTextColor(Color.WHITE);
                break;
            //Белый
            case 3:

                Backer.setBackgroundResource(R.drawable.edwhit);
                edTitle.setBackgroundResource(R.drawable.edwhiter);
                edTitle.setTextColor(Color.BLACK);
                edDisc.setBackgroundResource(R.drawable.edwhiter);
                edDisc.setTextColor(Color.BLACK);
                imageContainer.setBackgroundResource(R.color.greey);
                dater.setTextColor(Color.BLACK);
                break;
                //серый сток
            case 1:
                Backer.setBackgroundResource(R.drawable.fonge);
                edDisc.setBackgroundResource(R.drawable.edwhiter);
                edTitle.setBackgroundResource(R.drawable.edwhiter);
                edDisc.setTextColor(Color.BLACK);
                edTitle.setTextColor(Color.BLACK);
                dater.setTextColor(Color.WHITE);
                break;
            default:
                break;
        }


        Intent i = getIntent();
        if (i != null){
            ListItem item = (ListItem)i.getSerializableExtra(myConstants.LIST_ITEM_INTENT);
            isEditState = i.getBooleanExtra(myConstants.EDIT_STATE, true);

            if (!isEditState){
                edTitle.setText(item.getTitle());
                edDisc.setText(item.getDesc());
                dater.setText(item.getDate());
            }
        }


    }
    public void Save(View view){
        String title1= edTitle.getText().toString();
        String desc = edDisc.getText().toString();
        String tester = " (корректировка)";
        String title;
        String mode = "1";
        String status = "0";
        if(title1.equals(tester)){
            int index = title1.indexOf('(');
            title = title1.substring(0, index);
        }
        else
            title = title1;
        Date today = new Date();
        SimpleDateFormat DATA_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
        String date = DATA_FORMAT.format(today);
        Buffer = getIntent().getIntExtra("loc", 0);
        if (title.equals("")
                || desc.equals("")){
            Toast.makeText(this, R.string.errortitle, Toast.LENGTH_SHORT).show();
        } else {
            if (Buffer == 1){
                if(title.contains(tester)){
                    title = title.substring(0, title.indexOf(')'));
                    myDbManager.insertToDb(title +")"+"\n"+ date, desc, tempURI, mode, status, date);
                    Toast.makeText(this, R.string.save, Toast.LENGTH_SHORT).show();}
                else{
                    myDbManager.insertToDb(title + tester+"\n" + date, desc, tempURI, mode, status, date);
                    Toast.makeText(this, R.string.save, Toast.LENGTH_SHORT).show();
                }
            }
            else {
                myDbManager.insertToDb(title, desc, tempURI, mode, status, date);
                Toast.makeText(this, R.string.save, Toast.LENGTH_SHORT).show();
            }
            finish();
            myDbManager.closeDb();
        }
    }
    public void onClickDeleteImage(View view){
        imNewImage.setImageResource(R.drawable.ic_delimg);
        tempURI = "empty";
        imageContainer.setVisibility(View.GONE);
        fbAddImage.setVisibility(View.VISIBLE);
        dater.setVisibility(View.VISIBLE);
    }
//
//    @SuppressWarnings("deprecation")
    public void onClickAddImage(View view){
        imageContainer.setVisibility(View.VISIBLE);
        view.setVisibility(View.GONE);
        dater.setVisibility(View.GONE);
    }
    @SuppressWarnings("deprecation")
    public void onClickChooseImage(View view){
        Intent chooser = new Intent(Intent.ACTION_PICK);
        chooser.setType("image/*");
        startActivityForResult(chooser, PICK_IMAGE_CODE);
    }
}