//written by André Betz 
//http://www.andrebetz.de

public class ScanElement {
	String m_Value;
	int m_Type;
	ScanElement m_Next;
	ScanElement m_Last; 

	/* EnumTypes */
	public static int CONSTANT       = 0;
	public static int STRING_LITERAL = 1;	
	public static int CONSTANT_INT   = 2;	
	public static int CONSTANT_FLOAT = 3;	
	public static int CONSTANT_HEX   = 4;	
	public static int IDENTIFIER     = 5;	
	public static int VARIABLE       = 6;
	public static int OPERATOR       = 7;
	/* Ende */

	ScanElement(int Type,String value){
		m_Type = Type;
		m_Value = value;	
	}
	public String getElement() {
		return m_Value;
	}
	public ScanElement getM_Last() {
		return m_Last;
	}
	public ScanElement getM_Next() {
		return m_Next;
	}
	public int getType() {
		return m_Type;
	}
	public void setElement(String string) {
		m_Value = string;
	}
	public void setM_Last(ScanElement element) {
		m_Last = element;
	}
	public void setM_Next(ScanElement element) {
		m_Next = element;
		m_Next.setM_Last(this);
	}
	public void setType(int Type) {
		m_Type = Type;
	}
}
