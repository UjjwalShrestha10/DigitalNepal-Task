package com.example.assessment.page;

import com.example.assessment.blog.Blog;
import com.example.assessment.blog.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PageController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/home")
    public String showDashboard(Model model) {
        List<Blog> latestBlogs = blogService.getLatestBlogs();
        model.addAttribute("latestBlogs", latestBlogs);

        return "home";
    }
}
