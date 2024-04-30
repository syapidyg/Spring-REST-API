package com.syapi.workshopspringangular;

import com.syapi.workshopspringangular.entities.Payment;
import com.syapi.workshopspringangular.entities.PaymentStatus;
import com.syapi.workshopspringangular.entities.PaymentType;
import com.syapi.workshopspringangular.entities.Student;
import com.syapi.workshopspringangular.repository.PaymentRepository;
import com.syapi.workshopspringangular.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class WorkshopSpringAngularApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkshopSpringAngularApplication.class, args);
    }


    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository, PaymentRepository paymentRepository) {
        return args -> {
            studentRepository.save(Student.builder()
                    .id(UUID.randomUUID().toString())
                    .firstName("John")
                    .lastName("Smith")
                    .code("112233")
                    .programId("SOA21")
                    .build());
            studentRepository.save(Student.builder()
                    .id(UUID.randomUUID().toString())
                    .firstName("Axel")
                    .lastName("Tchiffo")
                    .code("112244")
                    .programId("SOA21")
                    .build());
            studentRepository.save(Student.builder()
                    .id(UUID.randomUUID().toString())
                    .firstName("Florent")
                    .lastName("Assako")
                    .code("112255")
                    .programId("SOA21")
                    .build());

            PaymentType[] paymentType = PaymentType.values();
            Random random = new Random();
            studentRepository.findAll().forEach(st->{
                for (int i = 0; i < 10; i++) {
                    int index = random.nextInt(paymentType.length);
                    Payment payment = Payment.builder()
                           .date(LocalDate.now())
                           .amount(1000+(int)(Math.random()+2000))
                           .status(PaymentStatus.CREATED)
                           .type(paymentType[index])
                            .student(st)
                           .build();
                    paymentRepository.save(payment);
                }
            });
        };


    }
}
