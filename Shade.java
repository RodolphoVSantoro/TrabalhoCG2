public class Shade{
	//posicao do observador
	double X,Y,Z;
	public Shade(double x, double y, double z){
		this.X=x;
		this.Y=y;
		this.Z=z;
	}
	public double[] getObservador(){
		double obs[] = {this.X,this.Y,this.Z};
		return obs;
	}
}