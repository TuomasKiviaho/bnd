package test.diff;

import java.io.*;

import junit.framework.*;
import aQute.bnd.differ.*;
import aQute.bnd.osgi.*;
import aQute.bnd.service.diff.*;

public class DiffTest extends TestCase {
	static DiffPluginImpl	differ	= new DiffPluginImpl();

	public static void testAwtGeom() throws Exception {
		Tree newer = differ.tree(new File("../cnf/repo/ee.j2se/ee.j2se-1.5.0.jar"));
		Tree gp = newer.get("<api>").get("java.awt.geom").get("java.awt.geom.GeneralPath");
		assertNotNull(gp);
		show(gp, 0);
	}

	public static final class Final {
		public void foo() {}
	}

	public static class II {
		final int	x	= 3;

		public void foo() {}
	}

	public static class I extends II {
		public static I bar() {
			return null;
		}

		@Override
		public void foo() {}
	}

	public static void testInheritance() throws Exception {
		Builder b = new Builder();
		b.addClasspath(new File("bin"));
		b.setProperty(Constants.EXPORT_PACKAGE, "test.diff");
		b.build();
		Tree newer = differ.tree(b);
		Tree older = differ.tree(b);
		Diff diff = newer.diff(older);
		Diff p = diff.get("<api>").get("test.diff");
		show(p, 0);
		Diff c = p.get("test.diff.DiffTest$I");
		assertNotNull(c.get("hashCode()"));
		assertNotNull(c.get("finalize()"));
		assertNotNull(c.get("foo()"));

		Diff cc = p.get("test.diff.DiffTest$Final");
		assertNotNull(cc.get("foo()"));
		b.close();
	}

	public static interface Intf {
		void foo();
	}

	public static abstract class X implements Intf {

		@Override
		public void foo() {}
	}

	static interface A extends Comparable<Object>, Serializable {

	}

	public static void testSimple() throws Exception {

		Tree newer = differ.tree(new Jar(new File("jar/osgi.core-4.3.0.jar")));
		Tree older = differ.tree(new Jar(new File("jar/osgi.core.jar"))); // 4.2
		Diff diff = newer.diff(older);

		show(diff, 0);
	}

	static abstract class SBB {}

	public static class CMP implements Comparable<Number> {

		@Override
		public int compareTo(Number var0) {
			// TODO Auto-generated method stub
			return 0;
		}

	}

	static class SB extends SBB implements Appendable {

		@Override
		public SB append(char var0) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public SB append(CharSequence var0) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public SB append(CharSequence var0, int var1, int var2) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
	}

	static void show(Diff diff, int indent) {
		// if (diff.getDelta() == Delta.UNCHANGED || diff.getDelta() ==
		// Delta.IGNORED)
		// return;

		for (int i = 0; i < indent; i++)
			System.err.print("   ");

		System.err.println(diff.toString());

		// if (diff.getDelta().isStructural())
		// return;

		for (Diff c : diff.getChildren()) {
			show(c, indent + 1);
		}
	}

	static void show(Tree tree, int indent) {

		for (int i = 0; i < indent; i++)
			System.err.print("   ");

		System.err.println(tree.toString());

		for (Tree c : tree.getChildren()) {
			show(c, indent + 1);
		}
	}
}
