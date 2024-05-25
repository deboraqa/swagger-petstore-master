package ip.swagger.petstore;

import io.restassured.http.ContentType;
import org.json.JSONObject;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.Is.is;

public class PetStoreTest {
        private String url = "https://petstore3.swagger.io/api/v3";

        @Test
        public void Cadastrar_novo_pedido_de_pet_com_sucesso_POST_Store_Order() {

                String pedido = "{\r\n    \"id\": 10,\r\n    \"petId\": 50,\r\n    \"quantity\": 7,\r\n    \"shipDate\": \"2024-02-27T17:50:28.803+00:00\",\r\n    \"status\": \"approved\",\r\n    \"complete\": true\r\n}";

                given()                                        // Dado que
                        .contentType(ContentType.JSON)          //Tipo de Conteúdo da requisição
                        .body(pedido)                           //Envio do body ( pedido do store)
                        .when()                                 // Quando
                        .post(url + "/store/order")             // Consultado o post url
                        .then()                               // Então
                        .log().all()                           // Mostrar tudo que foi recebido
                        .statusCode(200)                      // Valida que a operação teve status 200
                .body(
                        "id",is(10),                  // Validando que o conteudo do body da resposta possui id 10
                        "quantity",is(7)        // Validando que o body da resposta possui quantidade 7
                );
        }

        @Test
        public void Pesquisar_por_um_pet_inexistente_GET_PET_petid(){
            String petId = "-45805656";

            given()                                             // Dado que
                    .contentType(ContentType.JSON)                        // Tipo de conteúdo da requisição
                    .log().all()                                            // Mostrar tudo que foi enviado
                    .when()                                             // Quando
                    .get(url +"/pet/"+petId) // Consulta pelo petId
                    .then()                                             // Então
                    .log().all()                                            // Mostrar tudo que foi recebido
                    .statusCode(404);                                       // Validou que a operação retorno 404
                    }

        @Test
        public void Atualizar_dados_de_um_pet_existente_PUT_Pet (){

                String petPut = "{\r\n  \"id\": 50,\r\n  \"category\": {\r\n    \"id\": 50,\r\n    \"name\": \"dog\"\r\n  },\r\n  \"name\": \"Billy\",\r\n  \"photoUrls\": [\r\n    \"string\"\r\n  ],\r\n  \"tags\": [\r\n    {\r\n      \"id\": 0,\r\n      \"name\": \"cartoon\"\r\n    }\r\n  ],\r\n  \"status\": \"pending\"\r\n}";

                given()                                 // Dado que
                        .contentType(ContentType.JSON)    // Tipo de conteúdo da requisição
                        .log().all()                        // Gera um log completo da requisição
                        .body(petPut)                     // Conteúdo do corpo da requisição
                        .when()
                        .put(url+"/pet")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body("name", is("Billy"))
                        .body("status", is("pending"))
                ;

        }

        @Test
        public void Pesquisar_por_pets_com_status_pending_GET_pet_FindByStatus (){
             String status = "pending";

                given().
                        contentType(ContentType.JSON)
                        .when()
                        .get(url + "/pet/findByStatus?status="+status)
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body(
                                containsString("pending")// Utilizando o Contains String para verificar se existe o status pending dentro da resposta do body
                        );

        }
        }




