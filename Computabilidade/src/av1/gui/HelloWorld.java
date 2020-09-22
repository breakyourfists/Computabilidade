package av1.gui;
import org.eclipse.swt.widgets.Composite;

public class HelloWorld extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public HelloWorld(Composite parent, int style) {
		super(parent, style);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
