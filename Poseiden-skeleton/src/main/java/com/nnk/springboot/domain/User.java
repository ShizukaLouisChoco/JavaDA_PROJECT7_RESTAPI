package com.nnk.springboot.domain;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
//@NoArgsConstructor
//@AllArgsConstructor
@Validated
@Table(name = "users")
public class User implements Serializable, CrudEntity<User>  {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    //@NotBlank(message = "Username is mandatory")
    private String username;
    //@NotBlank(message = "Password is mandatory")
    private String password;
    //@NotBlank(message = "FullName is mandatory")
    private String fullname;
    //@NotBlank(message = "Role is mandatory")
    private String role;

    public User(User entity) {
        this.username = entity.getUsername();
        this.password = entity.getPassword();
        this.fullname = entity.getFullname();
        this.role = entity.getRole();
    }

    public User() {

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
