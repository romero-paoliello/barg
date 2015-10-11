package com.barganha.model;

import com.barganha.model.data.RegistrationData;
import com.google.common.base.MoreObjects;
import org.joda.time.DateTime;

import javax.persistence.*;

@Entity(name = "CUSTOMER")
public class User {

    @Id
    @SequenceGenerator(name = "USER_ID_GENERATOR", sequenceName = "SQ_USER_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ID_GENERATOR")
    private Long id;

    private String email;

    private String password;

    private String name;

    @Column(name = "RESET_PASSWORD_TOKEN")
    private String resetPasswordToken;

    @Column(name = "CREATED_ON")
    private DateTime createdOn;

    @Column(name = "LAST_UPDATED_ON")
    private DateTime lastUpdatedOn;

    public User() {
    }

    public User(RegistrationData rg) {
        this.email = rg.getEmail();
        this.name = rg.getName();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("email", email)
                .add("password", (password != null ? "NOT_EMPTY" : "EMPTY"))
                .add("name", name)
                .add("resetPasswordToken", (resetPasswordToken != null ? "NOT_EMPTY" : "EMPTY"))
                .add("createdOn", createdOn)
                .add("lastUpdatedOn", lastUpdatedOn)
                .toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public DateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(DateTime createdOn) {
        this.createdOn = createdOn;
    }

    public DateTime getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(DateTime lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }


}
