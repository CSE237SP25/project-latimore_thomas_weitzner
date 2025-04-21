package bankapp;

public class SecurityQuestion {
    private String question;
    private String answer;
    
    public SecurityQuestion(String question, String answer) {
        this.question = question;
        this.answer = answer.toLowerCase(); 
    }
    
    public String getQuestion() {
        return question;
    }
    
    public boolean verifyAnswer(String userAnswer) {
        return answer.equals(userAnswer.toLowerCase());
    }
    
    public String getAnswer() {
        return answer;
    }
}