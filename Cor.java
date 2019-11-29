import java.awt.*;

public class Cor{
	final private double SIGMA = 6.0/29.0;
	final private double Xn = 95.0489, Yn = 100.0, Zn = 108.8840;
	private double alpha;
	private double l,a,b;
	private double red,green,blue;
	public Cor(Color c){
		this.red=c.getRed();
		this.green=c.getGreen();
		this.blue=c.getBlue();
		this.alpha=c.getAlpha();
		this.RGBToLAB();
	}
	public Cor(double l, double a, double b, double alpha){
		this.l=l;
		this.a=a;
		this.b=b;
		this.alpha = alpha;
		this.LABToRGB();
	}
	double getL(){
		return this.l;
	}
	double getA(){
		return this.a;
	}
	double getB(){
		return this.b;
	}
	int getAlpha(){
		return (int)this.alpha;
	}
	int getRed(){
		return (int)Math.abs(Math.round(this.red));
	}
	int getGreen(){
		return (int)Math.abs(Math.round(this.green));
	}
	int getBlue(){
		return (int)Math.abs(Math.round(this.blue));
	}
	void somaL(double n){
		this.l+=n;
		this.LABToRGB();
	}
	void RGBToLAB() {
	    double red = this.red * 0.003922;
	    double green = this.green * 0.003922;
	    double blue = this.blue * 0.003922;

	    red = (red > 0.04045) ? Math.pow(((red + 0.055)/1.055), 2.4) : red / 12.92;
	    green = (green > 0.04045) ? Math.pow(((green + 0.055)/1.055), 2.4) : green / 12.92;
	    blue = (blue > 0.04045) ? Math.pow(((blue + 0.055)/1.055), 2.4) : blue / 12.92;

	    double x = 0.412424  * red + 0.357579 * green + 0.180464  * blue;
	    double y = 0.212656  * red + 0.715158 * green + 0.0721856 * blue;
	    double z = 0.0193324 * red + 0.119193 * green + 0.950444  * blue;

	    this.l = 116 * ( ( y / 1.000000) > 0.008856 ? Math.pow(y / 1.000000, 0.333333) : 7.787 * y / 1.000000 + 0.137931) - 16;
	    this.a = 500 * ( ((x / 0.950467) > 0.008856 ? Math.pow(x / 0.950467, 0.333333) : 7.787 * x / 0.950467 + 0.137931) - ((y / 1.000000) > 0.008856 ? Math.pow(y / 1.000000, 0.333333) : 7.787 * y / 1.000000 + 0.137931) );
	    this.b = 200 * ( ((y / 1.000000) > 0.008856 ? Math.pow(y / 1.000000, 0.333333) : 7.787 * y / 1.000000 + 0.137931) - ((z / 1.088969) > 0.008856 ? Math.pow(z / 1.088969, 0.333333) : 7.787 * z / 1.088969 + 0.137931) );
	}

	void LABToRGB() {
	    double y = (this.l + 16 ) / 116;
	    double x = this.a / 500 + y;
	    double z = y - this.b / 200;

	    y = (y*y*y > 0.008856) ? y*y*y : (y - (16 / 116)) / 7.787;
	    x = (x*x*x > 0.008856) ? x*x*x : (x - (16 / 116)) / 7.787;
	    z = (z*z*z > 0.008856) ? z*z*z : (z - (16 / 116)) / 7.787;

	    double red = 0.950467 * x *  3.2406 + 1.000000 * y * -1.5372 + 1.088969 * z * -0.4986;
	    double green = 0.950467 * x * -0.9689 + 1.000000 * y *  1.8758 + 1.088969 * z *  0.0415;
	    double blue = 0.950467 * x *  0.0557 + 1.000000 * y * -0.2040 + 1.088969 * z *  1.0570;

	    this.red = (255 * ( (red > 0.0031308) ? 1.055 * (Math.pow(red, (1/2.4)) - 0.055) : red * 12.92 ));
	    this.green = (255 * ( (green > 0.0031308) ? 1.055 * (Math.pow(green, (1/2.4)) - 0.055) : green * 12.92 ));
	    this.blue = (255 * ( (blue > 0.0031308) ? 1.055 * (Math.pow(blue, (1/2.4)) - 0.055) : blue * 12.92 ));
	}
	/*double normaliza(double comp){
		comp/=255.0;
		if (comp > 0.04045) 
			return  100.0*Math.pow(( comp + 0.055 )/1.055, 2.4);
	    else
	    	return (comp/12.92)*100.0;
	}
	void rgbToXYZ(){
		double red = normaliza(this.red);
		double green =normaliza(this.green);
		double blue = normaliza(this.blue);
		this.x = red*0.4124 + green*0.3576 + blue*0.1805;
	    this.y = red*0.2126 + green*0.7152 + blue*0.0722;
	    this.z = red* 0.0193 + green*0.1192 + blue*0.9505;
	    System.out.println("in:"+this.red+" "+this.blue+" "+this.z);
	    System.out.println("in: "+this.x+" "+this.y+" "+this.z);
	}
	double f(double t){
		if(t>Math.pow(this.SIGMA,3.0)) 
			return Math.pow(t,1/3.0);
		else 
			return t/(3.0*this.SIGMA*this.SIGMA)+(4.0/29.0);
	}
	double fInv(double t){
		if(t>this.SIGMA)
			return t*t*t;
		else
			return 3*this.SIGMA*this.SIGMA*(t-4.0/29.0);
	}
	void XYZToLAB(){
		this.l=116.0*f(this.y/this.Yn)-16;
		this.a=500.0*(f(this.x/this.Xn)-f(this.y/this.Yn));
		this.b=200.0*(f(this.y/this.Yn)-f(this.z/this.Zn));
	}
	double bound(double x, double min, double max){
		if(max<min)
			return x;
		if(x>max)
			return max;
		else if(x<min)
			return min;
		else
			return x;
	}
	void LABToXYZ(){
		this.x=this.Xn*fInv((this.l+16.0)/116.0+this.a/500.0);
		this.y=this.Yn*fInv((this.l+16.0)/116.0);
		this.z=this.Zn*fInv((this.l+16.0)/116.0-this.b/200.0);
		
		this.x=bound(this.x,0,1);
		this.y=bound(this.y,0,1);
		this.z=bound(this.z,0,1);
		
		System.out.println("f: "+this.l+" "+this.a+" "+this.b);
		System.out.println("f: "+this.x+" "+this.y+" "+this.z);
	}
	double normalizaInv(double c) {
	  if (Math.abs(c) < 0.0031308) {
	    return 12.92 * c;
	  }
	  return 1.055 * Math.pow(c, 0.41666) - 0.055;
	}
	void XYZToRGB(){
		this.red =  3.2404542*this.x - 1.5371385*this.y - 0.4985314*this.z;
		this.green = -0.9692660*this.x + 1.8760108*this.y + 0.0415560*this.z;
		this.blue =  0.0556434*this.x - 0.2040259*this.y + 1.0572252*this.z;
		this.red = normalizaInv(this.red);
		this.green = normalizaInv(this.green);
		this.blue = normalizaInv(this.blue);
	}
	*/
}