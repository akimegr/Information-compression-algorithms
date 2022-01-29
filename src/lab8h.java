import java.util.ArrayList;

public class lab8h {
}

class Node {
    private int frequence;//частота
    private char letter;//буква
    private Node leftChild;//левый потомок
    private Node rightChild;//правый потомок



    public Node(char letter, int frequence) { //собственно, конструктор
        this.letter = letter;
        this.frequence = frequence;
    }

    public Node() {}//перегрузка конструтора для безымянных узлов(см. выше в разделе о построении дерева Хаффмана)
    public void addChild(Node newNode) {//добавить потомка
        if (leftChild == null)//если левый пустой=> правый тоже=> добавляем в левый
            leftChild = newNode;
        else {
            if (leftChild.getFrequence() <= newNode.getFrequence()) //в общем, левым потомком
                rightChild = newNode;//станет тот, у кого меньше частота
            else {
                rightChild = leftChild;
                leftChild = newNode;
            }
        }

        frequence += newNode.getFrequence();//итоговая частота
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public int getFrequence() {
        return frequence;
    }

    public char getLetter() {
        return letter;
    }

    public boolean isLeaf() {//проверка на лист
        return leftChild == null && rightChild == null;
    }
}
class BinaryTree {
    private Node root;

    public BinaryTree() {
        root = new Node();
    }

    public BinaryTree(Node root) {
        this.root = root;
    }

    public int getFrequence() {
        return root.getFrequence();
    }

    public Node getRoot() {
        return root;
    }
}

class PriorityQueue {
    private ArrayList<BinaryTree> data;//список очереди
    private int nElems;//кол-во элементов в очереди

    public PriorityQueue() {
        data = new ArrayList<BinaryTree>();
        nElems = 0;
    }


    public void insert(BinaryTree newTree) {//вставка
        if (nElems == 0)
            data.add(newTree);
        else {
            for (int i = 0; i < nElems; i++) {
                if (data.get(i).getFrequence() > newTree.getFrequence()) {//если частота вставляемого дерева меньше
                    data.add(i, newTree);//чем част. текущего, то cдвигаем все деревья на позициях справа на 1 ячейку
                    break;//затем ставим новое дерево на позицию текущего
                }
                if (i == nElems - 1)
                    data.add(newTree);
            }
        }
        nElems++;//увеличиваем кол-во элементов на 1
    }

    public BinaryTree remove() {//удаление из очереди
        BinaryTree tmp = data.get(0);//копируем удаляемый элемент
        data.remove(0);//собственно, удаляем
        nElems--;//уменьшаем кол-во элементов на 1
        return tmp;//возвращаем удаленный элемент(элемент с наименьшей частотой)
    }
}