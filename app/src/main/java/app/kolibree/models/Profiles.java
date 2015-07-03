package app.kolibree.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Lapinou on 30/06/2015.
 */
public class Profiles implements Parcelable {

    @SerializedName("picture") @Expose private String picture;
    @SerializedName("first_name") @Expose private String firstName;
    @SerializedName("last_name") @Expose private String lastName;
    @SerializedName("stats") @Expose private Stats stats;
    @SerializedName("is_owner_profile") @Expose private Boolean isOwnerProfile;
    @SerializedName("address_country") @Expose private String addressCountry;
    @SerializedName("gender") @Expose private String gender;
    @SerializedName("address_state") @Expose private String addressState;
    @SerializedName("address_city") @Expose private String addressCity;
    @SerializedName("survey_handedness") @Expose private String surveyHandedness;
    @SerializedName("birthday") @Expose private String birthday;
    @SerializedName("survey_toothbrush_type") @Expose private String surveyToothbrushType;
    @SerializedName("address") @Expose private String address;
    @SerializedName("account") @Expose private Integer account;
    @SerializedName("brushing_goal_time") @Expose private Double brushingGoalTime;
    @SerializedName("survey_brushing_time") @Expose private Double surveyBrushingTime;
    @SerializedName("address_zipcode") @Expose private String addressZipcode;
    @SerializedName("id") @Expose private Integer id;

    public Profiles(){

    }

    public Profiles(Parcel parcel)
    {
        this.picture = parcel.readString();
        this.firstName = parcel.readString();
        this.lastName = parcel.readString();
        this.stats = parcel.readParcelable(Stats.class.getClassLoader());
        this.isOwnerProfile = parcel.readByte() != 0;
        this.addressCountry = parcel.readString();
        this.gender = parcel.readString();
        this.addressState = parcel.readString();
        this.addressCity = parcel.readString();
        this.surveyHandedness = parcel.readString();
        this.birthday = parcel.readString();
        this.surveyToothbrushType = parcel.readString();
        this.address = parcel.readString();
        this.account = parcel.readInt();
        this.brushingGoalTime = parcel.readDouble();
        this.surveyBrushingTime = parcel.readDouble();
        this.addressZipcode = parcel.readString();
        this.id = parcel.readInt();
    }

    public String getAddressCountry() {
        return addressCountry;
    }

    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public Boolean getIsOwnerProfile() {
        return isOwnerProfile;
    }

    public void setIsOwnerProfile(Boolean isOwnerProfile) {
        this.isOwnerProfile = isOwnerProfile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddressState() {
        return addressState;
    }

    public void setAddressState(String addressState) {
        this.addressState = addressState;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getSurveyHandedness() {
        return surveyHandedness;
    }

    public void setSurveyHandedness(String surveyHandedness) {
        this.surveyHandedness = surveyHandedness;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSurveyToothbrushType() {
        return surveyToothbrushType;
    }

    public void setSurveyToothbrushType(String surveyToothbrushType) {
        this.surveyToothbrushType = surveyToothbrushType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public Double getBrushingGoalTime() {
        return brushingGoalTime;
    }

    public void setBrushingGoalTime(Double brushingGoalTime) {
        this.brushingGoalTime = brushingGoalTime;
    }

    public Double getSurveyBrushingTime() {
        return surveyBrushingTime;
    }

    public void setSurveyBrushingTime(Double surveyBrushingTime) {
        this.surveyBrushingTime = surveyBrushingTime;
    }

    public String getAddressZipcode() {
        return addressZipcode;
    }

    public void setAddressZipcode(String addressZipcode) {
        this.addressZipcode = addressZipcode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static final Creator<Profiles> CREATOR = new Creator<Profiles>()
    {
        @Override public Profiles createFromParcel(Parcel source)
        {
            return new Profiles(source);
        }

        @Override public Profiles[] newArray(int size)
        {
            return new Profiles[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.picture);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeParcelable(this.stats, flags);
        dest.writeByte((byte) (this.isOwnerProfile ? 1 : 0));
        dest.writeString(this.addressCountry);
        dest.writeString(this.gender);
        dest.writeString(this.addressState);
        dest.writeString(this.addressCity);
        dest.writeString(this.surveyHandedness);
        dest.writeString(this.birthday);
        dest.writeString(this.surveyToothbrushType);
        dest.writeString(this.address);
        dest.writeInt(this.account);
        dest.writeDouble(this.brushingGoalTime);
        dest.writeDouble(this.surveyBrushingTime);
        dest.writeString(this.addressZipcode);
        dest.writeInt(this.id);
    }
}
