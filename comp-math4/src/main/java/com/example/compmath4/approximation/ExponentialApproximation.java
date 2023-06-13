package com.example.compmath4.approximation;

import com.example.compmath4.model.TableOfValues;
import com.example.compmath4.model.equation.AlgebraicFunction;
import com.example.compmath4.model.equation.Equation;
import com.example.compmath4.model.equation.TranscendentalEquation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ExponentialApproximation extends AbstractApproximation {
    private final ApproximationFunction linearApproximation;

    @Autowired
    public ExponentialApproximation(@Qualifier("LinearApproximation") ApproximationFunction linearApproximation) {
        super("Экспоненциальная апроксимация");
        this.linearApproximation = linearApproximation;
    }

    @Override
    public Equation findFunction(TableOfValues table) {
        TableOfValues newValues = new TableOfValues(table.getXValues().toArray(Double[]::new), table.getYValues().stream().map(Math::log).toArray(Double[]::new), table.getTableSize());
        Equation equation = linearApproximation.findFunction(newValues);
        TranscendentalEquation equation1 = new TranscendentalEquation("e", Math.exp(equation.getEquation().get(0).getCoefficients().get(1)), new AlgebraicFunction(1, equation.getEquation().get(0).getCoefficients().get(0), 0));
        return new Equation(0, "Экспоненциальная апроксимация", equation1);
    }
}
