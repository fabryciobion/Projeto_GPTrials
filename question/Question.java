package question;
import  java.util.Scanner;

public abstract class Question {
    private String   enunciado;
    private String[] alternativas;
    private int      pontos;
    private String   tema;

    public Question(String enunciado, String[] alternativas, int pontos, String tema) {
        this.enunciado    = enunciado;
        this.alternativas = alternativas;
        this.pontos       = pontos;
        this.tema         = tema;
    }

    public String   getEnunciado()    { return enunciado; }
    public String[] getAlternativas() { return alternativas; }
    public int      getPontos()       { return pontos; }
    public String   getTema()         { return tema; }

    public void exibirPergunta() {
        System.out.println("\n  Tema: [" + tema + "]");
        System.out.println("  " + enunciado);
        for (int i = 0; i < alternativas.length; i++) {
            System.out.println("    " + (i + 1) + ". " + alternativas[i]);
        }
    }

    public abstract int responderPergunta(Scanner scanner);
}
