package com.web.clients.testngimpl;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.xml.XmlSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.ResourceCDN;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.TestAttribute;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

/**
 * 实现testNG的报告监听器，需要在window-preferences-testNG中添加监听器：com.web.clients.util.
 * ExtentTestNGIReporterListener
 * 
 * 监听器可以加在xml中或者在测试类的的前边直接使用@Listeners({ ExtentTestNGIReporterListener.class })
 * 
 *
 */
public class ExtentTestNGIReporterListener implements IReporter {

	private static final String OUTPUT_FOLDER = "test-output/";
	private static final String FILE_NAME = "index.html";

	private ExtentReports extentReports;

	private HashMap<String, ExtentTest> parentTestMap = new HashMap<>(512);

	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		init();
		boolean createSuiteNode = false;
		if (suites.size() > 1) {
			createSuiteNode = true;
		}
		// 遍历测试集：suites
		for (ISuite suite : suites) {
			Map<String, ISuiteResult> result = suite.getResults();

			if (result.size() == 0) {
				continue;
			}
			int suiteFailSize = 0;
			int suitePassSize = 0;
			int suiteSkipSize = 0;

			ExtentTest suiteTest = null;
			if (createSuiteNode) {
				suiteTest = this.extentReports.createTest(suite.getName()).assignCategory(suite.getName());
			}
			boolean createSuiteResultNode = false;
			if (result.size() > 1) {
				createSuiteResultNode = true;
			}
			for (ISuiteResult r : result.values()) {
				ExtentTest resultNode;
				ITestContext context = r.getTestContext();
				if (createSuiteResultNode) {
					if (null == suiteTest) {
						resultNode = this.extentReports.createTest(r.getTestContext().getName());
					} else {
						resultNode = suiteTest.createNode(r.getTestContext().getName());
					}
				} else {
					resultNode = suiteTest;
				}
				if (resultNode != null) {

					String suiteName = suite.getName();
					String contextName = r.getTestContext().getName();

					resultNode.getModel().setName(contextName);
					if (resultNode.getModel().hasCategory()) {
						resultNode.assignCategory(contextName);
					} else {
						resultNode.assignCategory(suiteName, contextName);
					}

					Date startDate = r.getTestContext().getStartDate();

					Date endDate = r.getTestContext().getEndDate();

					resultNode.getModel().setStartTime(startDate);
					resultNode.getModel().setEndTime(endDate);

					int passSize = r.getTestContext().getPassedTests().size();
					int failSize = r.getTestContext().getFailedTests().size();
					int skipSize = r.getTestContext().getSkippedTests().size();
					suitePassSize += passSize;
					suiteFailSize += failSize;
					suiteSkipSize += skipSize;
					if (failSize > 0) {
						resultNode.getModel().setStatus(Status.FAIL);
					}
					resultNode.getModel().setDescription(
							String.format("Pass: %s ; Fail: %s ; Skip: %s ;", passSize, failSize, skipSize));
				}

				buildTestNodes(resultNode, context.getFailedTests(), Status.FAIL);
				buildTestNodes(resultNode, context.getSkippedTests(), Status.SKIP);
				buildTestNodes(resultNode, context.getPassedTests(), Status.PASS);
			}
			if (suiteTest != null) {
				suiteTest.getModel().setDescription(
						String.format("Pass: %s ; Fail: %s ; Skip: %s ;", suitePassSize, suiteFailSize, suiteSkipSize));
				if (suiteFailSize > 0) {
					suiteTest.getModel().setStatus(Status.FAIL);
				}
			}

		}

		this.extentReports.flush();
	}

	private void init() {
		File reportDir = new File(OUTPUT_FOLDER);
		if (!reportDir.exists() && !reportDir.isDirectory()) {
			reportDir.mkdir();
		}
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(OUTPUT_FOLDER + FILE_NAME);
		htmlReporter.config().setEncoding("GBK");
		htmlReporter.config().setDocumentTitle("接口测试报告");
		htmlReporter.config().setReportName("测试报告 -" + System.currentTimeMillis());
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setCSS(
				".node.level-1  ul{ display:none;} .node.level-1.active ul{display:block;}  .card-panel.environment  th:first-child{ width:30%;}");
		htmlReporter.config().setJS("$(window).off(\"keydown\");");
		htmlReporter.config().setResourceCDN(ResourceCDN.EXTENTREPORTS);

		this.extentReports = new ExtentReports();
		this.extentReports.attachReporter(htmlReporter);
		this.extentReports.setReportUsesManualConfiguration(true);
	}

	/**
	 * 处理pass、fail、skip掉的test开头的方法
	 * 
	 * @param extenttest
	 * @param tests
	 * @param status
	 */
	private void buildTestNodes(ExtentTest extenttest, IResultMap tests, Status status) {
		String[] categories = new String[0];
		if (extenttest != null) {
			List<TestAttribute> categoryList = extenttest.getModel().getCategoryContext().getAll();
			categories = new String[categoryList.size()];
			for (int index = 0; index < categoryList.size(); index++) {
				String catName = categoryList.get(index).getName();
				categories[index] = catName;
			}
		}

		ExtentTest test = null;
		if (tests.size() > 0) {
			Set<ITestResult> treeSet = new TreeSet<ITestResult>();
			treeSet.addAll(tests.getAllResults());
			for (ITestResult result : treeSet) {
				Object[] parameters = result.getParameters();
				String name = "";
				String className = "";
				for (Object param : parameters) {
					name += "-" + param.toString();
				}
				if (name.length() > 0) {
					if (name.length() > 50) {
						name = name.substring(0, 49) + "...";
					}
					name = result.getMethod().getMethodName().toString() + name;
					className = result.getMethod().getTestClass().getName();
				} else {
					name = result.getMethod().getMethodName();
					className = result.getMethod().getTestClass().getName();
				}
				if (extenttest == null) {
					if (!this.parentTestMap.containsKey(className)) {
						test = this.extentReports.createTest(className);
						this.parentTestMap.put(className, test);
						test = test.createNode(name);
					} else {
						test = this.parentTestMap.get(className).createNode(name);
					}
				} else {
					if (!this.parentTestMap.containsKey(className)) {
						test = extenttest.createNode(className);
						this.parentTestMap.put(className, test);
						test = test.createNode(name);
					} else {
						test = this.parentTestMap.get(className).createNode(name);
					}

				}
				for (String group : result.getMethod().getGroups())
					test.assignCategory(group);

				List<String> outputList = Reporter.getOutput(result);
				for (String output : outputList) {
					test.debug(output);
				}
				if (result.getThrowable() != null) {
					test.log(status, result.getThrowable());
				} else {
					test.log(status, name + "-" + status.toString().toLowerCase() + "ed");
				}

				Long resultStartTime = result.getStartMillis();
				Long resultEndTime = result.getEndMillis();

				test.getModel().setStartTime(getTime(resultStartTime));
				test.getModel().setEndTime(getTime(resultEndTime));
			}
		}
	}

	private Date getTime(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}
}
