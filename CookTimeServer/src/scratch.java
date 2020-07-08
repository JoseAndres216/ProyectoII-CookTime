import Logic.DataStructures.SimpleList.SimpleList;

import java.util.Stack;

class Scratch {
    private Stack<Character> stack;
    private SimpleList<Character> cola;

    public static void main(String[] args) {
        Scratch queueGenerate = new Scratch();
        System.out.println(queueGenerate.generateStack("(a*(b*c))"));
    }

    private SimpleList<Character> generateStack(String entry) {
        int len = entry.length();
        this.cola = new SimpleList<>();
        this.stack = new Stack<>();
        for (int i = 0; i < len; i++) {

            char element = entry.charAt(i);
            System.out.println(element);
            switch (element) {
                case '(':
                    this.stack.push(element);
                    break;

                case ')':
                    while (stack.peek() != '(') {
                        this.cola.append(stack.peek());
                        this.stack.pop();
                    }
                    stack.pop();
                    break;

                case '*':
                case '/':
                case '-':
                case '+':
                    if (this.stack.peek() != '*' && this.stack.peek() != '/' && this.stack.peek() != '-' && this.stack.peek() != '+') {
                        this.stack.push(element);
                    } else {
                        this.cola.append(stack.peek());
                        this.stack.pop();
                        this.stack.push(element);
                    }
                    break;
                default:
                    System.out.println(element);
                    this.cola.append(element);
                    break;
            }
        }
        return this.cola;
    }
}