package Tetris;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.*;



public class WorldMap extends JPanel {

    //游戏停止
    private boolean STOP = false;

    //用于随机创建一个新形状
    private Random random = new Random();

    //当前在移动的形状
    private Xing currentXing;

    //当前形状移动的方向
    static public char direct = 'D';

    //当前形状的停止标志
    private int stop = 1;

    public boolean isSTOP() {
		return STOP;
	}

    public boolean getSTOP() {
    	return this.STOP;
    }
	public void setSTOP(boolean sTOP) {
		STOP = sTOP;
	}

	public Random getRandom() {
		return random;
	}

	public void setRandom(Random random) {
		this.random = random;
	}

	public Xing getCurrentXing() {
		return currentXing;
	}

	public void setCurrentXing(Xing currentXing) {
		this.currentXing = currentXing;
	}

	public static char getDirect() {
		return direct;
	}

	public static void setDirect(char direct) {
		WorldMap.direct = direct;
	}

	public int getStop() {
		return stop;
	}

	public void setStop(int stop) {
		this.stop = stop;
	}

	public char[][] getNextStatus() {
		return nextStatus;
	}

	public void setNextStatus(char[][] nextStatus) {
		this.nextStatus = nextStatus;
	}

	public char[][] getTempStatus() {
		return tempStatus;
	}

	public void setTempStatus(char[][] tempStatus) {
		this.tempStatus = tempStatus;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public int getROW() {
		return ROW;
	}

	public int getCOLUMN() {
		return COLUMN;
	}

	public int getDELAY_TIME() {
		return DELAY_TIME;
	}

	//行
    private final int ROW = 18;

    //列
    private final int COLUMN = 13;

    //下一状态
    private char[][] nextStatus = new char[ROW][COLUMN];

    //当前状态
    private char[][] tempStatus = new char[ROW][COLUMN + 1];

    //计时器监听
    private Timer timer;

    // 动画帧之间的延时
    private final int DELAY_TIME = 400;

    /**
     * 构造器，并初始化第一帧
     */
    public WorldMap() {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COLUMN; col++) {
                nextStatus[row][col] = 'N';
                tempStatus[row][col] = 'N';
            }
        }
        // 创建计时器
        timer = new Timer(DELAY_TIME, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                changeCellStatus();
                repaint();
            }
        });
        // 开启计时器
        timer.start();
    }

    /**
     * 画图形界面
     */
    @Override
    //根据nextStatus数组绘制下一帧图像
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int length = 30;
        for (int i = 2; i < ROW - 1; i++) {
            for (int j = 1; j < COLUMN - 1; j++) {
                if (nextStatus[i][j] == 'A') {
                    g.fillRect(j * length, i * length, length, length);
                } else {
                    g.drawRect(j * length, i * length, length, length);
                }
            }
        }
    }

    /**
     * 结束游戏
     */
    private void ifStop(){
        if (tempStatus[currentXing.x + 1][currentXing.y + 1] == 'A') {
            timer.stop();
            STOP = true;
            JOptionPane.showMessageDialog(null, "游戏结束！");
        }
    }



    /**
     * 改变下一帧状态
     */
    private void changeCellStatus() {
        nextStatus = tempStatus;


        if (stop == 1) {
            //在（0，8）处创建新元素
            currentXing = createNew();
            char[][] temp = currentXing.shape;
            for (int i = 0; i <= 3; i++) {
                for (int j = 5; j <= 8; j++) {
                    nextStatus[i][j] = temp[i][j - 5];
                }
            }
            stop = 0;
            //判断是否结束
            ifStop();

        } else {
            if (currentXing.num == 0) {
                if (direct == 'D') {
                    for (int i = currentXing.y; i <= currentXing.y + 3; i++) {
                        if (tempStatus[currentXing.x + 1][i] == 'A' || currentXing.x == ROW - 2) {

                            stop = 1;
                        }
                    }

                    if (stop == 0) {
                        currentXing.down(tempStatus,nextStatus); 

                    }
                    currentXing.x++;
                }
                //向左一格
                else if (direct == 'L') {
                    if (tempStatus[currentXing.x][currentXing.y - 1] == 'A' || currentXing.y == 1) {
                        direct = 'D';
                        return;


                    }
                    if (stop == 0) {
                        currentXing.left(tempStatus,nextStatus);
                    }
                    currentXing.y--;
                    //改变状态
                    direct = 'D';
                }
                //向右一格
                else if (direct == 'R') {
                    if (tempStatus[currentXing.x][currentXing.y + 4] == 'A' || currentXing.y + 3 == COLUMN - 2) {

                        direct = 'D';
                        return;
                    }
                    if (stop == 0) {
                        currentXing.right(tempStatus,nextStatus); 
                    }
                    currentXing.y++;
                    //改变状态
                    direct = 'D';
                }
            }
            if (currentXing.num == 1) {
                if (direct == 'D') {
                    for (int i = currentXing.y; i <= currentXing.y + 2; i++) {
                        if (tempStatus[currentXing.x + 1][i] == 'A' || currentXing.x == ROW - 2) {
                            stop = 1;
                        }
                    }

                    if (stop == 0) {
                        currentXing.down(tempStatus,nextStatus);
                    }
                    currentXing.x++;
                } else if (direct == 'L') {
                    if (tempStatus[currentXing.x][currentXing.y - 1] == 'A' || tempStatus[currentXing.x - 1][currentXing.y] == 'A' || currentXing.y == 1) {

                        direct = 'D';
                        return;
                    }
                    if (stop == 0) {
                        currentXing.left(tempStatus,nextStatus);

                    }
                    currentXing.y--;
                    //改变状态
                    direct = 'D';
                } else if (direct == 'R') {
                    if (tempStatus[currentXing.x][currentXing.y + 3] == 'A' || tempStatus[currentXing.x - 1][currentXing.y + 2] == 'A' || currentXing.y + 2 == COLUMN - 2) {

                        direct = 'D';
                        return;
                    }
                    if (stop == 0) {
                        currentXing.right(tempStatus,nextStatus);

                    }
                    currentXing.y++;
                    //改变状态
                    direct = 'D';
                }
            }
            if (currentXing.num == 2) {
                if (direct == 'D') {
                    for (int i = currentXing.y; i <= currentXing.y + 1; i++) {
                        if (tempStatus[currentXing.x + 1][i] == 'A' || currentXing.x == ROW - 2) {
                            stop = 1;
                        }
                    }
                    if (stop == 0) {
                        currentXing.down(tempStatus,nextStatus);
                    }
                    currentXing.x++;
                } else if (direct == 'L') {
                    if (tempStatus[currentXing.x][currentXing.y - 1] == 'A' || tempStatus[currentXing.x - 1][currentXing.y - 1] == 'A' || currentXing.y == 1) {

                        direct = 'D';
                        return;
                    }
                    if (stop == 0) {
                        currentXing.left(tempStatus,nextStatus);

                    }
                    currentXing.y--;
                    //改变状态
                    direct = 'D';
                } else if (direct == 'R') {
                    if (tempStatus[currentXing.x][currentXing.y + 2] == 'A' || tempStatus[currentXing.x - 1][currentXing.y + 2] == 'A' || currentXing.y + 1 == COLUMN - 2) {

                        direct = 'D';
                        return;
                    }
                    if (stop == 0) {
                        currentXing.right(tempStatus,nextStatus);

                    }
                    currentXing.y++;
                    //改变状态
                    direct = 'D';
                }
            }
            if (currentXing.num == 3) {
                if (direct == 'D') {
                    if (tempStatus[currentXing.x][currentXing.y] == 'A') {
                        stop = 1;
                    }
                    for (int i = currentXing.y + 1; i <= currentXing.y + 2; i++) {
                        if (tempStatus[currentXing.x + 1][i] == 'A' || currentXing.x == ROW - 2) {
                            stop = 1;
                        }
                    }
                    if (stop == 0) {
                        currentXing.down(tempStatus,nextStatus);
                    }
                    currentXing.x++;
                } else if (direct == 'L') {
                    if (tempStatus[currentXing.x][currentXing.y] == 'A' || tempStatus[currentXing.x - 1][currentXing.y - 1] == 'A' || currentXing.y == 1) {

                        direct = 'D';
                        return;
                    }
                    if (stop == 0) {
                        currentXing.left(tempStatus,nextStatus);

                    }
                    currentXing.y--;
                    //改变状态
                    direct = 'D';
                } else if (direct == 'R') {
                    if (tempStatus[currentXing.x][currentXing.y + 3] == 'A' || tempStatus[currentXing.x - 1][currentXing.y + 2] == 'A' || currentXing.y + 2 == COLUMN - 2) {

                        direct = 'D';
                        return;
                    }
                    if (stop == 0) {
                        currentXing.right(tempStatus,nextStatus);

                    }
                    currentXing.y++;
                    //改变状态
                    direct = 'D';
                }
            }
        }

        //行满消行
        clearLine();

        //设定当前tempStatus
        copyWorldMap();
    }

    /**
     * 行满消行
     */
    private void clearLine(){
        if (stop == 1) {
            for (int i = 5; i <= ROW - 2; i++) {
                int tempNum = 0;
                for (int j = 1; j <= COLUMN - 2; j++) {
                    if (tempStatus[i][j] == 'A')
                        tempNum++;
                }
                //当前行满
                if (tempNum == COLUMN - 2) {
                    for (int n = i; n >= 5; n--) {
                        for (int m = 1; m <= COLUMN - 2; m++) {
                            nextStatus[n][m] = tempStatus[n - 1][m];
                        }
                    }
                }
            }
        }
    }

    /**
     * 复制地图
     */
    
    private void copyWorldMap() {
    	
        tempStatus=nextStatus;
        
    	
    }
    

    /**
     * 返回下一个方块类
     *
     * @return
     */
    public Xing createNew() {
        Xing x = new Xing();
        int number = random.nextInt(4);
        x.num = number;
        x.x = 3;
        x.y = 5;

        char[][] s = null;
        switch (number) {
            case 0:
                s = new char[][]{
                        {'N', 'N', 'N', 'N'},
                        {'N', 'N', 'N', 'N'},
                        {'N', 'N', 'N', 'N'},
                        {'A', 'A', 'A', 'A'}
                };
                break;
            case 1:
                s = new char[][]{
                        {'N', 'N', 'N', 'N'},
                        {'N', 'N', 'N', 'N'},
                        {'N', 'A', 'N', 'N'},
                        {'A', 'A', 'A', 'N'}
                };
                break;
            case 2:
                s = new char[][]{
                        {'N', 'N', 'N', 'N'},
                        {'N', 'N', 'N', 'N'},
                        {'A', 'A', 'N', 'N'},
                        {'A', 'A', 'N', 'N'}
                };
                break;
            case 3:
                s = new char[][]{
                        {'N', 'N', 'N', 'N'},
                        {'N', 'N', 'N', 'N'},
                        {'A', 'A', 'N', 'N'},
                        {'N', 'A', 'A', 'N'}
                };

        }
        x.shape = s;
        return x;
    }

    /**
     * 键盘监听器
     */
    public class MyKeyListener implements KeyListener {

        @Override
        public void keyPressed(KeyEvent e) {
            if (!STOP) {
                if (e.getKeyChar() == 'a' || e.getKeyChar() == 'A' || e.getKeyCode() == 37) {
                    WorldMap.direct = 'L';
                    changeCellStatus();
                    repaint();
                } else if (e.getKeyChar() == 'd' || e.getKeyChar() == 'D' || e.getKeyCode() == 39) {
                    WorldMap.direct = 'R';
                    changeCellStatus();
                    repaint();
                } else if (e.getKeyChar() == 's' || e.getKeyChar() == 'S' || e.getKeyCode() == 40) {
                    WorldMap.direct = 'D';
                    changeCellStatus();
                    repaint();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent arg0) {

        }

        @Override
        public void keyTyped(KeyEvent arg0) {

        }
    }
}
