# Minesweeper Game

This is a Java implementation of the classic game "Minesweeper," created as a challenge from the CodeGym platform. In this version, the game features a grid of cells where the player needs to identify mines without triggering them. The game allows interaction through mouse clicks, with left-clicks opening tiles and right-clicks marking tiles with flags.

## Features

- A grid of 9x9 cells (modifiable size).
- Mines are randomly placed on the grid.
- Left-click to open a cell, right-click to mark it with a flag.
- The game ends when the player triggers a mine or reveals all safe cells.
- The game includes a score that increases as the player uncovers safe tiles.
- A winning condition when all mines are flagged correctly, and all safe cells are uncovered.

## How to Play

1. **Left-click** to open a tile.
2. **Right-click** to place or remove a flag.
3. The goal is to uncover all the non-mined tiles without triggering any mines.
4. If you click on a tile containing a mine, the game will end, and you'll lose.
5. The game will automatically restart when it's over, and the score will reset.

## Requirements

- Java 8 or higher.

## How to Run

1. Clone this repository to your local machine.
2. Open the project in your favorite IDE (e.g., IntelliJ IDEA, Eclipse).
3. Run the `MinesweeperGame` class.

```bash
git clone https://github.com/your-username/minesweeper.git
cd minesweeper
# Open in your IDE and run the MinesweeperGame class
