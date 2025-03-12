package AB1;

import AB1.Interfaces.Encoder;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * The Application class serves as the entry point to the program.
 * <p>This class is used to test and demonstrate the functionality of printing text in Braille format
 * using the LinePrinter, configured with a BrailleFont and a BrailleEncoder.</p>
 * <p>Any implementation is not subject to examination and assessment by the EP2-Team, but serves as
 * free test hub for students.</p>
 */
public class Application {
    public static void main(String[] args) {
        // example from documentation
        LinePrinter lp=new  LinePrinter(
                                new BrailleFont(
                                        3,
                                        2,
                                        'o',
                                        '.',
                                        new BrailleEncoder()),
                                20,
                                4
                            );
        System.out.println("Hello World:");
        lp.printString("Hello World");
        lp.flush();
        System.out.println();

        System.out.println("Hello World:");
        lp.printString("Hello World");
        lp.flush();
        System.out.println();

        System.out.println("alphabet:");
        for (char start = 'a'; start <= 'z'; start += (char) ('j' - 'a' + 1)) {
            for (char letter = start; letter <= start + ('j' - 'a') && letter <= 'z'; letter++) {
                lp.printCharacter(letter);
            }
            lp.flush();
            System.out.println();
        }

        System.out.println("äöüß:");
        lp.printString("äöüß");
        lp.flush();
        System.out.println();

        System.out.println("()[]{}_-.:,:<>:");
        lp.printString("()[]{}_-.:,:<>");
        lp.flush();
        System.out.println();

        lp=new  LinePrinter(
                new BrailleFont(
                        2,
                        1,
                        'o',
                        '.',
                        new BrailleEncoder()),
                20,
                4
        );
        System.out.println("Hello World (smaller bitmaps):");
        lp.printString("Hello World");
        lp.flush();
        System.out.println();

        lp=new  LinePrinter(
                new BrailleFont(
                        4,
                        3,
                        'o',
                        '.',
                        new BrailleEncoder()),
                20,
                4
        );
        System.out.println("Hello World (bigger bitmaps):");
        lp.printString("Hello World");
        lp.flush();
        System.out.println();
    }
}
