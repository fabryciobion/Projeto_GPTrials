import character.player.ClassePlayer;
import character.Enemy;
import question.Question;
import question.QuestionLoader;
import java.util.*;

public class BattleManager {

    private static final int CURA_DESCANSO = 15;

    private final ClassePlayer               player;
    private final Enemy                      inimigo;
    private final Map<String, List<Question>> perguntasPorTema;
    private final List<String>               temas;
    private final Scanner                    scanner;
    private int                              temaAtualIdx = 0;
    private String                           ultimoEvento = "";

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
        ultimoEvento = "";

        System.out.println("\n==================================");
        System.out.printf("  BATALHA: %-10s vs %-10s%n", player.getNome(), inimigo.getNome());
        System.out.println("==================================");
        pausarBreve();

        int turno = 1;
        while (player.getVida() > 0 && inimigo.getVida() > 0) {
            limparTela();

            System.out.println("==================================");
            System.out.printf("  BATALHA: %-10s vs %-10s%n", player.getNome(), inimigo.getNome());
            System.out.println("==================================");

            if (!ultimoEvento.isEmpty()) {
                System.out.println("\n  >> " + ultimoEvento);
                System.out.println("  ----------------------------------");
            }

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
                    ultimoEvento = "Resposta errada! " + player.getNome()
                            + " fica exposto ao ataque!";
                    System.out.println("\n  " + ultimoEvento);
                    acao = -1;
                }
            }

            if (inimigo.getVida() > 0 && (acao == -1 || acao == 3)) {
                boolean desviou = (acao == 3) && (Math.random() < 0.5);
                contrataqueInimigo(desviou);
            }

            if (player.getVida() > 0 && inimigo.getVida() > 0) {
                System.out.print("\n  [ Pressione Enter para o proximo turno ] ");
                scanner.nextLine();
            }

            turno++;
        }

        limparTela();
        exibirResultadoBatalha();

        return player.getVida() > 0;
    }


// tela 

    private void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void pausarBreve() {
        try { Thread.sleep(800); } catch (InterruptedException ignored) {}
    }


    private void exibirStatus() {
        int hpPlayer  = player.getVida();
        int hpMax     = player.getVidaMaxima();
        int hpInimigo = inimigo.getVida();

        System.out.printf("%n  HP  %-12s %s (%d)%n",
                player.getNome(),  barraVida(hpPlayer, hpMax), hpPlayer);
        System.out.printf("  HP  %-12s %s (%d)%n",
                inimigo.getNome(), barraVida(hpInimigo, 999),  hpInimigo);
        System.out.printf("  Energia: %d%n", player.getEnergia());
    }

    private String barraVida(int atual, int maximo) {
        int total   = 20;
        int cheios  = (maximo <= 0) ? 0 : Math.max(0, (int)((double) atual / maximo * total));
        return "[" + "#".repeat(cheios) + ".".repeat(total - cheios) + "]";
    }

    private void exibirResultadoBatalha() {
        System.out.println("==================================");
        if (player.getVida() > 0) {
            System.out.println("  VITORIA! " + inimigo.getNome() + " foi eliminado da rede!");
        } else {
            System.out.println("  DERROTA! " + player.getNome() + " foi desconectado...");
        }
        System.out.println("==================================");
        pausarBreve();
    }



//combate

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
                + " HP; inimigo contra-ataca com 50% de desvio; sem pergunta)");
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
        ultimoEvento = player.getNome() + " descansou e recuperou " + cura + " HP.";
        System.out.println("\n  " + ultimoEvento);
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
            ultimoEvento = player.getNome() + " atacou " + inimigo.getNome()
                    + " por " + player.getDano() + " (base)"
                    + (danoBonus > 0 ? " + " + danoBonus + " (bonus quiz)" : "")
                    + " = " + danoTotal + " de dano!";
            System.out.println("  " + ultimoEvento);
            inimigo.vida -= danoTotal;
            if (inimigo.vida < 0) inimigo.vida = 0;
        } else {
            player.gastarEnergia(ClassePlayer.CustoHabilidade);
            player.habilidadeEspecial(inimigo);
            ultimoEvento = player.getNome() + " usou Habilidade Especial em " + inimigo.getNome() + "!";
            if (danoBonus > 0) {
                ultimoEvento += " + " + danoBonus + " de dano bonus do quiz!";
                System.out.println("  + " + danoBonus + " de dano bonus do quiz!");
                inimigo.vida -= danoBonus;
                if (inimigo.vida < 0) inimigo.vida = 0;
            }
            System.out.println("  " + ultimoEvento);
        }

        if (inimigo.getVida() == 0) {
            System.out.println("\n  " + inimigo.getNome() + " foi eliminado da rede!");
        }
    }

    private void contrataqueInimigo(boolean desviou) {
        if (desviou) {
            ultimoEvento = player.getNome() + " desviou do contra-ataque de " + inimigo.getNome() + "!";
            System.out.println("\n  " + ultimoEvento);
            return;
        }
        ultimoEvento = inimigo.getNome() + " contra-atacou " + player.getNome()
                + " causando " + inimigo.getDano() + " de dano!";
        System.out.println("\n  " + ultimoEvento);
        player.vida -= inimigo.getDano();
        if (player.vida < 0) player.vida = 0;

        if (player.getVida() == 0) {
            System.out.println("\n  " + player.getNome() + " foi desconectado...");
        }
    }
}