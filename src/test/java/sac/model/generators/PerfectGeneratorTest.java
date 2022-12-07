package sac.model.generators;

import org.junit.jupiter.api.Test;

public class PerfectGeneratorTest {
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
