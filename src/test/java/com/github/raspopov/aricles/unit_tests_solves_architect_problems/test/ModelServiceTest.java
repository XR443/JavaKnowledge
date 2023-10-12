package com.github.raspopov.aricles.unit_tests_solves_architect_problems.test;

import com.github.raspopov.aricles.unit_tests_solves_architect_problems.ModelService;
import com.github.raspopov.aricles.unit_tests_solves_architect_problems.exception.MissingParameterException;
import com.github.raspopov.aricles.unit_tests_solves_architect_problems.model.ModelA;
import com.github.raspopov.aricles.unit_tests_solves_architect_problems.model.ModelB;
import com.github.raspopov.aricles.unit_tests_solves_architect_problems.model.ModelC;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ModelServiceTest {

    ModelService modelService;
    @Mock
    Consumer<ModelA> constraint;

    @BeforeEach
    void setUp() {
        modelService = new ModelService(List.of(constraint));
    }

    @Test
    public void save_ChecksPassed_ModelASaved() {
        // GIVEN
        ModelA modelA = new ModelA(new ModelB(), new ModelC());

        // WHEN
        ModelA saved = modelService.save(modelA);

        // THEN
        assertEquals(modelA, saved);
    }

    @Test
    public void save_CheckNotPassed_ThrowsMissingParameterException() {
        // GIVEN
        ModelA modelA = new ModelA(null, new ModelC());

        Mockito.doThrow(MissingParameterException.class).when(constraint).accept(modelA);

        // WHEN
        // THEN
        assertThrows(MissingParameterException.class, () -> modelService.save(modelA));
    }
}
