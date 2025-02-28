package com.jetbrains.bookmarks;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookmarkRepository extends CrudRepository<Bookmark, String> {
    List<Bookmark> findAllByOrderByCreatedAtDesc();
}
