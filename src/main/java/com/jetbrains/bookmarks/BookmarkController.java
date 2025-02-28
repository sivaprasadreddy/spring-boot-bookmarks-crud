package com.jetbrains.bookmarks;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/bookmarks")
class BookmarkController {
    private final BookmarkRepository bookmarkRepository;

    BookmarkController(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    @GetMapping
    List<Bookmark> getBookmarks() {
        return bookmarkRepository.findAllByOrderByCreatedAtDesc();
    }

    @GetMapping("/{id}")
    ResponseEntity<Bookmark> getBookmarkById(@PathVariable String id) {
        var bookmark = bookmarkRepository
                .findById(id)
                .orElseThrow(() -> new BookmarkNotFoundException("Bookmark not found"));
        return ResponseEntity.ok(bookmark);
    }

    record CreateBookmarkPayload(
            @NotBlank(message = "Title is required")
            String title,
            @NotBlank(message = "URL is required")
            String url) {}

    @PostMapping
    ResponseEntity<Void> createBookmark(@Valid @RequestBody CreateBookmarkPayload payload) {
        var bookmark = new Bookmark(payload.title(), payload.url());
        var savedBookmark = bookmarkRepository.save(bookmark);
        var url = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .build(savedBookmark.getId());
        return ResponseEntity.created(url).build();
    }

    record UpdateBookmarkPayload(
             @NotBlank(message = "Title is required")
             String title,
             @NotBlank(message = "URL is required")
             String url) {}

    @PutMapping("/{id}")
    ResponseEntity<Void> updateBookmark(@PathVariable String id,
                                        @Valid @RequestBody UpdateBookmarkPayload payload) {
        var bookmark = bookmarkRepository.findById(id)
                        .orElseThrow(() -> new BookmarkNotFoundException("Bookmark not found"));
        bookmark.setTitle(payload.title());
        bookmark.setUrl(payload.url());
        bookmark.setUpdatedAt(Instant.now());
        bookmarkRepository.save(bookmark);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    void deleteBookmark(@PathVariable String id) {
        var bookmark = bookmarkRepository.findById(id)
                    .orElseThrow(() -> new BookmarkNotFoundException("Bookmark not found"));
        bookmarkRepository.delete(bookmark);
    }

    @ExceptionHandler(BookmarkNotFoundException.class)
    ResponseEntity<Void> handle(BookmarkNotFoundException e) {
        return ResponseEntity.notFound().build();
    }
}
