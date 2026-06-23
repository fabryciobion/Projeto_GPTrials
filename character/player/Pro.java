package character.player;
import  character.Character;

public class Pro extends ClassePlayer {
        public Pro(String nome) {
        super(nome, 100, 50);
    }

    @Override
    public void habilidadeEspecial(Character alvo) {
        alvo.vida -= this.dano * 3;
        if (alvo.vida < 0) alvo.vida = 0;
    }
}
