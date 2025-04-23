package bankapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SecurityQuestionTest {

    @Test
    public void testQuestionCreationAndRetrieval() {
        SecurityQuestion sq = new SecurityQuestion("What is your favorite color?", "blue");
        assertEquals("What is your favorite color?", sq.getQuestion());
    }

    @Test
    public void testExactMatchAnswerVerification() {
        SecurityQuestion sq = new SecurityQuestion("Pet's name?", "fluffy");
        assertTrue(sq.verifyAnswer("fluffy"));
    }

    @Test
    public void testCaseInsensitiveVerification() {
        SecurityQuestion sq = new SecurityQuestion("Mother's maiden name?", "smith");
        assertTrue(sq.verifyAnswer("SMITH"));
        assertTrue(sq.verifyAnswer("Smith"));
        assertTrue(sq.verifyAnswer("sMiTh"));
    }


}