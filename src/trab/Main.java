package trab;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        double tempoPrimeiroCliente = 0;
        double taxaChegadaMin = 0;
        double taxaChegadaMax = 0;
        double taxaSaidaMin = 0;
        double taxaSaidaMax = 0;
        int numeroServidores = 0;
        int capacidadeFila = 0;
        int quantAleatorios = 0;

        int numeroServidoresFila1 = 0;
        int capacidadeFila1 = 0;
        int numeroServidoresFila2 = 0;
        int capacidadeFila2 = 0;
        double taxaTrocaMin = 0;
        double taxaTrocaMax = 0;

        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o tipo de operação: 1 - Fila simples; 2 - Fila em Tandem");

        int op = scanner.nextInt();

        switch (op){
            case 1:
                System.out.println("Iniciando algoritmo fila simples");

                System.out.println("Quantos servidores tem a fila?");
                numeroServidores = 2;//scanner.nextInt();

                System.out.println("Qual a capacidade da fila (se infinito, digite 0)?");
                capacidadeFila = 5;//scanner.nextInt();

                System.out.println("Qual a taxa minima de chegada?");
                taxaChegadaMin = 2;//scanner.nextDouble();

                System.out.println("Qual a taxa maxima de chegada?");
                taxaChegadaMax = 4;//scanner.nextDouble();

                System.out.println("Qual a taxa minima de atendimento?");
                taxaSaidaMin = 3;//scanner.nextDouble();

                System.out.println("Qual a taxa maxima de atendimento?");
                taxaSaidaMax = 5;//scanner.nextDouble();

                System.out.println("Quanto tempo demora para chegar o primeiro cliente?");
                tempoPrimeiroCliente = 3;//scanner.nextDouble();

                System.out.println ("Quantos aleatórios serão executados?");
                quantAleatorios = 100000;//scanner.nextInt();

                FilaSimples filaSimples = new FilaSimples(numeroServidores, capacidadeFila, taxaChegadaMin, taxaChegadaMax, taxaSaidaMin, taxaSaidaMax, tempoPrimeiroCliente, quantAleatorios);

                filaSimples.iniciar();
                break;

            case 2:
                System.out.println("Iniciando algoritmo fila em tandem");

                System.out.println("Quantos servidores tem a fila 1?");
                numeroServidoresFila1 = scanner.nextInt();

                System.out.println("Qual a capacidade da fila 1 (se infinito, digite 0)?");
                capacidadeFila1 = scanner.nextInt();

                System.out.println("Qual a taxa minima de chegada na fila 1?");
                taxaChegadaMin = scanner.nextDouble();

                System.out.println("Qual a taxa maxima de chegada na fila 1?");
                taxaChegadaMax = scanner.nextDouble();

                System.out.println("Qual a taxa minima de saida da fila 1?");
                taxaTrocaMin = scanner.nextDouble();

                System.out.println("Qual a taxa maxima de saida da fila 1?");
                taxaTrocaMax = scanner.nextDouble();

                System.out.println("Quantos servidores tem a fila 2?");
                numeroServidoresFila2 = scanner.nextInt();

                System.out.println("Qual a capacidade da fila 2 (se infinito, digite 0)?");
                capacidadeFila2 = scanner.nextInt();

                System.out.println("Qual a taxa minima de saida da fila 2?");
                taxaSaidaMin = scanner.nextDouble();

                System.out.println("Qual a taxa maxima de saida da fila 2?");
                taxaSaidaMax = scanner.nextDouble();

                System.out.println("Quanto tempo demora para chegar o primeiro cliente?");
                tempoPrimeiroCliente = scanner.nextDouble();

                System.out.println ("Quantos aleatórios serão executados?");
                quantAleatorios = scanner.nextInt();
                FilaEmTandem filaEmTandem = new FilaEmTandem(numeroServidoresFila1, capacidadeFila1, taxaChegadaMin, taxaChegadaMax,
                        taxaTrocaMin, taxaTrocaMax, numeroServidoresFila2, capacidadeFila2, taxaSaidaMin, taxaSaidaMax, tempoPrimeiroCliente, quantAleatorios);
                filaEmTandem.executar();

                break;

            default:
                System.err.println("Operação não reconhecida");
        }

    }
}
