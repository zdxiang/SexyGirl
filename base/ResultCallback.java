package cn.zdxiang.sexygirl.base;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * Created by jm on 16-11-30.
 *
 * @author JM
 * @version v1.0
 * @description resultCallback for OkHttp, for example. Look at the "@see"
 */
public abstract class ResultCallback<Object> extends Callback<Object> {
    private Type mType;

    public ResultCallback() {
        mType = getSuperclassTypeParameter(getClass());
    }

    private static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    @Override
    public Object parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        return new Gson().fromJson(string, mType);
    }
}