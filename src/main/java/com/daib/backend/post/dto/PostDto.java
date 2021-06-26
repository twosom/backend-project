package com.daib.backend.post.dto;

import com.daib.backend.comment.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {

    private Long id;

    private String title;

    private String content;

    private String writer;

    private LocalDateTime createdAt;

    private List<CommentDto> commentList = new ArrayList<>();
}
