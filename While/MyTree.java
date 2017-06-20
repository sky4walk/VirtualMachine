//written by André Betz 
//http://www.andrebetz.de
public class MyTree extends Element{
	MyList m_Nodes;
	MyTree m_Root;

	MyTree(Object obj){
		super(obj);
		m_Nodes = null;
		m_Root = null;
	}
	
	public void AddNode(MyTree Node)
	{
		Node.setM_Root(this);
		if(m_Nodes==null){
			m_Nodes = new MyList();
		}
		m_Nodes.Add(Node);
	}

	public void ResetNodes(){
		if(m_Nodes!=null){
			m_Nodes.SetFirst();
		}
	}
	
	public MyTree GetNextNode()
	{
		if(m_Nodes!=null){			
			MyTree tmpTree = (MyTree)m_Nodes.GetAct();
			m_Nodes.SetNext();
			return tmpTree;
		}else{
			return null;
		}
	}
	
	public int GetNodesCount(){
		if(m_Nodes!=null){
			return m_Nodes.GetLength();
		}else{
			return 0;
		}
	}
	
	public MyTree getM_Root() {
		return m_Root;
	}

	public void setM_Root(MyTree root) {
		m_Root = root;
	}
}
