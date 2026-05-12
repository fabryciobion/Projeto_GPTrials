public abstract class Character {
    protected String nome;
    protected int vida;
    protected int dano;

    public Character(String nome, int vida, int dano) {
        this.nome = nome;
        this.vida = vida;
        this.dano = dano;
    }

    public String getNome() { return nome; }
    public int    getVida() { return vida; }
    public int    getDano() { return dano; }

    public void atacar(Character alvo) {
        System.out.println(this.nome + " ataca " + alvo.getNome()
                + " causando " + this.dano + " de dano.");
        alvo.vida -= this.dano;
        if (alvo.vida < 0) alvo.vida = 0;
    }

    public abstract void habilidadeEspecial(Character alvo);
}
//
