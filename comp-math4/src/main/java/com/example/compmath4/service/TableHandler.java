package com.example.compmath4.service;

import com.example.compmath4.model.TableOfValues;
import com.example.compmath4.model.equation.Equation;

public interface TableHandler {
    double calculateSumOfMultiOfXAndY(int degreeForX, int degreeForY, TableOfValues table);

    String getTable(Equation equation, TableOfValues table);

    double calculateDeviationMeasure(Equation equation, TableOfValues table);

    double calculateStandardDeviation(Equation equation, TableOfValues table);
}
