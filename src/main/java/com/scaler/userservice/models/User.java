package com.scaler.userservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "Users")
public class User extends BaseModel{
    private String name;
    private String email;
    private String hashedPassword;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

}
