//written by André Betz 
//http://www.andrebetz.de
public class BNFTree {
	MyList m_Rules;
	MyList m_TreeRules;
	
	BNFTree(MyList BNF){
		m_Rules = BNF;
		m_TreeRules = new MyList();
	}
	
	public void GenerateTree(){
		int len = m_Rules.GetLength();
		for(int i=0;i<len;i++){
			BNFRule bnf = (BNFRule)m_Rules.GetAt(i);
			RuleStart rs = bnf.GetStart();
			RuleElement re = (RuleElement)rs;
			if(re!=null){
				MyTree Tree = FindTreeWithRule(re);
				if(Tree==null){
					Tree = new MyTree(re);
					m_TreeRules.Add(Tree);
				}
				RuleElement Node = rs.GetNext();
				Tree.AddNode(new MyTree(Node));
			}
		}
	}

	public MyList FindRuleElement(RuleElement re){
		MyList retLst = new MyList();
		m_TreeRules.SetFirst();
		MyTree treeElement = (MyTree)m_TreeRules.GetAct();
		while(treeElement!=null){
			MyList TreeFound = FindRuleElementInTree(treeElement,re);
			retLst.AddList(TreeFound);
			m_TreeRules.SetNext();
			treeElement = (MyTree)m_TreeRules.GetAct();
		}
		return retLst;
	}
	
	public MyList FindRuleElementInTree(MyTree mt,RuleElement re){
		MyList retLst = new MyList();
		if(mt!=null){
			mt.ResetNodes();
			MyTree NodeTree = mt.GetNextNode();
			while(NodeTree!=null){
				RuleElement NodeStart = (RuleElement)NodeTree.get_value();
				MyList NodeFind = FindElementInOneRule(NodeStart,re);
				retLst.AddList(NodeFind);
				NodeTree = mt.GetNextNode();
			}
		}
		return retLst;
	}
	
	public MyList FindElementInOneRule(RuleElement First,RuleElement re){
		MyList retLst = new MyList();
		while(First!=null){
			if(First.GetToken().equals((re.GetToken()))){
				retLst.Add(First);
			}
			First = First.GetNext();
		}
		return retLst;
	}
	
	public MyTree FindTreeWithRule(RuleElement re){	
		if(m_TreeRules!=null){
			m_TreeRules.SetFirst();
			MyTree treeElement = (MyTree)m_TreeRules.GetAct();
			while(treeElement!=null){
				RuleElement TreeRule = (RuleElement)treeElement.get_value();
				if(TreeRule.GetToken().equals(re.GetToken())){
					return treeElement;					
				}
				m_TreeRules.SetNext();
				treeElement = (MyTree)m_TreeRules.GetAct();
			}
		}
		return null;
	}
	
	private void AddRuleToTree(MyTree Tree,RuleStart rs){
		RuleElement Node = rs.GetNext();

		while(Node!=null)
		{				
//			AddNodeToTree(Tree,Node);
			Node = Node.GetNext();
		}		
	}
	
	private MyTree AddNodeToTree(MyTree Tree,RuleElement Root,RuleElement Node){
		if(Tree==null){
			Tree = new MyTree(Root);
			Tree.AddNode(new MyTree(Node));
			return Tree;
		}else{
			MyTree InPos = FindNode(Tree,Root);
			if(InPos!=null){
				if(!IsInNodes(InPos,Node))
				{
					MyTree OutPos = FindNode(Tree,Node);
					if(OutPos==null){
						InPos.AddNode(new MyTree(Node));
					}else{
						InPos.AddNode(OutPos);						
					}
				}
				return Tree;
			}else{
				Tree.AddNode(new MyTree(Node));
				return Tree;
			}
		}
	}
	
	private MyTree FindNode(MyTree RootTree, RuleElement Node){
		RuleElement re = (RuleElement)RootTree.get_value();
		if(re.GetToken().equals(Node.GetToken())){
			return RootTree;			
		}else{
			RootTree.ResetNodes();
			MyTree NextNode = RootTree.GetNextNode();
			
			while(NextNode!=null)
			{
				MyTree res = FindNode(NextNode,Node);
				if(res!=null){
					return res;
				}
				NextNode = RootTree.GetNextNode();
			}
			
			return null;
		}
	}
	
	private boolean IsInNodes(MyTree TreeNode, RuleElement Node){
		TreeNode.ResetNodes();
		MyTree TreeElement = TreeNode.GetNextNode();
		while(TreeElement!=null){
			RuleElement re = (RuleElement)TreeElement.get_value();
			if(re.GetToken().equals(Node.GetToken())){
				return true;			
			}
			TreeElement = TreeNode.GetNextNode();
		}				
		return false;
	}
}
