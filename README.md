# Tic-Tac-Toe Ultimate

This repository contains the source code for a Java-based **Ultimate Tic-Tac-Toe** game, which is an advanced version of the classic Tic-Tac-Toe. In this game, each cell of the 3x3 grid contains another Tic-Tac-Toe grid, making the gameplay more strategic and challenging.

## Features
- **Ultimate Tic-Tac-Toe Mechanics**: Each move in a smaller grid influences which grid the opponent can play in.
- **Winner Detection**: Detects winners in individual smaller grids and the overall large grid.
- **Interactive Gameplay**: Supports human vs human gameplay.
- **Console Output**: The game runs in the console with simple text-based input and output.

## Table of Contents
- [Features](#features)
- [Installation](#installation)
- [How to Play](#how-to-play)
- [Game Rules](#game-rules)
- [Future Improvements](#future-improvements)
- [Contributing](#contributing)

## Installation

1. **Clone the repository**:
    ```bash
    git clone https://github.com/your-username/tic-tac-toe-ultimate.git
    ```
2. **Navigate into the project directory**:
    ```bash
    cd tic-tac-toe-ultimate
    ```

3. **Compile the Java files**:
    ```bash
    javac src/*.java
    ```

4. **Run the Game**:
    ```bash
    java src.Main
    ```

## How to Play

1. The game is played on a 3x3 grid of smaller Tic-Tac-Toe grids. Players take turns placing their marks ('X' or 'O') in the smaller grids.
2. The grid in which a player must place their mark is determined by the previous player's move. For example, if the last player placed their mark in the top-left cell of a smaller grid, the next player must place their mark in the top-left small grid.
3. A player wins a smaller grid when they form a horizontal, vertical, or diagonal line of their marks.
4. The goal of the game is to win the larger 3x3 grid by winning three smaller grids in a row (horizontally, vertically, or diagonally).

## Game Rules

- Players alternate turns to place their marks ('X' or 'O') in one of the smaller grids.
- A player wins an individual grid if they place three of their marks in a row, column, or diagonal.
- The overall winner is the player who wins three smaller grids in a row on the larger 3x3 grid.

## Future Improvements
- **AI Implementation**: Add support for human vs AI gameplay with various difficulty levels.
- **Graphical Interface**: Implement a GUI version using a framework such as Swing or JavaFX.
- **Save/Load Feature**: Add the ability to save and load games.
- **Multiplayer Online**: Enable network play for remote multiplayer gaming.

## Contributing

Feel free to fork this repository and submit pull requests. If you'd like to propose significant changes, consider opening an issue first to discuss them.

1. Fork the repo.
2. Create a feature branch: `git checkout -b feature/your-feature`.
3. Commit your changes: `git commit -m 'Add some feature'`.
4. Push to the branch: `git push origin feature/your-feature`.
5. Open a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
