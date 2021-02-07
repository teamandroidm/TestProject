package com.example.azmoon.Activitys;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.azmoon.Data;
import com.example.azmoon.Engine.DateConverter;
import com.example.azmoon.Engine.RecyclerAdapter.RecyclerViewAdapter;
import com.example.azmoon.Engine.RecyclerAdapter.RecyclerViewMethod;
import com.example.azmoon.Engine.Utils;
import com.example.azmoon.Models.Factors;
import com.example.azmoon.OnResult;
import com.example.azmoon.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


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
    Button changePassBtn, changePassDialogBtnNo, changePassDialogBtnYes, exitDialogBtnNo,
            exitDialogBtnYes, logOutDialogBtnNo, logOutDialogBtnYes;

    Data data;
    ArrayList<Factors> factors = new ArrayList<>();
    String termName;
    // for activity term
    boolean doubleBackPress = false;
    // for convert miladi date to shamsi date
    DateConverter dateConverter = new DateConverter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
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
        data = new Data(ProfileActivity.this);
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
        //TODO: set url (volley)

        data.getFactors("", new OnResult() {
            @Override
            public void success(Object... objects) {
                factors = (ArrayList<Factors>) objects[0];
            }
        });


        // fill user name and family
        data.getNameFamily(new OnResult() {
            @Override
            public void success(Object... objects) {
                txtNameFamily.setText((String) objects[0]);
            }
        });


        isEmptyFactors();
        setSupportActionBar(materialToolbar);
    }


    @Override
    public void onBackPressed() {
        finish();
        //region double press Exit for term activity
//        if (doubleBackPress) {
//
//            setDialogExit();
//
//            return;
//        }
//        this.doubleBackPress = true;
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                doubleBackPress = false;
//            }
//        }, 2000);
        //endregion

    }


    //region dialog exit
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
        // edit texts set empty
        edtNewPassAgain.setText("");
        edtNewPass.setText("");
        edtNewPass.setText("");

        changePassDialogBtnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassDialog.dismiss();
            }
        });

        //TODO:set url (volley)
        //  region change Pass with volley
        changePassDialogBtnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtNewPass.getText().toString().equals(edtNewPassAgain.getText().toString()) == false) {
                    edtLaNewPass.setError("رمزهای وارد شده با هم برابر نیستند");
                    edtLaNewPassAgain.setError("رمزهای وارد شده با هم برابر نیستند");

                } else {
                    edtLaNewPass.setError("");
                    edtLaNewPassAgain.setError("");
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {

                            data.NewPassword("", edtNewPass.getText().toString(), new OnResult() {
                                @Override
                                public void success(Object... objects) {
                                    if ((boolean) objects[0])
                                        changePassDialog.dismiss();
                                    else
                                        edtLaPass.setError("رمزعبور اشتباه است!");

                                }
                            });

                        }
                    });
                    thread.start();
                }

            }
        });
        // endregion

    }


    private void isEmptyFactors() {
        if (factors.size() == 0) {
            rcv1.setVisibility(View.GONE);
            emptyFactorLa.setVisibility(View.VISIBLE);
        } else {
            emptyFactorLa.setVisibility(View.GONE);
            rcv1.setVisibility(View.VISIBLE);
            setRecyclerViewFactor(utils);

        }

    }

    private void setRecyclerViewFactor(final Utils utils) {

        utils.addRecyclerView(rcv1, new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false),
                new RecyclerViewAdapter(getApplicationContext(),
                        R.layout.rcv_item_activity_profile, factors.size(), new RecyclerViewMethod() {
                    @Override
                    public void onItem(RecyclerViewAdapter.ViewHolder holder, int position, View itemView) {
                        TextView courseName = itemView.findViewById(R.id.rcv_item_activity_profile_txt_course_name);
                        TextView price = itemView.findViewById(R.id.rcv_item_activity_profile_txt_price);
                        TextView date = itemView.findViewById(R.id.rcv_item_activity_profile_txt_date);
                        TextView validityDate = itemView.findViewById(R.id.rcv_item_activity_profile_txt_validity_date);

                        data.getTermName(factors.get(position).getTermId(), new OnResult() {
                            @Override
                            public void success(Object... objects) {
                                termName = (String) objects[0];
                            }
                        });
                        courseName.setText(termName);
                        price.setText(utils.toPersianNumber4(utils.splitDigits(factors.get(position).getPrice())));

                        dateConverter.gregorianToPersian(Integer.parseInt(new SimpleDateFormat("yyyy").format(factors.get(position).getFinallyDate())), Integer.parseInt(new SimpleDateFormat("MM").format(factors.get(position).getFinallyDate())), Integer.parseInt(new SimpleDateFormat("dd").format(factors.get(position).getFinallyDate())));
                        date.setText(utils.toPersianNumber4(dateConverter.toString()));
                        // add day in finally date
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(factors.get(position).getFinallyDate());
                        calendar.add(Calendar.DATE, factors.get(position).getValidateTime());

                        dateConverter.gregorianToPersian(Integer.parseInt(new SimpleDateFormat("yyyy").format(calendar.getTime())), Integer.parseInt(new SimpleDateFormat("MM").format(calendar.getTime())), Integer.parseInt(new SimpleDateFormat("dd").format(calendar.getTime())));

                        validityDate.setText(utils.toPersianNumber4(dateConverter.toString()));


                    }
                }));

    }


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
