package com.example.azmoon;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.azmoon.Models.Factors;
import com.example.azmoon.Models.Terms;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class Data {
    Context context;
   // RequestQueue requestQueue = Volley.newRequestQueue(context);
    ArrayList<Factors> factorsList = new ArrayList<>();

    public Data(Context context) {
        this.context = context;
    }

    public void getFactors(String url, OnResult onResult) {
        // user id from class G
        int userId = 2;
        //

//        final StringRequest request = new StringRequest(
//                Request.Method.POST,
//                url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Gson gson = new Gson();
//                        Type listType = new TypeToken<ArrayList<Factors>>() {
//                        }.getType();
//                        factorsList = gson.fromJson(response, listType);
//                    }
//                }
//                , new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        requestQueue.add(request);

        for (Factors factor : fakeDataFactor()) {
            if (factor.getUserId() == userId && factor.isFinally()) {
                factorsList.add(factor);
            }
        }
        onResult.success(factorsList);

    }

    public void getTermName(int termId, OnResult onResult) {
        ArrayList<Terms> termsList = fakeDataTerm();
        for (Terms terms : termsList
        ) {
            if (terms.getTermId() == termId)
                onResult.success(terms.getTermName());
        }

    }


    public void NewPassword(String url, String newPass, OnResult onResult) {
        // user id from class G
        int userId = 2;


//        JSONObject user = new JSONObject();
//        try {
//            user.put("password", newPass);
//            user.put("userid", userId);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        JsonObjectRequest request = new JsonObjectRequest(
//                Request.Method.POST,
//                url,
//                user,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                });
//        requestQueue.add(request);

        onResult.success(true);
    }

    public void getNameFamily(OnResult onResult) {
        // user id from class G
        String name = "هانیه";
        String family = "پروین";
        //
        onResult.success( name+" "+ family );
    }

    public ArrayList<Factors> fakeDataFactor() {
        ArrayList<Factors> factorsArrayList = new ArrayList<>();
        Factors factors = new Factors();
        factors.setFactorId(1);
        factors.setFinally(true);
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

    public ArrayList<Terms> fakeDataTerm() {
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


}
