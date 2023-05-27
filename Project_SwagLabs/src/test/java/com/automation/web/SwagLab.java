package com.automation.web;
import org.testng.annotations.Test;
import java.text.SimpleDateFormat;
import org.apache.log4j.Logger;
import com.automation.baseClass.BaseClass;
import com.automation.scenarios.SwagLabScenarios;
import com.automation.supportLibraries.Log4jUtil;

public class SwagLab extends BaseClass {
	Logger log = Log4jUtil.loadLogger(SwagLab.class);
	SwagLabScenarios slScenario = new SwagLabScenarios();

	@Test(priority=0)
	public void SwagLabDemo()  throws Exception {
		scenarioName = new Exception().getStackTrace()[0].getMethodName();
		slScenario.orderInitiation(scenarioName);
		}
}

