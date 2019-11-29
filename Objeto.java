import java.util.ArrayList;
import java.util.Iterator;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.IOException;
import java.awt.*;

public class Objeto{
	private ArrayList<Vertice> vertices;
	private ArrayList<Face> faces;
	public Objeto(){
		this.vertices = new ArrayList<Vertice>();
		this.faces = new ArrayList<Face>();
	}
	public Objeto(ArrayList<Vertice> v, ArrayList<Face> f){
		this.vertices = v;
		this.faces = f;
	}
	public Objeto(String fname){
		this.vertices = new ArrayList<Vertice>();
		this.faces = new ArrayList<Face>();
		Boolean vert=true;
		try{
			File arq = new File(fname);
			Scanner leitor = new Scanner(arq);
			while(leitor.hasNextLine()){
				String linha = leitor.nextLine();
				if(linha.equals(";"))
					vert=false;
				else{
					String tmp[] = linha.split(" ");
					if(vert){
						double num[] = new double[tmp.length];
						for(int i=0; i<tmp.length; i++)
							num[i] = Double.parseDouble(tmp[i]);
						Vertice v = new Vertice(num[0], num[1], num[2]);
						this.vertices.add(v);
					}
					else{
						int ind[] = new int[tmp.length];
						for(int i=0; i<tmp.length; i++)
							ind[i] = Integer.parseInt(tmp[i]);
						Face f = new Face(ind);
						this.faces.add(f);
					}
				}
			}
			leitor.close();
		}
		catch(Exception e){
			System.out.println("Erro ao criar objeto:");
			e.printStackTrace();
		}
	}
	public void transforma(Matrix m, Vertice.CoordinateSystem in, Vertice.CoordinateSystem out){
		double min[] = new double[3];
		Iterator<Vertice> iteratorVertice = this.vertices.iterator();
		if(iteratorVertice.hasNext()){
			Vertice vertice = iteratorVertice.next();
			double v[] = vertice.verticeToArray(in);
			for(int i=0;i<3;i++)min[i]=v[i];
			while(iteratorVertice.hasNext()){
				vertice = iteratorVertice.next();
				v = vertice.verticeToArray(in);
				//1==y do vertice
				if(v[1]<min[1]){
					for(int i=0;i<3;i++){
						min[i]=v[i];
					}
				}
			}
		}
		Matrix translada = CriaMatriz.Translada(-min[0], -min[1], -min[2]);
		Matrix desfazTranslacao = CriaMatriz.Translada(min[0], min[1], min[2]);
		iteratorVertice = this.vertices.iterator();
		while(iteratorVertice.hasNext()){
			Vertice vertice = iteratorVertice.next();
			vertice.transforma(m, in, out, Matrix.MultiplyOrder.POS, translada);
		}
	}
	public void desenha(Graphics g){
		Iterator<Face> iteratorFace = this.faces.iterator();
		while(iteratorFace.hasNext()){
			Face f = iteratorFace.next();
			f.desenha(g, this.vertices);
		}
	}
	public void mudaAnimacao(double duracao, Matrix transformacao){
		double min[] = new double[3];
		Iterator<Vertice> iteratorVertice = this.vertices.iterator();
		if(iteratorVertice.hasNext()){
			Vertice vertice = iteratorVertice.next();
			double v[] = vertice.verticeToArray(Vertice.CoordinateSystem.CENA);
			for(int i=0;i<3;i++)min[i]=v[i];
			while(iteratorVertice.hasNext()){
				vertice = iteratorVertice.next();
				v = vertice.verticeToArray(Vertice.CoordinateSystem.CENA);
				//1==y do vertice
				if(v[1]<min[1]){
					for(int i=0;i<3;i++){
						min[i]=v[i];
					}
				}
			}
		}
		Matrix translada = CriaMatriz.Translada(-min[0], -min[1], -min[2]);
		Matrix desfazTranslacao = CriaMatriz.Translada(min[0], min[1], min[2]);
		iteratorVertice = this.vertices.iterator();
		while(iteratorVertice.hasNext()){
			Vertice vertice = iteratorVertice.next();
			vertice.mudaAnimacao(duracao, transformacao, translada, desfazTranslacao);
		}
	}
	public void anima(double t){
		//if(this.endedAnimacao(t))return;
		Iterator<Vertice> iteratorVertice = this.vertices.iterator();
		while(iteratorVertice.hasNext()){
			Vertice vertice = iteratorVertice.next();
			vertice.anima(t);
		}
	}
	public boolean endedAnimacao(double t){
		Vertice v = this.vertices.get(0);
		return v.endedAnimacao(t);
	}
}
