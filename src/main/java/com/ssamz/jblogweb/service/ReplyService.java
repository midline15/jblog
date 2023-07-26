package com.ssamz.jblogweb.service;

import com.ssamz.jblogweb.domain.Post;
import com.ssamz.jblogweb.domain.Reply;
import com.ssamz.jblogweb.domain.User;
import com.ssamz.jblogweb.persistence.PostRepository;
import com.ssamz.jblogweb.persistence.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;

    @Transactional
    public void deleteReply(Long replyId) {
        replyRepository.deleteById(replyId);
    }

    @Transactional
    public void insertReply(Long postId, Reply reply, User user) {
        Post post = postRepository.findById(postId).get();
        reply.setUser(user);
        reply.setPost(post);
        replyRepository.save(reply);
    }
}
