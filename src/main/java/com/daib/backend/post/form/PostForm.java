package com.daib.backend.post.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostForm {


    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private String writer;

    @NotBlank
    private String password;


}
