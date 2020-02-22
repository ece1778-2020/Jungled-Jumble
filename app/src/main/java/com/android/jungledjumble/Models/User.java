package com.android.jungledjumble.Models;

public class User {
    private String username;
    private String age;
    private String gender;
    private String profile_image;
    private String timestamp;

    public User(String username, String age, String gender, String timestamp, String profile_image) {
        this.username = username;
        this.age = age;
        this.gender = gender;
        this.profile_image = profile_image;
        this.timestamp = timestamp;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age= age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id='" + username + '\'' +
                ", email='" + age + '\'' +
                ", username='" +gender + '\'' +
                ", username='" +timestamp + '\'' +
                '}';
    }


}
