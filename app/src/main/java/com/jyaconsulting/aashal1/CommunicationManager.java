package com.jyaconsulting.aashal1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jyaconsulting.aashal1.utilinterface.CMResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CommunicationManager {
    Activity activity = null;
    String encryptLogin;

    class C05321 implements Listener<String> {
        C05321() {
        }

        public void onResponse(String response) {
            try {
                ((CMResponse) CommunicationManager.this.activity).consumeResponse(response, true);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class C05332 implements ErrorListener {
        C05332() {
        }

        public void onErrorResponse(VolleyError error) {
            try {
                ((CMResponse) CommunicationManager.this.activity).consumeResponse(error.getMessage(), false);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    class C05343 implements RetryPolicy {
        C05343() {
        }

        public int getCurrentTimeout() {
            return 50000;
        }

        public int getCurrentRetryCount() {
            return 50000;
        }

        public void retry(VolleyError error) throws VolleyError {
        }
    }

    class C05377 implements RetryPolicy {
        C05377() {
        }

        public int getCurrentTimeout() {
            return 50000;
        }

        public int getCurrentRetryCount() {
            return 50000;
        }

        public void retry(VolleyError error) throws VolleyError {
        }
    }

    public CommunicationManager(Activity c) {
        this.activity = c;
    }

    public void calculateUserLoggedInfo(String resultString) {
        JSONObject jArray;
        try {
            if (resultString != null) {
                jArray = new JSONObject(resultString);
                int rLength = jArray.length();
            }
        } catch (JSONException e) {
            Toast.makeText(activity, e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public void getRequestJsonObj(String link, String url, final String login, final CMResponse responseInterface) {
        encryptLogin = login;

//        viewDialog.showDialog();
        final ProgressDialog progressDialog = new ProgressDialog(this.activity, R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Working, Please Wait...");
        progressDialog.setProgressStyle(0);
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(this.activity.getApplicationContext());
        //  String hostURL = getHostURL(this.activity);
//        final JSONObject jSONObject = parameters;
//        String link = Utils.getPreferences("link", this.activity);
        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.GET, "http://" + link + "/" + url, null, new Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                if (response != null) {
//                    usernameET.getText().clear();
//                    passwordET.getText().clear();
                    try {
                        responseInterface.consumeResponse(response.toString(), true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    calculateUserLoggedInfo( response.toString());
                } else {
//                    Toast();
                }
                progressDialog.dismiss();

            }

        }, new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                if (error instanceof TimeoutError) {

                    try {
                        responseInterface.consumeResponse("Connection Timeout", false);
//                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (error instanceof NoConnectionError) {
                    try {
                        if (error.equals("java.net.ConnectException: failed to connect to /203.124.51.69 (port 5526) after 2500ms: isConnected failed: EHOSTUNREACH (No route to host)")) {
                            responseInterface.consumeResponse("Wrong Ip Address or Port", false);
                        } else {
                            responseInterface.consumeResponse("Internet is not available", false);
                        }
//                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (error instanceof AuthFailureError) {
                    try {
                        responseInterface.consumeResponse("Enter a valid UserName and Password", false);
//                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (error instanceof ServerError) {
                    try {
                        NetworkResponse response = error.networkResponse;
                        if (response != null) {
                            String res = null;
                            try {
                                res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                responseInterface.consumeResponse(res, false);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        } else {
                            responseInterface.consumeResponse("Server cannot be found", false);
                        }
//                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (error instanceof NetworkError) {
                    try {
                        responseInterface.consumeResponse("Internet is not available", false);
//                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (error instanceof ParseError) {
                    try {
                        responseInterface.consumeResponse("Data can not be Parsed", false);
//                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }) {

            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");

                String authorizationString = encryptLogin; //this line is diffe
                headers.put("Authorization", authorizationString);
                return headers;
            }
        };

        jsonObject.setRetryPolicy(new DefaultRetryPolicy(
                12000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObject);
//        queue.add(stringRequest);
    }

    public void getRequestJsonArr(String link, String url, final String login, final CMResponse responseInterface) {
        encryptLogin = login;
        final ProgressDialog progressDialog = new ProgressDialog(this.activity, R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Working, Please Wait...");
        progressDialog.setProgressStyle(0);
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(this.activity.getApplicationContext());
        //  String hostURL = getHostURL(this.activity);
//        final JSONObject jSONObject = parameters;
//        String link = Utils.getPreferences("link", this.activity);
        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, "http://" + link + "/" + url, null, new Listener<JSONArray>() {
            public void onResponse(JSONArray response) {
                if (response != null) {
//                    usernameET.getText().clear();
//                    passwordET.getText().clear();
                    try {
                        responseInterface.consumeResponse(response.toString(), true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    calculateUserLoggedInfo( response.toString());
                } else {
//                    Toast();
                }
                progressDialog.dismiss();

            }

        }, new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();

                if (error instanceof TimeoutError) {

                    try {
                        responseInterface.consumeResponse("Connection Timeout", false);
//                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (error instanceof NoConnectionError) {
                    try {
                        responseInterface.consumeResponse("Internet Not Available", false);
//                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (error instanceof AuthFailureError) {
                    try {
                        responseInterface.consumeResponse("Enter a valid UserName and Password", false);
//                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (error instanceof ServerError) {
                    try {
                        NetworkResponse response = error.networkResponse;
                        if (response != null) {
                            String res = null;
                            try {
                                res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                responseInterface.consumeResponse(res, false);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        } else {
                            responseInterface.consumeResponse("Server cannot be found", false);
                        }
//                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (error instanceof NetworkError) {
                    try {
                        responseInterface.consumeResponse("Can not connect to the internet", false);
//                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (error instanceof ParseError) {
                    try {
                        responseInterface.consumeResponse("Data can not be Parsed", false);
//                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }) {

            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");

                String authorizationString = encryptLogin; //this line is diffe
                headers.put("Authorization", authorizationString);
                return headers;
            }
        };

        jsonArray.setRetryPolicy(new DefaultRetryPolicy(
                12000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(jsonArray);
//        queue.add(stringRequest);
    }

    public void postRequest(String link, String url, final String header, JSONObject parameters, final CMResponse responseInterface) {
        final ProgressDialog progressDialog = new ProgressDialog(this.activity, R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Working, Please Wait...");
        progressDialog.setProgressStyle(0);
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(this.activity.getApplicationContext());
        final JSONObject jsonObject = parameters;
//        String hostURL = getHostURL(this.activity);
//        Map<String, String> body = new HashMap<>();
//        body.put("UserName", userName);
//        body.put("OldPassword", oldPassword);
//        body.put("NewPassword", newPassword);
//        final JSONObject finalJsonObject = jsonObject;
//        String link = Utils.getPreferences("link", this.activity);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://" + link + "/" + url, new Listener<String>() {
            public void onResponse(String response) {
                if (response != null) {
//                    usernameET.getText().clear();
//                    passwordET.getText().clear();
                    try {
                        responseInterface.consumeResponse(response.toString(), true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    calculateUserLoggedInfo( response.toString());
                } else {
//                    Toast();
                }
                progressDialog.dismiss();

            }

        }, new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                if (error instanceof TimeoutError) {

                    try {
                        responseInterface.consumeResponse("Connection Timeout", false);
//                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (error instanceof NoConnectionError) {
                    try {
                        responseInterface.consumeResponse("Internet Not Available", false);
//                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (error instanceof AuthFailureError) {
                    try {
                        responseInterface.consumeResponse("Enter a valid UserName and Password", false);
//                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (error instanceof ServerError) {
                    try {
                        NetworkResponse response = error.networkResponse;
                        if (response != null) {
                            String res = null;
                            try {
                                res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                responseInterface.consumeResponse(res, false);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        } else {
                            responseInterface.consumeResponse("Server cannot be found", false);
                        }
//                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (error instanceof NetworkError) {
                    try {
                        responseInterface.consumeResponse("Can not connect to the internet", false);
//                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (error instanceof ParseError) {
                    try {
                        responseInterface.consumeResponse("Data can not be Parsed", false);
//                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap();
                Iterator<String> iter = jsonObject.keys();
                while (iter.hasNext()) {
                    String key = (String) iter.next();
                    try {
                        params.put(key, (String) jsonObject.get(key));
                    } catch (JSONException e) {
                    }
                }
                return params;
            }

            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                String authorizationString = header; //this line is diffe
                headers.put("Authorization", authorizationString);
//                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                12000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        jsonObjectRequest.setRetryPolicy(new C05377());
        queue.add(stringRequest);
    }

    public void postStringRequest(String link, String url, final String header, final CMResponse responseInterface) {
        final ProgressDialog progressDialog = new ProgressDialog(this.activity, R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Working, Please Wait...");
        progressDialog.setProgressStyle(0);
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(this.activity.getApplicationContext());
//        final JSONObject jsonObject = parameters;
//        String hostURL = getHostURL(this.activity);
//        Map<String, String> body = new HashMap<>();
//        body.put("UserName", userName);
//        body.put("OldPassword", oldPassword);
//        body.put("NewPassword", newPassword);
//        final JSONObject finalJsonObject = jsonObject;
//        String link = Utils.getPreferences("link", this.activity);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://" + link + "/" + url, new Listener<String>() {
            public void onResponse(String response) {
                if (response != null) {
//                    usernameET.getText().clear();
//                    passwordET.getText().clear();
                    try {
                        responseInterface.consumeResponse(response.toString(), true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    calculateUserLoggedInfo( response.toString());
                } else {
//                    Toast();
                }
                progressDialog.dismiss();

            }

        }, new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                if (error instanceof TimeoutError) {

                    try {
                        responseInterface.consumeResponse("Connection Timeout", false);
//                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (error instanceof NoConnectionError) {
                    try {
                        responseInterface.consumeResponse("Internet Not Available", false);
//                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (error instanceof AuthFailureError) {
                    try {
                        responseInterface.consumeResponse("Enter a valid UserName and Password", false);
//                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (error instanceof ServerError) {
                    try {
                        NetworkResponse response = error.networkResponse;
                        if (response != null) {
                            String res = null;
                            try {
                                res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                responseInterface.consumeResponse(res, false);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        } else {
                            responseInterface.consumeResponse("Server cannot be found", false);
                        }
//                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (error instanceof NetworkError) {
                    try {
                        responseInterface.consumeResponse("Can not connect to the internet", false);
//                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (error instanceof ParseError) {
                    try {
                        responseInterface.consumeResponse("Data can not be Parsed", false);
//                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }) {

            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                String authorizationString = header; //this line is diffe
                headers.put("Authorization", authorizationString);
//                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                12000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        jsonObjectRequest.setRetryPolicy(new C05377());
        queue.add(stringRequest);
    }

    public void postJSONObjectRequest(String link, String url, final String header, JSONObject parameters, final CMResponse responseInterface) {
        final ProgressDialog progressDialog = new ProgressDialog(this.activity, R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Working, Please Wait...");
        progressDialog.setProgressStyle(0);
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(this.activity.getApplicationContext());
        final JSONObject jsonObject = parameters;
//        String hostURL = getHostURL(this.activity);
//        Map<String, String> body = new HashMap<>();
//        body.put("UserName", userName);
//        body.put("OldPassword", oldPassword);
//        body.put("NewPassword", newPassword);
//        final JSONObject finalJsonObject = jsonObject;
//        String link = Utils.getPreferences("link", this.activity);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://" + link + "/" + url, jsonObject, new Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                if (response != null) {
//                    usernameET.getText().clear();
//                    passwordET.getText().clear();
                    try {
                        responseInterface.consumeResponse(response.toString(), true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    calculateUserLoggedInfo( response.toString());
                } else {
//                    Toast();
                }
                progressDialog.dismiss();

            }

        }, new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                if (error instanceof TimeoutError) {

                    try {
                        responseInterface.consumeResponse("Connection Timeout", false);
//                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (error instanceof NoConnectionError) {
                    try {
                        responseInterface.consumeResponse("Internet Not Available", false);
//                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (error instanceof AuthFailureError) {
                    try {
                        responseInterface.consumeResponse("Enter a valid UserName and Password", false);
//                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (error instanceof ServerError) {
                    try {

                        NetworkResponse response = error.networkResponse;
                        if (response != null) {
                            String res = null;
                            try {
                                res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                responseInterface.consumeResponse(res, false);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        } else {
                            responseInterface.consumeResponse("Server cannot be found", false);
                        }
//                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (error instanceof NetworkError) {
                    try {
                        responseInterface.consumeResponse("Can not connect to the internet", false);
//                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (error instanceof ParseError) {
                    try {
                        responseInterface.consumeResponse("Data can not be Parsed", false);
//                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }) {


            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                String authorizationString = header; //this line is diffe
                headers.put("Authorization", authorizationString);
//                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                12000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        jsonObjectRequest.setRetryPolicy(new C05377());
        jsonObjectRequest.setShouldCache(false);
        queue.add(jsonObjectRequest);
    }

    public void getStringRequest(String link, String url, final String header, final CMResponse responseInterface) {
        final ProgressDialog progressDialog = new ProgressDialog(this.activity, R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Working, Please Wait...");
        progressDialog.setProgressStyle(0);
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(this.activity.getApplicationContext());
//        final JSONObject jsonObject = parameters;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://" + link + "/" + url, new Listener<String>() {
            public void onResponse(String response) {
                if (response != null) {
//                    usernameET.getText().clear();
//                    passwordET.getText().clear();
                    try {
                        responseInterface.consumeResponse(response.toString(), true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    calculateUserLoggedInfo( response.toString());
                } else {
//                    Toast();
                }
                progressDialog.dismiss();

            }

        }, new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                if (error instanceof TimeoutError) {

                    try {
                        responseInterface.consumeResponse("Connection Timeout", false);
//                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (error instanceof NoConnectionError) {
                    try {
                        if (error.equals("java.net.ConnectException: failed to connect to /203.124.51.67 (port 5526) after 2500ms: isConnected failed: EHOSTUNREACH (No route to host)")) {
                            responseInterface.consumeResponse("Wrong Ip Address or Port", false);
                        } else {
                            responseInterface.consumeResponse("Internet is not available", false);
                        }
//                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (error instanceof AuthFailureError) {
                    try {
                        responseInterface.consumeResponse("Enter a valid UserName and Password", false);
//                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (error instanceof ServerError) {
                    try {
                        responseInterface.consumeResponse("Internal Server Error", false);
//                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (error instanceof NetworkError) {
                    try {
                        responseInterface.consumeResponse("Internet is not available", false);
//                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (error instanceof ParseError) {
                    try {
                        responseInterface.consumeResponse("Data can not be Parsed", false);
//                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }) {

            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");

                String authorizationString = header; //this line is diffe
                headers.put("Authorization", authorizationString);
                return headers;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                12000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

//    private String getHostURL(Activity activity) {
//        String url = "";
//        try {
//            JSONArray rs = new DatabaseManager(activity).selectResultFromDB("select host_name , project_name from tbl_settings");
//            for (int j = 0; j < rs.length(); j++) {
//                JSONObject row = new JSONObject(rs.getString(j));
//                String hostname = row.getString("host_name");
//                url = "http://" + hostname + "/" + row.getString("project_name") + "/";
//            }
//            return url;
//        } catch (Exception ex) {
//            return ex.getLocalizedMessage();
//        }
//    }

//    public void postStringJSONObjectRequest(String link, String url, final String header, JSONObject parameters, final CMResponse responseInterface) {
//        final ProgressDialog progressDialog = new ProgressDialog(this.activity, R.style.MyAlertDialogStyle);
//        progressDialog.setMessage("Working, Please Wait...");
//        progressDialog.setProgressStyle(0);
//        progressDialog.show();
//        RequestQueue queue = Volley.newRequestQueue(this.activity.getApplicationContext());
//        final JSONObject jsonObject = parameters;
////        String hostURL = getHostURL(this.activity);
////        Map<String, String> body = new HashMap<>();
////        body.put("UserName", userName);
////        body.put("OldPassword", oldPassword);
////        body.put("NewPassword", newPassword);
////        final JSONObject finalJsonObject = jsonObject;
////        String link = Utils.getPreferences("link", this.activity);
//        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, "http://" + link + "/" + url,new Listener<String>() {
//            public void onResponse(String response) {
//                if (response != null) {
////                    usernameET.getText().clear();
////                    passwordET.getText().clear();
//                    try {
//                        responseInterface.consumeResponse(response.toString(), true);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
////                    calculateUserLoggedInfo( response.toString());
//                } else {
////                    Toast();
//                }
//                progressDialog.dismiss();
//
//            }
//
//        }, new ErrorListener() {
//            public void onErrorResponse(VolleyError error) {
//                progressDialog.hide();
//                if (error instanceof TimeoutError) {
//
//                    try {
//                        responseInterface.consumeResponse("Connection Timeout", false);
////                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                } else if (error instanceof NoConnectionError) {
//                    try {
//                        responseInterface.consumeResponse("Internet Not Available", false);
////                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                } else if (error instanceof AuthFailureError) {
//                    try {
//                        responseInterface.consumeResponse("Enter a valid UserName and Password", false);
////                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                } else if (error instanceof ServerError) {
//                    try {
//
//                        NetworkResponse response = error.networkResponse;
//                        if (response != null) {
//                            String res = null;
//                            try {
//                                res = new String(response.data,
//                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
//                                responseInterface.consumeResponse(res, false);
//                            } catch (UnsupportedEncodingException e) {
//                                e.printStackTrace();
//                            }
//                        } else {
//                            responseInterface.consumeResponse("Server cannot be found", false);
//                        }
////                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                } else if (error instanceof NetworkError) {
//                    try {
//                        responseInterface.consumeResponse("Can not connect to the internet", false);
////                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                } else if (error instanceof ParseError) {
//                    try {
//                        responseInterface.consumeResponse("Data can not be Parsed", false);
////                    Toast.makeText(CommunicationManager.this.activity, error.getMessage(), Toast.LENGTH_LONG).show();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }) {
//
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                String authorizationString = header; //this line is diffe
//                headers.put("Authorization", authorizationString);
////                headers.put("Content-Type", "application/json");
//                return headers;
//            }
//        };
//
////        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
////                7000,
////                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
////                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
////        jsonObjectRequest.setRetryPolicy(new C05377());
//        jsonObjectRequest.setShouldCache(false);
//        queue.add(jsonObjectRequest);
//    }
}
