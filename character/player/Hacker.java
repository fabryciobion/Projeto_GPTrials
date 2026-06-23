package character.player;
import  character.Character;

public class Hacker extends ClassePlayer {
    public Hacker(String nome) {
        super(nome, 100, 50);
    }

    @Override
    public void habilidadeEspecial(Character alvo) {
        int danoBase = this.dano * 2;
        alvo.vida -= danoBase;
        if (alvo.vida < 0) alvo.vida = 0;
        this.vida = Math.min(this.vida + (this.dano / 2), this.vidaMaxima);
    }
}
