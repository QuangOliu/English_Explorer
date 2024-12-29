package com.ptit.EnglishExplorer.data.dto;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResDTO implements Serializable {
    private String status;
    private String message;
    private String url;
}
