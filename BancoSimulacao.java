import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class BancoSimulacao {

    private static final int TEMPO_MIN_CHEGADA = 5;
    private static final int TEMPO_MAX_CHEGADA = 50;
    private static final int TEMPO_MIN_ATENDIMENTO = 30;
    private static final int TEMPO_MAX_ATENDIMENTO = 120;
    private static final int TEMPO_SIMULACAO = 720; //alterar o tempo de acordo com o teste que vamos realizar.
    private static final int TEMPO_LIMITE_ESPERA = 120; 

    public static void main(String[] args) throws InterruptedException {

        for (int numCaixas = 1; numCaixas <= 100; numCaixas++) {
            System.out.println("Iniciando simulação com " + numCaixas + " caixas...");
            List<Cliente> clientes = gerarClientes();
            System.out.println("Clientes gerados: " + clientes.size());
            List<Cliente> atendidos = simularAtendimento(clientes, numCaixas);

            Metricas.imprimir(atendidos, numCaixas, TEMPO_LIMITE_ESPERA);

            double mediaEspera = atendidos.stream().mapToLong(Cliente::TempoEspera).average().orElse(0);
            if (mediaEspera <= TEMPO_LIMITE_ESPERA) break; 
        }
    }

    private static List<Cliente> gerarClientes() {
        Random rand = new Random();
        List<Cliente> clientes = new ArrayList<>();
        int tempo = 0;
        int id = 0;

        while (tempo < TEMPO_SIMULACAO) {
            int atendimento = TEMPO_MIN_ATENDIMENTO + rand.nextInt(TEMPO_MAX_ATENDIMENTO - TEMPO_MIN_ATENDIMENTO + 1);
            clientes.add(new Cliente(id++, tempo, atendimento));
            tempo += TEMPO_MIN_CHEGADA + rand.nextInt(TEMPO_MAX_CHEGADA - TEMPO_MIN_CHEGADA + 1);
        }

        return clientes;
    }

    private static List<Cliente> simularAtendimento(List<Cliente> clientes, int numCaixas) throws InterruptedException {
        BlockingQueue<Cliente> fila = new LinkedBlockingQueue<>();
        List<Cliente> atendidos = Collections.synchronizedList(new ArrayList<>());
        AtomicInteger relogio = new AtomicInteger(0);

        Thread chegada = new Thread(() -> {
            for (Cliente c : clientes) {
                while (relogio.get() < c.tempoChegada) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
                fila.offer(c);
            }
        });

        chegada.start();

        ExecutorService executor = Executors.newFixedThreadPool(numCaixas);
        for (int i = 0; i < numCaixas; i++) {
            executor.submit(new Caixa(fila, atendidos, relogio, chegada));
        }

        chegada.join();
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        return atendidos;
    }
}
