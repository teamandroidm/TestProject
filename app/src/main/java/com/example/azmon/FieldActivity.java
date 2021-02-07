package com.example.azmon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.azmon.Engine.RecyclerView.RecyclerViewAdapter;
import com.example.azmon.Engine.RecyclerView.RecyclerViewMethod;
import com.example.azmon.Engine.Utils;
import com.example.azmon.Models.Fields;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class FieldActivity extends AppCompatActivity {
    Dialog dialog;
    Button custom_dialog_button_ok;
    Toolbar toolbar;
    ArrayList<Fields> fieldArrayList = new ArrayList<>();
    private Data date = new Data();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field);
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_field);
        setUpView();

        setSupportActionBar(toolbar);
        setTitle(null);
        final Utils utils = new Utils(getApplicationContext(), FieldActivity.this);
        setRecyclerViewField(utils);
        fieldItemList();
    }

    private void fieldItemList() {
        String computer = picPath(R.drawable.computer);
        String tourists = picPath(R.drawable.tourists);
        String accounting = picPath(R.drawable.accounting);

        fieldArrayList.add(new Fields((byte) 1, "کامپیوتر", computer, true));
        fieldArrayList.add(new Fields((byte) 2, "گردشگری", tourists, false));
        fieldArrayList.add(new Fields((byte) 3, "حسابداری", accounting, false));
    }

    private void setUpView() {
        custom_dialog_button_ok = dialog.findViewById(R.id.custom_dialog_button_ok);

        toolbar = findViewById(R.id.activity_field_toolbar);

    }

    private void showCustomDialod() {
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }

    private void setRecyclerViewField(final Utils utils) {
        utils.addRecyclerView(R.id.activity_field_recyclerview, new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false),
                new RecyclerViewAdapter(getApplicationContext(), R.layout.activity_field_list_item, 3, new RecyclerViewMethod() {
                    @Override
                    public void onItem(RecyclerViewAdapter.ViewHolder holder, final int position, final View itemView) {
                        ((TextView) itemView.findViewById(R.id.activity_field_text_name)).setText(fieldArrayList.get(position).getFieldName() + "");
                        // final LinearLayout activity_field_layout= itemView.findViewById(R.id.activity_field_layout);
                        Picasso.get().load(new File(fieldArrayList.get(position).getImageName())).error(R.drawable.tourists).fit().centerCrop().into((ImageView) itemView.findViewById(R.id.activity_field_image_courses));

                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                 date.sendIdField(1, fieldArrayList.get(position).getFieldId(), new OnResult() {
                                    @Override
                                    public void success(Object... objects) {
                                        if ((Boolean) objects[0]) {
                                            Intent intent=new Intent(FieldActivity.this,CoursesActivity.class);
                                            intent.putExtra("fieldId",fieldArrayList.get(position).getFieldId());
                                            startActivity(intent);
                                            //utils.goTo(CoursesActivity.class,new PutExtra("fieldId",fieldArrayList.get(position).getFieldId()));

                                        } else{

                                            showCustomDialod();
                                            //    activity_field_layout.setBackgroundColor(R.drawable.background_list_feild_purpel);
                                            custom_dialog_button_ok.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    dialog.dismiss();
                                                }
                                            });
                                        }
                                    }

                                });

                            }
                        });

                    }
                }));

    }

    public String picPath(int res) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), res);
        File mfile1 = getExternalFilesDir(null);
        String filename = res + ".png";
        File mfile2 = new File(mfile1, filename);
        try {
            FileOutputStream fileOutputStream;
            fileOutputStream = new FileOutputStream(mfile2);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        String path = mfile1.getAbsolutePath().toString() + "/" + filename;

        return path;
    }
}
