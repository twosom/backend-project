package com.daib.backend.post.dto;

import com.daib.backend.comment.dto.CommentViewDto;
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
public class PostViewDto {


    private Long id;

    private String title;

    private String content;

    private String writer;

    private LocalDateTime createdAt;

    List<CommentViewDto> commentList = new ArrayList<>();
}
