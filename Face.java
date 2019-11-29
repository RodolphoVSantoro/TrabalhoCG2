import java.awt.*;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Polygon;
import java.awt.GradientPaint;

public class Face{
	//guarda indice que referencia
	//vertice na arraylist do objeto
	private int indVertice[];
	public Face(int iV[]){
		indVertice = new int[iV.length];
		for(int i=0; i<iV.length; i++)
			indVertice[i]=iV[i];
	}
	
	public void desenha(Graphics g, ArrayList<Vertice> vertices){
		Vertice v1 = vertices.get(indVertice[0]), v2;
		for(int i=1;i<this.indVertice.length;i++){
			v2 = vertices.get(indVertice[i]);
			g.drawLine(v1.getX(), v1.getY(), v2.getX(), v2.getY());
			v1 = v2;
		}
		v2 = vertices.get(indVertice[0]);
		g.drawLine(v1.getX(), v1.getY(), v2.getX(), v2.getY());
	}
	
	/*
	public void desenha(Graphics g, ArrayList<Vertice> vertices, ArrayList<Color> cores){
		Polygon face = new Polygon();
		Color transparente;
		for(int i=0;i<this.indVertice.length;i++){
			Vertice v = vertices.get(indVertice[i]);
			face.addPoint(v.getX,v.getY);
		}
		for(int i=0;i<this.indVertice.length;i++){
			Vertice v = vertices.get(indVertice[i]);
			Color cor = cores.get(indVertice[i]);
			GradientPaint p1 = new GradientPaint(
            centroX, centroY, transparente,
            100, 100, cor
            );
		}
	}
	*/
}
