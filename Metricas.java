import java.util.List;

public class Metricas {

    public static void imprimir(List<Cliente> clientes, int numCaixas, int tempoLimite) {
        long maxEspera = 0;
        long maxAtendimento = 0;
        long totalEspera = 0;
        long totalTempoBanco = 0;

        for (Cliente c : clientes) {
            maxEspera = Math.max(maxEspera, c.TempoEspera());
            maxAtendimento = Math.max(maxAtendimento, c.tempoAtendimento);
            totalEspera += c.TempoEspera();
            totalTempoBanco += c.TempoTotalBanco();
        }

        double mediaEspera = totalEspera / (double) clientes.size();
        double mediaTotalBanco = totalTempoBanco / (double) clientes.size();

        System.out.printf("Simulação com %d caixa(s):%n", numCaixas);
        System.out.println("Clientes atendidos: " + clientes.size());
        System.out.println("Tempo máximo de espera: " + maxEspera + "s");
        System.out.println("Tempo máximo de atendimento: " + maxAtendimento + "s");
        System.out.printf("Tempo médio total no banco: %.2fs%n", mediaTotalBanco);
        System.out.printf("Tempo médio de espera: %.2fs%n", mediaEspera);
        System.out.println("Meta atingida (< " + tempoLimite + "s)? " + (mediaEspera <= tempoLimite));
        System.out.println("--------------------------------------------------");
    }
}


