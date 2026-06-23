package character.player;
import  character.Character;


public abstract class ClassePlayer extends Character {

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