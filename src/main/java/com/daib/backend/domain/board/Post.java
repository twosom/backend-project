package com.daib.backend.domain.board;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private String writer;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<Comment> commentList = new ArrayList<>();
}
