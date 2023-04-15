package librarysystem;

import business.LibraryMember;
import business.LibrarySystemException;

public interface LibWindow {
	void init() throws LibrarySystemException;
	boolean isInitialized();
	void isInitialized(boolean val);
	void setVisible(boolean b);
}

