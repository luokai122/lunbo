package toprs.cn.lunbo.Util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.Map;

import toprs.cn.lunbo.Final.App;

/**
 * 请求数据工具类
 */
public class VolleyUtil {
    private static RequestQueue requestQueue;

    private VolleyUtil() {

    }

    public static VolleyUtil getInstance() {
        return VolleyUtilHold.getVolleyUtilInstance();
    }

    public  void init() {
        requestQueue = Volley.newRequestQueue(App.getContext());
    }


    /**
     * post请求
     *
     * @param path   请求路径
     * @param map    请求参数
     * @param entity 需要解析的类
     * @return
     */
    public void StringRequest_POST(final Handler handler, String path, final Map<String, String> map, final Object entity) {
        StringRequest request = new StringRequest(StringRequest.Method.POST, path, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Object o = gson.fromJson(response, entity.getClass());
                Message msg = Message.obtain();
                msg.obj = o;
                handler.sendMessage(msg);
            }
        }, new MyErrorListener()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        request.setTag(path);
        requestQueue.add(request);
    }

    /**
     * get请求
     *
     * @param path   请求路径
     * @param entity 需要解析的类
     * @return
     */
    public void StringRequest_GET(final Handler handler, String path, final Object entity) {
        StringRequest request = new StringRequest(path, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Object o = gson.fromJson(response, entity.getClass());
                Message msg = Message.obtain();
                msg.obj = o;
                handler.sendMessage(msg);
            }
        }, new MyErrorListener());
        request.setTag(path);
        requestQueue.add(request);
    }

    private static final class VolleyUtilHold {
        public static VolleyUtil getVolleyUtilInstance() {
            return new VolleyUtil();
        }
    }

    /**
     * 执行请求错误
     */
    public static class MyErrorListener implements Response.ErrorListener {

        @Override
        public void onErrorResponse(VolleyError error) {
            //  LogUtils.showLog("VolleyError" + error.getMessage());
        }
    }

    /**
     * 需要手动解析字符串的post请求
     *
     * @param path
     * @param succes
     * @param error
     * @param map
     */
    public void simple_post(String path,
                            Response.Listener<String> succes, Response.ErrorListener error,
                            final Map<String, String> map) {

        StringRequest request_post = new StringRequest(Request.Method.POST,
                path, succes, error) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        request_post.setTag(path);
        System.out.println(request_post);
        requestQueue.add(request_post);

    }

    /**
     * 需要手动解析字符串的get请求
     *
     * @param path
     * @param succes
     * @param error
     */
    public void simple_get(String path, Response.Listener<String> succes, Response.ErrorListener error) {
        Log.e("VolleyUtil", "path-->"+path);
        StringRequest request = new StringRequest(path,succes,error);
        requestQueue.add(request);
    }

    /**
     * 取消请求
     *
     * @param path 请求路径
     */
    public void removeRequest(String path) {
        requestQueue.cancelAll(path);
    }


}
