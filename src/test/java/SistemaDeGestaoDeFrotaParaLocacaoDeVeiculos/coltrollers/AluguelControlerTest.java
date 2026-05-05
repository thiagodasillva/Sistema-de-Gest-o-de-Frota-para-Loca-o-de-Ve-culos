package SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.coltrollers;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AluguelControlerTest {


    @LocalServerPort
    private int port;

    public static String token;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;

        String jsonCliente = """
                {
                    "name": "Carlos Silva",
                    "telefone": "11988888888",
                    "cpf": "52998224725", 
                    "password": "senhaSecreta"
                }
                """;

        // criandoCliente
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(jsonCliente)
                .when()
                .post("/api/cliente") // Caminho absoluto
                .then()
                .log().all()
                .statusCode(201);



        String jsonLogin = """
                {
                    "cpf": "52998224725",
                    "password": "senhaSecreta"
                }
                """;


        // fazendo login
        token = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(jsonLogin) // corpo em json para inserir
                .when()
                .post("/api/auth/login")
                .then()
                .log().all()
                .statusCode(200)
                .body("token", notNullValue())
                .extract().path("token");



        // usar o token para acessar a rota, usar em todos os testes de requisições
        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/cliente")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void testDadoUsuarioDeveCriarAluguel(){

        String jsonTipoVeiculo = """
                {
                    
                     "name": "SUV de Luxo",
                     "descricao": "Veículo de alto padrão com mais conforto",
                     "precoDiario": 400.00
                  
                }
                """;

        String jsonVeiculo = """
                {
                   
                    "placa": "RTT-2A24",
                    "modelo": "Onix",
                    "marca": "Chevrolet",
                    "ano": 2023,
                    "cor": "Prata",
                    "tipoVeiculo": 1,
                    "status": "DISPONIVEL"                  
                }
                """;


        String jsonAluguel = """
                {
                   
                    "clienteId": 1,
                    "veiculoId": 1,
                    "taxaFixa": 50.00,
                    "dataInicio": "01/01/2026"               
                }
                """;


        //criar TipotipoVeiculo
        given()
                .header("Authorization","Bearer " + token)
                .body(jsonTipoVeiculo)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/tv")
                .then()
                .log().all()
                .statusCode(201);


        //criar Veiculo
        given()
                .header("Authorization","Bearer " + token)
                .body(jsonVeiculo)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/veiculo")
                .then()
                .log().all()
                .statusCode(201);


        //criarAluguiel
        given()
                .header("Authorization","Bearer " + token)
                .body(jsonAluguel)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/aluguel")
                .then()
                .log().all()
                .statusCode(201);

    }

    @Test
    public void testDadoUsuarioDeveCriarAluguelComDadosIncompletos(){

        String jsonTipoVeiculo = """
                {
                    
                     "name": "SUV de Luxo",
                     "descricao": "Veículo de alto padrão com mais conforto",
                     "precoDiario": 400.00
                  
                }
                """;

        String jsonVeiculo = """
                {
                   
                    "placa": "RTT-2A24",
                    "modelo": "Onix",
                    "marca": "Chevrolet",
                    "ano": 2023,
                    "cor": "Prata",
                    "tipoVeiculo": 1,
                    "status": "DISPONIVEL"                  
                }
                """;


        String jsonAluguel = """
                {
                   
                    "clienteId": 1,
                    "veiculoId": 1,
                    "dataInicio": "01/01/2026"               
                }
                """;


        //criar TipotipoVeiculo
        given()
                .header("Authorization","Bearer " + token)
                .body(jsonTipoVeiculo)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/tv")
                .then()
                .log().all()
                .statusCode(201);


        //criar Veiculo
        given()
                .header("Authorization","Bearer " + token)
                .body(jsonVeiculo)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/veiculo")
                .then()
                .log().all()
                .statusCode(201);


        //criarAluguiel
        given()
                .header("Authorization","Bearer " + token)
                .body(jsonAluguel)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/aluguel")
                .then()
                .log().all()
                .statusCode(400);

    }

    @Test
    public void testDadoUsuarioDeveCriarAluguelComDadosDeIdUsuarioIncorreto(){

        String jsonTipoVeiculo = """
                {
                    
                     "name": "SUV de Luxo",
                     "descricao": "Veículo de alto padrão com mais conforto",
                     "precoDiario": 400.00
                  
                }
                """;

        String jsonVeiculo = """
                {
                   
                    "placa": "RTT-2A24",
                    "modelo": "Onix",
                    "marca": "Chevrolet",
                    "ano": 2023,
                    "cor": "Prata",
                    "tipoVeiculo": 1,
                    "status": "DISPONIVEL"                  
                }
                """;


        String jsonAluguel = """
                {
                   
                    "clienteId": 3,
                    "veiculoId": 1,
                    "taxaFixa": 50.00,
                    "dataInicio": "01/01/2026"               
                }
                """;


        //criar TipotipoVeiculo
        given()
                .header("Authorization","Bearer " + token)
                .body(jsonTipoVeiculo)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/tv")
                .then()
                .log().all()
                .statusCode(201);


        //criar Veiculo
        given()
                .header("Authorization","Bearer " + token)
                .body(jsonVeiculo)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/veiculo")
                .then()
                .log().all()
                .statusCode(201);


        //criarAluguiel
        given()
                .header("Authorization","Bearer " + token)
                .body(jsonAluguel)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/aluguel")
                .then()
                .log().all()
                .statusCode(404);

    }

    @Test
    public void testDadoUsuarioDeveCriarAluguelComDadosDeIdVeiculoIncorreto(){

        String jsonTipoVeiculo = """
                {
                    
                     "name": "SUV de Luxo",
                     "descricao": "Veículo de alto padrão com mais conforto",
                     "precoDiario": 400.00
                  
                }
                """;

        String jsonVeiculo = """
                {
                   
                    "placa": "RTT-2A24",
                    "modelo": "Onix",
                    "marca": "Chevrolet",
                    "ano": 2023,
                    "cor": "Prata",
                    "tipoVeiculo": 1,
                    "status": "DISPONIVEL"                  
                }
                """;


        String jsonAluguel = """
                {
                   
                    "clienteId": 1,
                    "veiculoId": 3,
                    "taxaFixa": 50.00,
                    "dataInicio": "01/01/2026"               
                }
                """;


        //criar TipotipoVeiculo
        given()
                .header("Authorization","Bearer " + token)
                .body(jsonTipoVeiculo)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/tv")
                .then()
                .log().all()
                .statusCode(201);


        //criar Veiculo
        given()
                .header("Authorization","Bearer " + token)
                .body(jsonVeiculo)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/veiculo")
                .then()
                .log().all()
                .statusCode(201);


        //criarAluguiel
        given()
                .header("Authorization","Bearer " + token)
                .body(jsonAluguel)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/aluguel")
                .then()
                .log().all()
                .statusCode(404);

    }

    @Test
    public void testDadoUsuarioDeveCriarAluguelComValroDeTaxaFixaIncorreta(){

        String jsonTipoVeiculo = """
                {
                    
                     "name": "SUV de Luxo",
                     "descricao": "Veículo de alto padrão com mais conforto",
                     "precoDiario": 400.00
                  
                }
                """;

        String jsonVeiculo = """
                {
                   
                    "placa": "RTT-2A24",
                    "modelo": "Onix",
                    "marca": "Chevrolet",
                    "ano": 2023,
                    "cor": "Prata",
                    "tipoVeiculo": 1,
                    "status": "DISPONIVEL"                  
                }
                """;


        String jsonAluguel = """
                {
                   
                    "clienteId": 1,
                    "veiculoId": 1,
                    "taxaFixa": -1.00,
                    "dataInicio": "01/01/2026"               
                }
                """;


        //criar TipotipoVeiculo
        given()
                .header("Authorization","Bearer " + token)
                .body(jsonTipoVeiculo)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/tv")
                .then()
                .log().all()
                .statusCode(201);


        //criar Veiculo
        given()
                .header("Authorization","Bearer " + token)
                .body(jsonVeiculo)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/veiculo")
                .then()
                .log().all()
                .statusCode(201);


        //criarAluguiel
        given()
                .header("Authorization","Bearer " + token)
                .body(jsonAluguel)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/aluguel")
                .then()
                .log().all()
                .statusCode(400);

    }

    @Test
    public void testDadoUsuarioDeveCriarAluguelComVeiculoAlugado(){

        String jsonTipoVeiculo = """
                {
                    
                     "name": "SUV de Luxo",
                     "descricao": "Veículo de alto padrão com mais conforto",
                     "precoDiario": 400.00
                  
                }
                """;

        String jsonVeiculo = """
                {
                   
                    "placa": "RTT-2A24",
                    "modelo": "Onix",
                    "marca": "Chevrolet",
                    "ano": 2023,
                    "cor": "Prata",
                    "tipoVeiculo": 1,
                    "status": "ALUGADO"                  
                }
                """;


        String jsonAluguel = """
                {
                   
                    "clienteId": 1,
                    "veiculoId": 1,
                    "taxaFixa": 50.00,
                    "dataInicio": "01/01/2026"               
                }
                """;


        //criar TipotipoVeiculo
        given()
                .header("Authorization","Bearer " + token)
                .body(jsonTipoVeiculo)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/tv")
                .then()
                .log().all()
                .statusCode(201);


        //criar Veiculo
        given()
                .header("Authorization","Bearer " + token)
                .body(jsonVeiculo)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/veiculo")
                .then()
                .log().all()
                .statusCode(201);


        //criarAluguiel
        given()
                .header("Authorization","Bearer " + token)
                .body(jsonAluguel)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/aluguel")
                .then()
                .log().all()
                .statusCode(400);

    }


    @Test
    public void testDadoUsuarioDeveListaAluguelAtivos(){

        String jsonTipoVeiculo = """
                {
                    
                     "name": "SUV de Luxo",
                     "descricao": "Veículo de alto padrão com mais conforto",
                     "precoDiario": 400.00
                  
                }
                """;

        String jsonVeiculo = """
                {
                   
                    "placa": "RTT-2A24",
                    "modelo": "Onix",
                    "marca": "Chevrolet",
                    "ano": 2023,
                    "cor": "Prata",
                    "tipoVeiculo": 1,
                    "status": "DISPONIVEL"                  
                }
                """;


        String jsonAluguel = """
                {
                   
                    "clienteId": 1,
                    "veiculoId": 1,
                    "taxaFixa": 50.00,
                    "dataInicio": "01/01/2026"               
                }
                """;


        //criar TipotipoVeiculo
        given()
                .header("Authorization","Bearer " + token)
                .body(jsonTipoVeiculo)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/tv")
                .then()
                .log().all()
                .statusCode(201);


        //criar Veiculo
        given()
                .header("Authorization","Bearer " + token)
                .body(jsonVeiculo)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/veiculo")
                .then()
                .log().all()
                .statusCode(201);


        //criarAluguiel
        given()
                .header("Authorization","Bearer " + token)
                .body(jsonAluguel)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/aluguel")
                .then()
                .log().all()
                .statusCode(201);


        //ListarAluguielAtivo
        given()
                .header("Authorization","Bearer " + token)
                .when()
                .get("/api/aluguel/ativo")
                .then()
                .log().all()
                .statusCode(200);

    }

    @Test
    public void testDadoOUsuarioLogadoEListaAlugueis(){

        String jsonTipoVeiculo = """
                {
                     "name": "SUV de Luxo",
                     "descricao": "Veículo de alto padrão com mais conforto",
                     "precoDiario": 400.00                  
                }
                """;

        String jsonVeiculo = """
                {                   
                    "placa": "RTT-2A24",
                    "modelo": "Onix",
                    "marca": "Chevrolet",
                    "ano": 2023,
                    "cor": "Prata",
                    "tipoVeiculo": 1,
                    "status": "DISPONIVEL"                  
                }
                """;

        String jsonVeiculo2 = """
                {                  
                    "placa": "RTT-2A22",
                    "modelo": "Onix",
                    "marca": "Chevrolet",
                    "ano": 2023,
                    "cor": "Prata",
                    "tipoVeiculo": 1,
                    "status": "DISPONIVEL"                  
                }
                """;


        String jsonAluguel = """
                {
                   
                    "clienteId": 1,
                    "veiculoId": 1,
                    "taxaFixa": 50.00,
                    "dataInicio": "01/01/2026"               
                }
                """;

        String jsonAluguel2 = """
                {
                   
                    "clienteId": 1,
                    "veiculoId": 2,
                    "taxaFixa": 50.00,
                    "dataInicio": "01/01/2025"               
                }
                """;


        //criar TipotipoVeiculo
        given()
                .header("Authorization","Bearer " + token)
                .body(jsonTipoVeiculo)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/tv")
                .then()
                .log().all()
                .statusCode(201);


        //criar Veiculo
        given()
                .header("Authorization","Bearer " + token)
                .body(jsonVeiculo)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/veiculo")
                .then()
                .log().all()
                .statusCode(201);

        //criar Veiculo2
        given()
                .header("Authorization","Bearer " + token)
                .body(jsonVeiculo2)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/veiculo")
                .then()
                .log().all()
                .statusCode(201);


        //criarAluguiel
        given()
                .header("Authorization","Bearer " + token)
                .body(jsonAluguel)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/aluguel")
                .then()
                .log().all()
                .statusCode(201);

        //criarAluguiel2
        given()
                .header("Authorization","Bearer " + token)
                .body(jsonAluguel2)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/aluguel")
                .then()
                .log().all()
                .statusCode(201);


        //ListarAluguielAtivo
        given()
                .header("Authorization","Bearer " + token)
                .when()
                .get("/api/aluguel")
                .then()
                .log().all()
                .statusCode(200);

    }


    @Test
    public void testDadoOUsuarioListaAlugueisInezistentes(){

        //ListarAluguielInexistente
        given()
                .header("Authorization","Bearer " + token)
                .when()
                .get("/api/aluguel")
                .then()
                .log().all()
                .statusCode(200)
                .body("size()", equalTo(0));

    }


    @Test
    public void testDadoOUsuarBuscaAluguelPorId(){

        String jsonTipoVeiculo = """
                {
                     "name": "SUV de Luxo",
                     "descricao": "Veículo de alto padrão com mais conforto",
                     "precoDiario": 400.00                  
                }
                """;

        String jsonVeiculo = """
                {                   
                    "placa": "RTT-2A24",
                    "modelo": "Onix",
                    "marca": "Chevrolet",
                    "ano": 2023,
                    "cor": "Prata",
                    "tipoVeiculo": 1,
                    "status": "DISPONIVEL"                  
                }
                """;

        String jsonVeiculo2 = """
                {                  
                    "placa": "RTT-2A22",
                    "modelo": "Onix",
                    "marca": "Chevrolet",
                    "ano": 2023,
                    "cor": "Prata",
                    "tipoVeiculo": 1,
                    "status": "DISPONIVEL"                  
                }
                """;


        String jsonAluguel = """
                {
                   
                    "clienteId": 1,
                    "veiculoId": 1,
                    "taxaFixa": 50.00,
                    "dataInicio": "01/01/2026"               
                }
                """;

        String jsonAluguel2 = """
                {
                   
                    "clienteId": 1,
                    "veiculoId": 2,
                    "taxaFixa": 50.00,
                    "dataInicio": "01/01/2025"               
                }
                """;


        //criar TipotipoVeiculo
        given()
                .header("Authorization","Bearer " + token)
                .body(jsonTipoVeiculo)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/tv")
                .then()
                .log().all()
                .statusCode(201);


        //criar Veiculo
        given()
                .header("Authorization","Bearer " + token)
                .body(jsonVeiculo)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/veiculo")
                .then()
                .log().all()
                .statusCode(201);



        //criarAluguiel
        given()
                .header("Authorization","Bearer " + token)
                .body(jsonAluguel)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/aluguel")
                .then()
                .log().all()
                .statusCode(201);

        //BuscarAluguelPorId
        given()
                .header("Authorization","Bearer " + token)
                .when()
                .get("/api/aluguel/1")
                .then()
                .log().all()
                .statusCode(200);

    }

    @Test
    public void testDadoOUsuarBuscaAluguelPorIdInexistente(){

        //BuscarAluguelPorId
        given()
                .header("Authorization","Bearer " + token)
                .when()
                .get("/api/aluguel/1")
                .then()
                .log().all()
                .statusCode(404);

    }


    @Test
    public void testDadoOUsuarioLogadoCadastrarUmAlugielFinalizarECalcularValor(){

        String jsonTipoVeiculo = """
                {
                    
                     "name": "SUV de Luxo",
                     "descricao": "Veículo de alto padrão com mais conforto",
                     "precoDiario": 400.00
                  
                }
                """;

        String jsonVeiculo = """
                {
                   
                    "placa": "RTT-2A24",
                    "modelo": "Onix",
                    "marca": "Chevrolet",
                    "ano": 2023,
                    "cor": "Prata",
                    "tipoVeiculo": 1,
                    "status": "DISPONIVEL"                  
                }
                """;


        String jsonAluguel = """
                {
                   
                    "clienteId": 1,
                    "veiculoId": 1,
                    "taxaFixa": 50.00,
                    "dataInicio": "01/01/2026"               
                }
                """;


        //criar TipotipoVeiculo
        given()
                .header("Authorization","Bearer " + token)
                .body(jsonTipoVeiculo)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/tv")
                .then()
                .log().all()
                .statusCode(201);


        //criar Veiculo
        given()
                .header("Authorization","Bearer " + token)
                .body(jsonVeiculo)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/veiculo")
                .then()
                .log().all()
                .statusCode(201);


        //criarAluguiel
        given()
                .header("Authorization","Bearer " + token)
                .body(jsonAluguel)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/aluguel")
                .then()
                .log().all()
                .statusCode(201);


        //FinalizarAluguel
        given()
                .header("Authorization","Bearer " + token)
                .when()
                .post("/api/aluguel/1/finalizar")
                .then()
                .log().all()
                .statusCode(200);



        //calcularValorALuguelFinalizado
        given()
                .header("Authorization","Bearer " + token)
                .when()
                .get("/api/aluguel/1/valor")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void testDadoClienteDeveBuscarAlugueisPorPeriodo(){
        String jsonTipoVeiculo = """
                {
                    
                     "name": "SUV de Luxo",
                     "descricao": "Veículo de alto padrão com mais conforto",
                     "precoDiario": 400.00
                  
                }
                """;

        String jsonVeiculo = """
                {
                   
                    "placa": "RTT-2A24",
                    "modelo": "Onix",
                    "marca": "Chevrolet",
                    "ano": 2023,
                    "cor": "Prata",
                    "tipoVeiculo": 1,
                    "status": "DISPONIVEL"                  
                }
                """;


        String jsonAluguel = """
                {
                   
                    "clienteId": 1,
                    "veiculoId": 1,
                    "taxaFixa": 50.00,
                    "dataInicio": "01/01/2026"               
                }
                """;


        //criar TipotipoVeiculo
        given()
                .header("Authorization","Bearer " + token)
                .body(jsonTipoVeiculo)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/tv")
                .then()
                .log().all()
                .statusCode(201);


        //criar Veiculo
        given()
                .header("Authorization","Bearer " + token)
                .body(jsonVeiculo)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/veiculo")
                .then()
                .log().all()
                .statusCode(201);


        //criarAluguiel
        given()
                .header("Authorization","Bearer " + token)
                .body(jsonAluguel)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/aluguel")
                .then()
                .log().all()
                .statusCode(201);

        given()
                .header("Authorization", "Bearer " + token)
                .param("inicio", "2026-01-01T00:00:00")
                .param("fim", "2026-12-31T23:59:59")
                .when()
                .get("/api/aluguel/periodo")
                .then()
                .log().all()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    public void testDadoClienteDeveBuscarAlugueisPorPeriodoSemAlugueisCadastrados(){
        given()
                .header("Authorization", "Bearer " + token)
                .param("inicio", "2026-01-01T00:00:00")
                .param("fim", "2026-12-31T23:59:59")
                .when()
                .get("/api/aluguel/periodo")
                .then()
                .log().all()
                .statusCode(204);
    }

    @Test
    public void testDadoClienteDeveBuscarAlugueisPorPeriodoComDatasIncondizentes(){

        given()
                .header("Authorization", "Bearer " + token)
                .param("inicio", "2026-12-01T00:00:00")
                .param("fim", "2026-01-31T23:59:59")
                .when()
                .get("/api/aluguel/periodo")
                .then()
                .log().all()
                .statusCode(409);
    }

    @Test
    public void testDadoUsuarioDeveAtualizarValoresDeUmAluguel() {
        String jsonTipoVeiculo = """
                {
                    
                     "name": "SUV de Luxo",
                     "descricao": "Veículo de alto padrão com mais conforto",
                     "precoDiario": 400.00
                  
                }
                """;

        String jsonVeiculo = """
                {
                   
                    "placa": "RTT-2A24",
                    "modelo": "Onix",
                    "marca": "Chevrolet",
                    "ano": 2023,
                    "cor": "Prata",
                    "tipoVeiculo": 1,
                    "status": "DISPONIVEL"                  
                }
                """;


        String jsonAluguel = """
                {
                   
                    "clienteId": 1,
                    "veiculoId": 1,
                    "taxaFixa": 50.00,
                    "dataInicio": "01/01/2026"               
                }
                """;


        //criar TipotipoVeiculo
        given()
                .header("Authorization", "Bearer " + token)
                .body(jsonTipoVeiculo)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/tv")
                .then()
                .log().all()
                .statusCode(201);


        //criar Veiculo
        given()
                .header("Authorization", "Bearer " + token)
                .body(jsonVeiculo)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/veiculo")
                .then()
                .log().all()
                .statusCode(201);


        //criarAluguiel
        given()
                .header("Authorization", "Bearer " + token)
                .body(jsonAluguel)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/aluguel")
                .then()
                .log().all()
                .statusCode(201);


        String jsonAtualizacao = """
                {
                    "clienteId": 1,
                    "veiculoId": 1,
                    "taxaFixa": 75.00,
                    "dataInicio": "01/02/2026"
                }
                """;

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(jsonAtualizacao)
                .when()
                .put("/api/aluguel/1")
                .then()
                .log().all()
                .statusCode(200);

    }

    @Test
    public void testDadoUsuarioDeveAtualizarValoresDeUmAluguelComIdVeiculoInvalido() {
        String jsonTipoVeiculo = """
                {
                    
                     "name": "SUV de Luxo",
                     "descricao": "Veículo de alto padrão com mais conforto",
                     "precoDiario": 400.00
                  
                }
                """;

        String jsonVeiculo = """
                {
                   
                    "placa": "RTT-2A24",
                    "modelo": "Onix",
                    "marca": "Chevrolet",
                    "ano": 2023,
                    "cor": "Prata",
                    "tipoVeiculo": 1,
                    "status": "DISPONIVEL"                  
                }
                """;


        String jsonAluguel = """
                {
                   
                    "clienteId": 1,
                    "veiculoId": 1,
                    "taxaFixa": 50.00,
                    "dataInicio": "01/01/2026"               
                }
                """;


        //criar TipotipoVeiculo
        given()
                .header("Authorization", "Bearer " + token)
                .body(jsonTipoVeiculo)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/tv")
                .then()
                .log().all()
                .statusCode(201);


        //criar Veiculo
        given()
                .header("Authorization", "Bearer " + token)
                .body(jsonVeiculo)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/veiculo")
                .then()
                .log().all()
                .statusCode(201);


        //criarAluguiel
        given()
                .header("Authorization", "Bearer " + token)
                .body(jsonAluguel)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/aluguel")
                .then()
                .log().all()
                .statusCode(201);


        String jsonAtualizacao = """
                {
                    "clienteId": 1,
                    "veiculoId": 3,
                    "taxaFixa": 75.00,
                    "dataInicio": "01/02/2026"
                }
                """;

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(jsonAtualizacao)
                .when()
                .put("/api/aluguel/1")
                .then()
                .log().all()
                .statusCode(404);

    }

    @Test
    public void testDadoUsuarioDeveAtualizarValoresDeUmAluguelComIdClienteInvalido() {
        String jsonTipoVeiculo = """
                {
                    
                     "name": "SUV de Luxo",
                     "descricao": "Veículo de alto padrão com mais conforto",
                     "precoDiario": 400.00
                  
                }
                """;

        String jsonVeiculo = """
                {
                   
                    "placa": "RTT-2A24",
                    "modelo": "Onix",
                    "marca": "Chevrolet",
                    "ano": 2023,
                    "cor": "Prata",
                    "tipoVeiculo": 1,
                    "status": "DISPONIVEL"                  
                }
                """;


        String jsonAluguel = """
                {
                   
                    "clienteId": 1,
                    "veiculoId": 1,
                    "taxaFixa": 50.00,
                    "dataInicio": "01/01/2026"               
                }
                """;


        //criar TipotipoVeiculo
        given()
                .header("Authorization", "Bearer " + token)
                .body(jsonTipoVeiculo)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/tv")
                .then()
                .log().all()
                .statusCode(201);


        //criar Veiculo
        given()
                .header("Authorization", "Bearer " + token)
                .body(jsonVeiculo)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/veiculo")
                .then()
                .log().all()
                .statusCode(201);


        //criarAluguiel
        given()
                .header("Authorization", "Bearer " + token)
                .body(jsonAluguel)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/aluguel")
                .then()
                .log().all()
                .statusCode(201);


        String jsonAtualizacao = """
                {
                    "clienteId": 3,
                    "veiculoId": 1,
                    "taxaFixa": 75.00,
                    "dataInicio": "01/02/2026"
                }
                """;

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(jsonAtualizacao)
                .when()
                .put("/api/aluguel/1")
                .then()
                .log().all()
                .statusCode(404);

    }

    @Test
    public void testDadoUsuarioDeveAtualizarValoresDeUmAluguelComTaxaFixaInvalida() {
        String jsonTipoVeiculo = """
                {
                    
                     "name": "SUV de Luxo",
                     "descricao": "Veículo de alto padrão com mais conforto",
                     "precoDiario": 400.00
                  
                }
                """;

        String jsonVeiculo = """
                {
                   
                    "placa": "RTT-2A24",
                    "modelo": "Onix",
                    "marca": "Chevrolet",
                    "ano": 2023,
                    "cor": "Prata",
                    "tipoVeiculo": 1,
                    "status": "DISPONIVEL"                  
                }
                """;


        String jsonAluguel = """
                {
                   
                    "clienteId": 1,
                    "veiculoId": 1,
                    "taxaFixa": 50.00,
                    "dataInicio": "01/01/2026"               
                }
                """;


        //criar TipotipoVeiculo
        given()
                .header("Authorization", "Bearer " + token)
                .body(jsonTipoVeiculo)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/tv")
                .then()
                .log().all()
                .statusCode(201);


        //criar Veiculo
        given()
                .header("Authorization", "Bearer " + token)
                .body(jsonVeiculo)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/veiculo")
                .then()
                .log().all()
                .statusCode(201);


        //criarAluguiel
        given()
                .header("Authorization", "Bearer " + token)
                .body(jsonAluguel)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/aluguel")
                .then()
                .log().all()
                .statusCode(201);


        String jsonAtualizacao = """
                {
                    "clienteId": 1,
                    "veiculoId": 1,
                    "taxaFixa": -1.00,
                    "dataInicio": "01/02/2026"
                }
                """;

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(jsonAtualizacao)
                .when()
                .put("/api/aluguel/1")
                .then()
                .log().all()
                .statusCode(400);

    }
    @Test
    public void testDadoCienteDeveDeletarAlugul(){
        String jsonTipoVeiculo = """
                {
                    
                     "name": "SUV de Luxo",
                     "descricao": "Veículo de alto padrão com mais conforto",
                     "precoDiario": 400.00
                  
                }
                """;

        String jsonVeiculo = """
                {
                   
                    "placa": "RTT-2A24",
                    "modelo": "Onix",
                    "marca": "Chevrolet",
                    "ano": 2023,
                    "cor": "Prata",
                    "tipoVeiculo": 1,
                    "status": "DISPONIVEL"                  
                }
                """;


        String jsonAluguel = """
                {
                   
                    "clienteId": 1,
                    "veiculoId": 1,
                    "taxaFixa": 50.00,
                    "dataInicio": "01/01/2026"               
                }
                """;


        //criar TipotipoVeiculo
        given()
                .header("Authorization","Bearer " + token)
                .body(jsonTipoVeiculo)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/tv")
                .then()
                .log().all()
                .statusCode(201);


        //criar Veiculo
        given()
                .header("Authorization","Bearer " + token)
                .body(jsonVeiculo)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/veiculo")
                .then()
                .log().all()
                .statusCode(201);


        //criarAluguiel
        given()
                .header("Authorization","Bearer " + token)
                .body(jsonAluguel)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/aluguel")
                .then()
                .log().all()
                .statusCode(201);

        //deletar alugule
        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/api/aluguel/1")
                .then()
                .log().all()
                .statusCode(204);
    }




}
