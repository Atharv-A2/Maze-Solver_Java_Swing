import javax.swing.*;
import java.awt.*;
import java.lang.Thread;

public class MazeSolverVisualizer extends JPanel{

    private static final int CELL_SIZE = 40;          //Size of Each Cell
    private char[][] maze;
    private boolean[][] visited;

    public MazeSolverVisualizer(char[][] maze) {
        this.maze = maze;
        this.visited = new boolean[maze.length][maze[0].length];
        setPreferredSize(new Dimension(maze[0].length * CELL_SIZE, maze.length * CELL_SIZE));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[0].length; col++) {
                char cell = maze[row][col];
                Color color = getColorForCell(cell, visited[row][col]);

                g.setColor(color);
                g.fillRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }

    private Color getColorForCell(char cell, boolean isVisited) {            //Deciding the Colour for Each Cell used
        if (isVisited) {
            return Color.GREEN;
        }

        switch (cell) {
            case 'S':
                return Color.GRAY;
            case 'E':
                return Color.RED;
            case '#':
                return Color.BLACK;
            case 'X':
                return Color.YELLOW;
            default:
                return Color.WHITE;
        }
    }

    public boolean solveMaze(int startRow, int startCol) {
        int numRows = maze.length;
        int numCols = maze[0].length;

        if (startRow < 0 || startRow >= numRows || startCol < 0 || startCol >= numCols) {
            return false;
        }

        if (maze[startRow][startCol] == 'E') {
            return true; // Reached the endpoint
        }

        if (maze[startRow][startCol] == '#' || visited[startRow][startCol]) {
            return false; // Hit a wall or already visited
        }

        // Mark the current cell as visited
        visited[startRow][startCol] = true;
        try{
            Thread.sleep(150);
        }
        catch(InterruptedException ae){
            System.out.println("Exceptions ");
        }

        repaint();    // Update the colour visualization

        // Try moving in all four possible directions: up, down, left, right
        if (solveMaze(startRow - 1, startCol) ||
            solveMaze(startRow + 1, startCol) ||
            solveMaze(startRow, startCol + 1) ||
            solveMaze(startRow, startCol - 1)) {
            return true; // Found a path
        }

        // No path found, backtrack
        visited[startRow][startCol] = false;
        repaint(); // Update the visualization
        return false;
    }

    public static void main(String[] args) {
        // char[][] maze = {
        //     {'S', '.', '#', '#', '.', '.'},
        //     {'.', '#', '.', '.', '.', '#'},
        //     {'.', '#', '.', '#', '.', '.'},
        //     {'.', '.', '.', '.', '#', 'E'},
        //     {'.', '#', '.', '.', '.', '.'}
        // };

        char[][] maze = {
            {'S', '.', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#','#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
            {'#', '.', '.', '#', '.', '#', '.', '.', '#', '#', '.', '.', '.', '.','.', '#', '#', '.', '#', '#', '.', '.', '.', '.', '.', '.', '#'},
            {'#', '#', '.', '#', '.', '.', '.', '#', '#', '#', '#', '#', '#', '#','.', '.', '.', '.', '.', '#', '#', '#', '#', '#', '#', '.', '#'},
            {'#', '.', '.', '.', '.', '#', '.', '.', '.', '.', '.', '.', '.', '.','.', '#', '.', '#', '.', '.', '.', '.', '.', '.', '.', '.', '#'},
            {'#', '#', '#', '.', '#', '#', '.', '#', '.', '#', '.', '#', '#', '#','#', '#', '#', '#', '#', '#', '#', '.', '#', '#', '#', '.', '#'},
            {'#', '.', '.', '.', '.', '#', '#', '#', '.', '#', '#', '#', '#', '.','.', '.', '.', '.', '.', '.', '.', '.', '#', '.', '.', '.', '#'},
            {'#', '.', '#', '.', '#', '#', '#', '#', '.', '.', '#', '.', '#', '#','#', '.', '#', '#', '#', '#', '#', '#', '#', '#', '#', '.', '#'},
            {'#', '#', '#', '#', '#', '#', '.', '#', '#', '.', '.', '.', '#', '.','#', '#', '#', '#', '#', '#', '.', '.', '.', '.', '.', '.', '#'},
            {'#', '.', '.', '.', '.', '.', '.', '#', '#', '#', '#', '.', '#', '.','.', '.', '.', '.', '.', '.', '.', '#', '.', '#', '.', '#', '#'},
            {'#', '#', '.', '#', '.', '#', '.', '.', '.', '.', '.', '.', '.', '.','#', '#', '.', '#', '.', '#', '.', '#', '.', '#', '.', '.', '#'},
            {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#','#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', 'E', '#'}
        };

        MazeSolverVisualizer visualizer = new MazeSolverVisualizer(maze);

        JFrame frame = new JFrame("Maze Solver Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(visualizer);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Solve the maze
        int startRow = 0;
        int startCol = 0;
        long st = System.nanoTime();
        boolean pathFound = visualizer.solveMaze(startRow, startCol);
        long et = System.nanoTime();
        if (pathFound) {
            System.out.println("Path found!");
            System.out.println("Time - "+(et-st));
        } else {
            System.out.println("No path found.");
        }
        //maze[0][0] = 
    }
}
