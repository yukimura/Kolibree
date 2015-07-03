package app.kolibree.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Lapinou on 30/06/2015.
 */
public class Account implements Parcelable {

    @SerializedName("email_verified") @Expose private Boolean emailVerified;
    @SerializedName("profiles") @Expose private ArrayList<Profiles> profiles = new ArrayList<>();
    @SerializedName("facebook_id") @Expose private String facebookId;
    @SerializedName("access_token") @Expose private String accessToken;
    @SerializedName("owner_profile_id") @Expose private Integer ownerProfileId;
    @SerializedName("token_expires") @Expose private String tokenExpires;
    @SerializedName("id") @Expose private Integer id;
    @SerializedName("refresh_token") @Expose private String refreshToken;
    @SerializedName("email") @Expose private String email;

    public Account(){

    }

    public Account(Parcel parcel)
    {
        this.emailVerified = parcel.readByte() != 0;
        this.profiles = parcel.readArrayList(Profiles.class.getClassLoader());
        this.facebookId = parcel.readString();
        this.accessToken = parcel.readString();
        this.ownerProfileId = parcel.readInt();
        this.tokenExpires = parcel.readString();
        this.id = parcel.readInt();
        this.refreshToken = parcel.readString();
        this.email = parcel.readString();
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public ArrayList<Profiles> getProfiles() {
        return profiles;
    }

    public void setProfiles(ArrayList<Profiles> profiles) {
        this.profiles = profiles;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Integer getOwnerProfileId() {
        return ownerProfileId;
    }

    public void setOwnerProfileId(Integer ownerProfileId) {
        this.ownerProfileId = ownerProfileId;
    }

    public String getTokenExpires() {
        return tokenExpires;
    }

    public void setTokenExpires(String tokenExpires) {
        this.tokenExpires = tokenExpires;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static final Creator<Account> CREATOR = new Creator<Account>()
    {
        @Override public Account createFromParcel(Parcel source)
        {
            return new Account(source);
        }

        @Override public Account[] newArray(int size)
        {
            return new Account[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (this.emailVerified ? 1 : 0));
        dest.writeList(this.profiles);
        dest.writeString(this.facebookId);
        dest.writeString(this.accessToken);
        dest.writeInt(this.ownerProfileId);
        dest.writeString(this.tokenExpires);
        dest.writeInt(this.id);
        dest.writeString(this.refreshToken);
        dest.writeString(this.email);
    }

    @Override
    public String toString() {
        return "Account{" +
                "emailVerified=" + emailVerified +
                ", profiles=" + profiles +
                ", facebookId='" + facebookId + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", ownerProfileId=" + ownerProfileId +
                ", tokenExpires='" + tokenExpires + '\'' +
                ", id=" + id +
                ", refreshToken='" + refreshToken + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
