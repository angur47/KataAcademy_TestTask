import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter a task: ");
        String solution = calc(input.nextLine());
        if (!solution.equals("ERROR")) {
            System.out.println(solution);
        }
    }
    public static String calc(String input) {

        int solution = 0;
        int a = 0;
        int b = 0;

        boolean isRoman = false;
        String errorResponse = "ERROR";

        ArrayList arabicNumbers = new ArrayList<>(11);
        for(int i=0; i<11; i++) {
            arabicNumbers.add(String.valueOf(i));
        }

        ArrayList romanNumbers = new ArrayList<>(10);
        for(RomanNumber number: RomanNumber.values()) {
            romanNumbers.add(number.toString());
        }

        ArrayList operators = new ArrayList<>(4);
        operators.add("+");
        operators.add("-");
        operators.add("*");
        operators.add("/");

        boolean operatorExist = false;
        for (Object o : operators) {
            if (input.contains(o.toString())) {
                operatorExist = true;
            }
        }
        if (!operatorExist) {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println("Cтрока не является математической операцией.");
                return errorResponse;
            }
        }

        String [] strings = input.split(" ");

        if (strings.length != 3) {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println("Формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *).");
                return errorResponse;
            }
        }

        String operator = strings[1];
        String firstOperand = strings[0];
        String secondOperand = strings[2];

        if (!operators.contains(operator)) {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println("Cтрока не является математической операцией.");
                return errorResponse;
            }
        }

        if (arabicNumbers.contains(firstOperand) && arabicNumbers.contains(secondOperand)) {
            a = Integer.parseInt(firstOperand);
            b = Integer.parseInt(secondOperand);
        } else if (romanNumbers.contains(firstOperand) && romanNumbers.contains(secondOperand)) {
            isRoman = true;
            RomanNumber currentEnum;
            currentEnum = RomanNumber.valueOf(firstOperand);
            a = currentEnum.getNumber();
            currentEnum = RomanNumber.valueOf(secondOperand);
            b = currentEnum.getNumber();
        } else if ((arabicNumbers.contains(firstOperand) && romanNumbers.contains(secondOperand)) ||
                (romanNumbers.contains(firstOperand) && arabicNumbers.contains(secondOperand))) {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println("Используются одновременно разные системы счисления.");
                return errorResponse;
            }
        } else if (!arabicNumbers.contains(firstOperand)
                || !arabicNumbers.contains(secondOperand)
                || !romanNumbers.contains(firstOperand)
                || !romanNumbers.contains(secondOperand)) {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println("Введены неподходящие числа.");
                return errorResponse;
            }
        }

        switch(operator){
            case "+":
                solution = a + b;
                break;
            case "-":
                solution = a - b;
                break;
            case "*":
                solution = a * b;
                break;
            case "/":
                if (b==0) {
                    try {
                        throw new Exception();
                    } catch (Exception e) {
                        System.out.println("На 0 делить нельзя.");
                        return errorResponse;
                    }
                }
                solution = a / b;
                break;
        }

        if (isRoman) {
            if (solution<1) {
                try {
                    throw new Exception();
                } catch (Exception e) {
                    System.out.println("В римской системе нет нуля и отрицательных чисел.");
                    return errorResponse;
                }
            } else {
                return returnRomanNumber(solution, romanNumbers);
            }
        }

        return Integer.toString(solution);
    }

    private static String returnRomanNumber(int arabicNumber, ArrayList romanUnits) {
        String romanNumber;

        int decimal = arabicNumber / 10;
        int units = arabicNumber % 10;

        String[] romanDecimals = new String[11];
        romanDecimals[0] = "";
        romanDecimals[1] = "X";
        romanDecimals[2] = "XX";
        romanDecimals[3] = "XXX";
        romanDecimals[4] = "XL";
        romanDecimals[5] = "L";
        romanDecimals[6] = "LX";
        romanDecimals[7] = "LXX";
        romanDecimals[8] = "LXXX";
        romanDecimals[9] = "XC";
        romanDecimals[10] = "C";

        romanUnits.add(0, "");

        romanNumber = romanDecimals[decimal] + romanUnits.get(units);

        return romanNumber;
    }
}