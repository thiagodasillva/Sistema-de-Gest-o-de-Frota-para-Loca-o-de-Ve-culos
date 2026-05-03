package SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.coltrollers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AuthControllerTest {


    @LocalServerPort
    private int port;

    public static String token;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    public void testDadoOUsuarioDeveCriarUmaContaLogar() {

        //dados do cliente
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
    public void testDadoOUsuarioDeveCriarUmaContaTentarLogarComSenhaIncorreta() {

        //dados do cliente
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
                    "password": "senhaErrada"
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
                .statusCode(500).toString();
    }

    @Test
    public void testDadoOUsuarioDeveCriarUmaContaTentarLogarComCPFIncorreta() {

        //dados do cliente
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
                    "cpf": "52998224742",
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
                .statusCode(500).toString();


    }



    @Test
    public void testLoginSemAutenticacao() {
        String jsonLogin = """
                {
                    "cpf": "52998224725",
                    "password": "senha123"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(jsonLogin)
                .when()
                .post("/api/auth/login")
                .then()
                .log().all()
                .statusCode(500);
    }








}
