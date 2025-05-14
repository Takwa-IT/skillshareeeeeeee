package com.example.skillshareeeeeeee.dto;

public class UserDto {
    private Integer id;
    private String email;
    private String username;
    private String filepath;
    private String password;

    // Constructeurs
    public UserDto() {
    }

    public UserDto(Integer id, String email, String username, String filepath, String password) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.filepath = filepath;
        this.password=password;
    }

    // Getters et Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getPassword() { return password;
    }
}