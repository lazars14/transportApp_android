package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    @JsonProperty("_id")
    private String id;
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;
    @JsonProperty("firstname")
    private String firstName;
    @JsonProperty("lastname")
    private String lastName;
    @JsonProperty("address")
    private String address;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("firebaseToken")
    private String firebaseToken;

    public User(String id, String email, String password, String firstName, String lastName, String address, String phone, String firebaseToken) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.firebaseToken = firebaseToken;
    }

    public User(String email, String password, String firstName, String lastName, String address, String phone, String firebaseToken) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.firebaseToken = firebaseToken;
    }

    @JsonProperty("_id")
    public String getId() {
        return id;
    }

    @JsonProperty("_id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty("firstname")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("firstname")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonProperty("lastname")
    public String getLastName() {
        return lastName;
    }

    @JsonProperty("lastname")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonProperty("address")
    public String getAddress() {
        return address;
    }

    @JsonProperty("address")
    public void setAddress(String address) {
        this.address = address;
    }

    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    @JsonProperty("phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonProperty("firebaseToken")
    public String getFirebaseToken() {
        return firebaseToken;
    }

    @JsonProperty("firebaseToken")
    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }
}
