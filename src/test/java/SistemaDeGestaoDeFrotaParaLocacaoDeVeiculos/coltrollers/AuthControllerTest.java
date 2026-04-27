package SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.coltrollers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AuthControllerTest {


    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
        // Removi o basePath para usarmos o caminho completo nas chamadas abaixo
    }

    @Test
    public void deveFazerLoginEUsarTokenEmRotaProtegida() {

        //criando cliente
        String jsonCliente = """
                {
                    "name": "Carlos Silva",
                    "telefone": "11988888888",
                    "cpf": "52998224725", 
                    "password": "senhaSecreta"
                }
                """;
        // NOTA: Coloquei um CPF matematicamente válido ("52998224725") para passar no seu @CPF.

        System.out.println("--- TENTANDO CRIAR CLIENTE ---");
        given()
                .log().all() // <--- Vai imprimir a requisição no terminal
                .contentType(ContentType.JSON)
                .body(jsonCliente)
                .when()
                .post("/api/cliente") // Caminho absoluto
                .then()
                .log().all() // <--- Vai imprimir a resposta do erro 403 no terminal!
                .statusCode(201);

        String jsonLogin = """
                {
                    "cpf": "52998224725",
                    "password": "senhaSecreta"
                }
                """;
        // fazendo login
        String token = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(jsonLogin)
                .when()
                .post("/api/auth/login")
                .then()
                .log().all()
                .statusCode(200)
                .body("token", notNullValue())
                .extract().path("token");

        // usar o token para acessar a rota
        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/cliente")
                .then()
                .log().all()
                .statusCode(200);
    }


}
