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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.azmon.Engine.RecyclerView.RecyclerViewAdapter;
import com.example.azmon.Engine.RecyclerView.RecyclerViewMethod;
import com.example.azmon.Engine.Utils;
import com.example.azmon.Models.Terms;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

public class CoursesActivity extends AppCompatActivity {
    Dialog dialog;
    Button custom_dialog_button_courses_payment, custom_dialog_button_courses_cancel;
    TextView custom_dialog_text_courses_type, custom_dialog_text_courses_description, custom_dialog_text_courses_price;
    Toolbar toolbar;
    ArrayList<Terms> termsArrayList = new ArrayList<>();
    ArrayList<Terms> arrayList = new ArrayList<>();
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_buy_courses);
        setUpView();
        setSupportActionBar(toolbar);
        setTitle(null);
        final Utils utils = new Utils(getApplicationContext(), CoursesActivity.this);
        termsItemList();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CoursesActivity.this, FieldActivity.class);
                startActivity(intent);
            }
        });
        Bundle extrns = getIntent().getExtras();
        byte x = (byte) ((byte) extrns.getByte("fieldId"));
        //Toast.makeText(CoursesActivity.this,x + "",Toast.LENGTH_LONG).show();
        arrayList = fillTearm(x);
        setRecyclerViewCourses(utils);


    }

    private void termsItemList() {
        String java = picPath(R.drawable.java);
        String edit = picPath(R.drawable.edit);
        String excel = picPath(R.drawable.excel);
        String access = picPath(R.drawable.access);
        String powerpoint = picPath(R.drawable.powerpoint);
        String word = picPath(R.drawable.word);
        String tourists = picPath(R.drawable.tourists);
        String accounting = picPath(R.drawable.accounting);
        String hashtag = picPath(R.drawable.hashtag);
        termsArrayList.add(new Terms(1, "جاوا", java, 20000, true, 50, (byte) 50, (byte) 1));
        termsArrayList.add(new Terms(2, "پاورپوینت", powerpoint, 30000, true, 50, (byte) 50, (byte) 1));
        termsArrayList.add(new Terms(3, "اکسل", excel, 40000, true, 50, (byte) 50, (byte) 1));
        termsArrayList.add(new Terms(4, "اکسس", access, 12000, true, 50, (byte) 50, (byte) 1));
        termsArrayList.add(new Terms(5, "سی شارپ", hashtag, 18000, true, 50, (byte) 50, (byte) 1));
        termsArrayList.add(new Terms(6, "ورد", word, 25000, true, 50, (byte) 50, (byte) 1));
        termsArrayList.add(new Terms(7, "سی پلاس پلاس", edit, 32000, true, 50, (byte) 50, (byte) 1));
        termsArrayList.add(new Terms(1, "هلو", accounting, 20000, true, 50, (byte) 50, (byte) 3));
        termsArrayList.add(new Terms(2, "محک", accounting, 30000, true, 50, (byte) 50, (byte) 3));
        termsArrayList.add(new Terms(3, "قیاس", accounting, 22000, true, 50, (byte) 50, (byte) 3));
        termsArrayList.add(new Terms(4, "شایگان", accounting, 18000, true, 50, (byte) 50, (byte) 3));
        termsArrayList.add(new Terms(2, "تدبیر", accounting, 15000, true, 50, (byte) 50, (byte) 3));
        termsArrayList.add(new Terms(3, "پیوست", accounting, 40000, true, 50, (byte) 50, (byte) 3));
        termsArrayList.add(new Terms(4, "آرین سیستم", accounting, 82000, true, 50, (byte) 50, (byte) 3));
        termsArrayList.add(new Terms(2, "مدیریت جهانگردی", tourists, 30000, true, 50, (byte) 50, (byte) 2));
        termsArrayList.add(new Terms(3, "جغرافبا", tourists, 22000, true, 50, (byte) 50, (byte) 2));
        termsArrayList.add(new Terms(4, "مسافرتی", tourists, 18000, true, 50, (byte) 50, (byte) 2));
    }

    private void setUpView() {
        custom_dialog_button_courses_payment = dialog.findViewById(R.id.custom_dialog_button_courses_payment);
        custom_dialog_button_courses_cancel = dialog.findViewById(R.id.custom_dialog_button_courses_cancel);
        custom_dialog_text_courses_type = dialog.findViewById(R.id.custom_dialog_text_courses_type);
        custom_dialog_text_courses_description = dialog.findViewById(R.id.custom_dialog_text_courses_description);
        custom_dialog_text_courses_price = dialog.findViewById(R.id.custom_dialog_text_courses_price);
        toolbar = findViewById(R.id.activity_courses_toolbar);
        back = findViewById(R.id.back);

    }

    private void showCustomDialod() {
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }

    private void setRecyclerViewCourses(final Utils utils) {

        utils.addRecyclerView(R.id.activity_courses_recyclerview, new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false),
                new RecyclerViewAdapter(getApplicationContext(), R.layout.activity_courses_list_item, arrayList.size(), new RecyclerViewMethod() {
                    @Override
                    public void onItem(RecyclerViewAdapter.ViewHolder holder, int position, View itemView) {

                        Picasso.get().load(new File(arrayList.get(position).getImageName())).error(R.drawable.tourists).into((ImageView) itemView.findViewById(R.id.activity_courses_image_courses));
                        ((TextView) itemView.findViewById(R.id.activity_courses_text_name)).setText(arrayList.get(position).getTermName() + "");
                        ((TextView) itemView.findViewById(R.id.activity_courses_text_price)).setText(splitDigits(arrayList.get(position).getPrice()) + "");
                        //    Picasso.get().load(new File(termsArrayList.get(position).getImageName())).error(R.mipmap.logocourses).fit().centerCrop().into((ImageView) itemView.findViewById(R.id.activity_courses_image_courses));
                        Picasso.get().load(new File(arrayList.get(position).getImageName())).error(R.drawable.tourists).fit().centerCrop().into((ImageView) itemView.findViewById(R.id.activity_courses_image_courses));

                        final Button activity_courses_button_cart = itemView.findViewById(R.id.activity_courses_button_cart);
                        final LinearLayout activity_courses_layout_validity = itemView.findViewById(R.id.activity_courses_layout_validity);

                        activity_courses_layout_validity.setVisibility(View.GONE);

                        activity_courses_button_cart.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                showCustomDialod();
                                custom_dialog_button_courses_payment.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        activity_courses_layout_validity.setVisibility(View.VISIBLE);
                                        activity_courses_button_cart.setVisibility(View.GONE);
                                       // Date date = new Date();
                                       // Toast.makeText(CoursesActivity.this,date + "", Toast.LENGTH_LONG).show();
                                        dialog.dismiss();

                                    }
                                });
                                custom_dialog_button_courses_cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                });

                            }
                        });

                    }
                }));

    }

    public ArrayList<Terms> fillTearm(byte fieldId) {
        ArrayList<Terms> arrayList = new ArrayList<>();
        for (int i = 0; i < termsArrayList.size(); i++) {
            if (termsArrayList.get(i).getFieldId() == fieldId) {
                arrayList.add(termsArrayList.get(i));
            }
        }
        //Toast.makeText(CoursesActivity.this,arrayList.size(),Toast.LENGTH_LONG).show();
        return arrayList;
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

    public static String splitDigits(int number) {
        return new DecimalFormat("###,###,###").format(number);
    }
}
