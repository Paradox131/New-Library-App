package business;

import java.io.Serializable;
import java.util.Objects;


public class User implements Serializable {
    private int userId;
    private String userName;
    private String password;
    private String email;
    private String phoneNumber;
    private int userType;
    private int disable;

    /**
     * @param userId,      int userId
     * @param userName,    string userName
     * @param password,    string password
     * @param email,       string email
     * @param phoneNumber, string phoneNumber
     * @param userType,    int userType
     * @param disable,     int disable
     **/
    public User(int userId, String userName, String password, String email, String phoneNumber, int userType, int disable) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userType = userType;
        this.disable = disable;
    }

    /*public User() {
        this.userId = 0;
        this.userName = null;
        this.password =null;
        this.email = null;
        this.phoneNumber =null;
        this.userType=0;
    }*/

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getDisable() {
        return disable;
    }

    public void setDisable(int disable) {
        this.disable = disable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId && userType == user.userType && disable == user.disable && Objects.equals(userName, user.userName) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && Objects.equals(phoneNumber, user.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, password, email, phoneNumber, userType, disable);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", userType=" + userType +
                ", disable=" + disable +
                '}';
    }
}
