package com.Steprella.Steprella.repositories;

import com.Steprella.Steprella.entities.concretes.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends BaseRepository<Comment, Integer> {

    List<Comment> findByProductId(int productId);
    List<Comment> findByCustomerId(int customerId);
    boolean existsByIdAndCustomerId(int id, int customerId);
}
