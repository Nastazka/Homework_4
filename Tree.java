//ДОМАШНЕЕ ЗАДАНИЕ
// 1. Необходимо превратить собранное на семинаре дерево поиска в
// полноценное левостороннее красно-черное дерево. И реализовать
// в нем метод добавления новых элементов с балансировкой.
// 2. Красно-черное дерево имеет следующие критерии:
//      ● Каждая нода имеет цвет (красный или черный)
//      ● Корень дерева всегда черный
//      ● Новая нода всегда красная
//      ● Красные ноды могут быть только левым ребенком
//      ● У красной ноды все дети черного цвета
// 3. Соответственно, чтобы данные условия выполнялись, после
// добавления элемента в дерево необходимо произвести
// балансировку, благодаря которой все критерии выше станут
// валидными.
// 4. Для балансировки существует 3 операции – левый малый поворот,
// правый малый поворот и смена цвета.

// Критерии применения этих операций следующие:
//      ● Если правый ребенок – красный, а левый - черный, то применяем малый правый поворот
//      ● Если левый ребенок красный и его левый ребенок тоже красный – применяем малый левый поворот
//      ● Если оба ребенка красные – делаем смену цвета
//      ● Если корень стал красным – просто перекрашиваем его в черный


public class Tree <V extends Comparable<V>> {

    private Node root;

    public class Node{
        private V value;
        private Node left;
        private Node right;
        private boolean color; //Цвет узла: красный или черный.

        public Node(V value) {
            this.value = value;
            this.left = null;
            this.right = null;
            this.color = true; // Новая нода всегда красная
        }
    }

    // Метод добавления элемента в дерево с балансировкой
    public void add(V value) {
        // Если дерево пустое, то создаем новый корень
        if (root == null) {
            root = new Node(value);
            return;
        }

        // Ищем место для нового элемента
        Node node = root;
        while (node != null) {
            if (node.value.compareTo(value) > 0) {
                if (node.left == null) {
                    node.left = new Node(value);
                    break;
                } else {
                    node = node.left;
                }
            } else {
                if (node.right == null) {
                    node.right = new Node(value);
                    break;
                } else {
                    node = node.right;
                }
            }
        }

        // Балансируем дерево
        balance(node);
    }

    // Метод балансировки дерева
    private void balance(Node node) {
        // Если корень красный, то перекрашиваем его в черный
        if (node.color) {
            node.color = false;
            return;
        }

        // Если правый ребенок красного узла черный, а левый - нет, то делаем малый правый поворот
        if (node.color && node.right != null && !node.right.color) {
            rotateLeft(node);
            return;
        }

        // Если левый ребенок красного узла красный и его левый ребенок тоже красный, то делаем малый левый поворот
        if (node.color && node.left != null && node.left.color && node.left.left.color) {
            rotateRight(node);
            return;
        }

        // Если оба ребенка красного узла красные, то делаем смену цвета
        if (node.color && node.left != null && node.right != null && node.left.color && node.right.color) {
            node.color = false;
            node.left.color = false;
            node.right.color = false;
            return;
        }
    }

    // Метод малого левого поворота
    private void rotateLeft(Node node) {
        // Получаем правого ребенка узла
        Node right = node.right;

        // Перемещаем правого ребенка на место узла
        node.right = right.left;
        right.left = node;

        // Меняем цвета узлов
        node.color = right.color;
        right.color = true;
    }

    // Метод малого правого поворота
    private void rotateRight(Node node) {
        // Получаем левого ребенка узла
        Node left = node.left;

        // Перемещаем левого ребенка на место узла
        node.left = left.right;
        left.right = node;

        // Меняем цвета узлов
        node.color = left.color;
        left.color = true;
    }

    // Метод проверки, содержится ли элемент в дереве
    public boolean contains(V value){
        Node node = root;
        while (node != null) {
            if (node.value.equals((value)))
                return true;
            if (node.value.compareTo(value) > 0)
                node = node.left;
            else
                node = node.right;
        }
        return false;
    }    
}