package question;
import  java.util.Scanner;

class QuestionVerdadeiroFalso extends Question {
    private String respostaCorreta;

    public QuestionVerdadeiroFalso(String enunciado, String respostaCorreta,
                                   int pontos, String tema) {
        super(enunciado, new String[]{"Verdadeiro", "Falso"}, pontos, tema);
        this.respostaCorreta = respostaCorreta.trim();
    }

    @Override
    public int responderPergunta(Scanner scanner) {
        System.out.print("  Sua resposta (1-Verdadeiro / 2-Falso): ");
        int escolha = -1;
        try {
            escolha = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) { }

        String resp = (escolha == 1) ? "Verdadeiro" : (escolha == 2) ? "Falso" : "";
        if (resp.equalsIgnoreCase(respostaCorreta)) {
            System.out.println("  CORRETO! +" + getPontos() + " pts de dano bonus.");
            return getPontos();
        } else {
            System.out.println("  ERRADO. Resposta certa: " + respostaCorreta);
            return 0;
        }
    }
}
