//written by André Betz 
//http://www.andrebetz.de

class RuleElement
{
	RuleElement m_Next;
	RuleElement m_Begin;
	String m_Token;
	RuleElement(String Token,RuleElement re){
		m_Token = Token;
		m_Begin = re;
		m_Next = null;
	}
	public void SetBegin(RuleElement re){
		m_Begin = re;
	}
	public RuleElement GetBegin(){
		return m_Begin;
	}
	public void SetNext(RuleElement re){
		m_Next = re;
	}
	public RuleElement GetNext(){
		return m_Next;
	}
	public String GetToken(){
		return m_Token;
	}
	public boolean IsTerminal(){
		return false;
	}
}