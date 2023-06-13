package com.example.compmath4.service;

import com.example.compmath4.approximation.*;
import com.example.compmath4.model.TableOfValues;
import com.example.compmath4.model.equation.Equation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


@Service
public class EquationService {
    private final TableHandlerImpl handler;
    public ArrayList<Equation> equations = new ArrayList<>();
    private final ArrayList<ApproximationFunction> methodsOfApproximation;

    @Autowired
    public EquationService(TableHandlerImpl handler, ApproximationFunction... methods) {
        this.handler = handler;
        methodsOfApproximation = new ArrayList<>();
        LinearApproximation linearApproximation = new LinearApproximation(handler);
        this.methodsOfApproximation.add(new ExponentialApproximation(linearApproximation));
        this.methodsOfApproximation.add(new LogApproximation(linearApproximation));
        this.methodsOfApproximation.add(new QuadraticApproximation(handler));
        this.methodsOfApproximation.add(new ThirdApproximation(handler));
        this.methodsOfApproximation.add(linearApproximation);

    }



    public ArrayList<Equation> getApproximationFunction(TableOfValues table) {
        JFrame outFrame = new JFrame();
        outFrame.setSize(770,770);
        JTextArea out = new JTextArea();
        out.append("Исходные данные:\n" + table.toString());
        methodsOfApproximation.forEach(method -> {
            out.append("\n\nАпроксимация: " + method.toString());
            Equation equation = method.findFunction(table);
            if (equation.getEquation().get(0).getCoefficients().contains(Double.NaN)) {
                out.append("Не получилось апроксимировать данным методом");
            } else {
                this.equations.add(equation);
                out.append("\nПолученное уравнение: " + equation + "\n");
                out.append(handler.getTable(equation, table));
                out.append("e^2: " + handler.calculateDeviationMeasure(equation, table));
                double approximation = handler.calculateStandardDeviation(equation, table);
                out.append("\nОтклонение: " + approximation);
                if (method instanceof LinearApproximation) {
                    double xAverage = calculateAverageValue((ArrayList<Double>) table.getXValues());
                    double yAverage = calculateAverageValue((ArrayList<Double>) table.getYValues());
                    double sum1 = 0;
                    double sum2 = 0;
                    double sum3 = 0;
                    List<Double> xValues = table.getXValues();
                    List<Double> yValues = table.getYValues();
                    for (int counter = 0; counter < table.getTableSize(); counter++) {
                        double xDifference = xValues.get(counter) - xAverage;
                        double yDifference = yValues.get(counter) - yAverage;
                        sum1 += xDifference * yDifference;
                        sum2 += Math.pow(xDifference, 2);
                        sum3 += Math.pow(yDifference, 2);
                    }
                    double coefficient = sum1 / Math.sqrt(sum2 * sum3);
                    if (!Double.isNaN(coefficient)) out.append("\nКоэффициент Пирсона = "+ coefficient);

                }
            }
        });
        Equation bestEquation = new Equation(0);
        double bestApproximation = Double.MAX_VALUE;
        for (Equation equation : this.equations) {
            double approximation = handler.calculateStandardDeviation(equation, table);
            if (approximation <= bestApproximation) {
                bestApproximation = approximation;
                bestEquation = equation;
            }
        }
        out.append("\nЛучшая апроксимирующая функция: " + bestEquation + "\nМетод: " + bestEquation.getInformation());
        outFrame.add(out);

        outFrame.setVisible(true);
        return equations;
    }

    private double calculateAverageValue(ArrayList<Double> values) {
        double average = 0;
        for (double value : values) {
            average += value;
        }
        average /= values.size();
        return average;
    }
}
