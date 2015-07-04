package app.kolibree.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dk.view.folder.ResideMenu;
import com.getbase.floatingactionbutton.FloatingActionButton;

import app.kolibree.R;
import app.kolibree.activities.MainActivity;
import app.kolibree.adapters.ProfilesAdapter;
import app.kolibree.models.Account;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class ProfilesFragment extends Fragment implements View.OnClickListener {

    @InjectView(R.id.profilesRecyclerView)
    RecyclerView profilesRecyclerView;
    @InjectView(R.id.setter_drawable)
    FloatingActionButton setter_drawable;
    private ProfilesAdapter profilesAdapter;
    private ResideMenu resideMenu;

    private static int HANDLER_TIME_OUT = 600;

    private static Account account;

    public static ProfilesFragment newInstance(Account account) {
        ProfilesFragment fragment = new ProfilesFragment();
        Bundle args = new Bundle();
        args.putParcelable("account", account);
        fragment.setArguments(args);
        return fragment;
    }

    public ProfilesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profiles, container, false);

        setHasOptionsMenu(true);

        ButterKnife.inject(this, rootView);

        MainActivity parentActivity = (MainActivity) getActivity();
        resideMenu = parentActivity.getResideMenu();

        ActionBar actionbar = ((MainActivity) getActivity()).getSupportActionBar();
        actionbar.setTitle("Profiles");

        setter_drawable.setSize(FloatingActionButton.SIZE_MINI);
        setter_drawable.setColorNormalResId(R.color.yellow_kolibree);
        setter_drawable.setColorPressedResId(R.color.blue_kolibree);
        setter_drawable.setIcon(R.drawable.ic_add_white_36dp);
        setter_drawable.setStrokeVisible(false);
        setter_drawable.setOnClickListener(this);

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
        setupForm();
    }

    protected void setupForm() {
        profilesAdapter = new ProfilesAdapter(getActivity(), account.getProfiles());
        profilesRecyclerView.setHasFixedSize(true);
        profilesRecyclerView.setAdapter(profilesAdapter);
        profilesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        profilesRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setter_drawable:
                resideMenu.clearIgnoredViewList();
                if (resideMenu.isOpened()){
                    resideMenu.closeMenu();
                }

                new Handler() {
                }.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getFragmentManager().beginTransaction()
                                .replace(R.id.container, AddProfileFragment.newInstance(account))
                                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .commit();
                    }
                }, HANDLER_TIME_OUT);
                break;
        }
    }
}
