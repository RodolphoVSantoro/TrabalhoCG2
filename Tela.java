import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Polygon;
import java.awt.Color;
import java.awt.GradientPaint;

class Tela extends JPanel{
    /*vai usar cada id como parametro
    pra acessar e modificar o objeto 3d*/
    static int LARGURATELA = 600;
    static int ALTURATELA = 600;
    int idPrisma;
    Cena cena;
    Timer animationTimer;
    Matrix kf[];
    double duracaoAnimacao[] = {1.0, 0.5, 1.0, 1.0, 1.0, 0.01, 1.0, 1.0, 0.01, 0.01};
    //double duracaoAnimacao[] = {1.0, 0.1, 0.05, 0.05, 0.05, 0.05, 0.05, 0.05, 0.05, 0.05};
    int repeticoes[] = {0, 4, 0, 0, 0, 140, 0, 0, 70, 0};
    Tela() {
        this.cena = new Cena(LARGURATELA, ALTURATELA);
        this.animationTimer = new Timer();
        this.idPrisma = cena.adicionaObjeto("objetos/prisma.3dwf");
        kf = new Matrix[10];
        String name = "transformacoes/prisma";
        for(int i=0;i<10;i++){
            String fname = name + (i+1) + ".matriz";
            kf[i] = new Matrix(fname);
        }
        this.cena.mudaAnimacao(idPrisma, duracaoAnimacao[0], kf[0]);
        this.animationTimer = new Timer();
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        /*
        Color vermelhoClaro = new Color(255, 120, 120, 255);
        Color vermelhoEscuro = new Color(135, 0, 0, 255);
        Color preto = new Color(80, 0, 0, 255);
        Color transparente = new Color(157,30,30,90);
        float centroX = (100+150+50)/3 , centroY = (100+150+150)/3;
        GradientPaint p1 = new GradientPaint(
            centroX, centroY, transparente,
            100, 100, vermelhoClaro
            );
        GradientPaint p2 = new GradientPaint(
            centroX, centroY , transparente,
            150, 150, vermelhoEscuro);
        GradientPaint p3 = new GradientPaint(
            centroX, centroY, transparente,
            50, 150, preto
            );
        Polygon poly = new Polygon();
        poly.addPoint(100, 100);
        poly.addPoint(150, 150);
        poly.addPoint(50,  150);
        g2d.setPaint(p1);
        g2d.fillPolygon(poly);
        g2d.setPaint(p2);
        g2d.fillPolygon(poly);
        g2d.setPaint(p3);
        g2d.fillPolygon(poly);
        */
        this.cena.desenha(g2d);
    }

    public static void main(String[] args) {
        Tela tela = new Tela();
        JFrame janela = new JFrame();
        //Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        janela.setLocation(360,70);
        janela.add(tela);
        janela.setSize(LARGURATELA, ALTURATELA);
        janela.setVisible(true);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tela.animationTimer.start();
        int kf_index=1;
        int nRepetidas = 0;
        while(true){
            double t = tela.animationTimer.getTime(false);
            tela.cena.anima(t);
            if(tela.cena.endedAnimacao(tela.idPrisma, t) && kf_index<10){
                if(nRepetidas < tela.repeticoes[kf_index])
                    nRepetidas++;
                else{
                    nRepetidas=0;
                    if(kf_index<9)
                        kf_index++;
                }
                tela.cena.mudaAnimacao(tela.idPrisma, tela.duracaoAnimacao[kf_index], tela.kf[kf_index]);
            }
            tela.repaint();
            try {Thread.sleep(12);} catch(Exception e){} 
        }
    }
}
