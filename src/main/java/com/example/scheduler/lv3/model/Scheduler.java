package com.example.scheduler.lv3.model;

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

    public Scheduler(String todo) {
        this.todo = todo;
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }

    public static Scheduler from(String todo) {
        return new Scheduler(todo);
    }

    public void updateTodo(String todo) {
        this.todo = todo;
    }
}
