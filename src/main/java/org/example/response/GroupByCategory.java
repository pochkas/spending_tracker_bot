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
public class GroupByCategory {

    String category;
    Double price;


    @Override
    public String toString() {
        return  "category=" + category + " price=" + price+"\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupByCategory that = (GroupByCategory) o;
        return Objects.equals(category, that.category) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, price);
    }
}
