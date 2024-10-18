package com.tiagobarboza.tabela_fipe.apllication;

import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;

import com.tiagobarboza.tabela_fipe.model.DadosVeiculo;
import com.tiagobarboza.tabela_fipe.model.Modelos;
import com.tiagobarboza.tabela_fipe.model.Veiculo;
import com.tiagobarboza.tabela_fipe.services.ConsumoApi;
import com.tiagobarboza.tabela_fipe.services.ConverteDados;

public class Principal {
    final String BASE_URL = "https://parallelum.com.br/fipe/api/v1/";
    ConsumoApi consumoApi = new ConsumoApi();
    ConverteDados converteDados = new ConverteDados();

    public void showMenu(){
        Scanner sc = new Scanner(System.in);
        int op =0;
        String escolhaVeiculo = "";

            System.out.println("1) Caminhao ");
            System.out.println("2) Carro ");
            System.out.println("3) Moto ");
            System.out.print("Escolha um tipo de veiculo: ");
            op = sc.nextInt();
            switch (op){
                case 1:
                    escolhaVeiculo = "caminhoes";
                    break;
                case 2:
                    escolhaVeiculo = "carros";
                    break;
                case 3:
                    escolhaVeiculo = "motos";
                    break;
            }
            var json = consumoApi.obterDados(BASE_URL+escolhaVeiculo+"/marcas/");
            
            var marcas = converteDados.obterList(json, DadosVeiculo.class);

            marcas.forEach(marca -> {
                System.out.println("COD: "+marca.codigo()+" - "+marca.nome());
            });
            System.out.println("Informe o COD do modelo: ");
            int codMarca = sc.nextInt();

            json = consumoApi.obterDados(BASE_URL+escolhaVeiculo+"/marcas/"+codMarca+"/modelos");
            
            var modelos = converteDados.obterDados(json, Modelos.class);
            sc.nextLine();
            System.out.print("Digite o modelo do carro (Ex: Palio): ");
            String buscaCarro = sc.nextLine();

            List<DadosVeiculo> modelosFiltrado = modelos.modelos().stream()
                            .filter(m -> m.nome().toLowerCase().contains(buscaCarro.toLowerCase()))
                            .collect(Collectors.toList());

            System.out.println("\n Modelos filtrados: ");
            modelosFiltrado.forEach(m -> {
                System.out.println("COD: "+m.codigo()+" - "+m.nome());;
            });

            System.out.println("Digite o COD do modelo: ");
            var codModelo = sc.nextInt();

            json = consumoApi.obterDados(BASE_URL+escolhaVeiculo+"/marcas/"+codMarca+"/modelos/"+codModelo+"/anos");
            List<DadosVeiculo> anos = converteDados.obterList(json, DadosVeiculo.class);
            List<Veiculo> veiculos = new ArrayList<>();

            for (int i = 0; i < anos.size(); i++) {
                json = consumoApi.obterDados(BASE_URL+escolhaVeiculo+"/marcas/"+codMarca+"/modelos/"+codModelo+"/anos/"+anos.get(i).codigo());
                Veiculo veiculo = converteDados.obterDados(json, Veiculo.class);
                veiculos.add(veiculo);
            }

            System.out.println("Todos os veiculos filtrado: ");
            veiculos.forEach(veiculo ->{
                System.out.println("\nMarca      : "+veiculo.marca());
                System.out.println("Modelo     : "+veiculo.modelo());
                System.out.println("Ano        : "+veiculo.anoModelo());
                System.out.println("Combustivel: "+veiculo.combustivel());
                System.out.println("Valor      : "+veiculo.valor());
                System.out.println("\n-------------------------");
            });


            
            



    }

}
