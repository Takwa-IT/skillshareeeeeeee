package com.example.skillshareeeeeeee.dto;

public class CategoryDto {

    private Integer id;
    private String name;
    private String urlImg;

    // Constructeurs
    public CategoryDto() {
    }

    public CategoryDto(Integer id, String name, String urlImg) {
        this.id = id;
        this.name = name;
        this.urlImg = urlImg;
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrlImg() {
        return urlImg;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }
}
