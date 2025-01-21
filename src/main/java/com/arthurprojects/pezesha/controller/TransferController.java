package com.arthurprojects.pezesha.controller;

import com.arthurprojects.pezesha.dto.TransferDTO;
import com.arthurprojects.pezesha.entity.Transfer;
import com.arthurprojects.pezesha.service.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transfers")
public class TransferController {
    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    public ResponseEntity<Transfer> createTransfer(@RequestBody @Valid TransferDTO transferDTO) {
        return ResponseEntity.ok(transferService.createTransfer(transferDTO));
    }
}
