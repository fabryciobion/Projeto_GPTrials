abstract class ClassePlayer extends Character {

    public static final int CUSTO_HABILIDADE = 3;

    protected int energia;
    protected int vidaMaxima;

    public ClassePlayer(String nome, int vida, int dano) {
        super(nome, vida, dano);
        this.vidaMaxima = vida;
        this.energia    = 0;
    }

    public int  getEnergia()    { return energia; }
    public int  getVidaMaxima() { return vidaMaxima; }

    public void ganharEnergia(int quantidade) {
        energia += quantidade;
        System.out.println("  +" + quantidade + " energia! Total: " + energia);
    }

    public boolean gastarEnergia(int quantidade) {
        if (energia < quantidade) return false;
        energia -= quantidade;
        return true;
    }
}

class Noob extends ClassePlayer {
    public Noob(String nome) {
        super(nome, 80, 15);
    }

    @Override
    public void habilidadeEspecial(Character alvo) {
        System.out.println(nome + " usa NOOB ATTACK em " + alvo.getNome() + "!");
        System.out.println("  Atacou " + alvo.getNome() + " 5 vezes!");
        alvo.vida -= this.dano * 5;
        if (alvo.vida < 0) alvo.vida = 0;
    }
}

class Pro extends ClassePlayer {
    public Pro(String nome) {
        super(nome, 120, 35);
    }

    @Override
    public void habilidadeEspecial(Character alvo) {
        System.out.println(nome + " executa Ataque PRO em " + alvo.getNome() + "!");
        System.out.println("  Golpe causa 3x dano!");
        alvo.vida -= this.dano * 3;
        if (alvo.vida < 0) alvo.vida = 0;
    }
}

class Hacker extends ClassePlayer {
    public Hacker(String nome) {
        super(nome, 100, 50);
    }

    @Override
    public void habilidadeEspecial(Character alvo) {
        System.out.println(nome + " injeta EXPLOIT em " + alvo.getNome() + "!");
        System.out.println("  Dano dobrado e drena vida!");
        int danoBase = this.dano * 2;
        alvo.vida -= danoBase;
        if (alvo.vida < 0) alvo.vida = 0;
        this.vida = Math.min(this.vida + (this.dano / 2), this.vidaMaxima);
        System.out.println("  " + nome + " causa 100 de dano e absorveu " + (this.dano / 2) + " de vida. Vida atual: " + this.vida);
    }
}

class God extends ClassePlayer {
    public God(String nome) {
        super(nome, 200, 80);
    }

    @Override
    public void habilidadeEspecial(Character alvo) {
        System.out.println(nome + " invoca Poder Divino em " + alvo.getNome() + "!");
        System.out.println("  Poder absoluto, 33x de dano!");
        alvo.vida -= this.dano * 33;
        if (alvo.vida < 0) alvo.vida = 0;
    }
}

class ADMIN extends ClassePlayer {
    public ADMIN(String nome) {
        super(nome, 1000000, 99999);
    }

    @Override
    public void habilidadeEspecial(Character alvo) {
        System.out.println(nome + " ativa instakill em " + alvo.getNome() + "!");
        System.out.println("  HAHAHAHA instakill");
        alvo.vida = 0;
    }
}
