package question;
import  java.util.Scanner;

class QuestionMultiplaEscolha extends Question {
    private int indexCorreta;

    public QuestionMultiplaEscolha(String enunciado, String[] alternativas,
                                   int indexCorreta, int pontos, String tema) {
        super(enunciado, alternativas, pontos, tema);
        this.indexCorreta = indexCorreta;
    }

    @Override
    public int responderPergunta(Scanner scanner) {
        System.out.print("  Sua resposta: ");
        int resposta = -1;
        try {
            resposta = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
        }
        if (resposta == indexCorreta) {
            System.out.println("  CORRETO! +" + getPontos() + " pts de dano bonus.");
            return getPontos();
        } else {
            System.out.println("  ERRADO. Resposta certa: " + getAlternativas()[indexCorreta - 1]);
            return 0;
        }
    }
}