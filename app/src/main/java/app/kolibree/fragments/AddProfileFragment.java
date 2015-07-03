package app.kolibree.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.text.ParseException;
import java.util.Date;

import app.kolibree.R;
import app.kolibree.activities.MainActivity;
import app.kolibree.interfaces.KOLIBREEAPI;
import app.kolibree.models.Account;
import app.kolibree.rest.Common;
import app.kolibree.utils.DateUtils;
import butterknife.ButterKnife;
import butterknife.InjectView;
import me.zhanghai.android.materialprogressbar.IndeterminateProgressDrawable;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class AddProfileFragment extends Fragment implements View.OnClickListener {

    @InjectView(R.id.et_firstName)
    EditText et_firstName;
    @InjectView(R.id.et_lastName)
    EditText et_lastName;
    @InjectView(R.id.sp_gender)
    BetterSpinner sp_gender;
    @InjectView(R.id.sp_handed)
    BetterSpinner sp_handed;
    @InjectView(R.id.bt_datePicker)
    Button bt_datePicker;
    @InjectView(R.id.bt_add_profile)
    Button bt_add_profile;
    @InjectView(R.id.indeterminate_progress_large_library)
    ProgressBar indeterminateProgressLarge;

    private static Account account;

    private String firstName = null;
    private String lastName = null;
    private String genre = null;
    private String handed = null;
    private String datePicker = null;

    public static AddProfileFragment newInstance(Account account) {
        AddProfileFragment fragment = new AddProfileFragment();
        Bundle args = new Bundle();
        args.putParcelable("account", account);
        fragment.setArguments(args);
        return fragment;
    }

    public AddProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_profile, container, false);

        setHasOptionsMenu(true);

        ButterKnife.inject(this, rootView);

        indeterminateProgressLarge.setIndeterminateDrawable(new IndeterminateProgressDrawable(getActivity()));

        bt_datePicker.setOnClickListener(this);
        bt_add_profile.setOnClickListener(this);

        ActionBar actionbar = ((MainActivity) getActivity()).getSupportActionBar();
        actionbar.setTitle("Add Profile");

        return rootView;
    }

    @Override public void onViewCreated(final View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        Bundle b = getArguments();
        if(b != null)
        {
            account = b.getParcelable("account");
        }
        setupForm(view);
    }

    protected void setupForm(View view) {
        String[] GENDER = getResources().getStringArray(R.array.gender);
        String[] HANDED = getResources().getStringArray(R.array.handed);

        ArrayAdapter<String> adapter_gender = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, GENDER);
        sp_gender.setAdapter(adapter_gender);
        ArrayAdapter<String> adapter_handed = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, HANDED);
        sp_handed.setAdapter(adapter_handed);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_datePicker:
                DialogFragment newFragment = new DialogDateFragment();
                newFragment.show(getFragmentManager(), "DatePicker");
                break;
            case R.id.bt_add_profile:
                bt_add_profile.setVisibility(View.GONE);
                indeterminateProgressLarge.setVisibility(View.VISIBLE);

                JsonObject jsonObject = getCheckJsonObject();

                RestAdapter restAdapter = Common.getRestAdapter(getActivity(), account.getAccessToken().toString(), account.getId());
                KOLIBREEAPI service = restAdapter.create(KOLIBREEAPI.class);

                service.createProfile(jsonObject, account.getId(), new Callback<Account>() {

                    @Override
                    public void success(Account account, Response response) {
                        checkFinish();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        bt_add_profile.setVisibility(View.VISIBLE);
                        indeterminateProgressLarge.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Failed validation", Toast.LENGTH_SHORT).show();
                        String json =  new String(((TypedByteArray)error.getResponse().getBody()).getBytes());
                        Log.v("failure", json.toString());
                    }
                });
                break;
        }
    }

    private void checkFinish()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String email = preferences.getString("email", null);
        String password = preferences.getString("password", null);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("password", password);

        RestAdapter restAdapter = Common.getRestAdapter(getActivity(), null, 0);
        KOLIBREEAPI service = restAdapter.create(KOLIBREEAPI.class);

        service.getAccount(jsonObject, new Callback<Account>() {

            @Override
            public void success(Account account, Response response) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("account", account);
                startActivity(intent);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity(), "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private JsonObject getCheckJsonObject() {
        if (et_firstName.getText() != null && !et_firstName.getText().toString().equals("")) {
            firstName = et_firstName.getText().toString();
        }
        if (et_lastName.getText() != null && !et_lastName.getText().toString().equals("")) {
            lastName = et_lastName.getText().toString();
        }
        if (sp_gender.getText() != null && !sp_gender.getText().toString().equals("")) {
            if(sp_gender.getText().toString().equals(getString(R.string.tv_genre_m))) {
                genre = "M";
            }else if(sp_gender.getText().toString().equals(getString(R.string.tv_genre_f))) {
                genre = "F";
            }
        }
        if (sp_handed.getText() != null && !sp_handed.getText().toString().equals("")) {
            if(sp_handed.getText().toString().equals(getString(R.string.tv_handed_d))) {
                handed = "R";
            }else if(sp_handed.getText().toString().equals(getString(R.string.tv_handed_g))) {
                handed = "L";
            }
        }
        if (bt_datePicker.getText() != null && !bt_datePicker.getText().toString().equals("")) {
            Date date = null;
            try {
                date = DateUtils.PROGRAM_DATE_FORMAT.parse(bt_datePicker.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String strDate = DateUtils.CAPPTAIN_DATE_FORMAT.format(date);
            datePicker = strDate;
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("first_name", firstName);
        jsonObject.addProperty("last_name", lastName);
        jsonObject.addProperty("gender", genre);
        jsonObject.addProperty("survey_handedness", handed);
        jsonObject.addProperty("birthday", datePicker);

        return jsonObject;
    }
}
