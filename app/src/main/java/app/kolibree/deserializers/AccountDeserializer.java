package app.kolibree.deserializers;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import app.kolibree.models.Account;

public class AccountDeserializer implements JsonDeserializer<Account>
{
    @Override public Account deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        Account account = null;
        JsonObject obj = (JsonObject) json;

        account = new Gson().fromJson(obj.toString(), Account.class);

        return account;
    }
}
