import java.util.Date;

public class atividade1 implements Runnable{

    @Override
    public void run() {
        System.out.printf(Thread.currentThread() .getName(), new Date());
    }







    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName());
        
    for (int i = 0; i<10; i++){
        atividade1 a = new atividade1();
        Thread t = new Thread(a);
        t.start();
        

    }







    }
}