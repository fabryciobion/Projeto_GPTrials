import java.util.*;

public class BattleManager {

    private static final int CURA_DESCANSO = 15;

    private final ClassePlayer               player;
    private final Enemy                      inimigo;
    private final Map<String, List<Question>> perguntasPorTema;
    private final List<String>               temas;
    private final Scanner                    scanner;
    private int                              temaAtualIdx = 0;

    public BattleManager(ClassePlayer player, Enemy inimigo,
                         List<Question> perguntas, Scanner scanner) {
        this.player           = player;
        this.inimigo          = inimigo;
        this.scanner          = scanner;
        this.perguntasPorTema = QuestionLoader.agruparPorTema(perguntas);
        this.temas            = new ArrayList<>(perguntasPorTema.keySet());
        Collections.shuffle(temas);
    }

    public boolean executar() {
        System.out.println("\n==================================");
        System.out.printf("  BATALHA: %-10s vs %-10s%n", player.getNome(), inimigo.getNome());
        System.out.println("==================================");

        int turno = 1;
        while (player.getVida() > 0 && inimigo.getVida() > 0) {
            System.out.println("\n--- Turno " + turno + " ---");
            exibirStatus();

            int acao = escolherAcao();

            if (acao == 3) {
                executarDescanso();
            } else {
                Question pergunta = sortearPergunta();
                pergunta.exibirPergunta();
                int danoBonus = pergunta.responderPergunta(scanner);

                if (danoBonus > 0) {
                    player.ganharEnergia(1);
                    aplicarAcaoPlayer(acao, danoBonus);
                } else {
                    System.out.println("\n  Resposta errada! " + player.getNome()
                            + " nao consegue agir e fica exposto ao ataque!");
                    acao = -1;
                }
            }

            if (inimigo.getVida() > 0) {
                boolean desviou = (acao == 3) && (Math.random() < 0.5);
                contrataqueInimigo(desviou);
            }

            turno++;
        }

        return player.getVida() > 0;
    }

    private void exibirStatus() {
        System.out.printf("  HP %s: %d  |  Energia: %d  |  HP %s: %d%n",
                player.getNome(),  player.getVida(),
                player.getEnergia(),
                inimigo.getNome(), inimigo.getVida());
    }

    private int escolherAcao() {
        System.out.println("\nO que voce quer fazer?");
        System.out.println("  1. Atacar             (dano base: " + player.getDano() + ")");

        boolean temEnergia = player.getEnergia() >= ClassePlayer.CustoHabilidade;
        if (temEnergia) {
            System.out.println("  2. Habilidade Especial  (custo: "
                    + ClassePlayer.CustoHabilidade + " energia)");
        } else {
            System.out.println("  2. Habilidade Especial  [BLOQUEADA - precisa de "
                    + ClassePlayer.CustoHabilidade + " energia, voce tem "
                    + player.getEnergia() + "]");
        }
        System.out.println("  3. Descansar/Desviar   (recupera " + CURA_DESCANSO
                + " HP; 50% chance de desviar contra-ataque; sem pergunta)");
        System.out.print("Escolha: ");

        int escolha = -1;
        while (true) {
            try {
                escolha = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) { escolha = -1; }

            if (escolha == 1 || escolha == 3) break;
            if (escolha == 2 && temEnergia)   break;
            if (escolha == 2 && !temEnergia) {
                System.out.print("  Energia insuficiente! Escolha 1 ou 3: ");
            } else {
                System.out.print("  Digite 1, 2 ou 3: ");
            }
        }
        return escolha;
    }

    private void executarDescanso() {
        int cura = Math.min(CURA_DESCANSO, player.getVidaMaxima() - player.getVida());
        player.vida += cura;
        System.out.println("\n  " + player.getNome() + " descansa e recupera " + cura
                + " HP. Vida atual: " + player.getVida());
    }

    private Question sortearPergunta() {
        int tentativas = 0;
        while (tentativas < temas.size()) {
            String tema = temas.get(temaAtualIdx % temas.size());
            List<Question> pool = perguntasPorTema.get(tema);
            if (pool != null && !pool.isEmpty()) {
                Question q = pool.get((int)(Math.random() * pool.size()));
                temaAtualIdx++;
                System.out.println("\n  TRANSMISSAO GPT - Pergunta do Quiz:");
                return q;
            }
            temaAtualIdx++;
            tentativas++;
        }
        for (List<Question> pool : perguntasPorTema.values()) {
            if (!pool.isEmpty()) {
                System.out.println("\n  TRANSMISSAO GPT - Pergunta do Quiz:");
                return pool.get((int)(Math.random() * pool.size()));
            }
        }
        throw new IllegalStateException("Nenhuma pergunta disponivel!");
    }

    private void aplicarAcaoPlayer(int acao, int danoBonus) {
        System.out.println();
        if (acao == 1) {
            int danoTotal = player.getDano() + danoBonus;
            System.out.println("  " + player.getNome() + " ataca " + inimigo.getNome()
                    + " -> " + player.getDano() + " (base)"
                    + (danoBonus > 0 ? " + " + danoBonus + " (bonus quiz)" : "")
                    + " = " + danoTotal + " de dano!");
            inimigo.vida -= danoTotal;
            if (inimigo.vida < 0) inimigo.vida = 0;
        } else {
            player.gastarEnergia(ClassePlayer.CustoHabilidade);
            player.habilidadeEspecial(inimigo);
            if (danoBonus > 0) {
                System.out.println("  + " + danoBonus + " de dano bonus do quiz!");
                inimigo.vida -= danoBonus;
                if (inimigo.vida < 0) inimigo.vida = 0;
            }
        }

        if (inimigo.getVida() == 0) {
            System.out.println("\n  " + inimigo.getNome() + " foi eliminado da rede!");
        }
    }

    private void contrataqueInimigo(boolean desviou) {
        if (desviou) {
            System.out.println("\n  " + player.getNome()
                    + " desviou do contra-ataque de " + inimigo.getNome() + "!");
            return;
        }
        System.out.println("\n  " + inimigo.getNome() + " contra-ataca "
                + player.getNome() + " causando " + inimigo.getDano() + " de dano!");
        player.vida -= inimigo.getDano();
        if (player.vida < 0) player.vida = 0;

        if (player.getVida() == 0) {
            System.out.println("\n  " + player.getNome() + " foi desconectado...");
        }
    }
}