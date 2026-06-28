# GPTrials

GPTrials é um jogo de RPG de turnos ambientado em 2067, onde os últimos sobreviventes da humanidade são forçados a competir em arenas de quiz para provar ao ChatGPT que ainda merecem existir.

O jogador escolhe uma classe, enfrenta inimigos em arenas temáticas e responde perguntas para causar dano extra. Quem vencer todas as arenas e derrotar o boss final é libertado — talvez.

---

## Requisitos

- JDK 17 ou superior
- Arquivo `perguntas.csv` na mesma pasta dos arquivos compilados (ou no diretório raiz do projeto)

---

## Como compilar e executar

```bash
# Compilar todos os arquivos
javac -d . Main.java Game.java BattleManager.java character/*.java character/player/*.java question/*.java

# Executar
java Main
```

---

## Estrutura do Projeto

```
GPTrials/
│
├── Main.java                          # Ponto de entrada — inicia o jogo
├── Game.java                          # Loop principal: arenas, fluxo e narrativa
├── BattleManager.java                 # Lógica de combate turno a turno
├── perguntas.csv                      # Banco de perguntas (CSV)
│
├── character/
│   ├── Character.java                 # Classe base abstrata para todos os personagens
│   ├── Enemy.java                     # Inimigos: Android, Super Android, ChatGPT-Boss
│   └── player/
│       ├── ClassePlayer.java          # Classe base abstrata para o jogador (energia, vida máxima)
│       ├── Noob.java                  # 80 HP | 15 dano | Habilidade: Noob Attack (5x dano)
│       ├── Pro.java                   # 120 HP | 35 dano | Habilidade: Ataque PRO (3x dano)
│       ├── Hacker.java                # 100 HP | 50 dano | Habilidade: Exploit (2x dano + drena vida)
│       ├── God.java                   # 200 HP | 80 dano | Habilidade: Poder Divino (33x dano)
│       └── ADMIN.java                 # Classe secreta — mata qualquer inimigo instantaneamente
│
└── question/
    ├── Question.java                  # Classe base abstrata para perguntas + subclasses internas
    ├── QuestionMultiplaEscolha.java   # Pergunta de múltipla escolha (4 alternativas)
    ├── QuestionVerdadeiroFalso.java   # Pergunta verdadeiro ou falso
    ├── QuestionFillBlank.java         # Pergunta de completar a lacuna
    └── QuestionLoader.java            # Leitura do CSV e agrupamento por tema
```

## Feito por

Vinícius e Fabrycio