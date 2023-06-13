package com.example.compmath4.service;

import com.example.compmath4.model.TableOfValues;
import com.example.compmath4.model.equation.Equation;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class TableHandlerImpl implements TableHandler {

    @Override
    public double calculateSumOfMultiOfXAndY(int degreeForX, int degreeForY, TableOfValues table) {
        double sum = 0;
        List<Double> xValues = table.getXValues();
        List<Double> yValues = table.getYValues();
        for (int i = 0; i < table.getTableSize(); i++) {
            sum += Math.pow(xValues.get(i), degreeForX) * Math.pow(yValues.get(i), degreeForY);
        }
        return sum;
    }

    @Override
    public String getTable(Equation equation, TableOfValues table) {
        StringBuilder line1 = new StringBuilder();
        StringBuilder line2 = new StringBuilder();
        line1.append("f(x) | ");
        line2.append("e    | ");
        table.getPoints().forEach(point -> line1.append(String.format("%2s%-8s"," ", String.format("%.3f", equation.calculateEquationValue(point.getX())))).append("|"));
        table.getPoints().forEach(point -> line2.append(String.format("%2s%-8s"," ", String.format("%.3f",equation.calculateEquationValue(point.getX()) - point.getY()))).append("|"));
        line1.append("\n").append(line2).append("\n");
        return line1.toString();
    }

    @Override
    public double calculateDeviationMeasure(Equation equation, TableOfValues table) {
        return table.getPoints().stream().mapToDouble(point -> Math.pow(equation.calculateEquationValue(point.getX()) - point.getY(), 2)).sum();
    }

    @Override
    public double calculateStandardDeviation(Equation equation, TableOfValues table) {
        return Math.sqrt(table.getPoints().stream().mapToDouble(point -> Math.pow(equation.calculateEquationValue(point.getX()) - point.getY(), 2)).sum() / table.getTableSize());
    }

}
