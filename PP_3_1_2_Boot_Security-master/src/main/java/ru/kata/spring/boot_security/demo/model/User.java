package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;

/**
 * @author Karina Bashkatova.
 */
@Entity
@Table(name = "user")
public class User {

    public User() {
    }
    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;



    @Column(name="username")
    @NotEmpty(message =  "Обязательное поле")
    private String userName;

    @ManyToMany(fetch = EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Column(name = "age")
    @Min(value = 0, message = "Недопустимое значение")
    private int age;

    @Column
    @NotEmpty(message =  "Обязательное поле")
    @Email
    private String email;

    @Column(name="password")
    @NotEmpty(message =  "Обязательное поле")
    private String password;


    public User(int id, String userName, int age, String email, String password) {
        this.id = id;
        this.userName = userName;
        this.age = age;
        this.email = email;
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }


    public void setUsername(String userName) {
        this.userName = userName;
    }

    public String getUsername() {
        return userName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail () {
        return  email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}