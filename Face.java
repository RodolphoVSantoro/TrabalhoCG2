import java.util.ArrayList;
import java.awt.Color;
import java.awt.Polygon;
import java.awt.GradientPaint;
import java.awt.Graphics2D;

public class Face{
	private int indVertice[];
	private Boolean visibilidade;
	public Face(int iV[]){
		this.visibilidade=true;
		indVertice = iV.clone();
	}
	public int nVertices(){
		return this.indVertice.length;
	}
	public int getIndVertice(int n){
		return this.indVertice[n];
	}
	public Vertice getVertice(ArrayList<Vertice> vertices, int n){
		return vertices.get(this.indVertice[n]);
	}
	public double[] getCentroide(ArrayList<Vertice> vertices){
		double xyzl[] = {0,0,0,1};
		for(int i=0;i<this.nVertices();i++){
			Vertice v = this.getVertice(vertices, i);
			double arr[] = v.verticeToArray(Vertice.CoordinateSystem.CENA);
			for(int j=0;j<3;j++)
				xyzl[i]+=arr[i]/arr[3];
		}
		for(int i=0;i<3;i++)
			xyzl[i]/=this.nVertices();
		return xyzl;
	}
	public double[] getNormal(ArrayList<Vertice> vertices){
		double x,y,z;
		Vertice v[] = {null, null, null};
		for(int i=0;i<3;i++)
			v[i] = this.getVertice(vertices, i);
		double p1[] = v[0].verticeToArray(Vertice.CoordinateSystem.CENA);
		double p2[] = v[1].verticeToArray(Vertice.CoordinateSystem.CENA);
		double p3[] = v[2].verticeToArray(Vertice.CoordinateSystem.CENA);
		for(int i=0;i<3;i++){
			p1[i]/=p1[3];
			p2[i]/=p2[3];
			p3[i]/=p3[3];
		}
		x = (p2[1]-p1[1])*(p3[2]-p2[2])-(p2[2]-p1[2])*(p3[1]-p2[1]);
		y = (p2[2]-p1[2])*(p3[0]-p2[0])-(p2[0]-p1[0])*(p3[2]-p2[2]);
		z = (p2[0]-p1[0])*(p3[1]-p2[1])-(p2[1]-p1[1])*(p3[0]-p2[0]);
		double normal[] = {x,y,z,1};
		return normal;
	}
	public Boolean visivel(){
		return this.visibilidade;
	}
	public void backFaceCulling(ArrayList<Vertice> vertices){
		double normal[] = this.getNormal(vertices);
		double produto=0;
		double c[] = this.getCentroide(vertices);
		for(int i=0;i<3;i++)
			produto-=normal[i]*c[i];
		if(produto > 0)
			this.visibilidade=false;
		else
			this.visibilidade=true;
	}
	public void desenha(Graphics2D g, ArrayList<Vertice> vertices){
		Polygon face = new Polygon();
		double centroX=0, centroY=0;
		int nVertices=this.nVertices();
		double mR=0,mG=0,mB=0,mA=0;
		for(int i=0;i<nVertices;i++){
			Vertice v = this.getVertice(vertices,i);
			Cor c = v.getCor();
			face.addPoint(v.getX(),v.getY());
			centroX+=v.getX();
			centroY+=v.getY();
			mR+=c.getRed();
			mG+=c.getGreen();
			mB+=c.getBlue();
			mA+=c.getAlpha();
		}
		centroX/=nVertices;
		centroY/=nVertices;
		mR/=nVertices;
		mG/=nVertices;
		mB/=nVertices;
		mA/=nVertices;
		Color meio = new Color((int)mR,(int)mG,(int)mB,(int)mA);
		for(int i=0;i<nVertices;i++){
			Vertice v = vertices.get(indVertice[i]);
			Vertice v2 = vertices.get(indVertice[(i+1)%nVertices]);
			Cor cor = v.getCor();
			Color c = new Color(cor.getRed(),cor.getGreen(),cor.getBlue(),cor.getAlpha());
			GradientPaint p = new GradientPaint(
	            (float)centroX, (float)centroY, meio,
	            v.getX(), v.getY(), c
            );

            g.setPaint(p);
            g.fillPolygon(face);
		}
	}
}
