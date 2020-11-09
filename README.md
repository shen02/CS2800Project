# Lights Out SAT Solver
A program designed to solve a Lights Out game using a SAT solver. If unfamiliar with the game, please take a look at Lights Out games on [this site](https://www.geogebra.org/m/JexnDJpt) that provides various configurations of the game. 

Transparently states required packages/dependencies and how to install them

A repo-level README w/setup instructions

Diagrams of program's overall organization, structure or data flow

### Set up

so the only issue is how do you add libz3java to the java path? I think everything else is fine just by cloning all things in this repo

### Running the Solver

To start the solver, run `LightsOutSolverMain`. The following message will then print to the user:

`"Please enter the dimension of the square board, or 0 to quit: "`

If you wish to proceed with the solver, enter a positive integer. Another message will follow:

`"Please enter the number of turns to win in, or 0 to quit: "`

Enter another positive integer to proceed.

The program will then construct several boolean expressions that will be delegated to the Z3 Solver to find a winning solution.
If a winning solution is found, the program will output a solution that tells the user to click on all tiles in the same location 
as 1's in the outputted array to win the game. Such as follows:

| 1 | 0 | 1 |
|---|---|---|
| 0 | 1 | 0 |
| 1 | 0 | 1 |

Else, if no winning solution is possible with the user-input, the following message will follow:

`"Unable to solve the board in <k> moves."`

The program then exits with the below message:

`"Exited from Lights Out SAT Solver. Goodbye."`

### Program Organization

amy is doing this
