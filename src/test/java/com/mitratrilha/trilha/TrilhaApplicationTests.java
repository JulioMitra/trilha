package com.mitratrilha.trilha;

import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@ActiveProfiles("teste")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TrilhaApplicationTests {

	@Autowired
	private MockMvc mvc;

	public static final String path = "src/test/java/com/mitratrilha/trilha/jsonresponse/";


	private static String fileToJsonString(String fileName) throws IOException {
		return new String(Files.readAllBytes(Paths.get(path + fileName)));
	}


	@Container
	public static PostgreSQLContainer container = new PostgreSQLContainer<>
			("postgres:latest").
			withDatabaseName("mitradb").
			withUsername("postgres").
			withPassword("postgres");

	@DynamicPropertySource
	public static void properties(DynamicPropertyRegistry registry) {
		registry.add("teste.datasource.url", container::getJdbcUrl);
		registry.add("teste.datasource.password", container::getPassword);
		registry.add("teste.datasource.username", container::getUsername);
	}

	@Test
	@Order(0)
	@SneakyThrows
	@DisplayName("Fluxo 0 - Criação de dimensões - Cidade, Estado, Pais, DDD, Prefeito, Governador e " +
				 "ExtraParaAlterarNomeEDeletar")
	public void flx_0() {

		mvc.perform(post("/dimen")
						.content(fileToJsonString("FLUXO_0_1_FORM.json"))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().json(fileToJsonString("FLUXO_0_1_VIEW.json")))
				.andExpect(status().isCreated())
				.andDo(print())
				.andReturn();

		mvc.perform(post("/dimen")
						.content(fileToJsonString("FLUXO_0_2_FORM.json"))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().json(fileToJsonString("FLUXO_0_2_VIEW.json")))
				.andExpect(status().isCreated())
				.andDo(print())
				.andReturn();

		mvc.perform(post("/dimen")
						.content(fileToJsonString("FLUXO_0_3_FORM.json"))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().json(fileToJsonString("FLUXO_0_3_VIEW.json")))
				.andExpect(status().isCreated())
				.andDo(print())
				.andReturn();

		mvc.perform(post("/dimen")
						.content(fileToJsonString("FLUXO_0_4_FORM.json"))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().json(fileToJsonString("FLUXO_0_4_VIEW.json")))
				.andExpect(status().isCreated())
				.andDo(print())
				.andReturn();

		mvc.perform(post("/dimen")
						.content(fileToJsonString("FLUXO_0_5_FORM.json"))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().json(fileToJsonString("FLUXO_0_5_VIEW.json")))
				.andExpect(status().isCreated())
				.andDo(print())
				.andReturn();

		mvc.perform(post("/dimen")
						.content(fileToJsonString("FLUXO_0_6_FORM.json"))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().json(fileToJsonString("FLUXO_0_6_VIEW.json")))
				.andExpect(status().isCreated())
				.andDo(print())
				.andReturn();

		mvc.perform(post("/dimen")
						.content(fileToJsonString("FLUXO_0_7_FORM.json"))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().json(fileToJsonString("FLUXO_0_7_VIEW.json")))
				.andExpect(status().isCreated())
				.andDo(print())
				.andReturn();
	}

	@Test
	@Order(1)
	@SneakyThrows
	@DisplayName("Fluxo 1 - Listar as sete dimensões criadas")
	public void flx_1() {

		mvc.perform(get("/dimen"))
				.andExpect(content().json(fileToJsonString("FLUXO_1_1_VIEW.json")))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();
	}

	@Test
	@Order(2)
	@SneakyThrows
	@DisplayName("Fluxo 2 - Atualizar nome e sonid da dimensão ExtraParaAlterarNomeEDeletar para ExtraParaDeletar e 1")
	public void flx_2() {

		mvc.perform(put("/dimen")
						.content(fileToJsonString("FLUXO_2_1_FORM.json"))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().json(fileToJsonString("FLUXO_2_1_VIEW.json")))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();
	}

	@Test
	@Order(3)
	@SneakyThrows
	@DisplayName("Fluxo 3 - Deletar a dimensão ExtraParaDeletar e listar dimensões restantes")
	public void flx_3() {

		mvc.perform(delete("/dimen/7"))
				.andExpect(content().string("Dimensão excluída!"))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

		mvc.perform(get("/dimen"))
				.andExpect(content().json(fileToJsonString("FLUXO_3_2_VIEW.json")))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();
	}

	@Test
	@Order(4)
	@SneakyThrows
	@DisplayName("Fluxo 4 - Adicionar relacionamento de Estado, País, DDD, Prefeito e Governador")
	public void flx_4() {

		mvc.perform(put("/dimen")
						.content(fileToJsonString("FLUXO_4_1_FORM.json"))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().json(fileToJsonString("FLUXO_4_1_VIEW.json")))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

		mvc.perform(put("/dimen")
						.content(fileToJsonString("FLUXO_4_2_FORM.json"))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().json(fileToJsonString("FLUXO_4_2_VIEW.json")))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

		mvc.perform(put("/dimen")
						.content(fileToJsonString("FLUXO_4_3_FORM.json"))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().json(fileToJsonString("FLUXO_4_3_VIEW.json")))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

		mvc.perform(put("/dimen")
						.content(fileToJsonString("FLUXO_4_4_FORM.json"))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().json(fileToJsonString("FLUXO_4_4_VIEW.json")))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

		mvc.perform(put("/dimen")
						.content(fileToJsonString("FLUXO_4_5_FORM.json"))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().json(fileToJsonString("FLUXO_4_5_VIEW.json")))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();
	}

	@Test
	@Order(5)
	@SneakyThrows
	@DisplayName("Fluxo 5 - Listar árvore relacionamento Cidade")
	public void flx_5() {

		mvc.perform(get("/dimen/1"))
				.andExpect(content().json(fileToJsonString("FLUXO_5_1_VIEW.json")))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();
	}

	@Test
	@Order(6)
	@SneakyThrows
	@DisplayName("Fluxo 6 - Criação de membros da dimensão Cidade")
	public void flx_6() {

		mvc.perform(post("/dimen/member/1")
						.content(fileToJsonString("FLUXO_6_1_FORM.json"))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().json(fileToJsonString("FLUXO_6_1_VIEW.json")))
				.andExpect(status().isCreated())
				.andDo(print())
				.andReturn();

		mvc.perform(post("/dimen/member/1")
						.content(fileToJsonString("FLUXO_6_2_FORM.json"))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().json(fileToJsonString("FLUXO_6_2_VIEW.json")))
				.andExpect(status().isCreated())
				.andDo(print())
				.andReturn();

		mvc.perform(post("/dimen/member/1")
						.content(fileToJsonString("FLUXO_6_3_FORM.json"))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().json(fileToJsonString("FLUXO_6_3_VIEW.json")))
				.andExpect(status().isCreated())
				.andDo(print())
				.andReturn();

		mvc.perform(post("/dimen/member/1")
						.content(fileToJsonString("FLUXO_6_4_FORM.json"))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().json(fileToJsonString("FLUXO_6_4_VIEW.json")))
				.andExpect(status().isCreated())
				.andDo(print())
				.andReturn();

		mvc.perform(post("/dimen/member/1")
						.content(fileToJsonString("FLUXO_6_5_FORM.json"))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().json(fileToJsonString("FLUXO_6_5_VIEW.json")))
				.andExpect(status().isCreated())
				.andDo(print())
				.andReturn();

		mvc.perform(post("/dimen/member/1")
						.content(fileToJsonString("FLUXO_6_6_FORM.json"))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().json(fileToJsonString("FLUXO_6_6_VIEW.json")))
				.andExpect(status().isCreated())
				.andDo(print())
				.andReturn();
	}

	@Test
	@Order(7)
	@SneakyThrows
	@DisplayName("Fluxo 7 - Criação de membros da dimensão Estado")
	public void flx_7() {

		mvc.perform(post("/dimen/member/2")
						.content(fileToJsonString("FLUXO_7_1_FORM.json"))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().json(fileToJsonString("FLUXO_7_1_VIEW.json")))
				.andExpect(status().isCreated())
				.andDo(print())
				.andReturn();

		mvc.perform(post("/dimen/member/2")
						.content(fileToJsonString("FLUXO_7_2_FORM.json"))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().json(fileToJsonString("FLUXO_7_2_VIEW.json")))
				.andExpect(status().isCreated())
				.andDo(print())
				.andReturn();

		mvc.perform(post("/dimen/member/2")
						.content(fileToJsonString("FLUXO_7_3_FORM.json"))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().json(fileToJsonString("FLUXO_7_3_VIEW.json")))
				.andExpect(status().isCreated())
				.andDo(print())
				.andReturn();

		mvc.perform(post("/dimen/member/2")
						.content(fileToJsonString("FLUXO_7_4_FORM.json"))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().json(fileToJsonString("FLUXO_7_4_VIEW.json")))
				.andExpect(status().isCreated())
				.andDo(print())
				.andReturn();
	}

	@Test
	@Order(8)
	@SneakyThrows
	@DisplayName("Fluxo 8 - Criação de membros da dimensão País")
	public void flx_8() {

		mvc.perform(post("/dimen/member/3")
						.content(fileToJsonString("FLUXO_8_1_FORM.json"))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().json(fileToJsonString("FLUXO_8_1_VIEW.json")))
				.andExpect(status().isCreated())
				.andDo(print())
				.andReturn();
	}

	@Test
	@Order(9)
	@SneakyThrows
	@DisplayName("Fluxo 9 - Criação de membros da dimensão DDD")
	public void flx_9() {

		mvc.perform(post("/dimen/member/4")
						.content(fileToJsonString("FLUXO_9_1_FORM.json"))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().json(fileToJsonString("FLUXO_9_1_VIEW.json")))
				.andExpect(status().isCreated())
				.andDo(print())
				.andReturn();

		mvc.perform(post("/dimen/member/4")
						.content(fileToJsonString("FLUXO_9_2_FORM.json"))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().json(fileToJsonString("FLUXO_9_2_VIEW.json")))
				.andExpect(status().isCreated())
				.andDo(print())
				.andReturn();

		mvc.perform(post("/dimen/member/4")
						.content(fileToJsonString("FLUXO_9_3_FORM.json"))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().json(fileToJsonString("FLUXO_9_3_VIEW.json")))
				.andExpect(status().isCreated())
				.andDo(print())
				.andReturn();

		mvc.perform(post("/dimen/member/4")
						.content(fileToJsonString("FLUXO_9_4_FORM.json"))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().json(fileToJsonString("FLUXO_9_4_VIEW.json")))
				.andExpect(status().isCreated())
				.andDo(print())
				.andReturn();

		mvc.perform(post("/dimen/member/4")
						.content(fileToJsonString("FLUXO_9_5_FORM.json"))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().json(fileToJsonString("FLUXO_9_5_VIEW.json")))
				.andExpect(status().isCreated())
				.andDo(print())
				.andReturn();
	}

	@Test
	@Order(10)
	@SneakyThrows
	@DisplayName("Fluxo 10 - Listar membros da dimensão Cidade")
	public void flx_10() {

		mvc.perform(get("/dimen/member/1"))
				.andExpect(content().json(fileToJsonString("FLUXO_10_1_VIEW.json")))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();
	}

	@Test
	@Order(11)
	@SneakyThrows
	@DisplayName("Fluxo 11 - Atualizar nome do membro da dimensão Cidade de CidadeDeletar para Deletar")
	public void flx_11() {

		mvc.perform(put("/dimen/member/1")
						.content(fileToJsonString("FLUXO_11_1_FORM.json"))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().json(fileToJsonString("FLUXO_11_1_VIEW.json")))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();
	}

	@Test
	@Order(12)
	@SneakyThrows
	@DisplayName("Fluxo 12 - Deletar membro Deletar da dimensão Cidade e listar membros restantes")
	public void flx_12() {

		mvc.perform(delete("/dimen/member/1")
						.content(fileToJsonString("FLUXO_12_1_FORM.json"))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().string("Membro com id: 7 excluÃ\u00ADdo!"))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

		mvc.perform(get("/dimen/member/1"))
				.andExpect(content().json(fileToJsonString("FLUXO_12_1_VIEW.json")))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();
	}

	@Test
	@Order(13)
	@SneakyThrows
	@DisplayName("Fluxo 13 - Adicionar relacionamento membros dimensão Estado com Cidade")
	public void flx_13() {

		mvc.perform(post("/dimen/relation/2")
						.content(fileToJsonString("FLUXO_13_1_FORM.json"))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().string("Relacionamento entre a DimensÃ£o Pai 2 e a DimensÃ£o Filha 1 realizado " +
											"com sucesso!"))
				.andExpect(status().isCreated())
				.andDo(print())
				.andReturn();

	}

	@Test
	@Order(14)
	@SneakyThrows
	@DisplayName("Fluxo 14 - Adicionar relacionamento membros dimensão País com Estado")
	public void flx_14() {

		mvc.perform(post("/dimen/relation/3")
						.content(fileToJsonString("FLUXO_14_1_FORM.json"))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().string("Relacionamento entre a DimensÃ£o Pai 3 e a DimensÃ£o Filha 2 realizado " +
											"com sucesso!"))
				.andExpect(status().isCreated())
				.andDo(print())
				.andReturn();

	}

	@Test
	@Order(15)
	@SneakyThrows
	@DisplayName("Fluxo 15 - Adicionar relacionamento membros dimensão DDD com Cidade")
	public void flx_15() {

		mvc.perform(post("/dimen/relation/4")
						.content(fileToJsonString("FLUXO_15_1_FORM.json"))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().string("Relacionamento entre a DimensÃ£o Pai 4 e a DimensÃ£o Filha 1 realizado " +
											"com sucesso!"))
				.andExpect(status().isCreated())
				.andDo(print())
				.andReturn();

	}

	@Test
	@Order(16)
	@SneakyThrows
	@DisplayName("Fluxo 16 - Listar relacionamentos da dimensão raiz Cidade")
	public void flx_16() {

		mvc.perform(get("/dimen/relation/1"))
				.andExpect(content().json(fileToJsonString("FLUXO_16_1_VIEW.json")))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();
	}

	@Test
	@Order(17)
	@SneakyThrows
	@DisplayName("Fluxo 17 - Atualizar relacionamento entre membro da dimensão Estado e Cidade")
	public void flx_17() {

		mvc.perform(post("/dimen/relation/2")
						.content(fileToJsonString("FLUXO_17_1_FORM.json"))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().string("Relacionamento entre a DimensÃ£o Pai 2 e a DimensÃ£o Filha 1 realizado " +
											"com sucesso!"))
				.andExpect(status().isCreated())
				.andDo(print())
				.andReturn();

		mvc.perform(get("/dimen/relation/1"))
				.andExpect(content().json(fileToJsonString("FLUXO_17_1_VIEW.json")))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

	}

	@Test
	@Order(18)
	@SneakyThrows
	@DisplayName("Fluxo 18 - Deletar relacionamento entre membro da dimensão Estado e Cidade")
	public void flx_18() {

		mvc.perform(delete("/dimen/relation/2")
						.content(fileToJsonString("FLUXO_18_1_FORM.json"))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().string("Relacionamento entre membros da DimensÃ£o Pai 2 e da DimensÃ£o Filha 1 " +
											"excluÃ\u00ADdos com sucesso!"))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

		mvc.perform(get("/dimen/relation/1"))
				.andExpect(content().json(fileToJsonString("FLUXO_18_1_VIEW.json")))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

	}

	@Test
	@Order(19)
	@SneakyThrows
	@DisplayName("Fluxo 19 - Criação de membros da dimensão Prefeito")
	public void flx_19() {

		mvc.perform(post("/dimen/member/5")
						.content(fileToJsonString("FLUXO_19_1_FORM.json"))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().json(fileToJsonString("FLUXO_19_1_VIEW.json")))
				.andExpect(status().isCreated())
				.andDo(print())
				.andReturn();
	}

	@Test
	@Order(20)
	@SneakyThrows
	@DisplayName("Fluxo 20 - Adicionar relacionamento membros dimensão Prefeito com Cidade")
	public void flx_20() {

		mvc.perform(post("/dimen/relation/5")
						.content(fileToJsonString("FLUXO_20_1_FORM.json"))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().string("Relacionamento entre a DimensÃ£o Pai 5 e a DimensÃ£o Filha 1 realizado " +
											"com sucesso!"))
				.andExpect(status().isCreated())
				.andDo(print())
				.andReturn();

	}

	@Test
	@Order(21)
	@SneakyThrows
	@DisplayName("Fluxo 21 - Listar relacionamentos da dimensão raiz Cidade - Com Prefeito")
	public void flx_21() {

		mvc.perform(get("/dimen/relation/1"))
				.andExpect(content().json(fileToJsonString("FLUXO_21_1_VIEW.json")))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();
	}
}
