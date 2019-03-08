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
    public class MovePair{
	
	public CheckersMove move;
	public int value;
	
	public MovePair(CheckersMove Move, int val){
		move = Move;
		value = val;
	}
}

    //This is the current state of the game
    //test
    CheckersGame currentGame;
    //This array contains the legal moves at this point in the game for black.
    CheckersMove legalMoves[];
    //this is where the algorithm will stop in the tree
    int depth;
   
  
    CheckersMove cMove;
    // The constructor.
    public AIblackMove(CheckersGame game, CheckersMove moves[]) {
        currentGame = game;
        legalMoves = moves;
        depth = 6;
    }

    // This is where your logic goes to make a move.
    public CheckersMove nextMove() {
        // Here are some simple ideas:
        // 1. Always pick the first move
        //return legalMoves[0];
        // 2. Pick a random move
        //return legalMoves[currentGame.generator.nextInt(legalMoves.length)];
        return minimax(currentGame.boardData, depth, true).move;
        
        //Or you can create a copy of the current board like this:
        //CheckersData new_board = new CheckersData(currentGame.boardData);
        //You can then simulate a move on this new board like this:
        //currentGame.simulateMove(new_board, legalMoves[0],CheckersData.BLACK); 
        //After you simulate the move you can evaluate the state of the board  
        //after the move and see how it looks.  You can evaluate all the 
        //currently legal moves using a loop and select the best one.
    }
    
    public MovePair minimax(CheckersData oldboard, int depth, boolean maximizingPlayer){
   
    CheckersMove bestMove = null;
    //CheckersData new_board = new CheckersData(oldboard);
    
    //Get array of all black moves
    CheckersMove blackMoves[] = oldboard.getLegalMoves(CheckersData.BLACK);
    //Get array of all red moves
    CheckersMove redMoves[] = oldboard.getLegalMoves(CheckersData.RED);
    
    
        if(!(depth == 0 || oldboard.getLegalMoves(CheckersData.BLACK) == null || oldboard.getLegalMoves(CheckersData.RED) == null )){
        int max;
        int min;
        
            if (maximizingPlayer){
            
            max = Integer.MIN_VALUE;
            //do all the legal moves, reset the board after every move to try all possible moves
            //for(int i = 0; i < blackMoves.length; i++){
            for (CheckersMove blackMove : blackMoves) {
                
                CheckersData new_board = new CheckersData(oldboard);
              
                //new_board.makeMove(blackMove[i];
                new_board.makeMove(blackMove);
                //evals the board at given position
                int evaluation = minimax(new_board, depth -1, false).value;
                //compares that value to max and if its higher than max then it's the new best move
                if (evaluation > max) {
                    max = evaluation;
                    bestMove = blackMove;
                    //System.out.println("HERE!");
                }
            }
            return new MovePair(bestMove, max);
        }
        else {
            min = Integer.MAX_VALUE;
           
            //do all the legal moves, reset the board after every move to try all possible moves
            //for(int i = 0; i < redMoves.length; i++){
            for (CheckersMove redMove : redMoves) {
                CheckersData new_board = new CheckersData(oldboard);
                new_board.makeMove(redMove);
                //evals the board at given position
                int evaluation = minimax(new_board, depth -1, true).value;
                //compares that value to min and if its lower than min then it's the new best move for red
                if (evaluation < min) {
                    min = evaluation;
                  
                }
            }       
        }
            return new MovePair(bestMove, min);
    }
        return new MovePair(bestMove,evaluate(oldboard));
  }
    
//    public int max_value(CheckersData newboard, int depth, boolean maximizingPlayer){
//        //CheckersData new_board = new CheckersData(newboard);
//         //Get array of all black moves
//         //CheckersMove blackMoves[] = new_board.getLegalMoves(CheckersData.BLACK);
//         //Get array of all red moves
//         //CheckersMove redMoves[] = new_board.getLegalMoves(CheckersData.RED);
//        bestMoveBlack = blackMoves[0];
//        if(depth == 0 || newboard.getLegalMoves(CheckersData.BLACK) == null){
//            return evaluate(new_board);
//        }
//        max = -9999;
//            //do all the legal moves, reset the board after every move to try all possible moves
//            //for(int i = 0; i < blackMoves.length; i++){
//            for (CheckersMove blackMove : blackMoves) {
//                new_board = new CheckersData(newboard);
//                //new_board.makeMove(redMove[i];
//                new_board.makeMove(blackMove);
//                //evals the board at given position
//                int evaluation =  max_value(new_board, depth -1, true);
//                //compares that value to max and if its higher than max then it's the new best move
//                if (evaluation > max) {
//                    max = evaluation;
//                    bestMoveBlack = blackMove;
//                }
//            }
//            return max;
//        
//    }
//    
//    public int min_value(CheckersData newboard, int depth, boolean maximizingPlayer){
//        //CheckersData new_board = new CheckersData(newboard);
//         //Get array of all black moves
//         //CheckersMove blackMoves[] = new_board.getLegalMoves(CheckersData.BLACK);
//         //Get array of all red moves
//         //CheckersMove redMoves[] = new_board.getLegalMoves(CheckersData.RED);
//        if(depth == 0 || newboard.getLegalMoves(CheckersData.RED) == null){
//            return evaluate(new_board);
//        }
//        min = 9999;
//            //do all the legal moves, reset the board after every move to try all possible moves
//            //for(int i = 0; i < redMoves.length; i++){
//            for (CheckersMove redMove : redMoves) {
//                new_board = new CheckersData(newboard);
//                //new_board.makeMove(blackMove[i];
//                new_board.makeMove(redMove);
//                //evals the board at given position
//                int evaluation =  min_value(new_board, depth -1, false);
//                //compares that value to min and if its lower than min then it's the new best move
//                if (evaluation > max) {
//                    max = evaluation;
//                    //bestMove = blackMove;
//                }
//            }
//            return min;
//        
//    }

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
