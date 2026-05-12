class Enemy extends Character {

    public Enemy(String nome, int vida, int dano) {
        super(nome, vida, dano);
    }

    public static Enemy criarBotBasico()        { return new Enemy("Bot Basico",       80,  15); }
    public static Enemy criarAndroideAvancado() { return new Enemy("Androide Avancado", 200, 35); }
    public static Enemy criarGPTBoss()          { return new Enemy("ChatGPT-Boss",      999, 100); }

    @Override
    public void habilidadeEspecial(Character alvo) {
    }
}
