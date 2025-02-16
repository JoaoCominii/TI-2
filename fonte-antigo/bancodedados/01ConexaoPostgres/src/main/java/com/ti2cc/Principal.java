package com.ti2cc;

import java.util.Scanner;

public class Principal {
    
    public static void main(String[] args) {
        DAO dao = new DAO();
        dao.conectar();

        Scanner input = new Scanner(System.in);
        int opcao = 0;

        while(opcao != 5) {
            System.out.println("=== Menu ===");
            System.out.println("1 - Listar");
            System.out.println("2 - Inserir");
            System.out.println("3 - Excluir");
            System.out.println("4 - Atualizar");
            System.out.println("5 - Sair");
            System.out.print("Opção: ");
            opcao = input.nextInt();

            switch(opcao) {
                case 1: // Listar
                    Carro[] carros = dao.getCarros();
                    for(Carro c : carros) {
                        System.out.println(c.toString());
                    }
                    break;
                case 2: // Inserir
                    System.out.print("Marca: ");
                    String marca = input.next();
                    System.out.print("Modelo: ");
                    String modelo = input.next();
                    System.out.print("Ano: ");
                    int ano = input.nextInt();
                    Carro carro = new Carro(-1, marca, modelo, ano);
                    dao.inserirCarro(carro);
                    break;
                case 3: // Excluir
                    System.out.print("ID do carro a ser excluído: ");
                    int id = input.nextInt();
                    dao.excluirCarro(id);
                    break;
                case 4: // Atualizar
                    System.out.print("ID do carro a ser atualizado: ");
                    id = input.nextInt();
                    System.out.print("Nova marca: ");
                    marca = input.next();
                    System.out.print("Novo modelo: ");
                    modelo = input.next();
                    System.out.print("Novo ano: ");
                    ano = input.nextInt();
                    carro = new Carro(id, marca, modelo, ano);
                    dao.atualizarCarro(carro);
                    break;
                case 5: // Sair
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }

        dao.close();
        input.close();
    }
}