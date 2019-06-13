package Tetris;


//每个方块的类
public class Xing {
    public char[][] shape;
    public int num;
    int x;
    int y;

    public void down(char[][] tempStatus, char[][] nextStatus) {
        for (int i = x; i >= x - 1; i--) {
            for (int j = y; j <= y + 3; j++) {
                if (tempStatus[i][j] == 'A' && shape[i - x + 3][j - y] == 'A') {
                    nextStatus[i + 1][j] = 'A';
                    nextStatus[i][j] = 'N';
                }

            }
        }
    }

    public void left(char[][] tempStatus, char[][] nextStatus) {
        for (int i = x; i >= x - 1; i--) {
            for (int j = y; j <= y + 3; j++) {
                if (tempStatus[i][j] == 'A' && shape[i - x + 3][j - y] == 'A') {
                    nextStatus[i][j - 1] = 'A';
                    nextStatus[i][j] = 'N';
                }

            }
        }

    }

    public void right(char[][] tempStatus, char[][] nextStatus) {
        for (int i = x; i >= x - 1; i--) {
            for (int j = y + 3; j >= y; j--) {
                if (tempStatus[i][j] == 'A' && shape[i - x + 3][j - y] == 'A') {
                    nextStatus[i][j + 1] = 'A';
                    nextStatus[i][j] = 'N';
                }

            }
        }

    }
}