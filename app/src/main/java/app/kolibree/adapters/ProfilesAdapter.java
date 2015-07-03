package app.kolibree.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import app.kolibree.R;
import app.kolibree.models.Profiles;
import app.kolibree.utils.DateUtils;

/**
 * Created by Lapinou on 02/07/2015.
 */
public class ProfilesAdapter extends BaseAdapter{

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<Profiles> profiles = new ArrayList<>();

    public ProfilesAdapter(Context context, ArrayList<Profiles> profiles) {
        this.context = context;
        this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.profiles = profiles;
    }

    @Override
    public int getCount() {
        return profiles.size();
    }

    @Override
    public Profiles getItem(int position) {
        return this.profiles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class ProfilesViewHolder
    {
        public TextView tv_firstName;
        public TextView tv_lastName;
        public TextView tv_gender;
        public TextView tv_handed;
        public TextView tv_birthday;
        public TextView tv_lastBrushing;
        public TextView tv_allBrushingTime;
        public TextView tv_totalVisite;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ProfilesViewHolder view;

        if (convertView == null) {
            view = new ProfilesViewHolder();
            convertView = inflater.inflate(R.layout.lv_profiles, null);

            view.tv_firstName = (TextView) convertView.findViewById(R.id.tv_firstName);
            view.tv_lastName = (TextView) convertView.findViewById(R.id.tv_lastName);
            view.tv_gender = (TextView) convertView.findViewById(R.id.tv_gender);
            view.tv_handed = (TextView) convertView.findViewById(R.id.tv_handed);
            view.tv_birthday = (TextView) convertView.findViewById(R.id.tv_birthday);
            view.tv_lastBrushing = (TextView) convertView.findViewById(R.id.tv_lastBrushing);
            view.tv_allBrushingTime = (TextView) convertView.findViewById(R.id.tv_allBrushingTime);
            view.tv_totalVisite = (TextView) convertView.findViewById(R.id.tv_totalVisite);

            convertView.setTag(view);
        }
        else {
            view = (ProfilesViewHolder) convertView.getTag();
        }

        /* Picasso with url*/
        //Picasso.with(context).load("url").into(imageView);

        Resources res = context.getResources();

        view.tv_firstName.setText(res.getString(R.string.tv_firstName)+" "+profiles.get(position).getFirstName().toString());
        view.tv_lastName.setText(res.getString(R.string.tv_lastName)+" "+profiles.get(position).getLastName().toString());

        if(profiles.get(position).getGender().toString().equals("M")){
            view.tv_gender.setText(res.getString(R.string.tv_gender)+" "+res.getString(R.string.tv_genre_m));
        }else if(profiles.get(position).getGender().toString().equals("F")){
            view.tv_gender.setText(res.getString(R.string.tv_gender)+" "+res.getString(R.string.tv_genre_f));
        }
        if(profiles.get(position).getSurveyHandedness().toString().equals("R")){
            view.tv_handed.setText(res.getString(R.string.tv_handed)+" "+res.getString(R.string.tv_handed_d));
        }else if(profiles.get(position).getSurveyHandedness().toString().equals("L")){
            view.tv_handed.setText(res.getString(R.string.tv_handed)+" "+res.getString(R.string.tv_handed_g));
        }
        String strDate = getDate(profiles.get(position).getBirthday().toString());
        view.tv_birthday.setText(res.getString(R.string.tv_birthday)+" "+strDate);

        strDate = getDate(profiles.get(position).getStats().getLastBrushing().toString());
        view.tv_lastBrushing.setText(res.getString(R.string.tv_lastBrushing)+" "+strDate);

        view.tv_allBrushingTime.setText(res.getString(R.string.tv_allBrushingTime)+" "+profiles.get(position).getStats().getAllBrushingTime().toString());
        view.tv_totalVisite.setText(res.getString(R.string.tv_totalVisite)+" "+profiles.get(position).getStats().getTotalVisit().toString());


        return convertView;
    }

    public String getDate(String _date){
        Date date = null;
        try {
            date = DateUtils.CAPPTAIN_DATE_FORMAT.parse(_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String strDate = DateUtils.PROGRAM_DATE_FORMAT.format(date);
        return strDate;
    }
}
