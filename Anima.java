import java.util.ArrayList;
import java.util.Iterator;

public class Anima{
	private double duracao;
	private double start;
	//x,y,z e l finais. Quando terminar a animação
	private double old[];
	private double coord[];
	public Anima(){
		this.duracao=this.start=-1;
	}
	public Anima(double x, double y, double z, double l, double duracao){
		this.start=-1;
		double coord[] = {x, y, z, l};
		this.coord = coord;
		this.duracao = duracao;
	}
	public boolean ended(double t){
		return ((t-this.start) >= this.duracao);
	}
	public void anima(Vertice v, double t){
		double vet[] = v.verticeToArray(Vertice.CoordinateSystem.CENA);
		//System.out.println("dt="+dt);
		if(start==-1){
			old = new double[4];
			for(int i=0;i<4;i++)
				old[i]=vet[i];
			start=t;
		}
		if(!this.ended(t))
			for(int i=0;i<4;i++)
				vet[i] = this.old[i] + (this.coord[i]-this.old[i])*((t-this.start)/this.duracao);
		else
			for(int i=0;i<4;i++)
				vet[i] = this.coord[i];
		v.writeArray(vet, Vertice.CoordinateSystem.CENA);
	}
}