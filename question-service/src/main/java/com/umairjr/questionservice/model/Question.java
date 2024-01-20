package com.umairjr.questionservice.model;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "question_table")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String difficultylevel;
    private String category;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String question_title;
    private String correct_option;
}
