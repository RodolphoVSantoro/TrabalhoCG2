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
	private Cor corBase;
	public Objeto(){
		this.vertices = new ArrayList<Vertice>();
		this.faces = new ArrayList<Face>();
	}
	public Objeto(ArrayList<Vertice> v, ArrayList<Face> f){
		this.vertices = v;
		this.faces = f;
	}
	public Objeto(String fname, Cor corBase){
		int h=0;
		this.vertices = new ArrayList<Vertice>();
		this.faces = new ArrayList<Face>();
		this.corBase=corBase;
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
						v.setCor(corBase.copia());
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
	public void desenha(Graphics2D g2d, int ordem[]){
		for(int i=0;i<ordem.length;i++){
			Face f = this.faces.get(ordem[i]);
			if(f.visivel())
				f.desenha(g2d, this.vertices);
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
	public void backFaceCulling(){
		Iterator<Face> iteratorFaces = this.faces.iterator();
		while(iteratorFaces.hasNext()){
			Face f = iteratorFaces.next();
			f.backFaceCulling(this.vertices);
		}
	}
	public int[] painter(double observador[]){
		double distFace[] = new double[this.faces.size()];
		int ind[] = new int[this.faces.size()];
		for(int i=0;i<ind.length;i++)
			ind[i]=i;
		Iterator<Face> iteratorFace = this.faces.iterator();
		for(int i=0;iteratorFace.hasNext();i++){
			Face f = iteratorFace.next();
			double centroide[] = f.getCentroide(this.vertices);
			distFace[i]=0;
			for(int j=0;j<3;j++)
				distFace[i]+=(centroide[j]-observador[j])*(centroide[j]-observador[j]);
		}
		Sort.quickSort(distFace,ind);
		return ind;
	}
	static private double abs(double x){
		if(x>=0)return x;
		else return -x;
	}
	static private double abs(double v[]){
		double s=0;
		for(int i=0;i<v.length;i++)
			s+=v[i]*v[i];
		return Math.sqrt(s);
	}
	static private double escalar(double v1[], double v2[]){
		double s=0;
		for(int i=0;i<v1.length && i<v2.length;i++)
			s+=v1[i]*v2[i];
		return s;
	}
	static private double dist2(double p1[], double p2[]){
		double s=0;
		for(int i=0;i<p1.length && i<p2.length;i++)
			s+=(p1[i]-p2[i])*(p1[i]-p2[i]);
		return s;
	}
	public void ilumina(double observador[]){
		double normalVertice[][] = new double[this.vertices.size()][4];
		double n[] = new double[this.vertices.size()];
		for(int i=0;i<this.vertices.size();i++)
			n[i]=0;
		Iterator<Face> iteratorFaces = this.faces.iterator();
		while(iteratorFaces.hasNext()){
			Face f = iteratorFaces.next();
			double tmp[] = f.getNormal(this.vertices);
			double div = abs(tmp[0])+abs(tmp[1])+abs(tmp[2]);
			for(int i=0;i<f.nVertices();i++){
				int ind=f.getIndVertice(i);
				for(int j=0;j<3;j++)
					normalVertice[ind][j]+=(tmp[j]/div);
				n[ind]++;
			}
		}
		for(int i=0;i<this.vertices.size();i++){
			for(int j=0;j<3;j++)
				normalVertice[i][j]/=n[i];
			Cor c = corBase.copia();
			double k[] = {0,0,1};
			double tmp[] = this.vertices.get(i).verticeToArray(Vertice.CoordinateSystem.CENA);
			double v[] = {tmp[0]/tmp[3],tmp[1]/tmp[3],tmp[2]/tmp[3]};
			//System.out.println("L+"+100*abs(escalar(normalVertice[i],k)));
			c.somaL(100*abs(escalar(normalVertice[i],k)));

			this.vertices.get(i).setCor(c);
		}

	}
}
