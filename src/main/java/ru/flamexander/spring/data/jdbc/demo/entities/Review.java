package ru.flamexander.spring.data.jdbc.demo.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("REVIEWS")
public class Review {
    @Id
    private Long id;
    private String authorName;
    private String text;
    private int rating;
    private LocalDate reviewDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    @PersistenceCreator
    public Review(Long id, String authorName, String text, int rating, LocalDate reviewDate) {
        this.id = id;
        this.authorName = authorName;
        this.text = text;
        this.rating = rating;
        this.reviewDate = reviewDate;
    }
}
