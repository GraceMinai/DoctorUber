package com.twinternet.druber;

public class UserDetails
{
    private String myFullName;
    private String myEmail;
    private String myPhoneNumber;

    public UserDetails()
    {

    }

    public UserDetails(String myFullName, String myEmail, String myPhoneNumber)
    {
        this.myFullName = myFullName;
        this.myEmail = myEmail;
        this.myPhoneNumber = myPhoneNumber;
    }

    public String getMyFullName() {
        return myFullName;
    }

    public void setMyFullName(String myFullName) {
        this.myFullName = myFullName;
    }

    public String getMyEmail() {
        return myEmail;
    }

    public void setMyEmail(String myEmail) {
        this.myEmail = myEmail;
    }

    public String getMyPhoneNumber() {
        return myPhoneNumber;
    }

    public void setMyPhoneNumber(String myPhoneNumber) {
        this.myPhoneNumber = myPhoneNumber;
    }
}
