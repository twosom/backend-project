package com.daib.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

}
