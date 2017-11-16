package com.lisyx.extendreports;

import static org.testng.Assert.fail;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;
import static org.testng.Assert.fail;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
public class pcThree {


	public static 	ExtentReports extent;
	public static 	ExtentTest logger;
	/*---------------Primeras Validaciones web -------------------*/
	/*Pantalla cotizacion*/
	private WebDriver driver;
	private WebDriver driverPcTwo;
	private String baseUrl;
	private String baseUrlTwo;

	private String StrFirsCoberturaMateriales="";
	private String StrFirsCoberturaTotales="";
	private String StrFirsCoberturaResponsabilidadCivilLuc="";
	private String StrFirsCoberturaAsistenciaJuridica="";
	private String StrFirsCoberturaGastosMedicos="";
	private String StrFirsCoberturaResponsabilidadCivilMuerte="";
	private String StrFirsCoberturaAsistenciaMedica="";
	private String  numeroSeriePcOne="";
	private String rfcOne="";
	/* Calculo de la cotizacion*/
	private String StrFirsCoberturaMaterialesCot="";
	private String StrFirsCoberturaTotalesCot="";
	private String StrFirsCoberturaResponsabilidadCivilLucCot="";
	private String StrFirsCoberturaAsistenciaJuridicaCot="";
	private String StrFirsCoberturaGastosMedicosCot="";
	private String StrFirsCoberturaResponsabilidadCivilMuerteCot="";
	private String StrFirsCoberturaAsistenciaMedicaCot="";
	//--boleanos que indican si el test paso 
	boolean equalMateriales=false;
	boolean equalRoboTotal=false;
	boolean equalResponsabilidadCivilLuc=false;
	boolean equalAsistenciaJuridica=false;
	boolean equalGastosMedicosOcupantes=false;
	boolean equalResponsabilidadCivilExcesoPorMuerte=false;
	boolean equalAsistenciaMedica=false;

	static String filename = "config.properties";

	static InputStream input =null;


	private String strCliente = "";
	private String strTarifa = "";
	private String strIva = "";
	private String strDanos = "";
	private String strRobo = "";
	private String strRes = "";
	private String strAsis = "";
	private String strGas = "";
	private String strResMue = "";
	private String strAsisMed = "";
	private String strPlacas = "";
	private String strMotor = "";
	private String strPago = "";

	boolean equalsCliente = false;
	boolean equalsTarifa = false;
	boolean equalsIva = false;
	boolean equalsDanos = false;
	boolean equalsRobo = false;
	boolean equalsRes = false;
	boolean equalsAsis = false;
	boolean equalsGas = false;
	boolean equalsResMue = false;
	boolean equalsAsisMed = false;
	boolean equalsPlacas = false;
	boolean equalsMotor = false;
	boolean equalsPago = false;

	private String strClienteCob = "";
	private String strTarifaCob = "";
	private String strIvaCob = "";
	private String strDanosCob = "";
	private String strRoboCob = "";
	private String strResCob = "";
	private String strAsisCob = "";
	private String strGasCob = "";
	private String strResMueCob = "";
	private String strAsisMedCob = "";
	private String strPlacasCob = "";
	private String strMotorCob = "";
	private String strPagoCob = "";
	static Properties prop = new Properties();

	static Logger log = LoggerFactory.getLogger(pcThree.class);

	@BeforeTest
	public void startReportTwo() throws MalformedURLException{
		//ExtentReports(String filePath,Boolean replaceExisting) 
		//filepath - path of the file, in .htm or .html format - path where your report needs to generate. 
		//replaceExisting - Setting to overwrite (TRUE) the existing file or append to it
		//True (default): the file will be replaced with brand new markup, and all existing data will be lost. Use this option to create a brand new report
		//False: existing data will remain, new tests will be appended to the existing report. If the the supplied path does not exist, a new file will be created.
		//extent = new ExtentReports ("C:\\wselenium\\reporting\\STMExtentReport.html", false);
		
		extent = new ExtentReports("C:\\wselenium\\reporting\\HDIReport_three.html", false);
		//extent.addSystemInfo("Environment","Environment Name")
		extent
		.addSystemInfo("Host Name", "SoftwareTestingMaterial")
		.addSystemInfo("Environment", "Automation Testing")
		.addSystemInfo("User Name", "Leonardo Chávez");


		//loading the external xml file (i.e., extent-config.xml) which was placed under the base directory
		//You could find the xml file below. Create xml file in your project and copy past the code mentioned below
		extent.loadConfig(new File("C:\\Nuevo\\ExtendReportsMaven\\extent-config.xml"));
		//extent.loadConfig(new File("C:\\Users\\Administrator\\Downloads\\ExtendReportsMaven\\ExtendReportsMaven\\extent-config.xml"));

		baseUrl = "https://autos.staging.hdi.com.mx/";
		try{
			ClassLoader cLoader = pcThree.class.getClassLoader();

			input = cLoader.getResourceAsStream(filename);
			log.info("Path: "+input.getClass().getSimpleName());
			prop.load(input);
		}
		catch(Exception ex)
		{
			System.out.println("Error: "+ex.getMessage());
		}
		//System.setProperty("webdriver.gecko.driver", driverPath+"geckodriver");
		DesiredCapabilities capability =null;
		DesiredCapabilities capabilityPcTwo =null;

		String browser=prop.getProperty("config.browser");
		//String driverRoute = prop.getProperty("config.driverRoute");
		String driverRoute="C:\\geckodriver.exe";
		String browserPcTwo=prop.getProperty("config.browser");
		String driverRoutePcTwo = prop.getProperty("config.driverRoutet");
		switch (browser) {

		case  "firefox":
			capability= DesiredCapabilities.firefox();
			System.setProperty("webdriver.gecko.driver", driverRoute);

			break;

		case  "chrome":
			capability= DesiredCapabilities.chrome();
			System.setProperty("webdriver.chrome.driver", driverRoute);

			break;
		case  "internet explorer":
			capability= DesiredCapabilities.internetExplorer();

			break;

		default:
			capability= DesiredCapabilities.firefox();

			break;

		}


		capability.setBrowserName(browser);
		capability.setPlatform(Platform.WIN8);



		String ruta= prop.getProperty("config.urltr");

		numeroSeriePcOne=prop.getProperty("config.txtNumeroSerieTh");
		rfcOne=prop.getProperty("config.rfc");

		//	driver = nesw RemoteWebDriver(new URL("http://192.168.0.107:5556/wd/hub"), capability);
		driver = new RemoteWebDriver(new URL(ruta), capability);

		//s	driverPcTwo.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);


	}
	@Test
	public void CotizadorTestTwo()  {
		try{
		baseUrl = "https://autos.staging.hdi.com.mx";

		//generarReporteExterno();
		logger = extent.startTest("Cotizador Autos HDI PC Three");
		//generarReporteExterno();
		driver.get(baseUrl + "/Paginas/Login/InicioSesion.aspx");
		driver.findElement(By.id("UserName")).clear();
		driver.findElement(By.id("UserName")).sendKeys("430003354");
		Thread.sleep(3000);
		driver.findElement(By.id("Password")).clear();



		driver.findElement(By.id("Password")).sendKeys("Pa55word");

		driver.findElement(By.id("Login")).click();
		Thread.sleep(3000);
		Thread.sleep(3000);

		driver.findElement(By.xpath("(//a[contains(text(),'Cotizador Web')])[2]")).click();
		Thread.sleep(8000);

		driver.findElement(By.id("txtCodigoPostal")).click();
		Thread.sleep(1000);

		driver.findElement(By.id("txtCodigoPostal")).clear();
		Thread.sleep(1000);

		driver.findElement(By.id("txtCodigoPostal")).sendKeys("37218");
		//  driver.findElement(By.id("txtCodigoPostal")).sendKeys("37218");
		Thread.sleep(3000);
		driver.findElement(By.id("3select2ss")).click();

		// ERROR: Caught exception [ERROR: Unsupported command [fireEvent | id=txtCodigoPostal | blur]]
		for(int second = 0 ; second <= 60 ; second  ++)
		{
			if (second >= 60) fail("timeout");
			try { if ("LEON".equalsIgnoreCase(driver.findElement(By.id("3select2ss")).getText())) break; } catch (Exception e) {}
			System.out.println("intento: "+second);
			Thread.sleep(1000);
		}
		Thread.sleep(5000);

		driver.findElement(By.id("btnTipoVehiculoGlm")).click();
		Thread.sleep(4000);

		driver.findElement(By.linkText("VEHICULOS RESIDENTES")).click();
		Thread.sleep(4000);
		driver.findElement(By.linkText("FORD")).click();
		Thread.sleep(3000);

		driver.findElement(By.linkText("2014")).click();
		Thread.sleep(3000);

		driver.findElement(By.linkText("ESCAPE")).click();
		Thread.sleep(3000);

		driver.findElement(By.linkText("2.0L TITANIUM AT\\A")).click();
		Thread.sleep(8000);

		driver.findElement(By.id("liPaquetesModulos")).click();
		Thread.sleep(7000);

		driver.findElement(By.id("liPaquetesTradicionales")).click();
		Thread.sleep(7000);
		try {
			//Danos materiales
			StrFirsCoberturaMateriales=	driver.findElement(By.id("spanSA_233")).getText(); }
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		//Robo total
		try {StrFirsCoberturaTotales=driver.findElement(By.id("spanSA_236")).getText().replaceAll("\\s+","");
		System.out.println("Cobertura por robo toal: "+StrFirsCoberturaTotales);

		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		//Asistencia juridica
		try{ StrFirsCoberturaAsistenciaJuridica=	driver.findElement(By.className("spanLabel")).getText();
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		//Responsabilidad civil luc
		try {StrFirsCoberturaResponsabilidadCivilLuc=driver.findElement(By.cssSelector("#comboSA_253 > select")).getText();

		System.out.println("El valor de la cobertura por daños materiales es: "+StrFirsCoberturaMateriales);

		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		//Responsabilidad civil en exceso por muerte a personas
		try {
			StrFirsCoberturaResponsabilidadCivilMuerte=driver.findElement(By.cssSelector("#comboSA_366 > select")).getText();

			System.out.println("El valor de la cobertura Responsabilidad civil "
					+ "en exceso por muerte a personas: "+StrFirsCoberturaResponsabilidadCivilMuerte);

		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		//Asistencia medica
		try {
			StrFirsCoberturaGastosMedicos=driver.findElement(By.cssSelector("#comboSA_239 > select")).getText();

			System.out.println("El valor gastos Medicos por ocupante: "+StrFirsCoberturaGastosMedicos);

		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		//Gastos medicos por ocupante
		try {
			StrFirsCoberturaAsistenciaMedica=driver.findElement(By.id("spanSA_269")).getText();

			System.out.println("El valor de la asistencia medica "
					+ "en exceso por muerte a personas: "+StrFirsCoberturaAsistenciaMedica);

		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		Thread.sleep(4000);

		driver.findElement(By.cssSelector("#spanMod_1 > label.hdi-checked.check")).click();
		Thread.sleep(2000);
		//Pantallazo
		/*	try{
					driver.findElement(By.id("txtSumaAsegurada")).click();
				     Thread.sleep(2000);
					File scr=((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
			        File dest= new File("/Users/leonardo/Downloads/screenshot_1.png");
			        FileUtils.copyFile(scr, dest);
			        Thread.sleep(3000);

				}
				catch(Exception ex){
					System.out.println("Error al tratar de genenrar pantallazo: "+ex.getMessage());
				}*/
		driver.findElement(By.id("btnTarificar")).click();
		Thread.sleep(7000);

		//Cobertura materiales cotizador
		try{

			StrFirsCoberturaMaterialesCot= driver.findElement(By.xpath("//table[@id='TablaDetalles2']/tbody/tr[2]/td[2]")).getText();
			System.out.println("El valor de la cobertura por daños materiales en la cotizacion es "+StrFirsCoberturaMaterialesCot);

		}
		catch(Exception ex)
		{
			System.out.println("Error tabla"+ex.getMessage());
		}
		//Cobertura robo total cotizador
		try{

			StrFirsCoberturaTotalesCot= driver.findElement(By.xpath("//table[@id='TablaDetalles2']/tbody/tr[3]/td[2]")).getText();
			System.out.println("El valor de la cobertura por robo total en la cotizacion es "+StrFirsCoberturaTotalesCot);

		}
		catch(Exception ex)
		{
			System.out.println("Error tabla"+ex.getMessage());
		}
		//Cobertura Asistencia Juridica cotizador
		try{

			StrFirsCoberturaAsistenciaJuridicaCot= driver.findElement(By.xpath("//table[@id='TablaDetalles2']/tbody/tr[9]/td[2]")).getText();
			System.out.println("El valor de la cobertura por Asistencia Juridica en la cotizacion es "+StrFirsCoberturaAsistenciaJuridicaCot);

		}
		catch(Exception ex)
		{
			System.out.println("Error tabla"+ex.getMessage());
		}
		//Cobertura Responsabilidad Civil Luc cotizador
		try{

			StrFirsCoberturaResponsabilidadCivilLucCot= driver.findElement(By.xpath("//table[@id='TablaDetalles2']/tbody/tr[6]/td[2]")).getText();
			System.out.println("El valor de la cobertura por Responsabilidad Civil Luc en la cotizacion es "+StrFirsCoberturaResponsabilidadCivilLucCot);

		}
		catch(Exception ex)
		{
			System.out.println("Error tabla"+ex.getMessage());
		}
		//Cobertura Responsabilidad civil en exceso por muerte a personas cotizador
		try{

			StrFirsCoberturaResponsabilidadCivilMuerteCot= driver.findElement(By.xpath("//table[@id='TablaDetalles2']/tbody/tr[6]/td[2]")).getText();
			System.out.println("El valor de la cobertura por Responsabilidad civil en exceso por muerte en la cotizacion es "+StrFirsCoberturaResponsabilidadCivilMuerteCot);

		}
		catch(Exception ex)
		{
			System.out.println("Error tabla"+ex.getMessage());
		}

		try{

			StrFirsCoberturaAsistenciaMedicaCot= driver.findElement(By.xpath("//table[@id='TablaDetalles2']/tbody/tr[6]/td[3]")).getText();
			System.out.println("El valor de gastos medicos por ocupante en la cotizacion es "+StrFirsCoberturaAsistenciaMedicaCot);

		}
		catch(Exception ex)
		{
			System.out.println("Error tabla"+ex.getMessage());
		}
		Thread.sleep(4000);

		//Setteamos el resultado de las evaluaciones
		equalMateriales =StrFirsCoberturaMateriales.equalsIgnoreCase(StrFirsCoberturaMaterialesCot);
		equalAsistenciaJuridica=StrFirsCoberturaAsistenciaJuridicaCot.equalsIgnoreCase(StrFirsCoberturaAsistenciaJuridicaCot);
		equalResponsabilidadCivilExcesoPorMuerte=StrFirsCoberturaResponsabilidadCivilMuerteCot.equalsIgnoreCase(StrFirsCoberturaResponsabilidadCivilMuerteCot);
		equalRoboTotal=StrFirsCoberturaTotales.equalsIgnoreCase(StrFirsCoberturaTotalesCot);
		equalResponsabilidadCivilLuc=StrFirsCoberturaResponsabilidadCivilLuc.equalsIgnoreCase(StrFirsCoberturaResponsabilidadCivilLucCot);

		Thread.sleep(3000);
		driver.findElement(By.id("IconoSolicitaPoliza")).click();
		Thread.sleep(22000);

		driver.findElement(By.id("btnConfirmarAutorizador")).click();
		Thread.sleep(10000);
		driver.findElement(By.id("txtFolioChecador")).clear();
		driver.findElement(By.id("txtFolioChecador")).sendKeys("1717171717");
		Thread.sleep(3000);
		driver.findElement(By.id("txtNumeroSerie")).clear();
		driver.findElement(By.id("txtNumeroSerie")).sendKeys(numeroSeriePcOne);
		Thread.sleep(7000);

		driver.findElement(By.id("btnValidarNumeroSerie")).click();
		Thread.sleep(7000);

		driver.findElement(By.id("txtRFC")).clear();

		driver.findElement(By.id("txtRFC")).sendKeys(rfcOne);

		//    driver.findElement(By.id("txtRFC")).sendKeys("EIRA830305CX1");
		Thread.sleep(3000);

		driver.findElement(By.id("btnBuscarCliente")).click();
		Thread.sleep(7000);

		driver.findElement(By.id("btnAceptarClientes")).click();
		Thread.sleep(5000);
		//Actions action = new Actions(driver);

		//action.sendKeys(Keys.ESCAPE).perform();
		/*driver.findElement(By.cssSelector("div.modal-dialog > div.modal-content > div.modal-header > button.close")).click();



	    Thread.sleep(5000);*/
		driver.findElement(By.xpath("(//button[@type='button'])[213]")).click();
		//	driver.findElement(By.xpath("(//button[@type='button'])[1052]")).click();

		Thread.sleep(5000);
		driver.findElement(By.cssSelector("i.fa.fa-usd")).click();
		Thread.sleep(7000);

		driver.findElement(By.cssSelector("i.fa.fa-file-text-o")).click();
		Thread.sleep(10000);

		WebElement frame = driver.findElement(By.id("RptViewer"));
		Thread.sleep(12000);

		driver.switchTo().frame(frame);
		Thread.sleep(11000);

		String text = driver.findElement(By.className("textLayer")).getText();
		Thread.sleep(12000);

		text = text.replace("\n", "");

		validatePdf(text);

		driver.switchTo().defaultContent();

		try {
			strClienteCob = driver.findElement(By.id("lblNombreCliente")).getText();

			System.out.println("Cliente: " + strClienteCob);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		try {
			strTarifaCob = driver.findElement(By.id("lblPrimaNeta-Totales")).getText().replace("$", "");

//			System.out.println("Tarifa: " + strTarifaCob);
		} catch (Exception ex) {
			logger.log(LogStatus.FAIL,ex.getMessage());
			System.out.println(ex.getMessage());
		}

		try {
			strIvaCob = driver.findElement(By.id("lblIVA-Totales")).getText().replace("$", "");

			System.out.println("Iva: " + strIvaCob);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		try {
			Select select = new Select(driver.findElement(By.id("cmbTipoPago")));
			strPagoCob = select.getFirstSelectedOption().getText();

			System.out.println("Pago: " + strPagoCob);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		try {
			strDanosCob = driver.findElement(By.id("txtSumaAseguradaCobertura-233")).getAttribute("value").replace("$", "");

			System.out.println("Daños Materiales: " + strDanosCob);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		try {
			strRoboCob = driver.findElement(By.id("txtSumaAseguradaCobertura-236")).getAttribute("value").replace("$", "");

			System.out.println("Robo Total: " + strRoboCob);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		try {
			Select select = new Select(driver.findElement(By.id("cmbSumaAseguradaCobertura-239")));
			strGasCob = select.getFirstSelectedOption().getText().replace("$", "");

			System.out.println("Gastos Médicos: " + strGasCob);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		try {
			Select select = new Select(driver.findElement(By.id("cmbSumaAseguradaCobertura-253")));
			strResCob = select.getFirstSelectedOption().getText().replace("$", "");

			System.out.println("Responsabilidad: " + strResCob);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		try {
			strAsisCob = driver.findElement(By.id("lblSumaAseguradaCobertura-242")).getText();

			System.out.println("Asistencia Jurídica: " + strAsisCob);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		try {
			Select select = new Select(driver.findElement(By.id("cmbSumaAseguradaCobertura-366")));
			strResMueCob = select.getFirstSelectedOption().getText().replace("$", "");

			System.out.println("Responsabilidad Muertos: " + strResMueCob);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		try {
			strAsisMedCob = driver.findElement(By.id("lblSumaAseguradaCobertura-269")).getText();

			System.out.println("Asistencia Médica: " + strAsisMedCob);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		try {
			strPlacasCob = driver.findElement(By.id("txtNumeroPlacas")).getText();

			System.out.println("Placas: " + strPlacasCob);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		try {
			strMotorCob = driver.findElement(By.id("txtNumeroMotor")).getText();

			System.out.println("No. Motor: " + strMotorCob);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		equalsCliente = strClienteCob.equalsIgnoreCase(strCliente);
		equalsTarifa = strTarifaCob.equals(strTarifa);
		equalsIva = strIvaCob.equals(strIva);
		equalsDanos = strDanosCob.equals(strDanos);
		equalsRobo = strRoboCob.equals(strRobo);
		equalsRes = strResCob.equals(strRes);
		equalsAsis = strAsisCob.equalsIgnoreCase(strAsis);
		equalsGas = strGasCob.equals(strGas);
		equalsResMue = strResMueCob.equals(strResMue);
		equalsAsisMed = strAsisMedCob.equalsIgnoreCase(strAsisMed);
		equalsPlacas = strPlacasCob.equalsIgnoreCase(strPlacas);
		equalsMotor = strMotorCob.equalsIgnoreCase(strMotor);
		equalsPago = strPagoCob.equalsIgnoreCase(strPago);

		Thread.sleep(5000);

		driver.findElement(By.cssSelector("#VPHeader > input.hdi-btn-red")).click();
		Thread.sleep(5000);

		driver.findElement(By.cssSelector("button.close")).click();
		}
		catch(Exception ex){
			logger.log(LogStatus.FAIL,ex.getMessage());

		}
		//generarPDF();
	}

	@AfterMethod
	public void getResultTwo(ITestResult result){
		//if(result.getStatus() == ITestResult.FAILURE){
			/*Cobertura materiales*/
			if (StrFirsCoberturaMateriales=="" || StrFirsCoberturaMaterialesCot =="")
			{
				logger.log(LogStatus.SKIP, "Cobertura de materiales no evaluado");

			}
			else
			{

				if(equalMateriales)
					logger.log(LogStatus.PASS, "Cobertura de materiales correcta");
				else
					logger.log(LogStatus.FAIL, "Cobertura de materiales incorrecta la cobertura en web es de \n: "+StrFirsCoberturaMateriales+"\n y la cobertura en cotizador es \n: "+StrFirsCoberturaMaterialesCot);
			}
			/*Cobertura por robo total*/
			if (StrFirsCoberturaTotales=="" || StrFirsCoberturaTotalesCot =="")
			{
				logger.log(LogStatus.SKIP, "Cobertura  por robo total no evaluado");

			}
			else
			{
				if(equalRoboTotal)
					logger.log(LogStatus.PASS, "Cobertura de robo total correcta");
				else
					logger.log(LogStatus.FAIL, "Cobertura de robo total incorrecta la cobertura en web es de : \n  "+StrFirsCoberturaTotales+"\n y la cobertura en cotizador es : \n"+StrFirsCoberturaTotalesCot);

			}
			/*Cobertura responsabilidad civil luc*/
			/*if("".equals(StrFirsCoberturaResponsabilidadCivilLuc) || "".equals(StrFirsCoberturaResponsabilidadCivilLucCot))
			{
				logger.log(LogStatus.SKIP, "Cobertura  Responsabilidad Civil Luc no evaluado");

			}
			else
			{
				if(equalResponsabilidadCivilLuc)
					logger.log(LogStatus.PASS, "Cobertura de responsabilidad civil luc correcta");
				else
					logger.log(LogStatus.FAIL, "Cobertura de responsabilidad civil luc incorrecta la cobertura en web es de \n: "+StrFirsCoberturaResponsabilidadCivilLuc+"\n y la cobertura en cotizador es \n: "+StrFirsCoberturaResponsabilidadCivilLuc );

			}*/

			/*Cobertura responsabilidad civil luc*/
			if("".equals(StrFirsCoberturaResponsabilidadCivilMuerte) || "".equals(StrFirsCoberturaResponsabilidadCivilMuerteCot))
			{
				logger.log(LogStatus.SKIP, "Cobertura  Responsabilidad Civil Luc no evaluado");

			}
			else
			{
				if(equalResponsabilidadCivilExcesoPorMuerte)
					logger.log(LogStatus.PASS, "Cobertura de Responsabilidad Civil Exceso Por Muerte correcta");
				else
					logger.log(LogStatus.FAIL, "Cobertura de Responsabilidad Civil Exceso Por Muerte luc incorrecta la cobertura en web es de \n: "+StrFirsCoberturaResponsabilidadCivilMuerte+"\n y la cobertura en cotizador es \n: "+StrFirsCoberturaResponsabilidadCivilMuerteCot);

			}
			/*Gastos medicos por ocupante*/


			if("".equals(StrFirsCoberturaAsistenciaMedica) || "".equals(StrFirsCoberturaAsistenciaMedicaCot))
			{
				logger.log(LogStatus.SKIP, "Cobertura Gastos medicos por habitante no es posible evaluarla");

			}
			else
			{
				if(equalResponsabilidadCivilExcesoPorMuerte)
					logger.log(LogStatus.PASS, "Cobertura de Gastos medicos por habitante correcta");
				else
					logger.log(LogStatus.FAIL, "Cobertura de Gastos medicos por habitante incorrecta la cobertura en web es de \n: "+StrFirsCoberturaAsistenciaMedica+"\n y la cobertura en cotizador es \n: "+StrFirsCoberturaAsistenciaMedicaCot);

			}

			// un comentario con una linea bien larga ---------------------------------------------------------------------

			if (strClienteCob == "" || strCliente == "")
			{
				logger.log(LogStatus.SKIP, "Nombre del cliente no evaluado");

			}
			else
			{

				if(equalsCliente)
					logger.log(LogStatus.PASS, "Nombre de cliente correcto");
				else
					logger.log(LogStatus.FAIL, "Nombre de cliente incorrecto el nombre en web es \n: "+ strClienteCob +"\n y el nombre en el pdf es \n: "+ strCliente);
			}

			if (strTarifaCob == "" || strTarifa == "")
			{
				logger.log(LogStatus.SKIP, "Tarifa no evaluada");

			}
			else
			{

				if(equalsTarifa)
					logger.log(LogStatus.PASS, "Tarifa correcta");
				else
					logger.log(LogStatus.FAIL, "Tarifa incorrecta la tarifa en web es \n: "+ strTarifaCob +"\n y la tarifa en el pdf es \n: "+ strTarifa);
			}

			if (strIvaCob == "" || strIva == "")
			{
				logger.log(LogStatus.SKIP, "Iva no evaluado");

			}
			else
			{

				if(equalsIva)
					logger.log(LogStatus.PASS, "Iva correcto");
				else
					logger.log(LogStatus.FAIL, "Iva incorrecto el iva en web es \n: "+ strIvaCob +"\n y el iva en el pdf es \n: "+ strIva);
			}

			if (strDanosCob == "" || strDanos == "")
			{
				logger.log(LogStatus.SKIP, "Daños no evaluado");

			}
			else
			{

				if(equalsIva)
					logger.log(LogStatus.PASS, "Daños correcto");
				else
					logger.log(LogStatus.FAIL, "Daños incorrecto el monto en web es \n: "+ strDanosCob +"\n y el monto en el pdf es \n: "+ strDanos);
			}

			if (strRoboCob == "" || strRobo == "")
			{
				logger.log(LogStatus.SKIP, "Robo no evaluado");

			}
			else
			{

				if(equalsRobo)
					logger.log(LogStatus.PASS, "Robo correcto");
				else
					logger.log(LogStatus.FAIL, "Robo incorrecto el monto en web es \n: "+ strRoboCob +"\n y el monto en el pdf es \n: "+ strRobo);
			}

			if (strResCob == "" || strRes == "")
			{
				logger.log(LogStatus.SKIP, "Responsabilidad Civil no evaluada");

			}
			else
			{

				if(equalsRes)
					logger.log(LogStatus.PASS, "Responsabilidad civil correcta");
				else
					logger.log(LogStatus.FAIL, "Responsabilidad civil incorrecta el monto en web es \n: "+ strResCob +"\n y el monto en el pdf es \n: "+ strRes);
			}

			if (strAsisCob == "" || strAsis == "")
			{
				logger.log(LogStatus.SKIP, "Asistencia jurídica no evaluada");

			}
			else
			{

				if(equalsAsis)
					logger.log(LogStatus.PASS, "Asistencia jurídica correcta");
				else
					logger.log(LogStatus.FAIL, "Asistencia jurídica incorrecta la asistencia en web es \n: "+ strAsisCob +"\n y la asistencia en el pdf es \n: "+ strAsis);
			}

			if (strGasCob == "" || strGas == "")
			{
				logger.log(LogStatus.SKIP, "Gastos Médicos no evaluados");

			}
			else
			{

				if(equalsGas)
					logger.log(LogStatus.PASS, "Gastos Médicos correctos");
				else
					logger.log(LogStatus.FAIL, "Gastos Médicos incorrectos el monto en web es \n: "+ strGasCob +"\n y el monto en el pdf es \n: "+ strGas);
			}

			if (strResMueCob == "" || strResMue == "")
			{
				logger.log(LogStatus.SKIP, "Responsabilidad civil en exceso por muerte a personas no evaluada");

			}
			else
			{

				if(equalsResMue)
					logger.log(LogStatus.PASS, "Responsabilidad civil en exceso por muerte a personas correcta");
				else
					logger.log(LogStatus.FAIL, "Responsabilidad civil en exceso por muerte a personas incorrecta el monto en web es \n: "+ strResMueCob +"\n y el monto en el pdf es \n: "+ strResMue);
			}

			if (strAsisMedCob == "" || strAsisMed == "")
			{
				logger.log(LogStatus.SKIP, "Asistencia médica no evaluada");

			}
			else
			{

				if(equalsAsisMed)
					logger.log(LogStatus.PASS, "Asistencia médica correcta");
				else
					logger.log(LogStatus.FAIL, "Asistencia médica incorrecta la asistencia en web es \n: "+ strAsisMedCob +"\n y la asistencia en el pdf es \n: "+ strAsisMed);
			}

			if (strPlacasCob == "" || strPlacas == "")
			{
				logger.log(LogStatus.SKIP, "Placas no evaluadas");

			}
			else
			{

				if(equalsPlacas)
					logger.log(LogStatus.PASS, "Placas correctas");
				else
					logger.log(LogStatus.FAIL, "Placas incorrectas las placas en web es \n: "+ strPlacasCob +"\n y laa placas en el pdf es \n: "+ strPlacas);
			}

			if (strMotorCob == "" || strMotor == "")
			{
				logger.log(LogStatus.SKIP, "No. Motor no evaluado");

			}
			else
			{

				if(equalsMotor)
					logger.log(LogStatus.PASS, "No. Motor correcto");
				else
					logger.log(LogStatus.FAIL, "No. Motor incorrecto el número en web es \n: "+ strMotorCob +"\n y el número en el pdf es \n: "+ strMotor);
			}

			if (strPagoCob == "" || strPago == "")
			{
				logger.log(LogStatus.SKIP, "Pago no evaluado");

			}
			else
			{

				if(equalsPago)
					logger.log(LogStatus.PASS, "Pago correcto");
				else
					logger.log(LogStatus.FAIL, "Pago incorrecto el pago en web es \n: "+ strPagoCob +"\n y el pago en el pdf es \n: "+ strPago);
			}


			/*	logger.log(LogStatus.PASS, "Test Case Logger is passTest");

			logger.log(LogStatus.FAIL, "Test Case Failed is "+result.getName());
			logger.log(LogStatus.FAIL, "Test Case Failed is "+result.getThrowable());*/



	//	}else 

		if(result.getStatus() == ITestResult.SKIP){
			logger.log(LogStatus.SKIP, "Test Case Skipped is "+result.getName());
		}



		// ending test
		//endTest(logger) : It ends the current test and prepares to create HTML report
	
		//extent.endTest(logger);
		//driver.close();
		extent.endTest(logger);
		extent.flush();
		
	}
	@AfterTest
	public void endReportTwo(){
		// writing everything to document
		//flush() - to write or update test information to your report. 
	
		//Call close() at the very end of your session to clear all resources. 
		//If any of your test ended abruptly causing any side-affects (not all logs sent to ExtentReports, information missing), this method will ensure that the test is still appended to the report with a warning message.
		//You should call close() only once, at the very end (in @AfterSuite for example) as it closes the underlying stream. 
		//Once this method is called, calling any Extent method will throw an error.
		//close() - To close all the operation
	//	extent.endTest(logger);
	
		//extent.flush();
		extent.close();
		driver.close();
		//driver.close();
	}

	public void validatePdf (String st) {
		try{		    
			Matcher matcher = Pattern.compile("Prima\\s+?Neta([+-]?[0-9]{1,3}(?:,?[0-9]{3})*(?:\\.[0-9]{2})?)").matcher(st);
			if (matcher.find()) {
				strTarifa = matcher.group(1);
				System.out.println("Tarifa: " + strTarifa);
			}
			matcher = Pattern.compile("PÓLIZA(.+)RFC").matcher(st);
			if (matcher.find()) {
				strCliente = matcher.group(1).trim();
				System.out.println("Cliente: " + strCliente);
			}
			matcher = Pattern.compile("I\\.V\\.A\\.([+-]?[0-9]{1,3}(?:,?[0-9]{3})*(?:\\.[0-9]{2})?)").matcher(st);
			if (matcher.find()) {
				strIva = matcher.group(1);
				System.out.println("I.V.A.: " + strIva);
			}
			matcher = Pattern.compile("([+-]?[0-9]{1,3}(?:,?[0-9]{3})*(?:\\.[0-9]{2})?)\\d+?\\%?Daños\\s+?Materiales").matcher(st);
			if (matcher.find()) {
				strDanos = matcher.group(1);
				System.out.println("Daños: " + strDanos);
			}
			matcher = Pattern.compile("([+-]?[0-9]{1,3}(?:,?[0-9]{3})*(?:\\.[0-9]{2})?)\\d+?\\%Robo\\s+?Total").matcher(st);
			if (matcher.find()) {
				strRobo = matcher.group(1);
				System.out.println("Robo: " + strRobo);
			}
			matcher = Pattern.compile("([+-]?[0-9]{1,3}(?:,?[0-9]{3})*(?:\\.[0-9]{2})?)Responsabilidad\\s+?Civil\\s+?\\(").matcher(st);
			if (matcher.find()) {
				strRes = matcher.group(1);
				System.out.println("Responsavilidad Civil: " + strRes);
			}
			matcher = Pattern.compile("(No\\s+?\\w+|\\w+)Asistencia\\s+?Jurídica").matcher(st);
			if (matcher.find()) {
				strAsis = matcher.group(1);
				System.out.println("Asistencia Jurídica: " + strAsis);
			}
			matcher = Pattern.compile("([+-]?[0-9]{1,3}(?:,?[0-9]{3})*(?:\\.[0-9]{2})?)Gastos\\s+?Médicos\\s+?Ocupantes").matcher(st);
			if (matcher.find()) {
				strGas = matcher.group(1);
				System.out.println("Gastos: " + strGas);
			}
			matcher = Pattern.compile("([+-]?[0-9]{1,3}(?:,?[0-9]{3})*(?:\\.[0-9]{2})?)Responsabilidad\\s+?Civil\\s+?Exceso\\s+?por\\s+?Muerte").matcher(st);
			if (matcher.find()) {
				strResMue = matcher.group(1);
				System.out.println("Responsabilidad por Muerte: " + strResMue);
			}
			matcher = Pattern.compile("(No\\s+?\\w+|\\w+)Asistencia\\s+?Médica").matcher(st);
			if (matcher.find()) {
				strAsisMed = matcher.group(1);
				System.out.println("Asistencia Médica: " + strAsisMed);
			}
			matcher = Pattern.compile("Placas:(.+)Núm").matcher(st);
			if (matcher.find()) {
				strPlacas = matcher.group(1).trim();
				System.out.println("Placas: " + strPlacas);
			}
			matcher = Pattern.compile("Núm\\.\\s+?de\\s+?Motor:(.+)Ver").matcher(st);
			if (matcher.find()) {
				strMotor = matcher.group(1).trim();
				System.out.println("Motor: " + strMotor);
			}
			matcher = Pattern.compile("(ANUAL|SEMESTRAL|TRIMESTRAL|MENSUAL|12\\s+?MESES|6\\s+?MESES).+Datos\\s+?Pago").matcher(st);
			if (matcher.find()) {
				strPago = matcher.group(1);
				System.out.println("Pago: " + strPago);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
