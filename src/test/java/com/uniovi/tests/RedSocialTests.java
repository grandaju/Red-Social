package com.uniovi.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_NavView;
import com.uniovi.tests.pageobjects.PO_Properties;
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

	// 1.1 [RegVal] Registro de Usuario con datos válidos.
	@Test
	public void PR01() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "prueba@gmail.com", "Prueba", "Prueba", "123456", "123456");
		// comprobamos que entramos en Home
		PO_View.checkElement(driver, "text", "Bienvenidos a Red Social vital");
	}

	// 1.2 [RegInval] Registro de Usuario con datos inválidos (repetición de
	// contraseña invalida). 
	@Test
	public void PR02() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario pero la contraseña no coincide
		PO_RegisterView.fillForm(driver, "prueba2@gmail.com", "Prueba2", "Prueba2", "123456", "9999999");
		// comprobamos que se muestra el mensaje para la no coincidencia de contraseñas
		PO_View.checkElement(driver, "text", "Las contraseñas no coinciden");
	}

	// 2.1 [InVal] Inicio de sesión con datos válidos.
	@Test
	public void PR03() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_LoginView.fillForm(driver, "prueba@gmail.com", "123456");
		// Comprobamos que entramos en la sección del listado de usuarios
		PO_View.checkElement(driver, "text", "Usuarios que se encuentran en el sistema");
		

	}

	// 2.2 [InInVal] Inicio de sesión con datos inválidos (usuario no existente en
	// la aplicación).
	@Test
	public void PR04() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_LoginView.fillForm(driver, "NOEXISTE@gmail.com", "123456");
		// Comprobamos que entramos en la sección del listado de usuarios
		PO_View.checkElement(driver, "text", "Identificate");
	}

	// 3.1 [LisUsrVal] Acceso al listado de usuarios desde un usuario en sesión.
	@Test
	public void PR05() {
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
	public void PR06() {
		// Navegamos a la url /user/list
		driver.navigate().to("http://localhost:8090/user/list");
		// Ya que se trata de un usuario no identificado se redirige al login
		PO_View.checkElement(driver, "text", "Identificate");

	}

	// 4.1 [BusUsrVal] Realizar una búsqueda valida en el listado de usuarios desde
	// un usuario en sesión.
	@Test
	public void PR07() {
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
		PO_View.checkElement(driver, "free", "//td[contains(text(), 'prueba@gmail.com')]");
		// Volvemos a la primera página
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
		elementos.get(0).click();
		SeleniumUtils.esperarSegundos(driver, 1);

		// Buscamos un usuario
		PO_SearchUserView.fillSearch(driver, "prueba@gmail.com");
		SeleniumUtils.esperarSegundos(driver, 1);
		// Comprobamos que entramos en la sección del listado de usuarios
		PO_View.checkElement(driver, "free", "//td[contains(text(), 'prueba@gmail.com')]");

	}

	// 4.2 [BusUsrInVal] Intento de acceso con URL a la búsqueda de usuarios desde
	// un usuario no identificado. Debe producirse un acceso no permitido a vistas
	// privadas.
	@Test
	public void PR08() {
		// Navegamos a la url de la busqueda /user/list?searchText=prueba@gmail.com
		driver.navigate().to("http://localhost:8090/user/list?searchText=prueba@gmail.com");
		// Ya que se trata de un usuario no identificado se redirige al login
		PO_View.checkElement(driver, "text", "Identificate");

	}
	//5.1 [InvVal] Enviar una invitación de amistad a un usuario de forma valida.
	@Test
	public void PR09() {
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

		//Invitamos a pedro@gmail.com a ser amigos de prueba@gmail.com
		elems = PO_View.checkElement(driver, "free","//td[contains(text(), 'pedro@gmail.com')]/following-sibling::*/button[contains(@id, 'invitationButton1')]");
		elems.get(0).click();
		
		SeleniumUtils.esperarSegundos(driver, 2);
		//Logout
		driver.navigate().to("http://localhost:8090/login?logout");


		PO_LoginView.fillForm(driver, "pedro@gmail.com", "123456");
		
		
		//Vamos a comprobar si la invitacion se ha enviado vamos a la url
		driver.navigate().to("http://localhost:8090/invitation/list");

		//Comprobamos que estamos viendo las invitaciones 
		PO_View.checkElement(driver, "text", "Lista de invitaciones");
		//Comprobamos que la invitacion se ha enviado vemos que prueba quiere ser amigo de pedro@gmail.com
		PO_View.checkElement(driver, "free","//td[contains(text(), 'prueba@gmail.com')]");
		

	}
	
}
