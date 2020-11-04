package com.project.webapp.drivingschool.data.repository;

import com.project.webapp.drivingschool.data.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repozytorium dla płatności związanych z kursem nauki jazdy
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {


}
