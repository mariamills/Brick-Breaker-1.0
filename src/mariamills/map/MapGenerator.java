package mariamills.map;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;

public class MapGenerator {

    public int[][] map;
    public int brickWidth;
    public int brickHeight;

    public int distanceFromSide = 80;
    public int distanceFromTop = 50;

    public MapGenerator(int row, int col) {
        map = new int[row][col];

        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[0].length; j++) {
                map[i][j] = 1;
            }
        }
        brickWidth = 540 / col;
        brickHeight = 150 / row;
    }

    public void draw(Graphics2D gpc) {
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[0].length; j++) {
                if(map[i][j] > 0) {
                    gpc.setColor(Color.RED);
                    gpc.fillRect(j * brickWidth + distanceFromSide, i * brickHeight + distanceFromTop, brickWidth, brickHeight);

                    gpc.setStroke(new BasicStroke(3));
                    gpc.setColor(Color.WHITE);
                    gpc.drawRect(j * brickWidth + distanceFromSide, i * brickHeight + distanceFromTop, brickWidth, brickWidth);
                }
            }
        }
    }
    public void setBrickValue(int value, int row, int col) {
        map[row][col] = value;
    }
}
