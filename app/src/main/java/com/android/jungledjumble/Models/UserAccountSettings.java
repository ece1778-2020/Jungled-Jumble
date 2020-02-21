package com.android.jungledjumble.Models;

public class UserAccountSettings {

    private String bio;
    private String display_name;
    private String profile_photo;
    private String username;

    public UserAccountSettings(String bio, String display_name,String profile_photo, String username) {
        this.bio = bio;
        this.display_name = display_name;
        this.profile_photo = profile_photo;
        this.username = username;
    }
    public UserAccountSettings() {

    }

    public String getBio() {
        return bio;
    }

    public void setDscription(String description) {
        this.bio = description;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }


    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    @Override
    public String toString() {
        return "UserAccountSettings{" +
                "bio='" + bio + '\'' +
                ", display_name='" + display_name + '\'' +
                ", profile_photo='" + profile_photo + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
