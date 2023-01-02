
public class Reversi {
    int bræt;
    int[][] board;

        // Der skal angives brættets størrelse
    public Reversi(int n) {
        bræt = n;

        // Opretter Reversibrættet, med 2 x 2 brikker på kryds for hinanden i midten
        board = new int[n][n];
        board[n - 1][n - 1] = 1;
        board[n - 1][n] = -1;
        board[n][n - 1] = -1;
        board[n][n] = 1;
    }

        // Tjekker om feltet er tomt, går kun videre hvis der ikke allerede er en værdi
        // tilknyttet feltet
    public boolean lovlighed(int[][] felt, int x, int y) {

        if (felt[x][y] != 0) {
            return omringet(felt, x, y);
        } else {
            return false;
        }
    }

        // Tjekker om der en brik af modsat farve rundt om det valgte felt
    public boolean omringet(int[][] felt, int x, int y) {

        // Først sikres at der ikke kan gåes uden for arrayets størrelse for ikke at få
        // fejl.
        int op = x + 1;
        int ned = x - 1;

        int højre = y + 1;
        int venstre = y - 1;

        if (op >= bræt) {
            op = bræt - 1;
        }
        if (ned < 0) {
            ned = 0;
        }
        if (højre >= bræt) {
            højre = bræt - 1;
        }
        if (venstre < 0) {
            venstre = 0;
        }

        // Dernæst tjekkes om modsatte farve er rundt om feltet
        if (felt[op][højre] != 0) {
            return linje(felt, op, højre, x, y);
        }
        if (felt[op][y] != 0) {
            return linje(felt, op, y, x, y);
        }
        if (felt[op][venstre] != 0) {
            return linje(felt, op, venstre, x, y);
        }
        if (felt[x][højre] != 0) {
            return linje(felt, x, højre, x, y);
        }
        if (felt[x][venstre] != 0) {
            return linje(felt, x, venstre, x, y);
        }
        if (felt[ned][højre] != 0) {
            return linje(felt, ned, højre, x, y);
        }
        if (felt[ned][y] != 0) {
            return linje(felt, ned, y, x, y);
        }
        if (felt[ned][venstre] != 0) {
            return linje(felt, ned, venstre, x, y);
        } else {
            return false;
        }
    }

        // Tjekker om på den anden side af den modsat farvede brik der din farve brik.
        // Hvis der er samme farve kaldes metoden igen.
        // Hvis der er tomt fortsætter programmet ikke.
    public boolean linje(int[][] felt, int side_x, int side_y, int gammel_x, int gammel_y) {
        int ny_x = side_x + (side_x - gammel_x);
        int ny_y = side_y + (side_y - gammel_y);
        if (felt[ny_x][ny_y] != 0) {
            return linje(felt, ny_x, ny_y, side_x, side_y);
        } else if (felt[ny_x][ny_y] == 1) {
            return true;
        } else {
            return false;
        }
    }

        // Kode til at highlighte felter der er lovlige træk.
    public void highlight() {
        for (int i = 0; i < bræt - 1; i++) {
            for (int j = 0; j < bræt - 1; j++) {
                if (lovlighed(board, i, j)) {
                }
            }
        }
    }
}
