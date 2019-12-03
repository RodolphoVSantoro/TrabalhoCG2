import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;

class Tela extends JPanel{
    static int LARGURATELA = 600;
    static int ALTURATELA = 700;
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
        this.idPrisma = cena.adicionaObjeto("objetos/prisma.3dwf", new Cor(new Color(0,100,10,255)));
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
        this.cena.desenha(g2d);
    }

    public static void main(String[] args) {
        Tela tela = new Tela();
        JFrame janela = new JFrame();
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
                    kf_index++;
                }
                if(kf_index<10)
                    tela.cena.mudaAnimacao(tela.idPrisma, tela.duracaoAnimacao[kf_index], tela.kf[kf_index]);
            }
            tela.repaint();
            try {Thread.sleep(12);} catch(Exception e){} 
        }
    }
}
