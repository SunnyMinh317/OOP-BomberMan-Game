package main.Level;

import java.io.File;
import java.util.Scanner;

public class MapLoader {
    public static int[][] loadLevel(String path) {
        int[][] map;

        try {
            File level = new File(path);
            Scanner testReader = new Scanner(level);
            int levelNo = testReader.nextInt();
            int levelRow = testReader.nextInt();
            int levelCol = testReader.nextInt();

            map = new int[levelRow][levelCol];

            testReader.nextLine();
            for (int i = 0; i < levelRow; i++) {
                String processedRow = testReader.nextLine();
                for (int j = 0; j < levelCol; j++) {
                    if (processedRow.charAt(j) == '#') {
                        map[i][j] = 1;
                    } else if (processedRow.charAt(j) == '*') {
                        map[i][j] = 2;
                    } else if (processedRow.charAt(j) == 's') {
                        map[i][j] = 4;
                    } else if (processedRow.charAt(j) == 'f') {
                        map[i][j] = 5;
                    } else if (processedRow.charAt(j) == 'b') {
                        map[i][j] = 6;
                    } else if (processedRow.charAt(j) == '1') {
                        map[i][j] = 10;
                    } else if (processedRow.charAt(j) == '2') {
                        map[i][j] = 11;
                    } else {
                        map[i][j] = 0;
                    }
                }
            }

            testReader.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return map;
    }

    /** Set whichData to 0 for levelNo, to 1 for levelWidth, to 2 for levelHeight.*/
    public static int getLevelData(String path, int whichData) {
        try {
            File level = new File(path);
            Scanner testReader = new Scanner(level);
            int levelNo = testReader.nextInt();
            int levelRow = testReader.nextInt();
            int levelCol = testReader.nextInt();
            testReader.close();

            if (whichData == 1) {
                return levelCol;
            } else if (whichData == 2) {
                return levelRow;
            } else if (whichData == 0) {
                return levelNo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
