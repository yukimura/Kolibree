package app.kolibree.rest;

import android.content.Context;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import app.kolibree.R;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by Lapinou on 03/07/2015.
 */
public class Common {

    private static String getSignature(Context context, int id){
        String secret = context.getString(R.string.secret);
        String message = null;
        if(id == 0){
            message = context.getString(R.string.message_account);
        }else{
            message = "https://test.api.kolibree.com/v1/accounts/"+id+"/profiles/";
        }

        String signature = null;
        try {
            Mac sha256_HMAC = Mac.getInstance(context.getString(R.string.sha256_HMAC));
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), context.getString(R.string.sha256_HMAC));
            sha256_HMAC.init(secret_key);

            signature = Base64.encodeToString(sha256_HMAC.doFinal(message.getBytes()), Base64.NO_WRAP);
            System.out.println(signature);
        }
        catch (Exception e){
            System.out.println("Error");
        }
        return signature;
    }

    public static RestAdapter getRestAdapter(final Context context, final String access_token, int id) {
        final String signature = getSignature(context, id);

        final OkHttpClient okHttpClient = new OkHttpClient();

        Gson gson = new GsonBuilder().create();

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(context.getString(R.string.base_url))
                .setClient(new OkClient(new OkHttpClient()))
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setConverter(new GsonConverter(gson))
                .setClient(new OkClient(okHttpClient))
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Content-Type", "application/json");
                        request.addHeader(context.getString(R.string.header_id), "5");
                        request.addHeader(context.getString(R.string.header_sig), signature);
                        if(access_token != null){
                            request.addHeader(context.getString(R.string.header_accessToken), access_token);
                        }
                    }
                })
                .build();

        return restAdapter;
    }
}
