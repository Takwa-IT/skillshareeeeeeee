package com.example.skillshareeeeeeee.dto;

public class CategoryDto {

    private Integer id;
    private String name;
    private byte[] image; // Ajout du champ image

    // Constructeurs
    public CategoryDto() {
    }

    public CategoryDto(Integer id, String name, byte[] image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public byte[] getImage() { return image; }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(byte[] image) { this.image = image; }

}
