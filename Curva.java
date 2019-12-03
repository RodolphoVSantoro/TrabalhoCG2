import java.awt.geom.CubicCurve2D;
import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.Color;

public class Curva{
	private int indVertice[];
	public Curva(int indVertices[]){
		this.indVertice=indVertices.clone();
	}
	public Vertice getVertice(ArrayList<Vertice> v,int n){
		return v.get(this.indVertice[n]);
	}
	public void desenha(Graphics2D g, ArrayList<Vertice> vertices){
		Vertice v[] = {this.getVertice(vertices, 0), this.getVertice(vertices, 1),
			this.getVertice(vertices, 2), this.getVertice(vertices, 3)};
		CubicCurve2D c = new CubicCurve2D.Float(v[0].getX(),v[0].getY(),v[1].getX(),v[1].getY(),
												v[2].getX(),v[2].getY(),v[3].getX(),v[3].getY());
		g.setPaint(new Color(0,0,0,255));
		g.draw(c);
	}
}