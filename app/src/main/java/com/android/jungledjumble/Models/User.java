package com.android.jungledjumble.Models;

public class User {
    private String username;
    private String age;
    private String gender;
    private String profile_image;
    private String timestamp;
    private String choices;
    private String correct_choices;
    private String hand;
    private String glass;
    private String disorder;
    private String disability;
    private String fruit_lock;
    private String char_lock;
    private int points;


    public User(String username, String age, String gender, String hand, String glass,String disorder,String disability,
                String timestamp, String profile_image, String choices, String correct_choices, int points, String fruit_lock, String char_lock) {
        this.username = username;
        this.age = age;
        this.gender = gender;
        this.profile_image = profile_image;
        this.timestamp = timestamp;
        this.choices = choices;
        this.correct_choices = correct_choices;
        this.hand = hand;
        this.glass = glass;
        this.disorder = disorder;
        this.disability = disability;
        this.points = points;
        this.fruit_lock = fruit_lock;
        this.char_lock = char_lock;
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

    public String getChoices() {
        return choices;
    }

    public void setChoices(String choices) {
        this.choices = choices;
    }

    public String getCorrect_choices() {
        return correct_choices;
    }

    public void setCorrect_choices(String correct_choices) {
        this.correct_choices = correct_choices;
    }

    public String getHand() {
        return hand;
    }

    public void setHand(String hand) {
        this.hand = hand;
    }

    public String getGlass() {
        return glass;
    }

    public void setGlass(String glass) {
        this.glass = glass;
    }

    public String getDisorder() {
        return disorder;
    }

    public void setDisorder(String disorder) {
        this.disorder = disorder;
    }

    public String getDisability() {
        return disability;
    }

    public void setDisability(String disability) {
        this.disability = disability;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getFruit_lock() {
        return fruit_lock;
    }

    public void setFruit_lock(String fruit_lock) {
        this.fruit_lock = fruit_lock;
    }

    public String getChar_lock() {
        return char_lock;
    }

    public void setChar_lock(String char_lock) {
        this.char_lock = char_lock;
    }
    @Override
    public String toString() {
        return "User{" +
                "user_id='" + username + '\'' +
                ", email='" + age + '\'' +
                ", gender='" +gender + '\'' +
                ", timestamp='" +timestamp + '\'' +
                ", choices='" +choices + '\'' +
                ", correct choices='" +correct_choices + '\'' +
                '}';
    }


}
