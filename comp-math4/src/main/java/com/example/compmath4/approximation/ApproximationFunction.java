package com.example.compmath4.approximation;

import com.example.compmath4.model.TableOfValues;
import com.example.compmath4.model.equation.Equation;

public interface ApproximationFunction {
    Equation findFunction(TableOfValues table);

}
