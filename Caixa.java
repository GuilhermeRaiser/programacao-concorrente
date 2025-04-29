import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Caixa implements Runnable  {

    private final BlockingQueue<Cliente> fila;
    private final List<Cliente> atendidos;
    private final AtomicInteger relogio;
    private final Thread chegada;

    public Caixa(BlockingQueue<Cliente> fila, List<Cliente> atendidos, AtomicInteger relogio, Thread chegada) {
        this.fila = fila;
        this.atendidos = atendidos;
        this.relogio = relogio;
        this.chegada = chegada;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Cliente cliente = fila.poll(100, TimeUnit.MILLISECONDS);
                if (cliente != null) {
                    cliente.tempoInicioAtendimento = Math.max(cliente.tempoChegada, relogio.get());
                    Thread.sleep(cliente.tempoAtendimento * 10); // alterar de acordo com o teste
                    cliente.tempoFimAtendimento = cliente.tempoInicioAtendimento + cliente.tempoAtendimento;
                    atendidos.add(cliente);
                    relogio.set((int) cliente.tempoFimAtendimento);
                } else if (!chegada.isAlive() && fila.isEmpty()) {
                    break;
                }
            }
        } catch (InterruptedException ignored) {}
    }
}

