public class Vertice{
	static public enum CoordinateSystem{
		SRO(0), CENA(1), NORMAL(2), TELA(3);
		public int ind;
		CoordinateSystem(int v){
			this.ind = v;
		}
	}
	//0-coordenadas individuais(SRO)
	//1-coordenadas na cena 3d
	//2-coordenadas 2d normalizadas
	//3-coordenadas na tela
	private double x[]={0, 0, 0, 0}, y[]={0, 0, 0, 0}, z[]={0, 0, 0, 0}, l[]={1, 1, 1, 1};
	private Anima animacao;
	public Vertice(double x, double y, double z, double l){
		this.x[CoordinateSystem.SRO.ind]=this.x[CoordinateSystem.CENA.ind]=x;
		this.y[CoordinateSystem.SRO.ind]=this.y[CoordinateSystem.CENA.ind]=y;
		this.z[CoordinateSystem.SRO.ind]=this.z[CoordinateSystem.CENA.ind]=z;
		this.l[CoordinateSystem.SRO.ind]=this.l[CoordinateSystem.CENA.ind]=l;
	}
	public Vertice(double x, double y, double z){
		this.x[CoordinateSystem.SRO.ind]=this.x[CoordinateSystem.CENA.ind]=x;
		this.y[CoordinateSystem.SRO.ind]=this.y[CoordinateSystem.CENA.ind]=y;
		this.z[CoordinateSystem.SRO.ind]=this.z[CoordinateSystem.CENA.ind]=z;
		this.l[CoordinateSystem.SRO.ind]=this.l[CoordinateSystem.CENA.ind]=1;
	}
	public double[] verticeToArray(CoordinateSystem tm){
		double array[] = {x[tm.ind], y[tm.ind], z[tm.ind], l[tm.ind]};
		return array;
	}
	public void writeArray(double array[], CoordinateSystem tm){
		this.x[tm.ind]=array[0];
		this.y[tm.ind]=array[1];
		this.z[tm.ind]=array[2];
		this.l[tm.ind]=array[3];
	}
	public void transforma(Matrix m, CoordinateSystem inMode, CoordinateSystem outMode, Matrix.MultiplyOrder mO){
		double out[], in[] = this.verticeToArray(inMode);
		if(mO==Matrix.MultiplyOrder.PRE)
			out = m.preMultiply(in);
		else
			out = m.posMultiply(in);
		//dividindo todos por l(fator de escala da homogenea)
		for(int i=0;i<4;i++)
			out[i]/=out[3];
		this.writeArray(out, outMode);
	}
	public void transforma(Matrix m, CoordinateSystem inMode, CoordinateSystem outMode, Matrix.MultiplyOrder mO, Matrix bound){
		double out[], in[] = this.verticeToArray(inMode);
		if(mO==Matrix.MultiplyOrder.PRE){
			out = bound.preMultiply(in);
			out = m.preMultiply(out);
			bound = CriaMatriz.Translada(-bound.valores[3][0], -bound.valores[3][1], -bound.valores[3][2]);
			//System.out.println(bound.valores[3][0]+", "+bound.valores[3][1]+", "+bound.valores[3][2]);
			bound.transpose();
			out = bound.preMultiply(out);
		}
		else{
			//bound.print();
			out = bound.posMultiply(in);
			out = m.posMultiply(out);
			bound = CriaMatriz.Translada(-bound.valores[3][0], -bound.valores[3][1], -bound.valores[3][2]);
			//bound.print();
			out = bound.posMultiply(out);
		}
		//dividindo todos por l(fator de escala da homogenea)
		for(int i=0;i<4;i++)
			out[i]/=out[3];
		this.writeArray(out, outMode);
	}
	/*
	public void transforma(Matrix m, CoordinateSystem inMode, CoordinateSystem outMode, Matrix.MultiplyOrder mO, Matrix bound){
		double out[], in[] = this.verticeToArray(inMode);
		if(mO==Matrix.MultiplyOrder.PRE){
			out = bound.preMultiply(in);
			out = m.preMultiply(out);
			bound = CriaMatriz.Translada(-bound.valores[0][3], -bound.valores[1][3], -bound.valores[2][3]);
			//System.out.println(bound.valores[3][0]+", "+bound.valores[3][1]+", "+bound.valores[3][2]);
			out = bound.preMultiply(out);
		}
		else{
			//bound.print();
			out = bound.posMultiply(in);
			out = m.posMultiply(out);
			bound = CriaMatriz.Translada(-bound.valores[3][0], -bound.valores[3][1], -bound.valores[3][2]);
			//bound.print();
			out = bound.posMultiply(out);
		}
		//dividindo todos por l(fator de escala da homogenea)
		for(int i=0;i<4;i++)
			out[i]/=out[3];
		this.writeArray(out, outMode);
	}*/
	public void mudaAnimacao(double duracao, Matrix transformacao, Matrix centraliza, Matrix volta){
		double out[] = new double[4];
		//centraliza.print();
		out = centraliza.posMultiply(this.verticeToArray(CoordinateSystem.CENA));
		out = transformacao.posMultiply(out);
		out = volta.posMultiply(out);
		animacao = new Anima(out[0],out[1],out[2],out[3],duracao);
	}
	public void anima(double t){
		this.animacao.anima(this, t);
	}
	public boolean endedAnimacao(double t){
		return this.animacao.ended(t);
	}
	public int getX(){
		return (int)this.x[CoordinateSystem.TELA.ind];
	}
	public int getY(){
		return (int)this.y[CoordinateSystem.TELA.ind];
	}
}