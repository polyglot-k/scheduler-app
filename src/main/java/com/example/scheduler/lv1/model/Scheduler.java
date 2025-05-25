package com.example.scheduler.lv1.model;

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
    private String writer;
    private String password;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public Scheduler(String todo, String writer, String password) {
        this.todo = todo;
        this.writer = writer;
        this.password = password;
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }

    public static Scheduler from(String todo, String writer, String password) {
        return new Scheduler(todo, writer, password);
    }
}
