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
	 * Empty tree with no children.
	 * @param parent
	 */
	public Tree(){
		this.parent = null;
		this.children = new ArrayList<Tree<E>>(defaultChildren);
		this.depth = 0;
	}
	
	/**
	 * Copy constructor
	 * @param copy Tree to copy from
	 */
	public Tree(Tree<E> copy) {
		this.value = copy.value;
		this.parent = null;
		this.depth = copy.depth;
		this.children = new ArrayList<Tree<E>>(copy.children.size());
		
		Stack<Tree<E>> s = new Stack<>();
		for (Tree<E> originalChild : copy.children) {
			s.push(originalChild);
			Tree<E> thisChild = new Tree<E>();
			thisChild.setParent(this);
			this.children.add(thisChild);
			s.push(thisChild);
		}
		
		while(!s.empty()) {
			Tree<E> thisOne = s.pop();
			Tree<E> original = s.pop();
			
			thisOne.value = original.value;
			thisOne.depth = original.depth;
			thisOne.children = new ArrayList<Tree<E>>(original.children.size());
			
			for (Tree<E> originalChild : original.children) {
				s.push(originalChild);
				Tree<E> thisOneChild = new Tree<E>();
				thisOneChild.setParent(thisOne);
				thisOne.children.add(thisOneChild);
				s.push(thisOneChild);
			}
		}
	}
	
	public E getValue(){return this.value;}
	public void setValue(E value){this.value = value;}
	
	public Tree<E> getParent(){return parent;}
	public void setParent(Tree<E> p){parent = p; if(p == null) depth = 0; else depth = parent.getDepth() + 1;}
	
	public Tree<E> getLeftChild(){return this.children.get(0);}
	public void setLeftChild(Tree<E> left){this.children.set(0, left);}
	
	public Tree<E> getRightChild(){return this.children.get(this.children.size() - 1);}
	public void setRightChild(Tree<E> right){this.children.set(this.children.size() - 1, right);}
	
	public void addChild(Tree<E> child){this.children.add(child);}
	
	public Tree<E> getNChild(int n){return this.children.get(n);}
	public void setNChild(int n, Tree<E> child){this.children.set(n, child);}
	
	public int getDepth(){return this.depth;}
	
	public int getNodes() {
		Stack<Tree<E>> s = new Stack<>();
		int nodes = 1;
		s.push(this);
		
		while(!s.isEmpty()) {
			Tree<E> a = s.pop();
			
			for (Tree<E> child : a.children) {
				if(child != null) {
					s.push(child);
					nodes++;
				}
			}
		}
		
		return nodes;
	}
	
	public String preOrder() {
		return this.preOrder("    ");
	}
	
	private  String preOrder(String tabs){
		
		String content = tabs + this.value.toString();
		if (this.children.size() == 0)
			return content;
		else {
			tabs += "        ";
			for (Tree<E> child : this.children) {
				if(child != null)
					content += System.lineSeparator() + child.preOrder(tabs);
			}
		}
		
		return content;
	}
	
	public String preOrderIterative() {
		String content = new String("");
		Stack<Object> s = new Stack<>();
		int indent = 8;
		s.push(this);
		s.push(indent);
		
		while(!s.isEmpty()) {
			int n = (int) s.pop();
			@SuppressWarnings("unchecked")
			Tree<E> a = (Tree<E>) s.pop();
			content += new String(new char[n]).replace('\0', ' ') + a.value + System.lineSeparator();
			
			for (Tree<E> child : a.children) {
				if(child != null) {
					s.push(child);
					s.push(n + indent);
				}
			}
		}
		
		return content;
	}
	
	public String horizontalPreOrder(){
		String val = "";
		if(this.value == null)
			return val;
		
		val = this.value.toString();
		
		if(this.isLeaf())
			return val;
		else {
			String nextChildren = "( " + val;
			for (Tree<E> child : children) {
				if(child == null)
					nextChildren += " null";
				else
					nextChildren += " " + child.horizontalPreOrder();
			}
			return nextChildren + " )";
		}
	}
	
	public String toString() {
		return this.horizontalPreOrder();
	}
	
	public Tree<E> getNode(int n) {
		Stack<Tree<E>> s = new Stack<>();
		Tree<E> node = null;
		int counter = 0;
		boolean finish = false;
		s.push(this);
		
		while(!finish && !s.isEmpty()) {
			Tree<E> a = s.pop();
			if(counter == n) {
				node = a;
				finish = true;
			}
			
			for (int i = 0; !finish && i < a.children.size(); i++) {
				Tree<E> child = a.children.get(i);
				if(child != null) {
					s.push(child);
					counter++;
					if(counter == n) {
						node = child;
						finish = true;
					}
				}
			}
		}
		
		return node;
	}
	
	/**
	 * Adopt a child but does not change its parent old reference.
	 * @param current
	 * @param adopted
	 */
	public void exchangeChild(Tree<E> current, Tree<E> adopted) {
		boolean finished = false;
		
		for (int i = 0; !finished && i < this.children.size(); i++) {
			Tree<E> a = this.children.get(i);
			if(a == current) {
				this.children.set(i, adopted);
				
				finished = true;
			}
		}
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
		boolean leaf = true;
		
		for (int i = 0; leaf && i < this.children.size(); i++) {
			leaf = leaf && (this.children.get(i) == null);
		}
		
		return leaf;
	}
	
	public Tree<E> clone() {
		return new Tree<E>(this);
	}
	
	/* recursive clone
	public Tree<E> clone() {
		ArrayList<Tree<E>> retChildren = new ArrayList<Tree<E>>(this.children.size());
		
		for (int i = 0; i < this.children.size(); i++) {
			if(this.children.get(i) != null)
				retChildren.add(this.children.get(i).clone());
		}
		Tree<E> ret = new Tree<E>(retChildren, this.value, this.parent);
		
		return ret;
	}
	*/
	
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
	
	/*public void cambiaNiveles(int nivel) {
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