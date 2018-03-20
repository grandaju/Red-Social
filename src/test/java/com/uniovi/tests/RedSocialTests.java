package com.uniovi.tests;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_PublicationView;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_SearchUserView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.util.SeleniumUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RedSocialTests {

	// En Windows (Debe ser la versi�n 46.0 y desactivar las actualizacioens
	// autom�ticas)):
	static String PathFirefox = "C:\\Users\\Usuario\\Desktop\\ff\\FirefoxPortable.exe";
	// En MACOSX (Debe ser la versi�n 46.0 y desactivar las actualizaciones
	// autom�ticas):
	// static String PathFirefox =
	// "/Applications/Firefox.app/Contents/MacOS/firefox-bin";
	// Com�n a Windows y a MACOSX
	static WebDriver driver = getDriver(PathFirefox);
	static String URL = "http://localhost:8090";

	public static WebDriver getDriver(String PathFirefox) {
		// Firefox (Versi�n 46.0) sin geckodriver para Selenium 2.x.
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	// Antes de cada prueba se navega al URL home de la aplicaci�nn
	@Before
	public void setUp() {
		driver.navigate().to(URL);
	}

	// Despu�s de cada prueba se borran las cookies del navegador
	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	}

	// Antes de la primera prueba
	@BeforeClass
	static public void begin() {
	}

	// Al finalizar la �ltima prueba
	@AfterClass
	static public void end() {
		// Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}

	/** 1.1 [RegVal] Registro de Usuario con datos válidos. */
	@Test
	public void PR01_1() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "prueba@gmail.com", "Prueba", "Prueba", "123456", "123456");
		// comprobamos que entramos en Home
		PO_View.checkElement(driver, "text", "Bienvenidos a Red Social Genial");
	}

	/**
	 * 1.2 [RegInval] Registro de Usuario con datos inválidos (repetición de
	 * contraseña invalida).
	 */
	@Test
	public void PR01_2() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario pero la contraseña no coincide
		PO_RegisterView.fillForm(driver, "prueba2@gmail.com", "Prueba2", "Prueba2", "123456", "9999999");
		// comprobamos que se muestra el mensaje para la no coincidencia de contraseñas
		PO_View.checkElement(driver, "text", "Las contraseñas no coinciden");
	}

	/** 2.1 [InVal] Inicio de sesión con datos válidos. */
	@Test
	public void PR02_1() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_LoginView.fillForm(driver, "prueba@gmail.com", "123456");
		// Comprobamos que entramos en la sección del listado de usuarios
		PO_View.checkElement(driver, "text", "Usuarios que se encuentran en el sistema");

	}

	/**
	 * 2.2 [InInVal] Inicio de sesión con datos inválidos (usuario no existente en
	 * la aplicación).
	 */
	@Test
	public void PR02_2() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_LoginView.fillForm(driver, "NOEXISTE@gmail.com", "123456");
		// Comprobamos que entramos en la sección del listado de usuarios
		PO_View.checkElement(driver, "text", "Identificate");
	}

	/**
	 * 3.1 [LisUsrVal] Acceso al listado de usuarios desde un usuario en sesión.
	 */
	@Test
	public void PR03_1() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_LoginView.fillForm(driver, "prueba@gmail.com", "123456");
		// Comprobamos que entramos en la sección del listado de usuarios
		PO_View.checkElement(driver, "text", "Usuarios que se encuentran en el sistema");
		// Pinchamos en la opción de menu de usuarios: //li[contains(@id,
		// 'users-menu')]/a
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'users-menu')]/a");
		// Clickamos en ver los usuarios
		elementos.get(0).click();
		SeleniumUtils.esperarSegundos(driver, 2);
		// Comprobamos que entramos en la sección del listado de usuarios
		PO_View.checkElement(driver, "text", "Usuarios que se encuentran en el sistema");

	}
	// 3.2 [LisUsrInVal] Intento de acceso con URL desde un usuario no identificado
	// al listado de usuarios desde un usuario en sesión.
	// Debe producirse un acceso no permitido a vistas privadas.

	@Test
	public void PR03_2() {
		// Navegamos a la url /user/list
		driver.navigate().to("http://localhost:8090/user/list");
		// Ya que se trata de un usuario no identificado se redirige al login
		PO_View.checkElement(driver, "text", "Identificate");

	}

	/**
	 * 4.1 [BusUsrVal] Realizar una búsqueda valida en el listado de usuarios desde
	 * un usuario en sesión.
	 */
	@Test
	public void PR04_1() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_LoginView.fillForm(driver, "prueba@gmail.com", "123456");
		// Comprobamos que entramos en la sección del listado de usuarios
		PO_View.checkElement(driver, "text", "Usuarios que se encuentran en el sistema");
		// Vamos a la lista de usuarios (a pesar de estar alli)
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		SeleniumUtils.esperarSegundos(driver, 1);

		// Vamos a la ultima página y comprobamos que existe prueba@gmail.com
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
		elementos.get(3).click();
		SeleniumUtils.esperarSegundos(driver, 1);

		// Comprobamos si prueba existe y volvemos a la primera página
		PO_View.checkElement(driver, "free", "//td[contains(text(), 'edward@gmail.com')]");
		// Volvemos a la primera página
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
		elementos.get(0).click();
		SeleniumUtils.esperarSegundos(driver, 1);

		// Buscamos un usuario
		PO_SearchUserView.fillSearch(driver, "edward@gmail.com");
		SeleniumUtils.esperarSegundos(driver, 1);
		// Comprobamos que entramos en la sección del listado de usuarios
		PO_View.checkElement(driver, "free", "//td[contains(text(), 'edward@gmail.com')]");

	}

	/**
	 * 4.2 [BusUsrInVal] Intento de acceso con URL a la búsqueda de usuarios desde
	 * un usuario no identificado. Debe producirse un acceso no permitido a vistas
	 * privadas.
	 */
	@Test
	public void PR04_2() {
		// Navegamos a la url de la busqueda /user/list?searchText=prueba@gmail.com
		driver.navigate().to("http://localhost:8090/user/list?searchText=prueba@gmail.com");
		// Ya que se trata de un usuario no identificado se redirige al login
		PO_View.checkElement(driver, "text", "Identificate");

	}

	/**
	 * 5.1 [InvVal] Enviar una invitación de amistad a un usuario de forma valida.
	 */
	@Test
	public void PR05_1() {
		// Vamos al formulario de login
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		SeleniumUtils.esperarSegundos(driver, 1);

		// Rellenamos el formulario.
		PO_LoginView.fillForm(driver, "prueba@gmail.com", "123456");
		// Comprobamos que entramos en la sección del listado de usuarios
		PO_View.checkElement(driver, "text", "Usuarios que se encuentran en el sistema");
		// Vamos a la lista de usuarios (a pesar de estar alli)
		List<WebElement> elems = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elems.get(0).click();
		SeleniumUtils.esperarSegundos(driver, 1);

		// Invitamos a pedro@gmail.com a ser amigos de prueba@gmail.com
		elems = PO_View.checkElement(driver, "free", "//td[contains(text(), 'pedro@gmail.com')]/"
				+ "following-sibling::*/button[contains(@id, 'invitationButton1')]");
		elems.get(0).click();

		SeleniumUtils.esperarSegundos(driver, 2);
		// Logout
		driver.navigate().to("http://localhost:8090/login?logout");

		PO_LoginView.fillForm(driver, "pedro@gmail.com", "123456");

		// Vamos a comprobar si la invitacion se ha enviado vamos a la url
		driver.navigate().to("http://localhost:8090/invitation/list");

		// Comprobamos que estamos viendo las invitaciones
		PO_View.checkElement(driver, "text", "Lista de invitaciones");
		// Comprobamos que la invitacion se ha enviado vemos que prueba quiere ser amigo
		// de pedro@gmail.com
		PO_View.checkElement(driver, "free", "//td[contains(text(), 'prueba@gmail.com')]");

	}

	/**
	 * 5.2 [InvInVal] Enviar una invitación de amistad a un usuario al que ya le
	 * habíamos invitado la invitación previamente. No debería dejarnos enviar la
	 * invitación, se podría ocultar el botón de enviar invitación o notificar que
	 * ya había sido enviada previamente.
	 */
	@Test
	public void PR05_2() {
		// Vamos al formulario de login
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		SeleniumUtils.esperarSegundos(driver, 1);

		// Rellenamos el formulario.
		PO_LoginView.fillForm(driver, "prueba@gmail.com", "123456");
		// Comprobamos que entramos en la sección del listado de usuarios
		PO_View.checkElement(driver, "text", "Usuarios que se encuentran en el sistema");
		// Vamos a la lista de usuarios (a pesar de estar alli)
		List<WebElement> elems = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elems.get(0).click();
		SeleniumUtils.esperarSegundos(driver, 1);

		// Invitamos a pedro@gmail.com a ser amigos de prueba@gmail.com
		elems = PO_View.checkElement(driver, "free", "//td[contains(text(), 'pedro@gmail.com')]/following-sibling::"
				+ "*/button[contains(@id, 'invitationButton1')]");
		elems.get(0).click();

		PO_View.checkElement(driver, "free", "//td[contains(text(), 'pedro@gmail.com')]/following-sibling::"
				+ "*/p[contains(text(), 'Usuario ya invitado / User already invited')]");

	}

	/**
	 * 6.1 [LisInvVal] Listar las invitaciones recibidas por un usuario, realizar la
	 * comprobación con una lista que al menos tenga una invitación recibida.
	 */
	@Test
	public void PR06_1() {
		// Vamos al formulario de login
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		SeleniumUtils.esperarSegundos(driver, 1);

		// Rellenamos el formulario.
		PO_LoginView.fillForm(driver, "prueba@gmail.com", "123456");
		// Comprobamos que entramos en la sección del listado de usuarios
		PO_View.checkElement(driver, "text", "Usuarios que se encuentran en el sistema");
		// Vamos a la lista de usuarios (a pesar de estar alli)
		List<WebElement> elems = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elems.get(0).click();
		SeleniumUtils.esperarSegundos(driver, 1);

		// Invitamos a pedro@gmail.com a ser amigos de prueba@gmail.com
		elems = PO_View.checkElement(driver, "free", "//td[contains(text(), 'lucas@gmail.com')]/following-sibling::"
				+ "*/button[contains(@id, 'invitationButton2')]");
		elems.get(0).click();

		SeleniumUtils.esperarSegundos(driver, 2);
		// Logout
		driver.navigate().to("http://localhost:8090/login?logout");

		PO_LoginView.fillForm(driver, "lucas@gmail.com", "123456");

		// Vamos a comprobar si la invitacion se ha enviado vamos a la url
		driver.navigate().to("http://localhost:8090/invitation/list");

		// Comprobamos que estamos viendo las invitaciones
		PO_View.checkElement(driver, "text", "Lista de invitaciones");
		// Comprobamos que la invitacion se ha enviado vemos que prueba quiere ser amigo
		// de pedro@gmail.com
		PO_View.checkElement(driver, "free", "//td[contains(text(), 'prueba@gmail.com')]");

	}

	/**
	 * 7.1 [AcepInvVal] Aceptar una invitación recibida.
	 */
	@Test
	public void PR07_1() {
		// Vamos al formulario de login
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		SeleniumUtils.esperarSegundos(driver, 1);

		// Rellenamos el formulario.
		PO_LoginView.fillForm(driver, "prueba@gmail.com", "123456");
		// Comprobamos que entramos en la sección del listado de usuarios
		PO_View.checkElement(driver, "text", "Usuarios que se encuentran en el sistema");
		// Vamos a la lista de usuarios (a pesar de estar alli)
		List<WebElement> elems = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elems.get(0).click();
		SeleniumUtils.esperarSegundos(driver, 1);

		// Invitamos a pedro@gmail.com a ser amigos de prueba@gmail.com
		elems = PO_View.checkElement(driver, "free", "//td[contains(text(), 'maria@gmail.com')]/following-sibling::"
				+ "*/button[contains(@id, 'invitationButton3')]");
		elems.get(0).click();

		SeleniumUtils.esperarSegundos(driver, 2);
		// Logout
		driver.navigate().to("http://localhost:8090/login?logout");

		PO_LoginView.fillForm(driver, "maria@gmail.com", "123456");

		// Vamos a comprobar si la invitacion se ha enviado vamos a la url
		driver.navigate().to("http://localhost:8090/invitation/list");

		// Comprobamos que estamos viendo las invitaciones
		PO_View.checkElement(driver, "text", "Lista de invitaciones");
		// Comprobamos que la invitacion se ha enviado vemos que prueba quiere ser amigo
		// de maria@gmail.com y la aceptamos
		elems = PO_View.checkElement(driver, "free", "//td[contains(text(), 'prueba@gmail.com')]/following-sibling::"
				+ "*/button[contains(@id, 'friendButton7')]");
		elems.get(0).click();

		SeleniumUtils.esperarSegundos(driver, 2);
		// Vamos a la url de que muestra la lista de amigos
		driver.navigate().to("http://localhost:8090/friends/list");

		// Estamos viendo los amigos
		PO_View.checkElement(driver, "text", "Amigos");
		// En la tabla de los amigos se enctentra pedro
		PO_View.checkElement(driver, "free", "//td[contains(text(), 'Prueba')]");

	}

	/**
	 * 8.1 [ListAmiVal] Listar los amigos de un usuario, realizar la comprobación
	 * con una lista que al menos tenga un amigo.
	 */
	@Test
	public void PR08_1() {
		// Vamos al formulario de login
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		SeleniumUtils.esperarSegundos(driver, 1);

		// Rellenamos el formulario.
		PO_LoginView.fillForm(driver, "prueba@gmail.com", "123456");
		// Comprobamos que entramos en la sección del listado de usuarios
		PO_View.checkElement(driver, "text", "Usuarios que se encuentran en el sistema");
		// Vamos a la lista de usuarios (a pesar de estar alli)
		List<WebElement> elems = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elems.get(0).click();
		SeleniumUtils.esperarSegundos(driver, 1);

		// Invitamos a pelayoy@gmail.com a ser amigos de prueba@gmail.com
		elems = PO_View.checkElement(driver, "free", "//td[contains(text(), 'pelayo@gmail.com')]/following-sibling::"
				+ "*/button[contains(@id, 'invitationButton5')]");
		elems.get(0).click();

		SeleniumUtils.esperarSegundos(driver, 2);
		// Logout
		driver.navigate().to("http://localhost:8090/login?logout");

		PO_LoginView.fillForm(driver, "pelayo@gmail.com", "123456");

		// Vamos a comprobar si la invitacion se ha enviado vamos a la url
		driver.navigate().to("http://localhost:8090/invitation/list");

		// Comprobamos que estamos viendo las invitaciones
		PO_View.checkElement(driver, "text", "Lista de invitaciones");
		// Comprobamos que la invitacion se ha enviado vemos que prueba quiere ser amigo
		// de pelayo@gmail.com y la aceptamos
		elems = PO_View.checkElement(driver, "free", "//td[contains(text(), 'prueba@gmail.com')]/following-sibling::"
				+ "*/button[contains(@id, 'friendButton7')]");
		elems.get(0).click();

		SeleniumUtils.esperarSegundos(driver, 2);
		// Vamos a la url de que muestra la lista de amigos
		driver.navigate().to("http://localhost:8090/friends/list");

		// Estamos viendo los amigos
		PO_View.checkElement(driver, "text", "Amigos");
		// En la tabla de los amigos se enctentra pedro
		PO_View.checkElement(driver, "free", "//td[contains(text(), 'Prueba')]");

	}

	/**
	 * 9.1 [PubVal] Crear una publicación con datos válidos.
	 */
	@Test
	public void PR09_1() {
		// Vamos al formulario de login
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		SeleniumUtils.esperarSegundos(driver, 1);

		// Rellenamos el formulario.
		PO_LoginView.fillForm(driver, "edward@gmail.com", "123456");
		// Comprobamos que entramos en sesion
		PO_View.checkElement(driver, "text", "Usuarios que se encuentran en el sistema");
		// Vamos a crear una publicacion
		driver.navigate().to("http://localhost:8090/publication/create");
		// rellenamos el formulario de la publicacion
		PO_PublicationView.fillForm(driver, "Prueba titulo", "Prueba texto");

		// Comprobamos que está creada
		PO_View.checkElement(driver, "text", "Prueba texto");

	}

	/**
	 * 10.1 [LisPubVal] Acceso al listado de publicaciones desde un usuario en
	 * sesión.
	 */
	@Test
	public void PR10_1() {
		// Vamos al formulario de login
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		SeleniumUtils.esperarSegundos(driver, 1);

		// Rellenamos el formulario.
		PO_LoginView.fillForm(driver, "edward@gmail.com", "123456");
		// Comprobamos que entramos en sesion
		PO_View.checkElement(driver, "text", "Usuarios que se encuentran en el sistema");
		// Vamos a crear una publicacion
		driver.navigate().to("http://localhost:8090/publication/create");
		// rellenamos el formulario de la publicacion
		PO_PublicationView.fillForm(driver, "Prueba titulo", "Prueba texto");
		// Hemos creado la publicacion podemos ir a comprobar si tenemos acceso a la url
		// y comprobar que esta creada
		driver.navigate().to("http://localhost:8090/publication/list");
		// Comprobamos que está creada
		PO_View.checkElement(driver, "text", "Prueba texto");

	}

	/**
	 * 11.1 [LisPubAmiVal] Listar las publicaciones de un usuario amigo
	 */
	@Test
	public void PR11_1() {
		// Vamos al formulario de login
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		SeleniumUtils.esperarSegundos(driver, 1);

		// Rellenamos el formulario.
		PO_LoginView.fillForm(driver, "edward@gmail.com", "123456");
		// Comprobamos que entramos en sesion
		PO_View.checkElement(driver, "text", "Usuarios que se encuentran en el sistema");

		List<WebElement> elems = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elems.get(0).click();
		SeleniumUtils.esperarSegundos(driver, 1);

		// Invitamos a pelayoy@gmail.com a ser amigos de prueba@gmail.com
		elems = PO_View.checkElement(driver, "free", "//td[contains(text(), 'marta@gmail.com')]/following-sibling::"
				+ "*/button[contains(@id, 'invitationButton4')]");
		elems.get(0).click();

		SeleniumUtils.esperarSegundos(driver, 2);

		// Vamos a crear una publicacion
		driver.navigate().to("http://localhost:8090/publication/create");
		// rellenamos el formulario de la publicacion
		PO_PublicationView.fillForm(driver, "Prueba titulo 11", "Prueba texto 11");
		// Hemos creado la publicacion podemos ir a comprobar si tenemos acceso a la url
		// y comprobar que esta creada
		driver.navigate().to("http://localhost:8090/publication/list");
		// Comprobamos que está creada
		PO_View.checkElement(driver, "text", "Prueba texto 11");

		// Logout
		driver.navigate().to("http://localhost:8090/login?logout");

		PO_LoginView.fillForm(driver, "marta@gmail.com", "123456");

		// Vamos a comprobar si la invitacion se ha enviado vamos a la url
		driver.navigate().to("http://localhost:8090/invitation/list");

		// Comprobamos que estamos viendo las invitaciones
		PO_View.checkElement(driver, "text", "Lista de invitaciones");
		// Comprobamos que la invitacion se ha enviado vemos que prueba quiere ser amigo
		// de pelayo@gmail.com y la aceptamos
		elems = PO_View.checkElement(driver, "free", "//td[contains(text(), 'edward@gmail.com')]/following-sibling::"
				+ "*/button[contains(@id, 'friendButton6')]");
		elems.get(0).click();

		SeleniumUtils.esperarSegundos(driver, 2);
		// Vamos a la url de que muestra la lista de amigos
		driver.navigate().to("http://localhost:8090/friends/list");

		// Estamos viendo los amigos
		PO_View.checkElement(driver, "text", "Amigos");

		driver.navigate().to("http://localhost:8090/publication/list/6");

		PO_View.checkElement(driver, "text", "edward@gmail.com");
		PO_View.checkElement(driver, "text", "Prueba texto 11");

	}
	/**
	 * 11.2 [LisPubAmiInVal] Utilizando un acceso vía URL tratar de listar las publicaciones de un usuario que
no sea amigo del usuario identificado en sesión.
	 */
	@Test
	public void PR11_2() {
	
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		SeleniumUtils.esperarSegundos(driver, 1);

	
		PO_LoginView.fillForm(driver, "pedro@gmail.com", "123456");
		PO_View.checkElement(driver, "text", "Usuarios que se encuentran en el sistema");

		driver.navigate().to("http://localhost:8090/publication/list/2");
		
		PO_View.checkElement(driver, "text", "Bienvenidos a Red Social Genial");

		

	}
}
