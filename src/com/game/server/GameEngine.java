/**
 * 
 */
package com.game.server;

import java.io.IOException;

import com.game.exception.ColumnFullException;
import com.game.exception.GameDrawException;
import com.game.exception.InvalidInputException;
import com.game.exception.PlayerWonException;

/**
 * @author ramamohan.gogula
 *
 */
public class GameEngine implements Runnable {
	
	int height=9;
	int width=6;

    //client1 variables.......
    Client client1;
    //client2 variables.......
    Client client2;
    //Type of game
    //ConnectFive game;
    ConnectFive game;

    public GameEngine(Client client1, Client client2) throws IOException {
        //client1 initializing......
        this.client1 = client1;
        //client2 initializing......
        this.client2 = client2;

        //assigning game program        
        game = new ConnectFive(height, width);
    }

    @Override
    public void run() {

        //initial state
        client1.write("initial state\n" + game.toString());
        client2.write("initial state\n" + game.toString());

        boolean exit = false;
        boolean chance = true;
        String c1Mark = "R";
        String c2Mark = "Y";

        while (!exit) {
            //client1 move
            if (chance) {
                exit = play(client1, client2, c1Mark);
                chance = false;
            }

            //client2 move
            else {
                exit = play(client2, client1, c2Mark);
                chance = true;
            }

        }
        client1.close();
        client2.close();
    }

    private boolean play(Client c1, Client c2, String mark) {
        String msg;
        try {
            boolean marked = false;
            while (!marked) {
                try {
//                    System.out.println("Please enter from 0-8: ");
                    c1.write("Use 0-" + (height - 1) + " to choose a column");
                    msg = c1.read();    //read client1 next move
                    game.cardinal(msg, mark); //send client1 move to the game program
                    marked = true;     // if the given move is marked successfully then exit
                }catch (InvalidInputException ge) {
                    // exception thrown by game program is Invalid column position
                  c1.write("Column must be between 0 and " + (height - 1));
              }catch (ColumnFullException ge) {
                  // exception thrown by game program is Column is full
                c1.write("Column is full\nSelect another");
            } 
               
            }

            //send current state to the players
            c1.write(game.toString());
            c2.write(game.toString());

            c1.write("Wait for "+c2.name+ " move.........");
            c2.write("Your move!!!...........");
        } catch (PlayerWonException pwe) {
            c1.write("\n............Congrats!!!.........\n....."+c1.name+" won ");
            c2.write("\n.........You Lost ");
            c1.write(game.toString());
            c2.write(game.toString());
            return true;
        } catch (GameDrawException gde) {
            c1.write("...........Game Draw!!!...........");
            c2.write("...........Game Draw!!!...........");
            c1.write(game.toString());
            c2.write(game.toString());
            return true;
        }
        return false;
    }

}
