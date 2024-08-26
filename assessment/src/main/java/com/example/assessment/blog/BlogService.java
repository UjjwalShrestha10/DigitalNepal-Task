package com.example.assessment.blog;

import com.example.assessment.category.Category;
import com.example.assessment.category.CategoryRepository;
import com.example.assessment.tag.Tag;
import com.example.assessment.tag.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BlogService {

    private final BlogRepository blogRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    public BlogService(BlogRepository blogRepository, CategoryRepository categoryRepository, TagRepository tagRepository) {
        this.blogRepository = blogRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
    }

    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    public Optional<Blog> getBlogById(Long id) {
        return blogRepository.findById(id);
    }

    @Transactional
    public Blog createBlog(BlogDTO blogDTO) {
        Category category = categoryRepository.findById(blogDTO.getCategory())
                .orElseThrow(() -> new RuntimeException("Category with ID " + blogDTO.getCategory() + " does not exist"));

        Set<Tag> tags = blogDTO.getTags().stream()
                .map(tagId -> tagRepository.findById(tagId)
                        .orElseThrow(() -> new RuntimeException("Tag with ID " + tagId + " does not exist")))
                .collect(Collectors.toSet());

        Blog blog = new Blog();
        blog.setTitle(blogDTO.getTitle());
        blog.setContent(blogDTO.getContent());
        blog.setCategory(category);
        blog.setTags(tags);

        return blogRepository.save(blog);
    }

    @Transactional
    public void updateBlog(Long id, BlogDTO blogDTO) {
        Blog existingBlog = blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog with ID " + id + " does not exist"));

        Category category = categoryRepository.findById(blogDTO.getCategory())
                .orElseThrow(() -> new RuntimeException("Category with ID " + blogDTO.getCategory() + " does not exist"));
        existingBlog.setCategory(category);

        Set<Tag> tags = blogDTO.getTags().stream()
                .map(tagId -> tagRepository.findById(tagId)
                        .orElseThrow(() -> new RuntimeException("Tag with ID " + tagId + " does not exist")))
                .collect(Collectors.toSet());

        existingBlog.setTags(tags);
        existingBlog.setTitle(blogDTO.getTitle());
        existingBlog.setContent(blogDTO.getContent());

        blogRepository.save(existingBlog);
    }

    public void deleteBlog(Long id) {
        if (!blogRepository.existsById(id)) {
            throw new RuntimeException("Blog with ID " + id + " does not exist");
        }
        blogRepository.deleteById(id);
    }

    public List<Blog> getLatestBlogs() {
        return blogRepository.findTop5ByOrderByIdDesc();
    }
}
