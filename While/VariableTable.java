//written by André Betz 
//http://www.andrebetz.de

public class VariableTable {
	String m_VarName = null;
	int m_VarType = -1;
	int m_Scope = 0;
	
	VariableTable(String VarName,int VarType,int Scope){
		Add(VarName,VarType,Scope);
	}
	
	void Add(String VarName,int VarType,int Scope){
		m_VarName = VarName;
		m_VarType = VarType;		
		m_Scope = Scope;
	}
}
