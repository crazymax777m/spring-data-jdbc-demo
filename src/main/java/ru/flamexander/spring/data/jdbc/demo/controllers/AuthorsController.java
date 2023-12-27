package ru.flamexander.spring.data.jdbc.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.flamexander.spring.data.jdbc.demo.dtos.AuthorDto;
import ru.flamexander.spring.data.jdbc.demo.entities.Author;
import ru.flamexander.spring.data.jdbc.demo.exceptions.ResourceNotFoundException;
import ru.flamexander.spring.data.jdbc.demo.services.AuthorsService;

import java.util.function.Function;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorsController {
    private final AuthorsService authorsService;

    private static final Function<Author, AuthorDto> MAP_TO_DTO_FUNCTION = a -> new AuthorDto(a.getId(), a.getFullName());

    @Autowired
    public AuthorsController(AuthorsService authorsService) {
        this.authorsService = authorsService;
    }

    @GetMapping("/{id}")
    public AuthorDto findById(@PathVariable Long id) {
        return authorsService.findById(id)
                .map(MAP_TO_DTO_FUNCTION)
                .orElseThrow(() -> new ResourceNotFoundException("Автор не найден"));
    }
}
