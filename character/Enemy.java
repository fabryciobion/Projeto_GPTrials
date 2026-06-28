package character;

public class Enemy extends Character {

    private final int vidaMaxima;

    public Enemy(String nome, int vida, int dano) {
        super(nome, vida, dano);
        this.vidaMaxima = vida;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }
    public static Enemy criarAndroid() {
        return new Enemy("Android", 80, 15);
    }
    public static Enemy criarSuperAndroid() {
        return new Enemy("Super Android", 200, 35);
    }
    public static Enemy criarGPTBoss() {
        return new Enemy("ChatGPT-Boss", 999, 100);
    }
}