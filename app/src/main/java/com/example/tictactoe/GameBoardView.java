package com.example.tictactoe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class GameBoardView extends View {
    private static final int ROWS = 3;
    private static final int COLS = 3;
    private static final int EMPTY = 0;
    private static final int CROSS = 1;
    private static final int CIRCLE = 2;
    private int[][] board;
    private int currentPlayer;
    private Paint paint;
    public static String currentStatus = "status";


    public GameBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        board = new int[ROWS][COLS];
        currentPlayer = CROSS;

        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        drawBoard(canvas);
        drawShapes(canvas);

        int winner = checkForWinner();
        if (winner != EMPTY) {
            String s = (winner == CROSS) ? "Player X wins!" :
                    (winner == CIRCLE) ? "Player O wins!" : "It's a draw!";

            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();

            currentStatus = "status";
            MainActivity.status.setText(currentStatus);
            resetGame();
        }
    }

    private void drawBoard(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        int cellWidth = width / COLS;
        int cellHeight = height / ROWS;

        for (int i = 1; i < ROWS; i++) {
            canvas.drawLine(0, i * cellHeight, width, i * cellHeight, paint);
        }

        for (int i = 1; i < COLS; i++) {
            canvas.drawLine(i * cellWidth, 0, i * cellWidth, height, paint);
        }
    }

    private void drawShapes(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        int cellWidth = width / COLS;
        int cellHeight = height / ROWS;

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                float centerX = j * cellWidth + cellWidth / 2f;
                float centerY = i * cellHeight + cellHeight / 2f;

                switch (board[i][j]) {
                    case CROSS:
                        drawCross(canvas, centerX, centerY, cellWidth, cellHeight);
                        break;
                    case CIRCLE:
                        drawCircle(canvas, centerX, centerY, cellWidth, cellHeight);
                        break;
                }
            }
        }
    }

    private void drawCross(Canvas canvas, float centerX, float centerY, float cellWidth, float cellHeight) {
        canvas.drawLine(centerX - cellWidth / 3, centerY - cellHeight / 3,
                centerX + cellWidth / 3, centerY + cellHeight / 3, paint);

        canvas.drawLine(centerX - cellWidth / 3, centerY + cellHeight / 3,
                centerX + cellWidth / 3, centerY - cellHeight / 3, paint);
    }

    private void drawCircle(Canvas canvas, float centerX, float centerY, float cellWidth, float cellHeight) {
        canvas.drawCircle(centerX, centerY, Math.min(cellWidth, cellHeight) / 3, paint);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int col = (int) (event.getX() / (getWidth() / COLS));
            int row = (int) (event.getY() / (getHeight() / ROWS));

            if (board[row][col] == EMPTY) {
                board[row][col] = currentPlayer;
                currentPlayer = (currentPlayer == CROSS) ? CIRCLE : CROSS;
                if (currentPlayer == CROSS) {
                    currentStatus = "X's Turn - Tap to play";
                    MainActivity.status.setText(currentStatus);
                } else {
                    currentStatus = "O's Turn - Tap to play";
                    MainActivity.status.setText(currentStatus);
                }
                invalidate();
            }
        }
        return true;
    }


    private int checkForWinner() {

        for (int i = 0; i < ROWS; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != EMPTY) {
                return board[i][0];
            }
        }

        for (int i = 0; i < COLS; i++) {
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != EMPTY) {
                return board[0][i];
            }
        }

        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != EMPTY) {
            return board[0][0];
        }

        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != EMPTY) {
            return board[0][2];
        }

        boolean isBoardFull = true;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (board[i][j] == EMPTY) {
                    isBoardFull = false;
                    break;
                }
            }
        }
        if (isBoardFull) {
            return -1;
        }

        return EMPTY;
    }

    public void resetGame() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = EMPTY;
            }
        }

        currentStatus = "status";
        MainActivity.status.setText(currentStatus);
        currentPlayer = CROSS;
        invalidate();
    }
}
