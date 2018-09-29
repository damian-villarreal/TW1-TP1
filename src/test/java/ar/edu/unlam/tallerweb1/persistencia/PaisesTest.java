package ar.edu.unlam.tallerweb1.persistencia;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.modelo.Ciudad;
import ar.edu.unlam.tallerweb1.modelo.Continente;
import ar.edu.unlam.tallerweb1.modelo.Pais;
import ar.edu.unlam.tallerweb1.modelo.Ubicacion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class PaisesTest extends SpringTest {
	@Test
	@Transactional
	@Rollback(true)

	public void buscarPaisesDeHablaInglesa() {

		Pais usa = new Pais();
		Pais mexico = new Pais();
		Pais canada = new Pais();

		usa.setIdioma("ingles");
		mexico.setIdioma("español");
		canada.setIdioma("ingles");

		Session session = getSession();
		session.save(usa);
		session.save(mexico);
		session.save(canada);

		List<Pais> paisesHablaInglesa = session
				.createCriteria(Pais.class)
				.add(Restrictions.eq("idioma", "ingles"))
				.list();

		assertThat(paisesHablaInglesa).hasSize(2);

	}

	@Test
	@Transactional
	@Rollback(true)
	public void buscarPaisesDelContinenteEuropeo() {

		Continente africa = new Continente();
		africa.setNombre("africa");
		Continente europa = new Continente();
		europa.setNombre("europa");

		Pais francia = new Pais();
		Pais italia = new Pais();
		Pais nigeria = new Pais();

		francia.setContinente(europa);
		italia.setContinente(europa);
		nigeria.setContinente(africa);

		Session session = getSession();

		session.save(francia);
		session.save(italia);
		session.save(nigeria);

		List<Pais> paisesDelContinenteEuropeo = session
				.createCriteria(Pais.class)
				.createAlias("continente", "cont")
				.add(Restrictions.eq("cont.nombre", "europa"))
				.list();

		assertThat(paisesDelContinenteEuropeo).hasSize(2);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void buscarPaisesAlNorteDelTropicoDeCancer() {

		Ubicacion ubicacion1 = new Ubicacion();
		ubicacion1.setLatitud(59.91);
		Ubicacion ubicacion2 = new Ubicacion();
		ubicacion2.setLatitud(-34.05);
		Ubicacion ubicacion3 = new Ubicacion();
		ubicacion3.setLatitud(-35.28);

		Ciudad oslo = new Ciudad();
		Ciudad montevideo = new Ciudad();
		Ciudad canberra = new Ciudad();

		oslo.setUbicacionGeografica(ubicacion1);
		montevideo.setUbicacionGeografica(ubicacion2);
		canberra.setUbicacionGeografica(ubicacion3);

		Pais noruega = new Pais();
		Pais uruguay = new Pais();
		Pais australia = new Pais();

		noruega.setCapital(oslo);
		uruguay.setCapital(montevideo);
		australia.setCapital(canberra);

		Session session = getSession();

		session.save(noruega);
		session.save(uruguay);
		session.save(australia);

		List<Pais> paisesAlNorteDelTRopicoDeCancer = session
				.createCriteria(Pais.class)
				.createAlias("capital", "ciu")
				.createAlias("ciu.ubicacionGeografica", "ub")
				.add(Restrictions.gt("ub.latitud", 20.00))
				.list();

		assertThat(paisesAlNorteDelTRopicoDeCancer).hasSize(1);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void buscapCiudadesDelHemisferioSur() {

		Ubicacion ubicacion1 = new Ubicacion();
		ubicacion1.setLatitud(59.91);
		Ubicacion ubicacion2 = new Ubicacion();
		ubicacion2.setLatitud(-34.05);
		Ubicacion ubicacion3 = new Ubicacion();
		ubicacion3.setLatitud(-35.28);

		Ciudad oslo = new Ciudad();
		Ciudad montevideo = new Ciudad();
		Ciudad canberra = new Ciudad();

		oslo.setUbicacionGeografica(ubicacion1);
		montevideo.setUbicacionGeografica(ubicacion2);
		canberra.setUbicacionGeografica(ubicacion3);
		
		Session session = getSession();
		session.save(oslo);
		session.save(montevideo);
		session.save(canberra);
		
		List<Ciudad>ciudadesDelHemisferioSur = session.createCriteria(Ciudad.class)
				.createAlias("ubicacionGeografica", "ub")
				.add(Restrictions.lt("ub.latitud", 00.00))
				.list();
		
		assertThat(ciudadesDelHemisferioSur).hasSize(2);
	}

}