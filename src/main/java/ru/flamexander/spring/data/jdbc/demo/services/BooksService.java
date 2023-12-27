package ru.flamexander.spring.data.jdbc.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.flamexander.spring.data.jdbc.demo.dtos.DetailedBookDto;
import ru.flamexander.spring.data.jdbc.demo.dtos.DetailedBookWithAverageRatingDto;
import ru.flamexander.spring.data.jdbc.demo.repositories.BooksRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<DetailedBookDto> findAllDetailedBooks(int page, int size) {
        int offset = page * size;
        return booksRepository.findAllDetailedBooks(offset, size);
    }


    public DetailedBookWithAverageRatingDto findBookDetailsWithAverageRating(Long bookId) {
        return booksRepository.findBookDetailsWithAverageRating(bookId);
    }

    public List<DetailedBookWithAverageRatingDto> findTop10BooksByRatingLastMonth() {
        return booksRepository.findTop10BooksByRatingLastMonth();
    }

    public long countAllBooks() {
        return booksRepository.countAllBooks();
    }

    public void addReview(Long bookId, String authorName, String text, int rating) {
        LocalDate reviewDate = LocalDate.now();
        booksRepository.addReview(bookId, authorName, text, rating, reviewDate);
    }


    public void updateTitleById(Long id, String newTitle) {
        booksRepository.changeTitleById(id, newTitle);
    }
}
