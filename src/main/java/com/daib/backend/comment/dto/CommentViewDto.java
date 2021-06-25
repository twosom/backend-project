package com.daib.backend.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentViewDto {

    private Long id;
    private String writer;
    private String content;
    private LocalDateTime createdAt;

}
