package SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.coltrollers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ClienteControllerTest {


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
    public void testDadoClientDeveCriarClientesListarTodosBusacarPorIdePorCPF() {

        String jsonCliente2 = """
                {
                    "name": "Carlos Silva",
                    "telefone": "11988888888",
                    "cpf": "876.286.670-23", 
                    "password": "senhaSecreta2"
                }
                """;


        given()
                .header("Authorization","Bearer "+ token)
                .contentType(ContentType.JSON)
                .body(jsonCliente2)
                .when()
                .post("/api/cliente")
                .then()
                .log().all()
                .statusCode(201);


        given()
                .header("Authorization","Bearer "+ token)
                .when()
                .get("/api/cliente")
                .then()
                .log().all()
                .statusCode(200);

        given()
                .header("Authorization","Bearer "+ token)
                .when()
                .get("/api/cliente/{id}","1")
                .then()
                .log().all()
                .statusCode(200);

        given()
                .header("Authorization","Bearer "+ token)
                .when()
                .get("/api/cliente/cpf/{cpf}","87628667023")
                .then()
                .log().all()
                .statusCode(200);

    }

    @Test
    public void testDadoClienteDeveAlterarDadosDeCliente(){

        String jsonCliente2 = """
                {
                    "name": "Carlos Silva",
                    "telefone": "11988888888",
                    "cpf": "876.286.670-23", 
                    "password": "senhaSecreta2"
                }
                """;

        String jsonClienteAlterado = """
                {
                    "name": "Bruno Silva",
                    "telefone": "999999999",
                    "cpf": "261.295.130-03",
                    "password": "senhaSecreta2"
                    
                }
                """;


        given()
                .header("Authorization","Bearer "+ token)
                .contentType(ContentType.JSON)
                .body(jsonCliente2)
                .when()
                .post("/api/cliente")
                .then()
                .log().all()
                .statusCode(201);




        given()
                .header("Authorization","Bearer "+ token)
                .contentType(ContentType.JSON)
                .body(jsonClienteAlterado)
                .when()
                .put("/api/cliente/{id}","2")
                .then()
                .log().all()
                .statusCode(200);


    }

    @Test
    public void testDadoClienteDeveReceberMensagemDeErroAoAtualizarComDadosErrados(){

        String jsonCliente2 = """
                {
                    "name": "Carlos Silva",
                    "telefone": "11988888888",
                    "cpf": "876.286.670-23", 
                    "password": "senhaSecreta2"
                }
                """;

        String jsonClienteAlterado = """
                {
                    "name": "Bruno Silva",
                    "telefone": "999999999",
                    "cpf": "876.286.600-23" 
                }
                """;


        given()
                .header("Authorization","Bearer "+ token)
                .contentType(ContentType.JSON)
                .body(jsonCliente2)
                .when()
                .post("/api/cliente")
                .then()
                .log().all()
                .statusCode(201);




        given()
                .header("Authorization","Bearer "+ token)
                .contentType(ContentType.JSON)
                .body(jsonClienteAlterado)
                .when()
                .put("/api/cliente/{id}","2")
                .then()
                .log().all()
                .statusCode(400);


    }


    @Test
    public void testDadoClienteDeveAlterarDadosDeClienteComIdInexistente() {

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/cliente/999")
                .then()
                .log().all()
                .statusCode(500);

    }

    @Test
    public void testDadoClienteDeveAlterarDadosDeClienteComCPFInexistente() {

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/cliente/cpf/00000000000")
                .then()
                .log().all()
                .statusCode(500);

    }

}
