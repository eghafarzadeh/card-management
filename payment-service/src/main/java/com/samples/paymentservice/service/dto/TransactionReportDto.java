package com.samples.paymentservice.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @author Elham
 * @since 10/1/2020
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionReportDto {
    private String source;
    private Long successfulTransactionCount;
    private Long unSuccessfulTransactionCount;

    public TransactionReportDto(String source) {
        this.source = source;
    }

    @Override
    public int hashCode() {
        return 31 * source.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TransactionReportDto transactionReportDto = (TransactionReportDto) obj;
        return Objects.equals(source, transactionReportDto.source);
    }

}
