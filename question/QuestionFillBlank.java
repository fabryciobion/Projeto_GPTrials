package question;
import  java.util.Scanner;


class QuestionFillBlank extends Question {
    private String respostaCorreta;

    public QuestionFillBlank(String enunciado, String respostaCorreta,
                             int pontos, String tema) {
        super(enunciado, new String[]{}, pontos, tema);
        this.respostaCorreta = respostaCorreta.trim();
    }

    @Override
    public void exibirPergunta() {
        System.out.println("\n  Tema: [" + getTema() + "]");
        System.out.println("  " + getEnunciado());
    }

    @Override
    public int responderPergunta(Scanner scanner) {
        System.out.print("  Sua resposta: ");
        String resposta = scanner.nextLine().trim();
        if (resposta.equalsIgnoreCase(respostaCorreta)) {
            System.out.println("  CORRETO! +" + getPontos() + " pts de dano bonus.");
            return getPontos();
        } 
        else {
            System.out.println("  ERRADO. Resposta certa: " + respostaCorreta);
            return 0;
        }
    }
}