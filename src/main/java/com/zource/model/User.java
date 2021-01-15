package com.zource.model;

import antlr.StringUtils;
import com.fasterxml.jackson.annotation.JsonView;
import com.zource.model.jsonViews.View;
import lombok.Data;
import org.apache.commons.lang.WordUtils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@DynamicUpdate(true)
@Table(name="user", uniqueConstraints = {@UniqueConstraint(columnNames = {"username", "email"})})
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(value = { View.UserView.NoPassword.class })
    private Long id;

    @Column(name = "first_name")
    @NotNull(message = "Name may not be null")
    @NotEmpty(message = "Name may not be empty")
    @JsonView(value = { View.UserView.NoPassword.class })
    private String firstName;

    @Column(name = "last_name")
    @NotNull(message = "Last name may not be null")
    @NotEmpty(message = "Last name may not be empty")
    @JsonView(value = { View.UserView.NoPassword.class })
    private String lastName;


    @Column(name="username", unique = true)
    @NotNull(message = "Username may not be null")
    @NotEmpty(message = "Username may not be empty")
    @JsonView(value = { View.UserView.NoPassword.class })
    private String username;

    @Column(name="email", unique = true)
    @NotEmpty(message = "Email may not be empty")
    @Email
    @JsonView(value = { View.UserView.NoPassword.class })
    private String email;

    @PrePersist
    @PreUpdate
    private void prepareData(){
        this.email = email == null ? null : email.toLowerCase();
        this.username = username == null ? null : username.toLowerCase();
        this.firstName = firstName == null ? null : WordUtils.capitalize(firstName.toLowerCase());
        this.lastName = lastName == null ? null : WordUtils.capitalize(lastName.toLowerCase());
    }

    @Column(name="tel")
    @JsonView(value = { View.UserView.NoPassword.class })
    private String tel;


    @Column(name="password")
   /* @NotNull(message = "Password may not be null")
    @NotEmpty(message = "Password may not be empty")*/
    @JsonView(value = { View.UserView.Full.class })
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name="role")
    @JsonView(value = { View.UserView.NoPassword.class })
    private Role role;

    @Column(name="creation_timestamp", updatable = false)
    @CreationTimestamp
    @JsonView(value = { View.UserView.NoPassword.class })
    private LocalDateTime createDateTime;

    @Column(name="update_timestamp")
    @UpdateTimestamp
    @JsonView(value = { View.UserView.NoPassword.class })
    private LocalDateTime updateDateTime;


    @Column(name="verified", columnDefinition = "bit default 0", nullable = false)
    @JsonView(value = { View.UserView.NoPassword.class })
    private boolean verified;


    @Transient
    private String token;
}
