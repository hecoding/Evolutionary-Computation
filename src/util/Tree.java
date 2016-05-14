package util;

import java.util.ArrayList;
import java.util.Stack;

public class Tree<E> implements Cloneable {
	private E value;
	private Tree<E> parent;
	private ArrayList<Tree<E>> children;
	private int depth;
	private static final int defaultChildren = 2;
	
	/**
	 * Tree with no childs.
	 * @param parent
	 */
	public Tree(Tree<E> parent){
		this.parent = parent;
		this.children = new ArrayList<Tree<E>>(defaultChildren);
		this.depth = 1;
	}
	
	/**
	 * Binary tree.
	 * @param leftChild
	 * @param value
	 * @param rightChild
	 * @param parent
	 */
	public Tree(Tree<E> leftChild, E value, Tree<E> rightChild,Tree<E> parent){
		this.parent = parent;
		this.children = new ArrayList<Tree<E>>(defaultChildren);
		
		this.children.add(leftChild);
		this.value=value;
		this.children.add(rightChild);
		
		if(leftChild == null && rightChild == null)
			this.depth = 1;
		else if(leftChild == null && rightChild != null)
			this.depth = rightChild.getDepth() + 1;
		else if(rightChild == null && leftChild != null)
			this.depth = leftChild.getDepth() + 1;
		else { // none null
			if(leftChild.getDepth() > rightChild.getDepth())
				this.depth = leftChild.getDepth() + 1;
			else
				this.depth = rightChild.getDepth() + 1;
		}
	}
	
	/**
	 * Ternary tree.
	 * @param leftChild
	 * @param middleChild
	 * @param value
	 * @param rightChild
	 * @param parent
	 */
	public Tree(Tree<E> leftChild, Tree<E> middleChild, E value, Tree<E> rightChild,Tree<E> parent){
		this.parent = parent;
		this.children = new ArrayList<Tree<E>>(3);
		
		this.children.add(leftChild);
		this.children.add(middleChild);
		this.value=value;
		this.children.add(rightChild);
		
		// no null checking done here
		if(leftChild.getDepth()>=middleChild.getDepth() && leftChild.getDepth()>=rightChild.getDepth())
			this.depth = leftChild.getDepth() + 1;
		else if(middleChild.getDepth()>=leftChild.getDepth() && middleChild.getDepth()>=rightChild.getDepth())
			this.depth = middleChild.getDepth() + 1;
		else if(rightChild.getDepth()>=leftChild.getDepth() && rightChild.getDepth()>=middleChild.getDepth())
			this.depth = rightChild.getDepth() + 1;
	}
	
	/**
	 * N-ary tree.
	 * @param children
	 * @param value
	 * @param parent
	 */
	public Tree(ArrayList<Tree<E>> children, E value, Tree<E> parent){
		this.children = new ArrayList<Tree<E>>(children.size());
		
		this.children = children;
		this.value = value;
		this.parent = parent;
		
		int currentDepth = 0;
		for (Tree<E> child : children) {
			if(child.getDepth() > currentDepth)
				currentDepth = child.getDepth();
		}
		this.depth = currentDepth + 1;
	}
	
	public E getValue(){return this.value;}
	public void setValue(E value){this.value = value;}
	
	public Tree<E> getParent(){return parent;}
	public void setParent(Tree<E> p){parent = p;}
	
	public Tree<E> getLeftChild(){return this.children.get(0);}
	public void setLeftChild(Tree<E> left){this.children.set(0, left);}
	
	public Tree<E> getRightChild(){return this.children.get(this.children.size() - 1);}
	public void setRightChild(Tree<E> right){this.children.set(this.children.size() - 1, right);}
	
	public Tree<E> getNChild(int n){return this.children.get(n);}
	public void setNChild(int n, Tree<E> child){this.children.set(n, child);}
	
	public int getDepth(){return this.depth;}
	
	/**
	 * Sets the next right-most child of the node.
	 * @param child The child to set to the parent.
	 */
	public void setNextChild(Tree<E> child){this.children.add(child);}
	
	public String preOrder() {
		return this.preOrder("");
	}
	
	private  String preOrder(String tabs){
		
		String content = tabs + "(" + this.value.toString();
		if (this.children.size() == 0)
			return content + ")";
		else {
			for (Tree<E> child : this.children) {
				if(child != null)
					content += System.lineSeparator() + child.preOrder("    ");
			}
		}
		
		return content + System.lineSeparator() + tabs + ")";
	}
	
	public String horizontalPreOrder(){
		
		String val = this.value.toString();
		
		if(this.isLeaf())
			return val;
		else {
			String nextChildren = "( " + val;
			for (Tree<E> child : children) {
				if(child != null)
					nextChildren += " " + child.horizontalPreOrder();
			}
			return nextChildren + " )";
		}
	}
	
	public String toString() {
		return this.horizontalPreOrder();
	}
	
	public boolean isRightChild(Tree<E> a) {
		Stack<Tree<E>> stack = new Stack<Tree<E>>();
		Tree<E> current = this;
		
		stack.push(current);
		while (!stack.isEmpty()) {
			current = stack.pop();
			if (current.getRightChild() == a)
				return true;
			else if (current.getLeftChild() == a)
				return false;
			if (current.getRightChild()!= null) {
				stack.push(current.getRightChild());
			}
			if (current.getLeftChild()!= null) {
				stack.push(current.getLeftChild());
			}
		}
		return false;
	}

	public boolean isLeftChild(Tree<E> a) {
		Stack<Tree<E>> stack = new Stack<Tree<E>>();
		Tree<E> current = this;
		
		stack.push(current);
		while (!stack.isEmpty()) {
			current = stack.pop();
			if (current.getRightChild() == a)
				return false;
			else if (current.getLeftChild() == a)
				return true;
			if (current.getRightChild()!= null) {
				stack.push(current.getRightChild());
			}
			if (current.getLeftChild()!= null) {
				stack.push(current.getLeftChild());
			}
		}
		return false;
	}

	public boolean isLeaf() {
		return this.depth == 1;
	}
	
	/*public int maximoNivel(){*/
	    /*int iz=0; iterativo
	    int de=0;
	    if(esHoja()){
	        return 1;
	    }else{
	        if (gethijoIzq()!=null) iz=gethijoIzq().altura();
	        if (gethijoDer()!=null) de=gethijoDer().altura();
	        if((iz>=de)&&(gethijoIzq()!=null)){
	            return (1+gethijoIzq().altura());
	        }else{
	            if((de>=iz)&&(gethijoDer()!=null)){
	                return (1+gethijoDer().altura());
	            }else{
	                return 0;
	        }
	    }*/
		/*Stack<Tree<E>> pilaArboles;
		pilaArboles = new Stack<Tree<E>>();
		Tree<E> actual = (Tree<E>) this;
		pilaArboles.push(actual);
		int maxNivel = 0;
		while (!pilaArboles.isEmpty()) {
			actual = pilaArboles.pop();
			if (actual.getDepth() > maxNivel)
				maxNivel = actual.getDepth();
			if (actual.getRightChild()!= null) {
				pilaArboles.push(actual.getRightChild());
			}
			if (actual.getLeftChild()!= null) {
				pilaArboles.push(actual.getLeftChild());
			}
		}
		return maxNivel;          
	}
	
	public int minimoNivel() {  
		Tree<E> actual = (Tree<E>) this;
		int minNivel = actual.getDepth();
	    return minNivel;  
	}*/
	
	/*public Tree<E> clone() {
		Tree<E> retorno = new Tree<E>(null);
		retorno.value = ((E)value).clone();
		if(this.parent == null){
			retorno.parent = null;
		}
		retorno.hijoDer = null;
		retorno.hijoIzq = null;
		if (hijoDer != null){
			retorno.hijoDer = hijoDer.clone();
			retorno.hijoDer.parent = retorno;}
		if (hijoIzq != null){
			retorno.hijoIzq = hijoIzq.clone();
			retorno.hijoIzq.parent = retorno;}
		return retorno;
	}

	public void cambiaNiveles(int nivel) {
		((E)value).setNivel(nivel);
		if (hijoIzq != null)
			hijoIzq.cambiaNiveles(nivel+1);
		if (hijoDer != null)
			hijoDer.cambiaNiveles(nivel+1);
	}

	public ArrayList<Integer> dameNumE() {
		Stack<Tree<E>> pilaArboles;
		pilaArboles = new Stack<Tree<E>>();
		Tree<E> actual = (Tree<E>) this;
		pilaArboles.push(actual);
		ArrayList<Integer> retorno;
		int numFunciones = 0;
		int numFuncionesBinarias = 0;
		int numTerminales = 0;
		while (!pilaArboles.isEmpty()) {
			actual = pilaArboles.pop();
			if (actual.value instanceof Funcion) {
				if (actual.value.obtener() != 's') {
					numFuncionesBinarias++;
				}
				numFunciones++;
			}
			else numTerminales++;
			if (actual.getRightChild()!= null) {
				pilaArboles.push(actual.getRightChild());
			}
			if (actual.getLeftChild()!= null) {
				pilaArboles.push(actual.getLeftChild());
			}
		}
		retorno = new ArrayList<Integer>();
		retorno.add(numFunciones);
		retorno.add(numFuncionesBinarias);
		retorno.add(numTerminales);
		return retorno;
	}
	
	public void recalculaNiveles(int nivel){
		if(value instanceof E){
			((E)this.value).setNivel(nivel);
			if(this.hijoIzq != null){
				this.hijoIzq.recalculaNiveles(nivel+1);
			}
			if(this.hijoDer != null){
				this.hijoDer.recalculaNiveles(nivel+1);
			}
		}		
	}
	
	public static Tree<E> buscarPtoCruce(int puntoCruce,Tree<E> origen){
		Stack<Tree<E>> pilaArboles;
		pilaArboles = new Stack<Tree<E>>();
		Tree<E> actual = origen;
		pilaArboles.push(actual);
		int indice = 0;
		while (!pilaArboles.isEmpty()) {
			actual = pilaArboles.pop();
			indice++;
			if (indice == puntoCruce) {
				return actual;
			}
			if (actual.getRightChild()!= null) {
				pilaArboles.push(actual.getRightChild());
			}
			if (actual.getLeftChild()!= null) {
				pilaArboles.push(actual.getLeftChild());
			}
		}
		return null;
	}*/
}