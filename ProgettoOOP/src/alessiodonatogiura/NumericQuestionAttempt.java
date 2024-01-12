package alessiodonatogiura;

public class NumericQuestionAttempt {
    private final NumericQuestion question;
    private final int givenAnswer;
    private final String displayableAttempt;
    private final String correct;

    public NumericQuestionAttempt(NumericQuestion question, int givenAnswer) {
        this.question = question;
        this.givenAnswer = givenAnswer;
        this.displayableAttempt = question.getNum1() + Character.toString(question.getOperator())+ question.getNum2() + "=" + question.getResult();
        this.correct = getResult();
    }

    public NumericQuestion getQuestion() {
        return question;
    }

    public int getGivenAnswer() {
        return givenAnswer;
    }

    public String getDisplayableAttempt() {
        return displayableAttempt;
    }

    public String getCorrect() {
        return correct;
    }


    public boolean isCorrect(){
        return question.getResult() == givenAnswer;
    }

    public String getResult(){
        return isCorrect() ? "Corretto" : "Sbagliato";
    }

    @Override
    public String toString() {
        return question.getNum1() + question.getOperator() + question.getNum2() + " = " + givenAnswer + ";" + question.getResult() + ";" + this.getResult();
    }
}
