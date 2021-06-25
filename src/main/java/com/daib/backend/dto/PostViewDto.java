package com.daib.backend.dto;

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
