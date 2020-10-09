package com.project.webapp.drivingschool.repository;

import com.project.webapp.drivingschool.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repozytorium dla dokumentów wymaganych w trakcie kursu
 */
@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {


}
