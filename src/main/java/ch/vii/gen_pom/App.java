package ch.vii.gen_pom;

import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.model.Build;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;

public class App {
	public static void main(String[] args) throws Exception {

		/*
		 * <plugin> <groupId>org.openapitools</groupId>
		 * <artifactId>openapi-generator-maven-plugin</artifactId>
		 * <version>${openapi-generator-maven-plugin-version}</version> <executions>
		 * <execution> <goals> <goal>generate</goal> </goals> <configuration>
		 * <inputSpec>${project.basedir}/src/main/resources/yaml/yamlfilename.yaml</
		 * inputSpec> <!-- language file, like e.g. JavaJaxRSCodegen -->
		 * <generatorName>com.my.package.for.GeneratorLanguage</generatorName>
		 * <templateDirectory>myTemplateDir</templateDirectory>
		 * 
		 * <output>${project.build.directory}/generated-sources</output>
		 * <apiPackage>${default.package}.handler</apiPackage>
		 * <modelPackage>${default.package}.model</modelPackage>
		 * <invokerPackage>${default.package}.handler</invokerPackage> </configuration>
		 * </execution> </executions>
		 * 
		 * <dependencies> <dependency> <groupId>com.my.generator</groupId>
		 * <artifactId>customgenerator</artifactId> <version>1.0-SNAPSHOT</version>
		 * </dependency> </dependencies> </plugin>
		 */

		String openapiGeneratorMavenPluginVersion = "3.3.4";
		Model model = new Model();
		Writer writer = new FileWriter("./target/pom.xml");
		List<Dependency> dependencyList = new ArrayList<Dependency>();

		model.setGroupId("TestGroupArtifactID");
		model.setArtifactId("TestGroupArtifactName");
		model.setVersion("1.0.0");
		model.setModelVersion("4.0.0");

		Dependency dep = new Dependency();
		dep.setGroupId("com.my.generator");
		dep.setArtifactId("customgenerator");
		dep.setVersion("1.0.0");
		dependencyList.add(dep);

		Build build = new Build();
		Plugin plugin = new Plugin();
		plugin.setGroupId("org.openapitools");
		plugin.setArtifactId("openapi-generator-maven-plugin");
		plugin.setVersion(openapiGeneratorMavenPluginVersion);
		PluginExecution pluginexecution = new PluginExecution();

		pluginexecution.addGoal("generate");

		Configuration configuration = new Configuration("configuration");

		configuration.addChild("inputSpec", "${project.basedir}/src/main/resources/yaml/yamlfilename.yaml");
		configuration.addChild("generatorName", "com.my.package.for.GeneratorLanguage");
		configuration.addChild("templateDirectory", "com.my.package.for.GeneratorLanguage");
		configuration.addChild("output", "${project.build.directory}/generated-sources");
		configuration.addChild("apiPackage", "${default.package}.handle");
		configuration.addChild("modelPackage", "${default.package}.model");
		configuration.addChild("invokerPackage", "${default.package}.handler");

		pluginexecution.setConfiguration(configuration);

		plugin.addExecution(pluginexecution);

		plugin.setDependencies(dependencyList);

		build.addPlugin(plugin);
		model.setBuild(build);

		new MavenXpp3Writer().write(writer, model);
		writer.close();

	}

}

class Configuration extends org.codehaus.plexus.util.xml.Xpp3Dom {

	private static final long serialVersionUID = 1L;

	public Configuration(String name) {
		super(name);
	}

	public Configuration(String name, String value) {
		super(name);
		setValue(value);
	}

	public void addChild(String name, String value) {
		Configuration generatorName = new Configuration(name, value);
		addChild(generatorName);
	}

}
