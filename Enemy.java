class Enemy extends Character {

    public Enemy(String nome, int vida, int dano) {
        super(nome, vida, dano);
    }

    public static Enemy criarAndroid() {
        return new Enemy("Android", 20, 5);
    }
    public static Enemy criarSuperAndroid() {
        return new Enemy("Super Android", 50, 15);
    }
    public static Enemy criarGPTBoss() {
        return new Enemy("ChatGPT-Boss", 100, 25);
    }

    @Override
    public void habilidadeEspecial(Character alvo) {
    }
}
//teste de balanceamento