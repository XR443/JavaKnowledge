package com.github.raspopov.aricles.unit_tests_solves_architect_problems.constraints;

import com.github.raspopov.aricles.unit_tests_solves_architect_problems.exception.MissingParameterException;
import com.github.raspopov.aricles.unit_tests_solves_architect_problems.model.ModelA;

import java.util.function.Consumer;

public class ModelCMissing implements Consumer<ModelA> {

    @Override
    public void accept(ModelA modelA) {
        if (modelA.modelC() == null)
            throw new MissingParameterException("ModelC param missing");
    }
}
