import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;

class Node 
{
  public int val;
  public Node left;
  public Node right;
  public Node p;
  public int is_Red;
  
  public Node(int newval) 
  {
    val = newval;
    left = Tree_NIL();
    right = Tree_NIL();
    p = Tree_NIL();
    is_Red = 1;
  }
  
  public Node()
  {
  	val = 0;
    left = null;
    right = null;
    p = null;
    is_Red = 0;
  }
  
  public static Node Tree_NIL()
  {
  	return new Node();
  }	
}

class RBT
{
	public Node root;
	
	public RBT()
	{
		root = Node.Tree_NIL();
	}

	public void RBT_insert(Node n)
	{
		Node y = Node.Tree_NIL();
		Node x = root;
	    while (x.val != 0)
	    {
	    	y = x;
	    	if (n.val < x.val)
	    		x = x.left;
	    	else
	    		x = x.right;
	    }
	    n.p = y;
	    if (y.val == 0)
	    	root = n;
	    else if (n.val < y.val)
	    	y.left = n;
	    else
	    	y.right=n;
	    
	    
		RBT_insert_fixup(n);
	}
	
	
	public void left_rotate(Node x)
	{
		Node y = x.right;
		x.right = y.left;
		if(y.left.val != 0)
			y.left.p = x;
		y.p = x.p;
		if(x.p.val == 0)
			root = y;
		else if(x==x.p.left)
			x.p.left =y;
		else
			x.p.right = y;
		y.left = x;
		x.p = y;
	}

	public void right_rotate(Node x)
	{
		Node y = x.left;
		x.left = y.right;
		if(y.right.val != 0)
			y.right.p = x;
		y.p = x.p;
		if(x.p.val == 0)
			root = y;
		else if( x==x.p.right )
			x.p.right =y;
		else
			x.p.left = y;
		y.right = x;
		x.p =y;
	}

	public void RBT_insert_fixup(Node n)
	{
		Node y;
			while( n.p.is_Red == 1)
			{
				if(n.p == n.p.p.left)
				{
					y = n.p.p.right; 
					if(y.is_Red == 1)
					{
						n.p.is_Red = 0;
						y.is_Red = 0;
						n.p.p.is_Red = 1;
						n = n.p.p;
					}
					else
					{
					 if (n==n.p.right)
					 { 
						n = n.p;
						left_rotate(n);
					}
					n.p.is_Red = 0;
					n.p.p.is_Red = 1;
					right_rotate(n.p.p);
					}
					
				}
				else
				{
					y = n.p.p.left; 
					if(y.is_Red == 1)
					{
						n.p.is_Red = 0;
						y.is_Red = 0;
						n.p.p.is_Red = 1;
						n = n.p.p;
					}
					else
					{
						if (n==n.p.left)
					 	{
							n = n.p;
							right_rotate(n);
					 	}
					n.p.is_Red = 0;
					n.p.p.is_Red = 1;
					left_rotate(n.p.p);
					}
				}
		
		}
		root.is_Red = 0; 
	}


	public void RBT_delete(Node n)
	{
		Node y = n;
 	 	int y_originalColor = y.is_Red;
 	 	Node x;
	 	if(n.left.val == 0)
	 	{
	 		x = n.right;
	 		RBT_Trans(n,n.right);
	 	}
	 	else if(n.right.val == 0)
	 	{
	 		x = n.left;
	 		RBT_Trans(n, n.left);
	 	}
	 	else
	 	{
	 		y = Tree_minimum(n.right);
	 		y_originalColor = y.is_Red;
	 		x = y.right;
	 		if(y.p == n)
	 		 x.p=y;
	 		else
	 		{
	 			RBT_Trans(y,y.right);
	 			y.right = n.right;
	 			y.right.p =y;
	 		}
	 		RBT_Trans(n,y);
	 		y.left = n.left;
	 		y.left.p = y;
	 		y.is_Red = n.is_Red;
	 	}
	 	if(y_originalColor == 0)
	 		RBT_delete_fixup(x);
	 }

	
   public void RBT_delete_fixup(Node x)
   {
   	Node w;
  	while(x!=root && x.is_Red == 0)
  	{
  		if(x==x.p.left)
  		{
  			w = x.p.right;
  			if(w.is_Red == 1)
  			{
  				w.is_Red = 0;
  				x.p.is_Red = 1;
  				left_rotate(x.p);
  				w=x.p.right;
  			}
  			if(w.left.is_Red == 0 && w.right.is_Red == 0)
  			{
  				w.is_Red =1;
  				x=x.p;
  			}
  			else
  			{
  				if(w.right.is_Red == 0)
  				{
  					w.left.is_Red = 0;
  					w.is_Red = 1;
  					right_rotate(w);
  					w = x.p.right;
  				}
  				w.is_Red = x.p.is_Red;
  				x.p.is_Red = 0;
  				w.right.is_Red = 0;
  				left_rotate(x.p);
  				x = root;
  			}
  		}
  		else
  		{
  			w = x.p.left;
  			if(w.is_Red == 1)
  			{
  				w.is_Red = 0;
  				x.p.is_Red = 1;
  				right_rotate(x.p);
  				w=x.p.left;
  			}
  			if(w.right.is_Red == 0 && w.left.is_Red ==0)
  			{
  				w.is_Red = 1;
  				x=x.p;
  			}
  			else
  			{
  				if(w.left.is_Red == 0)
  				{
  					w.right.is_Red = 0;
  					w.is_Red = 1;
  					left_rotate(w);
  					w = x.p.left;

  				}
  				w.is_Red = x.p.is_Red;
  				x.p.is_Red = 0;
  				w.left.is_Red = 0;
  				right_rotate(x.p);
  				x = root;
  			}
  		}
	}
	x.is_Red = 0;

  	}

	public Node Tree_minimum(Node n)
	{
	    while(n.left.val != 0)
	    {
	      n = n.left;
	    }
	    return n;
	  }

	public void RBT_Trans(Node u, Node v)
	{
	  	if(u.p.val==0)
	  		root = v;
	  	else if(u == u.p.left)
	  		u.p.left = v;
	  	else
	  		u.p.right = v;
	  	v.p = u.p;
	 }


	 public Node tree_search(Node tree, int val) 
	 {
	 	if (tree.val == 0)
	 	{
	 		return Node.Tree_NIL();
	 	}
	    else if (val == tree.val)
	      return tree;
	    else if (val < tree.val)
	      return tree_search(tree.left,val);
	    else
	      return tree_search(tree.right,val);
  	}


  	public void inorder(Node tree) 
  	{
    	if (tree.val == 0)
      		return;
   		else 
   		{
	      inorder(tree.left);
	      System.out.print(tree.val);
	      if(tree.is_Red==1)
	    	  System.out.println(" R");
	      else
	    	  System.out.println(" B");
	      inorder(tree.right);
   		}
 	 }


	public int Black_Node_Count(Node tree)
	{	
		int bn = 0;
		if(tree.left.val != 0) 
			bn+=Black_Node_Count(tree.left);
		if(tree.right.val != 0) 
			bn+=Black_Node_Count(tree.right);
		if(tree.is_Red == 0) 
			bn+=1;
		return bn;
	}


	public int BlackHeight(Node tree)
	{
		if(tree.val == 0)
			return 0;
		else if(tree.is_Red==0)
			return BlackHeight(tree.left) + 1;
		else
			return BlackHeight(tree.left);
	}

}



public class main 
{
    public static void main(String[] args) throws IOException 
    {
    	
    	File dir = new File("./input/"); 
		File[] fileList = dir.listFiles(); 
		String file_name=" ";

			for(int i = 0; i < fileList.length ; i++){
				File file = fileList[i]; 
				if(file.isFile()){
					RBT RBInst = new RBT();
					int total_Node = 0;
			    	int insert_Node = 0;
			    	int delete_Node = 0;
			    	int miss_Node = 0;
			    	
					file_name = file.getName();
					
			        BufferedReader br = new BufferedReader(new FileReader("./input/"+file_name));
			        while(true) 
			        {
			            String line = br.readLine();
			            if (line==null) break;
			            int num = Integer.parseInt(line.trim());
			            if(num > 0){
			            	RBInst.RBT_insert(new Node(num));
			            	total_Node++;
			            	insert_Node++;
			            }
			            else if(num<0)
			            {
			            	Node n;
			            	n = RBInst.tree_search(RBInst.root, -1*num);
			            	if(n.val == 0)
			            	{
			            		//System.out.println("Couldn't find the node "+ (-1*num));
			            		miss_Node++;
			            	}
			            	else
			            	{
			           			RBInst.RBT_delete(n);
			           			total_Node--;
			           			delete_Node++;
			           		}
			           	}
			            else break;
			        }
			        br.close();
			        System.out.println("filename = "+file_name);
			        System.out.println("total = " + total_Node);
			        System.out.println("insert = "+ insert_Node);
			        System.out.println("delete = "+delete_Node);
			        System.out.println("miss = "+miss_Node);
			        System.out.println("nb = " + RBInst.Black_Node_Count(RBInst.root));
			        System.out.println("bh = " + RBInst.BlackHeight(RBInst.root));
			        RBInst.inorder(RBInst.root);
				}
				
			}


    }
}