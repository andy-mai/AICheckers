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
        depth = 5;
        
    }

    // This is where your logic goes to make a move.
    public CheckersMove nextMove() {
        // Here are some simple ideas:
        // 1. Always pick the first move
        //return legalMoves[0];
        // 2. Pick a random move
        //return legalMoves[currentGame.generator.nextInt(legalMoves.length)];
        return minimax(currentGame.boardData, null, depth, -9999, 9999, true).move;
        
        //Or you can create a copy of the current board like this:
        //CheckersData new_board = new CheckersData(currentGame.boardData);
        //You can then simulate a move on this new board like this:
        //currentGame.simulateMove(new_board, legalMoves[0],CheckersData.BLACK); 
        //After you simulate the move you can evaluate the state of the board  
        //after the move and see how it looks.  You can evaluate all the 
        //currently legal moves using a loop and select the best one.
    }
    
    public MovePair minimax(CheckersData oldboard, CheckersMove bestMove, int depth, int alpha, int beta, boolean maximizingPlayer){
   
    if(depth <= 0 || oldboard.getLegalMoves(CheckersData.BLACK) == null || oldboard.getLegalMoves(CheckersData.RED) == null ){
            return new MovePair(bestMove,evaluate(oldboard));
        }
    //CheckersData new_board = new CheckersData(oldboard);
    
    //Get array of all black moves
    CheckersMove blackMoves[] = oldboard.getLegalMoves(CheckersData.BLACK);
    //CheckersMove bestMove = blackMoves[0];
    //Get array of all red moves
    CheckersMove redMoves[] = oldboard.getLegalMoves(CheckersData.RED);
            
            if (maximizingPlayer){
             MovePair max = new MovePair(bestMove,Integer.MIN_VALUE);
            //int max = Integer.MIN_VALUE;
        //do all the legal moves, reset the board after every move to try all possible moves
        for(int i = 0; i < blackMoves.length; i++){
        //for (CheckersMove blackMove : blackMoves) {
          
            
            CheckersData new_board = new CheckersData(oldboard);
            new_board.makeMove(blackMoves[i]);
            //new_board.makeMove(blackMove);
            
//                if(new_board.getLegalJumpsFrom(new_board.pieceAt(blackMoves[i].toRow, blackMoves[i].toCol),
//                            blackMoves[i].toRow, blackMoves[i].toCol) != null) { maximizingPlayer = !maximizingPlayer; }

            //private boolean canJump(int player, int r1, int c1, int r2, int c2, int r3, int c3)
//                for(int i = 0; i < redMoves.length; i++){ //try to check and see if any red can jump after making black move, if red can, remove black move from array?
//                    if(canJump(redMoves[i],new_board.pieceAt(, i)))
//                }
//evals the board at given position

        MovePair evaluation = null;
       
        if (bestMove == null) {
                        evaluation= minimax(new_board, blackMoves[i], depth -1, alpha, beta, !maximizingPlayer);
                    }else {
                        evaluation = minimax(new_board, bestMove, depth -1, alpha, beta, !maximizingPlayer);
                    }

//        int evaluation = minimax(new_board, depth -1, alpha, beta, !maximizingPlayer).value;
        
        //compares that value to max and if its higher than max then it's the new best move
        if(Math.max(max.value, evaluation.value) == evaluation.value){
            //if (max.value <= evaluation.value) {
                max = evaluation;
                //bestMove = blackMoves[i]; 
            }
            alpha = Math.max(alpha, evaluation.value);
            if (beta <= alpha){
                break;
            }
        }
        
            
            //return new MovePair(bestMove, max);
            return max;
        }
        else {
            MovePair min = new MovePair(bestMove, Integer.MAX_VALUE);
            
            //do all the legal moves, reset the board after every move to try all possible moves
            for(int i = 0; i < redMoves.length; i++){
            //for (CheckersMove redMove : redMoves) {
                CheckersData new_board = new CheckersData(oldboard);
                new_board.makeMove(redMoves[i]);
                
//                if(new_board.getLegalJumpsFrom(new_board.pieceAt(redMoves[i].toRow, redMoves[i].toCol),
//                            redMoves[i].toRow, redMoves[i].toCol) != null) { maximizingPlayer = !maximizingPlayer; }
                
                //evals the board at given position
                MovePair evaluation = minimax(new_board, bestMove, depth -1, alpha, beta, !maximizingPlayer);
                //compares that value to min and if its lower than min then it's the new best move for red
                if(Math.min(min.value, evaluation.value) == evaluation.value){
                //if (min.value >= evaluation.value) {
                    min = evaluation;
                }   
                beta = Math.min(beta, evaluation.value);
                if (beta <= alpha){
                    break;
                }
                
            }    
             return min; //returns movepair min which will old the bestmove and min value
        }
            
    }
        
    
   
    // One thing you will probably want to do is evaluate the current
    // goodness of the board.  This is a toy example, and probably isn't
    // very good, but you can tweak it in any way you want.  Not only is
    // number of pieces important, but board position could also be important.
    // Also, are kings more valuable than regular pieces?  How much?
    int evaluate(CheckersData board) { 
    int score = 0;
  
      int totalPieces = (board.getNumPieces(CheckersData.RED)) + (board.getNumPieces(CheckersData.BLACK));
      int totalKings = (board.numBlackKing() + board.numRedKing());
      
     
            double boardValues[][] = {
              {6, 0, 6, 0, 6, 0, 6, 0},
              {0, 3, 0, 3, 0, 3, 0, 6},
              {6, 0, 3, 0, 3, 0, 4, 0},
              {0, 2, 0, 4, 0, 3, 0, 6},
              {6, 0, 2, 0, 3, 0, 1, 0},
              {0, 2, 0, 3, 0, 3, 0, 6},
              {6, 0, 3, 0, 3, 0, 3, 0},
              {0, 4, 0, 4, 0, 4, 0, 4}

      };

//          double boardValues[][] = {
//              {4, 0, 4, 0, 4, 0, 4, 0},
//              {0, 2, 0, 2, 0, 2, 0, 4},
//              {3, 0, 3, 0, 3, 0, 2, 0},
//              {0, 2, 0, 4, 0, 3, 0, 3},
//              {3, 0, 3, 0, 4, 0, 2, 0},
//              {0, 2, 0, 3, 0, 3, 0, 3},
//              {4, 0, 2, 0, 2, 0, 2, 0},
//              {0, 4, 0, 4, 0, 4, 0, 4}
//
//      };
            
            double rowValues[][] = {
              {5, 0, 5, 0, 5, 0, 5, 0},
              {0, 1, 0, 3, 0, 3, 0, 1},
              {1, 0, 3, 0, 3, 0, 2, 0},
              {0, 2, 0, 5, 0, 3, 0, 1},
              {1, 0, 3, 0, 5, 0, 2, 0},
              {0, 2, 0, 3, 0, 3, 0, 1},
              {1, 0, 3, 0, 3, 0, 1, 0},
              {0, 5, 0, 5, 0, 5, 0, 5}

      };
            double endValues[][] = {
              {5, 0, 5, 0, 5, 0, 5, 0},
              {0, 10, 0, 10, 0, 10, 0, 10},
              {10, 0, 20, 0, 20, 0, 10, 0},
              {0, 20, 0, 20, 0, 20, 0, 10},
              {10, 0, 20, 0, 20, 0, 10, 0},
              {0, 20, 0, 20, 0, 20, 0, 10},
              {10, 0, 10, 0, 10, 0, 10, 0},
              {0, 10, 0, 10, 0, 10, 0, 10}

      };
            double halfValues[][] = {
              {5, 0, 5, 0, 5, 0, 5, 0},
              {0, 5, 0, 5, 0, 5, 0, 5},
              {5, 0, 6, 0, 6, 0, 5, 0},
              {0, 5, 0, 6, 0, 6, 0, 5},
              {5, 0, 6, 0, 6, 0, 5, 0},
              {0, 5, 0, 6, 0, 6, 0, 5},
              {5, 0, 5, 0, 5, 0, 5, 0},
              {0, 5, 0, 5, 0, 5, 0, 5}

      };
      
          if (totalKings == totalPieces){
          System.out.print("Endgame ");
          score = 0;
          score += (10*board.numBlack())
                + (20*board.numBlackKing())
                - (5*board.numRed())
                - (10*board.numRedKing());
          
     } //else {
         score += (2*board.numBlack())
                + (board.numBlackKing())
                - (2*board.numRed())
                - (3*board.numRedKing());
            
         for(int col = 0; col < 8; col++){
            for(int row = Math.floorMod(col,2); row < 8; row+=2){ 
                int piece = board.pieceAt(row,col);
                switch(piece){
                        case CheckersData.BLACK:
                                score += (2*boardValues[row][col]); 
                                score += (3*rowValues[row][col]);
                                //score += (halfValues[row][col]);
                                break;
                        case CheckersData.BLACK_KING:
                               score += (2*boardValues[row][col]); 
                               score += (3*rowValues[row][col]);
                               //score += (3*halfValues[row][col]);
                                break;
                        case CheckersData.RED:
                                score -= (2*boardValues[row][col]);
                                //score -= (3*rowValues[row][col]); 
                                //score -= (halfValues[row][col]);
                                break;
                        case CheckersData.RED_KING:
                                score -= (5*boardValues[row][col]); 
                                //score += (5*rowValues[row][col]);
                                //score -= (3*halfValues[row][col]);
                                break;
                }
            }
        }
         
          System.out.println(score);
        //  }
        return score;
    }
}
