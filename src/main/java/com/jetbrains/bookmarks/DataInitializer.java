package com.jetbrains.bookmarks;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final BookmarkRepository bookmarkRepository;

    public DataInitializer(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    @Override
    public void run(String... args) {
        bookmarkRepository.save(new Bookmark("JetBrains Blog", "https://blog.jetbrains.com"));
        bookmarkRepository.save(new Bookmark("IntelliJ IDEA Blog", "https://blog.jetbrains.com/idea/"));
    }
}
