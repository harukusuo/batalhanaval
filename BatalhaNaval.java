import java.util.Random;
import java.util.Scanner;

public class BatalhaNaval {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // aq cria o mapa dos jogador
        Mapa mapaJogador1 = new Mapa();
        Mapa mapaJogador2 = new Mapa();

        // escolher pc vs usuario ou pc vs pc
        System.out.println("Deseja jogar contra outro jogador (J) ou contra o computador (C)?");
        char escolhaJogo = scanner.next().charAt(0);

        // jogador 1 escolher barco manual ou aleatorio
        System.out.println("Jogador 1: Deseja alocar os barcos de forma manual (M) ou aleatória (A)?");
        char escolhaAlocacao = scanner.next().charAt(0);

        if (escolhaAlocacao == 'M' || escolhaAlocacao == 'm') {
            alocaBarcos(mapaJogador1, scanner);
        } else if (escolhaAlocacao == 'A' || escolhaAlocacao == 'a') {
            alocaBarcosAleatoriamente(mapaJogador1);
        } else {
            // qualquer opcao q nao seja m/a
        }

        if (escolhaJogo == 'J' || escolhaJogo == 'j') {
            // jogador 2 escolher barco manual ou aleatorio
            System.out.println("Jogador 2: Deseja alocar os barcos de forma manual (M) ou aleatória (A)?");
            escolhaAlocacao = scanner.next().charAt(0);

            if (escolhaAlocacao == 'M' || escolhaAlocacao == 'm') {
                alocaBarcos(mapaJogador2, scanner);
            } else if (escolhaAlocacao == 'A' || escolhaAlocacao == 'a') {
                alocaBarcosAleatoriamente(mapaJogador2);
            } else {
                // qualquer opcao q nao seja m/a
            }
        } else if (escolhaJogo == 'C' || escolhaJogo == 'c') {
            // bota barco aleatorio pro pc
            alocaBarcosAleatoriamente(mapaJogador2);
        } else {
            // outra opcao
        }

        // loop do jogo
        while (!mapaJogador1.todosBarcosAfundados() && !mapaJogador2.todosBarcosAfundados()) {
            if (escolhaJogo == 'J' || escolhaJogo == 'j') {
                // jogadas do jogador 1
                System.out.println("\nJogador 1 - Seu Mapa:");
                mapaJogador1.mostrarMapa();
                System.out.println("\nJogador 1 - Mapa do Oponente:");
                mapaJogador2.mostrarMapaDoOponente();
                while (realizarAtaque(mapaJogador2, scanner)) {
                    System.out.println("\nJogador 1 - Seu Mapa:");
                    mapaJogador1.mostrarMapa();
                    System.out.println("\nJogador 1 - Mapa do Oponente:");
                    mapaJogador2.mostrarMapaDoOponente();
                }

                // ve se o jogador 1 venceu
                if (mapaJogador2.todosBarcosAfundados()) {
                    System.out.println("\nJogador 1 venceu!");
                    break;
                }

                // jogadas do jogador 1
                System.out.println("\nJogador 2 - Seu Mapa:");
                mapaJogador2.mostrarMapa();
                System.out.println("\nJogador 2 - Mapa do Oponente:");
                mapaJogador1.mostrarMapaDoOponente();
                while (realizarAtaque(mapaJogador1, scanner)) {
                    System.out.println("\nJogador 2 - Seu Mapa:");
                    mapaJogador2.mostrarMapa();
                    System.out.println("\nJogador 2 - Mapa do Oponente:");
                    mapaJogador1.mostrarMapaDoOponente();
                }

                // ve se o jogador 2 venceu
                if (mapaJogador1.todosBarcosAfundados()) {
                    System.out.println("\nJogador 2 venceu!");
                    break;
                }
            } else if (escolhaJogo == 'C' || escolhaJogo == 'c') {
                // jogadas do jogador 1
                System.out.println("\nJogador 1 - Seu Mapa:");
                mapaJogador1.mostrarMapa();
                System.out.println("\nJogador 1 - Mapa do Oponente:");
                mapaJogador2.mostrarMapaDoOponente();
                while (realizarAtaque(mapaJogador2, scanner)) {
                    System.out.println("\nJogador 1 - Seu Mapa:");
                    mapaJogador1.mostrarMapa();
                    System.out.println("\nJogador 1 - Mapa do Oponente:");
                    mapaJogador2.mostrarMapaDoOponente();
                }

                // ve se o jogador 1 venceu
                if (mapaJogador2.todosBarcosAfundados()) {
                    System.out.println("\nJogador 1 venceu!");
                    break;
                }

                // jogadas do pc
                System.out.println("\nVez do Computador - Mapa do Oponente:");
                mapaJogador1.mostrarMapaDoOponenteComputador();
                while (realizarAtaqueComputador(mapaJogador1)) {
                    System.out.println("\nVez do Computador - Mapa do Oponente:");
                    mapaJogador1.mostrarMapaDoOponenteComputador();
                }

                // ve se o pc venceu
                if (mapaJogador1.todosBarcosAfundados()) {
                    System.out.println("\nComputador venceu!");
                    break;
                }
            } else {
                System.out.println("Escolha inválida! Por favor, escolha J para jogar contra outro jogador ou C para jogar contra o computador.");
                escolhaJogo = scanner.next().charAt(0);
            }
        }

        scanner.close();
    }

    // metodo de ataque pro jogador
    public static boolean realizarAtaque(Mapa mapa, Scanner scanner) {
        System.out.println("\nDigite a linha (0-9):");
        int linha = scanner.nextInt();
        System.out.println("Digite a coluna (A-J):");
        char coluna = Character.toUpperCase(scanner.next().charAt(0));
        int colunaIndex = coluna - 'A';

        boolean acertou = mapa.atacar(linha, colunaIndex);
        return acertou;
    }

    // metodo aleatorio de ataque pro pc 
    public static boolean realizarAtaqueComputador(Mapa mapa) {
        Random random = new Random();
        int linha, coluna;

        do {
            linha = random.nextInt(10);
            coluna = random.nextInt(10);
        } while (!mapa.validarAtaque(linha, coluna));

        boolean acertou = mapa.atacar(linha, coluna);
        return acertou;
    }

    // metodo p coloca barco manual
    public static void alocaBarcos(Mapa mapa, Scanner scanner) {
        System.out.println("\nAlocar barcos no mapa manualmente:");

        for (int tamanho = 4; tamanho >= 1; tamanho--) {
            for (int i = 0; i < 5 - tamanho; i++) {
                while (true) {
                    System.out.println("\nAlocar um barco de tamanho " + tamanho);
                    System.out.println("Digite a linha (0-9):");
                    int linha = scanner.nextInt();
                    System.out.println("Digite a coluna (A-J):");
                    char coluna = Character.toUpperCase(scanner.next().charAt(0));
                    int colunaIndex = coluna - 'A';
                    System.out.println("Digite a direção (1 - Horizontal, 2 - Vertical):");
                    int direcao = scanner.nextInt();
                    if (mapa.alocarBarco(linha, colunaIndex, tamanho, direcao)) {
                        break;
                    } else {
                        System.out.println("Posição inválida! Tente novamente.");
                    }
                }
            }
        }
    }

    // metodo p coloca barco aleatorio
    public static void alocaBarcosAleatoriamente(Mapa mapa) {
        System.out.println("\nAlocar barcos no mapa aleatoriamente:");
        Random random = new Random();

        for (int tamanho = 4; tamanho >= 1; tamanho--) {
            for (int i = 0; i < 5 - tamanho; i++) {
                while (true) {
                    int linha = random.nextInt(10);
                    int coluna = random.nextInt(10);
                    int direcao = random.nextInt(2) + 1;
                    if (mapa.alocarBarco(linha, coluna, tamanho, direcao)) {
                        break;
                    }
                }
            }
        }
    }
}

class Mapa {
    private char[][] matriz;
    private boolean[][] atacado;
    private static final int TAMANHO = 10;

    public Mapa() {
        matriz = new char[TAMANHO][TAMANHO];
        atacado = new boolean[TAMANHO][TAMANHO];

        // inicio do mapa vazio sem ataque
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                matriz[i][j] = ' ';
                atacado[i][j] = false;
            }
        }
    }

    // metodo q adiciona barco e valida posicao etc etc
    public boolean alocarBarco(int linha, int coluna, int tamanho, int direcao) {
        if (linha < 0 || linha >= TAMANHO || coluna < 0 || coluna >= TAMANHO) {
            return false;
        }

        if (direcao == 1) { // aq vai fica horizontal
            if (coluna + tamanho > TAMANHO) {
                return false;
            }
            for (int i = 0; i < tamanho; i++) {
                if (matriz[linha][coluna + i] != ' ') {
                    return false;
                }
            }
            for (int i = 0; i < tamanho; i++) {
                matriz[linha][coluna + i] = (char) ('A' + tamanho - 1);
            }
        } else { // aq vai fica horizontal
            if (linha + tamanho > TAMANHO) {
                return false;
            }
            for (int i = 0; i < tamanho; i++) {
                if (matriz[linha + i][coluna] != ' ') {
                    return false;
                }
            }
            for (int i = 0; i < tamanho; i++) {
                matriz[linha + i][coluna] = (char) ('A' + tamanho - 1);
            }
        }
        return true;
    }

    public boolean validarAtaque(int linha, int coluna) {
        return linha >= 0 && linha < TAMANHO && coluna >= 0 && coluna < TAMANHO && !atacado[linha][coluna];
    }

    public boolean atacar(int linha, int coluna) {
        atacado[linha][coluna] = true;
        if (matriz[linha][coluna] == ' ') {
            System.out.println("Água!");
            return false; // da "false" e diz q foi na agua
        } else {
            System.out.println("Acertou um barco!");
            matriz[linha][coluna] = 'X';
            return true; // da "true" e diz q acertou barco
        }
    }

    public boolean todosBarcosAfundados() {
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                if (matriz[i][j] != ' ' && matriz[i][j] != 'X') {
                    return false;
                }
            }
        }
        return true;
    }

    public void mostrarMapa() {
        System.out.print("  ");
        for (int i = 0; i < TAMANHO; i++) {
            System.out.print((char) ('A' + i) + " ");
        }
        System.out.println();
        for (int i = 0; i < TAMANHO; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < TAMANHO; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void mostrarMapaDoOponente() {
        System.out.print("  ");
        for (int i = 0; i < TAMANHO; i++) {
            System.out.print((char) ('A' + i) + " ");
        }
        System.out.println();
        for (int i = 0; i < TAMANHO; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < TAMANHO; j++) {
                if (atacado[i][j]) {
                    if (matriz[i][j] == ' ') {
                        System.out.print("- ");
                    } else {
                        System.out.print("X ");
                    }
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }

    public void mostrarMapaDoOponenteComputador() {
        System.out.print("  ");
        for (int i = 0; i < TAMANHO; i++) {
            System.out.print((char) ('A' + i) + " ");
        }
        System.out.println();
        for (int i = 0; i < TAMANHO; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < TAMANHO; j++) {
                if (atacado[i][j]) {
                    if (matriz[i][j] == ' ') {
                        System.out.print("- ");
                    } else {
                        System.out.print("X ");
                    }
                } else {
                    if (matriz[i][j] != ' ') {
                        System.out.print("B ");
                    } else {
                        System.out.print("  ");
                    }
                }
            }
            System.out.println();
        }
    }
}
