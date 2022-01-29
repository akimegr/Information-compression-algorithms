import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

public class lab8Havman {
    public static void main(String[] args) throws FileNotFoundException {
        FileReader r = new FileReader("check.txt");
        Scanner scan= new Scanner(r);
        String text = scan.nextLine();
        StringBuilder ss = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            ss.append(Integer.toBinaryString(text.charAt(i)));
        }
        System.out.println(ss);
        File file = new File("hafInp.txt");
        PrintWriter pw = new PrintWriter(file);
        pw.println(ss.toString());
        pw.close();
        System.out.println(text);
        TreeMap<Character,Integer> frequencies = countFrequency(text);

        ArrayList<CodeTreeNode> codeTreeNodes = new ArrayList<>();  //список листов
        for (Character c :
                frequencies.keySet()) {
            codeTreeNodes.add(new CodeTreeNode(c,frequencies.get(c)));
        }
        CodeTreeNode tree = huffman(codeTreeNodes); //заполняем деерево
        
        TreeMap<Character,String> codes = new TreeMap<>();
        for (Character c:frequencies.keySet()) {
            codes.put(c,tree.getCodeForCharacter(c,""));
        }
        System.out.println("таблица префиксных кодов: " + codes.toString());

        StringBuilder encod = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            encod.append(codes.get(text.charAt(i)));
        }
        System.out.println("размер исходной строки: " + text.getBytes().length*8 + "бит");
        System.out.println("размер сжатой строки " + encod.length() + " бит");
        System.out.println("Биты сжатой строки: " + encod);
        File file2 = new File("hafOut.txt");
        PrintWriter pw2 = new PrintWriter(file2);
        pw2.println(encod.toString());
        pw2.close();

    }

    private static CodeTreeNode huffman(ArrayList<CodeTreeNode> codeTreeNodes){     //записываем дерево
        while (codeTreeNodes.size()>1){
            Collections.sort(codeTreeNodes);
            CodeTreeNode left = codeTreeNodes.remove(codeTreeNodes.size()-1);
            CodeTreeNode right = codeTreeNodes.remove(codeTreeNodes.size()-1);

            CodeTreeNode parent = new CodeTreeNode(null,right.weight+left.weight,left,right);   //создаем родителя из сумм и новыми корням
            codeTreeNodes.add(parent);  //заносим в дерево
        }
        return codeTreeNodes.get(0);
    }

    private static TreeMap<Character,Integer> countFrequency(String text){//считаем количество встречаемости сивола
        TreeMap<Character,Integer> freqMap= new TreeMap<>();
        for (int i = 0; i < text.length(); i++) {
            Character c = text.charAt(i);
            Integer count = freqMap.get(c);
            freqMap.put(c, count!=null ? count+1:1);
        }
        return freqMap;
    }
    private static class CodeTreeNode implements Comparable<CodeTreeNode>{  //для представления кодового дерева
        Character content;
        int weight;
        CodeTreeNode left;
        CodeTreeNode right;

        public CodeTreeNode(Character content, int weight) {
            this.content = content;
            this.weight = weight;
        }

        public CodeTreeNode(Character content, int weight, CodeTreeNode left, CodeTreeNode right) {
            this.content = content;
            this.weight = weight;
            this.left = left;
            this.right = right;
        }

        @Override
        public int compareTo(CodeTreeNode o){
            return  o.weight - weight;
        }

        public String getCodeForCharacter(Character ch, String parentPath){ //обход дерева в глубин
            if(content==ch) //дошли до нкжного листа
            {
                return  parentPath;
            }
            else{
                if(left!=null){
                    String path = left.getCodeForCharacter(ch, parentPath+0);

                    if(path!=null){ //в левом поддереве нашёл нужный нам код то возвр путь
                        return path;
                    }
                }
                if(right!=null){    //тк право то +1
                    String path = right.getCodeForCharacter(ch, parentPath+1);
                    if(path!=null){
                        return  path;
                    }
                }

            }
            return null;    //не удача поиска
        }

    }
}
