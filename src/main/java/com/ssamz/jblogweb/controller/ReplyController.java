package com.ssamz.jblogweb.controller;

import com.ssamz.jblogweb.DTO.ResponseDTO;
import com.ssamz.jblogweb.domain.Reply;
import com.ssamz.jblogweb.domain.User;
import com.ssamz.jblogweb.persistence.ReplyRepository;
import com.ssamz.jblogweb.security.UserDetailsImpl;
import com.ssamz.jblogweb.service.ReplyService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class ReplyController {

    private final ReplyService replyService;

    @DeleteMapping("/reply/{replyId}")
    public @ResponseBody ResponseDTO<?> deleteReply(@PathVariable Long replyId) {
        replyService.deleteReply(replyId);
        return new ResponseDTO<>(HttpStatus.OK.value(), replyId + "번 댓글이 삭제됐습니다.");
    }

    @PostMapping("/reply/{postId}")
    public @ResponseBody ResponseDTO<?> insertReply(@PathVariable Long postId, @RequestBody Reply reply, @AuthenticationPrincipal UserDetailsImpl principal) {
        replyService.insertReply(postId, reply, principal.getUser());
        return new ResponseDTO<>(HttpStatus.OK.value(), postId + "번 포스트에 대한 댓글이 등록됐습니다.");
    }
}
