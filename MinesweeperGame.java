package es.codegym.games.minesweeper;

import com.codegym.engine.cell.Color;
import com.codegym.engine.cell.Game;

import java.util.ArrayList;
import java.util.List;

public class MinesweeperGame extends Game {
    private static final int SIDE = 9;
    private GameObject[][] gameField = new GameObject[SIDE][SIDE];
    private int countMinesOnField;
    private static final String MINE = "\uD83D\uDCA3"; 
    private static final String FLAG = "\uD83D\uDEA9";
    private int countFlags;
    private boolean isGameStopped; 
    private int countClosedTiles = SIDE*SIDE;
    private int score;

    @Override
    public void initialize() {
        setScreenSize(SIDE, SIDE);
        createGame();
    }
    
    @Override
    public void onMouseLeftClick(int x, int y){
        if(isGameStopped){
            restart();
        }else{
            openTile(x, y);
        }
    }
    
    @Override
    public void onMouseRightClick(int x, int y){
        markTile(x, y);
    }
    
    private void countMineNeighbors(){
        for(GameObject[] row: gameField){
            for(GameObject cell: row){
                if(!cell.isMine){
                    for(GameObject neighbors: getNeighbors(cell)){
                        cell.countMineNeighbors += (neighbors.isMine?1:0);
                    }
                }
            }
        }
    }
    
    private void win(){
        isGameStopped = true;
        showMessageDialog(Color.RED, "Ganaste", Color.ORANGE, score);

    }
    
    private void gameOver(){
        isGameStopped = true;
        showMessageDialog(Color.RED, "Perdiste", Color.ORANGE, score);
    }
    
    private void restart(){
        isGameStopped = false;
        countClosedTiles = SIDE*SIDE;
        score = 0;
        countMinesOnField = 0;
        setScore(score);
        createGame();
    }

    private void createGame() {
        gameField = new GameObject[SIDE][SIDE];
        
        for (int y = 0; y < SIDE; y++) {
            for (int x = 0; x < SIDE; x++) {
                boolean isMine = getRandomNumber(10) < 1;
                if (isMine) {
                    countMinesOnField++;
                }
                gameField[y][x] = new GameObject(x, y, isMine);
                setCellColor(x, y, Color.ORANGE);
                setCellValue(x, y, "");
            }
        }
        countMineNeighbors();
        countFlags = countMinesOnField;
    }

    private List<GameObject> getNeighbors(GameObject gameObject) {
        List<GameObject> result = new ArrayList<>();
        for (int y = gameObject.y - 1; y <= gameObject.y + 1; y++) {
            for (int x = gameObject.x - 1; x <= gameObject.x + 1; x++) {
                if (y < 0 || y >= SIDE) {
                    continue;
                }
                if (x < 0 || x >= SIDE) {
                    continue;
                }
                if (gameField[y][x] == gameObject) {
                    continue;
                }
                result.add(gameField[y][x]);
            }
        }
        return result;
    }
    
    private void openTile(int x, int y){
        if(!isGameStopped){
            GameObject gameObject = gameField[y][x];
            if(!gameObject.isOpen){ 
                if(!gameObject.isFlag){
                    gameObject.isOpen = true;
                    countClosedTiles -= 1;
                    setCellColor(x, y, Color.GREEN);
                    if(gameObject.isMine){
                        setCellValueEx(x, y, Color.RED, MINE);
                        gameOver();
                    }else{
                        if(gameObject.countMineNeighbors==0){
                            List<GameObject> result = getNeighbors(gameObject);
                            for(GameObject neighbors: result){
                                if(!neighbors.isOpen){
                                    openTile(neighbors.x, neighbors.y);
                                }
                            }
                            setCellValue(x, y, "");
                        }else{
                            setCellNumber(x, y, gameObject.countMineNeighbors); 
                        }
                        if(countMinesOnField == countClosedTiles){
                            win();
                        }
                        score += 5;
                        setScore(score);
                    }
                }
            }
        }
    }
    
    private void markTile(int x, int y){
        if(!isGameStopped){
        GameObject gameObject = gameField[y][x];
        if(!gameObject.isOpen){
            if(countFlags > 0 && !gameObject.isFlag){
                gameObject.isFlag = true;
                countFlags -= 1;
                setCellValue(x, y, FLAG);
                setCellColor(x, y, Color.YELLOW);
            }else if(gameObject.isFlag){
                gameObject.isFlag = false;
                countFlags += 1;
                setCellValue(x, y, "");
                setCellColor(x, y, Color.ORANGE);
            }
        }
            
        }
    }
}
