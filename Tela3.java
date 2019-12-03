import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import javax.swing.JPanel;

class Tela3 extends JPanel{
    static int LARGURATELA = 600;
    static int ALTURATELA = 700;
    public int idCurva;
    public Cena cena;
    public Timer animationTimer;
    Tela3() {
        this.cena = new Cena(LARGURATELA, ALTURATELA);
        this.animationTimer = new Timer();
        this.idCurva = cena.adicionaCurva("objetos/onda.3dwf");
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        this.cena.desenha(g2d);
    }

    public static void main(String[] args) {
        Tela3 tela = new Tela3();
        JFrame janela = new JFrame();
        janela.setLocation(360,70);
        janela.add(tela);
        janela.setSize(LARGURATELA, ALTURATELA);
        janela.setVisible(true);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Matrix translada = new Matrix("transformacoes/translada.matriz");
        Matrix roda = new Matrix("transformacoes/roda.matriz");
        tela.cena.mudaAnimacao(tela.idCurva, 0, translada);
        while(true){
            double t = tela.animationTimer.getTime(false);
            tela.cena.anima(t);
            tela.repaint();
            try {Thread.sleep(12);} catch(Exception e){} 
        }
    }
}
