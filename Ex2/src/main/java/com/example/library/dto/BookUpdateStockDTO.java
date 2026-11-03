package com.example.library.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookUpdateStockDTO {

    @NotNull(message = "Stock cannot be null")
    @Min(value = 0, message = "Stock must be greater than or equal to 0")
    private Integer stock;
}
