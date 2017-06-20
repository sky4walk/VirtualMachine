//written by André Betz 
//http://www.andrebetz.de


class MyList
{
	Element First;
	Element Last;
	Element Walker;
	int Counter;
	int ActPos;
	boolean Started;
	
	MyList(){	
		Clear();
	}
	
	public int GetLength(){
		return Counter;
	}
	
	public int GoToObject(Object obj){
		int i = 0;
		Element temp = First;
		while(temp!=null){
			if(temp.get_value()==obj){
				return i;
			}
			i++;
			temp = temp.get_Next();
		}
		return -1;
	}
	
	public void Add(Object a){
		if(First==null){
			First = new Element(a);
			Last = First;
		}else{
			Last.set_Next(new Element(a));
			Last = Last.get_Next();
		}
		Counter++;
	}
	
	public void AddList(MyList lst){
		if(lst!=null){
			int len = lst.GetLength();
			lst.SetFirst();
			for(int i=0;i<len;i++){
				this.Add(lst.GetAct());
				lst.SetNext();				
			}
		}
	}
	
	public void DeleteAt(int pos){
		int i = 0;
		Element temp = First;
		while(temp!=null){
			if(pos==i){
				Element nxt = temp.get_Next();
				Element lst = temp.get_Last();
				if(lst==null){
					First = nxt;
				}else{
					lst.set_Next(nxt);
				}
				return;				
			}
			i++;
			temp = temp.get_Next();
		}
	}
	
	public void SetNext(){
		if(Walker!=null){
			Walker = Walker.get_Next();
			ActPos++;
		}else{
			Walker = null;
		}
	}
	
	public void SetFirst(){
		Walker = First;
		ActPos = 0;
	}
	
	public Object GetAct(){
		if(Walker!=null){
			return Walker.get_value();	
		}else{
			return null;
		}
	}
	
	public Object GetAt(int pos){
		int i = 0;
		Element temp = First;
		while(temp!=null){
			if(pos==i){
				return temp.get_value();
			}
			i++;
			temp = temp.get_Next();
		}
		return null;
	}
	
	public int GetPos(){
		return ActPos;
	}

	public void SetAt(int pos){
		int i = 0;
		Element temp = First;
		while(temp!=null){
			if(pos==i){
				ActPos = i;
				Walker = temp;
				break;
			}
			i++;
			temp = temp.get_Next();
		}
	}
	
	public int IndexOf(Object obj){
		int i = 0;
		Element temp = First;
		while(temp!=null){
			if(obj.equals(temp.get_value())){
				return i;
			}
			i++;
			temp = temp.get_Next();		
		}
		return -1;
	}
	
	public void Clear(){
		First = null;
		Last = null;
		Walker = null;
		Counter = 0;
		ActPos = 0;
	}
}
