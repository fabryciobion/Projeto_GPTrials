# Projeto_GPTrials
GPTrials é um jogo de RPG em turnos no terminal ambientado em 2067, onde o restante da humanidade é forçada a competir em arenas de quiz  para entreter o novo Rei do Mundo, o ChatGPT.

O jogador escolhe uma classe, enfrenta inimigos em arenas temáticas e
responde perguntas para causar dano extra. Quem vencer todas as arenas
e derrotar o boss final é libertado (talvez).

# Requesitos:
Precisa de JDK 17 ou superior instalado
Coloque o arquivo `perguntas.csv` na mesma pasta dos arquivos compilados

# Estrutura do Projeto
GPTrials/
├── main.java            # Start do jogo

├── Game.java            # Loop principal, arenas e fluxo do jogo

├── BattleManager.java   # Lógica de combate turno a turno

├── Character.java       # Classe base abstrata para personagens

├── ClassePlayer.java    # Classes jogaveis: Noob, Pro, Hacker, God, ADMIN(Classe teste)

├── Enemy.java           # Inimigos: Bot Basico, Androide Avançado, ChatGPT-Boss

├── Question.java        # Perguntas: Multipla Escolha, V/F, Completar a lacuna

├── QuestionLoader.java  # Leitura e agrupamento do CSV de perguntas

└── perguntas.csv        # Banco de perguntas

# feito pro fabrycio e vinicius
