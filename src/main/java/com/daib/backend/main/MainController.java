package com.daib.backend.main;

import com.daib.backend.post.dto.PostListDto;
import com.daib.backend.post.repository.PostQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final PostQueryRepository postQueryRepository;

    @GetMapping("/")
    public String index(Pageable pageable, Model model) {
        Page<PostListDto> postList = postQueryRepository.findAllForIndex(pageable);
        model.addAttribute("postList", postList);
        return "index";
    }
}
