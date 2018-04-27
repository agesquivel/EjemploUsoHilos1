import javax.swing.*;
import java.awt.*;

class TestThread extends Thread {
    private Graphics g;
    private int linea;

    public TestThread ( Graphics g, int l) {
        this.g = g;
        this.linea = l;
    }

    // El metodo run() es similar al main(), pero para
    // threads. Cuando run() termina el thread muere
    public void run() {
        SimulaCarrera(g, linea);
    }

    public void SimulaCarrera(Graphics contGraf, int coordY){
        int inicio = 100;
        int coordX = 105;

        contGraf.setColor(new Color((int) (Math.random()*255),
                (int) (Math.random()*255),
                (int) (Math.random()*255)) );

        for (int i = 0; i < 50; i++){
            contGraf.fillRect(inicio, coordY, coordX - inicio, 20);
            coordX += 5;

            // Retrasamos la ejecución el tiempo especificado
            try {
                this.sleep( 200 );
            } catch( InterruptedException e )
            {
                System.out.println(e.getStackTrace());
            }

            // Ahora imprimimos
            System.out.println( "Linea = " + coordY);
        }
    }
} //Class TestThread


class TestRunnable implements Runnable {
    //Thread hilo;

    private Graphics g;
    private int linea;

    public TestRunnable ( Graphics g, int l) {
        this.g = g;
        this.linea = l;
        //hilo = new Thread(this);
    }

    public void iniciar() {
        //this.hilo.start();
    }

    // El metodo run() es similar al main(), pero para
    // threads. Cuando run() termina el thread muere
    @Override
    public void run() {
        SimulaCarrera(g, linea);
    }

    public void SimulaCarrera(Graphics contGraf, int coordY){
        int inicio = 100;
        int coordX = 105;

        contGraf.setColor(new Color((int) (Math.random()*255),
                (int) (Math.random()*255),
                (int) (Math.random()*255)) );

        for (int i = 0; i < 50; i++){
            contGraf.fillRect(inicio, coordY, coordX - inicio, 20);
            coordX += 5;

            // Retrasamos la ejecución el tiempo especificado
            try {
                //hilo.sleep( 200 );
                Thread.currentThread().sleep(200);
            } catch( InterruptedException e )
            {
                System.out.println(e.getStackTrace());
            }

            // Ahora imprimimos
            System.out.println( "Linea = " + coordY);
        }
    }
} //class TestRunnable

public class EjemploHilo1 {
    public static void main( String args[] ) {
        JFrame Ventana = new JFrame("Simula carrera");
        Ventana.setSize(600, 500);
        Ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel canvas = new JPanel();
        Ventana.add(canvas, BorderLayout.CENTER);

        Ventana.setVisible(true);

        /*TestThread t1,t2,t3;

        // Creamos los threads
        t1 = new TestThread ( canvas.getGraphics(),100);
        t2 = new TestThread ( canvas.getGraphics(),200);
        t3 = new TestThread ( canvas.getGraphics(),300);

        // Arrancamos los threads
        t1.start();
        t2.start();
        t3.start();
        */

        int numHilos = 50;

        TestThread hilo;

        for (int i=0; i<numHilos; i++){
            hilo = new TestThread ( canvas.getGraphics(),
                    (int) (Math.random() * Ventana.getHeight() - 200) );
            hilo.start();
        }

        TestRunnable hiloRunnable = new TestRunnable(canvas.getGraphics(), 400);
        //hiloRunnable.iniciar();
        Thread hilocualquiera = new Thread(hiloRunnable);
        hilocualquiera.start();

        TestThread hiloHeredado = new TestThread(canvas.getGraphics(), 450);
        Thread nuevoHilo = new Thread(hiloHeredado);
        nuevoHilo.start();

        Thread hiloAnonimo = new Thread(new Runnable(){
            @Override
            public void run(){
                SimulaCarrera(canvas.getGraphics(), 350);
            }

            public void SimulaCarrera(Graphics contGraf, int coordY) {
                int inicio = 100;
                int coordX = 105;

                contGraf.setColor(Color.red);

                for (int i = 0; i < 50; i++) {
                    contGraf.fillRect(inicio, coordY, coordX - inicio, 20);
                    coordX += 5;

                    // Retrasamos la ejecución el tiempo especificado
                    try {
                        //hilo.sleep( 200 );
                        Thread.currentThread().sleep(200);
                    } catch (InterruptedException e) {
                        System.out.println(e.getStackTrace());
                    }

                    // Ahora imprimimos
                    System.out.println("Linea = " + coordY);
                }
            }
        }
        );

        hiloAnonimo.start();
    }
}










