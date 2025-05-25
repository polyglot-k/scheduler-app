package com.example.scheduler.lv4.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@ToString
public class Scheduler {
    private Long id;
    private String todo;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private User user;

    public Scheduler(String todo, User user) {
        this.todo = todo;
        this.user = user;
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }

    public static Scheduler from(String todo, User user) {
        return new Scheduler(todo, user);
    }

    public void updateTodo(String todo) {
        this.todo = todo;
    }
}
