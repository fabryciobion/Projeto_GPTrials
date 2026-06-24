public class GameStats {

    private int danoTotalCausado      = 0;
    private int vidaTotalRecuperada   = 0;
    private int perguntasRespondidas  = 0;
    private int perguntasCorretas     = 0;
    private int habilidadesUsadas     = 0;
    private int inimigosEliminados    = 0;
    private int turnosTotais          = 0;


    public void registrarDano(int dano) {
        danoTotalCausado += dano;
    }

    public void registrarCura(int cura) {
        vidaTotalRecuperada += cura;
    }

    public void registrarPergunta(boolean acertou) {
        perguntasRespondidas++;
        if (acertou) perguntasCorretas++;
    }

    public void registrarHabilidade() {
        habilidadesUsadas++;
    }

    public void registrarInimigo() {
        inimigosEliminados++;
    }

    public void registrarTurno() {
        turnosTotais++;
    }

    public void exibir() {
        int pct = perguntasRespondidas == 0 ? 0
                : (perguntasCorretas * 100) / perguntasRespondidas;

        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║         ESTATISTICAS DA PARTIDA      ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.printf( "║  Dano total causado    : %6d       ║%n", danoTotalCausado);
        System.out.printf( "║  Vida total recuperada : %6d       ║%n", vidaTotalRecuperada);
        System.out.printf( "║  Perguntas respondidas : %6d       ║%n", perguntasRespondidas);
        System.out.printf( "║  Perguntas corretas    : %6d (%3d%%) ║%n", perguntasCorretas, pct);
        System.out.printf( "║  Habilidades usadas    : %6d       ║%n", habilidadesUsadas);
        System.out.printf( "║  Inimigos eliminados   : %6d       ║%n", inimigosEliminados);
        System.out.printf( "║  Turnos jogados        : %6d       ║%n", turnosTotais);
        System.out.println("╚══════════════════════════════════════╝");
    }
}