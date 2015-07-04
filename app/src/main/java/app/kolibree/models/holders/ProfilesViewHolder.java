package app.kolibree.models.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import app.kolibree.R;

public class ProfilesViewHolder extends RecyclerView.ViewHolder
{
    public TextView profileFirstName;
    public TextView profileLastName;
    public TextView profileGender;
    public TextView profileHanded;
    public TextView profileBirthday;
    public TextView profileLastBrushing;
    public TextView profileAllBrushingTime;
    public TextView profileTotalVisite;

    public ProfilesViewHolder(View view)
    {
        super(view);

        profileFirstName = (TextView) view.findViewById(R.id.tv_firstName);
        profileLastName = (TextView) view.findViewById(R.id.tv_lastName);
        profileGender = (TextView) view.findViewById(R.id.tv_gender);
        profileHanded = (TextView) view.findViewById(R.id.tv_handed);
        profileBirthday = (TextView) view.findViewById(R.id.tv_birthday);
        profileLastBrushing = (TextView) view.findViewById(R.id.tv_lastBrushing);
        profileAllBrushingTime = (TextView) view.findViewById(R.id.tv_allBrushingTime);
        profileTotalVisite = (TextView) view.findViewById(R.id.tv_totalVisite);
    }
}
