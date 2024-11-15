package com.Steprella.Steprella.repositories;

import com.Steprella.Steprella.entities.concretes.File;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends BaseRepository<File, Integer> {
}
