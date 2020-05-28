import com.sun.istack.internal.NotNull;

public class Stack {
    private Node top;
    private int size;

    Stack(){
        top = null;
        size = 0;
    }

    public Node push(Node item){
        Node second = top;
        top = item;
        top.setNextNode(second);
        size++;
        return item;
    }

    public Node push(int value){
        return push(new Node(value));
    }

    public Node pop(){
        if(!isEmpty()) {
            Node popped = top;
            top = top.getNextNode();
            size--;
            return popped;
        }
        System.out.println("Stack is empty, pop operation failed");
        return null;
    }

    public Node peek(){
        return top;
    }

    public boolean isEmpty(){
        return top == null;
    }


    public void initialize(int[] fileArray){
        if(fileArray == null){
            System.out.println("Stack File Array Does Not Exist");
            return;
        }
        if(fileArray.length ==  0){
            System.out.println(" Warning: 'stack.txt' is empty. Stack cannot be initialized");
            return;
        }

        top = new Node(fileArray[0]);
        size++;

        Node index = top;
        for (int i = 1; i< fileArray.length ; i++) {
            index.setNextNode(new Node(fileArray[i]));
            index = index.getNextNode();
            size++;
        }
    }

    public Node getTop() {
        return top;
    }

    public void setTop(Node top) {
        this.top = top;
    }

    public int getSize() {
        return size;
    }

    public String toString(){
        StringBuilder stack = new StringBuilder();
        Node index = top;
        if(top != null){
            for (int i = 0; i < size; i++) {
                stack.append(index.getValue()).append(" ");
                index = index.getNextNode();
            }
            return stack.toString().trim(); //trim to get rid of the last empty space
        }
        return "";
    }

    /**
     * this method uses bubble sort technique
     * @return the head of the sorted queue
     */
    public Node sort(){
        //tale will be updated at the end of the sorting process
        Node beforeNode;
        Node biggest;
        Node restOfTheList;

        if(top == null){
            System.out.println("The queue is empty, sort process cannot be applied");
        }

        for(int i = 0; i < size -1; i++){
            biggest = beforeNode = top;
            for (int limit = 0; limit < size - i -1 ; limit++) {
                //has to run size-1 times because it compares two items at a time
                // -think the process as you are on a rope between the nodes-
                if (biggest.getNextNode().getValue() >= biggest.getValue()) {
                    beforeNode = biggest;
                    biggest = biggest.getNextNode();
                } else {
                    restOfTheList = biggest.getNextNode().getNextNode(); //holding the rest of the list safe
                    if(beforeNode != biggest){
                        beforeNode.setNextNode(biggest.getNextNode());
                        beforeNode.getNextNode().setNextNode(biggest);
                        biggest.setNextNode(restOfTheList);
                        beforeNode = beforeNode.getNextNode();
                    }else {
                        //means before is equal to the biggest and it is the beginning -the head- of the queue
                        beforeNode = biggest.getNextNode();
                        biggest.setNextNode(restOfTheList);
                        beforeNode.setNextNode(biggest);
                        top = beforeNode; //update the head of the list
                    }

                    //switched the place of the smaller value and the biggest node so far.
                }
            }
        }
        return top;
    }

    public void removeBiggerThan(int number){
        Node index = top;
        int fixedSize = size;

        for(int i = 0; i< fixedSize-1 ; i++){
            if(index.getNextNode() != null) {
                if (index.getNextNode().getValue() > number) {
                    //next node now assigned to the node after and the actual next node lost it's reference,
                    // will be collected by garbage collector
                    //remember edge case: if there are 2 elements in the list, then the node after will be null and the code will not break
                    index.setNextNode(index.getNextNode().getNextNode());
                    size--; //size update
                }else {
                    index = index.getNextNode();
                }
            }
        }
        //I check the head last (after getting rid of every other match case in list),
        // to avoid the second element match case which will force us to remove the head element again.
        if(top.getValue() > number){
            top = top.getNextNode();
            size--;
        }
    }

}