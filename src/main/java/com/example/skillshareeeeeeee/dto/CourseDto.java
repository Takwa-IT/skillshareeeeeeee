package com.example.skillshareeeeeeee.dto;

import java.util.List;

public class CourseDto {
        private Integer id;
        private String title;
        private String description;
        private Integer viewCounts;
        private Integer downloadCounts;
        private Integer ownerId;
        private List<Integer> lessonIds;
        private List<Integer> commentIds;
        private Integer categoryId;  // Ajouter l'ID de la catégorie

        // Constructeur par défaut
        public CourseDto() {}

        // Getters
        public Integer getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public Integer getViewCounts() {
            return viewCounts;
        }

        public Integer getDownloadCounts() {
            return downloadCounts;
        }

        public Integer getOwnerId() {
            return ownerId;
        }

        public List<Integer> getLessonIds() {
            return lessonIds;
        }

        public List<Integer> getCommentIds() {
            return commentIds;
        }

        public Integer getCategoryId() {  // Ajoute ce getter
            return categoryId;
        }

        // Setters
        public void setId(Integer id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setViewCounts(Integer viewCounts) {
            this.viewCounts = viewCounts;
        }

        public void setDownloadCounts(Integer downloadCounts) {
            this.downloadCounts = downloadCounts;
        }

        public void setOwnerId(Integer ownerId) {
            this.ownerId = ownerId;
        }

        public void setLessonIds(List<Integer> lessonIds) {
            this.lessonIds = lessonIds;
        }

        public void setCommentIds(List<Integer> commentIds) {
            this.commentIds = commentIds;
        }

        public void setCategoryId(Integer categoryId) {  // Ajoute ce setter
            this.categoryId = categoryId;
        }

        // Classe interne pour les stats (optionnelle)
        public static class StatsDto {
            private Integer totalLessons;
            private Integer totalComments;

            // Getters/Setters pour StatsDto
            public Integer getTotalLessons() {
                return totalLessons;
            }

            public void setTotalLessons(Integer totalLessons) {
                this.totalLessons = totalLessons;
            }

            public Integer getTotalComments() {
                return totalComments;
            }

            public void setTotalComments(Integer totalComments) {
                this.totalComments = totalComments;
            }
        }

}