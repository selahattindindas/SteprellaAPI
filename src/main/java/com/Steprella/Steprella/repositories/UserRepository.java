package com.Steprella.Steprella.repositories;

import com.Steprella.Steprella.entities.concretes.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, Integer>{

    Optional<User> findByEmailIgnoreCase(String email);

    Optional<User> findByEmail(String email);
}
