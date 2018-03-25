package com.uniovi.tests;

import static org.junit.Assert.assertEquals;
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
import com.uniovi.tests.pageobjects.PO_NavView;
import com.uniovi.tests.pageobjects.PO_PostView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_SerachView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.util.SeleniumUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NotaneitorTests {

	// En Windows (Debe ser la versión 46.0 y desactivar las actualizacioens
	// automáticas)):
	// static String PathFirefox =
	// "C:\\Users\\Toshiba\\Documents\\aSDI\\Firefox46.0.win\\Firefox46.win\\FirefoxPortable.exe";
	static String PathFirefox = "C:/Users/Iván/Documents/Ingenieria Uniovi/Tercero/SDI/Firefox46.win/FirefoxPortable.exe";


	//En MACOSX (Debe ser la versión 46.0 y desactivar las actualizaciones automáticas):
	//static String PathFirefox = "/Applications/Firefox.app/Contents/MacOS/firefox-bin";
	//Común a Windows y a MACOSX
	static WebDriver driver = getDriver(PathFirefox);
	static String URL = "http://localhost:8090";

	public static WebDriver getDriver(String PathFirefox) {
		// Firefox (Versión 46.0) sin geckodriver para Selenium 2.x.
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	// Antes de cada prueba se navega al URL home de la aplicación
	@Before
	public void setUp() {
		driver.navigate().to(URL);
	}

	// Después de cada prueba se borran las cookies del navegador
	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	}

	// Antes de la primera prueba
	@BeforeClass
	static public void begin() {
	}

	// Al finalizar la última prueba
	@AfterClass
	static public void end() {
		// Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}

	// PR01.1. Prueba del formulario de registro. Registro con datos correctos
	@Test
	public void PR01_1RegVal() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "Juanjo@prueba.es", "Juanjo", "77777", "77777");
		// Comprobamos que entramos en la sección privada
		PO_View.checkElement(driver, "text", "Usuario autenticado como");
	}

	// PR01.2. Prueba del formulario de registro. DNI repetido en la BD, Nombre
	// corto, .... pagination
	// pagination-centered, Error.signup.email.length
	@Test
	public void PR01_2RegInVal() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "maria@prueba.es", "Maria", "77777", "77777");
		PO_View.getP();
		// Comprobamos el error de email repetido.
		PO_RegisterView.checkKey(driver, "Error.signup.email.duplicate", PO_Properties.getSPANISH());
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "chemaprueba.es", "Chema", "77777", "77777");
		// Comprobamos el error de email con formato incorrecto.
		PO_RegisterView.checkKey(driver, "Error.signup.email.form", PO_Properties.getSPANISH());
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "c@.es", "Chema", "77777", "77777");
		// Comprobamos el error de email con longitud inválida.
		PO_RegisterView.checkKey(driver, "Error.signup.email.length", PO_Properties.getSPANISH());
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "ccccccccccccccccccccc@prueba.es", "Chema", "77777", "77777");
		// Comprobamos el error de email con longitud inválida.
		PO_RegisterView.checkKey(driver, "Error.signup.email.length", PO_Properties.getSPANISH());
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "carlos@prueba.es", "Ca", "77777", "77777");
		// Comprobamos el error de nombre con longitud inválida.
		PO_RegisterView.checkKey(driver, "Error.signup.name.length", PO_Properties.getSPANISH());
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "carlos@prueba.es", "Ccaarrllooss Fernández Blanco", "77777", "77777");
		// Comprobamos el error de nombre con longitud inválida.
		PO_RegisterView.checkKey(driver, "Error.signup.name.length", PO_Properties.getSPANISH());
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "carlos@prueba.es", "Carlos Fernández", "7777", "7777");
		// Comprobamos el error de contraseña con longitud inválida.
		PO_RegisterView.checkKey(driver, "Error.signup.password.length", PO_Properties.getSPANISH());
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "carlos@prueba.es", "Carlos Fernández", "77778", "77779");
		// Comprobamos el error de contraseñas que no coinciden.
		PO_RegisterView.checkKey(driver, "Error.signup.passwordConfirm.coincidence", PO_Properties.getSPANISH());
	}

	// PR02.1. Loguearse con exito desde el ROl de Usuario, maria@prueba.es, 123456
	@Test
	public void PR02_1InVal() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "maria@prueba.es", "123456");
		// COmprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Lista de usuarios");
	}

	// PR02.2. Loguearse sin exito
	@Test
	public void PR02_2InInVal() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "maria@prueba.es", "12345678");
		// COmprobamos el error de identificación
		PO_RegisterView.checkKey(driver, "Error.login", PO_Properties.getSPANISH());
	}

	// PR03.1. Loguearse con exito desde el ROl de Usuario, maria@prueba.es, 123456
	// y
	// ver los usuarios registrados
	@Test
	public void PR03_1LisUsrVal() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "maria@prueba.es", "123456");
		// Comprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Lista de usuarios");
		// Contamos el número de filas de usuarios
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());
		assertTrue(elementos.size() == 5);
	}

	// PR03.2. Loguearse con exito desde el ROl de Usuario, maria@prueba.es, 123456
	// y
	// ver los usuarios registrados
	@Test
	public void PR03_2LisUsrInVal() {
		// Esperamos a aparezca la opción de listar usuarios: //a[contains(@href,
		// 'user/list')] pero no debe aparecer
		try {
			PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/list')]");
		} catch (TimeoutException e) {
		}
	}

	// PR04.1. Loguearse con exito desde el ROl de Usuario, maria@prueba.es, 123456
	// y
	// buscar a usuarios
	@Test
	public void PR04_1BusUsrVal() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "maria@prueba.es", "123456");
		// Comprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Lista de usuarios");
		// Buscamos la cadena "ma"
		PO_SerachView.fillForm(driver, "ma");
		// Contamos el número de filas de usuarios
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());
		assertTrue(elementos.size() == 2);
		// Comprobamos que nos aparecen dos usuarios maria@prueba.es y marta@prueba.es
		PO_View.checkElement(driver, "text", "maria@prueba.es");
		PO_View.checkElement(driver, "text", "marta@prueba.es");
	}

	// PR04.2. Intentamos acceder a /user/list y rellenar el input para realizar la
	// búsqueda y buscar
	@Test
	public void PR04_2BusUsrInVal() {
		try {
			PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/list')]");
			PO_SerachView.fillForm(driver, "ma");
		} catch (TimeoutException e) {
		}
	}

	// PR5.1. [InvVal] Enviar una invitación de amistad a un usuario de forma
	// valida.
	@Test
	public void PR05_1InvVal() {
		//Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		//Rellenamos el formulario
		PO_LoginView.fillForm(driver, "jorge@prueba.es" , "123456" );
		//Comprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Lista de usuarios");
		PO_NavView.clickOption(driver, "/user/list", "id", "sendButton4");
		List<WebElement> elemntos = PO_View.checkElement(driver, "id", "sendButton4");
		elemntos.get(0).click();
		PO_View.checkElement(driver, "id", "pSended4");

	}


	//PR5.2. [InvVal] Enviar una invitación de amistad a un usuario al que ya le habíamos invitado la invitación
	//previamente. No debería dejarnos enviar la invitación, se podría ocultar el botón de enviar invitación o
	//notificar que ya había sido enviada previamente.
	@Test
	public void PR05_2InvInVal() {
		//Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		//Rellenamos el formulario
		PO_LoginView.fillForm(driver, "jorge@prueba.es" , "123456" );
		//Comprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Lista de usuarios");
		PO_NavView.clickOption(driver, "/user/list", "id", "pSended4");
		List<WebElement> elemntos = PO_View.checkElement(driver, "id", "pSended4");
		assertTrue(elemntos.size()==1);
	}

	//		PR6.1 [LisInvVal] Listar las invitaciones recibidas por un usuario, realizar la comprobación con una lista
	//		que al menos tenga una invitación recibida.
	@Test
	public void PR06_1LisInvVal() {
		//Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		//Rellenamos el formulario
		PO_LoginView.fillForm(driver, "lucas@prueba.es" , "123456" );
		//Comprobamos que entramos en la pagina privada de Alumno
		PO_HomeView.clickOption(driver, "/invitation/list", "id", "invitaciones");
		List<WebElement> elemntos = PO_View.checkElement(driver, "class", "text-left");
		assertEquals(2, elemntos.size());

	}


	//PR7.1 [AcepInvVal] Aceptar una invitación recibida
	@Test
	public void PR07_1AcepInvVal() {
		//Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		//Rellenamos el formulario
		PO_LoginView.fillForm(driver, "lucas@prueba.es" , "123456" );
		//Comprobamos que entramos en la pagina privada de Alumno
		PO_NavView.clickOption(driver, "/invitation/list", "id", "invitaciones");
		List<WebElement> elemntos = PO_View.checkElement(driver, "class", "text-left");
		assertEquals(2, elemntos.size());
		PO_NavView.clickOption(driver, "/invitation/list", "id", "aceptButton1");
		elemntos= PO_View.checkElement(driver, "id", "aceptButton1");
		elemntos.get(0).click();

	}


	//8.1 [ListAmiVal] Listar los amigos de un usuario, realizar la comprobación con una lista que al menos tenga un amigo
	@Test
	public void PR08_1ListAmiVal() {
		//Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		//Rellenamos el formulario
		PO_LoginView.fillForm(driver, "marta@prueba.es" , "123456" );
		//Comprobamos que entramos en la pagina privada de Alumno

		PO_NavView.clickOption(driver, "/friends", "id", "amigos");
		List<WebElement> elemntos = PO_View.checkElement(driver, "class", "text-left");
		assertEquals(2, elemntos.size());

	}


	//PR9.1. Crear una publicación con datos válidos.
	@Test
	public void PR09_1PubVal() {
		// Hacemos login con éxito
		PR02_1InVal();
		// Accedemos a la viste de nueva publicación
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'post/add')]");
		elementos.get(0).click();
		// Comprobamos que accedemos a la vista
		PO_RegisterView.checkKey(driver, "post.add", PO_Properties.getSPANISH());
		// Rellenamos el formulario con datos válidos
		PO_PostView.fillForm(driver, "Nueva publicación 1", "Esta es mi primera publicación");
		// Comprobamos que nos redirige a home
		PO_View.checkElement(driver, "text", "Esta es mi primera publicación");
	}

	// PR10.1. Acceso al listado de publicaciones desde un usuario en sesión.
	@Test
	public void PR10_1LisPubVal() {
		// Hacemos login con éxito
		PR02_1InVal();
		// Accedemos a la viste de listar publicaciones
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'post/list')]");
		elementos.get(0).click();
		// Comprobamos que accedemos a la vista
		PO_RegisterView.checkKey(driver, "post.list", PO_Properties.getSPANISH());
		// Comprobamos que muestra las publicaciones
		PO_View.checkElement(driver, "text", "My first post");
		PO_View.checkElement(driver, "text", "My second post");
	}

	// 11.1 [LisPubAmiVal] Listar las publicaciones de un usuario amigo
	@Test
	public void PR11_1ListPubAmiVal() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "lucas@prueba.es", "123456");
		PO_NavView.clickOption(driver, "/friends", "id", "amigos");
		PO_NavView.clickOption(driver, "/post/1/list", "text", "Jorge Fernández");
	}

	// 11.2 [LisPubAmiInVal] Utilizando un acceso vía URL tratar de listar las
	// publicaciones de un usuario que
	// no sea amigo del usuario identificado en sesión
	@Test
	public void PR11_2ListPubAmiInVal() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "lucas@prueba.es", "123456");
		PO_NavView.clickOption(driver, "/friends", "id", "amigos");
		driver.navigate().to("http://localhost:8090/post/3/list");
		PO_View.checkElement(driver, "text", "Amigos");
	}

	// PR13.2. Inicio de sesión como administrador con datos válidos.
	@Test
	public void PR13_2AdInVal() {
		// Vamos al formulario de logueo de administrador
		driver.navigate().to("http://localhost:8090/admin/login");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "admin@prueba.es", "123456");
		// COmprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Administrador");
	}
	
	//12.1 [PubFot1Val] Crear una publicación con datos válidos y una foto adjunta.
	@Test
	public void PR12_1PubFot1Val() {
		// Hacemos login con éxito
				PR02_1InVal();
				// Accedemos a la viste de nueva publicación
				List<WebElement> elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'post/add')]");
				elementos.get(0).click();
				// Comprobamos que accedemos a la vista
				PO_RegisterView.checkKey(driver, "post.add", PO_Properties.getSPANISH());
				// Rellenamos el formulario con datos válidos
				PO_PostView.fillFormPhoto(driver, "Nueva publicación 3", "Esta es mi primera publicación con foto", "a.jpg");
				// Comprobamos que nos redirige a home
				PO_View.checkElement(driver, "text", "Esta es mi primera publicación con foto");
	}
	//12.1 [PubFot2Val] Crear una publicación con datos válidos y sin una foto adjunta.
	@Test
	public void PR12_1PubFot2Val() {
		PR09_1PubVal();
	}

	// PR13.2. Inicio de sesión como administrador con datos inválidos (usar los
	// datos de un usuario
	// que no tenga perfil administrador)
	@Test
	public void PR13_2AdInInVal() {
		// Vamos al formulario de logueo de administrador
		driver.navigate().to("http://localhost:8090/admin/login");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "maria@prueba.es", "123456");
		// COmprobamos el error de identificación
		PO_RegisterView.checkKey(driver, "Error.admin.login", PO_Properties.getSPANISH());
		// Comprobamos que no tenemos acceso a /user/list
		PR03_2LisUsrInVal();
	}
    
	//PR14.1 Desde un usuario identificado en sesión como administrador listar a todos los
	// usuarios de la aplicación.
	@Test
	public void PR14_1AdLisUsrVal() {
		//Entrar como administrador
		PR13_2AdInVal();
		//Contamos el número de filas de usuarios 
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free","//tbody/tr", PO_View.getTimeout());
		assertTrue(elementos.size() == 8);
	}

	// 15.1 [AdBorUsrVal] Desde un usuario identificado en sesión como administrador
	// eliminar un usuario
	// existente en la aplicación.
	@Test
	public void PR15_1AdBorUsrVal() {
		// Vamos al formulario de logueo de administrador
		driver.navigate().to("http://localhost:8090/admin/login");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "admin@prueba.es", "123456");
		List<WebElement> elemntos = PO_View.checkElement(driver, "id", "deleteButton1");
		elemntos.get(0).click();
		PO_NavView.clickOption(driver, "logout", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "jorge@prueba.es", "123456");
		PO_RegisterView.checkKey(driver, "Error.login", PO_Properties.getSPANISH());
	}
	// 15.2 [AdBorUsrInVal] Intento de acceso vía URL al borrado de un usuario
	// existente en la aplicación.
	// Debe utilizarse un usuario identificado en sesión pero que no tenga perfil de
	// administrador.
	@Test
	public void PR15_2AdBorUsrInVal() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_LoginView.fillForm(driver, "maria@prueba.es", "123456");
		//Borramos al usuario 1
		driver.navigate().to("http://localhost:8090/admin/edit/2/delete");
		PO_View.checkElement(driver, "text", "Access is denied");
		driver.navigate().back();
		PO_NavView.clickOption(driver, "logout", "class", "btn btn-primary");
		//intentamos intrar con el usuario 2 y lo conseguimos
		PO_LoginView.fillForm(driver, "lucas@prueba.es", "123456");
		PO_View.checkElement(driver, "text", "Lista de usuarios");
	}
}

