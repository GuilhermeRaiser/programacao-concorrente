public class atividade2 extends Thread{

    public atividade2(String name){
        super(name);
    }

    @Override
    public void run(){
        for (int i = 1; i <= 10; i++){
            System.out.printf("%s = %d /n ", getName(), i);
        }
    }
    public static void main(String[] args){
        for (int j = 1; j <= 10; j++){
            atividade2 c = new atividade2("Sou a Thread "+j);
            c.start();
        }
    }
}













    
