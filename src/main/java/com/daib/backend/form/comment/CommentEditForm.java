package com.daib.backend.form.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentEditForm {

    private Long id;

    @NotBlank
    private String writer;

    @NotBlank
    private String content;
}
