package com.ssamz.jblogweb.persistence;

import com.ssamz.jblogweb.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
