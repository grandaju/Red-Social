package com.uniovi.tests.pageobjects;

import static org.junit.Assert.assertTrue;
import java.util.List;
import org.openqa.selenium.*;
import com.uniovi.tests.util.SeleniumUtils;

public class PO_HomeView extends PO_View {

	static public void checkWelcome(WebDriver driver, int language) {
		// Esperamos a que se cargue el saludo de bienvenida en Español
		SeleniumUtils.EsperaCargaPagina(driver, "text", p.getString("welcome.message", PO_Properties.SPANISH),
				getTimeout());
	}

	static public void checkChangeIdiom(WebDriver driver, String textIdiom1, String textIdiom2, int locale1,
			int locale2) {
		// Esperamos a que se cargue el saludo de bienvenida en Español
		PO_HomeView.checkWelcome(driver, locale1);
		// Cambiamos a segundo idioma
		PO_HomeView.changeIdiom(driver, textIdiom2);
		// COmprobamos que el texto de bienvenida haya cambiado a segundo idioma
		PO_HomeView.checkWelcome(driver, locale2);
		// Volvemos a Español.
		PO_HomeView.changeIdiom(driver, textIdiom1);
		// Esperamos a que se cargue el saludo de bienvenida en Español
		PO_HomeView.checkWelcome(driver, locale1);
	}

	/**
	 * Selecciona el enlace de idioma correspondiente al texto textLanguage
	 * 
	 * @param driver:
	 *            apuntando al navegador abierto actualmente.
	 * @param textLanguage:
	 *            el texto que aparece en el enlace de idioma ("English" o
	 *            "Spanish")
	 */
	public static void changeIdiom(WebDriver driver, String textLanguage) {
		// clickamos la opción Idioma.
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "btnLanguage", getTimeout());
		elementos.get(0).click();
		// Esperamos a que aparezca el menú de opciones.
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "languageDropdownMenuButton", getTimeout());
		// SeleniumUtils.esperarSegundos(driver, 2);
		// CLickamos la opción Inglés partiendo de la opción Español
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", textLanguage, getTimeout());
		elementos.get(0).click();
	}

}
