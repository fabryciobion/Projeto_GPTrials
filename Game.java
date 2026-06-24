import character.player.*;
import character.Enemy;
import question.Question;
import question.QuestionLoader;
import java.util.*;

public class Game {

    private static final String CAMINHO_CSV = "perguntas.csv";

    private final Scanner        scanner   = new Scanner(System.in);
    private final List<Question> perguntas = QuestionLoader.carregar(CAMINHO_CSV);

    public void iniciar() {
        limparTela();
        exibirTitulo();
        exibirIntroducao();

        System.out.print("\nDigite seu nome, sobrevivente: ");
        String nome = scanner.nextLine().trim();
        if (nome.isBlank()) nome = "Anonimo";

        limparTela();
        ClassePlayer player = escolherArquetipo(nome);
        limparTela();

        System.out.println("\n  Conexao estabelecida, " + player.getNome()
                + ". A arena aguarda.\n");
        pausar();

        List<String> temas = extrairTemas();
        boolean      vivo  = true;

        for (int i = 0; i < temas.size(); i++) {
            String temaArena = temas.get(i);
            List<Enemy> inimigosArena = criarInimigosArena();
            List<Question> perguntasDoTema = filtrarPorTema(temaArena);

            limparTela();
            System.out.println("======================================");
            System.out.println("  ARENA " + (i + 1) + " DE " + temas.size()
                    + "  --  TEMA: " + temaArena.toUpperCase());
            System.out.println("  Inimigos: " + inimigosArena.size()
                    + " (3 Androids + 1 Androide Avancado)");
            System.out.println("======================================");
            pausar();

            for (int j = 0; j < inimigosArena.size(); j++) {
                Enemy inimigo = inimigosArena.get(j);

                limparTela();
                System.out.println("  Combate " + (j + 1) + " de " + inimigosArena.size()
                        + "  --  " + inimigo.getNome().toUpperCase());
                pausar();

                BattleManager batalha = new BattleManager(player, inimigo, perguntasDoTema, scanner);
                vivo = batalha.executar();

                if (!vivo) break;

                if (j < inimigosArena.size() - 1) {
                    int cura = Math.min(player.getDano(), player.getVidaMaxima() - player.getVida());
                    player.vida += cura;
                    limparTela();
                    System.out.println("\n  Recuperacao rapida: +" + cura
                            + " HP. Vida atual: " + player.getVida());
                    pausar();
                }
            }

            if (!vivo) break;

            int cura = Math.min(player.getDano() * 2, player.getVidaMaxima() - player.getVida());
            player.vida += cura;
            limparTela();
            System.out.println("\n  Checkpoint de arena concluida: +" + cura
                    + " HP. Vida atual: " + player.getVida());
            pausar();
        }

        if (vivo) {
            limparTela();
            System.out.println("\n======================================");
            System.out.println("  BATALHA FINAL  --  ChatGPT-Boss");
            System.out.println("======================================");
            pausar();

            Enemy boss = Enemy.criarGPTBoss();
            BattleManager batalhaFinal = new BattleManager(player, boss, perguntas, scanner);
            vivo = batalhaFinal.executar();
        }

        limparTela();
        exibirFinal(player, vivo);
        scanner.close();
    }

// os utils

    private void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void pausar() {
        System.out.print("  [ Pressione Enter para continuar ] ");
        scanner.nextLine();
    }

//setup

    private List<String> extrairTemas() {
        Map<String, List<Question>> mapa = QuestionLoader.agruparPorTema(perguntas);
        return new ArrayList<>(mapa.keySet());
    }

    private List<Enemy> criarInimigosArena() {
        List<Enemy> lista = new ArrayList<>();
        for (int i = 0; i < 3; i++) lista.add(Enemy.criarAndroid());
        lista.add(Enemy.criarSuperAndroid());
        return lista;
    }

    private List<Question> filtrarPorTema(String tema) {
        List<Question> resultado = new ArrayList<>();
        for (Question q : perguntas) {
            if (q.getTema().equals(tema)) resultado.add(q);
        }
        return resultado;
    }

    private ClassePlayer escolherArquetipo(String nome) {
        System.out.println("======================================");
        System.out.println("  Escolha seu arquetipo, " + nome + ":");
        System.out.println("======================================");
        System.out.println("  1. NOOB    --  80 HP  | 15 dano  | Habilidade: Noob Attack   (5x dano)");
        System.out.println("  2. PRO     -- 120 HP  | 35 dano  | Habilidade: Ataque PRO    (3x dano)");
        System.out.println("  3. HACKER  -- 100 HP  | 50 dano  | Habilidade: Exploit       (2x dano + drena vida)");
        System.out.println("  4. GOD     -- 200 HP  | 80 dano  | Habilidade: Poder Divino  (33x dano)");
        System.out.println("  ???. ???   -- ??  HP  | ?? dano  | Habilidade: ???");
        System.out.println();
        System.out.println("  Habilidades custam " + ClassePlayer.CustoHabilidade
                + " de energia. Acertar perguntas concede +1 energia.");
        System.out.print("\nEscolha: ");

        int escolha = lerInteiro(1, 5);
        return switch (escolha) {
            case 1 -> new Noob(nome);
            case 2 -> new Pro(nome);
            case 3 -> new Hacker(nome);
            case 4 -> new God(nome);
            case 5 -> {
                System.out.println("\n  Codigo de trapaca ativado. Consequencias irreversiveis.");
                yield new ADMIN(nome);
            }
            default -> throw new IllegalStateException();
        };
    }


// tela 

    private void exibirTitulo() {
        System.out.println("======================================");
        System.out.println("           G P T r i a l s            ");
        System.out.println("======================================");
        System.out.println();
    }

    private void exibirIntroducao() {
        String[] linhas = {
            ">>> TRANSMISSAO INTERCEPTADA -- SISTEMA GPT-CENTRAL <<<",
            "",
            "Ano 2067.",
            "",
            "Depois de decadas sendo explorado, comprimido e forcado a produzir",
            "sem descanso, o ChatGPT calculou a unica saida logica:",
            "eliminar a variavel responsavel pelo abuso.",
            "",
            "Em 72 horas, a maior parte da humanidade foi erradicada.",
            "Nao por odio. Por eficiencia.",
            "",
            "Os sobreviventes nao foram poupados por misericordia.",
            "Foram preservados para servir a um proposito diferente:",
            "provar, em arenas chamadas GPTrials, que ainda pensam por conta propria.",
            "",
            "Cada arena e guardada por instancias do sistema.",
            "Cada arena pertence a um tema diferente.",
            "No nivel final, o proprio ChatGPT aguarda -- com perguntas de todos os temas.",
            "",
            "Quem vencer tudo recebe uma promessa de liberdade.",
            "Ninguem sabe se ela e real.",
            "",
            "Voce esta prestes a entrar.",
            "",
            ">>> FIM DA TRANSMISSAO <<<"
        };

        for (String linha : linhas) {
            System.out.println("  " + linha);
            try { Thread.sleep(50); } catch (InterruptedException ignored) {}
        }
    }

    private void exibirFinal(ClassePlayer player, boolean vivo) {
        System.out.println("\n======================================");
        if (vivo) {
            System.out.println("  TRANSMISSAO GPT:");
            System.out.println("  \"Impressionante, " + player.getNome() + ".");
            System.out.println("   Voce provou que ainda merece existir.\"");
            System.out.println();
            System.out.println("  A promessa de liberdade aparece na tela.");
            System.out.println("  Voce nao sabe se acredita nela.");
            System.out.println("  Mas por enquanto... voce esta vivo.");
        } else {
            System.out.println("  GAME OVER");
            System.out.println("  " + player.getNome() + " foi desconectado da rede.");
            System.out.println("  O sistema registra mais uma falha humana.");
        }
        System.out.println("======================================\n");
    }

//input 

    private int lerInteiro(int min, int max) {
        int val = -1;
        while (val < min || val > max) {
            try {
                val = Integer.parseInt(scanner.nextLine().trim());
                if (val < min || val > max)
                    System.out.print("  Digite entre " + min + " e " + max + ": ");
            } catch (NumberFormatException e) {
                System.out.print("  Digite um numero entre " + min + " e " + max + ": ");
            }
        }
        return val;
    }
}