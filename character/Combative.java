package character;

public interface Combative {
    void atacar(Combative alvo);
    void receberDano(int valor);
    boolean estarVivo();
    String getNome();
    int getVida();
    int getDano();
}