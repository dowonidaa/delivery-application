package com.sparta.delivery.payment.service;

import com.sparta.delivery.common.ResponseCode;
import com.sparta.delivery.common.exception.NotFoundException;
import com.sparta.delivery.order.repository.OrderRepository;
import com.sparta.delivery.payment.dto.CancelPaymentRequest;
import com.sparta.delivery.payment.dto.PaymentInfoResponse;
import com.sparta.delivery.payment.entity.Payment;
import com.sparta.delivery.payment.repositoy.PaymentRepository;
import com.sparta.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public Page<PaymentInfoResponse> getPayments(Pageable pageable) {
        return paymentRepository.findAll(pageable).map(PaymentInfoResponse::of);
    }

    public PaymentInfoResponse getPayment(UUID paymentId) {
        return paymentRepository.findById(paymentId)
                .map(PaymentInfoResponse::of)
                .orElseThrow(() -> new NotFoundException(ResponseCode.NOT_FOUND_PAYMENT));
    }


    @Transactional
    public void updatePayment(UUID paymentId, CancelPaymentRequest cancelPaymentRequest) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NotFoundException(ResponseCode.NOT_FOUND_PAYMENT));
        payment.changePaymentByCancel(cancelPaymentRequest.getPaymentStatus());
    }


    @Transactional
    public void deletePayment(UUID paymentId, UUID userId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NotFoundException(ResponseCode.NOT_FOUND_PAYMENT));
        payment.delete(userId);
    }
}
