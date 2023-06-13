package com.example.compmath4.approximation;

import com.example.compmath4.model.TableOfValues;
import com.example.compmath4.model.equation.AlgebraicFunction;
import com.example.compmath4.model.equation.Equation;
import com.example.compmath4.model.equation.TranscendentalEquation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class LogApproximation extends AbstractApproximation{
    private final ApproximationFunction linearApproximation;

    @Autowired
    public LogApproximation(@Qualifier("LinearApproximation") ApproximationFunction linearApproximation) {
        super("Логарифмическая апроксимация");
        this.linearApproximation = linearApproximation;
    }

    @Override
    public Equation findFunction(TableOfValues table) {
        TableOfValues newValues = new TableOfValues(table.getXValues().stream().map(Math::log).toArray(Double[]::new), table.getYValues().toArray(Double[]::new), table.getTableSize());
        Equation equation = linearApproximation.findFunction(newValues);
        TranscendentalEquation equation1 = new TranscendentalEquation("ln", equation.getEquation().get(0).getCoefficients().get(0), new AlgebraicFunction(1, 1, 0 ));
        return new Equation(0,"Логарифмическая апроксимация", equation1, new AlgebraicFunction(1,0,equation.getEquation().get(0).getCoefficients().get(1)));
    }
}
