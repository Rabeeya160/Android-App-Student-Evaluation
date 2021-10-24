package com.jyaconsulting.aashal1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;

public class Utils {
    public static String server = "http://192.168.100.10/doctor_app/api/";
    public static String imageUrl = "http://192.168.100.10/doctor_app/uploads/images/";
    public static String get_doctor_categories = server + "get_doctor_categories.php";
    public static String register_doctor = server + "register_doctor.php";
    public static String register_patient = server + "register_patient.php";
    public static String login_doctor = server + "login_doctor.php";
    public static String login_patient = server + "login_patient.php";
    public static String logout = server + "logout.php";
    public static String otp_send = server + "otp_send.php";
    public static String forgot_password = server + "forgot-password.php";
    public static String update_profile_details = server + "update_profile_details.php";
    public static String update_user_profile = server + "update_user_profile.php";
    public static String update_doctor_documents = server + "update_doctor_documents.php";
    public static String get_doctor_documents = server + "get_doctor_documents.php";
    public static String delete_doctor_documents = server + "delete_doctor_documents.php";
    public static String get_doctors_list = server + "get_doctors_list.php";
    public static String get_doctors_search = server + "get_doctors_search.php";
    public static String send_time_slots_against_day = server + "send_time_slots_against_day.php";
    public static String send_time_slots = server + "send_time_slots.php";
    public static String get_doctors_time_slots_against_day = server + "get_doctors_time_slots_against_day.php";
    public static String update_schedule = server + "update_schedule.php";
    public static String get_patient_appointments = server + "get_patient_appointments.php";
    public static String get_doctor_appointments = server + "get_doctor_appointments.php";
    public static String get_doctors_list_all = server + "get_doctors_list_all.php";
    public static String get_doctors_list_location = server + "get_doctors_list_location.php";
    public static String new_booking = server + "new_booking.php";
    public static String get_doctors_list_location_category_search = server + "get_doctors_list_location_category_search.php";
    public static String delete_doctor_request = server + "delete_doctor_request.php";
    public static String delete_patient_appointment = server + "delete_patient_appointment.php";
    public static String inactive_doctor_time_slot = server + "inactive_doctor_time_slot.php";
    public static String active_doctor_time_slot = server + "active_doctor_time_slot.php";
    public static String get_day_slots = server + "get_day_slots.php";
    public static String rating_doctor = server + "rating_doctor.php";
    public static String load_profile = server + "load_profile.php";
//    public static String imageUrl = " http://dev.boxtribetracker.com/1118/new/final/vanmove/uploads/images/";

    public static int DISTANCE_TO_ROTATE = 20;

//    public static ArrayList<Categories> GetCategoriesList = new ArrayList<Categories>();

    public static String getPreferences(String key, Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String userName = sharedPreferences.getString(key, "");
        return userName;
    }

    public static int getPreferencesInt(String key, Context context) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        int int_value = sharedPreferences.getInt(key, 0);
        return int_value;
    }

    public static boolean savePreferences(String key, String value,
                                          Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
        return true;
    }

    public static boolean savePreferencesInt(String key, int value,
                                             Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
        return true;
    }


    public static byte[] getImageBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }


    public static void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
        }
    }

//	private void postJSonbyCreatingJsonObject() {
//		try {
//			RequestQueue requestQueue = Volley.newRequestQueue();
//
//			String URL = CONSTANTS.baseUrl + "register_driver2.php";
//			JSONObject postParam = new JSONObject();
//
//			postParam.put("device", "android");
//			postParam.put("vehicle_license_number", "111111");
//			postParam.put("vehicle_registration_number", "433");
//			postParam.put("vehicle_maker_model", "4343");
//			postParam.put("vehicle_year", "443");
//			postParam.put("vehicle_type", "dfdsfsd");
//			postParam.put("vehicle_air_conditioner", "sdfsdf");
//			postParam.put("voice_booking", "dfdsfs");
//			postParam.put("payment_card", "dfdsf");
//
//			final String requestBody = postParam.toString();
//
//
//
//
//
//			StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
//				@Override
//				public void onResponse(String response) {
//					Log.i("VOLLEY", response);
//					Toast.makeText(context, "message", Toast.LENGTH_SHORT).show();
//				}
//			}, new Response.ErrorListener() {
//				@Override
//				public void onErrorResponse(VolleyError error) {
//					Log.e("VOLLEY", error.toString());
//					Toast.makeText(context, "message", Toast.LENGTH_SHORT).show();
//				}
//			}) {
//
//
//				@Override
//				public Map<String, String> getHeaders ()throws AuthFailureError {
//					HashMap<String, String> headers = new HashMap<String, String>();
//					headers.put("Content-Type", "application/json");
//					headers.put("registration_id", "cBTXx4-Q3n0:APA91bHs81tH5Lz6IdmI8_vE7c43nRObz20lTtWaWL0mAtOf9qnIUvafjd2qMQvpvCPxSQifhv1AK-EXD_a9S2ypaz5XkQbka2YjkQRcDY21kXvPGFpz6M1scGnD4d2_gYiXw3vXATmq");
//					headers.put("driver_id", "1");
//					return headers;
//				}
//
//				@Override
//				public byte[] getBody() throws AuthFailureError {
//					try {
//						return requestBody == null ? null : requestBody.getBytes("utf-8");
//					} catch (UnsupportedEncodingException uee) {
//						VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
//						return null;
//					}
//				}};
//
//
//
//
//
//			requestQueue.add(stringRequest);
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//	}

    public static boolean WifiEnable(Context context) {
        boolean mWifiEnable = false; // Assume disabled
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mWifi.isConnected()) {
            mWifiEnable = true;
            return true;
        }
        return false;
    }

    public static boolean isMobileDataEnable(Context context) {
        boolean mobileDataEnabled = false; // Assume disabled
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            Class cmClass = Class.forName(cm.getClass().getName());
            Method method = cmClass.getDeclaredMethod("getMobileDataEnabled");
            method.setAccessible(true); // Make the method callable
            // get the setting for "mobile data"
            mobileDataEnabled = (Boolean) method.invoke(cm);
        } catch (Exception e) {
            // Some problem accessible private API and do whatever error
            // handling you want here
        }
        return mobileDataEnabled;
    }

    public static void showToast(Context context, String txt) {
        Toast.makeText(context, "" + txt, Toast.LENGTH_SHORT).show();

    }

    public static void showToastTest(Context context, String txt) {

//        Toast.makeText(context, "" + txt, Toast.LENGTH_SHORT).show();

    }

    public static void showCustomDialog(Context context, String message) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);

        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
