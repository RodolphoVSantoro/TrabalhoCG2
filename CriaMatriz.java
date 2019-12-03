public class CriaMatriz{
	private CriaMatriz(){}
	static public Matrix Identidade(){
		Matrix m = new Matrix();
		double mat[][] =
		{
			{1, 0, 0, 0},
			{0, 1, 0, 0},
			{0, 0, 1, 0},
			{0, 0, 0, 1}
		};
		m.setMatriz(mat);
		return m;
	}
	static public Matrix Escala(double eX, double eY, double eZ){
		double mat[][] =
		{
			{eX, 0, 0, 0},
			{0, eY, 0, 0},
			{0, 0, eZ, 0},
			{0, 0,  0, 1}
		};
		return new Matrix(mat);
	}
	static public Matrix rodaXY(double t){
		double mat[][] = 
		{
			{Math.cos(t), Math.sin(t), 0, 0},
			{-Math.sin(t), Math.cos(t), 0, 0},
			{0, 0, 1, 0},
			{0, 0, 0, 1}
		};
		return new Matrix(mat);
	}
	static public Matrix rodaXZ(double t){
		double mat[][] = 
		{
			{Math.cos(t), 0, -Math.sin(t), 0},
			{0, 1, 0, 0},
			{Math.sin(t), Math.cos(t), 0, 0},
			{0, 0, 0, 1}
		};
		return new Matrix(mat);
	}
	static public Matrix rodaYZ(double t){
		double mat[][] =
		{
			{1, 0, 0, 0},
			{0, Math.cos(t), Math.sin(t), 0},
			{0, -Math.sin(t), Math.cos(t), 0},
			{0, 0, 0, 1}
		};
		return new Matrix(mat);
	}
	static public Matrix Tela(int larguraTela, int alturaTela){
		Matrix m1 = CriaMatriz.Escala(1,-1,1);
		Matrix m2 = CriaMatriz.Escala(larguraTela, alturaTela, 0);
		Matrix m3 = CriaMatriz.Translada(0, alturaTela, 0);
		m1=m1.preMultiply(m2);
		m1=m1.preMultiply(m3);
		return m1;
	}
	static public Matrix Perspectiva(double xc, double yc, double zc){
		double r,s,t;
		if(xc==0) r=0;
		else r=-1.0/xc;
		if(yc==0) s=0;
		else s=-1.0/yc;
		if(zc==0) t=0;
		else t=-1.0/zc;
		double mat[][] = 
		{
			{1, 0, 0, r},
			{0, 1, 0, s},
			{0, 0, 1, t},
			{0, 0, 0, 1}
		};
		Matrix m = new Matrix(mat);
		return m;
	}
	static public Matrix Translada(double x, double y, double z){
		double mat[][] = 
		{
			{1, 0, 0, 0},
			{0, 1, 0, 0},
			{0, 0, 1, 0},
			{x, y, z, 1}
		};
		return new Matrix(mat);
	}
	static public Matrix Normaliza(double limX, double limY){
		Matrix m1 = CriaMatriz.Translada(limX, limY, 0);
		Matrix m2 = CriaMatriz.Escala(1.0/limX, 1.0/limY, 0);
		m1 = m1.posMultiply(m2);
		return m1;
	}
}
