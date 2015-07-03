package app.kolibree.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.dk.view.folder.ResideMenu;
import com.dk.view.folder.ResideMenuItem;

import app.kolibree.R;
import app.kolibree.fragments.AddProfileFragment;
import app.kolibree.fragments.ProfilesFragment;
import app.kolibree.models.Account;
import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, Toolbar.OnMenuItemClickListener {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    private static int HANDLER_TIME_OUT = 600;

    private ResideMenu resideMenu;
    private MainActivity mContext;
    private ResideMenuItem itemProfile;
    private ResideMenuItem itemAddProfile;
    private ResideMenuItem itemClose;

    private Fragment currentFragment;

    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setOnMenuItemClickListener(this);

        account = getIntent().getParcelableExtra("account");

        // attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.menu_background);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
        resideMenu.setScaleValue(0.6f);


        // create menu items;
        itemProfile = new ResideMenuItem(this, R.drawable.ic_home_yellow_36dp, "Profiles");
        itemAddProfile = new ResideMenuItem(this, R.drawable.ic_add_yellow_36dp, "Add Profile");
        itemClose = new ResideMenuItem(this, R.drawable.ic_highlight_off_yellow_24dp, "Close");

        itemProfile.setOnClickListener(this);
        itemAddProfile.setOnClickListener(this);
        itemClose.setOnClickListener(this);

        resideMenu.addMenuItem(itemProfile, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemAddProfile, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemClose, ResideMenu.DIRECTION_RIGHT);

        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_LEFT);

        if( savedInstanceState == null ){
            onCurrentFragment(0);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        menu.clear();
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View view) {
        if (view == itemProfile){
            onCurrentFragment(0);
        }else if (view == itemAddProfile) {
            onCurrentFragment(1);
        }else if (view == itemClose) {
            finish();
        }

        resideMenu.closeMenu();
    }

    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
            //Toast.makeText(mContext, "Menu is opened!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void closeMenu() {
            //Toast.makeText(mContext, "Menu is closed!", Toast.LENGTH_SHORT).show();
        }
    };

    private void onCurrentFragment(int position){
        currentFragment = null;

        switch (position) {
            case 0:
                currentFragment = ProfilesFragment.newInstance(account);
                break;
            case 1:
                currentFragment = AddProfileFragment.newInstance(account);
                break;
            default:
                throw new RuntimeException("Unexpected position.");
        }

        resideMenu.clearIgnoredViewList();
        if (resideMenu.isOpened()){
            resideMenu.closeMenu();
        }

        new Handler() {
        }.postDelayed(new Runnable() {
            @Override
            public void run() {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, currentFragment)
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        }, HANDLER_TIME_OUT);
    }

    // What good method is to access resideMenu?
    public ResideMenu getResideMenu(){
        return resideMenu;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu:
                resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
                return true;
        }
            return false;
    }
}
