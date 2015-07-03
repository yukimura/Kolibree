package app.kolibree.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Lapinou on 30/06/2015.
 */
public class Stats implements Parcelable {

    @SerializedName("profile") @Expose private Integer profile;
    @SerializedName("all_brushing_time") @Expose private Double allBrushingTime;
    @SerializedName("coins") @Expose private Integer coins;
    @SerializedName("last_brushing") @Expose private String lastBrushing;
    @SerializedName("regular_brushing_day") @Expose private Integer regularBrushingDay;
    @SerializedName("total_visit") @Expose private Integer totalVisit;

    public Stats(){

    }

    public Stats(Parcel parcel)
    {
        this.profile = parcel.readInt();
        this.allBrushingTime = parcel.readDouble();
        this.coins = parcel.readInt();
        this.lastBrushing = parcel.readString();
        this.regularBrushingDay = parcel.readInt();
        this.totalVisit = parcel.readInt();
    }

    public Integer getProfile() {
        return profile;
    }

    public void setProfile(Integer profile) {
        this.profile = profile;
    }

    public Double getAllBrushingTime() {
        return allBrushingTime;
    }

    public void setAllBrushingTime(Double allBrushingTime) {
        this.allBrushingTime = allBrushingTime;
    }

    public Integer getCoins() {
        return coins;
    }

    public void setCoins(Integer coins) {
        this.coins = coins;
    }

    public String getLastBrushing() {
        return lastBrushing;
    }

    public void setLastBrushing(String lastBrushing) {
        this.lastBrushing = lastBrushing;
    }

    public Integer getRegularBrushingDay() {
        return regularBrushingDay;
    }

    public void setRegularBrushingDay(Integer regularBrushingDay) {
        this.regularBrushingDay = regularBrushingDay;
    }

    public Integer getTotalVisit() {
        return totalVisit;
    }

    public void setTotalVisit(Integer totalVisit) {
        this.totalVisit = totalVisit;
    }


    public static final Creator<Stats> CREATOR = new Creator<Stats>()
    {
        @Override public Stats createFromParcel(Parcel source)
        {
            return new Stats(source);
        }

        @Override public Stats[] newArray(int size)
        {
            return new Stats[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.profile);
        dest.writeDouble(this.allBrushingTime);
        dest.writeInt(this.coins);
        dest.writeString(this.lastBrushing);
        dest.writeInt(this.regularBrushingDay);
        dest.writeInt(this.totalVisit);
    }
}
