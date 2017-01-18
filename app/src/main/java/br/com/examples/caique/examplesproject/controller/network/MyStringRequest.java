package br.com.examples.caique.examplesproject.controller.network;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import br.com.examples.caique.examplesproject.controller.GsonHelper;
import org.greenrobot.eventbus.EventBus;

public class MyStringRequest<C> {

    private final EventBus eventBus;
    private boolean isList;
    private Class<C> classResult;
    private Context context;
    private String tag;
    private int timeout = 10000;

    public MyStringRequest(Context context, String tag, Class<C> classResult) {
        this.context = context;
        this.tag = tag;
        this.classResult = classResult;
        this.isList = false;
        eventBus = EventBus.getDefault();
    }

    public MyStringRequest(Context context, String tag, Class<C> classResult, boolean isList) {
        this.context = context;
        this.tag = tag;
        this.classResult = classResult;
        this.isList = isList;
        eventBus = EventBus.getDefault();
    }

    public MyStringRequest setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

//    public void getFake(final int res) {
//        com.android.volley.toolbox.StringRequest stringRequest = new com.android.volley.toolbox.StringRequest(
//                Request.Method.GET,
//                Constants.URL_2S,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        InputStream is = context.getResources().openRawResource(res);
//                        Writer writer = new StringWriter();
//                        char[] buffer = new char[1024];
//                        try {
//                            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
//                            int n;
//                            while ((n = reader.read(buffer)) != -1) {
//                                writer.write(buffer, 0, n);
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        } finally {
//                            try {
//                                is.close();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        response = writer.toString();
//
//                        postEvent(response);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        eventBus.post(error);
//                    }
//                });
//
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        WebRequestQueue.getInstance(context).addToQueue(stringRequest, tag);
//    }

    public void get(String url) {
        get(url, null);
    }

    public void get(String url, final HashMap<String, String> params) {
        com.android.volley.toolbox.StringRequest stringRequest = new com.android.volley.toolbox.StringRequest(
                Request.Method.GET,
                url+getParams(params),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        postEvent(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        eventBus.post(error);
                    }
                });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        WebRequestQueue.getInstance(context).addToQueue(stringRequest, tag);
    }

    public void post(String url){
        post(url, null);
    }

    public void post(String url, final HashMap<String, String> params) {
        com.android.volley.toolbox.StringRequest stringRequest = new com.android.volley.toolbox.StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        postEvent(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        eventBus.post(error);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params != null ? params : super.getParams();
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        WebRequestQueue.getInstance(context).addToQueue(stringRequest, tag);
    }

    private void postEvent(String response) {
        if(classResult != null) {
            Gson gson = new GsonHelper().getGson();
            if(isList) {
                List<C> ret = gson.fromJson(response, new ListOfJson<>(classResult));
                eventBus.post(ret);
            }
            else {
                C ret = gson.fromJson(response, classResult);
                eventBus.post(ret);
            }
        }
    }

    private String getParams(HashMap<String, String> params) {
        if(params == null)
            return "";

        try {
            String query = "?";
            Iterator<String> iterator = params.keySet().iterator();

            while (iterator.hasNext()) {
                String key = iterator.next();
                String parameter = params.get(key);

                String value = key + "=" + URLEncoder.encode(parameter, "UTF-8");

                if (iterator.hasNext()) {
                    value += "&";
                }

                query += value;
            }

            return query;
        } catch (Exception e) {
            return "";
        }
    }

    private boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

}