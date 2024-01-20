package com.umairjr.quizservice.model;

import lombok.Data;

@Data
public class QuizDTO {
    private String quiz_title;
    private String category;
    private Integer numQs;
}
