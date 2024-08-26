package com.example.assessment.blog;

import com.example.assessment.category.CategoryService;
import com.example.assessment.tag.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/blog")
public class BlogController {

    private final BlogService blogService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;


    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/blog-list")
    public String listAllBlogs(Model model) {
        List<Blog> blogs = blogService.getAllBlogs();
        model.addAttribute("blogs", blogs);

        return "blog_list";
    }

    @GetMapping("/update/page/{id}")
    public String updateBlogPage(Model model, @PathVariable long id) {
        Blog blog = blogService.getBlogById(id).orElseThrow(() -> new RuntimeException("Blog with ID " + id + " does not exist"));
        model.addAttribute("blog", blog);
        model.addAttribute("tags", tagService.getAllTags());
        model.addAttribute("categories", categoryService.getAllCategories());

        return "update_blog";
    }

    @GetMapping("/page")
    public String showCreateBlogPage(Model model) {
        model.addAttribute("blog", new Blog());
        model.addAttribute("tags", tagService.getAllTags());
        model.addAttribute("categories", categoryService.getAllCategories());

        return "create_blog";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Blog> getBlogById(@PathVariable Long id) {
        Optional<Blog> blog = blogService.getBlogById(id);

        return blog.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Blog> createBlog(@RequestBody BlogDTO blog) {
        Blog createdBlog = blogService.createBlog(blog);

        return ResponseEntity.ok(createdBlog);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBlog(@PathVariable Long id, @RequestBody BlogDTO blog) {
        blogService.updateBlog(id, blog);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteBlog(@PathVariable Long id) {
        blogService.deleteBlog(id);

        return ResponseEntity.noContent().build();
    }
}
