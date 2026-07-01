package com.shivam.blog.mappers;

import com.shivam.blog.domain.dtos.CategoryDto;
import com.shivam.blog.domain.dtos.CreateCategoryRequest;
import com.shivam.blog.domain.entities.Category;
import com.shivam.blog.domain.entities.PostStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {
    @Mapping(target = "postCount", source = "posts", qualifiedByName = "calculatePostCount")
    CategoryDto toDto(Category category);

    @Named("calculatePostCount")
    default long calculatePostCount(java.util.List<com.shivam.blog.domain.entities.Post> posts) {
        if (posts == null) {
            return 0;
        }
        return posts.stream()
                .filter(post -> PostStatus.PUBLISHED.equals(post.getStatus()))
                .count();
    }

    Category toEntity(CreateCategoryRequest createCategoryRequest);
}
