package com.github.raspopov.aricles.unit_tests_solves_architect_problems;

import com.github.raspopov.aricles.unit_tests_solves_architect_problems.model.ModelA;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class ModelService {

    private final List<Consumer<ModelA>> checks;

    public ModelA save(ModelA modelA) {
        checks.forEach(consumer -> consumer.accept(modelA));

        System.out.println("Saving...\nSaved!");

        return modelA;
    }
}
