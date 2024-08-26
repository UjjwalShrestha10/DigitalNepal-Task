package com.example.assessment.tag;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Optional<Tag> getTagById(Long id) {
        return tagRepository.findById(id);
    }

    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public Tag updateTag(Long id, Tag tag) {
        if (tagRepository.existsById(id)) {
            tag.setId(id);
            return tagRepository.save(tag);
        }
        return null;
    }

    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}