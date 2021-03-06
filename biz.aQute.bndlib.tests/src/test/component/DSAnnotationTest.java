package test.component;

import java.io.*;
import java.util.*;

import javax.xml.xpath.*;

import org.osgi.framework.*;
import org.osgi.service.component.*;
import org.osgi.service.component.annotations.*;
import org.osgi.service.log.*;

import aQute.bnd.component.*;
import aQute.bnd.osgi.*;
import aQute.bnd.test.*;

/**
 * #118
 */
public class DSAnnotationTest extends BndTestCase {

	/**
	 * Property test
	 */

	@Component(xmlns= "http://www.osgi.org/xmlns/scr/v1.1.0", 
			property = {
			"x:Integer=3.0", "a=1", "a=2", "b=1", "boolean:Boolean=true", "byte:Byte=1", "char:Character=1",
			"short:Short=3", "integer:Integer=3", "long:Long=3", "float:Float=3.0", "double:Double=3e7",
			"string:String=%", "wrongInteger:Integer=blabla", "\n\r\t \u0343\u0344\u0345\u0346\n:Integer=3"
	})
	public static class PropertiesTestx {

	}

	public static void testProperties() throws Exception {
		Builder b = new Builder();
		b.setProperty("-dsannotations", "test.component.*x");
		b.setProperty("Private-Package", "test.component");
		b.addClasspath(new File("bin"));

		Jar jar = b.build();
		if (!b.check("Cannot convert data blabla to type Integer", "Cannot convert data 3.0 to type Integer"))
			fail();

		//
		// Test all the defaults
		//

		Resource r = jar.getResource("OSGI-INF/test.component.DSAnnotationTest$PropertiesTestx.xml");
		System.err.println(Processor.join(jar.getResources().keySet(), "\n"));
		assertNotNull(r);
		r.write(System.err);
		XmlTester xt = new XmlTester(r.openInputStream(), "scr", "http://www.osgi.org/xmlns/scr/v1.1.0");
		xt.assertAttribute("1", "scr:component/property[@name='b']/@value");
		xt.assertAttribute("", "scr:component/property[@name='a']/@value");
		xt.assertAttribute("Byte", "scr:component/property[@name='byte']/@type");
		xt.assertAttribute("Boolean", "scr:component/property[@name='boolean']/@type");
		xt.assertAttribute("Character", "scr:component/property[@name='char']/@type");
		xt.assertAttribute("Short", "scr:component/property[@name='short']/@type");
		xt.assertAttribute("Integer", "scr:component/property[@name='integer']/@type");
		xt.assertAttribute("Long", "scr:component/property[@name='long']/@type");
		xt.assertAttribute("Float", "scr:component/property[@name='float']/@type");
		xt.assertAttribute("Double", "scr:component/property[@name='double']/@type");
		xt.assertAttribute("Integer", "scr:component/property[@name='\u0343\u0344\u0345\u0346']/@type");
	}

	/**
	 * Check that a DS 1.0 compotible class with annotations ends up with the DS 1.0 (no) namespace
	 *
	 */
	@Component()
	public static class DS10_basic implements Serializable, Runnable {
		private static final long	serialVersionUID	= 1L;

		@Activate
		void activate(@SuppressWarnings("unused")ComponentContext cc) {}

		@Deactivate
		void deactivate(@SuppressWarnings("unused")ComponentContext cc) {}

		@Reference
		void xsetLogService(@SuppressWarnings("unused") LogService log) {

		}

		void unxsetLogService(@SuppressWarnings("unused") LogService log) {

		}

		@Override
		public void run() {
			// TODO Auto-generated method stub

		}
	}

	/**
	 * Check that a DS 1.1 compotible class ends up with the DS 1.1 namespace and appropriate activate/deactivate attributes
	 *
	 */
	@Component()
	public static class DS11_basic implements Serializable, Runnable {
		private static final long	serialVersionUID	= 1L;

		@Activate
		void activate(@SuppressWarnings("unused")ComponentContext cc, @SuppressWarnings("unused")BundleContext ctx) {}

		@Deactivate
		void deactivate(@SuppressWarnings("unused")ComponentContext cc, @SuppressWarnings("unused")int cause) {}

		@Reference
		void xsetLogService(@SuppressWarnings("unused") LogService log) {

		}

		void unxsetLogService(@SuppressWarnings("unused") LogService log) {

		}

		@Override
		public void run() {
			// TODO Auto-generated method stub

		}
	}

	/**
	 * Check that a DS 1.1 bind method causes a DS 1.1 namespace
	 *
	 */
	@Component()
	public static class DS11_ref1_basic implements Serializable, Runnable {
		private static final long	serialVersionUID	= 1L;

		@Activate
		void activate(@SuppressWarnings("unused")ComponentContext cc) {}

		@Deactivate
		void deactivate(@SuppressWarnings("unused")ComponentContext cc) {}

		@Reference
		void xsetLogService(@SuppressWarnings("unused") LogService log, @SuppressWarnings("unused")  Map map) {

		}

		void unxsetLogService(@SuppressWarnings("unused") LogService log) {

		}

		@Override
		public void run() {
			// TODO Auto-generated method stub

		}
	}

	/**
	 * Check that a DS 1.1 unbind method causes a DS 1.1 namespace
	 *
	 */
	@Component()
	public static class DS11_ref2_basic implements Serializable, Runnable {
		private static final long	serialVersionUID	= 1L;

		@Activate
		void activate(@SuppressWarnings("unused")ComponentContext cc) {}

		@Deactivate
		void deactivate(@SuppressWarnings("unused")ComponentContext cc) {}

		@Reference
		void xsetLogService(@SuppressWarnings("unused") LogService log) {

		}

		void unxsetLogService(@SuppressWarnings("unused") LogService log, @SuppressWarnings("unused")  Map map) {

		}

		@Override
		public void run() {
			// TODO Auto-generated method stub

		}
	}

	/**
	 * The basic test. This test will take an all default component and a
	 * component that has all values set.  Since the activate/deactivate methods have non-default names
	 * and there is a modified methods, this is a DS 1.1 component.
	 */
	@Component()
	public static class Defaults_basic implements Serializable, Runnable {
		private static final long	serialVersionUID	= 1L;

		@Activate
		void open() {}

		@Deactivate
		void close() {}

		@Modified
		void modified() {}

		@Reference
		void xsetLogService(@SuppressWarnings("unused") LogService log) {

		}

		void unxsetLogService(@SuppressWarnings("unused") LogService log) {

		}

		// void modifiedLogService(LogService log) {
		//
		// }

		@Override
		public void run() {
			// TODO Auto-generated method stub

		}
	}

	@Component(service = Object.class, configurationPolicy = ConfigurationPolicy.IGNORE, enabled = false, factory = "factory", immediate = false, name = "name", property = {
			"a=1", "a=2", "b=3"
	}, properties = "resource.props", servicefactory = false, configurationPid = "configuration-pid", xmlns = "xmlns")
	public static class Explicit_basic implements Serializable, Runnable {
		private static final long	serialVersionUID	= 1L;

		@Activate
		void open() {}

		@Deactivate
		void close() {}

		@Modified
		void changed() {}

		@Reference(cardinality = ReferenceCardinality.AT_LEAST_ONE, name = "foo", policy = ReferencePolicy.DYNAMIC, service = Object.class, target = "(objectclass=*)", unbind = "unset", updated = "updatedLogService", policyOption = ReferencePolicyOption.GREEDY)
		void setLogService(@SuppressWarnings("unused") LogService log) {

		}

		void unset(@SuppressWarnings("unused") Object log) {

		}

		void unset() {

		}

		void unsetLogService(@SuppressWarnings("unused") LogService log) {

		}

		void updatedLogService(@SuppressWarnings("unused") Object log) {

		}

		@Override
		public void run() {
			// TODO Auto-generated method stub

		}
	}

	public static void testBasic() throws Exception {
		Builder b = new Builder();
		b.setProperty("-dsannotations", "test.component.*_basic");
		b.setProperty("Private-Package", "test.component");
		b.addClasspath(new File("bin"));

		Jar jar = b.build();
		assertOk(b);

		{
			//
			// Test all the DS 1.0 defaults
			//

			Resource r = jar.getResource("OSGI-INF/test.component.DSAnnotationTest$DS10_basic.xml");
			System.err.println(Processor.join(jar.getResources().keySet(), "\n"));
			assertNotNull(r);
			r.write(System.err);
			XmlTester xt = new XmlTester(r.openInputStream()); 

			// Test the defaults
			xt.assertAttribute("test.component.DSAnnotationTest$DS10_basic", "component/implementation/@class");

			// Default must be the implementation class
			xt.assertAttribute("test.component.DSAnnotationTest$DS10_basic", "component/@name");

			xt.assertAttribute("", "component/@configuration-policy");
			xt.assertAttribute("", "component/@immediate");
			xt.assertAttribute("", "component/@enabled");
			xt.assertAttribute("", "component/@factory");
			xt.assertAttribute("", "component/@servicefactory");
			xt.assertAttribute("", "component/@configuration-pid");
			xt.assertAttribute("", "component/@activate");
			xt.assertAttribute("", "component/@deactivate");
			xt.assertAttribute("", "component/@modified");
			xt.assertAttribute("java.io.Serializable", "component/service/provide[1]/@interface");
			xt.assertAttribute("java.lang.Runnable", "component/service/provide[2]/@interface");

			xt.assertAttribute("0", "count(component/properties)");
			xt.assertAttribute("0", "count(component/property)");

			xt.assertAttribute("xsetLogService", "component/reference[1]/@name");
			xt.assertAttribute("", "component/reference[1]/@target");
			xt.assertAttribute("xsetLogService", "component/reference[1]/@bind");
			xt.assertAttribute("unxsetLogService", "component/reference[1]/@unbind");
			xt.assertAttribute("", "component/reference[1]/@cardinality");
			xt.assertAttribute("", "component/reference[1]/@policy");
			xt.assertAttribute("", "component/reference[1]/@target");
			xt.assertAttribute("", "component/reference[1]/@policy-option");
	}
	{
			//
			// Test the DS 1.1 defaults 
			//

			Resource r = jar.getResource("OSGI-INF/test.component.DSAnnotationTest$DS11_basic.xml");
			System.err.println(Processor.join(jar.getResources().keySet(), "\n"));
			assertNotNull(r);
			r.write(System.err);
			XmlTester xt = new XmlTester(r.openInputStream(), "scr", "http://www.osgi.org/xmlns/scr/v1.1.0"); // #136
																												// was
																												// http://www.osgi.org/xmlns/scr/1.1.0

			// Test the defaults
			xt.assertAttribute("test.component.DSAnnotationTest$DS11_basic", "scr:component/implementation/@class");

			// Default must be the implementation class
			xt.assertAttribute("test.component.DSAnnotationTest$DS11_basic", "scr:component/@name");

			xt.assertAttribute("", "scr:component/@configuration-policy");
			xt.assertAttribute("", "scr:component/@immediate");
			xt.assertAttribute("", "scr:component/@enabled");
			xt.assertAttribute("", "scr:component/@factory");
			xt.assertAttribute("", "scr:component/@servicefactory");
			xt.assertAttribute("", "scr:component/@configuration-pid");
			xt.assertAttribute("activate", "scr:component/@activate");
			xt.assertAttribute("deactivate", "scr:component/@deactivate");
			xt.assertAttribute("", "scr:component/@modified");
			xt.assertAttribute("java.io.Serializable", "scr:component/service/provide[1]/@interface");
			xt.assertAttribute("java.lang.Runnable", "scr:component/service/provide[2]/@interface");

			xt.assertAttribute("0", "count(scr:component/properties)");
			xt.assertAttribute("0", "count(scr:component/property)");

			xt.assertAttribute("xsetLogService", "scr:component/reference[1]/@name");
			xt.assertAttribute("", "scr:component/reference[1]/@target");
			xt.assertAttribute("xsetLogService", "scr:component/reference[1]/@bind");
			xt.assertAttribute("unxsetLogService", "scr:component/reference[1]/@unbind");
			xt.assertAttribute("", "scr:component/reference[1]/@cardinality");
			xt.assertAttribute("", "scr:component/reference[1]/@policy");
			xt.assertAttribute("", "scr:component/reference[1]/@target");
			xt.assertAttribute("", "scr:component/reference[1]/@policy-option");
	}
	{
		//
		// Test a DS 1.1 bind method results in the DS 1.1 namespace
		//

		Resource r = jar.getResource("OSGI-INF/test.component.DSAnnotationTest$DS11_ref1_basic.xml");
		System.err.println(Processor.join(jar.getResources().keySet(), "\n"));
		assertNotNull(r);
		r.write(System.err);
		XmlTester xt = new XmlTester(r.openInputStream(), "scr", "http://www.osgi.org/xmlns/scr/v1.1.0"); // #136
																											// was
																											// http://www.osgi.org/xmlns/scr/1.1.0

		// Test the defaults
		xt.assertAttribute("test.component.DSAnnotationTest$DS11_ref1_basic", "scr:component/implementation/@class");

		// Default must be the implementation class
		xt.assertAttribute("test.component.DSAnnotationTest$DS11_ref1_basic", "scr:component/@name");

		xt.assertAttribute("", "scr:component/@configuration-policy");
		xt.assertAttribute("", "scr:component/@immediate");
		xt.assertAttribute("", "scr:component/@enabled");
		xt.assertAttribute("", "scr:component/@factory");
		xt.assertAttribute("", "scr:component/@servicefactory");
		xt.assertAttribute("", "scr:component/@configuration-pid");
		xt.assertAttribute("activate", "scr:component/@activate");
		xt.assertAttribute("deactivate", "scr:component/@deactivate");
		xt.assertAttribute("", "scr:component/@modified");
		xt.assertAttribute("java.io.Serializable", "scr:component/service/provide[1]/@interface");
		xt.assertAttribute("java.lang.Runnable", "scr:component/service/provide[2]/@interface");

		xt.assertAttribute("0", "count(scr:component/properties)");
		xt.assertAttribute("0", "count(scr:component/property)");

		xt.assertAttribute("xsetLogService", "scr:component/reference[1]/@name");
		xt.assertAttribute("", "scr:component/reference[1]/@target");
		xt.assertAttribute("xsetLogService", "scr:component/reference[1]/@bind");
		xt.assertAttribute("unxsetLogService", "scr:component/reference[1]/@unbind");
		xt.assertAttribute("", "scr:component/reference[1]/@cardinality");
		xt.assertAttribute("", "scr:component/reference[1]/@policy");
		xt.assertAttribute("", "scr:component/reference[1]/@target");
		xt.assertAttribute("", "scr:component/reference[1]/@policy-option");
	}
	{
		//
		// Test a DS 1.1 unbind method results in the DS 1.1 namespace
		//

		Resource r = jar.getResource("OSGI-INF/test.component.DSAnnotationTest$DS11_ref2_basic.xml");
		System.err.println(Processor.join(jar.getResources().keySet(), "\n"));
		assertNotNull(r);
		r.write(System.err);
		XmlTester xt = new XmlTester(r.openInputStream(), "scr", "http://www.osgi.org/xmlns/scr/v1.1.0"); // #136
																											// was
																											// http://www.osgi.org/xmlns/scr/1.1.0

		// Test the defaults
		xt.assertAttribute("test.component.DSAnnotationTest$DS11_ref2_basic", "scr:component/implementation/@class");

		// Default must be the implementation class
		xt.assertAttribute("test.component.DSAnnotationTest$DS11_ref2_basic", "scr:component/@name");

		xt.assertAttribute("", "scr:component/@configuration-policy");
		xt.assertAttribute("", "scr:component/@immediate");
		xt.assertAttribute("", "scr:component/@enabled");
		xt.assertAttribute("", "scr:component/@factory");
		xt.assertAttribute("", "scr:component/@servicefactory");
		xt.assertAttribute("", "scr:component/@configuration-pid");
		xt.assertAttribute("activate", "scr:component/@activate");
		xt.assertAttribute("deactivate", "scr:component/@deactivate");
		xt.assertAttribute("", "scr:component/@modified");
		xt.assertAttribute("java.io.Serializable", "scr:component/service/provide[1]/@interface");
		xt.assertAttribute("java.lang.Runnable", "scr:component/service/provide[2]/@interface");

		xt.assertAttribute("0", "count(scr:component/properties)");
		xt.assertAttribute("0", "count(scr:component/property)");

		xt.assertAttribute("xsetLogService", "scr:component/reference[1]/@name");
		xt.assertAttribute("", "scr:component/reference[1]/@target");
		xt.assertAttribute("xsetLogService", "scr:component/reference[1]/@bind");
		xt.assertAttribute("unxsetLogService", "scr:component/reference[1]/@unbind");
		xt.assertAttribute("", "scr:component/reference[1]/@cardinality");
		xt.assertAttribute("", "scr:component/reference[1]/@policy");
		xt.assertAttribute("", "scr:component/reference[1]/@target");
		xt.assertAttribute("", "scr:component/reference[1]/@policy-option");
	}
	{
			//
			// Test all the defaults
			//

			Resource r = jar.getResource("OSGI-INF/test.component.DSAnnotationTest$Defaults_basic.xml");
			System.err.println(Processor.join(jar.getResources().keySet(), "\n"));
			assertNotNull(r);
			r.write(System.err);
			XmlTester xt = new XmlTester(r.openInputStream(), "scr", "http://www.osgi.org/xmlns/scr/v1.1.0"); // #136
																												// was
																												// http://www.osgi.org/xmlns/scr/1.1.0

			// Test the defaults
			xt.assertAttribute("test.component.DSAnnotationTest$Defaults_basic", "scr:component/implementation/@class");

			// Default must be the implementation class
			xt.assertAttribute("test.component.DSAnnotationTest$Defaults_basic", "scr:component/@name");

			xt.assertAttribute("", "scr:component/@configuration-policy");
			xt.assertAttribute("", "scr:component/@immediate");
			xt.assertAttribute("", "scr:component/@enabled");
			xt.assertAttribute("", "scr:component/@factory");
			xt.assertAttribute("", "scr:component/@servicefactory");
			xt.assertAttribute("", "scr:component/@configuration-pid");
			xt.assertAttribute("open", "scr:component/@activate");
			xt.assertAttribute("close", "scr:component/@deactivate");
			xt.assertAttribute("modified", "scr:component/@modified");
			xt.assertAttribute("java.io.Serializable", "scr:component/service/provide[1]/@interface");
			xt.assertAttribute("java.lang.Runnable", "scr:component/service/provide[2]/@interface");

			xt.assertAttribute("0", "count(scr:component/properties)");
			xt.assertAttribute("0", "count(scr:component/property)");

			xt.assertAttribute("xsetLogService", "scr:component/reference[1]/@name");
			xt.assertAttribute("", "scr:component/reference[1]/@target");
			xt.assertAttribute("xsetLogService", "scr:component/reference[1]/@bind");
			xt.assertAttribute("unxsetLogService", "scr:component/reference[1]/@unbind");
			xt.assertAttribute("", "scr:component/reference[1]/@cardinality");
			xt.assertAttribute("", "scr:component/reference[1]/@policy");
			xt.assertAttribute("", "scr:component/reference[1]/@target");
			xt.assertAttribute("", "scr:component/reference[1]/@policy-option");
		}
		{
			//
			// Test explicit
			//

			Resource r = jar.getResource("OSGI-INF/name.xml");
			assertNotNull(r);
			r.write(System.err);
			XmlTester xt = new XmlTester(r.openInputStream(), "scr", "xmlns");

			// Test the defaults
			xt.assertAttribute("test.component.DSAnnotationTest$Explicit_basic", "scr:component/implementation/@class");

			// Default must be the implementation class
			xt.assertAttribute("name", "scr:component/@name");

			xt.assertAttribute("ignore", "scr:component/@configuration-policy");
			xt.assertAttribute("configuration-pid", "scr:component/@configuration-pid");
			xt.assertAttribute("false", "scr:component/@immediate");
			xt.assertAttribute("false", "scr:component/@enabled");
			xt.assertAttribute("factory", "scr:component/@factory");
			xt.assertAttribute("false", "scr:component/@servicefactory");
			xt.assertAttribute("open", "scr:component/@activate");
			xt.assertAttribute("close", "scr:component/@deactivate");
			xt.assertAttribute("changed", "scr:component/@modified");
			xt.assertAttribute("java.lang.Object", "scr:component/service/provide[1]/@interface");
			xt.assertAttribute("1", "count(scr:component/service/provide)");

			xt.assertAttribute("1", "count(scr:component/properties)");
			xt.assertAttribute("2", "count(scr:component/property)");

			xt.assertAttribute("(objectclass=*)", "scr:component/reference[1]/@target");
			xt.assertAttribute("setLogService", "scr:component/reference[1]/@bind");
			xt.assertAttribute("unset", "scr:component/reference[1]/@unbind");
			xt.assertAttribute("updatedLogService", "scr:component/reference[1]/@updated");
			xt.assertAttribute("1..n", "scr:component/reference[1]/@cardinality");
			xt.assertAttribute("dynamic", "scr:component/reference[1]/@policy");
			xt.assertAttribute("(objectclass=*)", "scr:component/reference[1]/@target");

			xt.assertAttribute("2", "count(scr:component/property)");
			xt.assertAttribute("1", "count(scr:component/properties)");
			xt.assertAttribute("resource.props", "scr:component/properties[1]/@entry");
			xt.assertAttribute("greedy", "scr:component/reference[1]/@policy-option");
		}
	}
	
	/**
	 * Check that a Felix 1.2 compotible class ends up with the Felix 1.2 namespace and appropriate activate/deactivate attributes
	 *
	 */
	@Component()
	public static class activate_basicFelix12 implements Serializable, Runnable {
		private static final long	serialVersionUID	= 1L;

		@Activate
		Map<String, Object> activate(@SuppressWarnings("unused")ComponentContext cc, @SuppressWarnings("unused")BundleContext ctx) {
			return null;
		}

		@Deactivate
		void deactivate(@SuppressWarnings("unused")ComponentContext cc, @SuppressWarnings("unused")int cause) {}

		@Modified
		void modified(@SuppressWarnings("unused")ComponentContext cc) {}

		@Reference
		void setLogService(@SuppressWarnings("unused") LogService log) {}

		void unsetLogService(@SuppressWarnings("unused") LogService log) {}

		void updatedLogService(@SuppressWarnings("unused") LogService log) {}

		@Override
		public void run() {
			// TODO Auto-generated method stub

		}
	}

	@Component()
	public static class deactivate_basicFelix12 implements Serializable, Runnable {
		private static final long	serialVersionUID	= 1L;

		@Activate
		void activate(@SuppressWarnings("unused")ComponentContext cc, @SuppressWarnings("unused")BundleContext ctx) {}

		@Deactivate
		Map<String, Object> deactivate(@SuppressWarnings("unused")ComponentContext cc, @SuppressWarnings("unused")int cause) {
			return null;
		}
		
		@Modified
		void modified(@SuppressWarnings("unused")ComponentContext cc) {}

		@Reference
		void setLogService(@SuppressWarnings("unused") LogService log) {}

		void unsetLogService(@SuppressWarnings("unused") LogService log) {}

		void updatedLogService(@SuppressWarnings("unused") LogService log) {}

		@Override
		public void run() {
			// TODO Auto-generated method stub

		}
	}

	@Component()
	public static class modified_basicFelix12 implements Serializable, Runnable {
		private static final long	serialVersionUID	= 1L;

		@Activate
		void activate(@SuppressWarnings("unused")ComponentContext cc, @SuppressWarnings("unused")BundleContext ctx) {}

		@Deactivate
		void deactivate(@SuppressWarnings("unused")ComponentContext cc, @SuppressWarnings("unused")int cause) {}
		
		@Modified
		Map<String, Object> modified(@SuppressWarnings("unused")ComponentContext cc) {
			return null;
		}

		@Reference
		void setLogService(@SuppressWarnings("unused") LogService log) {}

		void unsetLogService(@SuppressWarnings("unused") LogService log) {}

		void updatedLogService(@SuppressWarnings("unused") LogService log) {}

		@Override
		public void run() {
			// TODO Auto-generated method stub

		}
	}

	@Component()
	public static class bind_basicFelix12 implements Serializable, Runnable {
		private static final long	serialVersionUID	= 1L;

		@Activate
		void activate(@SuppressWarnings("unused")ComponentContext cc, @SuppressWarnings("unused")BundleContext ctx) {}

		@Deactivate
		void deactivate(@SuppressWarnings("unused")ComponentContext cc, @SuppressWarnings("unused")int cause) {}
		
		@Modified
		void modified(@SuppressWarnings("unused")ComponentContext cc) {}

		@Reference
		Map<String, Object> setLogService(@SuppressWarnings("unused") LogService log) {
			return null;
		}

		void unsetLogService(@SuppressWarnings("unused") LogService log) {}

		void updatedLogService(@SuppressWarnings("unused") LogService log) {}

		@Override
		public void run() {
			// TODO Auto-generated method stub

		}
	}

	@Component()
	public static class unbind_basicFelix12 implements Serializable, Runnable {
		private static final long	serialVersionUID	= 1L;

		@Activate
		void activate(@SuppressWarnings("unused")ComponentContext cc, @SuppressWarnings("unused")BundleContext ctx) {}

		@Deactivate
		void deactivate(@SuppressWarnings("unused")ComponentContext cc, @SuppressWarnings("unused")int cause) {}
		
		@Modified
		void modified(@SuppressWarnings("unused")ComponentContext cc) {}

		@Reference
		void setLogService(@SuppressWarnings("unused") LogService log) {}

		Map<String, Object> unsetLogService(@SuppressWarnings("unused") LogService log) {
			return null;
		}
		
		void updatedLogService(@SuppressWarnings("unused") LogService log) {}

		@Override
		public void run() {
			// TODO Auto-generated method stub

		}
	}

	@Component()
	public static class updated_basicFelix12 implements Serializable, Runnable {
		private static final long	serialVersionUID	= 1L;

		@Activate
		void activate(@SuppressWarnings("unused")ComponentContext cc, @SuppressWarnings("unused")BundleContext ctx) {}

		@Deactivate
		void deactivate(@SuppressWarnings("unused")ComponentContext cc, @SuppressWarnings("unused")int cause) {}
		
		@Modified
		void modified(@SuppressWarnings("unused")ComponentContext cc) {}

		@Reference
		void setLogService(@SuppressWarnings("unused") LogService log) {}

		void unsetLogService(@SuppressWarnings("unused") LogService log) {}

		Map<String, Object> updatedLogService(@SuppressWarnings("unused") LogService log) {
			return null;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub

		}
	}

	public static void testBasicFelix12() throws Exception {
		Builder b = new Builder();
		b.setProperty("-dsannotations", "test.component.*_basicFelix12");
		b.setProperty("-ds-felix-extensions", "");
		b.setProperty("Private-Package", "test.component");
		b.addClasspath(new File("bin"));

		Jar jar = b.build();
		assertOk(b);

		// Test Felix12 activate gives Felix 1.2 namespace 
		checkDSFelix12(jar, "test.component.DSAnnotationTest$activate_basicFelix12");
		// Test Felix12 deactivate gives Felix 1.2 namespace 
		checkDSFelix12(jar, "test.component.DSAnnotationTest$deactivate_basicFelix12");
		// Test Felix12 modified gives Felix 1.2 namespace 
		checkDSFelix12(jar, "test.component.DSAnnotationTest$modified_basicFelix12");
		// Test Felix12 bind gives Felix 1.2 namespace 
		checkDSFelix12(jar, "test.component.DSAnnotationTest$bind_basicFelix12");
		// Test Felix12 bind gives Felix 1.2 namespace 
		checkDSFelix12(jar, "test.component.DSAnnotationTest$unbind_basicFelix12");
	}

	private static void checkDSFelix12(Jar jar, String name) throws Exception, XPathExpressionException {
		Resource r = jar.getResource("OSGI-INF/" + name + ".xml");
		System.err.println(Processor.join(jar.getResources().keySet(), "\n"));
		assertNotNull(r);
		r.write(System.err);
		XmlTester xt = new XmlTester(r.openInputStream(), "scr", AnnotationReader.FELIX_1_2); 
		// Test the defaults
		xt.assertAttribute(name, "scr:component/implementation/@class");

		// Default must be the implementation class
		xt.assertAttribute(name, "scr:component/@name");

		xt.assertAttribute("", "scr:component/@configuration-policy");
		xt.assertAttribute("", "scr:component/@immediate");
		xt.assertAttribute("", "scr:component/@enabled");
		xt.assertAttribute("", "scr:component/@factory");
		xt.assertAttribute("", "scr:component/@servicefactory");
		xt.assertAttribute("", "scr:component/@configuration-pid");
		xt.assertAttribute("activate", "scr:component/@activate");
		xt.assertAttribute("deactivate", "scr:component/@deactivate");
		xt.assertAttribute("modified", "scr:component/@modified");
		xt.assertAttribute("java.io.Serializable", "scr:component/service/provide[1]/@interface");
		xt.assertAttribute("java.lang.Runnable", "scr:component/service/provide[2]/@interface");

		xt.assertAttribute("0", "count(scr:component/properties)");
		xt.assertAttribute("0", "count(scr:component/property)");

		xt.assertAttribute("LogService", "scr:component/reference[1]/@name");
		xt.assertAttribute("", "scr:component/reference[1]/@target");
		xt.assertAttribute("setLogService", "scr:component/reference[1]/@bind");
		xt.assertAttribute("unsetLogService", "scr:component/reference[1]/@unbind");
		xt.assertAttribute("updatedLogService", "scr:component/reference[1]/@updated");
		xt.assertAttribute("", "scr:component/reference[1]/@cardinality");
		xt.assertAttribute("", "scr:component/reference[1]/@policy");
		xt.assertAttribute("", "scr:component/reference[1]/@target");
		xt.assertAttribute("", "scr:component/reference[1]/@policy-option");
	}

	/**
	 * Tests all the different enum values. This also tests the ordering.
	 */
	@Component(name = "enums")
	public static class Enums {

		@Reference
		void setA(@SuppressWarnings("unused") LogService l) {}

		@Reference(cardinality = ReferenceCardinality.AT_LEAST_ONE, policy = ReferencePolicy.DYNAMIC, policyOption = ReferencePolicyOption.GREEDY)
		void setB(@SuppressWarnings("unused") LogService l) {}
		void unsetB(@SuppressWarnings("unused") LogService l) {}

		@Reference(cardinality = ReferenceCardinality.OPTIONAL, policy = ReferencePolicy.DYNAMIC, policyOption = ReferencePolicyOption.RELUCTANT)
		void setE(@SuppressWarnings("unused") LogService l) {}
		void unsetE(@SuppressWarnings("unused") LogService l) {}

		@Reference(cardinality = ReferenceCardinality.MANDATORY, policy = ReferencePolicy.STATIC, policyOption = ReferencePolicyOption.RELUCTANT)
		void setC(@SuppressWarnings("unused") LogService l) {}

		@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC, policyOption = ReferencePolicyOption.GREEDY)
		void setD(@SuppressWarnings("unused") LogService l) {}
		void unsetD(@SuppressWarnings("unused") LogService l) {}

	}

	public static void testEnums() throws Exception {
		Builder b = new Builder();
		b.setProperty("-dsannotations", "test.component.DSAnnotationTest*Enums");
		b.setProperty("Private-Package", "test.component");
		b.addClasspath(new File("bin"));

		Jar jar = b.build();
		assertOk(b);

		Resource r = jar.getResource("OSGI-INF/enums.xml");
		assertNotNull(r);
		r.write(System.err);
		XmlTester xt = new XmlTester(r.openInputStream(), "scr", "http://www.osgi.org/xmlns/scr/v1.2.0");

		xt.assertAttribute("A", "scr:component/reference[1]/@name");
		xt.assertAttribute("", "scr:component/reference[1]/@cardinality");
		xt.assertAttribute("", "scr:component/reference[1]/@policy");
		xt.assertAttribute("", "scr:component/reference[1]/@policy-option");

		xt.assertAttribute("B", "scr:component/reference[2]/@name");
		xt.assertAttribute("1..n", "scr:component/reference[2]/@cardinality");
		xt.assertAttribute("dynamic", "scr:component/reference[2]/@policy");
		xt.assertAttribute("greedy", "scr:component/reference[2]/@policy-option");

		xt.assertAttribute("C", "scr:component/reference[3]/@name");
		xt.assertAttribute("1..1", "scr:component/reference[3]/@cardinality");
		xt.assertAttribute("static", "scr:component/reference[3]/@policy");
		xt.assertAttribute("reluctant", "scr:component/reference[3]/@policy-option");

		xt.assertAttribute("D", "scr:component/reference[4]/@name");
		xt.assertAttribute("0..n", "scr:component/reference[4]/@cardinality");
		xt.assertAttribute("dynamic", "scr:component/reference[4]/@policy");
		xt.assertAttribute("greedy", "scr:component/reference[4]/@policy-option");

		xt.assertAttribute("E", "scr:component/reference[5]/@name");
		xt.assertAttribute("0..1", "scr:component/reference[5]/@cardinality");
		xt.assertAttribute("dynamic", "scr:component/reference[5]/@policy");
		xt.assertAttribute("reluctant", "scr:component/reference[5]/@policy-option");
	}

	/**
	 * Test the - for the unbind and updated parameter.
	 */
	@Component(name = "methods")
	public static class Methods {

		@Reference(unbind = "-", updated = "-")
		void setA(@SuppressWarnings("unused") LogService l) {}

		void updatedA(@SuppressWarnings("unused") LogService l) {}

		void unsetA(@SuppressWarnings("unused") LogService l) {}

		@Reference(unbind = "_B", updated = "__B")
		void setB(@SuppressWarnings("unused") LogService l) {}

		void _B(@SuppressWarnings("unused") LogService l) {}

		void __B(@SuppressWarnings("unused") LogService l) {}

		void updatedB(@SuppressWarnings("unused") LogService l) {}

		void unsetB(@SuppressWarnings("unused") LogService l) {}

		@Reference
		void setC(@SuppressWarnings("unused") LogService l) {}

		void updatedC(@SuppressWarnings("unused") LogService l) {}

		void unsetC(@SuppressWarnings("unused") LogService l) {}

		@Reference
		void setD(@SuppressWarnings("unused") LogService l) {}

	}

	public static void testMethods() throws Exception {
		Builder b = new Builder();
		b.setProperty("-dsannotations", "test.component.DSAnnotationTest*Methods");
		b.setProperty("Private-Package", "test.component");
		b.addClasspath(new File("bin"));

		Jar jar = b.build();
		assertOk(b);

		Resource r = jar.getResource("OSGI-INF/methods.xml");
		assertNotNull(r);
		r.write(System.err);
		XmlTester xt = new XmlTester(r.openInputStream(), "scr", "http://www.osgi.org/xmlns/scr/v1.2.0");

		// use - to make sure no unbind and updated method is set
		xt.assertAttribute("A", "scr:component/reference[1]/@name");
		xt.assertAttribute("setA", "scr:component/reference[1]/@bind");
		xt.assertAttribute("", "scr:component/reference[1]/@unbind");
		xt.assertAttribute("", "scr:component/reference[1]/@updated");

		// override the names for the methods
		xt.assertAttribute("B", "scr:component/reference[2]/@name");
		xt.assertAttribute("setB", "scr:component/reference[2]/@bind");
		xt.assertAttribute("_B", "scr:component/reference[2]/@unbind");
		xt.assertAttribute("__B", "scr:component/reference[2]/@updated");

		xt.assertAttribute("C", "scr:component/reference[3]/@name");
		xt.assertAttribute("setC", "scr:component/reference[3]/@bind");
		xt.assertAttribute("unsetC", "scr:component/reference[3]/@unbind");
		xt.assertAttribute("updatedC", "scr:component/reference[3]/@updated");

		xt.assertAttribute("D", "scr:component/reference[4]/@name");
		xt.assertAttribute("setD", "scr:component/reference[4]/@bind");
		xt.assertAttribute("", "scr:component/reference[4]/@unbind");
		xt.assertAttribute("", "scr:component/reference[4]/@updated");
	}

	/**
	 * Test inheritance (this is not official)
	 */

	public static class Top {

		@Reference
		void setLogService(@SuppressWarnings("unused") LogService l) {}

		void updatedLogService(@SuppressWarnings("unused") ServiceReference ref) {

		}

		@Reference
		protected void setPrivateLogService(@SuppressWarnings("unused") LogService l) {

		}

		@SuppressWarnings("unused")
		private void updatedPrivateLogService(ServiceReference ref) {

		}
	}

	@Component(name = "bottom")
	public static class Bottom extends Top {
		void unsetLogService(@SuppressWarnings("unused") LogService l, @SuppressWarnings("unused") Map<Object,Object> map) {

		}

		void unsetPrivateLogService(@SuppressWarnings("unused") ServiceReference ref) {

		}
	}

	public static void testInheritance() throws Exception {
		Builder b = new Builder();
		b.setProperty("-dsannotations", "test.component.DSAnnotationTest*Bottom");
		b.setProperty("-dsannotations-inherit", "true");
		b.setProperty("Private-Package", "test.component");
		b.addClasspath(new File("bin"));

		Jar jar = b.build();
		assertOk(b);

		Resource r = jar.getResource("OSGI-INF/bottom.xml");
		assertNotNull(r);
		r.write(System.err);
		XmlTester xt = new XmlTester(r.openInputStream(), "scr", "http://www.osgi.org/xmlns/scr/v1.2.0");

		xt.assertAttribute("LogService", "scr:component/reference[1]/@name");
		xt.assertAttribute("setLogService", "scr:component/reference[1]/@bind");
		xt.assertAttribute("unsetLogService", "scr:component/reference[1]/@unbind");
		xt.assertAttribute("updatedLogService", "scr:component/reference[1]/@updated");

		xt.assertAttribute("PrivateLogService", "scr:component/reference[2]/@name");
		xt.assertAttribute("setPrivateLogService", "scr:component/reference[2]/@bind");
		xt.assertAttribute("unsetPrivateLogService", "scr:component/reference[2]/@unbind");
		xt.assertAttribute("", "scr:component/reference[2]/@updated"); // is
																		// private
																		// in
																		// super
																		// class

	}

	/**
	 * Test the different prototypes ...
	 */

	@Component(name = "prototypes")
	public static class Prototypes {
		@SuppressWarnings("unused")
		@Activate
		private void activate() {}

		@Deactivate
		protected void deactivate(@SuppressWarnings("unused") ComponentContext ctx) {

		}

		@Modified
		void modified(@SuppressWarnings("unused") BundleContext context) {

		}

		@SuppressWarnings("unused")
		@Reference
		private void setLogService(LogService l) {

		}

		protected void unsetLogService(@SuppressWarnings("unused") LogService l, @SuppressWarnings("unused") Map<Object,Object> map) {

		}

		void updatedLogService(@SuppressWarnings("unused") ServiceReference ref) {

		}

	}

	public static void testPrototypes() throws Exception {
		Builder b = new Builder();
		b.setProperty("-dsannotations", "test.component.DSAnnotationTest*Prototypes");
		b.setProperty("Private-Package", "test.component");
		b.addClasspath(new File("bin"));

		Jar jar = b.build();
		assertOk(b);

		Resource r = jar.getResource("OSGI-INF/prototypes.xml");
		assertNotNull(r);
		r.write(System.err);
		XmlTester xt = new XmlTester(r.openInputStream(), "scr", "http://www.osgi.org/xmlns/scr/v1.2.0");

		xt.assertAttribute("LogService", "scr:component/reference[1]/@name");
		xt.assertAttribute("setLogService", "scr:component/reference[1]/@bind");
		xt.assertAttribute("unsetLogService", "scr:component/reference[1]/@unbind");
		xt.assertAttribute("updatedLogService", "scr:component/reference[1]/@updated");

	}

	/**
	 * Test the different prototypes ...
	 */

	@Component(name = "prototypes")
	public static class CheckBinds {
		@SuppressWarnings("unused")
		@Activate
		private void activate() {}

		@Deactivate
		protected void deactivate(@SuppressWarnings("unused") ComponentContext ctx) {

		}

		@Modified
		void modified(@SuppressWarnings("unused") BundleContext context) {

		}

		@SuppressWarnings("unused")
		@Reference
		private void bindLogService(LogService l) {

		}

		protected void unbindLogService(@SuppressWarnings("unused") LogService l, @SuppressWarnings("unused") Map<Object,Object> map) {

		}

		void updatedLogService(@SuppressWarnings("unused") ServiceReference ref) {

		}

	}

	public static void testBinds() throws Exception {
		Builder b = new Builder();
		b.setProperty("-dsannotations", "test.component.DSAnnotationTest*CheckBinds");
		b.setProperty("Private-Package", "test.component");
		b.addClasspath(new File("bin"));

		Jar jar = b.build();
		assertOk(b);

		Resource r = jar.getResource("OSGI-INF/prototypes.xml");
		assertNotNull(r);
		r.write(System.err);
		XmlTester xt = new XmlTester(r.openInputStream(), "scr", "http://www.osgi.org/xmlns/scr/v1.2.0");

		xt.assertAttribute("LogService", "scr:component/reference[1]/@name");
		xt.assertAttribute("bindLogService", "scr:component/reference[1]/@bind");
		xt.assertAttribute("unbindLogService", "scr:component/reference[1]/@unbind");
		xt.assertAttribute("updatedLogService", "scr:component/reference[1]/@updated");

	}
	
	@Component(name = "NoUnbindDynamic")
	public static class NoUnbindDynamic {
		@SuppressWarnings("unused")
		@Reference(policy = ReferencePolicy.DYNAMIC)
		private void bindLogService(LogService l) {
		}
	}
	
	public static void testNoUnbindDynamic() throws Exception {
		Builder b = new Builder();
		b.setProperty("-dsannotations", "test.component.DSAnnotationTest*NoUnbindDynamic");
		b.setProperty("Private-Package", "test.component");
		b.addClasspath(new File("bin"));

		Jar jar = b.build();
		assertEquals(1, b.getErrors().size());
		assertTrue(b.getErrors().get(0).endsWith("dynamic but has no unbind method."));
	}
	
	
	@Component(name="testConfigPolicy", configurationPolicy=ConfigurationPolicy.IGNORE)
	public static class TestConfigPolicy {}
	
	public static void testConfigPolicySetsNamespace() throws Exception {
		Builder b = new Builder();
		b.setProperty("-dsannotations", "test.component.DSAnnotationTest*TestConfigPolicy");
		b.setProperty("Private-Package", "test.component");
		b.addClasspath(new File("bin"));

		Jar jar = b.build();
		assertOk(b);

		Resource r = jar.getResource("OSGI-INF/testConfigPolicy.xml");
		assertNotNull(r);
		r.write(System.err);
		XmlTester xt = new XmlTester(r.openInputStream(), "scr", "http://www.osgi.org/xmlns/scr/v1.1.0");
		xt.assertNamespace("http://www.osgi.org/xmlns/scr/v1.1.0");
	}

}
