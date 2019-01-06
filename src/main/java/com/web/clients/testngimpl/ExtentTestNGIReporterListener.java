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

	// 生成的路径以及文件名
	private static final String OUTPUT_FOLDER = "test-output/";
	private static final String FILE_NAME = "index.html";

	// 实例化一个ExtentReports
	private ExtentReports extentReports;

	// 测试类、节点对应关系
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
			// 获取每一个suite里的测试结果
			Map<String, ISuiteResult> result = suite.getResults();

			// 如果suite里面没有任何用例，直接跳过，不在报告里生成
			if (result.size() == 0) {
				continue;
			}
			// 统计suite下的成功、失败、跳过的总用例数
			int suiteFailSize = 0;
			int suitePassSize = 0;
			int suiteSkipSize = 0;

			// 存在多个suite的情况下，在报告中将同一个suite的测试结果归为一类，创建一级节点。
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
				// 测试结果上下文(TestRunner)
				ITestContext context = r.getTestContext();
				if (createSuiteResultNode) {
					// 没有创建suite的情况下，将在SuiteResult的创建为一级节点，否则创建为suite的一个子节点。
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

					// 设置左侧栏节点名称--》父节点
					resultNode.getModel().setName(suiteName + " : " + contextName);
					if (resultNode.getModel().hasCategory()) {
						resultNode.assignCategory(contextName);
					} else {
						resultNode.assignCategory(suiteName, contextName);
					}

					Date startDate = r.getTestContext().getStartDate();

					Date endDate = r.getTestContext().getEndDate();

					resultNode.getModel().setStartTime(startDate);
					resultNode.getModel().setEndTime(endDate);

					// 统计SuiteResult下的数据
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
		htmlReporter.config().setDocumentTitle("API Reporter");
		htmlReporter.config().setReportName("API Reporter -" + System.currentTimeMillis());
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
		// 存在父节点时，获取父节点的标签
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
			// 调整用例排序，按时间排序
			Set<ITestResult> treeSet = new TreeSet<ITestResult>();
			treeSet.addAll(tests.getAllResults());
			for (ITestResult result : treeSet) {
				Object[] parameters = result.getParameters();
				// 定义节点名称
				String name = "";
				String className = "";
				for (Object param : parameters) {
					name += "-" + param.toString();
				}
				if (name.length() > 0) {
					if (name.length() > 50) {
						name = name.substring(0, 49) + "...";
					}
					// 修改成：使用方法名+参数名称作为标签名
					name = result.getMethod().getMethodName().toString() + name;
					className = result.getMethod().getTestClass().getName();
				} else {
					name = result.getMethod().getMethodName();
					className = result.getMethod().getTestClass().getName();
				}
				if (extenttest == null) {
					if (!this.parentTestMap.containsKey(className)) {
						// 创建父节点
						test = this.extentReports.createTest(className);
						this.parentTestMap.put(className, test);
						// 创建子节点
						test = test.createNode(name);
					} else {
						// 创建子节点
						test = this.parentTestMap.get(className).createNode(name);
					}
				} else {
					// 在父节点下创建子节点
					test = extenttest.createNode(className);
					// test.assignCategory(className);
					test = test.createNode(name);

				}
				for (String group : result.getMethod().getGroups())
					test.assignCategory(group);

				// 在子节点下，把需要的日志、截图等信息添加到子节点中
				List<String> outputList = Reporter.getOutput(result);
				for (String output : outputList) {
					// 将用例的log输出报告中
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
