package com.example.azmoon;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.LocaleData;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.azmoon.Engine.RecyclerAdapter.RecyclerViewAdapter;
import com.example.azmoon.Engine.RecyclerAdapter.RecyclerViewMethod;
import com.example.azmoon.Engine.Utils;
import com.example.azmoon.Models.Factors;
import com.example.azmoon.Models.Terms;
import com.example.azmoon.Models.Users;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class ProfileActivity extends AppCompatActivity {

    Utils utils;
    TextView txtNameFamily;
    RecyclerView rcv1;
    ImageView imgBack;
    LinearLayout emptyFactorLa;
    MaterialToolbar materialToolbar;
    Dialog changePassDialog, exitDialog, logOutDialog;
    TextInputEditText edtPass, edtNewPass, edtNewPassAgain;
    TextInputLayout edtLaPass, edtLaNewPass, edtLaNewPassAgain;
    Button changePassBtn, changePassDialogBtnNo, changePassDialogBtnYes,exitDialogBtnNo,
            exitDialogBtnYes,logOutDialogBtnNo,logOutDialogBtnYes;

    //TODO:volley
    //region volley
    RequestQueue requestQueue = null;
    ArrayList<Factors> factors2=new ArrayList<>();
    ArrayList<Users> user=new ArrayList<>();
    int userId=2;
    //endregion

    ArrayList<Factors> factors=new ArrayList<>() ;
    ArrayList<Factors> factors1 = fakeData();
    ArrayList<Terms> terms = fakeDataTerm();
    boolean doubleBackPress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        requestQueue = Volley.newRequestQueue(this);
        changePassDialog = new Dialog(ProfileActivity.this);
        exitDialog = new Dialog(ProfileActivity.this);
        logOutDialog = new Dialog(ProfileActivity.this);
        changePassDialog.setContentView(R.layout.custom_dialog_acticity_profile_change_pass);
        changePassDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        exitDialog.setContentView(R.layout.custom_dialog_exit);
        exitDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        logOutDialog.setContentView(R.layout.custom_dialog_logout);
        logOutDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        init();
        changePassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDialogChangePass();
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        for (Factors factor :factors1) {
            if (factor.getUserId() == userId&&factor.isFinally()) {
                factors.add(factor);
            }
        }

        isEmptyFactors();
        setSupportActionBar(materialToolbar);

        //TODO: volley
        //region get All Factor & get All User with volley
//        Thread thread=new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                factors2=getAllFactor("");
//                                user=getAllUser("");
//                            }
//                        });
//                        thread.start();
        //endregion

        //TODO: volley
        //region username
        //        for (Users users:user
//             ) {
//            if (users.getUserId()==userId)
//                txtNameFamily.setText(users.getName()+" "+users.getFamily());
//
//        }
        //endregion

    }

    //region double press Exit

    @Override
    public void onBackPressed() {
        if (doubleBackPress) {

            setDialogExit();

            return;
        }
        this.doubleBackPress = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackPress = false;
            }
        }, 2000);

    }
    //endregion

    //region dialog log out
    private void setDialogLogOut() {
        logOutDialog.setCancelable(false);
        logOutDialog.show();
        logOutDialogBtnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOutDialog.dismiss();
            }
        });
    }
    //endregion


    private void setDialogChangePass() {
        changePassDialog.setCancelable(false);
        changePassDialog.show();
        changePassDialogBtnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassDialog.dismiss();
            }
        });
        if (edtNewPass.getText().toString().equals(edtNewPassAgain.getText().toString())==false){
            edtLaNewPass.setError("رمزهای وارد شده با هم برابر نیستند");
            edtLaNewPassAgain.setError("رمزهای وارد شده با هم برابر نیستند");
        }
        else {
            edtLaNewPass.setError("");
            edtLaNewPassAgain.setError("");

        }

        //TODO:volley
        //region change Pass with volley
        //        changePassDialogBtnYes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                for (Users users:user
//                     ) {
//                    if (users.getUserId()==userId&&users.getPassword().equals(edtPass.getText().toString()))
//                    {
//                        Thread thread=new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                String newPass = edtNewPass.getText().toString();
//
//                                JSONObject user = new JSONObject();
//                                try {
//                                    user.put("password",newPass);
//                                    user.put("userid",userId);
//                                    sendNewPassword("",user);
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//
//                            }
//                        });
//                        thread.start();
//                        break;
//
//                    }
//                    else
//                        edtLaPass.setError("رمزعبور اشتباه است!");
//                }
//
//                changePassDialog.dismiss();
//            }
//        });
        //endregion

        }

    private void setDialogExit() {
        exitDialog.setCancelable(false);
        exitDialog.show();
        exitDialogBtnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitDialog.dismiss();
            }
        });
        exitDialogBtnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                finish();
            }
        });
    }
    private void isEmptyFactors(){
        if (factors.size()==0){
            rcv1.setVisibility(View.GONE);
            emptyFactorLa.setVisibility(View.VISIBLE);

        }
        //TODO:volley

        //region is empty factor with volley
        //        if (factors2.size()==0){
//            emptyFactorLa.setVisibility(View.GONE);
//        }
        //endregion
        else{
            emptyFactorLa.setVisibility(View.GONE);
            rcv1.setVisibility(View.VISIBLE);
            setRecyclerViewFactor(utils);

        }

    }

    private void setRecyclerViewFactor(final Utils utils) {

        utils.addRecyclerView(rcv1, new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false),
                new RecyclerViewAdapter(getApplicationContext(),
                        R.layout.rcv_item_activity_profile, factors.size() , new RecyclerViewMethod() {
                    @Override
                    public void onItem(RecyclerViewAdapter.ViewHolder holder, int position, View itemView) {
                        TextView courseName = itemView.findViewById(R.id.rcv_item_activity_profile_txt_course_name);
                        TextView price = itemView.findViewById(R.id.rcv_item_activity_profile_txt_price);
                        TextView date = itemView.findViewById(R.id.rcv_item_activity_profile_txt_date);
                        TextView validityDate = itemView.findViewById(R.id.rcv_item_activity_profile_txt_validity_date);

                        //TODO: volley
                        // region factor with volley
//
//                        if (factors2.get(position).getUserId() == 2 && factors2.get(position).isFinally()) {
//                                courseName.setText(getTermById("", factors2.get(position).getTermId()));
//                                price.setText(utils.toPersianNumber4(utils.splitDigits(factors2.get(position).getPrice())));
//                                date.setText(utils.toPersianNumber4(new SimpleDateFormat("yyyy/MM/dd").format(factors2.get(position).getFinallyDate().toString())));
//                                validityDate.setText(utils.toPersianNumber4(new SimpleDateFormat("yyyy/MM/dd").format(factors2.get(position).getFinallyDate().getTime() + (factors2.get(position).getValidateTime()*86400000))));
//
//                            }
                        //endregion

                                for (Terms terms:terms
                                     ) {
                                    if (terms.getTermId()==factors.get(position).getTermId())
                                    {
                                        courseName.setText(terms.getTermName());

                                    }
                        }
                        price.setText(utils.toPersianNumber4(utils.splitDigits(factors.get(position).getPrice())));
                        date.setText(utils.toPersianNumber4(new SimpleDateFormat("yyyy/MM/dd").format(factors.get(position).getFinallyDate())));
                        validityDate.setText(utils.toPersianNumber4(new SimpleDateFormat("yyyy/MM/dd").format(factors.get(position).getFinallyDate().getTime()+(factors.get(position).getValidateTime()*86400000))));


                    }
                }));

    }

    //region get All User with volley
    public ArrayList<Users> getAllUser(String url) {
        final ArrayList<Users>[] usersList = new ArrayList[]{new ArrayList<>()};
        final StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Type listType = new TypeToken<ArrayList<Users>>() {
                        }.getType();
                        usersList[0] = gson.fromJson(response, listType);
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });
        requestQueue.add(request);
        return usersList[0];
    }
    //endregion

    //region get Term By Id with volley
    public String getTermById(String url, final byte id) {
        final String[] s = new String[1];
        final StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Type listType = new TypeToken<ArrayList<Terms>>() {
                        }.getType();
                        ArrayList<Terms> termsList = gson.fromJson(response, listType);
                        for (Terms terms : termsList) {
                            if (terms.getTermId() == id)
                                s[0] = terms.getTermName();
                        }

                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });
        requestQueue.add(request);
        return s[0];
    }
    //endregion

    //region get All Factor with volley
    public ArrayList<Factors> getAllFactor(String url) {
        final ArrayList<Factors>[] factorsList = new ArrayList[]{new ArrayList<>()};
        final StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Type listType = new TypeToken<ArrayList<Factors>>() {
                        }.getType();
                        factorsList[0] = gson.fromJson(response, listType);
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });
        requestQueue.add(request);
        return factorsList[0];
    }
    //endregion

    //region change Password with volley
    private void sendNewPassword(String url,JSONObject jsonObject) {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(request);
    }
    //endregion

    //region fake data factor
    private ArrayList<Factors> fakeData() {
        ArrayList<Factors> factorsArrayList = new ArrayList<>();
        Factors factors = new Factors();
        factors.setFactorId(1);
        factors.setFinally(true);
        new DateFormat() {
            @Override
            public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
                return null;
            }

            @Override
            public Date parse(String source, ParsePosition pos) {
                return null;
            }
        };

        factors.setFinallyDate(new Date());
        factors.setPrice(30000);
        factors.setTermId((byte) 1);
        factors.setUserId(2);
        factors.setValidateTime(14);
        factorsArrayList.add(factors);

        /////////
        Factors factors1 = new Factors();
        factors1.setFactorId(2);
        factors1.setFinally(true);
        factors1.setFinallyDate(new Date(new Date().getTime() - 1000));
        factors1.setPrice(20000);
        factors1.setTermId((byte) 5);
        factors1.setUserId(2);
        factors1.setValidateTime(14);
        factorsArrayList.add(factors1);
        /////////////
        Factors factors2 = new Factors();
        factors2.setFactorId(3);
        factors2.setFinally(true);
        factors2.setFinallyDate(new Date(new Date().getTime() - 2000));
        factors2.setPrice(30000);
        factors2.setTermId((byte) 1);
        factors2.setUserId(3);
        factors2.setValidateTime(14);
        factorsArrayList.add(factors2);
        ////////////
        Factors factors3 = new Factors();
        factors3.setFactorId(4);
        factors3.setFinally(false);
        factors3.setFinallyDate(new Date(new Date().getTime() - 1000));
        factors3.setPrice(30000);
        factors3.setTermId((byte) 1);
        factors3.setUserId(2);
        factors3.setValidateTime(14);
        factorsArrayList.add(factors3);

        return factorsArrayList;

    }
    //endregion

    //region fake data term
    private ArrayList<Terms> fakeDataTerm() {
        ArrayList<Terms> termsArrayList = new ArrayList<>();
        Terms terms = new Terms();
        terms.setTermId(1);
        terms.setPrice(30000);
        terms.setTermName("جاوا");

        termsArrayList.add(terms);
        /////////
        Terms terms1 = new Terms();
        terms1.setTermId(5);
        terms1.setPrice(20000);
        terms1.setTermName("icdl");

        termsArrayList.add(terms1);

        return termsArrayList;

    }
    //endregion



    private void init() {
        rcv1 = findViewById(R.id.activity_profile_rcv_factor);
        materialToolbar = findViewById(R.id.activity_profile_toolbar);
        txtNameFamily = findViewById(R.id.activity_profile_txt_user_name);
        changePassBtn = findViewById(R.id.activity_profile_btn_change_pass);
        changePassDialogBtnNo = changePassDialog.findViewById(R.id.custom_dialog_activity_profile_change_pass_btn_no);
        changePassDialogBtnYes = changePassDialog.findViewById(R.id.custom_dialog_activity_profile_change_pass_btn_yes);
        exitDialogBtnNo = exitDialog.findViewById(R.id.custom_dialog_exit_btn_no);
        exitDialogBtnYes = exitDialog.findViewById(R.id.custom_dialog_exit_btn_yes);
        logOutDialogBtnNo = logOutDialog.findViewById(R.id.custom_dialog_logout_btn_no);
        logOutDialogBtnYes = logOutDialog.findViewById(R.id.custom_dialog_logout_btn_yes);
        edtNewPass = changePassDialog.findViewById(R.id.custom_dialog_activity_profile_change_pass_edt_newpass);
        edtNewPassAgain = changePassDialog.findViewById(R.id.custom_dialog_activity_profile_change_pass_edt_newpass_again);
        edtPass = changePassDialog.findViewById(R.id.custom_dialog_activity_profile_change_pass_edt_pass);
        edtLaPass = changePassDialog.findViewById(R.id.custom_dialog_activity_profile_change_pass_edtla_pass);
        edtLaNewPass = changePassDialog.findViewById(R.id.custom_dialog_activity_profile_change_pass_edtla_newpass);
        edtLaNewPassAgain = changePassDialog.findViewById(R.id.custom_dialog_activity_profile_change_pass_edtla_newpass_again);
        imgBack = findViewById(R.id.activity_profile_toolbar_img_back);
        emptyFactorLa = findViewById(R.id.activity_profile_empty_layout);
        utils = new Utils(getApplicationContext(), ProfileActivity.this);

    }
}
