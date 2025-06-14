import java.awt.*;
import java.awt.event.*;
import java.lang.Math;

class Calculator extends WindowAdapter implements ActionListener, KeyListener {
    Frame f;
    Label l1;
    double num1 = 0, num2 = 0, result = 0;
    int operator = 0;

    Calculator() {
        f = new Frame("Scientific Calculator (Keyboard Enabled)");
        l1 = new Label();
        l1.setBounds(30, 50, 300, 40);
        l1.setBackground(Color.LIGHT_GRAY);
        f.add(l1);

        String[] buttons = {
            "7", "8", "9", "+", "sin",
            "4", "5", "6", "-", "cos",
            "1", "2", "3", "*", "tan",
            "0", ".", "=", "/", "log",
            "C", "√", "x^y", "e^x", "π"
        };

        int x = 30, y = 100;
        for (int i = 0; i < buttons.length; i++) {
            Button b = new Button(buttons[i]);
            b.setBounds(x, y, 50, 40);
            b.addActionListener(this);
            f.add(b);
            x += 60;
            if ((i + 1) % 5 == 0) {
                x = 30;
                y += 50;
            }
        }

        f.setSize(360, 550);
        f.setLayout(null);
        f.setVisible(true);
        f.addWindowListener(this);
        f.addKeyListener(this);       // Enable keyboard input
        f.setFocusable(true);         // Important to get keyboard focus
        f.requestFocus();             // Request focus
    }

    public void windowClosing(WindowEvent e) {
        f.dispose();
    }

    // ACTION HANDLING (Button Clicks)
    public void actionPerformed(ActionEvent e) {
        processCommand(e.getActionCommand());
    }

    // KEY HANDLING
    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        char ch = e.getKeyChar();
        int code = e.getKeyCode();

        if (Character.isDigit(ch) || ch == '.') {
            l1.setText(l1.getText() + ch);
        } else if (ch == '+') {
            saveNum(1);
        } else if (ch == '-') {
            saveNum(2);
        } else if (ch == '*') {
            saveNum(3);
        } else if (ch == '/') {
            saveNum(4);
        } else if (code == KeyEvent.VK_ENTER || ch == '=') {
            compute();
        } else if (code == KeyEvent.VK_BACK_SPACE) {
            String text = l1.getText();
            if (text.length() > 0)
                l1.setText(text.substring(0, text.length() - 1));
        } else if (ch == 'c') {
            l1.setText("");
        } else if (ch == 's') {
            computeUnary("sin");
        } else if (ch == 't') {
            computeUnary("tan");
        } else if (ch == 'l') {
            computeUnary("log");
        } else if (ch == 'e') {
            computeUnary("e^x");
        } else if (ch == 'p') {
            l1.setText("" + Math.PI);
        }
    }

    // Core logic
    void saveNum(int op) {
        num1 = Double.parseDouble(l1.getText());
        l1.setText("");
        operator = op;
    }

    void compute() {
        num2 = Double.parseDouble(l1.getText());
        switch (operator) {
            case 1: result = num1 + num2; break;
            case 2: result = num1 - num2; break;
            case 3: result = num1 * num2; break;
            case 4: result = num1 / num2; break;
            case 5: result = Math.pow(num1, num2); break;
        }
        l1.setText("" + result);
    }

    void computeUnary(String func) {
        double val = Double.parseDouble(l1.getText());
        switch (func) {
            case "sin": result = Math.sin(Math.toRadians(val)); break;
            case "cos": result = Math.cos(Math.toRadians(val)); break;
            case "tan": result = Math.tan(Math.toRadians(val)); break;
            case "log": result = Math.log10(val); break;
            case "e^x": result = Math.exp(val); break;
        }
        l1.setText("" + result);
    }

    void processCommand(String cmd) {
        try {
            if (cmd.matches("\\d") || cmd.equals(".")) {
                l1.setText(l1.getText() + cmd);
            } else if (cmd.equals("+")) saveNum(1);
            else if (cmd.equals("-")) saveNum(2);
            else if (cmd.equals("*")) saveNum(3);
            else if (cmd.equals("/")) saveNum(4);
            else if (cmd.equals("=")) compute();
            else if (cmd.equals("C")) l1.setText("");
            else if (cmd.equals("√")) {
                result = Math.sqrt(Double.parseDouble(l1.getText()));
                l1.setText("" + result);
            } else if (cmd.equals("sin") || cmd.equals("cos") || cmd.equals("tan") ||
                       cmd.equals("log") || cmd.equals("e^x")) {
                computeUnary(cmd);
            } else if (cmd.equals("x^y")) {
                saveNum(5);
            } else if (cmd.equals("π")) {
                l1.setText("" + Math.PI);
            }
        } catch (Exception ex) {
            l1.setText("Error");
        }
    }

    public static void main(String[] args) {
        new Calculator();
    }
}

