package sac.generators;

import org.junit.jupiter.api.Test;
import sac.model.generators.PerfectGenerator;

public class TestPerfectGenerator {
    @Test
    void testPerfectGenerator() {
        PerfectGenerator generator = new PerfectGenerator();
        for (int i = 0; i < 100; i++) {
            generator.nextPiece();
        }
        generator.reset();
        for (int i = 0; i < 100; i++) {
            generator.nextPiece();
        }
    }
}
