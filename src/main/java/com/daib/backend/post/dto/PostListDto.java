package com.daib.backend.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostListDto {

    private Long id;
    private String title;
    private String writer;
    private LocalDateTime createdAt;


}
