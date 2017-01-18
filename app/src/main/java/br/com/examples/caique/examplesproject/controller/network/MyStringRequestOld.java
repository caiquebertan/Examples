//package br.com.examples.caique.examplesproject.controller.network;
//
//import android.content.Context;
//
//import com.android.volley.DefaultRetryPolicy;
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.Reader;
//import java.io.StringWriter;
//import java.io.Writer;
//
//import br.com.examples.caique.examplesproject.R;
//import br.com.examples.caique.examplesproject.model.generic.GenericResult;
//import de.greenrobot.event.EventBus;
//
//public class MyStringRequest {
//
//    private final EventBus eventBus;
//    private final GenericResult result;
//    private Context context;
//    private String tag;
//    private int timeout = 10000;
//
//    public MyStringRequest(Context context, String tag, GenericResult objectTypeToReturn) {
//        this.context = context;
//        this.tag = tag;
//        this.result = objectTypeToReturn;
//        eventBus = EventBus.getDefault();
//    }
//
//    public MyStringRequest setTimeout(int timeout) {
//        this.timeout = timeout;
//        return this;
//    }
//
//    public void makeRequest(String url) {
//        com.android.volley.toolbox.StringRequest stringRequest = new com.android.volley.toolbox.StringRequest(
//                Request.Method.GET,
//                url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        //TODO: As long as not calling web
//                        InputStream is = context.getResources().openRawResource(R.raw.list_items);
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
//                        //TODO:\\
//
//                        eventBus.post(result.parseResult(response));
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        eventBus.post(error);
//                    }
//                });
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        WebRequestQueue.getInstance(context).addToQueue(stringRequest, tag);
//    }
//}