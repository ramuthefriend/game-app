package com.game.server;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.game.exception.ColumnFullException;
import com.game.exception.GameDrawException;
import com.game.exception.InvalidInputException;
import com.game.exception.PlayerWonException;
/**
 * @author ramamohan.gogula
 *
 */
public class ConnectFive {
	// dimensions for our board
		private final int width, height;
		// grid for the board
		private final char[][] grid;
		// we store last move made by a player
		private int lastCol = -1, lastTop = -1;
		
		private int moves;
		int count;
		
		public ConnectFive(int w, int h) {
			width = w;
			height = h;
			moves=w*h;
			count=0;
			grid = new char[h][];
			// init the grid will blank cell
			for (int i = 0; i < h; i++) {
				Arrays.fill(grid[i] = new char[w], '_');
			}
		}
		
		// we use Streams to make a more concise method
		// for representing the board
		public String toString() {
			return IntStream.range(0, width).mapToObj(Integer::toString).collect(Collectors.joining()) + "\n"
					+ Arrays.stream(grid).map(String::new).collect(Collectors.joining("\n"));
		}
		
		public void cardinal(String msg, String mark) throws InvalidInputException, ColumnFullException, GameDrawException, PlayerWonException {
	        int position = Integer.parseInt(msg);
	        
	        chooseAndDrop(mark.charAt(0),position);
	        continueGameOrNot();
	    }

		private boolean continueGameOrNot() throws GameDrawException, PlayerWonException{
			if(isWinningPlay()) {
				throw new PlayerWonException("");
			}else if(count==moves) {
				throw new GameDrawException();
			}
			return true;
		}
		
		// get string representation of the row containing
		// the last play of the user
		public String horizontal() {
			return new String(grid[lastTop]);
		}

		// get string representation fo the col containing
		// the last play of the user
		public String vertical() {
			StringBuilder sb = new StringBuilder(height);

			for (int h = 0; h < height; h++) {
				sb.append(grid[h][lastCol]);
			}

			return sb.toString();
		}

		// get string representation of the "/" diagonal
		// containing the last play of the user
		public String slashDiagonal() {
			StringBuilder sb = new StringBuilder(height);

			for (int h = 0; h < height; h++) {
				int w = lastCol + lastTop - h;

				if (0 <= w && w < width) {
					sb.append(grid[h][w]);
				}
			}

			return sb.toString();
		}

		// get string representation of the "\"
		// diagonal containing the last play of the user
		public String backslashDiagonal() {
			StringBuilder sb = new StringBuilder(height);

			for (int h = 0; h < height; h++) {
				int w = lastCol - lastTop + h;

				if (0 <= w && w < width) {
					sb.append(grid[h][w]);
				}
			}

			return sb.toString();
		}

		// static method checking if a substring is in str
		public static boolean contains(String str, String substring) {
			return str.indexOf(substring) >= 0;
		}

		
		// now, we create a method checking if last play is a winning play
		public boolean isWinningPlay() {
			if (lastCol == -1) {
				System.err.println("No move has been made yet");
				return false;
			}

			char sym = grid[lastTop][lastCol];
			// winning streak with the last play symbol
			String streak = String.format("%c%c%c%c%c", sym, sym, sym, sym,sym);

			// check if streak is in row, col,
			// diagonal or backslash diagonal
			return contains(horizontal(), streak) || contains(vertical(), streak) || contains(slashDiagonal(), streak)
					|| contains(backslashDiagonal(), streak);
		}

			private void chooseAndDrop(char symbol, int position) throws InvalidInputException, ColumnFullException {
				int col = position;
				boolean isPlaced = false;
				// check if column is ok
				if (!(0 <= col && col < width)) {
					throw new InvalidInputException();
					// System.out.println("Column must be between 0 and " + (width - 1));
					// continue;
				}

				// now we can place the symbol to the first
				// available row in the asked column
				for (int h = height - 1; h >= 0; h--) {
					if (grid[h][col] == '_') {
						grid[lastTop = h][lastCol = col] = symbol;
						isPlaced = true;
						count++;
						break;
					}
				}
				if (!isPlaced) {
					throw new ColumnFullException();
				}

			}

		}



