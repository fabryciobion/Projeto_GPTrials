package character.player;
import  character.Character;

public class Noob extends ClassePlayer {
    public Noob(String nome) {
        super(nome, 80, 15);
    }

    @Override
    public void habilidadeEspecial(Character alvo) {
        alvo.vida -= this.dano * 5;
        if (alvo.vida < 0) alvo.vida = 0;
    }
}

