package com.example.skillshareeeeeeee.dto;

public class UserDto {
    private Integer id;
    private String email;
    private String username;
    private byte[] image;
    private String password;
    public UserDto() {
    }

    public UserDto(Integer id, String email, String username, byte[] image, String password) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.image = image;
        this.password=password;
    }

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

    public byte[]  getImage() {
        return image;
    }

    public void setImage(byte[] image ) {
        this.image = image;
    }

    public String getPassword() { return password;
    }
}