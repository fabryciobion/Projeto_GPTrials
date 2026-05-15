import java.util.Scanner;

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
        } else {
            System.out.println("  ERRADO. Resposta certa: " + respostaCorreta);
            return 0;
        }
    }
}