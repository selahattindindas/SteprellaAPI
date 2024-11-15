package com.Steprella.Steprella.repositories;

import com.Steprella.Steprella.entities.concretes.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User, Integer>{
}
