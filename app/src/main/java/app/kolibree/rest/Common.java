package app.kolibree.rest;

import android.content.Context;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import app.kolibree.R;
import app.kolibree.deserializers.AccountDeserializer;
import app.kolibree.models.Account;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by Lapinou on 03/07/2015.
 */
public class Common<S> {

    private final Class<S> common;

    public Common(Class<S> common)
    {
        this.common = common;
    }

    public static <S> Common<S> create(Class<S> common)
    {
        return new Common<>(common);
    }

    public S build(final Context context, final String access_token, int id)
    {
        // Signature
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

        // Cache
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        File cacheDirectory = new File(context.getCacheDir().getAbsolutePath(), "HttpCache");
        Cache cache = null;
        cache = new Cache(cacheDirectory, cacheSize);

        // HttpClient
        OkHttpClient okHttpClient = new OkHttpClient();
        if (cache != null) {
            okHttpClient.setCache(cache);
        }
        okHttpClient.setConnectTimeout(0, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(0, TimeUnit.SECONDS);

        // Gson
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Account.class, new AccountDeserializer())
                .create();

        // RestAdapter
        final String finalSignature = signature;
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
                        request.addHeader(context.getString(R.string.header_sig), finalSignature);
                        if (access_token != null) {
                            request.addHeader(context.getString(R.string.header_accessToken), access_token);
                        }
                    }
                })
                .build();

        return restAdapter.create(common);
    }
}
