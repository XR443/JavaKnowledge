package com.github.raspopov.aricles.unit_tests_solves_architect_problems.test.constraints;

import com.github.raspopov.aricles.unit_tests_solves_architect_problems.constraints.ModelCMissing;
import com.github.raspopov.aricles.unit_tests_solves_architect_problems.exception.MissingParameterException;
import com.github.raspopov.aricles.unit_tests_solves_architect_problems.model.ModelA;
import com.github.raspopov.aricles.unit_tests_solves_architect_problems.model.ModelC;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ModelCMissingTest {

    ModelCMissing modelCMissing;

    @BeforeEach
    void setUp() {
        modelCMissing = new ModelCMissing();
    }

    @Test
    public void accept_ModelCSet_NoExceptionThrow() {
        // GIVEN
        ModelA modelA = new ModelA(null, new ModelC());

        // WHEN
        // THEN
        assertDoesNotThrow(() -> modelCMissing.accept(modelA));
    }

    @Test
    public void accept_ModelCNotSet_ThrowsMissingParameterException() {
        // GIVEN
        ModelA modelA = new ModelA(null, null);

        // WHEN
        // THEN
        assertThrows(MissingParameterException.class, () -> modelCMissing.accept(modelA));
    }
}
