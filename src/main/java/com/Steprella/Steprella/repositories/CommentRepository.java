package com.Steprella.Steprella.repositories;

import com.Steprella.Steprella.entities.concretes.Comment;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends BaseRepository<Comment, Integer> {
}
