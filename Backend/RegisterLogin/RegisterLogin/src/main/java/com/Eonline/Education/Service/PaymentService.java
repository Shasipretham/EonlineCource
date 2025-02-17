package com.Eonline.Education.Service;

import com.Eonline.Education.Request.PaymentRequest;
import com.Eonline.Education.modals.Payment;
import com.Eonline.Education.repository.PaymentRepository;
import com.Eonline.Education.user.PaymentStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    OtpService otpService;

    public Payment processPayment(PaymentRequest paymentRequest) {
        Payment payment = new Payment();
        String userId= otpService.generateUserId();
        payment.setUserId(Long.valueOf(userId));
        payment.setUserName(paymentRequest.getFirstName() + " " + paymentRequest.getLastName());
        payment.setUserEmail(paymentRequest.getUserEmail());
        payment.setRazorpayPaymentId(paymentRequest.getRazorpayPaymentId());
        payment.setTotalAmount(paymentRequest.getTotalAmount());
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
        LocalDate joiningDate=LocalDate.now();
        payment.setJoiningDate(LocalDate.now());
        LocalDate expiryDate = joiningDate.plusMonths(paymentRequest.getCourseDuration());
        payment.setExpiryDate(expiryDate);
        payment.setPaymentStatus(PaymentStatus.COMPLETED);
        // Convert course names and prices to JSON string
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> courseDetails = new HashMap<>();
            courseDetails.put("courseNames", paymentRequest.getCourseNames());
            courseDetails.put("coursePrices", paymentRequest.getCoursePrices());
            payment.setCourseDetails(objectMapper.writeValueAsString(courseDetails));
        } catch (Exception e) {
            // Handle JSON serialization exception
            e.printStackTrace();
        }
        payment.setTotalAmount(paymentRequest.getTotalAmount());
        payment.setRazorpayPaymentId(paymentRequest.getRazorpayPaymentId());
        paymentRepository.save(payment);
        return payment;
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
