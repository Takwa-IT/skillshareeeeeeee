package com.example.skillshareeeeeeee.models;

public class ApiResponse <T>{
        private String status;
        private T data;

        // Constructeurs
        public ApiResponse(String status, T data) {
            this.status = status;
            this.data = data;
        }

        // Getters et Setters
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }


        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }
    }

