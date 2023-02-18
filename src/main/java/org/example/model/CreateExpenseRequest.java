package org.example.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
public class CreateExpenseRequest {

    String category;
    Double price;

    LocalDateTime date;

    public CreateExpenseRequest(String category, Double price, LocalDateTime date) {
        this.category = category;
        this.price = price;
        this.date=date;

    }
}
