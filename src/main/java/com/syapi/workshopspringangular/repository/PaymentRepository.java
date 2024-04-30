package com.syapi.workshopspringangular.repository;

import com.syapi.workshopspringangular.entities.Payment;
import com.syapi.workshopspringangular.entities.PaymentStatus;
import com.syapi.workshopspringangular.entities.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByStudentCode(String code);
    List<Payment> findByStatus(PaymentStatus status);
    List<Payment> findByType(PaymentType type);
}
