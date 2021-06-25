package com.daib.backend.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentForm {


    private Long postId;

    @NotBlank
    private String writer;

    @NotBlank
    private String content;

}