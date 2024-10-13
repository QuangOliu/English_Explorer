package com.ptit.EnglishExplorer.data.repository;

import com.ptit.EnglishExplorer.data.entity.Action;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionRepository  extends JpaRepository<Action, Long> {
}
