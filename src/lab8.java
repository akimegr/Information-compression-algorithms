import java.io.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

public class lab8 {
    public static void main(String[] args) throws FileNotFoundException {
        String name = reads();
        HashMap<String,String> dict = new HashMap<String,String>();
        name = name.toUpperCase(Locale.ROOT);
        d(name,dict);
        StringBuilder newStr = new StringBuilder("");
        for (int i = 0; i < name.length(); i++) {
            boolean check = true;
            int z = i;
            StringBuilder checkStr = new StringBuilder(Character.toString(name.charAt(z)));
            while(check ){
                if(z<name.length()-1 && dict.containsValue(checkStr.toString() + name.charAt(z+1))){
                    checkStr.append(name.charAt(z+1));
                    z++;
                }
                else{
                    check = false;
                    i = i +(z-i);
                    for (String key :
                            dict.keySet()) {
                        if(dict.get(key).equals(checkStr.toString()))
                            newStr.append(key);
                    }
                }
            }
        }
        System.out.println(newStr);

            File file = new File("z.txt");
            PrintWriter pw = new PrintWriter(file);
            pw.println(newStr.toString());
            pw.close();



    }

    public static void d(String name,HashMap<String,String> dict){
        int counter = 0;
        for (int i = 0; i < name.length(); i++) {
            boolean check = true;
            int z = i;
            StringBuilder subStr = new StringBuilder("");

            while(check){
                subStr.append(name.charAt(z));
                if (dict.containsValue(subStr.toString())){
                    z++;
                }
                else{
                    char f = (char)(counter+65);
                    dict.put(Integer.toString(counter)+f,subStr.toString());
                    check = false;
                    i = i + (z-i);
                }
            }
            counter++;
        }
    }

    public static String reads() throws FileNotFoundException{
        FileReader reader = new FileReader("check.txt");
        Scanner scan= new Scanner(reader);
        String name = scan.nextLine();
        System.out.println(name);
        return name;
    }
}
