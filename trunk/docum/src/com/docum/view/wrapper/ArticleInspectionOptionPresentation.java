package com.docum.view.wrapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.TreeNode;

import com.docum.domain.po.common.Article;
import com.docum.domain.po.common.ArticleInspectionOption;

public class ArticleInspectionOptionPresentation implements TreeNode, Serializable {
	private static final long serialVersionUID = -1195279104178916794L;

	public static final String TYPE = "ARTICLE_INSPECTION_OPTION_PRESENTATION";

	private ArticleInspectionOption option;

	private TreeNode parent;
	
	private List<TreeNode> children = new ArrayList<TreeNode>();

	private boolean expanded;

    private boolean selected;

    private boolean selectable;
    
	public ArticleInspectionOptionPresentation() {
		option = new ArticleInspectionOption();
	}

	public ArticleInspectionOptionPresentation(Article article) {
		this();
		option.setArticle(article);
	}
	
	public ArticleInspectionOptionPresentation(ArticleInspectionOption option) {
		this.option = option;
		for (ArticleInspectionOption child : option.getChildren()) {
			addChildInternal(new ArticleInspectionOptionPresentation(child));
		}
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public Object getData() {
		return option.getName();
	}

	@Override
	public List<TreeNode> getChildren() {
		return children;
	}

	@Override
	public TreeNode getParent() {
		return parent;
	}

	private void setParentInternal(ArticleInspectionOptionPresentation parent) {
		this.parent = parent;
	}

	@Override
	public void setParent(TreeNode node) {
		if(node instanceof ArticleInspectionOptionPresentation) {
			ArticleInspectionOptionPresentation parent = (ArticleInspectionOptionPresentation)node;
			setParentInternal(parent);
			option.setParent(parent.option);
		}
	}

	@Override
	public boolean isExpanded() {
		return expanded;
	}

	@Override
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
		
		if(parent != null) {
			parent.setExpanded(expanded);
		}
	}

	private void addChildInternal(ArticleInspectionOptionPresentation child) {
		child.setParentInternal(this);
		children.add(child);
	}
	@Override
	public void addChild(TreeNode child) {
		if(child instanceof ArticleInspectionOptionPresentation) {
			ArticleInspectionOptionPresentation item = (ArticleInspectionOptionPresentation)child;
			addChildInternal(item);
			option.addChild(item.option);
		}
	}

	@Override
	public int getChildCount() {
		return children.size();
	}

	@Override
	public boolean isLeaf() {
		return children.isEmpty();
	}

	@Override
    public boolean isSelected() {
        return this.selected;
    }

	@Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

	@Override
	public boolean isSelectable() {
        return this.selectable;
	}

	@Override
	public void setSelectable(boolean selectable) {
        this.selectable = selectable;
	}

}
