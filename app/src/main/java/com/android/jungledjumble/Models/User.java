package com.android.jungledjumble.Models;

public class User {


    private String username;
    private String age;
    private String gender;
    private String profile_image;

    public User(String user_name, String age, String gender, String profile_image) {
        this.username = username;
        this.age = age;
        this.gender = gender;
        this.profile_image = profile_image;
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
                '}';
    }


}
