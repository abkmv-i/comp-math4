package com.example.compmath4.approximation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class AbstractApproximation implements ApproximationFunction{
    private String nameOfApproximation;

    @Override
    public String toString() {
        return nameOfApproximation;
    }
}
