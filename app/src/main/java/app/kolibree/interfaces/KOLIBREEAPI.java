package app.kolibree.interfaces;

import com.google.gson.JsonObject;

import app.kolibree.models.Account;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Path;

public interface KOLIBREEAPI
{
    @POST("/v1/accounts/request_token/") public void getAccount(@Body JsonObject body, Callback<Account> callback);

    @POST("/v1/accounts/{account_id}/profiles/") public void createProfile(@Body JsonObject body, @Path("account_id") int groupId, Callback<Account> callback);
}