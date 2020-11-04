package com.project.webapp.drivingschool.data.repository;

import com.project.webapp.drivingschool.data.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repozytorium dla dokumentów wymaganych w trakcie kursu
 */
@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {


}
