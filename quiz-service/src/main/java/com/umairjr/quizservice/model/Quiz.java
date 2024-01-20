package com.umairjr.quizservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "quiz_table")
@Data
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String quiz_title;

    @ElementCollection
    private List<Integer> questionIds;
}
