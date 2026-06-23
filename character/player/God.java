package character.player;
import  character.Character;

public class God extends ClassePlayer {
    public God(String nome) {
        super(nome, 200, 80);
    }

    @Override
    public void habilidadeEspecial(Character alvo) {
        int novaVida = alvo.getVida() - this.dano * 33;
        if (novaVida < 0) novaVida = 0;
        alvo.setVida(novaVida);
    }
}