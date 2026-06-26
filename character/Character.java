package character;

public abstract class Character implements Combative {
    protected String nome;
    public int vida;
    protected int dano;

//constructor
    public Character(String nome, int vida, int dano) {
        this.nome = nome;
        this.vida = vida;
        this.dano = dano;
    }

//getters
    public String getNome() {
        return nome;
    }
    public int    getVida() {
        return vida;
    }
    public int    getDano() {
        return dano;
    }

//setters
    public void setVida(int vida) {
        this.vida = vida;
    }
    public void setDano(int dano) {
        this.dano = dano;
    }

//metodos
    public void receberDano(int valor){
        vida -= valor;
        if (vida < 0){
            vida = 0;
        }
    }
    public void atacar(Combative alvo) {
        alvo.receberDano(dano);
    }
    public boolean estarVivo() {
        return vida > 0;
    }
}