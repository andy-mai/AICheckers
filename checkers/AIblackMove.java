/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author phil
 */
public class AIblackMove {

    //This is the current state of the game
    CheckersGame currentGame;
    //This array contains the legal moves at this point in the game for black.
    CheckersMove legalMoves[];
    //this is where the algorithm will stop in the tree
    int depth;
    // The constructor.
    public AIblackMove(CheckersGame game, CheckersMove moves[]) {
        currentGame = game;
        legalMoves = moves;
        depth = 7;
    }

    // This is where your logic goes to make a move.
    public CheckersMove nextMove() {
        // Here are some simple ideas:
        // 1. Always pick the first move
        //return legalMoves[0];
        // 2. Pick a random move
        //return legalMoves[currentGame.generator.nextInt(legalMoves.length)];
        return minimax(currentGame.boardData, 7, true);

        //Or you can create a copy of the current board like this:
        //CheckersData new_board = new CheckersData(currentGame.boardData);
        //You can then simulate a move on this new board like this:
        //currentGame.simulateMove(new_board, legalMoves[0],CheckersData.BLACK); 
        //After you simulate the move you can evaluate the state of the board  
        //after the move and see how it looks.  You can evaluate all the 
        //currently legal moves using a loop and select the best one.
    }
    
    public int minimax(CheckersData newboard, int depth, boolean maximizingPlayer){
    CheckersData new_board = new CheckersData(newboard);
    int max;
    int min;
    
    //Get array of all black moves
    CheckersMove blackMoves[] = new_board.getLegalMoves(CheckersData.BLACK);
    //Get array of all red moves
    CheckersMove redMoves[] = new_board.getLegalMoves(CheckersData.RED);
    
    
    //variable for the best move of classtype CheckersMove, 
    // CheckersMove bestMove = new CheckersMove(0,0,0,0);
    //bestMove = blackMoves[0];
    
        if(depth == 0 || newboard.getLegalMoves(CheckersData.BLACK) == null){
        return evaluate(new_board);
        }
            if (maximizingPlayer){
            max = 9999;
            //do all the legal moves, reset the board after every move to try all possible moves
            //for(int i = 0; i < blackMoves.length; i++){
            for (CheckersMove blackMove : blackMoves) {
                new_board = new CheckersData(newboard);
                new_board.makeMove(blackMove);
                //evals the board at given position
                int evaluation = minimax(new_board, depth -1, false);
                //compares that value to max and if its higher than max then it's the new best move
                if (evaluation > max) {
                    max = evaluation;
                    //bestMove = blackMove;
                }
            }
            return max;
        }
        else {
        min = -9999;
            //do all the legal moves, reset the board after every move to try all possible moves
            //for(int i = 0; i < redMoves.length; i++){
            for (CheckersMove redMove : redMoves) {
                new_board = new CheckersData(newboard);
                new_board.makeMove(redMove);
                //evals the board at given position
                int evaluation = minimax(new_board, depth -1, true);
                //compares that value to max and if its higher than max then it's the new best move
                if (evaluation < min) {
                    min = evaluation;
                    //bestMove = blackMove;
                }
            }       
        }
            return min;
    }

    // One thing you will probably want to do is evaluate the current
    // goodness of the board.  This is a toy example, and probably isn't
    // very good, but you can tweak it in any way you want.  Not only is
    // number of pieces important, but board position could also be important.
    // Also, are kings more valuable than regular pieces?  How much?
    int evaluate(CheckersData board) {
        return board.numBlack()+ 2*board.numBlackKing()
                - board.numRed() - 2*board.numRedKing();
    }
}
