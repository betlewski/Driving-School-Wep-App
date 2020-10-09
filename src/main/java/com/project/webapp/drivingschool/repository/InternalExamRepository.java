package com.project.webapp.drivingschool.repository;

import com.project.webapp.drivingschool.model.InternalExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repozytorium dla egzaminów wewnętrznych w trakcie kursu
 */
@Repository
public interface InternalExamRepository extends JpaRepository<InternalExam, Long> {


}
