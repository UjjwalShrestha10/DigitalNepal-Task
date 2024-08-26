package com.example.assessment.blog;

import lombok.Data;

import java.util.Set;

@Data
public class BlogDTO {
    private String title;
    private String content;
    private Long category;
    private Set<Long> tags;
}
