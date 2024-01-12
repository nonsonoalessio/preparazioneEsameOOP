package alessiodonatogiura;

import java.util.Random;

/**
 * La classe NumericQuestion crea la rappresentazione di un quiz composto da due interi e un operatore, di addizione o sottrazione, generati casualmente con la classe {@link Random}.
 * @see Random
 * @author Giura Alessio Donato
 */
public class NumericQuestion {
    int num1;
    int num2;
    char operator;

    /**
     * Il costruttore di default consente di inizializzare l'oggetto con operatore e operandi random.
     * @see Random
     */
    public NumericQuestion() {
        this.randomInt();
    }

    /**
     * Consente di ottenere il valore del primo operando.
     * @return il valore del primo operando.
     */
    public int getNum1() {
        return num1;
    }

    /**
     * Consente di ottenere il valore del secondo operando.
     * @return il valore del secondo operando.
     */
    public int getNum2() {
        return num2;
    }

    /**
     * Consente di ottenere il valore dell'operatore.
     * @return il valore dell'operatore.
     */
    public char getOperator() {
        return operator;
    }

    /**
     * Inizializza l'oggetto con due interi casuali, generati mediante {@link Random#nextInt()}.
     * Inizializza anche l'operatore: in particolare, genera un terzo numero casuale e, se questo è pari, l'operatore sarà di somma, altrimenti sarà di sottrazione.
     */
    public void randomInt(){
        Random initializer = new Random();
        num1 = initializer.nextInt(50);
        num2 = initializer.nextInt(50);
        if(initializer.nextInt() % 2 == 0)
            operator = '+';
        else
            operator = '-';
    }

    /**
     * Consente di ottenere il risultato dell'operazione.
     * @return il risultato dell'operazione.
     */
    public int getResult(){
        return operator == '+' ? num1 + num2 : num1 - num2;
    }

    @Override
    public String toString() {
        return num1 + Character.toString(operator) + num2 + "= ?";
    }
}
