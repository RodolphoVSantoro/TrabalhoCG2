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
		if(this.l<0)
			this.l=0;
		if(this.l>100)
			this.l=100;
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
	public Cor copia(){
		return new Cor(this.l,this.a,this.b,this.alpha);
	}	
}