import java.util.ArrayList;
import java.util.Iterator;
import java.io.File;
import java.util.Scanner;
import java.io.IOException;

public class Matrix{
	public double valores[][];
	public Matrix(String fname){
		this.valores = new double[4][4];
		try{
			File arq = new File(fname);
			Scanner leitor = new Scanner(arq);
			for(int i=0;i<4;i++){
				String linha = leitor.nextLine();
				String tmp[] = linha.split(" ");
				for(int j=0;j<4;j++)
					this.valores[i][j] = Double.parseDouble(tmp[j]);
			}
			leitor.close();
		}
		catch(Exception e){
			System.out.println("Erro ao criar objeto:");
			e.printStackTrace();
		}
	}
	public Matrix(){
		this.valores = new double[4][4];
	}
	public Matrix(double m[][]){
		this.valores = new double[4][4];
		this.setMatriz(m);
	}

	static public enum MultiplyOrder{
		PRE, POS;
	}

	public double get(int linha, int coluna){
		return this.valores[linha][coluna];
	}
	public void setMatriz(double m[][]){
		for(int i=0;i<4;i++)
			for (int j=0;j<4;j++)
				this.valores[i][j]=m[i][j];
	}
	public void transpose(){
		double m[][] = new double[4][4];
		for(int i=0;i<4;i++)
			for(int j=0;j<4;j++)
				m[i][j] = this.valores[j][i];
		for(int i=0;i<4;i++)
			for(int j=0;j<4;j++)
				this.valores[i][j] = m[i][j];
	}
	public double[] posMultiply(double v[]){
		double r[] = {0.0, 0.0, 0.0, 0.0};
		for(int i=0;i<4;i++)
			for(int j=0;j<4;j++)
				r[i] += this.valores[j][i]*v[j];
		return r;
	}
	public double[] preMultiply(double v[]){
		double r[] = {0.0, 0.0, 0.0, 0.0};
		for(int i=0;i<4;i++)
			for(int j=0;j<4;j++)
				r[i] += this.valores[i][j]*v[j];
		return r;
	}
	public Matrix posMultiply(Matrix m){
		Matrix r = new Matrix();
		for(int i=0;i<4;i++)
			for(int j=0;j<4;j++)
				for(int k=0;k<4;k++)
					r.valores[i][j] += this.valores[k][j]*m.get(i, k);
		return r;
	}
	public Matrix preMultiply(Matrix m){
		Matrix r = new Matrix();
		for(int i=0;i<4;i++)
			for(int j=0;j<4;j++)
				for(int k=0;k<4;k++)
					r.valores[i][j] += this.valores[i][k]*m.get(k, j);
		return r;
	}
	private ArrayList<Vertice> multiply(ArrayList<Vertice> vs, Vertice.CoordinateSystem inMode, 
		Vertice.CoordinateSystem outMode, MultiplyOrder mO)
	{
		ArrayList<Vertice> av = new ArrayList<Vertice>();
		Iterator<Vertice> iv = vs.iterator();
		while(iv.hasNext()){
			Vertice v = iv.next();
			v.transforma(this, inMode, outMode, mO);
			av.add(v);
		}
		return av;
	}
	public ArrayList<Vertice> posMultiply(ArrayList<Vertice> vs, Vertice.CoordinateSystem inMode, 
		Vertice.CoordinateSystem outMode)
	{
		return this.multiply(vs, inMode, outMode, MultiplyOrder.POS);
	}
	public ArrayList<Vertice> preMultiply(ArrayList<Vertice> vs, Vertice.CoordinateSystem inMode, 
		Vertice.CoordinateSystem outMode)
	{
		return this.multiply(vs, inMode, outMode, MultiplyOrder.PRE);
	}
	public void print(){
		int i,j;
		for(i=0;i<4;i++){
			for(j=0;j<3;j++)
				System.out.print(this.valores[i][j]+", ");
			System.out.print(this.valores[i][j]+"\n");
		}
		System.out.print("\n");
	}
}
