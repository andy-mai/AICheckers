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
        return minimax(currentGame.boardData, depth, -9999, 9999, true).move;
        
        //Or you can create a copy of the current board like this:
        //CheckersData new_board = new CheckersData(currentGame.boardData);
        //You can then simulate a move on this new board like this:
        //currentGame.simulateMove(new_board, legalMoves[0],CheckersData.BLACK); 
        //After you simulate the move you can evaluate the state of the board  
        //after the move and see how it looks.  You can evaluate all the 
        //currently legal moves using a loop and select the best one.
    }
    
    public MovePair minimax(CheckersData oldboard, int depth, int alpha, int beta, boolean maximizingPlayer){
   
    CheckersMove bestMove = null;
    //CheckersData new_board = new CheckersData(oldboard);
    
    //Get array of all black moves
    CheckersMove blackMoves[] = oldboard.getLegalMoves(CheckersData.BLACK);
    //Get array of all red moves
    CheckersMove redMoves[] = oldboard.getLegalMoves(CheckersData.RED);
    
    
        if(depth == 0 || oldboard.getLegalMoves(CheckersData.BLACK) == null || oldboard.getLegalMoves(CheckersData.RED) == null ){
            return new MovePair(bestMove,evaluate(oldboard));
        }
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
//                if(new_board.getLegalJumpsFrom(new_board.pieceAt(blackMove.toRow, blackMove.toCol),
//                            blackMove.toRow, blackMove.toCol) == null) {
//                } else {
//                    //bestMove = blackMove;
//                    maximizingPlayer = !maximizingPlayer;
//                }
                //evals the board at given position
                int evaluation = minimax(new_board, depth -1, alpha, beta, false).value;
                //compares that value to max and if its higher than max then it's the new best move
                if (evaluation > max) {
                    max = evaluation;
                    bestMove = blackMove;
                }
                alpha = Math.max(alpha, evaluation);
                if (beta <= alpha){
                    break;
                }
            }
            //System.out.println(max);
            return new MovePair(bestMove, max);
        }
        else {
            min = Integer.MAX_VALUE;
            
            //do all the legal moves, reset the board after every move to try all possible moves
            //for(int i = 0; i < redMoves.length; i++){
            for (CheckersMove redMove : redMoves) {
                CheckersData new_board = new CheckersData(oldboard);
                new_board.makeMove(redMove);
//                if(new_board.getLegalJumpsFrom(new_board.pieceAt(redMove.toRow, redMove.toCol),
//                            redMove.toRow, redMove.toCol) != null)
//                        maximizingPlayer = !maximizingPlayer;
                //evals the board at given position
                int evaluation = minimax(new_board, depth -1, alpha, beta, true).value;
                //compares that value to min and if its lower than min then it's the new best move for red
                if (evaluation < min) {
                    min = evaluation;
                }
                beta = Math.min(beta, evaluation);
                if (beta <= alpha){
                    break;
                }
                
            }       
        }
            
            return new MovePair(bestMove, min);
    }
        
  
    
    // One thing you will probably want to do is evaluate the current
    // goodness of the board.  This is a toy example, and probably isn't
    // very good, but you can tweak it in any way you want.  Not only is
    // number of pieces important, but board position could also be important.
    // Also, are kings more valuable than regular pieces?  How much?
    int evaluate(CheckersData board) { 
        
    int score = (board.numBlack())
                + (3*board.numBlackKing())
                - (board.numRed())
                - (3*board.numRedKing());
    
            double boardValues[][] = {
              {4, 0, 4, 0, 4, 0, 4, 0},
              {0, 2, 0, 2, 0, 2, 0, 4},
              {4, 0, 3, 0, 4, 0, 1, 0},
              {0, 2, 0, 4, 0, 3, 0, 4},
              {4, 0, 3, 0, 4, 0, 2, 0},
              {0, 1, 0, 3, 0, 3, 0, 4},
              {4, 0, 2, 0, 2, 0, 1, 0},
              {0, 4, 0, 4, 0, 4, 0, 4}

      };
            
            double rowValues[][] = {
              {5, 0, 5, 0, 5, 0, 5, 0},
              {0, 1, 0, 1, 0, 1, 0, 1},
              {1, 0, 1, 0, 1, 0, 1, 0},
              {0, 2, 0, 1, 0, 1, 0, 1},
              {1, 0, 1, 0, 1, 0, 1, 0},
              {0, 1, 0, 1, 0, 1, 0, 1},
              {1, 0, 1, 0, 1, 0, 1, 0},
              {0, 5, 0, 5, 0, 5, 0, 5}

      };
            double halfValues[][] = {
              {5, 0, 5, 0, 5, 0, 5, 0},
              {0, 5, 0, 5, 0, 5, 0, 5},
              {5, 0, 5, 0, 5, 0, 5, 0},
              {0, 5, 0, 5, 0, 5, 0, 5},
              {5, 0, 5, 0, 5, 0, 5, 0},
              {0, 5, 0, 5, 0, 5, 0, 5},
              {5, 0, 5, 0, 5, 0, 5, 0},
              {0, 5, 0, 5, 0, 5, 0, 5}

      };
         
        for(int y = 0; y < 8; y++){
            for(int x = Math.floorMod(y,2); x < 8; x+=2){ //x starts at 0 when y is even and starts at 1 when y is odd
                int piece = board.pieceAt(x,y);
                switch(piece){
                        case CheckersData.BLACK:
                                score += (3 * boardValues[x][y]); //3
                                score += (3 * rowValues[x][y]);
                                score += (3 * halfValues[x][y]);
                                break;
                        case CheckersData.BLACK_KING:
                                score += (5 * boardValues[x][y]); //5
                                score += (5 * halfValues[x][y]);
                                break;
                        case CheckersData.RED:
                                score -= (3 * rowValues[x][y]); //3
                                score -= (3 * boardValues[x][y]);
                                score -= (3 * halfValues[0][0]);
                                break;
                        case CheckersData.RED_KING:
                                score -= (5 * boardValues[x][y]); //5
                                score -= (5 * halfValues[x][y]);
                                break;
                }
            }
        }
        System.out.println(score);

                   
          
    
    return score;
    }
}
