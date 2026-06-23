package character.player;
import  character.Character;

public class ADMIN extends ClassePlayer {
    public ADMIN(String nome) {
        super(nome, 1000000, 99999);
    }

    @Override
    public void habilidadeEspecial(Character alvo) {
        alvo.vida = 0;
    }
}//
