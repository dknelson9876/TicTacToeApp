package com.example.tictactoe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.Toast;

public class GameGrid extends View {
    private Paint paint = new Paint();
    private Context context;
    private float cellWidth, cellHeight;
    private int cellCount = 3;
    private char[][] cells;
    private boolean turn;
    private int turnCount;
    private boolean winState;

    public GameGrid(Context context) {
        super(context);

        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(8);

        this.context = context;
        cells = new char[cellCount][cellCount];
        //fill with 'a' as a default char
        for (int i = 0; i < cellCount; i++) {
            for (int j = 0; j < cellCount; j++) {
                cells[i][j] = 'a';
            }
        }

        turn = false;
        turnCount = 0;
    }

    @Override
    public void onDraw(Canvas canvas) {

        float width = canvas.getWidth();
        float height = canvas.getHeight();
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);


        //assumes a square grid, same # of cells both directions
        cellCount = 3;

        cellWidth = width / cellCount;
        cellHeight = height / cellCount;

        float x = cellWidth;

        //vertical lines
        for (int i = 1; i < cellCount; i++) {
            canvas.drawLine(x, 0f, x, height, paint);
            x += cellWidth;
        }

        float y = cellHeight;
        //horizontal lines
        for (int i = 1; i < cellCount; i++) {
            canvas.drawLine(0f, y, width, y, paint);
            y += cellHeight;
        }

        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j] == 'O')
                    drawO(i * cellWidth, j * cellHeight, canvas);
            }
        }

        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j] == 'X')
                    drawX(i * cellWidth, j * cellHeight, canvas);
            }
        }
        paint.setColor(Color.WHITE);
    }

    public void onClick(float x, float y) {
//        Toast.makeText(context, x+", "+y, Toast.LENGTH_SHORT).show();
        if (winState) {
            winState = false;
            turnCount = 0;
            turn = false;
            for (int i = 0; i < cellCount; i++) {
                for (int j = 0; j < cellCount; j++) {
                    cells[i][j] = 'a';
                }
            }
            this.invalidate();
            return;
        }

        int xIndex = (int) (x / cellWidth);
        int yIndex = (int) (y / cellHeight);

        if (cells[xIndex][yIndex] != 'a')
            return;
        if (turn)
            cells[xIndex][yIndex] = 'O';
        else
            cells[xIndex][yIndex] = 'X';
        turn = !turn;


        this.invalidate();

        turnCount++;
        if (turnCount > 4)
            checkForWin(xIndex, yIndex);
    }

    private void checkForWin(int x, int y) {
        char winner = 'a';

        //check the row just used
        if (cells[0][y] == cells[1][y] && cells[1][y] == cells[2][y])
            winner = cells[0][y];

        //check the column just used
        if (cells[x][0] == cells[x][1] && cells[x][1] == cells[x][2])
            winner = cells[x][0];

        //check diagonals
        if (cells[0][0] == cells[1][1] && cells[1][1] == cells[2][2])
            winner = cells[0][0];
        if (cells[0][2] == cells[1][1] && cells[1][1] == cells[2][0])
            winner = cells[0][2];


        if (winner != 'a') {
            Toast.makeText(context, winner + "'s won!", Toast.LENGTH_SHORT).show();
            winState = true;
            Toast.makeText(context, "Tap anywhere to start over", Toast.LENGTH_LONG).show();
        } else if (turnCount > 8) {
            Toast.makeText(context, "Draw!", Toast.LENGTH_LONG).show();
            winState = true;
            Toast.makeText(context, "Tap anywhere to start over", Toast.LENGTH_LONG).show();

        }
    }

    public void drawX(float x, float y, Canvas canvas) {
        int pad = 20;

        canvas.drawLine(x + pad,
                y + cellHeight / 2 - cellWidth / 2 + pad,
                x + cellWidth - pad,
                y + cellHeight / 2 + cellWidth / 2 - pad, paint);

        canvas.drawLine(x + pad,
                y + cellHeight / 2 + cellWidth / 2 - pad,
                x + cellWidth - pad,
                y + cellHeight / 2 - cellWidth / 2 + pad, paint);


        if (cellHeight > cellWidth) {
        }//inverse the order of priority
    }

    public void drawO(float x, float y, Canvas canvas) {
        int pad = 20;

        canvas.drawOval(x + pad,
                y + cellHeight / 2 - cellWidth / 2 + pad,
                x + cellWidth - pad,
                y + cellHeight / 2 + cellWidth / 2 - pad, paint);
    }


}
