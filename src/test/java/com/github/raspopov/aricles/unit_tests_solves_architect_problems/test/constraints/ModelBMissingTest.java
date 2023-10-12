package com.github.raspopov.aricles.unit_tests_solves_architect_problems.test.constraints;

import com.github.raspopov.aricles.unit_tests_solves_architect_problems.constraints.ModelBMissing;
import com.github.raspopov.aricles.unit_tests_solves_architect_problems.exception.MissingParameterException;
import com.github.raspopov.aricles.unit_tests_solves_architect_problems.model.ModelA;
import com.github.raspopov.aricles.unit_tests_solves_architect_problems.model.ModelB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ModelBMissingTest {

    ModelBMissing modelBMissing;

    @BeforeEach
    void setUp() {
        modelBMissing = new ModelBMissing();
    }

    @Test
    public void accept_ModelBSet_NoExceptionThrow() {
        // GIVEN
        ModelA modelA = new ModelA(new ModelB(), null);

        // WHEN
        // THEN
        assertDoesNotThrow(() -> modelBMissing.accept(modelA));
    }

    @Test
    public void accept_ModelBNotSet_ThrowsMissingParameterException() {
        // GIVEN
        ModelA modelA = new ModelA(null, null);

        // WHEN
        // THEN
        assertThrows(MissingParameterException.class, () -> modelBMissing.accept(modelA));
    }
}
