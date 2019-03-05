//package com.casestudy;

import java.util.Scanner;

/**
 * @version 0.1
 * @author serialgeek
 * StandAlone class to simulate a game of Battle Ships.
 * A standard two player game where the input is accepted from
 * the command line at the beginning. The progress of the game can be viewed 
 * after all the required data has been entered into the system.
 * 
 * Inputs contain dimensions of the battle area (x & y for bottom right corner), battleship
 * type, dimensions (Width X Height) & positions (x & y) for Player-1 and then for Player-2.
 * Finally, Player-1’s sequence of missile target cells (x & y) and Player-2’s sequence.
 * 
 * Constraints:
 * 1 <= Width of Battle area (M’) <= 9,
 * A <= Height of Battle area (N’) <= Z
 * 1 <= Number of battleships <= M’ * N’
 * Type of ship = {‘P’, ‘Q’}
 * 1 <= Width of battleship <= M’
 * A <= Height of battleship <= N’
 * 1 <= X coordinate of ship <= M’
 * A <= Y coordinate of ship <= N’
 * 
 * Each player gets the same number of battleships of a particular type 
 * placed on the battle area. One player cannot see the other player’s battleship locations.
 * 
 * Both players will get a chance to launch missiles one by one. Eg. If Player-1 fires a
 * missile into Player-2’s battle area, targeting some location (eg. E1), and the missile hits,
 * Player-2 needs to communicate to Player-1 that there was a hit.
 * In the example above, the missile hits the ship on E1. In this case, Player-1 gets another
 * chance of firing as he successfully hit Player-2’s ship. The game continues like this. If the
 * missile misses, then Player-2 gets to fire. Players may have different number of missiles.
 * If a ship is hit in all the cells, then that ship is considered destroyed. Eg. if E1, E2, E3 and
 * E4 from Player-2’s battle area are hit by the Player-1, then that ship is considered
 * destroyed. Note that only upon hitting a live cell, a player will get another chance. Ships
 * are classified as type P or Q. Type P ships are weaker than type Q ships. Each cell of a
 * Type-Q ship requires 2 accurate missile hits to be destroyed whereas Type-P ship cells
 * will be destroyed only by 1 missile hit.
 * 
 * Sample Input:
 * 
 * Enter area boundaries: 5 E
 * Type for battleship 1: Q
 * Dimension for battleship 1: 1 1
 * Location of battleship 1 for player A: A1
 * Location of battleship 1 for player B: B2
 * Type for battleship 2: P
 * Dimension for battleship 2: 2 1
 * Location of battleship 2 for player A: D4
 * Location of battleship 2 for player B: C3
 * Missile targets for player A: A1 B2 B2 B3
 * Missile targets for player B: A1 B2 B3 A1 D1 E1 D4 D4 D5 D5
 * 
 * Sample Output:
 * 
 * Player-1 fires a missile with target A1 which missed
 * Player-2 fires a missile with target A1 which hit
 * Player-2 fires a missile with target B2 which missed
 * Player-1 fires a missile with target B2 which hit
 * Player-1 fires a missile with target B2 which hit
 * Player-1 fires a missile with target B3 which missed
 * Player-2 fires a missile with target B3 which missed
 * Player-1 has no more missiles left
 * Player-2 fires a missile with target A1 which hit
 * Player-2 fires a missile with target D1 which missed
 * Player-1 has no more missiles left
 * Player-2 fires a missile with target E1 which missed
 * Player-1 has no more missiles left
 * Player-2 fires a missile with target D4 which hit
 * Player-2 fires a missile with target D4 which missed
 * Player-1 no more missiles left
 * Player-2 fires a missile with target D5 which hit
 * Player-2 won the battle
 * 
 *
 */
public class BattleShip {
    
    private static Scanner scan = new Scanner(System.in);
    
    private static int tot_row_count = 0;
    
    private static int tot_col_count = 0;
    
    private static char[][] battle_area_A;
    
    private static char[][] battle_area_B;
    
    private static int cell_count_A = 0;
    
    private static int cell_count_B = 0;
    
    private static String[] missile_targets_A;
    
    private static String[] missile_targets_B;
    
    /**
     * Displays a blank line on the console
     */
    public final static void log() {
        log(" ");
    }
    
    /**
     * Displays the toString() value of the object on the console
     * @param obj - Object to be printed on the console
     */
    public final static void log(Object obj) {
        
        System.out.println(obj.toString());
    }
    
    /**
     * For Testing
     * Displays a 2-d char array on the console
     * @param charArr - 2-d char array
     */
    public final static void logCharArr(char[][] charArr) {
        for (int rowCounter = 0; rowCounter < charArr.length; rowCounter++) {
            for (int colCounter = 0; colCounter < charArr[rowCounter].length; colCounter++) {
                System.out.print(charArr[rowCounter][colCounter] + " ");
            }
            System.out.println();
        }
    }
    
    /**
     * Populates the battle areas with the ship layout
     * @param startRow - start index for row
     * @param endRow - end index for row
     * @param startCol - start index for column
     * @param endCol - end index for column
     * @param shipType - type of ship - 'P' or 'Q'
     * @param player - Player A or Player B
     */
    private static void populateBattleShip(int startRow, int endRow, int startCol, int endCol, char shipType, char player) {
        
        for (int rowCounter = startRow; rowCounter <= endRow; rowCounter++) {
            for (int colCounter = startCol; colCounter <= endCol; colCounter++) {
                switch (player) {
                case 'A':
                    battle_area_A[rowCounter][colCounter] = shipType;
                    if (shipType == 'P') {
                        cell_count_A ++;
                    }
                    else {
                        cell_count_A += 2;
                    }
                    break;
                case 'B':
                    battle_area_B[rowCounter][colCounter] = shipType;
                    if (shipType == 'P') {
                        cell_count_B ++;
                    }
                    else {
                        cell_count_B += 2;
                    }
                    break;
                }

            }
        }
    }
    
    /**
     * Populates battle area with the ships by calling populateBattleShip()
     * @param width - width of ship
     * @param height - height of ship
     * @param bShip1LocA - Ship 1 location for player A
     * @param bShip1LocB - Ship 1 location for player B
     * @param shipType - Type of Ship - 'P' or 'Q'
     */
    private static void populateBattleField(int width, int height, String bShip1LocA, String bShip1LocB, char shipType) {
        
        int startRowA = bShip1LocA.charAt(0) - 'A' + 1;
        int endRowA = startRowA + height - 1;
        int startColA = bShip1LocA.charAt(1) - '0';
        int endColA = startColA + width - 1;
        
        populateBattleShip(startRowA, endRowA, startColA, endColA, shipType, 'A');
        // logCharArr(battle_area_A);
        
        int startRowB = bShip1LocB.charAt(0) - 'A' + 1;
        int endRowB = startRowB + height - 1;
        int startColB = bShip1LocB.charAt(1) - '0';
        int endColB = startColB + width - 1;
        
        populateBattleShip(startRowB, endRowB, startColB, endColB, shipType, 'B');
        // logCharArr(battle_area_B);

    }
    
    /**
     * Initializes missile targets for both players
     */
    private static void initMissileTargets() {
        scan.nextLine();
        log("Missile targets for player A: ");
        missile_targets_A = scan.nextLine().split(" ");
        log("Missile targets for player B: ");
        missile_targets_B = scan.nextLine().split(" ");
    }
    
    /**
     * Returns index of given position
     * @param position - either character (A - Z) or numeric (1 - 9)
     * @return - index of given position
     */
    private static int index(int position) {
        if (position >= 65 && position <= 90) {
            return position - 'A' + 1;
        }
        return position - '0';
    }
    
    /**
     * Verifies if the ship is struck at a given cell
     * @param battleArea - battle area where the game is being played
     * @param row - row of attack
     * @param col - column of attack
     * @return - true if the cell is hit successfully
     */
    private static boolean isBattleShipHit(char[][] battleArea, int row, int col) {
        // log("Cell hit: " + row + " " +col + battleArea[row][col]);
        if (battleArea[row][col] == 'P') {
            battleArea[row][col] = 'X';
            return true;
        }
        else if (battleArea[row][col] == 'Q') {
            battleArea[row][col] = 'q';
            return true;
        }
        else if (battleArea[row][col] == 'q') {
            battleArea[row][col] = 'X';
            return true;
        }
        return false;
    }
    
    /**
     * Initializes the game play between two players
     */
    private static void initGamePlay() {
        int counterA = 0, counterB = 0, hitsPlayerA =0, hitsPlayerB = 0;
        boolean isPlayerA = true;
        while(counterA < missile_targets_A.length || counterB < missile_targets_B.length) {
            if (counterA < missile_targets_A.length && isPlayerA) {

                int missileHitRow = index(missile_targets_A[counterA].charAt(0));
                int missileHitCol = index(missile_targets_A[counterA].charAt(1));
                if (!isBattleShipHit(battle_area_B, missileHitRow, missileHitCol)) {
                    isPlayerA = !isPlayerA;
                    log(String.format("Player-1 fires a missile with target %s which missed",
                            missile_targets_A[counterA]));
                } else {
                    hitsPlayerA++;
                    log(String.format("Player-1 fires a missile with target %s which hit",
                            missile_targets_A[counterA]));
                    if (hitsPlayerA == cell_count_B) {
                        log("Player-1 won the battle");
                        break;
                    }
                }
                counterA++;
            }
            else if (counterA >= missile_targets_A.length) {

                log("Player-1 has no more missiles left");
                if (isPlayerA) {
                    isPlayerA = !isPlayerA;
                }

            }
            
            if (counterB < missile_targets_B.length && !isPlayerA) {
                int missileHitRow = index(missile_targets_B[counterB].charAt(0));
                int missileHitCol = index(missile_targets_B[counterB].charAt(1));
                if (!isBattleShipHit(battle_area_A, missileHitRow, missileHitCol)) {
                    isPlayerA = !isPlayerA;
                    log(String.format("Player-2 fires a missile with target %s which missed", missile_targets_B[counterB]));
                }
                else {
                    hitsPlayerB++;
                    log(String.format("Player-2 fires a missile with target %s which hit", missile_targets_B[counterB]));
                    if (hitsPlayerB == cell_count_A) {
                        log("Player-2 won the battle");
                        break;
                    }
                }
                counterB++;
            }
            else if (counterB >= missile_targets_B.length) {

                log("Player-2 has no more missiles left");
                if (!isPlayerA) {
                    isPlayerA = !isPlayerA;
                }

            }
        }
        
        if (counterA >= missile_targets_A.length && counterB >= missile_targets_B.length) {
            log("No Winner. Player-1 and Player-2 have sued for peace.");
        }
        
        
    }
    
    /**
     * Initializes the battle areas for the two players
     */
    private static void initBattleAreas() {
        
        log("Enter area boundaries: ");
        tot_col_count = scan.nextInt() + 1;
        // log(tot_col_count);
        
        tot_row_count = (scan.next().charAt(0) - 'A') + 2;
        // log(tot_row_count);
        
        battle_area_A = new char[tot_row_count][tot_col_count];
        battle_area_B = new char[tot_row_count][tot_col_count];
    }
    
    /**
     * Populates the battle field by calling populateBattleField()
     * for the two areas
     */
    private static void populateBattleFields() {
        log("Type for battleship 1: ");
        char shipType1 = scan.next().charAt(0);
        
        log("Dimension for battleship 1: ");
        int width = scan.nextInt();
        int height = scan.nextInt();
        
        log("Location of battleship 1 for player A: ");
        String bShipLocA = scan.next();
        log("Location of battleship 1 for player B: ");
        String bShipLocB = scan.next();
        
        populateBattleField(width, height, bShipLocA, bShipLocB, shipType1);
        
        log("Type for battleship 2: ");
        char shipType2 = scan.next().charAt(0);
        while (shipType2 == shipType1) {
            log("Error - This type of battleship has already been utilized. Please select another type");
            shipType2 = scan.next().charAt(0);
        }
        
        log("Dimension for battleship 2: ");
        width = scan.nextInt();
        height = scan.nextInt();
        
        log("Location of battleship 2 for player A: ");
        bShipLocA = scan.next();
        log("Location of battleship 2 for player B: ");
        bShipLocB = scan.next();
        
        populateBattleField(width, height, bShipLocA, bShipLocB, shipType2);
    }
    
    /**
     * Initializes the game with the input data
     */
    private static void initInputData() {
        
        initBattleAreas();

        populateBattleFields();
        
        initMissileTargets();
        
    }

    /**
     * Static main method from where class execution starts
     * @param args - Input arguments if any
     */
    public static void main(String[] args) {
        
        initInputData();
        
        log();
        
        initGamePlay();

    }

}
