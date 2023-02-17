package org.example.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GroupByCategoryAndMonth {

    String category;
    Double price;
    int monthDate;


    @Override
    public String toString() {
        return "category=" + category + " price=" + price + " month=" + monthDate+"\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupByCategoryAndMonth that = (GroupByCategoryAndMonth) o;
        return monthDate == that.monthDate && Objects.equals(category, that.category) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, price, monthDate);
    }
}
