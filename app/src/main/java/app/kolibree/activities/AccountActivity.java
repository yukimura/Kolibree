package app.kolibree.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.JsonObject;

import app.kolibree.R;
import app.kolibree.interfaces.KOLIBREEAPI;
import app.kolibree.models.Account;
import app.kolibree.rest.ApiRestServices;
import butterknife.ButterKnife;
import butterknife.InjectView;
import me.zhanghai.android.materialprogressbar.IndeterminateProgressDrawable;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class AccountActivity extends AppCompatActivity implements View.OnClickListener {

    @InjectView(R.id.et_email)
    EditText et_email;
    @InjectView(R.id.et_password)
    EditText et_password;
    @InjectView(R.id.bt_login)
    Button bt_login;
    @InjectView(R.id.indeterminate_progress_large_library)
    ProgressBar indeterminateProgressLarge;

    private String signature = null;
    private Account _account;

    @Override protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ButterKnife.inject(this);
        indeterminateProgressLarge.setIndeterminateDrawable(new IndeterminateProgressDrawable(this));
        bt_login.setOnClickListener(this);
    }

    private void getAppInfos()
    {
        KOLIBREEAPI service = ApiRestServices.create(KOLIBREEAPI.class).build(this, null, 0);

        JsonObject jsonObject = getCheckJsonObject();

        service.getAccount(jsonObject, new Callback<Account>() {

            @Override
            public void success(Account account, Response response) {
                _account = account;
                checkFinish();
            }

            @Override
            public void failure(RetrofitError error) {
                bt_login.setVisibility(View.VISIBLE);
                indeterminateProgressLarge.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Failed Login", Toast.LENGTH_SHORT).show();
                String json =  new String(((TypedByteArray)error.getResponse().getBody()).getBytes());
                Log.v("failure", json.toString());
            }
        });
    }

    private JsonObject getCheckJsonObject() {
        String email = et_email.getText().toString();
        String password = et_password.getText().toString();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor user = preferences.edit();
        user.putString("email", et_email.getText().toString());
        user.putString("password", et_password.getText().toString());
        user.commit();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("password", password);

        return jsonObject;
    }

    private void checkFinish()
    {
        Intent intent = new Intent(AccountActivity.this, MainActivity.class);
        intent.putExtra("account", _account);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                if (et_email.getText() != null
                        && !et_email.getText().equals("")
                        && et_password.getText() != null
                        && !et_password.getText().equals("")) {
                    bt_login.setVisibility(View.GONE);
                    indeterminateProgressLarge.setVisibility(View.VISIBLE);
                    getAppInfos();
                }
                break;
        }
    }
}
