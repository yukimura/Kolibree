package app.kolibree.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import app.kolibree.R;
import app.kolibree.models.Profiles;
import app.kolibree.models.holders.ProfilesViewHolder;
import app.kolibree.utils.DateUtils;

/**
 * Created by Lapinou on 02/07/2015.
 */
public class ProfilesAdapter extends RecyclerView.Adapter<ProfilesViewHolder> {

    private Context context;
    private ArrayList<Profiles> profiles = new ArrayList<>();
    private Resources res;

    public ProfilesAdapter(Context context, ArrayList<Profiles> profiles) {
        this.context = context;
        this.profiles = profiles;
        this.res = context.getResources();
    }

    @Override public ProfilesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.lv_profiles, parent, false);
        ProfilesViewHolder holder = new ProfilesViewHolder(view);
        return holder;
    }

    @Override public void onBindViewHolder(ProfilesViewHolder holder, int position) {
        Profiles item = profiles.get(position);

        /* Picasso */
        // Picasso.with(holder.image.getContext()).load(item.getPicture()).into(holder.image);

        holder.profileFirstName.setText(res.getString(R.string.tv_firstName) + " " + String.valueOf(item.getFirstName()));
        holder.profileLastName.setText(res.getString(R.string.tv_lastName) + " " + String.valueOf(item.getLastName()));

        holder.profileGender.setVisibility(View.VISIBLE);
        if(String.valueOf(item.getGender()).equals("M")){
            holder.profileGender.setText(res.getString(R.string.tv_gender)+" "+res.getString(R.string.tv_genre_m));
        }else if(String.valueOf(item.getGender()).equals("F")){
            holder.profileGender.setText(res.getString(R.string.tv_gender)+" "+res.getString(R.string.tv_genre_f));
        }else{
            holder.profileGender.setVisibility(View.GONE);
        }

        holder.profileHanded.setVisibility(View.VISIBLE);
        if(String.valueOf(item.getSurveyHandedness()).equals("R")){
            holder.profileHanded.setText(res.getString(R.string.tv_handed) + " " + res.getString(R.string.tv_handed_d));
        }else if(String.valueOf(item.getGender()).equals("L")){
            holder.profileHanded.setText(res.getString(R.string.tv_handed)+" "+res.getString(R.string.tv_handed_g));
        }else{
            holder.profileHanded.setVisibility(View.GONE);
        }

        String strDate = getDate(item.getBirthday().toString());
        holder.profileBirthday.setText(res.getString(R.string.tv_birthday)+" "+strDate);

        strDate = getDate(item.getStats().getLastBrushing().toString());
        holder.profileLastBrushing.setText(res.getString(R.string.tv_lastBrushing)+" "+strDate);


        holder.profileAllBrushingTime.setText(res.getString(R.string.tv_allBrushingTime) + " " + String.valueOf(item.getStats().getAllBrushingTime()));
        holder.profileTotalVisite.setText(res.getString(R.string.tv_totalVisite) + " " + String.valueOf(item.getStats().getTotalVisit()));
    }

    @Override public int getItemCount() {
        return profiles.size();
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
