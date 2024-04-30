package com.syapi.workshopspringangular.web;

import com.syapi.workshopspringangular.entities.Payment;
import com.syapi.workshopspringangular.entities.PaymentStatus;
import com.syapi.workshopspringangular.entities.PaymentType;
import com.syapi.workshopspringangular.entities.Student;
import com.syapi.workshopspringangular.repository.PaymentRepository;
import com.syapi.workshopspringangular.repository.StudentRepository;
import com.syapi.workshopspringangular.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class StudentRestController {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private PaymentService paymentService;



    @GetMapping(path = "/payments/getAll")
    public List<Payment> allPayments() {
        return paymentRepository.findAll();
    }

    @GetMapping(path = "/payments/getByCode/{code}")
    public List<Payment> paymentByStudent(@PathVariable String code){
        return paymentRepository.findByStudentCode(code);
    }

    @GetMapping(path = "/payments/getByStatus")
    public List<Payment> paymentByStatus(@RequestParam PaymentStatus status){
        return paymentRepository.findByStatus(status);
    }

    @GetMapping(path = "/students/findByType")
    public List<Payment> paymentByType(@RequestParam PaymentType type){
        return paymentRepository.findByType(type);
    }

    @GetMapping(path = "/payments/getOne/{id}")
    public Payment getPaiementById(@PathVariable Long id){
        return paymentRepository.findById(id).get();
    }

    @GetMapping(path = "/students/getAll")
    public List<Student> allStudent() {
        return studentRepository.findAll();
    }

    @GetMapping(path = "/students/findByCode/{code}")
    public Student getStudentByCode(@PathVariable String code){
        return studentRepository.findByCode(code);
    }

    @GetMapping(path = "/students/Program")
    public List<Student> getStudentByProgramId(@RequestParam String programId){
        return studentRepository.findByProgramId(programId);
    }

    @PutMapping(path = "/paymentsupdate/{id}")
    public Payment updatePaymentStatus(@RequestParam PaymentStatus status, @PathVariable Long id) {
        return paymentService.updatePaymentStatus(status, id);

    }

    @PostMapping(path = "/payments/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Payment createPayment(@RequestParam MultipartFile file, double amount, PaymentType type, String studentCode) throws IOException {

        return this.paymentService.createPayment(file, amount, type, studentCode);
    }

    @GetMapping(path= "/payementFile/{paymentId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] getPaymentFile(@PathVariable Long paymentId) throws IOException {

        return paymentService.getPaymentFile(paymentId);

    }

}
