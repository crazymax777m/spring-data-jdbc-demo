package ru.flamexander.spring.data.jdbc.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.flamexander.spring.data.jdbc.demo.dtos.DetailedBookDto;
import ru.flamexander.spring.data.jdbc.demo.dtos.DetailedBookWithAverageRatingDto;
import ru.flamexander.spring.data.jdbc.demo.dtos.PageDto;
import ru.flamexander.spring.data.jdbc.demo.dtos.SimplestPageDto;
import ru.flamexander.spring.data.jdbc.demo.services.BooksService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BooksController {
    private final BooksService booksService;

    @Autowired
    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }

    @GetMapping()
    public PageDto<DetailedBookDto> findAllDetailedBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<DetailedBookDto> books = booksService.findAllDetailedBooks(page, size);
        long totalBooks = booksService.countAllBooks();

        return new PageDto<>(books, page, size, calculateTotalPages(size, totalBooks), totalBooks);
    }

    @GetMapping("/top10")
    public SimplestPageDto<DetailedBookWithAverageRatingDto> findTop10BooksLastMonth() {
        List<DetailedBookWithAverageRatingDto> top10Books = booksService.findTop10BooksByRatingLastMonth();
        return new SimplestPageDto<>(top10Books);
    }

    @GetMapping("/{id}/details")
    public DetailedBookWithAverageRatingDto findBookDetailsWithAverageRating(@PathVariable Long id) {
        return booksService.findBookDetailsWithAverageRating(id);
    }

    private int calculateTotalPages(int pageSize, long totalElements) {
        return (int) Math.ceil((double) totalElements / pageSize);
    }

    @PatchMapping("/{id}/title")
    public void updateTitleById(@PathVariable Long id, @RequestParam String value) {
        booksService.updateTitleById(id, value);
    }
}
