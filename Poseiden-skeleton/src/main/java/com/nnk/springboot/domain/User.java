package com.nnk.springboot.domain;

import com.nnk.springboot.config.ValidPassword;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Data
@Validated
@Table(name = "users")
public class User implements Serializable, CrudEntity<User>  {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @NotBlank(message = "Username is mandatory")
    private String username;
    @NotBlank(message = "Password is mandatory")
    //@Min(value = 7, message = "Password should not be less than 8")
    @ValidPassword
    private String password;
    @NotBlank(message = "FullName is mandatory")
    private String fullname;
    @NotBlank(message = "Role is mandatory")
    private String role;

    public User(User entity) {
        this.username = entity.getUsername();
        this.password = entity.getPassword();
        this.fullname = entity.getFullname();
        this.role = entity.getRole();
    }

    public User() {

    }

    public User(String fullname, String username, String role, String password) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.role = role;
    }


    @Override
    public User update(User entity) {
        this.username = entity.getUsername();
        this.password = entity.getPassword();
        this.fullname = entity.getFullname();
        this.role = entity.getRole();
        return this;
    }

}
