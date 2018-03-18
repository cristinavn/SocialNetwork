package com.uniovi.tests;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_PostView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_SerachView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.util.SeleniumUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NotaneitorTests {


	//En Windows (Debe ser la versión 46.0 y desactivar las actualizacioens automáticas)):
	static String PathFirefox = "C:\\Users\\Toshiba\\Documents\\aSDI\\Firefox46.0.win\\Firefox46.win\\FirefoxPortable.exe";
	//En MACOSX (Debe ser la versión 46.0 y desactivar las actualizaciones automáticas):
	//static String PathFirefox = "/Applications/Firefox.app/Contents/MacOS/firefox-bin";
	//Común a Windows y a MACOSX
	static WebDriver driver = getDriver(PathFirefox);
	static String URL = "http://localhost:8090";
	public static WebDriver getDriver(String PathFirefox) {
		//Firefox (Versión 46.0) sin geckodriver para Selenium 2.x.
		System.setProperty("webdriver.firefox.bin",PathFirefox);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	//Antes de cada prueba se navega al URL home de la aplicación
	@Before
	public void setUp(){
		driver.navigate().to(URL);
	}

	//Después de cada prueba se borran las cookies del navegador
	@After
	public void tearDown(){
		driver.manage().deleteAllCookies();
	}

	//Antes de la primera prueba
	@BeforeClass
	static public void begin() {
	}

	//Al finalizar la última prueba
	@AfterClass
	static public void end() {
		//Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}



	//PR01.1. Prueba del formulario de registro. Registro con datos correctos
	@Test
	public void RegVal() {
		//Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		//Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "Juanjo@prueba.es", "Juanjo", "77777",
				"77777");
		//Comprobamos que entramos en la sección privada
		PO_View.checkElement(driver, "text", "Usuario autenticado como");
	}

	//PR01.2. Prueba del formulario de registro. DNI repetido en la BD, Nombre corto, .... pagination
	//pagination-centered, Error.signup.email.length
	@Test
	public void RegInVal() {
		//Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		//Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "maria@prueba.es", "Maria", "77777", "77777");
		PO_View.getP();
		//Comprobamos el error de email repetido.
		PO_RegisterView.checkKey(driver, "Error.signup.email.duplicate",
				PO_Properties.getSPANISH() );
		//Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "chemaprueba.es", "Chema", "77777",
				"77777");
		//Comprobamos el error de email con formato incorrecto.
		PO_RegisterView.checkKey(driver, "Error.signup.email.form",
				PO_Properties.getSPANISH() );
		//Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "c@.es", "Chema", "77777",
				"77777");
		//Comprobamos el error de email con longitud inválida.
		PO_RegisterView.checkKey(driver, "Error.signup.email.length",
				PO_Properties.getSPANISH() );
		//Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "ccccccccccccccccccccc@prueba.es", "Chema", 
				"77777","77777");
		//Comprobamos el error de email con longitud inválida.
		PO_RegisterView.checkKey(driver, "Error.signup.email.length",
				PO_Properties.getSPANISH() );
		//Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "carlos@prueba.es", "Ca", 
				"77777","77777");
		//Comprobamos el error de nombre con longitud inválida.
		PO_RegisterView.checkKey(driver, "Error.signup.name.length",
				PO_Properties.getSPANISH() );
		//Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "carlos@prueba.es", "Ccaarrllooss Fernández Blanco", 
				"77777","77777");
		//Comprobamos el error de nombre con longitud inválida.
		PO_RegisterView.checkKey(driver, "Error.signup.name.length",
				PO_Properties.getSPANISH() );
		//Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "carlos@prueba.es", "Carlos Fernández", 
				"7777","7777");
		//Comprobamos el error de contraseña con longitud inválida.
		PO_RegisterView.checkKey(driver, "Error.signup.password.length",
				PO_Properties.getSPANISH() );
		//Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "carlos@prueba.es", "Carlos Fernández", 
				"77778","77779");
		//Comprobamos el error de contraseñas que no coinciden.
		PO_RegisterView.checkKey(driver, "Error.signup.passwordConfirm.coincidence",
				PO_Properties.getSPANISH() );
	}

	//PR02.1. Loguearse con exito desde el ROl de Usuario, maria@prueba.es, 123456
	@Test
	public void InVal() {
		//Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		//Rellenamos el formulario
		PO_LoginView.fillForm(driver, "maria@prueba.es" , "123456" );
		//COmprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Lista de usuarios");
	}

	//PR02.2. Loguearse sin exito 
	@Test
	public void InInVal() {
		//Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		//Rellenamos el formulario
		PO_LoginView.fillForm(driver, "maria@prueba.es" , "12345678" );
		//COmprobamos el error de identificación
		PO_RegisterView.checkKey(driver, "Error.login",
				PO_Properties.getSPANISH() );
	}

	//PR03.1. Loguearse con exito desde el ROl de Usuario, maria@prueba.es, 123456 y
	//ver los usuarios registrados
	@Test
	public void LisUsrVal() {
		//Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		//Rellenamos el formulario
		PO_LoginView.fillForm(driver, "maria@prueba.es" , "123456" );
		//Comprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Lista de usuarios");
		//Contamos el número de filas de usuarios
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free","//tbody/tr", PO_View.getTimeout());
		assertTrue(elementos.size() == 5);	
	}

	//PR03.2. Loguearse con exito desde el ROl de Usuario, maria@prueba.es, 123456 y
	//ver los usuarios registrados
	@Test
	public void LisUsrInVal() {
		//Esperamos a aparezca la opción de listar usuarios: //a[contains(@href, 'user/list')] pero no debe aparecer
		try{
			PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/list')]");
		}catch (TimeoutException e) {}
	}

	//PR04.1. Loguearse con exito desde el ROl de Usuario, maria@prueba.es, 123456 y
	//buscar a usuarios 
	@Test
	public void BusUsrVal() {
		//Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		//Rellenamos el formulario
		PO_LoginView.fillForm(driver, "maria@prueba.es" , "123456" );
		//Comprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Lista de usuarios");
		//Buscamos la cadena "ma"
		PO_SerachView.fillForm(driver, "ma");
		//Contamos el número de filas de usuarios 
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free","//tbody/tr", PO_View.getTimeout());
		assertTrue(elementos.size() == 2);	
		//Comprobamos que nos aparecen dos usuarios maria@prueba.es y marta@prueba.es
		PO_View.checkElement(driver, "text", "maria@prueba.es");
		PO_View.checkElement(driver, "text", "marta@prueba.es");
	}

	//PR04.2. Intentamos acceder a /user/list y rellenar el input para realizar la búsqueda y buscar
	@Test
	public void BusUsrInVal() {
		try {
			PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/list')]");
			PO_SerachView.fillForm(driver, "ma");
		}catch (TimeoutException e) {}
	}


	//PR9.1. Crear una publicación con datos válidos.
	@Test
	public void PubVal() {
		// Hacemos login con éxito
		InVal();
		//Accedemos a la viste de nueva publicación
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'post/add')]");
		elementos.get(0).click();
		//Comprobamos que accedemos a la vista
		PO_RegisterView.checkKey(driver, "post.add",PO_Properties.getSPANISH() );
		//Rellenamos el formulario con datos válidos
		PO_PostView.fillForm(driver, "Nueva publicación 1", "Esta es mi primera publicación");
		//Comprobamos que nos redirige a home
		PO_View.checkElement(driver, "text", "Esta es mi primera publicación");
	}

	//PR10.1.  Acceso al listado de publicaciones desde un usuario en sesión.
	@Test
	public void LisPubVal() {
		// Hacemos login con éxito
		InVal();
		//Accedemos a la viste de listar publicaciones
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'post/list')]");
		elementos.get(0).click();
		//Comprobamos que accedemos a la vista
		PO_RegisterView.checkKey(driver, "post.list",PO_Properties.getSPANISH() );
		//Comprobamos que muestra las publicaciones
		PO_View.checkElement(driver, "text", "My first post");
		PO_View.checkElement(driver, "text", "My second post");
	}

	//PR13.2. Inicio de sesión como administrador con datos válidos.
	@Test
	public void AdInVal() {
		//Vamos al formulario de logueo de administrador
		driver.navigate().to("http://localhost:8090/admin/login");
		//Rellenamos el formulario
		PO_LoginView.fillForm(driver, "admin@prueba.es" , "123456" );
		//COmprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Administrador");
	}

	//PR13.2. Inicio de sesión como administrador con datos inválidos (usar los datos de un usuario
	// que no tenga perfil administrador)
	@Test
	public void AdInInVal() {
		//Vamos al formulario de logueo de administrador
		driver.navigate().to("http://localhost:8090/admin/login");
		//Rellenamos el formulario
		PO_LoginView.fillForm(driver, "maria@prueba.es" , "123456" );
		//COmprobamos el error de identificación
		PO_RegisterView.checkKey(driver, "Error.admin.login",
				PO_Properties.getSPANISH() );
		//Comprobamos que no tenemos acceso a /user/list
		LisUsrInVal();
	}
	/*
	//PR13. Loguearse como estudiante y ver los detalles de la nota con Descripcion = Nota A2.
	//P13. Ver la lista de Notas.
	@Test
	public void PR13() {
		//Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		//Rellenamos el formulario
		PO_LoginView.fillForm(driver, "99999990A" , "123456" );
		//COmprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Notas del usuario");
		SeleniumUtils.esperarSegundos(driver, 1);
		//Contamos las notas
		By enlace = By.xpath("//td[contains(text(), 'Nota A2')]/followingsibling::*[2]");
		driver.findElement(enlace).click();
		SeleniumUtils.esperarSegundos(driver, 1);
		//Esperamos por la ventana de detalle
		PO_View.checkElement(driver, "text", "Detalles de la nota");
		SeleniumUtils.esperarSegundos(driver, 1);
		//Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate");
	}

	//P14. Loguearse como profesor y Agregar Nota A2.
	//P14. Esta prueba podría encapsularse mejor ...
	@Test
	public void PR14() {
		//Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		//Rellenamos el formulario
		PO_LoginView.fillForm(driver, "99999993D" , "123456" );
		//COmprobamos que entramos en la pagina privada del Profesor
		PO_View.checkElement(driver, "text", "99999993D");
		//Pinchamos en la opción de menu de Notas: //li[contains(@id, 'marks-menu')]/a
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'marks-menu')]/a");
		elementos.get(0).click();
		//Esperamos a aparezca la opción de añadir nota: //a[contains(@href, 'mark/add')]
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'mark/add')]");
		//Pinchamos en agregar Nota.
		elementos.get(0).click();
		//Ahora vamos a rellenar la nota. //option[contains(@value, '4')]
		PO_PrivateView.fillFormAddMark(driver, 3, "Nota Nueva 1", "8");
		//Esperamos a que se muestren los enlaces de paginación la lista de notas
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
		//Nos vamos a la última página
		elementos.get(3).click();
		//Comprobamos que aparece la nota en la pagina
		elementos = PO_View.checkElement(driver, "text", "Nota Nueva 1");
		//Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate");
	}

	//PRN. Loguearse como profesor, vamos a la ultima página y Eliminamos la Nota Nueva 1.
	//PRN. Ver la lista de Notas.
	@Test
	public void PR15() {
		//Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		//Rellenamos el formulario
		PO_LoginView.fillForm(driver, "99999993D" , "123456" );
		//COmprobamos que entramos en la pagina privada del Profesor
		PO_View.checkElement(driver, "text", "99999993D");
		//Pinchamos en la opción de menu de Notas: //li[contains(@id, 'marks-menu')]/a
		List<WebElement> elementos = PO_View.checkElement(driver, "free",
				"//li[contains(@id, 'marks-menu')]/a");
		elementos.get(0).click();
		//Pinchamos en la opción de lista de notas.
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href,'mark/list')]");
		elementos.get(0).click();
		//Esperamos a que se muestren los enlaces de paginacion la lista de notas
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'pagelink')]");
		//Nos vamos a la última página
		elementos.get(3).click();
		//Esperamos a que aparezca la Nueva nota en la ultima pagina
		//Y Pinchamos en el enlace de borrado de la Nota "Nota Nueva 1"
		//td[contains(text(), 'Nota Nueva 1')]/following-sibling::a[contains(text(),'mark/delete')]"
		elementos = PO_View.checkElement(driver, "free", "//td[contains(text(), 'Nota Nueva 1')]/following-sibling::a[contains(@href, 'mark/delete')]");
		elementos.get(0).click();
		//Volvemos a la última pagina
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'pagelink')]");
		elementos.get(3).click();
		//Y esperamos a que NO aparezca la ultima "Nueva Nota 1"
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Nota Nueva 1",PO_View.getTimeout() );
		//Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate");
	}
	 */



}
