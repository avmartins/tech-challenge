package pixeon.tech.challenge.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import pixeon.tech.challenge.Application;
import pixeon.tech.challenge.entity.Exam;
import pixeon.tech.challenge.entity.HealthCareInstitution;

/**
 * @author Anderson Virginio Martins
 * 
 * Claase responsável por testar a HealthCareExamRestController 
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@ActiveProfiles(profiles = "test")
public class HealthCareExamRestControllerTest {
	
	@Test
	public void contextLoads() {
	}

	@Test
	public void testCreateHealthCareInstitution() {

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();

		HealthCareInstitution healthCareInstitution = new HealthCareInstitution();
		healthCareInstitution.setCnpj("555.555.555-51");
		healthCareInstitution.setName("Health Care Test 51");
		healthCareInstitution.setMoeda(20);
		healthCareInstitution.setQtdMoedaUsada(0);

		HttpEntity<HealthCareInstitution> request = new HttpEntity<>(healthCareInstitution, headers);

		final String baseUrl = "http://localhost:8090/HealthCareExam/CreateHealthCare";

		try {
			URI uri = new URI(baseUrl);
			restTemplate.postForEntity(uri, request, HealthCareInstitution.class);
			assertNotNull(request.getBody());
			System.out.println("\ntestCreateHealthCareInstitution : HealthCareInstitution Criado com sucesso!\n");
		} catch (HttpClientErrorException ex) {
			assertEquals(400, ex.getRawStatusCode());
			assertEquals(true, ex.getResponseBodyAsString().contains("Erro não existe request ou header"));
		} catch (Exception ex) {
			assertEquals(500, ex.getMessage());
		}
	}
	
	@Test
	public void testCreateExam() {

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		
		HealthCareInstitution healthCareInstitution = buscaHealthCareInstitution().getHealthCareInstitution();
				
		Exam exam = new Exam();
		exam.setHealthCareInstitution(healthCareInstitution);
		exam.setPatientAge(30);
		exam.setPatientGender("M");
		exam.setPatientName("KKKKKKKKKKKKKKKKKK");
		exam.setPhysicianCRM("CRM");
		exam.setPhysicianName("KKKKKKK");
		exam.setProcedureName("KKKKKKKKKKKKK");

		HttpEntity<Exam> request = new HttpEntity<>(exam, headers);

		final String baseUrl = "http://localhost:8090/HealthCareExam/CreateExam";

		try {
			URI uri = new URI(baseUrl);
			restTemplate.postForEntity(uri, request, HealthCareInstitution.class);
			assertNotNull(request.getBody());
			System.out.println("\ntestCreateExam : Exam Criado com sucesso!\n");
		} catch (HttpClientErrorException ex) {
			assertEquals(400, ex.getRawStatusCode());
			assertEquals(true, ex.getResponseBodyAsString().contains("Erro não existe request ou header"));
		} catch (Exception ex) {
			assertEquals(500, ex.getMessage());
		}
	}
	
	@Test
	public void testUpdateExam() {

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
				
		Exam exam = buscaHealthCareInstitution();
		exam.setPatientAge(34);
		exam.setPatientGender("F");
		exam.setPatientName("uuuuuuuuuuuuuuuuuuuuuuuuuuuu");
		exam.setPhysicianCRM("CRM");
		exam.setPhysicianName("YYYYYYY");
		exam.setProcedureName("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
		
		HttpEntity<Exam> request = new HttpEntity<>(exam, headers);

		final String baseUrl = "http://localhost:8090/HealthCareExam/UpdateExam/1";

		try {
			URI uri = new URI(baseUrl);
			restTemplate.exchange(uri, HttpMethod.PUT, request,Exam.class);
			assertNotNull(request.getBody());
			System.out.println("\ntestUpdateExam : Atualização Exam com sucesso!\n");
		} catch (HttpClientErrorException ex) {
			assertEquals(400, ex.getRawStatusCode());
			assertEquals(true, ex.getResponseBodyAsString().contains("Erro não existe request ou header"));
		} catch (Exception ex) {
			assertEquals(500, ex.getMessage());
		}
	}
	
	@Test
	public void testDeleteExam() {

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
				
		Exam exam = buscaHealthCareInstitution();
				
		HttpEntity<Exam> request = new HttpEntity<>(exam, headers);

		final String baseUrl = "http://localhost:8090/HealthCareExam/DeleteExam/1";

		try {
			URI uri = new URI(baseUrl);
			restTemplate.exchange(uri, HttpMethod.DELETE, request,Exam.class);
			assertNotNull(request.getBody());
			System.out.println("\ntestDeleteExam : Exam deletado com sucesso!\n");
		} catch (HttpClientErrorException ex) {
			assertEquals(400, ex.getRawStatusCode());
			assertEquals(true, ex.getResponseBodyAsString().contains("Erro não existe request ou header"));
		} catch (Exception ex) {
			assertEquals(500, ex.getMessage());
		}
	}

	@Test
	public void testRetrieveAllExams() {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<Iterable<Exam>> response = restTemplate.exchange("http://localhost:8090/HealthCareExam/exams",
				HttpMethod.GET, null, new ParameterizedTypeReference<Iterable<Exam>>() {
				});

		ResponseEntity<String> responseString = restTemplate.exchange("http://localhost:8090/HealthCareExam/exams",
				HttpMethod.GET, entity, String.class);

		System.out.println("\ntestRetrieveAllExams : Retorno JSON : "+ responseString.getBody());

		Iterable<Exam> listaExames = response.getBody();

		for (Exam exam : listaExames) {
			System.out.println("\n"+exam.getPatientName());
		}

		assertNotNull(response.getBody());
	}
	
	private Exam buscaHealthCareInstitution() {
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<Iterable<Exam>> response = restTemplate.exchange("http://localhost:8090/HealthCareExam/exams",
				HttpMethod.GET, null, new ParameterizedTypeReference<Iterable<Exam>>() {
				});

		Iterable<Exam> listaExames = response.getBody();

		Exam examReturn = null;
		
		for (Exam exam : listaExames) {
			examReturn = exam;
			break;
		}

		return examReturn;
	}

	@Test
	public void testRetrieveExam() {
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<Exam> response = restTemplate.exchange("http://localhost:8090/HealthCareExam/exams/2/1",
				HttpMethod.GET, null, new ParameterizedTypeReference<Exam>() {
				});

		Exam exam = response.getBody();

		System.out.println("\ntestRetrieveExam : "+exam.getPatientName());	

		assertNotNull(response.getBody());
	}

}
