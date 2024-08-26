package com.example.assessment.tag;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/tag")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/tag-list")
    public String listAllTags(Model model) {
        List<Tag> tags = tagService.getAllTags();
        model.addAttribute("tags", tags);

        return "tag_list";
    }


    @GetMapping("/update/page/{id}")
    public String updateTagPage(Model model, @PathVariable long id) {
        Tag tag = tagService.getTagById(id).orElseThrow(() -> new RuntimeException("Tag with ID " + id + " does not exist"));
        model.addAttribute("tag", tag);

        return "update_tag";
    }

    @GetMapping("/page")
    public String showCreateTagPage() {
        return "create_tag";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> getTagById(@PathVariable Long id) {
        Optional<Tag> tag = tagService.getTagById(id);

        return tag.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag) {
        Tag createdTag = tagService.createTag(tag);

        return ResponseEntity.ok(createdTag);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable Long id, @RequestBody Tag tag) {
        Tag updatedTag = tagService.updateTag(id, tag);

        return updatedTag != null ? ResponseEntity.ok(updatedTag) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);

        return ResponseEntity.noContent().build();
    }
}
