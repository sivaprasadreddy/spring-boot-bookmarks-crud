package com.jetbrains.bookmarks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@Import(TestcontainersConfiguration.class)
class BookmarkRepositoryTest {
    @Autowired
    BookmarkRepository bookmarkRepository;

    @BeforeEach
    void setUp() {
        bookmarkRepository.deleteAll();
    }

    @Test
    void shouldFindBookmarkById() {
        var bookmark = new Bookmark("JetBrains Blog", "https://blog.jetbrains.com");
        String bookmarkId = bookmarkRepository.save(bookmark).getId();

        var bookmarkInfo = bookmarkRepository.findById(bookmarkId).orElseThrow();
        assertThat(bookmarkInfo.getTitle()).isEqualTo("JetBrains Blog");
        assertThat(bookmarkInfo.getUrl()).isEqualTo("https://blog.jetbrains.com");
    }

    @Test
    void shouldGetAllBookmarksOrderByCreatedAtDesc() {
        bookmarkRepository.save(new Bookmark("JetBrains Blog", "https://blog.jetbrains.com"));
        bookmarkRepository.save(new Bookmark("IntelliJ IDEA Blog", "https://blog.jetbrains.com/idea/"));

        List<Bookmark> bookmarks = bookmarkRepository.findAllByOrderByCreatedAtDesc();

        assertThat(bookmarks).hasSize(2);
        assertThat(bookmarks.get(0).getTitle()).isEqualTo("IntelliJ IDEA Blog");
        assertThat(bookmarks.get(1).getTitle()).isEqualTo("JetBrains Blog");
    }
}
