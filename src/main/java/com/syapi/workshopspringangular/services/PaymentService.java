package com.syapi.workshopspringangular.services;


import com.syapi.workshopspringangular.entities.Payment;
import com.syapi.workshopspringangular.entities.PaymentStatus;
import com.syapi.workshopspringangular.entities.PaymentType;
import com.syapi.workshopspringangular.entities.Student;
import com.syapi.workshopspringangular.repository.PaymentRepository;
import com.syapi.workshopspringangular.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
public class PaymentService {

    private StudentRepository studentRepository;
    private PaymentRepository paymentRepository;

    public PaymentService(StudentRepository studentRepository, PaymentRepository paymentRepository) {
        this.studentRepository = studentRepository;
        this.paymentRepository = paymentRepository;
    }

    public Payment createPayment(MultipartFile file, double amount, PaymentType type, String studentCode) throws IOException {

        Path folderPath = Paths.get(System.getProperty("user.home"), "workshop", "REST");

        if (!Files.exists(folderPath)){
            Files.createDirectories(folderPath);
        }
        String fileName = UUID.randomUUID().toString();
        Path filePath = Paths.get(folderPath.toString(), fileName+".pdf");
        Files.copy(file.getInputStream(), filePath);
        Student student = studentRepository.findByCode(studentCode);
        Payment payment = Payment.builder().amount(amount)
                .date(LocalDate.now())
                .file(filePath.toUri().toString())
                .student(student)
                .type(type)
                .status(PaymentStatus.CREATED)
                .build();
        return paymentRepository.save(payment);
    }

    public Payment updatePaymentStatus(PaymentStatus status, Long id) {
        Payment payment = paymentRepository.findById(id).get();
        payment.setStatus(status);
        return paymentRepository.save(payment);

    }

    public byte[] getPaymentFile(Long paymentId) throws IOException {
        Payment payment = paymentRepository.findById(paymentId).get();
        return Files.readAllBytes(Path.of(URI.create(payment.getFile())));
    }


}
