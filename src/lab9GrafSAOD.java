import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class lab9GrafSAOD {
    public static void main(String[] args) throws Exception {
        Graf gr = new Graf();

        FileReader reader = new FileReader("que.txt");
        Scanner scan= new Scanner(reader);
        String s = scan.nextLine();
        String [] a = s.split(" ");


        for (int i = 0; i < a.length; i++) {
            gr.addVertex(a[i]);
        }

        gr.addEdge(0,1,1);
        gr.addEdge(1,2,1);
        gr.addEdge(2,3,1);
        gr.addEdge(0,4,1);
        gr.addEdge(4,5,1);

        gr.passInWidth(0);


    }
}

class Queue{        //обход
    private int[] array;
    private int size = 10;
    private int head;
    private int tail;
    private int count;

    public Queue(){
        array = new int [size];
        head = 0;
        tail = -1;
        count = 0;
    }

    public void insert(int v){
        if(tail==size-1){//123 доб 4 потом 234
            tail = -1;
        }

        array[++tail] = v;
        count++;
    }

    public int remove(){
        if(head==size){
            head = 0;
        }
        count--;
        return array[head++];
    }

    public boolean isEmpty(){
        return count == 0;
    }

}

class Vertex{//вершина
    public  String name;
    public boolean isVisited;
    public Vertex(String name){
        this.name = name;
        isVisited = false;
    }
}

class Graf{
    private int maxN = 10;//    вершин
    private  int[][] mas;    //есть переход между графами 1 - есть
    Vertex[] vertexList;
    private  int curN;
    Queue queue = new Queue();
    public Graf(){
        vertexList = new Vertex[maxN];
        mas = new int [maxN][maxN];
        curN = 0;
    }
    public void addVertex(String name){//созд вершины
        vertexList[curN++] = new Vertex(name);
    }

    public void addEdge(int start, int end, int val){//ребра. val для смежности
        mas[start][end] = val;
        mas[end][start] = val; //чтобы был путь
    }
    public int check (int v ){      //проверка вершины
        for (int i = 0; i < curN; i++) {
            if(mas[v][i]==1 && vertexList[i].isVisited==false){
                return i;
            }
        }
        return -1;
    }

    public void passInWidth(int index){
        System.out.println(vertexList[index].name);
        vertexList[index].isVisited = true;
        queue.insert(index);

        int vertex;
        while(!queue.isEmpty()){
            int temp = queue.remove();
            while ((vertex = check(temp))!=-1){
                System.out.println(vertexList[vertex].name);
                vertexList[vertex].isVisited = true;
                queue.insert(vertex);
            }
        }
        for (int i = 0; i < curN; i++) {
            vertexList[i].isVisited = false;
        }
    }



//            2   3   4
//        1
//            5   6


}