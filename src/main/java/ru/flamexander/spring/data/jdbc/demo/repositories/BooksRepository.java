package ru.flamexander.spring.data.jdbc.demo.repositories;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.flamexander.spring.data.jdbc.demo.dtos.DetailedBookDto;
import ru.flamexander.spring.data.jdbc.demo.dtos.DetailedBookWithAverageRatingDto;
import ru.flamexander.spring.data.jdbc.demo.entities.Book;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BooksRepository extends ListPagingAndSortingRepository<Book, Long> {

    @Query(
            "SELECT b.id, b.title, b.genre, a.full_name AS author_name, bd.description " +
                    "FROM BOOKS b " +
                    "LEFT JOIN AUTHORS a ON b.author_id = a.id " +
                    "LEFT JOIN BOOKS_DETAILS bd ON bd.book_id = b.id " +
                    "ORDER BY b.id LIMIT :limit OFFSET :offset"
    )
    List<DetailedBookDto> findAllDetailedBooks(@Param("offset") int offset, @Param("limit") int limit);

    @Query(
            "SELECT b.id, b.title, b.genre, a.full_name AS author_name, bd.description, " +
                    "ROUND(COALESCE(AVG(r.rating), 0), 2) AS average_rating " +
                    "FROM BOOKS b " +
                    "LEFT JOIN AUTHORS a ON b.author_id = a.id " +
                    "LEFT JOIN BOOKS_DETAILS bd ON bd.book_id = b.id " +
                    "LEFT JOIN REVIEWS r ON r.book_id = b.id " +
                    "WHERE b.id = :bookId " +
                    "GROUP BY b.id, a.full_name, bd.description"
    )
    DetailedBookWithAverageRatingDto findBookDetailsWithAverageRating(Long bookId);

    @Query(
            "SELECT b.id, b.title, b.genre, a.full_name AS author_name, bd.description, " +
                    "ROUND(COALESCE(AVG(r.rating), 0), 2) AS average_rating " +
                    "FROM BOOKS b " +
                    "LEFT JOIN AUTHORS a ON b.author_id = a.id " +
                    "LEFT JOIN BOOKS_DETAILS bd ON bd.book_id = b.id " +
                    "LEFT JOIN REVIEWS r ON r.book_id = b.id " +
                    "WHERE r.review_date >= CURRENT_DATE - INTERVAL '1' MONTH " +
                    "GROUP BY b.id, a.full_name, bd.description " +
                    "ORDER BY average_rating DESC " +
                    "LIMIT 10"
    )
    List<DetailedBookWithAverageRatingDto> findTop10BooksByRatingLastMonth();

    @Query("SELECT COUNT(*) FROM BOOKS")
    long countAllBooks();

    @Query("insert into reviews (book_id, author_name, text, rating, review_date) " +
            "values (:bookId, :authorName, :text, :rating, :reviewDate)")
    @Modifying
    void addReview(Long bookId, String authorName, String text, int rating, LocalDate reviewDate);


    @Query("update books set title = :title where id = :id")
    @Modifying
    void changeTitleById(Long id, String title);
}