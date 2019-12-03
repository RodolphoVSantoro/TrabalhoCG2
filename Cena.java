import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Graphics2D;

public class Cena{
	private ArrayList<Objeto> objetos;
	private Matrix escalaTela;
	private Matrix perspectiva;
	private Matrix normaliza;
	private int larguraTela, alturaTela;
	private double observador[] = {0,0,100};
	public Cena(int larguraTela, int alturaTela){
		this.objetos = new ArrayList<Objeto>();
		this.larguraTela = larguraTela;
		this.alturaTela = alturaTela;
		this.escalaTela = CriaMatriz.Tela(larguraTela, alturaTela);
		this.perspectiva = CriaMatriz.Perspectiva(60, 80, 50);
		this.normaliza = CriaMatriz.Normaliza(40.0,40.0);
	}
	//retorna id do objeto
	public int adicionaObjeto(String fname, Cor corBase){
		Objeto o = new Objeto(fname, corBase);
		this.objetos.add(o);
		return objetos.size()-1;
	}

	public int adicionaCurva(String fname){
		Objeto o = new Objeto(fname);
		this.objetos.add(o);
		return objetos.size()-1;
	}

	public void mudaAnimacao(int idObjeto, double duracao, Matrix transformacao){
		Objeto obj = this.objetos.get(idObjeto);
		obj.mudaAnimacao(duracao, transformacao);
	}

	public void anima(double t){
		Iterator<Objeto> objetoIterator = objetos.iterator();
		while(objetoIterator.hasNext()){
			Objeto objeto = objetoIterator.next();
			objeto.anima(t);
		}
	}

	public void anima(int idObjeto, double t){
		Objeto obj = objetos.get(idObjeto);
		obj.anima(t);
	}

	public boolean endedAnimacao(int idObjeto, double t){
		Objeto obj = this.objetos.get(idObjeto);
		return obj.endedAnimacao(t);
	}

	public void desenha(Graphics2D g2d){
		Iterator<Objeto> objetoIterator = objetos.iterator();
		while(objetoIterator.hasNext()){
			Objeto objeto = objetoIterator.next();
			//objeto.backFaceCulling();
			int ordem[] = objeto.painter(this.observador);
			objeto.ilumina(this.observador);
			objeto.transforma(perspectiva, Vertice.CoordinateSystem.CENA, Vertice.CoordinateSystem.NORMAL);
			objeto.transforma(normaliza, Vertice.CoordinateSystem.NORMAL, Vertice.CoordinateSystem.NORMAL);
			objeto.transforma(escalaTela, Vertice.CoordinateSystem.NORMAL, Vertice.CoordinateSystem.TELA);
			objeto.desenha(g2d, ordem);
		}
	}
}
