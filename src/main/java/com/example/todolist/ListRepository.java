package com.example.todolist;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ListRepository extends JpaRepository<CustomList, Integer> {
    public CustomList findByTitle(String title);
}
