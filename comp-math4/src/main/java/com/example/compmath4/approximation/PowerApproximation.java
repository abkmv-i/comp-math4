package com.example.compmath4.approximation;

import com.example.compmath4.model.TableOfValues;
import com.example.compmath4.model.equation.AlgebraicFunction;
import com.example.compmath4.model.equation.Equation;
import com.example.compmath4.model.equation.TranscendentalEquation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component
public class PowerApproximation extends AbstractApproximation {
    private ApproximationFunction linearApproximation;

    @Autowired
    public PowerApproximation(@Qualifier("LinearApproximation") ApproximationFunction linearApproximation) {
        super("Степенная апроксимация");
        this.linearApproximation = linearApproximation;
    }

    @Override
    public Equation findFunction(TableOfValues table) {
        TableOfValues newValues = new TableOfValues(table.getXValues().stream().map(Math::log).toArray(Double[]::new), table.getYValues().stream().map(Math::log).toArray(Double[]::new), table.getTableSize());
        Equation equation = linearApproximation.findFunction(newValues);
        TranscendentalEquation equation1 = new TranscendentalEquation("x", Math.exp(equation.getEquation().get(0).getCoefficients().get(1)), new AlgebraicFunction(1, 0, equation.getEquation().get(0).getCoefficients().get(0)));
        return new Equation(0, "Степенная апроксимация", equation1);
    }
}
