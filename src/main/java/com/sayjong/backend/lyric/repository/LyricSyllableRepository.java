package com.sayjong.backend.lyric.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sayjong.backend.lyric.domain.LyricSyllable;

@Repository
public interface LyricSyllableRepository
        extends JpaRepository<LyricSyllable, Long> {
}
