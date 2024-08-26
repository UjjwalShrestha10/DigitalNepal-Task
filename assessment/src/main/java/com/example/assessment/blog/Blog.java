package com.example.assessment.blog;

import com.example.assessment.category.Category;
import com.example.assessment.tag.Tag;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Entity(name = "blog")
@Data
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @NotNull(message = "Category must not be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @NotEmpty(message = "Tags must not be empty")
    @ManyToMany
    @JoinTable(
            name = "blog_tag",
            joinColumns = @JoinColumn(name = "blog_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;
}

