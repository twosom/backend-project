package com.daib.backend.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostEditForm {

    private Long id;
    private String title;
    private String content;
    private String writer;
    private String password;

}
