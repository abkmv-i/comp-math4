package com.example.compmath4.approximation;

import com.example.compmath4.model.Matrix;
import com.example.compmath4.model.TableOfValues;
import com.example.compmath4.model.equation.AlgebraicFunction;
import com.example.compmath4.model.equation.Equation;
import com.example.compmath4.service.TableHandler;

import java.util.ArrayList;

public class LeastSquaresMethod extends AbstractApproximation {
    private TableHandler tableHandler;
    private int type;

    public LeastSquaresMethod(TableHandler tableHandler, int type, String name) {
        super(name);
        this.tableHandler = tableHandler;
        this.type = type;
    }

    public ArrayList<Double> findCoefficients(Matrix matrix) {

        for (int line = 0; line < matrix.getMatrixSize() - 1; line++) {
            for (int innerLine = line + 1; innerLine < matrix.getMatrixSize() - 1; innerLine++) {
                double value = -matrix.getValue(innerLine, line) / matrix.getValue(line, line);
                Matrix.Line line1 = matrix.multiplyLine(line, value);
                matrix.addLinetoLine(line1, innerLine);
            }
        }
        ArrayList<Double> vectorX = new ArrayList<>();
        for (int line = matrix.getMatrixSize() - 2; line >= 0; line--) {
            double rightValue = matrix.getLines().get(line).getRightValue();
            for (int column = matrix.getMatrixSize() - 2; column > line; column--) {
                rightValue -= matrix.getValue(line, column) * vectorX.get(-column + matrix.getMatrixSize() - 2);
            }
            rightValue /= matrix.getValue(line, line);
            vectorX.add(rightValue);
        }
        return vectorX;
    }

    public Matrix getMatrixByTable(TableOfValues values) {
        Matrix matrix = new Matrix(type + 2);
        for (int i = 0; i < matrix.getMatrixSize() - 1; i++) {
            ArrayList<Double> values1 = new ArrayList<>();
            for (int j = i; j < i + matrix.getMatrixSize() - 1; j++) {
                values1.add(tableHandler.calculateSumOfMultiOfXAndY(j, 0, values));
            }
            matrix.addLine(tableHandler.calculateSumOfMultiOfXAndY(i, 1, values), values1);
        }

        return matrix;
    }

    @Override
    public Equation findFunction(TableOfValues table) {
        Matrix matrix = getMatrixByTable(table);
        ArrayList<Double> values = findCoefficients(matrix);
        AlgebraicFunction algebraicFunction = new AlgebraicFunction(type, values);
        return new Equation(0, super.getNameOfApproximation(), algebraicFunction);
    }
}
