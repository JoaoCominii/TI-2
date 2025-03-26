package app;

import static spark.Spark.*;
import service.CarroService;

public class Aplicacao {
    
    private static CarroService carroService = new CarroService();
    
    public static void main(String[] args) {
        port(8080);
        
        staticFiles.location("/public");
        
        // Inserir um novo carro
        post("/carros/insert", (request, response) -> carroService.insert(request, response));

        // Obter um carro por ID
        get("/carros/:id", (request, response) -> carroService.get(request, response));
        
        // Listar todos os carros, ordenados por uma coluna específica
        get("/carros/list/:orderby", (request, response) -> carroService.getAll(request, response));

        // Buscar dados de um carro para atualização
        get("/carros/update/:id", (request, response) -> carroService.getToUpdate(request, response));
        
        // Atualizar um carro
        post("/carros/update/:id", (request, response) -> carroService.update(request, response));
           
        // Excluir um carro por ID
        get("/carros/delete/:id", (request, response) -> carroService.delete(request, response));
    }
}
