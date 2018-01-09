package ca.personalprojects;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * MathReader.
 *
 * @author Andy Loi, Set B
 * @version 3.1
 */
public class MathReader {
    /**
     * Stores a list of doubles.
     */
    private static ArrayList<Double> list;
    
    /**
     * Stores a list of arithmetic symbols.
     */
    private static ArrayList<Character> symbols;
    
    /**
     * A private scanner to be instanced.
     */
    private static Scanner scan;
    
    /**
     * The arithmetic symbol for addition.
     */
    private static final char PLUS = '+';
    
    /**
     * The arithmetic symbol for subtraction.
     */
    private static final char MINUS = '-';
    
    /**
     * The arithmetic symbol for multiplication.
     */
    private static final char MULTIPLY = '*';
    
    /**
     * The arithmetic symbol for division.
     */
    private static final char DIVIDE = '/';
    
    /**
     * A place holder character.
     */
    private static final char PLACE_HOLDER = 'A';
    
    /**
     * State of which and error has occurred.
     */
    private static boolean notANumber;
    
    /**
     * Contructor of MathReader.
     * 
     * @param input to be read.
     */
    public MathReader(String input) {
        notANumber = false;
        parseData(input);
        ordersOfOperation();
    }
    
    /**
     * Returns the result as a string.
     * 
     * @return string
     */
    public String result() {
        if (notANumber) {
            return "MATH ERROR";
        } else {
            return "" + list.get(0);
        }
    }
    /**
     * Parses a string into two separate ArrayLists.
     *
     * @param input a string
     */
    public static void parseData(String input) {
        list = new ArrayList<Double>();
        String temp = input.replace(PLUS, PLACE_HOLDER);
        temp = temp.replace(MINUS, PLACE_HOLDER);
        temp = temp.replace(MULTIPLY, PLACE_HOLDER);
        temp = temp.replace(DIVIDE, PLACE_HOLDER);
        scan = new Scanner(temp);
        scan.useDelimiter("" + PLACE_HOLDER);
        
        while (scan.hasNextDouble()) {
            list.add(scan.nextDouble());
        }
        
        symbols = new ArrayList<Character>();
        for (int count = 0; count < input.length(); count++) {
            if ((input.charAt(count) == PLUS) || (input.charAt(count) == MINUS) 
                    || (input.charAt(count) == MULTIPLY) 
                    || (input.charAt(count) == DIVIDE)) {
                symbols.add(input.charAt(count));
            }
        }
    }
    
    /**
     * The multiplication arithmetic method.
     */
    private static void multiply() {
        // position of the symbol.
        int position = symbols.indexOf(MULTIPLY);
        
        // find the numbers and do the math.
        double x = list.get(position);
        double y = list.get(position + 1);
        double result = x * y;
        
        // System.out.println("Result: " + x + ", " + y + ", " + result);
        
        // clear the list of items.
        symbols.remove(position);
        
        list.remove(position);
        list.remove(position);
        
        list.add(position, result);
    }
    
    
    /**
     * The division arithmetic method.
     */
    private static void divide() {
        // position of the symbol.
        int position = symbols.indexOf(DIVIDE);
        
        // find the numbers and do the math.
        double x = list.get(position);
        double y = list.get(position + 1);
        double result = 0;
        // Error checking.
        if (y == 0) {
            notANumber = true;
            y = 1;
        } else {
            result = x / y;
        }
        
        // System.out.println("Result: " + x + ", " + y + ", " + result);
        
        // clear the list of items.
        symbols.remove(position);
        
        list.remove(position);
        list.remove(position);
        
        list.add(position, result);
    }
    
    /**
     * The addition arithmetic method.
     */
    private static void add() {
        // position of the symbol.
        int position = symbols.indexOf(PLUS);
        
        // find the numbers and do the math.
        double x = list.get(position);
        double y = list.get(position + 1);
        double result = x + y;
        
        // System.out.println("Result: " + x + ", " + y + ", " + result);
        
        // clear the list of items.
        symbols.remove(position);
        
        list.remove(position);
        list.remove(position);
        
        list.add(position, result);
    }
    
    /**
     * The minus arithmetic method.
     */
    private static void minus() {
        // position of the symbol.
        int position = symbols.indexOf(MINUS);
        
        // find the numbers and do the math.
        double x = list.get(position);
        double y = list.get(position + 1);
        double result = x - y;
        
        // System.out.println("Result: " + x + ", " + y + ", " + result);
        
        // clear the list of items.
        symbols.remove(position);
        
        list.remove(position);
        list.remove(position);
        
        list.add(position, result);
    }
    
    /**
     * Determines the order of operations and calls the arithmetic methods.
     */
    private static void ordersOfOperation() {
        int initialLength = symbols.size();
        for (int count = 0; count < initialLength; count++) {
            if (symbols.contains(MULTIPLY) || symbols.contains(DIVIDE)) {
                if (symbols.contains(MULTIPLY) && symbols.contains(DIVIDE)) {
                    if (symbols.indexOf(MULTIPLY) < symbols.indexOf(DIVIDE)) {
                        multiply();
                    } else {
                        divide();
                    }
                } else {
                    if (symbols.indexOf(MULTIPLY) >= 0) {
                        multiply();
                    } else {
                       divide();
                    }
                }  
            } else {
                if ((symbols.contains(PLUS) || symbols.contains(MINUS)) 
                        && !(symbols.contains(PLUS) 
                                && symbols.contains(MINUS))) {
                    if (symbols.indexOf(PLUS) >= 0) {
                        add();
                    } else {
                       minus();
                    } 
                }
                if (symbols.contains(PLUS) && symbols.contains(MINUS)) {
                    if (symbols.indexOf(PLUS) < symbols.indexOf(MINUS)) {
                        add();
                    } else {
                        minus();
                    }
                }
            }
        }
    }
    
    /**
     * Drives the program.
     *
     * @param args unused.
     */
    public static void main(String[] args) {
        notANumber = false;
        
        // Prompt for input.
        scan = new Scanner(System.in);
        System.out.println("Enter an arithmetic expression: ");
        String input = scan.nextLine();
        scan.close();
        
        // Parse the input.
        parseData(input);
        
        // Checkpoint 1 for debugging.
        System.out.println("\n~~~~Debugging~~~~\nNumbers: " + list.toString() 
            + "\nSymbols: " + symbols.toString());
        
        // Determine orders of operation and perform the math.
        ordersOfOperation();
        
        // Print out answer or and error.
        if (notANumber) {
            System.out.println("\nAnswer: MATH ERROR");
        } else {
            System.out.println("\nAnswer: " + list.get(0));
        }
    }
}
