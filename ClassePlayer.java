abstract class ClassePlayer extends Character {

    public static final int CustoHabilidade = 3;

    protected int energia;
    protected int vidaMaxima;

//constructor
    public ClassePlayer(String nome, int vida, int dano) {
        super(nome, vida, dano);
        this.vidaMaxima = vida;
        this.energia = 0;
    }

//getters
    public int  getEnergia() {
        return energia;
    }
    public int  getVidaMaxima() {
        return vidaMaxima;
    }

//metodos 
    public void ganharEnergia(int quantidade) {
        energia += quantidade;
    }
    public boolean gastarEnergia(int quantidade) {
        if (energia < quantidade) return false;
        energia -= quantidade;
        return true;
    }
}

//opcoes pro jogador
class Noob extends ClassePlayer {
    public Noob(String nome) {
        super(nome, 50, 10);
    }

    @Override
    public void habilidadeEspecial(Character alvo) {
        alvo.vida -= this.dano * 5;
        if (alvo.vida < 0) alvo.vida = 0;
    }
}

class Pro extends ClassePlayer {
    public Pro(String nome) {
        super(nome, 40, 40);
    }

    @Override
    public void habilidadeEspecial(Character alvo) {
        alvo.vida -= this.dano * 3;
        if (alvo.vida < 0) alvo.vida = 0;
    }
}

class Hacker extends ClassePlayer {
    public Hacker(String nome) {
        super(nome, 30, 45);
    }

    @Override
    public void habilidadeEspecial(Character alvo) {
        int danoBase = this.dano * 2;
        alvo.vida -= danoBase;
        if (alvo.vida < 0) alvo.vida = 0;
        this.vida = Math.min(this.vida + (this.dano / 2), this.vidaMaxima);
    }
}

class God extends ClassePlayer {
    public God(String nome) {
        super(nome, 20, 50);
    }

    @Override
    public void habilidadeEspecial(Character alvo) {
        alvo.vida -= this.dano * 33;
        if (alvo.vida < 0) alvo.vida = 0;
    }
}

class ADMIN extends ClassePlayer {
    public ADMIN(String nome) {
        super(nome, 10, 55);
    }

    @Override
    public void habilidadeEspecial(Character alvo) {
        alvo.vida = 0;
    }
}//