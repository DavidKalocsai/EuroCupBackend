package com.example.easynotes.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.Voucher;
import com.example.easynotes.repository.VoucherRepository;

/**
 * Voucher Controller.
 */
@RestController
@RequestMapping("/api")
public class VoucherController {

    @Autowired
    VoucherRepository voucherRepository;

    @GetMapping("/vouchers")
    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    @PostMapping("/vouchers")
    public Voucher addVoucher(@Valid @RequestBody Voucher note) {
        return voucherRepository.save(note);
    }

    @GetMapping("/vouchers/{id}")
    public Voucher getVoucherById(@PathVariable(value = "id") Long voucherId) {
        return voucherRepository.findById(voucherId)
                .orElseThrow(() -> new ResourceNotFoundException("Voucher", "id", voucherId));
    }
}
