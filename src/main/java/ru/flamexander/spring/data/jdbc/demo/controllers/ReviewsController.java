package ru.flamexander.spring.data.jdbc.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.flamexander.spring.data.jdbc.demo.exceptions.BadArgumentException;
import ru.flamexander.spring.data.jdbc.demo.services.BooksService;

@RestController
@RequestMapping("/api/v1/books")
public class ReviewsController {

    private final BooksService booksService;

    @Autowired
    public ReviewsController(BooksService booksService) {
        this.booksService = booksService;
    }

    @PostMapping("/{id}/reviews")
    public void addReview(@PathVariable Long id,
                          @RequestParam String authorName,
                          @RequestParam String text,
                          @RequestParam int rating) {
        if (rating < 0 || rating > 10) {
            throw new BadArgumentException("Оценка должна находиться в диапазоне от 0 до 10");
        }

        booksService.addReview(id, authorName, text, rating);
    }
}
