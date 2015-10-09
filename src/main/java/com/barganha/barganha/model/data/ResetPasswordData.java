package com.barganha.barganha.model.data;

import com.google.common.base.MoreObjects;

public class ResetPasswordData {

    private String email;
    private String resetPasswordToken;
    private String password;
    private String confirmPassword;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("email", email)
                .add("resetPasswordToken", resetPasswordToken)
                .add("password", (password != null ? "NOT_EMPTY" : "EMPTY"))
                .add("confirmPassword", (confirmPassword != null ? "NOT_EMPTY" : "EMPTY"))
                .toString();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}
