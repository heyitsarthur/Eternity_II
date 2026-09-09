# Eternity II Puzzle Builder

Eternity 2 (E2) is an edge-matching puzzle known for being extremely difficult to solve. In fact, the best solution according to online sources so far is 467 matching edges out of 480. Our goal for this project will be to match the most edges on the 16x16 E2 board without having any leftover pieces.
Essentially, the puzzle has 256 unique pieces. Each piece is a simple square with a shape and color for each side (top, bottom, left, right). The shape and color on each side of a piece in the board must match adjacent pieces for the puzzle to be complete. There is a total of 5 edge colours and 17 shapes for a total of 22 possible edges plus the border (23 possibilities).
To render the problem amenable to an algorithmic solution, we will represent each shape and color with a number and assign that number to each edge of our pieces (0 for border, 1-22 for other edges). To match edges, we will only need to match those numbers.
Now that we have a better understanding of the task at hand, we can start thinking about our method to place the pieces on the board. One way is to build the board line by line starting with the leftmost piece. That way, we only need to match the left and top edges of the new piece each time, except for the last piece in the row (right edge must be 0) and for the bottom row (where bottom edge needs to be 0 since we reached the border). For that method, we will create a board with Border Pieces (their id is -1 to distinguish them and all edges are 0) around the board. This will cost some memory (a 16x16 board turns to an 18x18 board, more pieces to store) but ultimately will help us when selecting a new individual.


Algorithm was programmed in JAVA language.

## To execute program, copy all source files into workspace and run Main.

(Puzzle class | line: 486)
The program is set to match at least 430 edges of the 16x16 puzzle, 
440 is a viable option too but sometimes (in worst cases) runtime can be a minute.


For other sizes, change the file name in Main (Main class | line: 18)  AND 
the number of edges in the while loop inside build() (Puzzle class | line: 486). 
(total edges not correct anymore, other functions should work as intended).

PS: For any issues please contact me at: mosnier.arthur.lfgeb@gmail.com
