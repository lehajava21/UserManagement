import java.io.IOException;
import java.util.Scanner;

public class UserCreator {

    public static void main(String[] args){

        User user = new User();
        Scanner scanner = new Scanner(System.in);
        String inp;
        do{
            System.out.println("Enter username:");
        }while ((inp = scanner.nextLine().trim()).isEmpty());
        user.setUsername(inp);
        System.out.println("Password must contain six or more");
        System.out.println("letters uppercase,letters lowercase,digits and symbols");
        System.out.println("Enter password:");
        while(!checkPassword(inp = scanner.nextLine().trim())) {
            System.out.println("Incorrect password!");
        }
        user.setPassword(inp);
        do{
            System.out.println("Enter first name:");
        }while ((inp = scanner.nextLine().trim()).isEmpty());
        user.setFirstname(inp);
        do{
            System.out.println("Enter last name:");
        }while ((inp = scanner.nextLine().trim()).isEmpty());
        user.setLastname(inp);
        System.out.println("Enter email:");
        while(!checkEmail(inp = scanner.nextLine().trim())) {
            System.out.println("Incorrect email!");
        }
        user.setEmail(inp);
        UserStorage userStorage = new UserStorage();
        try {
            if(!userStorage.addUser(user)){
                System.out.println("Can not add new user");
            }
        } catch (Exception e) {}
    }

    private static boolean checkPassword(String password) {
        int len = password.length();
        if(len < 6){
            return false;
        }
        boolean letterUc = false;
        boolean letterLc = false;
        boolean digit = false;
        boolean symbol = false;
        for( int i = 0; i < len; i++){
            char c = password.charAt(i);
            if(c >= 'A' && c <= 'Z'){
                letterUc = true;
            }else if(c >= 'a' && c <= 'z'){
                letterLc = true;
            }else if(c >= '0' && c <= '9'){
                digit = true;
            }else if(c >= '!' && c <= '/' || c >= ':' && c <= '@' ||
                    c >= '[' && c <= '_' || c >= '{' && c <= '~'){
                symbol = true;
            }
        }
        if(letterUc && letterLc && digit && symbol) {
            return true;
        }
        return false;
    }

    private static boolean checkEmail(String email) {
        int at = email.indexOf('@');
        if (at <= 0 || at != email.lastIndexOf('@')) {
            return false;
        }
        if (email.indexOf('.', at) == -1) {
            return false;
        }

        if (email.lastIndexOf('.') >= (email.length() - 2)) {
            return false;
        }
        if (!checkSymbols(email)) {
            return false;
        }
        return true;
    }

    private static boolean checkSymbols(String email) {
        int size = email.length();
        char c;
        for (int i = 0; i < size; i++) {
            c = email.charAt(i);
            if (!((c >= 'a' && c <= 'z') || (c >= '0' && c <= '9')
                    || c == '.' || c == '@' || c == '-' || c == '_')) {
                return false;
            }
        }
        return true;
    }
}
