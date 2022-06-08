package com.twinternet.druber;

public class UserDetails
{
     String userFullName;
     String userEmail;
     String userPhoneNumber;
     String userPassword;


     public UserDetails() {
     }

     public UserDetails(String userFullName, String userEmail, String userPhoneNumber, String userPassword) {
          this.userFullName = userFullName;
          this.userEmail = userEmail;
          this.userPhoneNumber = userPhoneNumber;
          this.userPassword = userPassword;
     }

     public String getUserFullName() {
          return userFullName;
     }

     public void setUserFullName(String userFullName) {
          this.userFullName = userFullName;
     }

     public String getUserEmail() {
          return userEmail;
     }

     public void setUserEmail(String userEmail) {
          this.userEmail = userEmail;
     }

     public String getUserPhoneNumber() {
          return userPhoneNumber;
     }

     public void setUserPhoneNumber(String userPhoneNumber) {
          this.userPhoneNumber = userPhoneNumber;
     }

     public String getUserPassword() {
          return userPassword;
     }

     public void setUserPassword(String userPassword) {
          this.userPassword = userPassword;
     }
}
