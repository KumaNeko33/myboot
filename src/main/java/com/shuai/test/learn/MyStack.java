package com.shuai.test.learn;

import java.util.Stack;

/**
 * @Author: MiaoHongShuai
 * @Description:
 * @Date: Created on 2017/9/4
 * @Modified By:
 */
public class MyStack {
    private Stack<Integer> stackData;
    private Stack<Integer> stackMin;//可改成只存最小值对应stackData中的下标

    public void push(int num){
        if (stackMin == null) {
            stackMin.push(num);
        } else if (num < stackMin.peek()) {
            stackMin.push(num);
        }else {
            stackMin.push(stackMin.peek());
        }
        stackData.push(num);
    }

    public Integer pop() {
        if (stackData == null) {
            throw new IndexOutOfBoundsException("stack已经为空");
        }
        stackMin.pop();
        return stackData.pop();
    }

    //stackMin.get(index)是Stack继承的Vector中内部数组的方法
    public Integer getMin(){
        if (stackMin == null) {
            throw new IndexOutOfBoundsException("stack已经为空");
        }
        return stackMin.peek();
    }

    //方法二：使用链表结点技术
    public class Node{
        private Node next;
        private int value;
        private int min;
        public Node(int value, int min) {
            this.value = value;
            this.min = min;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }
    }
    private Stack<Node> nodeStack;
    public void push2(int num) {
        if (nodeStack == null) {
            Node node = new Node(num, num);
            nodeStack.push(node);
        }
    }
//    private Node<Integer> node;

}
