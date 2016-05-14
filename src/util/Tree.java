package util;

import java.util.ArrayList;
import java.util.Stack;

public class Tree<E> implements Cloneable {
	private E valor;
	private Tree<E> hijoIzq;
	private Tree<E> hijoDer;
	private Tree<E> padre;
	
	public Tree(Tree<E> padre){
		this.padre = padre;
	}
	
	public Tree(Tree<E> hijoIzq, E valor, Tree<E> hijoDer,Tree<E> padre){
		this.hijoIzq = hijoIzq;
		this.valor=valor;
		this.hijoDer=hijoDer;
		this.padre = padre;
	}
	
	public E getValor(){return this.valor;}
	
	public Tree<E> getPadre() { return padre; }
	
	public void setPadre(Tree<E> p) {
		padre = p;
	}
	
	public void setValor(E valor){this.valor = valor;}
	
	public Tree<E> gethijoIzq(){return this.hijoIzq;}
	
	public Tree<E> gethijoDer(){return this.hijoDer;}
	
	public void sethijoIzq(Tree<E> hijoIzq){this.hijoIzq = hijoIzq;}
	
	public void sethijoDer(Tree<E> hijoDer){this.hijoDer = hijoDer;}
	
	public String muestraPreOrden(){
		if(valor instanceof TipoNodo){
			char c = ((TipoNodo)valor).obtener();
			if(valor instanceof Funcion){
				if(c == 's'){
					return "( "+c+" "+hijoIzq.muestraPreOrden()+" )";
				}else{
					return "( "+c+" "+hijoIzq.muestraPreOrden()+" "+hijoDer.muestraPreOrden()+" )";
				}
			}else{
				return String.valueOf(c);
			}
		}
		return "";
	}
	
	public boolean esHijoDer(Tree<TipoNodo> a) {
		Stack<Tree<TipoNodo>> pilaTreees;
		pilaTreees = new Stack<Tree<TipoNodo>>();
		Tree<TipoNodo> actual = (Tree<TipoNodo>) this;
		pilaTreees.push(actual);
		while (!pilaTreees.isEmpty()) {
			actual = pilaTreees.pop();
			if (actual.gethijoDer() == a)
				return true;
			else if (actual.gethijoIzq() == a)
				return false;
			if (actual.gethijoDer()!= null) {
				pilaTreees.push(actual.gethijoDer());
			}
			if (actual.gethijoIzq()!= null) {
				pilaTreees.push(actual.gethijoIzq());
			}
		}
		return false;
	}

	public boolean esHijoIzq(Tree<TipoNodo> a) {
		Stack<Tree<TipoNodo>> pilaTreees;
		pilaTreees = new Stack<Tree<TipoNodo>>();
		Tree<TipoNodo> actual = (Tree<TipoNodo>) this;
		pilaTreees.push(actual);
		while (!pilaTreees.isEmpty()) {
			actual = pilaTreees.pop();
			if (actual.gethijoDer() == a)
				return false;
			else if (actual.gethijoIzq() == a)
				return true;
			if (actual.gethijoDer()!= null) {
				pilaTreees.push(actual.gethijoDer());
			}
			if (actual.gethijoIzq()!= null) {
				pilaTreees.push(actual.gethijoIzq());
			}
		}
		return false;
	}

	public boolean esHoja() {
		if (hijoDer == null && hijoIzq == null) 
			return true;
		return false;
	}
	
	public int maximoNivel(){
	    /*int iz=0;
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
		Stack<Tree<TipoNodo>> pilaTreees;
		pilaTreees = new Stack<Tree<TipoNodo>>();
		Tree<TipoNodo> actual = (Tree<TipoNodo>) this;
		pilaTreees.push(actual);
		int maxNivel = 0;
		while (!pilaTreees.isEmpty()) {
			actual = pilaTreees.pop();
			if (actual.getValor().getNivel() > maxNivel)
				maxNivel = actual.getValor().getNivel();
			if (actual.gethijoDer()!= null) {
				pilaTreees.push(actual.gethijoDer());
			}
			if (actual.gethijoIzq()!= null) {
				pilaTreees.push(actual.gethijoIzq());
			}
		}
		return maxNivel;          
	}
	
	public int minimoNivel() {  
		Tree<TipoNodo> actual = (Tree<TipoNodo>) this;
		int minNivel = actual.getValor().getNivel();
	    return minNivel;  
	}
	
	public Tree<TipoNodo> clone() {
		Tree<TipoNodo> retorno = new Tree<TipoNodo>(null);
		retorno.valor = ((TipoNodo)valor).clone();
		if(this.padre == null){
			retorno.padre = null;
		}
		retorno.hijoDer = null;
		retorno.hijoIzq = null;
		if (hijoDer != null){
			retorno.hijoDer = hijoDer.clone();
			retorno.hijoDer.padre = retorno;}
		if (hijoIzq != null){
			retorno.hijoIzq = hijoIzq.clone();
			retorno.hijoIzq.padre = retorno;}
		return retorno;
	}

	public void cambiaNiveles(int nivel) {
		((TipoNodo)valor).setNivel(nivel);
		if (hijoIzq != null)
			hijoIzq.cambiaNiveles(nivel+1);
		if (hijoDer != null)
			hijoDer.cambiaNiveles(nivel+1);
	}

	public ArrayList<Integer> dameNumTipoNodo() {
		Stack<Tree<TipoNodo>> pilaTreees;
		pilaTreees = new Stack<Tree<TipoNodo>>();
		Tree<TipoNodo> actual = (Tree<TipoNodo>) this;
		pilaTreees.push(actual);
		ArrayList<Integer> retorno;
		int numFunciones = 0;
		int numFuncionesBinarias = 0;
		int numTerminales = 0;
		while (!pilaTreees.isEmpty()) {
			actual = pilaTreees.pop();
			if (actual.valor instanceof Funcion) {
				if (actual.valor.obtener() != 's') {
					numFuncionesBinarias++;
				}
				numFunciones++;
			}
			else numTerminales++;
			if (actual.gethijoDer()!= null) {
				pilaTreees.push(actual.gethijoDer());
			}
			if (actual.gethijoIzq()!= null) {
				pilaTreees.push(actual.gethijoIzq());
			}
		}
		retorno = new ArrayList<Integer>();
		retorno.add(numFunciones);
		retorno.add(numFuncionesBinarias);
		retorno.add(numTerminales);
		return retorno;
	}
	
	public void recalculaNiveles(int nivel){
		if(valor instanceof TipoNodo){
			((TipoNodo)this.valor).setNivel(nivel);
			if(this.hijoIzq != null){
				this.hijoIzq.recalculaNiveles(nivel+1);
			}
			if(this.hijoDer != null){
				this.hijoDer.recalculaNiveles(nivel+1);
			}
		}		
	}
	
	public static Tree<TipoNodo> buscarPtoCruce(int puntoCruce,Tree<TipoNodo> origen){
		Stack<Tree<TipoNodo>> pilaTreees;
		pilaTreees = new Stack<Tree<TipoNodo>>();
		Tree<TipoNodo> actual = origen;
		pilaTreees.push(actual);
		int indice = 0;
		while (!pilaTreees.isEmpty()) {
			actual = pilaTreees.pop();
			indice++;
			if (indice == puntoCruce) {
				return actual;
			}
			if (actual.gethijoDer()!= null) {
				pilaTreees.push(actual.gethijoDer());
			}
			if (actual.gethijoIzq()!= null) {
				pilaTreees.push(actual.gethijoIzq());
			}
		}
		return null;
	}
}